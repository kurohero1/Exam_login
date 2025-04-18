package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        if (teacher == null) {
            res.sendRedirect("Login.action");
            return;
        }


        String classNum = req.getParameter("class_num");

        if (classNum != null && !classNum.isEmpty()) {
            try {
            	ClassNumDao cNumDao = new ClassNumDao();
                boolean isDeleted = cNumDao.deleteClass(classNum);

                if (isDeleted) {
                    req.setAttribute("message", "クラス情報が削除されました。");
                } else {
                    req.setAttribute("message", "クラス情報の削除に失敗しました。");
                }
            } catch (Exception e) {
                req.setAttribute("message", "エラーが発生しました: " + e.getMessage());
            }
        } else {
            req.setAttribute("message", "クラスが指定されていません。");
        }

        req.getRequestDispatcher("class_delete.jsp").forward(req, res);
    }
}