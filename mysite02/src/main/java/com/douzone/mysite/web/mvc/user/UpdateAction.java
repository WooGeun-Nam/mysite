package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		UserVo vo = new UserVo();
		vo.setNo(no);
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);

		new UserDao().update(vo);
		
		UserVo authUser = new UserVo();
		authUser.setNo(vo.getNo());
		authUser.setName(vo.getName());
		
		request.getSession().setAttribute("authUser", authUser);
		
		MvcUtil.redirect(request.getContextPath()+"/user?a=updateform", request, response);
	}

}
