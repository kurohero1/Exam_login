<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">
                <c:choose>
                    <c:when test="${displayMode == 'subject'}">成績一覧（科目別）</c:when>
                    <c:when test="${displayMode == 'student'}">成績一覧（学生別）</c:when>
                    <c:otherwise>成績一覧</c:otherwise>
                </c:choose>
            </h2>

            <!-- 検索フォーム部分 -->
            <div class="border border-bottom mx-3 mb-2 px-3 py-2 align-items-center rounded" id="filter">
                <!-- 科目検索フォーム -->
                <form action="TestListSubjectExecute.action" method="get">
                    <div class="row">
                        <div class="col-2 mt-4 text-center">
                            <p>科目情報</p>
                        </div>
                        <div class="col-2">
                            <label class="form-label" for="test-f1-select">入学年度</label>
                            <select class="form-select" id="test-f1-select" name="f1">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2">
                            <label class="form-label" for="test-f2-select">クラス</label>
                            <select class="form-select" id="test-f2-select" name="f2">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <label class="form-label" for="test-subjectList-select">科目</label>
                            <select class="form-select" id="test-subjectList-select" name="f3">
                                <option value="0">--------</option>
                                <c:forEach var="subject" items="${subjectList}">
                                    <option value="${subject.cd}" <c:if test="${subject.cd==param.f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-2 mt-3 text-center">
                            <input type="hidden" name="f" value="sj">
                            <button class="btn btn-secondary" id="subject-button">検索</button>
                        </div>
                        <div class="mt-2 text-warning">${errors.f1} ${errors.f2} ${errors.f3}</div>
                    </div>
                </form>

                <div class="row border-bottom mx-0 mb-3 py-2 align-items-center"></div>

                <!-- 学生検索フォーム -->
                <form action="TestListStudentExecute.action" method="get">
                    <div class="row">
                        <div class="col-2 mt-3 text-center">
                            <p>学生情報</p>
                        </div>
                        <div class="col-4">
                            <label class="form-label" for="student-f4-select">学生番号</label>
                            <input class="form-control" type="text" id="student-f4-select"
                                   name="f4" value="${f4}" required maxlength="10"
                                   placeholder="学生番号を入力してください"/>
                        </div>
                        <div class="col-2 mt-3 text-center">
                            <input type="hidden" name="f" value="st">
                            <button class="btn btn-secondary" id="student-button">検索</button>
                        </div>
                        <div class="col-4 mt-2 text-warning">${errors.f4}</div>
                    </div>
                </form>
            </div>

            <!-- 検索結果表示部分 -->
            <c:choose>
                <%-- 科目別成績表示モード --%>
                <c:when test="${displayMode == 'subject' && not empty testList}">
				    <div style="margin-top:20px;">
				        <h4 style="padding-bottom:5px; border-bottom:1px solid #eee;">科目: ${subjectName}</h4>

				        <!-- 表頭 -->
				        <div style="display:grid; grid-template-columns:repeat(6, 1fr); margin-bottom:5px; border-bottom:1px solid #eee; padding-bottom:5px;">
				            <div style="padding:3px; font-weight:bold;">入学年度</div>
				            <div style="padding:3px; font-weight:bold;">クラス</div>
				            <div style="padding:3px; font-weight:bold;">学生番号</div>
				            <div style="padding:3px; font-weight:bold;">氏名</div>
				            <div style="padding:3px; font-weight:bold;">1回目</div>
				            <div style="padding:3px; font-weight:bold;">2回目</div>
				        </div>

				        <!-- データ -->
				        <c:forEach var="student" items="${testList}">
				            <div style="display:grid; grid-template-columns:repeat(6, 1fr); margin-bottom:5px; padding-bottom:5px; border-bottom:1px solid #eee;">
				                <div style="padding:3px;">${student.entYear}</div>
				                <div style="padding:3px;">${student.classNum}</div>
				                <div style="padding:3px;">${student.studentNo}</div>
				                <div style="padding:3px;">${student.studentName}</div>
				                <div style="padding:3px;">
				                    <c:set var="point1" value="${student.getPoint(1)}" />
				                    <c:choose><c:when test="${point1 > 0}">${point1}</c:when><c:otherwise>-</c:otherwise></c:choose>
				                </div>
				                <div style="padding:3px;">
				                    <c:set var="point2" value="${student.getPoint(2)}" />
				                    <c:choose><c:when test="${point2 > 0}">${point2}</c:when><c:otherwise>-</c:otherwise></c:choose>
				                </div>
				            </div>
				        </c:forEach>
				    </div>
				</c:when>

                <%-- 学生別表示モード --%>
               <c:when test="${displayMode == 'student' && not empty testList}">
				    <div style="margin-top:20px;">
				        <h4 style="padding-bottom:5px; border-bottom:1px solid #eee;">氏名：${student.name} (${student.no})</h4>

				        <!-- 表頭 -->
				        <div style="display:grid; grid-template-columns:repeat(4, 1fr); margin-bottom:5px; border-bottom:1px solid #eee; padding-bottom:5px;">
				            <div style="padding:3px; font-weight:bold;">科目コード</div>
				            <div style="padding:3px; font-weight:bold;">科目名</div>
				            <div style="padding:3px; font-weight:bold;">回数</div>
				            <div style="padding:3px; font-weight:bold;">点数</div>
				        </div>

				        <!-- データ -->
				        <c:forEach var="test" items="${testList}">
				            <div style="display:grid; grid-template-columns:repeat(4, 1fr); margin-bottom:5px; padding-bottom:5px; border-bottom:1px solid #eee;">
				                <div style="padding:3px;">${test.subjectCd}</div>
				                <div style="padding:3px;">${test.subjectName}</div>
				                <div style="padding:3px;">${test.num}回目</div>
				                <div style="padding:3px;">
				                    <c:choose>
				                        <c:when test="${test.point > 0}">${test.point}</c:when>
				                        <c:otherwise>-</c:otherwise>
				                    </c:choose>
				                </div>
				            </div>
				        </c:forEach>
				    </div>
				</c:when>
				<c:otherwise>
				    <c:choose>
				        <%-- データなし --%>
				        <c:when test="${displayMode == 'subject' && empty testList}">
				            <div>成績情報が存在しませんでした</div>
				        </c:when>

				        <c:when test="${displayMode == 'student' && empty testList}">
					        <div>
					        氏名：${student.name} (${student.no})
				        	</div>
				            <div>成績情報が存在しませんでした</div>
				        </c:when>

				        <%-- 初期画面 --%>
				        <c:otherwise>
				            <div class="alert" style="color: #00FFFF;">
							    科目情報を選択または学生情報を入力して検索ボタンをクリックしてください。
							</div>
				        </c:otherwise>
				    </c:choose>
				</c:otherwise>

            </c:choose>
        </section>
    </c:param>
</c:import>


