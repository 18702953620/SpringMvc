package com.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.test.utils.Global;

public class UserInterceptor implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 请求后，返回数据之前

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

//		response.setHeader("Access-Control-Allow-Origin", "*");
		System.out.println("user=" + request.getSession().getAttribute(Global.USER_SESSION_KEY));
		// 请求之前调用
		if (!request.getRequestURI().contains("login") && !request.getRequestURI().contains("getUser")
				&& request.getSession().getAttribute(Global.USER_SESSION_KEY) == null) {
			response.sendRedirect("/SpringMvc/");
			return false;
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回数据之后
	}

}
