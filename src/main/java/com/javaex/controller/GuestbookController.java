package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDAO;
import com.javaex.vo.GuestVO;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GuestbookController");
		
		//action 파라미터의 값이 뭔지 알아야됨
		String action = request.getParameter("action");
		System.out.println(action); //업무구분
				
		if("list".equals(action)) { //리스트업무
			System.out.println("리스트");
			
			//db데이터가져온다  --> list
			GuestbookDAO guestbookDAO = new GuestbookDAO();
			List<GuestVO> guestList = guestbookDAO.guestSelect();
			System.out.println(guestList);
			
			//저밑에 있는 list.jsp에게 후반일 html을 만들고 응답문서 만들어 보낸다
			//1)request객체에 데이터를 넣어준다
			request.setAttribute("gList", guestList);
			
			//2)list.jsp에 request객체와 response객체를 보낸다
			//*포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);

		}else if("write".equals(action) ) { //등록업무
			System.out.println("등록");
			
			//파라미터3개 꺼내기
			String name = request.getParameter("name");
			String password =  request.getParameter("password");
			String content = request.getParameter("content");
			String regDate = request.getParameter("regDate");
			
			//데이터를 묶는다
			GuestVO guestVO = new GuestVO(name, password, content, regDate);
			System.out.println(guestVO);
			
			//DAO를 통해서 저장시키기
			GuestbookDAO guestbookDAO= new GuestbookDAO();
			guestbookDAO.guestInsert(guestVO);
			
			//리다이렉트
			response.sendRedirect("http://localhost:8080/guestbook/gbc?action=list");
		
		}else if("dform".equals(action)) { //삭제폼업무
			System.out.println("삭제폼");
	
			//삭제폼을 응답해야한다
			//1)DB관련 할일이 없다 - 안하면된다
			
			//2)jsp에게 화면을 그리게 한다(포워드)
			//deleteForm.jsp 포워드한다
			RequestDispatcher rd = request.getRequestDispatcher("/deleteForm.jsp");
			rd.forward(request, response);
		
		}else if("delete".equals(action)) {
			System.out.println("삭제");
			
			//파라미터에서 password 꺼내온다
			int no = Integer.parseInt(request.getParameter("no"));
			String password =  request.getParameter("password");
						
			//dao를 통해서 password를 주고 삭제
			GuestbookDAO guestbookDAO= new GuestbookDAO();
			guestbookDAO.guestDelete(no, password);
			
			// 리다이렉트 action=list
			response.sendRedirect("http://localhost:8080/guestbook/gbc?action=list");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
