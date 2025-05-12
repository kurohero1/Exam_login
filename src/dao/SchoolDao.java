package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class SchoolDao extends Dao {
    /**
     * getメソッド 学校コードを指定して学校インスタンスを１件取得する
     *
     * @param cd:String
     *            学校コード
     * @return 学校クラスのインスタンス 存在しない場合はnull
     * @throws Exception
     */
    public School get(String cd) throws Exception {
        School school = new School();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("select * from school where cd = ?");
            statement.setString(1, cd);
            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
            } else {
                school = null;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }
        return school;
    }

    /**
     * 全ての学校情報を取得
     *
     * @return List<School> 学校リスト
     * @throws Exception
     */
    public List<School> getAllSchools() throws Exception {
        List<School> schools = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            // 全ての学校情報を取得するSQL
            statement = connection.prepareStatement("SELECT * FROM school");
            ResultSet rSet = statement.executeQuery();

            // 結果セットから学校情報をリストに追加
            while (rSet.next()) {
                School school = new School();
                school.setCd(rSet.getString("cd"));
                school.setName(rSet.getString("name"));
                schools.add(school);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException sqle) {
                    throw sqle;
                }
            }
        }

        return schools;
    }
}
