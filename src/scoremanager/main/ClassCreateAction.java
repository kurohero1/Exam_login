package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SchoolDao;
import tool.Action;

public class ClassCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        int line = 0;
        try {
            // 現在ログインしている教師の学校情報を取得
            School schoolCd = teacher.getSchool();

            // 学校情報を全て取得する
            SchoolDao schoolDao = new SchoolDao();
            List<School> schoolList = schoolDao.getAllSchools(); // すべての学校情報を取得

            // 現在の学校のクラス番号リストを取得
            ClassNumDao cNumDao = new ClassNumDao();
            List<String> classNumSet = cNumDao.filter(schoolCd); // 現在の学校に関連するクラス番号を取得

            // リクエスト属性に学校リスト、クラス番号リスト、学校コードを設定
            req.setAttribute("schoolList", schoolList);  // 学校情報
            req.setAttribute("classNumSet", classNumSet);  // 現在の学校のクラス情報
            req.setAttribute("schoolCd", schoolCd);  // 現在の学校のコード

            // 新しいクラス番号のための ClassNum インスタンスを作成
            ClassNum cc = new ClassNum();
            cc.setSchool(schoolCd);
            req.setAttribute("classNum", cc);  // 新しいクラスインスタンスをリクエストに設定

            // フォームから送信されたクラス番号を取得
            String classNum = req.getParameter("class_num");

            // クラス番号が空でない場合、新しいクラスを登録
            if (classNum != null && !classNum.isEmpty()) {
                ClassNum s = new ClassNum();
                s.setSchool(schoolCd);
                s.setClass_num(classNum);

                // 新しいクラス情報をデータベースに挿入
                line = cNumDao.insertClass(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // クラス情報登録結果をリクエストに設定
        req.setAttribute("line", line);
        // 登録ページにフォワード
        req.getRequestDispatcher("class_create.jsp").forward(req, res);
    }
}


