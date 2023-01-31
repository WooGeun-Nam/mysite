package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("list")
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
}
