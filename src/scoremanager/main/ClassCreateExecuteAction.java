package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String classNum = req.getParameter("classNumSet");
        String schoolCd = req.getParameter("schoolCd");

        ClassNumDao cNumDao = new ClassNumDao();
        List<String> classNumSet = cNumDao.filter(teacher.getSchool());

        ClassNum c  = new ClassNum();
        c.setClass_num(classNum);
        c.setSchool(teacher.getSchool());

        ClassNumDao classNumDao = new ClassNumDao();

        if (classNumDao.exists(classNum)) {
            req.setAttribute("schoolCd", schoolCd);
            req.setAttribute("class_num_set", classNumSet);
            req.setAttribute("duplicateError", "クラス番号が重複しています");

            ClassNum cc = new ClassNum();
            cc.setSchool(teacher.getSchool());
            req.setAttribute("classNum", cc);

            req.getRequestDispatcher("class_create.jsp").forward(req, res);
            return;
        }

        boolean isInserted = classNumDao.save(c);

        if (isInserted) {
            req.setAttribute("message", "成功");
            req.getRequestDispatcher("class_create_success.jsp").forward(req, res);
        } else {
            req.setAttribute("error", "登録中にエラーが発生しました。");

            ClassNum cc = new ClassNum();
            cc.setSchool(teacher.getSchool());
            req.setAttribute("classNum", cc);

            req.getRequestDispatcher("class_create.jsp").forward(req, res);
        }
    }
}
