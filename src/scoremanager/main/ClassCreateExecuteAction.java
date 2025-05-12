package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.ClassNum;
import bean.School;
import dao.ClassNumDao;
import dao.SchoolDao;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
	    // フォームから送信された schoolCd と classNumSet を取得
	    String classNum = req.getParameter("classNumSet");  // クラス番号
	    String schoolCd = req.getParameter("schoolCd");     // 学校コード

	    // 学校コードを使って学校オブジェクトを取得
	    SchoolDao schoolDao = new SchoolDao();
	    School school = schoolDao.get(schoolCd);  // 指定された学校を取得

	    // ClassNumDao のインスタンスを作成し、学校の全クラス情報を取得
	    ClassNumDao classNumDao = new ClassNumDao();
	    List<String> classNumSet = classNumDao.filter(school); // 学校に基づいてクラス番号をフィルタリング

	    // 新しいクラスオブジェクトを作成
	    ClassNum classToSave = new ClassNum();
	    classToSave.setClass_num(classNum);  // クラス番号を設定
	    classToSave.setSchool(school);       // 学校を設定

	    // クラス番号がすでに存在するか確認
	    if (classNumDao.exists(classNum, schoolCd)) {
	        // クラス番号が重複している場合、エラーメッセージを返す
	        req.setAttribute("schoolCd", schoolCd);               // 学校コードをリクエストに設定
	        req.setAttribute("classNumSet", classNumSet);         // 既存のクラス番号セットをリクエストに設定
	        req.setAttribute("duplicateError", "クラス番号が重複しています");  // 重複エラーのメッセージ

	        // エラーページに遷移
	        req.getRequestDispatcher("class_create.jsp").forward(req, res);
	        return;
	    }

	    // 新しいクラスを挿入
	    boolean isInserted = classNumDao.save(classToSave);

	    if (isInserted) {
	        // 登録成功メッセージを設定して、成功ページに遷移
	        req.setAttribute("message", "クラス登録が完了しました。");
	        req.getRequestDispatcher("class_create_success.jsp").forward(req, res);
	    } else {
	        // 登録中にエラーが発生した場合、エラーメッセージを設定して再度登録ページに遷移
	        req.setAttribute("error", "登録中にエラーが発生しました。");
	        req.getRequestDispatcher("class_create.jsp").forward(req, res);
	    }
	}


}
