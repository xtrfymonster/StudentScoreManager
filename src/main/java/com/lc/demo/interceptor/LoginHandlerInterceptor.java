package com.lc.demo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录检查拦截器类，用于在请求处理过程中检查用户是否已经登录，以此来控制请求的访问权限。
 * 该类实现了Spring Web框架中的HandlerInterceptor接口，通过重写其定义的方法，
 * 可以在不同阶段对请求进行拦截和相应处理。
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    /**
     * preHandle方法在目标Controller方法执行之前被调用，用于进行登录验证等前置操作。
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从当前用户的会话（Session）中获取名为"loginUser"的属性值，通常在用户登录成功后，会将用户相关的信息（比如用户对象、用户ID等）以"loginUser"为键存储在会话中，
        // 这里通过获取该属性来判断用户是否已经登录。如果获取到的值为null，说明用户尚未登录。
        Object user = request.getSession().getAttribute("loginUser");
        if (user == null) {
            // 如果用户未登录，设置一个提示信息"没有权限请先登陆"到请求的属性中，方便在后续的登录页面（如/index.html）中可以获取并展示该提示信息给用户，告知用户需要登录。
            request.setAttribute("msg", "没有权限请先登陆");
            // 使用请求转发的方式将请求转发到"/index.html"页面，也就是引导用户跳转到登录页面进行登录操作。
            // 请求转发会在服务器端内部进行，对于客户端来说，它看到的请求路径仍然是最初发起请求的那个路径，只不过最终呈现的页面内容是转发后的页面内容。
            request.getRequestDispatcher("/index.html").forward(request, response);
            // 返回false，表示拦截该请求，不让其继续访问后续的目标Controller方法。
            return false;
        } else {
            // 如果用户已经登录（即从会话中获取到了"loginUser"属性值），则返回true，放行请求，允许请求继续执行后续的目标Controller方法，以便进行正常的业务逻辑处理。
            return true;
        }
    }

    /**
     * postHandle方法在目标Controller方法执行之后、视图渲染之前被调用，可以用于对ModelAndView对象进行一些修改或添加额外的模型数据等操作。
     * 在这个登录拦截器的简单场景中，当前没有具体的业务逻辑需要在此处处理，所以方法体为空，但如果后续有需要，例如想在视图渲染前添加一些通用的模型数据给所有视图使用等情况，
     * 就可以在这里进行相应的代码编写。
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * afterCompletion方法在整个请求处理完成（包括视图渲染完成）之后被调用，主要用于进行一些资源清理、记录日志等收尾工作。
     * 在当前登录拦截器的简单场景下，没有特定的收尾操作需要执行，所以方法体为空，但如果有例如关闭数据库连接、释放一些缓存资源等操作需求，
     * 可以在这里添加相应的代码逻辑来确保资源的正确释放和系统的良好状态维护。
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}