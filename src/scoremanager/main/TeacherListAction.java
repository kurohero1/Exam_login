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

        // TeacherDao のインスタンスを作成
        TeacherDao teacherDao = new TeacherDao();

        // クラス番号のリストを取得
        List<String> teacherStrings = teacherDao.filter(teacher.getSchool());

        // 新しい List<Teacher> を作成
        List<Teacher> teacherList = new ArrayList<>();

        // 各教師IDを使って教師の詳細情報を取得
        for (String teacherId : teacherStrings) {
            // 教師オブジェクトを取得
            Teacher teacherO = teacherDao.get(teacherId);  // 获取完整的教师数据

            if (teacherO != null) {
                // 通过教师的 school_cd 获取对应的学校信息
                SchoolDao schoolDao = new SchoolDao();
                School school = schoolDao.get(teacherO.getSchool().getCd());  // 获取学校信息
                teacherO.setSchool(school);  // 设置正确的学校信息

                // 将教师对象添加到列表中
                teacherList.add(teacherO);
            }
        }

        // 取得した教師のリストをJSPに渡す
        req.setAttribute("teacherList", teacherList);
        req.setAttribute("schoolCd", teacher.getSchool());
        req.getRequestDispatcher("teacher_list.jsp").forward(req, res);
    }
}

