package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class TestListExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // フィルター後のロジックを実行し、ページを更新する
        String action = req.getParameter("action");
        if ("search_subject".equals(action)) {
            // 科目検索の操作をトリガーし、TestListAction を実行する
            new TestListAction().execute(req, res);
        }
        else if ("search_student".equals(action)) {
            // 学生検索の操作をトリガーし、TestListAction を実行する
            new TestListAction().execute(req, res);
        }
    }
}
