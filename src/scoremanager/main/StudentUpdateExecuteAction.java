package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String studentNo = req.getParameter("no");
        String studentName = req.getParameter("name");
        String classNum = req.getParameter("f2");
        String entYearStr = req.getParameter("f1");
        boolean isAttend = "t".equals(req.getParameter("f3"));
        int entYear = Integer.parseInt(entYearStr);
        StudentDao studentDao = new StudentDao();
        Student student = new Student();
        student.setNo(studentNo);
        student.setName(studentName);
        student.setClassNum(classNum);
        student.setEntYear(entYear);
        student.setIsAttend(isAttend);
        student.setSchool(teacher.getSchool());

        boolean isUpdated = studentDao.save(student);

        if (isUpdated) {
            req.setAttribute("message", "学生情報更新が完了しました。");
            req.getRequestDispatcher("student_update_success.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "学生情報の更新に失敗しました。");
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
        }
    }
}
