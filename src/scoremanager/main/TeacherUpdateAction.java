package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String teacherId = req.getParameter("no");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        TeacherDao teacherDao = new TeacherDao();
        Teacher teachers = teacherDao.get(teacherId);

        if (teachers != null) {
           TeacherDao teacherrDao = new TeacherDao();
            List<String> teacherSet = teacherrDao.filter(teacher.getSchool());
            School schoolCd = teacher.getSchool();
            req.setAttribute("teacherId", teacherId);
            req.setAttribute("schoolCd", schoolCd);
            req.setAttribute("teacherSet", teacherSet);
            req.setAttribute("password", password);
            req.setAttribute("name", name);
            req.setAttribute("teacher", teacher);
            req.getRequestDispatcher("teacher_update.jsp").forward(req, res);

        } else {
            req.setAttribute("error", "クラス情報はもらえません");
            req.getRequestDispatcher("/error.jsp").forward(req, res);
        }
    }
}
