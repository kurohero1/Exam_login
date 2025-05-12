package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class TeacherListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ClassNumDao のインスタンスを作成し、クラスデータ、教師所属する学校を取得
        TeacherDao teacherDao = new TeacherDao();
        School schoolCd = teacher.getSchool();

        // クラス番号のリストを取得
        List<String> teacherStrings = teacherDao.filter(schoolCd);

        // 新しい List<ClassNum> を作成し、変換後の結果を格納
        List<Teacher> teacherList = new ArrayList<>();

        // 各クラス番号を ClassNum オブジェクトに変換し、学校情報を設定
        for (String teachers : teacherStrings) {
            Teacher teacherO = new Teacher();
            teacherO.setId(teachers);

            // セッションから学校情報を取得
            teacherO.setSchool(schoolCd);
            teacherList.add(teacherO);
        }

        req.setAttribute("teacherList", teacherList);
        req.setAttribute("schoolCd", schoolCd);
        req.getRequestDispatcher("teacher_list.jsp").forward(req, res);
    }
}
