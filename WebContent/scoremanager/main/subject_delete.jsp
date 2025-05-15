<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
<c:param name="title">
    得点管理システム
</c:param>

<c:param name="scripts"></c:param>

<c:param name="content">
<section>
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>
<form action="SubjectDeleteExecute.action" method="post">

<div class="form-group mt-4">
    <p class="form-control-static">「${name}(${cd})」を削除してもよろしいですか</p>

    <!-- 提交用的真正字段，只提交 cd，不拼接 name -->
    <input type="hidden" name="cd" value="${cd}">

    <button class="btn btn-danger fw-bold px-4 py-2" type="submit">削除</button>
</div>


<div class="mx-auto py-2 mt-5">
    <a href="SubjectList.action" style="color: blue; text-decoration: underline;">戻る</a>
</div>
</form>
</section>
</c:param>
</c:import>
