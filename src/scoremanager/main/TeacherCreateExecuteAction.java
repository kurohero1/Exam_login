package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import dao.TeacherDao;
import tool.Action;

public class TeacherCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // セッションに教師情報がない場合、ログイン画面にリダイレクト
        if (teacher == null) {
            res.sendRedirect("login.jsp");
            return;
        }

        // フォームから送信されたパラメータを取得
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String schoolCd = req.getParameter("schoolCd");

        // 必須フィールドの検証
        if (id == null || id.trim().isEmpty() || password == null || password.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "すべてのフィールドは必須です。");
            req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
            return;
        }

        // SchoolDao で学校情報を取得
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);
        if (school == null) {
            req.setAttribute("error", "指定された学校が存在しません。");
            req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
            return;
        }

        // 新しい教師オブジェクトを作成
        Teacher newTeacher = new Teacher();
        newTeacher.setId(id);
        newTeacher.setPassword(password);
        newTeacher.setName(name);
        newTeacher.setSchool(school);  // 学校情報を設定

        // TeacherDao を使って教師情報をデータベースに登録
        TeacherDao teacherDao = new TeacherDao();
        int insertResult = teacherDao.insertTeacher(newTeacher);

        // 結果に応じて処理を分岐
        if (insertResult > 0) {
            req.setAttribute("message", "教員の登録が完了しました。");
            req.getRequestDispatcher("teacher_create_success.jsp").forward(req, res);
        } else {
            req.setAttribute("duplicateError", "教員番号がすでに存在します。");
            req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
        }
    }
}


