package scoremanager.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        // プルダウンデータの準備（毎回設定する）
        SubjectDao subjectDao = new SubjectDao();
        ClassNumDao classNumDao = new ClassNumDao();

        List<Subject> subjectList = subjectDao.filter(school);
        List<String> classNumList = classNumDao.filter(school);
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = java.time.LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            entYearSet.add(i);
        }

        req.setAttribute("subjectList", subjectList);
        req.setAttribute("class_num_set", classNumList);
        req.setAttribute("ent_year_set", entYearSet);

        // パラメータのバリデーション
        if (entYearStr == null || entYearStr.equals("0")) {
            req.setAttribute("error", "入学年度を指定してください");
            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
            return;
        }
        if (classNum == null || classNum.equals("0")) {
            req.setAttribute("error", "クラスを指定してください");
            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
            return;
        }
        if (subjectCd == null || subjectCd.equals("0")) {
            req.setAttribute("error", "科目を選択してください");
            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
            return;
        }

        // 科目の取得と存在チェック
        Subject subject = subjectDao.get(subjectCd, school);
        if (subject == null) {
            req.setAttribute("error", "科目が見つかりません");
            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
            return;
        }

        // 成績情報の取得
        TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
        List<TestListSubject> testList = testListSubjectDao.filter(
                Integer.parseInt(entYearStr), classNum, subject, school);

        // 画面に渡す情報を設定
        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("testList", testList != null ? testList : Collections.emptyList());
        req.setAttribute("subjectName", subject.getName());
        req.setAttribute("displayMode", "subject");

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
