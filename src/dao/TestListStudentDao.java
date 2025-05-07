package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {

    // 使用していないSql
    private String baseSql = "SELECT * FROM TestListStudent WHERE subjectCd=?";

    public List<TestListStudent> filter(Student student) throws Exception {
        //使用しているSql
        String baseSql = "SELECT t.subject_cd, s.name AS subject_name, t.no AS num, t.point " +
                    "FROM test t " +
                    "JOIN subject s ON t.subject_cd = s.cd AND t.school_cd = s.school_cd " +
                    "WHERE t.student_no = ? AND t.school_cd = ? " +
                    "ORDER BY t.subject_cd, t.no";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(baseSql)) {

            ps.setString(1, student.getNo());
            ps.setString(2, student.getSchool().getCd());

            try (ResultSet rs = ps.executeQuery()) {
                return postFilter(rs, student); // postFilterを使って結果を処理する
            }
        }
    }

    //
    private List<TestListStudent> postFilter(ResultSet rSet, Student student) throws Exception {
        List<TestListStudent> list = new ArrayList<>();
        try {
            while (rSet.next()) {
                TestListStudent test = new TestListStudent();
                test.setSubjectCd(rSet.getString("subject_cd"));
                test.setSubjectName(rSet.getString("subject_name"));
                test.setNum(rSet.getInt("num"));
                test.setPoint(rSet.getInt("point"));
                list.add(test);
            }
        } catch (SQLException e) {
            throw new Exception("成績データの処理中にエラーが発生しました", e);
        }
        return list;
    }
}
