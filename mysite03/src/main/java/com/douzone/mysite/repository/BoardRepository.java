package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;

	public List<BoardVo> findAll() {
		return sqlSession.selectList("board.findAll");
	}
	
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount", keyword);
	}
	
	public List<BoardVo> findPage(int page, String keyword,int row) {
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", "%"+keyword+"%");
		map.put("page", (page-1)*row);
		map.put("row", row);
		return sqlSession.selectList("board.findPage", map);
	}
	
	public BoardVo findByBoardNo(Long no) {
		return sqlSession.selectOne("board.findByBoardNo", no);
	}
	
	public BoardVo findByBoardNoAndUserNo(Long no, Long userNo) {
		Map<String, Object> map = Map.of("no", no, "userno", userNo);
		
		return sqlSession.selectOne("board.findByBoardNoAndUserNo", map);
	}
	
	public void addContents(BoardVo vo) {
		sqlSession.insert("board.addContents", vo);
	}
	
	public void replyContents(BoardVo vo) {
		sqlSession.update("board.replyUpdate", vo);
		sqlSession.insert("board.replyContents", vo);
	}
	
	public void updateByNo(BoardVo vo) {
		sqlSession.update("board.updateByNo", vo);
	}
	
	public void updateHit(Long no) {
		sqlSession.update("board.updateHit", no);
	}
	
	public void deleteByNo(Long no, Long userNo) {
		Map<String, Object> map = Map.of("no", no, "userno", userNo);
		
		sqlSession.update("board.deleteByNo", map);
	}
}