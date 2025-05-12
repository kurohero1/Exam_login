package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("Login.action");
            return;
        }


        String teacherId = req.getParameter("id");

        if (teacherId != null && !teacherId.isEmpty()) {
            try {
            	TeacherDao teacherDao = new TeacherDao();
                boolean isDeleted = teacherDao.deleteTeacher(teacherId);

                if (isDeleted) {
                    req.setAttribute("message", "教員情報が削除されました。");
                } else {
                    req.setAttribute("message", "教員情報の削除に失敗しました。");
                }
            } catch (Exception e) {
                req.setAttribute("message", "エラーが発生しました: " + e.getMessage());
            }
        } else {
            req.setAttribute("message", "クラスが指定されていません。");
        }
        req.getRequestDispatcher("teacher_delete.jsp").forward(req, res);
    }
}