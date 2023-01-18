package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");
		
		GuestBookVo guestBook = new GuestBookDao().findNo(no);
		
		if (guestBook.getPassword().equals(password)) {
			new GuestBookDao().deleteByPassword(no, password);
			response.sendRedirect(request.getContextPath()+"/guestbook");
		} else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter writer = response.getWriter();
			writer.println("<script>alert('비밀번호가 틀렸습니다.'); location.href='"+request.getContextPath()+"/guestbook?a=deleteform&&no=" + no+"';</script>"); 
			writer.close();
		}
	}
}
