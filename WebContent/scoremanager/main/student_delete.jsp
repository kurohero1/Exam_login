<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">学生削除結果</c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生削除結果</h2>

            <div class="alert alert-info mt-3 mx-3">
                <c:out value="${message}"/>
            </div>

            <div class="mt-4 ms-3">
                <a href="StudentList.action" class="text-decoration: underline" style="color: blue;"> 学生一覧に戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
