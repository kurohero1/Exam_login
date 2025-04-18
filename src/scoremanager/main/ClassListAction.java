package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ClassNumDao のインスタンスを作成し、クラスデータ、教師所属する学校を取得
        ClassNumDao cNumDao = new ClassNumDao();
        School schoolCd = teacher.getSchool();

        // クラス番号のリストを取得
        List<String> classNumStrings = cNumDao.filter(schoolCd);

        // 新しい List<ClassNum> を作成し、変換後の結果を格納
        List<ClassNum> classNumList = new ArrayList<>();

        // 各クラス番号を ClassNum オブジェクトに変換し、学校情報を設定
        for (String classNum : classNumStrings) {
            ClassNum classNumO = new ClassNum();
            classNumO.setClass_num(classNum);

            // セッションから学校情報を取得
            classNumO.setSchool(schoolCd);
            classNumList.add(classNumO);
        }

        req.setAttribute("classNumList", classNumList);
        req.setAttribute("schoolCd", schoolCd);
        req.getRequestDispatcher("class_list.jsp").forward(req, res);
    }
}
