package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		UserVo authUser = (UserVo)request.getSession().getAttribute("authUser");
//		
//		if(authUser == null) {
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter writer = response.getWriter();
//			writer.println("<script>alert('로그인 후 이용가능 합니다.'); location.href='"+request.getContextPath()+"/user?a=loginform';</script>");
//			return;
//		}
		
		List<BoardVo> list = new BoardDao().findAll();
		request.setAttribute("list", list);
		
		MvcUtil.forward("board/list", request, response);
	}

}
