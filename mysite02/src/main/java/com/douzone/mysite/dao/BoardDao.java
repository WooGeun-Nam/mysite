package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

public class BoardDao {

	public List<BoardVo> findAll(String keyword) {
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth, u.no "
					+ "from board b join user u on b.user_no = u.no where title like ? or contents like ? order by b.g_no desc, b.o_no asc;";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setString(2, "%"+keyword+"%");
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getLong(4));
				vo.setRegDate(rs.getString(5));
				vo.setDepth(rs.getLong(6));
				
				UserVo uVo = new UserVo();
				uVo.setName(rs.getString(3));
				uVo.setNo(rs.getLong(7));
				vo.setUserVo(uVo);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return result;
	}

	public List<BoardVo> findPage(Long page, String keyword) {
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth, u.no, b.status "
					+ "from board b join user u on b.user_no = u.no where title like ? or contents like ? order by b.g_no desc, b.o_no asc limit ?,5";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setString(2, "%"+keyword+"%");
			
			page = (page-1)*5;
			pstmt.setLong(3, page);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setHit(rs.getLong(4));
				vo.setRegDate(rs.getString(5));
				vo.setDepth(rs.getLong(6));
				vo.setStatus(rs.getString(8));
				
				UserVo uVo = new UserVo();
				uVo.setName(rs.getString(3));
				uVo.setNo(rs.getLong(7));
				vo.setUserVo(uVo);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return result;
	}
	
	public BoardVo findByBoardNo(Long no) {
		BoardVo result = new BoardVo();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select no, title, contents, hit, date_format(reg_date, '%Y-%m-%d'), g_no, o_no, depth, user_no from board where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setTitle(rs.getString(2));
				vo.setContents(rs.getString(3));
				vo.setHit(rs.getLong(4));
				vo.setRegDate(rs.getString(5));
				vo.setgNo(rs.getLong(6));
				vo.setoNo(rs.getLong(7));
				vo.setDepth(rs.getLong(8));
				
				UserVo uVo = new UserVo();
				uVo.setNo(rs.getLong(9));
				
				vo.setUserVo(uVo);
				
				result = vo;
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		return result;
	}
	
	public void newContents(BoardVo vo, Long userNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

			String sql = "insert into board values(null, ?, ?, 0, now(), (SELECT IFNULL(MAX(g_no) + 1, 1) FROM board b), 1, 0, ?, 'new')";
			// 0일때 1로 처리 완료
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, userNo);
			
			//5. SQL 실행
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void replyContents(BoardVo vo, Long userNo) {
		Connection conn = null;
		
		try {
			conn = getConnection();
			conn.setAutoCommit(false);

			String sql1 = "update board set o_no = o_no+1 where g_no = ? and o_no >= ?";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			pstmt1.setLong(1, vo.getgNo());
			pstmt1.setLong(2, vo.getoNo());
			pstmt1.executeUpdate();
			pstmt1.close();
			
			String sql2 = "insert into board values(null, ?, ?, 0, now(), ?, ?, ?, ?, 'new')";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.setLong(3, vo.getgNo());
			pstmt2.setLong(4, vo.getoNo());
			pstmt2.setLong(5, vo.getDepth());
			pstmt2.setLong(6, userNo);
			pstmt2.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("error:" + e);
		} finally {
			try {
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateByNo(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set title = ?, contents = ?, status = 'modify' where no = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateHit(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			
			String sql = "update board set hit = hit+1 where no = ?";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteByNo(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();

//			String sql = "delete from board where no = ?";
			String sql = "update board set status = 'deleted' where no = ?";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://"+StaticIP.IP+":"+StaticIP.PORT+"/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

// <td colspan=4><%=vo.getMessage() %></td> <!-- 멀티라인으로 개행까지 되게끔 -->