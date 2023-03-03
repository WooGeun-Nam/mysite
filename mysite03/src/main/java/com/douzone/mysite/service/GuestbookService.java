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
	
	public Boolean deleteMessage(Long no, String password) {
		return 1 == guestbookRepository.deleteByNoAndPassword(no, password);
	}
	
	public GuestbookVo getByNo(Long no) {
		return guestbookRepository.findNo(no);
	}

	public List<GuestbookVo> getMessageList(Long no) {
		return guestbookRepository.findAll(no);
	}	
}
