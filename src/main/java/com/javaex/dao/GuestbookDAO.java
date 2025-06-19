package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestVO;

public class GuestbookDAO {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/guestbook_db";
	private String id = "guestbook";
	private String pw = "guestbook";

	// 생성자
	public GuestbookDAO() {}

	// 메소드일반
	// DB연결 메소드-공통
	private void connect() { // 메인에서는 사용하지 못함

		try {
			// 1. JDBC 드라이버 (MySQL) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			this.conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}

	// 자원정리 메소드-공통
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	// 리스트 조회
	public List<GuestVO> guestSelect(){
		
		//리스트 생성
		List<GuestVO> guestList = new ArrayList<GuestVO>();
		
		this.connect();
		
		System.out.println("guestSelect()");
		try {
			// 3. SQL문준비 / 바인딩 / 실행
			// SQL문준비
			String query = "";
			query += " select no ";
			query += " 		  ,name ";
			query += " 		  ,password ";
			query += " 		  ,content ";
			query += " 		  ,reg_date ";
			query += " from   guestbook ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			// 4. 결과처리
			while (rs.next()) {// 반복한다
				// ResultSet에서 각각의 값을 꺼내서 자바 변수에 담는다
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String regDate = rs.getString("reg_date");

				// VO로 묶어준다
				GuestVO guestVO = new GuestVO(no, name, password, content, regDate);
				// 묶여진VO를 리스트에 추가한다
				guestList.add(guestVO);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return guestList;
	}
	
	// guest 등록
		public int guestInsert(GuestVO guestVO) {
			System.out.println("guestInsert()");
			
			int count = -1;
			this.connect();

			try {
				// 3. SQL문준비 / 바인딩 / 실행
				// - SQL문준비
				String query = "";
				query += " insert into guestbook ";
				query += " values(null, ?, ?, ?, now()) ";

				// - 바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, guestVO.getName());
				pstmt.setString(2, guestVO.getPassword());
				pstmt.setString(3, guestVO.getContent());

				// - 실행
				count = pstmt.executeUpdate();

				// 4. 결과처리
				System.out.println(count + "건이 저장되었습니다.");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			this.close();
			return count;
		}

		// guest 삭제
		public int guestDelete(int no, String password) {
			System.out.println("guestDelete");

			int count = -1;

			this.connect();

			try {
				// 3. SQL문준비 / 바인딩 / 실행
				// - SQL문준비
				String query = "";
				query += " delete from guestbook ";
				query += " where no = ? ";
				query += " and password = ? ";

				// - 바인딩
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				pstmt.setString(2, password);

				// - 실행
				count = pstmt.executeUpdate();

				// 4. 결과처리
				System.out.println(count + "건 삭제되었습니다.");

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			this.close();
			return count;
		}
	}
