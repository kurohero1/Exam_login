package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDao;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	    HttpSession session = req.getSession();
	    Teacher teacher = (Teacher) session.getAttribute("user");

//	    String schoolCd    = req.getParameter("f1");
	    String oldClassNum = req.getParameter("oldClassNum");
	    String newClassNum = req.getParameter("newClassNum");

	    ClassNumDao classNumDao = new ClassNumDao();
	  //古いクラスからゲット
	    ClassNum existing = classNumDao.get(oldClassNum, teacher.getSchool());

	    if (existing != null) {
	        // 新しいクラスをbeanに設置して
	        existing.setClass_num(newClassNum);

//	        School school = new School();
//	        school.setCd(schoolCd);
//	        existing.setSchool(school);　　　　　schoolの更新、今は要らない

	        // 3. 新しい update
	        boolean updated = classNumDao.update(existing, oldClassNum);

	        if (updated) {
	            req.setAttribute("line", 1);
	            req.setAttribute("classNum", existing);
	            req.setAttribute("classNumSet", classNumDao.filter(teacher.getSchool()));
	            req.getRequestDispatcher("class_update_success.jsp").forward(req, res);
	        } else {
	            // 失敗すれば
	            req.setAttribute("classNum", existing);
	            req.setAttribute("classNumSet", classNumDao.filter(teacher.getSchool()));
	            req.setAttribute("error", "クラス情報の更新に失敗しました。");
	            req.getRequestDispatcher("class_update.jsp").forward(req, res);
	        }
	    } else {
	        req.setAttribute("error", "指定されたクラスは存在しません。");
	        req.getRequestDispatcher("class_update.jsp").forward(req, res);
	    }
	}
}