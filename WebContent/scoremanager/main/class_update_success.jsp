<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>

            <div class="col-12 mt-3 mb-3 text-center" style="background-color: #66CDAA; color: black; padding: 5px 10px;">
    			<label class="form-label">変更が完了しました</label>

			</div>

            <div class="mt-4 ms-3">
    			<a href="ClassList.action" class="text-decoration: underline" style="color: blue;">クラス一覧</a>
			</div>
        </section>
    </c:param>
</c:import>
