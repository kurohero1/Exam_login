<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教員管理</h2>

            <form method="post" action="TeacherCreateExecute.action" id="teacherForm">
                <div class="row mx-3 mb-3 py-2">

                    <!-- 学校 -->



                    <div class="col-12 mt-3 mb-3">
                        <label class="form-label" for="schoolCd">学校</label>
                        <input type="text" class="form-control" name="schoolCd" id="schoolCd"
                               value="${param.schoolCd}" placeholder="学校を入力してください。" required>
                        <div class="invalid-feedback" style="color: orange;">
                            学校を入力してください。
                        </div>
                        <c:if test="${not empty duplicateError}">
                            <div class="text-warning ms-1">${duplicateError}</div>
                        </c:if>
                    </div>

                    <!-- 教員番号 -->
                    <div class="col-12 mt-3 mb-3">
                        <label class="form-label" for="teacheR">教員番号</label>
                        <input type="text" class="form-control" name="id" id="teacheR"
                               value="${param.id}" placeholder="教員番号を入力してください。" required>
                        <div class="invalid-feedback" style="color: orange;">
                            教員番号を入力してください。
                        </div>
                        <c:if test="${not empty duplicateError}">
                            <div class="text-warning ms-1">${duplicateError}</div>
                        </c:if>
                    </div>

                    <div class="col-12 mt-3 mb-3">
                        <label class="form-label" for="password">パスワード</label>
                        <input type="text" class="form-control" name="password" id="password"
                               value="${param.password}" placeholder="パスワードを入力してください。" required>
                        <div class="invalid-feedback" style="color: orange;">
                            パスワードを入力してください。
                        </div>
                    </div>

                    <div class="col-12 mt-3 mb-3">
                        <label class="form-label" for="name">教員名</label>
                        <input type="text" class="form-control" name="name" id="name"
                               value="${param.name}" placeholder="教員名を入力してください。" required>
                        <div class="invalid-feedback" style="color: orange;">
                            教員名を入力してください。
                        </div>
                    </div>

                    <!-- 登録ボタン -->
                    <div class="col-2 mt-2">
                        <button type="submit" class="btn btn-primary text-white" style="width: 120px; height: 40px; font-size: 16px;">登録して終了</button>
                    </div>
                </div>
            </form>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger mt-3 mx-3">${errorMessage}</div>
            </c:if>

            <div class="mt-4 ms-3">
                <a href="Menu.action" class="text-decoration: underline" style="color: blue;"> 戻る</a>
            </div>
        </section>
    </c:param>
</c:import>


