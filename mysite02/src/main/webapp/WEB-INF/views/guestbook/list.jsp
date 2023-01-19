<%@ page import="java.util.List"%>
<%@ page import="com.douzone.mysite.dao.GuestBookDao"%>
<%@ page import="com.douzone.mysite.vo.GuestBookVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
	List<GuestBookVo> list = (List<GuestBookVo>)request.getAttribute("list");
%>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/guestbook.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="guestbook">
				<form action="<%=request.getContextPath() %>/guestbook" method="post">
					<input type="hidden" name="a" value="insert">
					<table border=1 width=500>
						<tr>
							<td>이름</td><td><input type="text" name="name"></td>
							<td>비밀번호</td><td><input type="password" name="password"></td>
						</tr>
						<tr>
							<td colspan=4><textarea name="message" cols=60 rows=5 style="resize: none;"></textarea></td>
						</tr>
						<tr>
							<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
						</tr>
					</table>
				</form>
				<ul>
					<li>
						<%
							int i = list.size();
							for(GuestBookVo vo : list) {
								
						%>
						<table width=510 border=1>
							<tr>
								<td>[<%=i %>]</td>
								<td><%=vo.getName() %></td>
								<td><%=vo.getRegDate() %></td>
								<td><a href="<%=request.getContextPath() %>/guestbook?a=deleteform&&no=<%=vo.getNo()%>">삭제</a></td>
							</tr>
							<tr>
								<td colspan="4"><textarea readonly cols=60 rows=3 style="resize: none;"><%=vo.getMessage() %></textarea></td>
							</tr>
						</table>
						<%
								i--;
							}
						%>
						<br>
					</li>
				</ul>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>