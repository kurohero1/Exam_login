package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションから現在のユーザー（教師）を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 教師がログインしていない場合
        if (teacher == null) {
            // エラーメッセージをリクエストにセット
        	request.setAttribute("error", "ログインしてください。");
            // ログイン画面にフォワード
        	request.getRequestDispatcher("../login.jsp").forward(request, response);
            return;
        }

        // ログイン中の教師が所属する学校情報を取得
        School school = teacher.getSchool();

        // SubjectDaoを使って、学校に関連する科目情報を取得
        SubjectDao subjectDao = new SubjectDao();

        // filterメソッドを使用して、指定された学校に紐づくすべての科目を取得
        List<Subject> subjects = subjectDao.filter(school);

        // 取得した科目リストをリクエストにセット
        request.setAttribute("subjects", subjects);

        // JSPページ「subject_list.jsp」に転送
        request.getRequestDispatcher("subject_list.jsp").forward(request, response);
    }
}
