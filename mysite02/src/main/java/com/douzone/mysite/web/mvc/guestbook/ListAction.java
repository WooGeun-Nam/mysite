package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web2.mvc.Action;
import com.douzone.web2.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void excute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<GuestBookVo> list = new GuestBookDao().findAll();
		request.setAttribute("list", list);

		MvcUtil.forward("guestbook/list", request, response);
	}

}
