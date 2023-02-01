package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	private static final int LIST_SIZE = 3; // 리스팅되는 게시물의 수
	private static final Long PAGE_SIZE = 5L; // 페이지리스트의 사이즈 수
	
	@Autowired
	private BoardRepository boardRepository;
	
	public void addContents(BoardVo vo) {
		
	}
	
	public BoardVo getContents(Long no) { // view 에서 쓰는거
		return null;
	}
	
	public BoardVo getContents(Long no, Long serNo) { // 수정 폼
		return null;
	}
	
	public void updateContents(BoardVo vo) {
		
	}
	
	public void deleteContents(Long no, Long userNo) {
		
	}
	
	public Map<String, Object> getContentsList(int pageNo, String keyword) {
		//int toTalCount = boardRepository.getTotalCount(keyword);
		
		// 1. view에서 게시판 리스트를 렌더링 하기 위한 데이터 값 계산
		int beginPage = 0;
		int prevPage = 0;
		int nextPage = 0;
		int endPage = 0;
		
		// 2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findPage(PAGE_SIZE , keyword, LIST_SIZE);
		
		// 3. 리스트 정보를 맵에 저장하기
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		
		return map;
	}
}
