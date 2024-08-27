package fun.quzhi.shop.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import fun.quzhi.shop.common.Constant;
import fun.quzhi.shop.exception.ShopException;
import fun.quzhi.shop.exception.ShopExceptionEnum;
import fun.quzhi.shop.model.pojo.User;
import fun.quzhi.shop.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户过滤器
 */
public class UserFilter implements Filter {

    public static User curUser;

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
       HttpServletRequest req =  (HttpServletRequest)servletRequest;
       HttpSession session = req.getSession(true); // 创建一个新的session或获取现有的session

//       HttpSession session = req.getSession();
//        curUser = (User)session.getAttribute(Constant.SESSION_USER_KEY);
//        if (user == null) {
//            HttpServletResponseWrapper  wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
//            wrapper.setContentType("application/json;charset=UTF-8");
//            PrintWriter out =  wrapper.getWriter();
//            out.write("{\n" +
//                    "    \"success\": false,\n" +
//                    "    \"status\": 10006,\n" +
//                    "    \"message\": \"用户未登录\",\n" +
//                    "    \"result\": null\n" +
//                    "}");
//            out.flush();
//            out.close();
//            return;
//        }
        String token = req.getHeader(Constant.JWT_HEADER_TOKEN_KEY);
        if (token == null) {
            HttpServletResponseWrapper  wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            wrapper.setContentType("application/json;charset=UTF-8");
            PrintWriter out =  wrapper.getWriter();
            out.write("{\n" +
                    "    \"success\": false,\n" +
                    "    \"status\": 10006,\n" +
                    "    \"message\": \"jwt token required\",\n" +
                    "    \"result\": null\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        try {
            DecodedJWT jwt = verifier.verify(token);
            curUser = new User();
            curUser.setId(jwt.getClaim(Constant.USER_ID).asString());
            curUser.setUsername(jwt.getClaim(Constant.USER_NAME).asString());
            curUser.setRole(jwt.getClaim(Constant.USER_ROLE).asInt());
            session.setAttribute(Constant.SESSION_USER_KEY, curUser); // 将用户信息存储在session中
        } catch (TokenExpiredException e) {
            // token过期
            throw new ShopException(ShopExceptionEnum.TOKEN_EXPIRED);
        } catch (JWTDecodeException e) {
            // token解析失败
            throw new ShopException(ShopExceptionEnum.TOKEN_INVALID);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
