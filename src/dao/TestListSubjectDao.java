package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {



    public List<TestListSubject> filter(int entYear, String classNum,
                                      Subject subject, School school) throws Exception {
        // 使用しているSql
        String baseSql = "SELECT st.ent_Year AS entYear, t.class_Num AS classNum, t.student_no AS studentNo, st.name AS studentName, " +
                    "t.no AS test_no, t.point " +
                    "FROM test t " +
                    "JOIN student st ON t.student_no = st.no " +
                    "WHERE t.school_cd = ? " +
                    "AND t.subject_cd = ? " +
                    (entYear != 0 ? "AND st.ent_Year = ? " : "") +
                    (classNum != null && !classNum.isEmpty() ? "AND t.class_Num = ? " : "") +
                    "ORDER BY t.student_no, t.no";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(baseSql)) {

            int paramIndex = 1;
            ps.setString(paramIndex++, school.getCd());
            ps.setString(paramIndex++, subject.getCd());
            if (entYear != 0) ps.setInt(paramIndex++, entYear);
            if (classNum != null && !classNum.isEmpty()) ps.setString(paramIndex, classNum);

            try (ResultSet rs = ps.executeQuery()) {
                return postFilter(rs, school, subject);
            }
        }
    }

    //
    private List<TestListSubject> postFilter(ResultSet rs, School school, Subject subject) throws Exception {
        Map<String, TestListSubject> studentMap = new LinkedHashMap<>();

        while (rs.next()) {
            String studentNo = rs.getString("studentNo");
            TestListSubject student = studentMap.computeIfAbsent(studentNo, k -> {
                TestListSubject s = new TestListSubject();
                try {
                    s.setEntYear(rs.getInt("entYear"));
                    s.setClassNum(rs.getString("classNum"));
                    s.setStudentNo(studentNo);
                    s.setStudentName(rs.getString("studentName"));
                } catch (SQLException e) {
                    throw new RuntimeException("学生データの設定中にエラーが発生しました", e);
                }
                return s;
            });

            //Null値
            int point = rs.getInt("point");
            if (!rs.wasNull()) {
                student.setPoint(rs.getInt("test_no"), point);
            }
        }

        return new ArrayList<>(studentMap.values());
    }
}
