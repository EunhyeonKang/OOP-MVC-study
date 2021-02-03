package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.MemberDao;
import db.DBClose;
import db.DBConnection;
import dto.MemberDto;

public class MemberDaoImpl implements MemberDao {
	
	// 아이디 중복검사 
	@Override
	public boolean getId(String id) {
		String sql = " SELECT COUNT(*) "
				+ " FROM MEMBER "
				+ " WHERE ID = ? ";
	
		Connection conn = null;	// DB CONNECTION 
		PreparedStatement psmt = null;	// SQL
		ResultSet rs = null;			// RESULT
		
		boolean findid = false;
		System.out.println("sql : " + sql ); 	// SQL 확인		
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			// 아이디가 있으면 true 반환
			int count = 0;
			if(rs.next()) count = rs.getInt(1);
			if(count > 0) findid = true;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		return findid;
	}
	
	// 회원가입하기
	@Override
	public boolean addMember(MemberDto dto) {
		if(getId(dto.getId())) {	// 아이디가 같음
			return false;
		}
		// DB에 Member를 추가하는 작업
		String id = dto.getId();
		String pwd = dto.getPwd();
		String name = dto.getName();
		String email = dto.getEmail();

		// AUTH : 관리자 0, 유저 1 
		String sql = " INSERT INTO MEMBER ( ID, PWD, NAME, EMAIL) " +
			 " VALUES ( ?, ?, ?, ?) ";
		System.out.println("sql : " + sql);
		
		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean b =false;
		
		try {
			conn = DBConnection.getConnection();
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,id);
			psmt.setString(2,pwd);
			psmt.setString(3,name);
			psmt.setString(4,email);
	
			
			// 쿼리 실행하기
			psmt.executeUpdate();//executeUpdate()
			b=true;
			// 업데이트여부 count변수에 담기 
			//if(rs.next()) b =true;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBClose.close(psmt, conn, rs);
		}
		return b;
	}
	// 로그인 하기 
	@Override
	public MemberDto login(String id, String pwd) {
		String sql = " SELECT ID, NAME, EMAIL"
				+ " FROM MEMBER "
				+ " WHERE ID = ? AND PWD = ?";
		
		System.out.println("sql : " + sql);

		// 기본셋팅
		PreparedStatement psmt = null;
		Connection conn = null;
		ResultSet rs = null;
	
		MemberDto loginUser = null;
		try {
			conn = DBConnection.getConnection();
			
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,id);
			psmt.setString(2,pwd);			
			
			// 쿼리 실행하기
			rs = psmt.executeQuery();
			// 업데이트여부 count변수에 담기 
			if(rs.next()) {
				String _id = rs.getString(1);
				String _name = rs.getString(2);
				String _email= rs.getString(3);
				loginUser = new MemberDto(_id, null, _name, _email);
			}
			
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			DBClose.close(psmt, conn, rs);
			
		}
		return loginUser;
	}

}
