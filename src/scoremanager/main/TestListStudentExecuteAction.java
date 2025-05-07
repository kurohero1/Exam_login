package scoremanager.main;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        try {
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher)session.getAttribute("user");
            School school = teacher.getSchool();

            // 学生番号を取得する
            String studentNo = req.getParameter("f4");
            if (studentNo == null || studentNo.isEmpty()) {
                req.setAttribute("errors", Collections.singletonMap("f4", "学生番号を入力してください"));
                req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
                return;
            }

            // 学生メッセージを取得する
            StudentDao studentDao = new StudentDao();
            Student student = studentDao.get(studentNo);
            if (student == null || !student.getSchool().getCd().equals(school.getCd())) {
                req.setAttribute("errors", Collections.singletonMap("f4", "該当する学生が見つかりません"));
                req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
                return;
            }

            // 成績リストを取得する
            TestListStudentDao testDao = new TestListStudentDao();
            List<TestListStudent> testList = testDao.filter(student);

            // パラメータを取得する
            req.setAttribute("f4", studentNo);
            req.setAttribute("student", student);
            req.setAttribute("testList", testList != null ? testList : Collections.emptyList());
            req.setAttribute("displayMode", "student");

            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "エラーが発生しました：" + e.getMessage());
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
