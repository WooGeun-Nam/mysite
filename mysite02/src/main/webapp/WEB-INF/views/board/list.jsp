<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="keyword" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${startno }"/>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count - status.index }</td>
							
							<td style="text-align:left; padding-left:${vo.depth*10}px">
								<c:if test="${vo.depth > 0 }">
									<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no }">${vo.title }</a>
							</td>
							<td>${vo.userVo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<c:if test="${vo.userVo.no == sessionScope.authUser.no }">
								<td><a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no }" class="del">삭제</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</table>
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${page != 1 }">
							<li><a href="${pageContext.request.contextPath }/board?page=${page - 1 }&keyword=${keyword }">◀</a></li>
						</c:if>
						<c:forEach begin="${begin }" end="${end }" step="1" var="i">
							<c:choose>
								<c:when test="${i == page && i <= size}">
									<li class="selected">${i }</li>
								</c:when>
								<c:when test="${i != page && i <= size}">
									<li><a href="${pageContext.request.contextPath }/board?page=${i }&keyword=${keyword }">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li>${i }</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${page != size }">
							<li><a href="${pageContext.request.contextPath }/board?page=${page + 1 }&keyword=${keyword }">▶</a></li>
						</c:if>
					</ul>
				</div>					
				<!-- pager 추가 -->
				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser }">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>