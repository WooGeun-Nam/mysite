package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;
	
	public List<GuestbookVo> getMessageList() {
		return guestbookRepository.findAll();
	}
	
	public void addMessage(GuestbookVo vo) {
		guestbookRepository.insert(vo);
	}
	
	public void deleteMessage(Long no, String password) {
		guestbookRepository.deleteByNoAndPassword(no, password);
	}
	
	public GuestbookVo getByNo(Long no) {
		return guestbookRepository.findNo(no);
	}
}
