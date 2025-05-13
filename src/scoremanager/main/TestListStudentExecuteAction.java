package scoremanager.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String studentNo = req.getParameter("f4");

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

        // 学生番号のバリデーション
        if (studentNo == null || studentNo.isEmpty()) {
            req.setAttribute("errors", Collections.singletonMap("f4", "学生番号を入力してください"));
            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
            return;
        }

        // 学生情報の取得と存在確認
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);
        if (student == null || !student.getSchool().getCd().equals(school.getCd())) {
            req.setAttribute("errors", Collections.singletonMap("f4", "該当する学生が見つかりません"));
            req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
            return;
        }

        // 成績情報の取得
        TestListStudentDao testDao = new TestListStudentDao();
        List<TestListStudent> testList = testDao.filter(student);

        // 画面に渡す情報を設定
        req.setAttribute("f4", studentNo);
        req.setAttribute("student", student);
        req.setAttribute("testList", testList != null ? testList : Collections.emptyList());
        req.setAttribute("displayMode", "student");

        req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
    }
}
