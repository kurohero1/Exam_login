package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String studentNo = req.getParameter("no");
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.get(studentNo);

        if (student != null) {
            int currentYear = java.time.LocalDate.now().getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = currentYear - 10; i <= currentYear; i++) {
                entYearSet.add(i);
            }

            ClassNumDao cNumDao = new ClassNumDao();
            List<String> classNumSet = cNumDao.filter(teacher.getSchool());

            req.setAttribute("student", student);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("class_num_set", classNumSet);
            req.getRequestDispatcher("student_update.jsp").forward(req, res);

        } else {
            req.setAttribute("error", "学生情報はもらえません");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
