<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">クラス管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>

            <form method="post" action="ClassCreateExecute.action" id="classForm">
                <div class="row mx-3 mb-3 py-2">

                    <!-- 学校 -->
                    <div class="col-12 mt-3 mb-3">
                        <label class="form-label" for="schoolCd">学校</label>
                        <select class="form-control" name="schoolCd" id="schoolCd" required>
                            <option value="" disabled selected>学校を選択してください</option>
                            <c:forEach var="school" items="${schoolList}">
                                <option value="${school.cd}">${school.name}</option>
                            </c:forEach>
                        </select>
                        <div class="invalid-feedback" style="color: orange;">
                            学校を選択してください。
                        </div>
                    </div>

                    <!-- クラス -->
                    <div class="col-12 mt-3 mb-3">
                        <label class="form-label" for="classNumSet">クラス番号</label>
                        <input type="text" class="form-control" name="classNumSet" id="classNumSet"
                               value="${param.classNumSet}" placeholder="クラス番号を入力してください。" required>
                        <div class="invalid-feedback" style="color: orange;">
                            クラス番号を入力してください。
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



