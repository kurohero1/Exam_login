package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import tool.Action;

public class TeacherCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合、ログイン画面にリダイレクト
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // 現在の教師の学校情報を取得
        School school = teacher.getSchool();
        req.setAttribute("school", school);

        // 現在の教師情報をリクエストに設定
        req.setAttribute("teacher", teacher);

        // エラーメッセージがあれば設定
        String errorMessage = (String) req.getAttribute("error");
        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
        }

        // 学校リストを取得して、リクエストに設定
        SchoolDao schoolDao = new SchoolDao();
        List<School> schoolList = schoolDao.getAllSchools();  // 这里需要调用你从数据库获取学校列表的方法
        req.setAttribute("schoolList", schoolList);

        // 教員作成フォームへ遷移
        req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
    }
}

