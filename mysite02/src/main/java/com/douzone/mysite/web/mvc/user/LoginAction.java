package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.UserDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class LoginAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		
		UserVo authUser = new UserDao().findByEmailAndPassword(vo);
		
		if(authUser == null) {
			request.setAttribute("email", email);
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		/* login 처리 */
		
	}

}
