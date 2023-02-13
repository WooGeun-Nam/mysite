package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		List<GuestbookVo> list = guestbookService.getMessageList();
		
		model.addAttribute("list", list);
		
		return "guestbook/list";
	}
	
	@RequestMapping("/add")
	public String add(GuestbookVo vo) {
		System.out.println(vo);
		guestbookService.addMessage(vo);
		System.out.println(vo);
		
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Model model,String no) {
		model.addAttribute("no", no);
		
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(Model model, GuestbookVo vo) {
		
		GuestbookVo guestBook = guestbookService.getByNo(vo.getNo());
		
		if(guestBook.getPassword().equals(vo.getPassword())) {
			guestbookService.deleteMessage(vo.getNo(), vo.getPassword());
			return "redirect:/guestbook/list";
		} else {
            model.addAttribute("msg","비밀번호가 틀렸습니다.");
            model.addAttribute("url","/guestbook/delete?no="+vo.getNo());
			return "tools/redirect";
		}
	}
}
