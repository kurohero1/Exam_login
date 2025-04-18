package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("Login.action");
            return;
        }


        String studentNo = req.getParameter("no");

        if (studentNo != null && !studentNo.isEmpty()) {
            try {
                StudentDao studentDao = new StudentDao();
                boolean isDeleted = studentDao.deleteStudent(studentNo);

                if (isDeleted) {
                    req.setAttribute("message", "学生情報が削除されました。");
                } else {
                    req.setAttribute("message", "学生情報の削除に失敗しました。");
                }
            } catch (Exception e) {
                req.setAttribute("message", "エラーが発生しました: " + e.getMessage());
            }
        } else {
            req.setAttribute("message", "学生番号が指定されていません。");
        }

        req.getRequestDispatcher("student_delete.jsp").forward(req, res);
    }
}
