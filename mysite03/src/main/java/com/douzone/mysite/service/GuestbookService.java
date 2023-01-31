package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	GuestbookRepository guestbookRepository = new GuestbookRepository();
	
	public List<GuestbookVo> getMessageList() {
		return null;
	}
	
	public void deleteMessage(Long no, String password) {
		
	}
	
	public void addMessage(GuestbookVo vo) {
		
	}
}
