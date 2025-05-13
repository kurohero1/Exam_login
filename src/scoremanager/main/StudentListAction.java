package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.Action;

public class StudentListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションから教師情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 初期化
        String entYearStr = "";
        String classNum = "";
        String isAttendStr = "";
        int entYear = 0;
        boolean isAttend = false;
        List<Student> students = null;

        // 現在の年を取得
        LocalDate todaysDate = LocalDate.now();
        int year = todaysDate.getYear();

        // DAO の初期化
        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();

        // エラーを格納するマップ
        Map<String, String> errors = new HashMap<>();

        // リクエストパラメータの取得
        entYearStr = req.getParameter("f1");
        classNum = req.getParameter("f2");
        isAttendStr = req.getParameter("f3");

        // 入学年度の変換
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                entYear = Integer.parseInt(entYearStr);
            } catch (NumberFormatException e) {
                errors.put("f1", "入学年度は数字で入力してください");
            }
        }

        // 「在学中」のチェック状態を確認
        isAttend = "t".equals(isAttendStr);

        // 入学年度の選択肢リストを作成（現在年から過去10年分）
        List<Integer> entYearSet = new ArrayList<>();
        for (int i = year - 10; i <= year; i++) {
            entYearSet.add(i);
        }

        // クラス番号の選択肢を取得（教師の所属校に基づく）
        List<String> list = cNumDao.filter(teacher.getSchool());

        // 条件に応じて学生リストを取得
        if (entYear != 0 && !classNum.equals("0")) {
            // 入学年度・クラス・在学中で絞り込み
            students = sDao.filter(teacher.getSchool(), entYear, classNum, isAttend);
        } else if (entYear != 0 && classNum.equals("0")) {
            // 入学年度・在学中で絞り込み
            students = sDao.filter(teacher.getSchool(), entYear, isAttend);
        } else if ((entYear == 0 && classNum == null) || (entYear == 0 && classNum.equals("0"))) {
            // 在学中のみで絞り込み
            students = sDao.filter(teacher.getSchool(), isAttend);
        } else {
            // 入学年度なしでクラス指定された場合はエラー
            errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
            students = sDao.filter(teacher.getSchool(), isAttend);
        }

        // エラーを JSP に渡す
        req.setAttribute("errors", errors);

        // フォームの初期値を設定
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        if (isAttend) {
            req.setAttribute("f3", isAttendStr); // チェックボックスの状態を保持
        }

        // 学生リストを JSP に渡す
        req.setAttribute("students", students);

        // セレクトボックス用データを JSP に渡す
        req.setAttribute("class_num_set", list);
        req.setAttribute("ent_year_set", entYearSet);

        // 表示先 JSP にフォワード
        req.getRequestDispatcher("student_list.jsp").forward(req, res);
    }
}

