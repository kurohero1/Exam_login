<%-- 学生リストJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>
            <div class="my-2 text-end px-4">
                <a href="ClassCreate.action">新規登録</a>
            </div>

            <c:choose>
				<c:when test="${classNumList.size() > 0}">
	            	<div>検索結果：${classNumList.size()}件</div>
	                <table class="table table-hover">
                        <tr>
                            <th>学校</th>
                            <th>クラス</th>
                            <th></th>
                            <th></th>
                        </tr>
                        <c:forEach var="classNum" items="${classNumList }"><% %>
                            <tr>
                                <td>${classNum.school.cd }</td>
                                <td>${classNum.class_num }</td>
                                <td><a href="ClassUpdate.action?no=${classNum.class_num }">変更</a></td>
                            	<td><a href="ClassDelete.action?class_num=${classNum.class_num}">削除</a></td>
                            </tr>
                        </c:forEach>
                	</table>
                </c:when>
                <c:otherwise>
                	<div>クラス情報が存在しませんてした</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
