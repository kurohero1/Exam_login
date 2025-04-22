package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{

	private String baseSql = "SELECT * FROM subject WHERE no=?";

	private List<TestListStudent> postFilter(ResultSet rSet, Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				TestListStudent testListStudent = new TestListStudent();
				testListStudent.setSubjectName(rSet.getString("subjectName"));
				testListStudent.setSubjectCd(rSet.getString("subjectCd"));
				testListStudent.setNum(rSet.getInt("num"));
				testListStudent.setPoint(rSet.getInt("point"));
				list.add(testListStudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<TestListStudent> filter(Student student) throws Exception {

        List<TestListStudent> list = new ArrayList<>();

        Connection connection = getConnection();

        PreparedStatement statement = null;

        ResultSet rSet = null;

        try {

            statement = connection.prepareStatement(baseSql + " ORDER BY cd ASC");

            statement.setString(1, student.getNo());

            rSet = statement.executeQuery();

            list = postFilter(rSet, student);

        } finally {

            if (rSet != null) try { rSet.close(); } catch (SQLException e) { throw e; }

            if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }

            if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }

        }

        return list;

    }


}