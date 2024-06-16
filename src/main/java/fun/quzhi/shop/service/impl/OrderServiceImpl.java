package fun.quzhi.shop.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.zxing.WriterException;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.filter.UserFilter;
import fun.quzhi.shop.model.dao.CartMapper;
import fun.quzhi.shop.model.dao.OrderItemMapper;
import fun.quzhi.shop.model.dao.OrderMapper;
import fun.quzhi.shop.model.dao.ProductMapper;
import fun.quzhi.shop.model.pojo.Order;
import fun.quzhi.shop.model.pojo.OrderItem;
import fun.quzhi.shop.model.pojo.Product;
import fun.quzhi.shop.model.request.CreateOrderReq;
import fun.quzhi.shop.model.vo.CartVO;
import fun.quzhi.shop.model.vo.OrderItemVO;
import fun.quzhi.shop.model.vo.OrderVO;
import fun.quzhi.shop.service.CartService;
import fun.quzhi.shop.service.OrderService;
import fun.quzhi.shop.util.OrderCodeFactory;
import fun.quzhi.shop.util.QRCodeGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    CartService cartService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Value("${app.file-upload-ip}")
    String ip;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(CreateOrderReq createOrderReq) {
        // 用户id
        String userId = UserFilter.curUser.getId();

        // 从购物车查找已勾选的商品
        List<CartVO> cartVOList = cartService.list(userId);
        List<CartVO> selectedList = new ArrayList<>();
        for (CartVO cartVO : cartVOList) {
            if (cartVO.getSelected().equals(Constant.Cart.CHECKED)) {
                selectedList.add(cartVO);
            }
        }
        cartVOList = selectedList;
        // 购物车已勾选的为空
        if (CollectionUtils.isEmpty(cartVOList)) {
            throw new ShopException(ShopExceptionEnum.CART_SELECTED_EMPTY);
        }
        // 判断商品是否存在、装填、库存
        validateStatusAndStock(cartVOList);
        // 把购物车对象转换成订单item对象
        List<OrderItem > orderItemList = toOrderItemList(cartVOList);
        // 库存校验，扣库存
        for (OrderItem orderItem : orderItemList) {
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            Integer stock = product.getStock() - orderItem.getQuantity();
            if (stock < 0) {
                throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_ENOUGH);
            }
            product.setStock(stock);
            productMapper.updateByPrimaryKeySelective(product);
        }
        // 从购物车中删除已勾选的商品
        cleanCart(cartVOList);
        // 生成订单
        Order order = new Order();
        String orderCode = OrderCodeFactory.getOrderCode();
        order.setCode(orderCode);
        order.setUserId(userId);
        order.setTotalPrice(totalPrice(orderItemList));
        order.setReceiverName(createOrderReq.getReceiverName());
        order.setReceiverPhone(createOrderReq.getReceiverPhone());
        order.setReceiverAddress(createOrderReq.getReceiverAddress());
        order.setStatus(Constant.OrderStatusEnum.UNPAID.getCode());
        order.setPostage(0);
        order.setPaymentType(1);
        order.setCreateBy(userId);
        order.setUpdateBy(userId);
        // 插入到订单表
        orderMapper.insertSelective(order);
        // 循环保存商品到order item表
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderCode(orderCode);
            orderItem.setCreateBy(userId);
            orderItem.setUpdateBy(userId);
            orderItemMapper.insertSelective(orderItem);
        }

        return orderCode;
    }

    public void validateStatusAndStock(List<CartVO> cartVOList) {
        for (CartVO cartVO : cartVOList) {
            Integer productId = cartVO.getProductId();
            Product product = productMapper.selectByPrimaryKey(productId);
            // 判断商品是否存在，商品是否上架
            if (product == null || product.getId().equals(Constant.SaleStatus.NOT_SALE)) {
                throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_SALE);
            }
            Integer count = cartVO.getQuantity();
            // 判断商品库存
            if (count > product.getStock()) {
                throw new ShopException(ShopExceptionEnum.PRODUCT_NOT_ENOUGH);
            }
        }
    }

    public List<OrderItem> toOrderItemList(List<CartVO> cartVOList) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for(CartVO cartVO : cartVOList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartVO.getProductId());
            orderItem.setProductName(cartVO.getProductName());
            orderItem.setProductImage(cartVO.getProductImage());
            orderItem.setProductPrice(cartVO.getPrice());
            orderItem.setQuantity(cartVO.getQuantity());
            orderItem.setTotalPrice(cartVO.getTotalPrice());
            orderItemList.add(orderItem);
        }
        return orderItemList;
    }

    public void cleanCart(List<CartVO> cartVOList) {
        for(CartVO cartVO : cartVOList) {
            cartMapper.deleteByPrimaryKey(cartVO.getId());
        }
    }

    public Integer totalPrice(List<OrderItem> orderItemList) {
        Integer totalPrice = 0;
        for (OrderItem orderItem : orderItemList) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public PageInfo listForCustomer(Integer pageNum, Integer pageSize) {
        String userId = UserFilter.curUser.getId();
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectForCustomer(userId);
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        PageInfo orderPageInfo = new PageInfo(orderList);
        orderPageInfo.setList(orderVOList);
        return orderPageInfo;
    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Order> orderList = orderMapper.selectAllForAdmin();
        List<OrderVO> orderVOList = orderListToOrderVOList(orderList);
        PageInfo orderPageInfo = new PageInfo(orderList);
        orderPageInfo.setList(orderVOList);
        return orderPageInfo;
    }

    private List<OrderVO> orderListToOrderVOList(List<Order> orderList) {
        List<OrderVO> orderVOList = new ArrayList<>();
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            OrderVO orderVO = getOrderVO(order);
            orderVOList.add(orderVO);
        }
        return orderVOList;
    }

    @Override
    public OrderVO detail(String orderCode) {
       Order order =  orderMapper.selectByOrderCode(orderCode);
       if (order == null) {
            throw new ShopException(ShopExceptionEnum.NO_ORDER);
       }
       String userId = UserFilter.curUser.getId();
       if (!order.getUserId().equals(userId)) {
           throw new ShopException(ShopExceptionEnum.NOT_YOUR_ORDER);
       }
       OrderVO orderVO = getOrderVO(order);
       return orderVO;
    }

    private OrderVO getOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(order, orderVO);
        // 获取订单orderItemVOList
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderCode(order.getCode());
        List<OrderItemVO> orderItemVOList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            orderItemVOList.add(orderItemVO);
        }
        orderVO.setOrderItemVOList(orderItemVOList);
        orderVO.setOrderStatusName(Constant.OrderStatusEnum.getByCode(orderVO.getStatus()).getName());
        return orderVO;
    }

    @Override
    public void cancel(String orderCode){
        Order order = orderMapper.selectByOrderCode(orderCode);
        // 订单不存在
        if(order == null){
            throw new ShopException(ShopExceptionEnum.NO_ORDER);
        }
        // 验证用户身份
        String userId = UserFilter.curUser.getId();
        if (!order.getUserId().equals(userId)) {
            throw new ShopException(ShopExceptionEnum.NOT_YOUR_ORDER);
        }
        if (order.getStatus().equals(Constant.OrderStatusEnum.UNPAID.getCode())  ) {
            order.setStatus(Constant.OrderStatusEnum.CANCELLED.getCode());
            order.setFinishTime(new Date());
            orderMapper.updateByPrimaryKeySelective(order);
        } else {
            throw new ShopException(ShopExceptionEnum.WRONG_ORDER_STATUS);
        }
    }

    @Override
    public String qrcode(String orderCode){
        ServletRequestAttributes attributes =  (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String address = ip + ":" + request.getLocalPort();
        String payUrl = "https://" + address + "/pay?orderCode=" + orderCode;
        try {
            QRCodeGenerator.generateQRCodeImage(payUrl, 350, 350, Constant.FILE_UPLOAD_PATH + orderCode + ".png");
        } catch (WriterException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        String pngAddress = "http://" + address + "/files/" + orderCode + ".png";
        return pngAddress;
    }
}
