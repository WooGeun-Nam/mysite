package com.douzone.mysite.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@PostMapping("")
	public JsonResult ex01(@RequestBody GuestbookVo vo) {
		guestbookService.addMessage(vo);
		
		vo.setPassword("");

		return JsonResult.success(vo);
	}

	@GetMapping("")
	public JsonResult ex01(@RequestParam(value = "sno", required = true, defaultValue = "0") Long startNo) {
		List<GuestbookVo> list = guestbookService.getMessageList();

		return JsonResult.success(list);
	}
	
}
