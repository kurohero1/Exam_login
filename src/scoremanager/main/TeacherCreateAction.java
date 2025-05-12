package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import tool.Action;

public class TeacherCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            // もし未登録の場合は、登録画面に戻す
            res.sendRedirect("login.jsp");
            return;
        }

        // いまの先生の学校メッセージをゲット
        School school = teacher.getSchool();
        req.setAttribute("school", school);

        // いまの先生のメッセージ
        req.setAttribute("teacher", teacher);

        // エラーメッセージ
        String errorMessage = (String) req.getAttribute("error");
        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
        }


        req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
    }
}


