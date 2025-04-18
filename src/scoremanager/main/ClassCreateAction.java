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

public class ClassCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        int line = 0;
        try {
            String classNum = "";

            ClassNumDao cNumDao = new ClassNumDao();

            classNum = req.getParameter("class_num");

            List<String> classNumSet = cNumDao.filter(teacher.getSchool());

            ClassNum s = new ClassNum();
            ClassNumDao dao = new ClassNumDao();
            School schoolCd = teacher.getSchool();

            s.setSchool(schoolCd);
            s.setClass_num(classNum);

            req.setAttribute("classNumSet", classNumSet);
            req.setAttribute("schoolCd", schoolCd);

            ClassNum cc = new ClassNum();
            cc.setSchool(teacher.getSchool());
            req.setAttribute("classNum", cc);

            line = dao.insertClass(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.setAttribute("line", line);
        req.getRequestDispatcher("class_create.jsp").forward(req, res);
    }
}

