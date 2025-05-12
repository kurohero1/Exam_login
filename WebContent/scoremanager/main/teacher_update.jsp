<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">教員管理</h2>

            <form method="post" action="TeacherUpdateExecute.action" id="teacherForm">
                <div class="mx-3 mb-3 py-2">

                    <!-- 学校 -->
                    <div class="mb-3">
                        <label class="form-label">学校</label>
                        <p class="form-control-plaintext">${teacher.school.cd}</p>
                        <input type="hidden" name="schoolCd" value="${teacher.school.cd}" />
                    </div>

                    <!-- 教員番号 -->
                    <div class="cmb-3">
					    <label class="form-label" for="teacherId">教員番号</label>
					    <p class="form-control-plaintext">${param.no}</p>
					    <input type="hidden" class="form-control" name="id" id="teacherId" value="${param.no}" readonly>
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

                    <!-- ボタン -->
                    <div class="col-2 mt-2">
                        <button type="submit" class="btn btn-primary text-white" style="width: 80px; height: 40px; font-size: 16px;">変更</button>
                    </div>

                </div>
            </form>

            <c:if test="${line > 0}">
                <div class="alert alert-success mt-3 mx-3">教員変更が完了しました。</div>
            </c:if>

            <div class="mt-4 ms-3">
                <a href="Menu.action" class="text-decoration: underline" style="color: blue;"> 戻る</a>
            </div>
        </section>
    </c:param>
</c:import>

<script>
    document.getElementById("teacherForm").addEventListener("submit", function(event) {
        var form = this;
        var isValid = true;

        // クラスだけの検証
        const customFields = ["teacherId", "password", "name"];
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
