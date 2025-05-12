package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher currentTeacher = (Teacher) session.getAttribute("user");

        String teacherId = currentTeacher.getId();  // いまの先生の teacherId
        String password = req.getParameter("password");
        String name = req.getParameter("name");

        TeacherDao teacherDao = new TeacherDao();
        Teacher teacher = teacherDao.get(teacherId);

        if (teacher != null && teacher.getSchool().getCd().equals(currentTeacher.getSchool().getCd())) {
            teacher.setPassword(password);
            teacher.setName(name);

            boolean updated = teacherDao.update(teacher);

            if (updated) {
                req.setAttribute("line", 1);
                req.setAttribute("teacher", teacher);
                req.getRequestDispatcher("teacher_update_success.jsp").forward(req, res);
            } else {
                req.setAttribute("error", "更新失敗");
                req.getRequestDispatcher("teacher_update.jsp").forward(req, res);
            }
        } else {
            req.setAttribute("error", "教員メッセージがない");
            req.getRequestDispatcher("teacher_update.jsp").forward(req, res);
        }
    }
}
