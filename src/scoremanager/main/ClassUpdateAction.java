package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        String classNum = req.getParameter("no");
        ClassNumDao classNumDao = new ClassNumDao();
        ClassNum classNums = classNumDao.get(classNum, teacher.getSchool());

        if (classNums != null) {
            ClassNumDao cNumDao = new ClassNumDao();
            List<String> classNumSet = cNumDao.filter(teacher.getSchool());
            School schoolCd = teacher.getSchool();
            req.setAttribute("classNum", classNums);
            req.setAttribute("schoolCd", schoolCd);
            req.setAttribute("classNumSet", classNumSet);
            req.getRequestDispatcher("class_update.jsp").forward(req, res);

        } else {
            req.setAttribute("error", "クラス情報はもらえません");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
