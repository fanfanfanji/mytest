package edu.soft1.interceptor;

import edu.soft1.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckUserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取session对象
        HttpSession session = request.getSession();
        //判定session中的有无登录对象
        Object obj = session.getAttribute("user");//获取session中的登录对象
//        User user = (User) session.getAttribute("user");
        if (obj != null && obj instanceof User){
            System.out.println("拦截器放行");
        return true;
        }
        request.getRequestDispatcher("/index.jsp").forward(request,response);//跳转（转发）
//        response.sendRedirect("/indexx.jsp");//跳转（重定向）
        System.out.println("返回登录页");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
