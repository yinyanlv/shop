package fun.quzhi.shop.filter;

import fun.quzhi.shop.common.Constant;
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
       HttpSession session = req.getSession();
        curUser = (User)session.getAttribute(Constant.SESSION_USER_KEY);

        if (curUser == null) {
            HttpServletResponseWrapper  wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            wrapper.setContentType("application/json;charset=UTF-8");
            PrintWriter out =  wrapper.getWriter();
            out.write("{\n" +
                    "    \"success\": false,\n" +
                    "    \"status\": 10006,\n" +
                    "    \"message\": \"用户未登录\",\n" +
                    "    \"result\": null\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
