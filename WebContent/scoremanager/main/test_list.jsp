<%-- テストリストJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

            <div class="border border-bottom mx-3 mb-2 px-3 py-2 align-items-center rounded" id="filter">
				<form action="TestListSubjectExecute.action" method="get">
					<div class="row">
						<div class="col-2 mt-4" style="text-align:center">
							<p>科目情報</p>
						</div>

						<div class="col-2">
							<label class="form-label" for="test-f1-select">入学年度</label>
							<select class="form-select" id="test-f1-select" name="f1">
								<option value="0">--------</option>
								<c:forEach var="year" items="${ent_year_set }">
									<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
									<option value="${year }" <c:if test="${year==f1 }">selected</c:if>>${year }</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-2">
							<label class="form-label" for="test-f2-select">クラス</label>
							<select class="form-select" id="test-f2-select" name="f2">
								<option value="0">--------</option>
								<c:forEach var="num" items="${class_num_set }">
									<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
									<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-4">
							<label class="form-label" for="test-subjectList-select">科目</label>
							<select class="form-select" id="test-subjectList-select" name="f3">
								<option value="0">--------</option>
								<c:forEach var="subject" items="${subjectList }">
									<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
									<option value="${subject.cd }" <c:if test="${subject.cd==param.subjectList }">selected</c:if>>${subject.name }</option>
								</c:forEach>
							</select>
						</div>

						<div class="col-2 mt-3 text-center">
						<input type="hidden" name="f" value="sj">
							<button class="btn btn-secondary" id="subject-button">検索</button>
						</div>
						<div class="mt-2 text-warning">${errors.get("1") }</div>
					</div>
				</form>

				<!-- 未選択エラーを文字で警告します -->
				<div id="warning-message" class="mt-2 text-warning" style="display:none;">
                    入学年度とクラスと科目を選択してください
                </div>

				<div class="row border-bottom mx-0 mb-3 py-2 align-items-center"></div>

				<form action="TestListStudentExecute.action" method="get">
					<div class="row">
						<div class="col-2 mt-3" style="text-align:center">
							<p>学生情報</p>
						</div>

						<div class="col-4">
							<label class="form-label" for="student-f4-select">学生番号</label>
							<input class="form-control" type="text" id="student-f4-select"
							name="f4" value="${f4 }" required maxlength="10"
							placeholder="学生番号を入力してください"/>
						</div>

						<div class="col-2 mt-3 text-center">
							<input type="hidden" name="f" value="st">
							<button class="btn btn-secondary" id="student-button">検索</button>
						</div>
					</div>
				</form>
			</div>

			<!-- 学生別成績一覧表示 -->
			<div style="color: #00FFFF;">
			科目情報を選択または学生情報を入力して検索ボタンをクリックしてください。
			</div>
        </section>
    </c:param>
</c:import>


<script>
    document.getElementById("subject-button").addEventListener("click", function(event) {
        // エラーをしたい値
        var f1 = document.getElementById("test-f1-select").value;
        var f2 = document.getElementById("test-f2-select").value;
        var f3 = document.getElementById("test-subjectList-select").value;

        // エラーをゲットするもの
        var warningMessage = document.getElementById("warning-message");

        // もし選択しない場合エラーをする，エラーメッセージも表示します
        if (f1 == "0" || f2 == "0" || f3 == "0") {
            event.preventDefault();  // フォームの送信を防止します
            warningMessage.style.display = "block";  // エラーメッセージを表示します
        } else {
            warningMessage.style.display = "none";  // エラーメッセージを後ろにします
        }
    });
</script>
