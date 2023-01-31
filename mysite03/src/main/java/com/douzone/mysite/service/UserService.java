package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	// UserRepository 가 없으면 작동하지 않는다 -> 의존관계
	
	public void join(UserVo vo) {
		userRepository.insert(vo);
	}

	public UserVo getUser(UserVo vo) {
		return userRepository.findByEmailAndPassword(vo);
	}

	public void update(UserVo vo) {
		userRepository.update(vo);
	}

	public UserVo getUserByNo(Long no) {
		return userRepository.findByUserNo(no);
	}
}
