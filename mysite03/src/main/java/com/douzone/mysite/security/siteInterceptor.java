package com.douzone.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

public class siteInterceptor implements HandlerInterceptor {
	@Autowired
	private ServletContext servletContext;
	@Autowired
	private SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(servletContext.getAttribute("site") == null) {
			SiteVo site = siteService.getSite();
			servletContext.setAttribute("site", site);
		}
		
		return true;
	}

}
