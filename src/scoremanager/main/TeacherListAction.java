package scoremanager.main;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.SchoolDao;
import dao.TeacherDao;
import tool.Action;

public class TeacherListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // TeacherDaoのインスタンスを作成
        TeacherDao teacherDao = new TeacherDao();

        // 現在ログインしている教員に基づいて、その学校に所属する教員リストを取得
        List<String> teacherStrings = teacherDao.filter(teacher.getSchool());

        // 教員リストを格納する新しいListを作成
        List<Teacher> teacherList = new ArrayList<>();

        // 各教員IDに基づいて、教員情報を取得
        for (String teacherId : teacherStrings) {
            // 教員オブジェクトを取得（教員IDを使って詳細情報を取得）
            Teacher teacherO = teacherDao.get(teacherId);

            if (teacherO != null) {
                // 教員の学校コード（school_cd）を使って、学校情報を取得
                SchoolDao schoolDao = new SchoolDao();
                School school = schoolDao.get(teacherO.getSchool().getCd());  // 学校情報を取得
                teacherO.setSchool(school);  // 教員オブジェクトに学校情報を設定

                // 学校情報が設定された教員オブジェクトをリストに追加
                teacherList.add(teacherO);
            }
        }

        // 教員リストをJSPに渡す
        req.setAttribute("teacherList", teacherList);
        // 現在ログインしている教員の学校コードも渡す
        req.setAttribute("schoolCd", teacher.getSchool());
        // teacher_list.jspにフォワード
        req.getRequestDispatcher("teacher_list.jsp").forward(req, res);
    }
}


