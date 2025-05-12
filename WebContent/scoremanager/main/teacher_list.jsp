<%-- 学生リストJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教員管理</h2>
            <div class="my-2 text-end px-4">
                <a href="TeacherCreate.action">新規登録</a>
            </div>

            <c:choose>
				<c:when test="${teacherList.size() > 0}">
	            	<div>検索結果：${teacherList.size()}件</div>
	                <table class="table table-hover">
                        <tr>
                            <th>学校</th>
                            <th>教員番号</th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="teacher" items="${teacherList }"><% %>
                            <tr>
                                <td>${teacher.school.cd }</td>
                                <td>${teacher.id }</td>
                                <td><a href="TeacherUpdate.action?no=${teacher.id }">変更</a></td>
                            	<td><a href="TeacherDelete.action?id=${teacher.id}">削除</a></td>
                            </tr>
                        </c:forEach>
                	</table>
                </c:when>
                <c:otherwise>
                	<div>教員情報が存在しませんてした</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
