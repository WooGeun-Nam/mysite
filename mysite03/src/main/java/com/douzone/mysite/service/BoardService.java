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
	@Autowired
	private BoardRepository boardRepository;
	
	public void addContents(BoardVo vo) {
		if(null == vo.getgNo()) {
			boardRepository.addContents(vo);
		} else {
			vo.setgNo(vo.getgNo());
			vo.setoNo(vo.getoNo()+1);
			vo.setDepth(vo.getDepth()+1);
			boardRepository.replyContents(vo);
		}
	}
	
	public BoardVo getContents(Long no) { // view 에서 쓰는거
		return boardRepository.findByBoardNo(no);
	}
	
	public BoardVo getContents(Long no, Long userNo) { // 수정
		return boardRepository.findByBoardNoAndUserNo(no, userNo);
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.updateByNo(vo);
	}
	
	public void hitsContents(Long no) {
		boardRepository.updateHit(no);
	}
	
	public void deleteContents(Long no, Long userNo) {
		boardRepository.deleteByNo(no, userNo);
	}
	
	public Map<String, Object> getContentsList(int pageNo, String keyword, int listSize) {
		// 1. view에서 게시판 리스트를 렌더링 하기 위한 데이터 값 계산
		int listCount = boardRepository.getTotalCount(keyword);
		int size = (int)Math.ceil(listCount/(double)listSize);
		
		int startNo = (int) (listCount-(pageNo-1)*listSize);
		
		int begin;
		if(pageNo%5==0) {
			begin = (pageNo/5-1)*5+1;
		} else {
			begin = (pageNo/5)*5+1;
		}
		int end = begin+4;
		
		// 2. 리스트 가져오기
		List<BoardVo> list = boardRepository.findPage(pageNo , keyword, listSize);
		
		// 3. 리스트 정보를 맵에 저장하기
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		
		map.put("listSize", listSize);
		map.put("startno", startNo);
		map.put("keyword", keyword);
		map.put("begin", begin);
		map.put("end", end);
		map.put("pageNo", pageNo);
		map.put("size", size);
		
		return map;
	}
}
