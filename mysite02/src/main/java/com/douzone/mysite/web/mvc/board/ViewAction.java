package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().findByBoardNo(no);
		request.setAttribute("vo", vo);
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			boolean check = false;
			for (Cookie cookie : cookies) {
				if (request.getParameter("no").equals(cookie.getName())) {
					check = true;
				}
			}
			if(!check) {
				new BoardDao().updateHit(no);
				// 쿠키 쓰기
				Cookie cookie = new Cookie(request.getParameter("no"), String.valueOf(1));
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(24 * 60 * 60); // 1day
				response.addCookie(cookie);
			}
		}
		
		MvcUtil.forward("board/view", request, response);
	}
}
