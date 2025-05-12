package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {
	/**
	 * getメソッド 教員IDを指定して教員インスタンスを1件取得する
	 *
	 * @param id:String
	 *            教員ID
	 * @return 教員クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Teacher get(String id) throws Exception {
		// 教員インスタンスを初期化
		Teacher teacher = new Teacher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from teacher where id=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				teacher.setId(resultSet.getString("id"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setName(resultSet.getString("name"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(resultSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				teacher = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return teacher;
	}

	/**
	 * loginメソッド 教員IDとパスワードで認証する
	 *
	 * @param id:String
	 *            教員ID
	 * @param password:String
	 *            パスワード
	 * @return 認証成功:教員クラスのインスタンス, 認証失敗:null
	 * @throws Exception
	 */
	public Teacher login(String id, String password) throws Exception {
		// 教員クラスのインスタンスを取得
		Teacher teacher = get(id);
		// 教員がnullまたはパスワードが一致しない場合
		if (teacher == null || !teacher.getPassword().equals(password)) {
			return null;
		}
		return teacher;
	}

	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select id from teacher where school_cd = ? order by id");
			statement.setString(1, school.getCd());

			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				list.add(rSet.getString("id"));
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
		return list;
	}

	public int insertTeacher(Teacher teacher) throws Exception {
	    // 入力が空でないかを確認し、NullPointerException やデータベースエラーを防ぎます。
	    if (teacher == null || teacher.getId() == null || teacher.getSchool() == null || teacher.getSchool().getCd() == null) {
	        throw new IllegalArgumentException("Teacher 或其必要属性为 null");
	    }

	    String schoolCd = teacher.getSchool().getCd();
	    String id = teacher.getId();
	    String password = teacher.getPassword();
	    String name = teacher.getName();

	    // try-with-resources を使用して、接続およびステートメントオブジェクトを自動的にクローズします。
	    try (Connection con = getConnection()) {

	        // まず、既に存在しているかどうかを確認します。
	        String checkSql = "SELECT id FROM teacher WHERE id = ? AND school_cd = ?";
	        try (PreparedStatement checkStmt = con.prepareStatement(checkSql)) {
	            checkStmt.setString(1, id);
	            checkStmt.setString(2, schoolCd);
	            ResultSet rs = checkStmt.executeQuery();

	            if (rs.next()) {
	                // 既に存在している場合は、挿入されていないことを示すために 0 を返します。
	                return 0;
	            }
	        }

	        // 新しいレコードを挿入します。
	        String insertSql = "INSERT INTO teacher (school_cd, id, password, name) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement insertStmt = con.prepareStatement(insertSql)) {
	            insertStmt.setString(1, schoolCd);
	            insertStmt.setString(2, id);
	            insertStmt.setString(3, password);
	            insertStmt.setString(4, name);

	            return insertStmt.executeUpdate(); 
	        }
	    }
	}

	public boolean update(Teacher teacher) throws Exception {
	    boolean result = false;

	    String sql = "UPDATE teacher SET password = ?, name = ? WHERE id = ? AND school_cd = ?";

	    try (Connection con = getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, teacher.getPassword());
	        stmt.setString(2, teacher.getName());
	        stmt.setString(3, teacher.getId());
	        stmt.setString(4, teacher.getSchool().getCd());

	        int rows = stmt.executeUpdate();
	        result = (rows > 0);
	    }

	    return result;
	}

	public boolean deleteTeacher(String id) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int rowsAffected = 0;

	    try {
	        String sql = "DELETE FROM teacher WHERE id = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, id);

	        rowsAffected = statement.executeUpdate();
	    } catch (SQLException e) {
	        throw new Exception("クラス情報削除エラー: " + e.getMessage());
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

	    return rowsAffected > 0;
	}



}
