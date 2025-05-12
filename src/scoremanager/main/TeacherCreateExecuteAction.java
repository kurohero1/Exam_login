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
        System.out.println("Entering TeacherCreateExecuteAction");

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        if (teacher == null) {
            System.out.println("No teacher found in session, redirecting to login");
            res.sendRedirect("login.jsp");
            return;
        }
        System.out.println("Teacher found in session: " + teacher.getId());

        // 必要なもの
        String id = req.getParameter("id");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String schoolCd = req.getParameter("schoolCd");

        System.out.println("Received parameters: ");
        System.out.println("id = " + id);
        System.out.println("password = " + password);
        System.out.println("name = " + name);
        System.out.println("schoolCd = " + schoolCd);

        // 検証必要なこと
        if (id == null || id.trim().isEmpty() || password == null || password.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            System.out.println("Validation failed: Some required fields are missing.");
            req.setAttribute("error", "すべてのフィールドは必須です。");
            req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
            return;
        }

        //  Schoolをゲット
        SchoolDao schoolDao = new SchoolDao();
        School school = schoolDao.get(schoolCd);
        if (school == null) {
            System.out.println("School with code " + schoolCd + " not found.");
            req.setAttribute("error", "指定された学校が存在しません。");
            req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
            return;
        }

        System.out.println("School found: " + school.getCd() + " - " + school.getName());

        // 新しいTeacher
        Teacher newTeacher = new Teacher();
        newTeacher.setId(id);
        newTeacher.setPassword(password);
        newTeacher.setName(name);
        newTeacher.setSchool(school); // 设置 School 对象

        // TeacherDaoを使ってインサート
        TeacherDao teacherDao = new TeacherDao();
        int insertResult = teacherDao.insertTeacher(newTeacher);

        System.out.println("insertTeacher result: " + insertResult);

        // 結果を処理する
        if (insertResult > 0) {
            System.out.println("Teacher registered successfully.");
            req.setAttribute("message", "教員の登録が完了しました。");
            req.getRequestDispatcher("teacher_create_success.jsp").forward(req, res);
        } else {
            System.out.println("Teacher registration failed: Duplicate teacher ID.");
            req.setAttribute("duplicateError", "教員番号がすでに存在します。");
            req.getRequestDispatcher("teacher_create.jsp").forward(req, res);
        }
    }
}


