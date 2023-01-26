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
		
		String keyword = null;
		if(request.getParameter("keyword") != null) {
			keyword = request.getParameter("keyword");
		} else {
			keyword = "";
		}
		
		Long page = null;
		if(request.getParameter("page") != null) {
			page = Long.parseLong(request.getParameter("page"));
		} else {
			page = 1L;
		}
		
		List<BoardVo> list = new BoardDao().findAll(keyword);
		
		int size = (int)Math.ceil(list.size()/5d);
		int startNo = (int) (list.size()-(page-1)*5);
		
		Long begin = null;
		if(page%5==0) {
			begin = (page/5-1)*5+1;
		} else {
			begin = (page/5)*5+1;
		}
		Long end = begin+4;
		
		list = new BoardDao().findPage(page,keyword);
		
		request.setAttribute("startno", startNo);
		request.setAttribute("keyword", keyword);
		request.setAttribute("begin", begin);
		request.setAttribute("end", end);
		request.setAttribute("page", page);
		request.setAttribute("size", size);
		request.setAttribute("list", list);
		
		MvcUtil.forward("board/list", request, response);
	}

}
