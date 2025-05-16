<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生管理</h2>

            <form method="post" action="StudentCreateExecute.action" id="studentForm" >
                <div class="row mx-3 mb-3 py-2">

                    <!-- 入学年度 -->
                    <div class="cil-4">
                        <label class="form-label" for="student-fl-select">入学年度</label>
                        <select class="form-select" id="student-fl-select" name="f1" required>
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback" style="color: orange;">
                            入学年度を選択してください。
                        </div>
                    </div>

					<!-- 学生番号 -->
					<div class="col-12 mt-3 mb-3">
					    <label class="form-label" for="no">学生番号</label>
					    <input type="text" class="form-control" name="no" id="no"
					           value="${param.no}" placeholder="学生番号を入力してください。" required>
					    <div class="invalid-feedback" style="color: orange;">
					        学生番号を入力してください。
					    </div>
					    <c:if test="${not empty duplicateError}">
					        <div class="text-warning ms-1">${duplicateError}</div>
					    </c:if>
					</div>


					<!-- 氏名 -->
                    <div class="col-12 mb-3">
                        <label class="form-label" for="name">氏名</label>
                        <input type="text" class="form-control" name="name" id="name" value="${param.name}" placeholder="氏名を入力してください。" required>
                    </div>

                    <!-- クラス -->
                    <div class="cil-4">
                        <label class="form-label" for="student-f2-select">クラス</label>
                        <select class="form-select" id="student-f2-select" name="f2" required>
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback" style="color: orange;">
                            クラスを選択してください。
                        </div>
                    </div>

                    <!-- 登録ボタン -->
                    <!-- 登録ボタン -->
					<div class="col-2 mt-2">
					    <button type="submit" class="btn text-white" style="background-color: #6c757d; border: none; width: 120px; height: 40px; font-size: 16px;">
					        登録して終了
					    </button>
					</div>

                </div>
            </form>

            <c:if test="${line > 0}">
                <div class="alert alert-success mt-3 mx-3">学生登録が完了しました。</div>
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
        const customFields = ["student-fl-select", "student-f2-select"];
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




