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

public class StudentCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        int line = 0;
        try {
            String entYearStr = "";
            String classNum = "";
            int entYear = 0;
            LocalDate todaysDate = LocalDate.now();
            int year = todaysDate.getYear();
            ClassNumDao cNumDao = new ClassNumDao();

            entYearStr = req.getParameter("ent_year");
            classNum = req.getParameter("class_num");

            if (entYearStr != null && !entYearStr.equals("0")) {
                entYear = Integer.parseInt(entYearStr);
            }

            List<Integer> entYearSet = new ArrayList<>();
            for (int i = year - 10; i < year + 1; i++) {
                entYearSet.add(i);
            }

            List<String> classNumSet = cNumDao.filter(teacher.getSchool());

            String studentNo = req.getParameter("no");
            String studentName = req.getParameter("name");

            Student s = new Student();
            StudentDao dao = new StudentDao();

            s.setEntYear(entYear);
            s.setNo(studentNo);
            s.setName(studentName);
            s.setClassNum(classNum);

            req.setAttribute("class_num_set", classNumSet);
            req.setAttribute("ent_year_set", entYearSet);


            line = dao.insertStudent(s);
        } catch (Exception e) {

            e.printStackTrace();
        }


        req.setAttribute("line", line);
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }
}

