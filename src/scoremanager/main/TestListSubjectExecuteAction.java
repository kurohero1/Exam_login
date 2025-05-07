package scoremanager.main;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher)session.getAttribute("user");
            School school = teacher.getSchool();

            // パラメータの取得
            String entYearStr = req.getParameter("f1");
            String classNum = req.getParameter("f2");
            String subjectCd = req.getParameter("f3");

            // パラメータの検証
            if (entYearStr == null || entYearStr.equals("0")) {
                req.setAttribute("error", "入学年度を指定してください");
                req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
                return;
            }
            if (classNum == null || classNum.equals("0")) {
                req.setAttribute("error", "クラスを指定してください");
                req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
                return;
            }
            if (subjectCd == null || subjectCd.equals("0")) {
                req.setAttribute("error", "科目を選択してください");
                req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
                return;
            }

            // 科目情報の取得
            SubjectDao subjectDao = new SubjectDao();
            Subject subject = subjectDao.get(subjectCd, school);
            if (subject == null) {
                req.setAttribute("error", "科目が見つかりません");
                req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
                return;
            }

            // 成績情報の取得
            TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
            List<TestListSubject> testList = testListSubjectDao.filter(
                    Integer.parseInt(entYearStr), classNum, subject, school);

            // 空のリストを安全に処理する
            if (testList == null) {
                testList = Collections.emptyList();
            }

            // プロパティを設定する
            req.setAttribute("f1", entYearStr);
            req.setAttribute("f2", classNum);
            req.setAttribute("f3", subjectCd);
            req.setAttribute("testList", testList);
            req.setAttribute("subjectName", subject.getName());
            req.setAttribute("displayMode", "subject");


            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "エラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
