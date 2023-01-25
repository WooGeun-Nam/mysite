package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteFormAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(null != request.getParameter("gno")) {
			BoardVo vo = new BoardVo();
			vo.setgNo(Long.parseLong(request.getParameter("gno")));
			vo.setoNo(Long.parseLong(request.getParameter("ono")));
			vo.setDepth(Long.parseLong(request.getParameter("depth")));
			
			request.setAttribute("vo", vo);
		}
		
		MvcUtil.forward("board/write", request, response);
	}

}
