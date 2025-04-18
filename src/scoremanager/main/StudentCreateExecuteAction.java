package scoremanager.main;

import java.time.LocalDate;
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

public class StudentCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String studentNo = req.getParameter("no");
        String studentName = req.getParameter("name");
        String classNum = req.getParameter("f2");
        String entYearStr = req.getParameter("f1");
        int entYear = 0;
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();
        ClassNumDao cNumDao = new ClassNumDao();

        if (entYearStr != null && !entYearStr.equals("0")) {
            entYear = Integer.parseInt(entYearStr);
        }

        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }

        List<String> classNumSet = cNumDao.filter(teacher.getSchool());

        Student student = new Student();
        student.setNo(studentNo);
        student.setName(studentName);
        student.setClassNum(classNum);
        student.setEntYear(entYear);
        student.setIsAttend(true);
        student.setSchool(teacher.getSchool());
        StudentDao studentDao = new StudentDao();

        if (studentDao.exists(studentNo)) {
        	req.setAttribute("class_num_set", classNumSet);
            req.setAttribute("ent_year_set", entYearSet);
            req.setAttribute("duplicateError", "学生番号が重複しています");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
            return;
        }

        boolean isInserted = studentDao.save(student);

        if (isInserted) {
            req.setAttribute("message", "成功");
            req.getRequestDispatcher("student_create_success.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "登録中にエラーが発生しました。");
            req.getRequestDispatcher("student_create.jsp").forward(req, res);
        }
    }
}

