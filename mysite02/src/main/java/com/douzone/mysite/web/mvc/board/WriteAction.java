package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contentes = request.getParameter("contents");

		UserVo uVo = (UserVo)request.getSession().getAttribute("authUser");
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contentes);
		
		if("" == request.getParameter("gno")) {
			new BoardDao().newContents(vo, uVo.getNo());
		} else {
			Long gNo = Long.parseLong(request.getParameter("gno"));
			Long oNo = Long.parseLong(request.getParameter("ono"));
			Long depth = Long.parseLong(request.getParameter("depth"));
			vo.setgNo(gNo);
			vo.setoNo(oNo+1);
			vo.setDepth(depth+1);
			new BoardDao().replyContents(vo, uVo.getNo());
		}
		
		MvcUtil.redirect(request.getContextPath()+"/board", request, response);
	}

}
