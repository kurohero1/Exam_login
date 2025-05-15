<%-- 科目変更JSP --%>
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
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
      <form action="SubjectUpdateExecute.action" method="post">

        <!-- 元の科目コード (hidden) -->
        <input type="hidden" name="original_cd" value="${original_cd}">

        <!-- 科目コード（表示のみ） -->
        <div class="mt-4">
  			<label for="cd" class="mb-2">科目コード</label>
  			<div style="text-indent: 1em;">
    			<p class="mb-0">${cd}</p>
  			</div>
		</div>
		<input type="hidden" name="cd" value="${cd}">
		<div class="mt-2 text-warning">${errors.get("1")}</div>

        <!-- 科目名 -->
        <div class="mt-4">
          <label for="name" class="mb-2">科目名</label><br>
          <input class="form-control" type="text" id="name" name="name"
                 value="${name}" required maxlength="30"
                 placeholder="科目名を入力してください" />
        </div>
        <div class="mt-2 text-warning">${errors.get("2")}</div>

        <!-- 変更ボタン -->
        <div class="mx-auto py-2 mt-4">
          <button class="btn btn-primary text-white fw-bold px-4 py-2"
                  type="submit">変更</button>
        </div>
      </form>

      <!-- 戻るリンク，蓝色带下划线 -->
      <div class="mt-3">
        <a href="SubjectList.action" style="color: blue; text-decoration: underline;">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>
