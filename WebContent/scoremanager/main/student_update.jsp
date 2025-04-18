<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>

			<form method="post" action="StudentUpdateExecute.action" id="studentForm">
			    <div class="mx-3 mb-3 py-2">

			        <!-- 入学年度 -->
			        <div class="mb-3">
			            <label class="form-label">入学年度</label>
			            <p class="form-control-plaintext">${student.entYear}</p>
			            <input type="hidden" name="f1" value="${student.entYear}" />
			        </div>

			        <!-- 学生番号 -->
			        <div class="mb-3">
			            <label class="form-label">学生番号</label>
			            <p class="form-control-plaintext">${student.no}</p>
			            <input type="hidden" name="no" value="${student.no}" />
			        </div>

			        <!-- 氏名 -->
			        <div class="mb-3">
			            <label class="form-label" for="name">氏名</label>
			            <input type="text" class="form-control" name="name" id="name" value="${student.name}" required />
			        </div>

			        <!-- クラス -->
			        <div class="mb-3">
			            <label class="form-label" for="classNum">クラス</label>
			            <select class="form-select" id="classNum" name="f2" required>
			                <option value="">--------</option>
			                <c:forEach var="num" items="${class_num_set}">
			                    <option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>${num}</option>
			                </c:forEach>
			            </select>
			        </div>

					<div class="mb-3">
					    <label class="form-label" for="isAttend">在学中：</label>
					    <input class="form-check-input ms-2" type="checkbox" id="isAttend" name="f3" value="t"
					        <c:if test="${student.attend}">checked</c:if> />
					</div>


			        <!-- ボタン -->
					<div class="col-2 mt-2">
					    <button type="submit" class="btn btn-primary text-white" style="width: 80px; height: 40px; font-size: 16px;">変更</button>
					</div>

			    </div>
			</form>



            <c:if test="${line > 0}">
                <div class="alert alert-success mt-3 mx-3">学生変更が完了しました。</div>
            </c:if>

            <div class="mt-4 ms-3">
                <a href="Menu.action" class="text-decoration: underline" style="color: blue;"> 戻る</a>
            </div>
        </section>
    </c:param>
</c:import>

<script>
    document.getElementById("studentForm").addEventListener("submit", function(event) {
        var form = this;
        var isValid = true;

        // 入学年度とクラスだけの検証
        const customFields = ["entYear", "classNum"];
        customFields.forEach(function(id) {
            const field = document.getElementById(id);
            if (!field.value || field.value === "0") {
                field.classList.add("is-invalid");
                isValid = false;
            } else {
                field.classList.remove("is-invalid");
            }
        });

        if (!isValid) {
            event.preventDefault();
        }
    });
</script>
