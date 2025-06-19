<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.List" %>	
<%@ page import = "com.javaex.vo.GuestVO" %>

<%
	List<GuestVO> guestList = (List)request.getAttribute("gList");	
	System.out.println("여기는 jsp");
	System.out.println(guestList);
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
	</head>
	<body>
		<form action="http://localhost:8080/guestbook/gbc"  method="get">
			<table>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password" value = ></td>
			        <input type="hidden" name="no" value="<%= request.getParameter("no") %>">
					<input type = "hidden" name = "action"  value = "delete">
					<td>
						<button type="submit">삭제</button>
					</td>
				</tr>
			</table>
		</form>

		<br>
		<a href="http://localhost:8080/guestbook/gbc">메인으로 돌아가기</a>
	</body>
</html>