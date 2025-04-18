package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDao extends Dao {

	public ClassNum get(String class_num, School school) throws Exception {
		ClassNum classNum = new ClassNum();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select * from class_num where class_num = ? and school_cd = ?");
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());

			ResultSet rSet = statement.executeQuery();
			SchoolDao sDao = new SchoolDao();
			if (rSet.next()) {
				classNum.setClass_num(rSet.getString("class_num"));
				classNum.setSchool(sDao.get(rSet.getString("school_cd")));
			} else {
				classNum = null;
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
		return classNum;
	}

	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement("select class_num from class_num where school_cd = ? order by class_num");
			statement.setString(1, school.getCd());

			ResultSet rSet = statement.executeQuery();
			while (rSet.next()) {
				list.add(rSet.getString("class_num"));
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


	public ClassNum get(String school) throws Exception {
		ClassNum classNum = new ClassNum();
		Connection connection = getConnection();
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement("select * from Class_Num where school_cd=?");
			statement.setString(1, school);
			ResultSet rSet = statement.executeQuery();

			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				classNum.setSchool(schoolDao.get(rSet.getString("school_cd")));
				classNum.setClass_num(rSet.getString("class_num"));
			} else {
				classNum = null;
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
		return classNum;
	}

	public boolean deleteClass(String classNum) throws Exception {
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int rowsAffected = 0;

	    try {
	        String sql = "DELETE FROM CLASS_NUM WHERE CLASS_NUM = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, classNum);

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


	public boolean update(ClassNum bean, String oldClassNum) throws Exception {
	    String sql = "UPDATE class_num SET class_num = ? WHERE class_num = ? AND school_cd = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, bean.getClass_num());
	        stmt.setString(2, oldClassNum);
	        stmt.setString(3, bean.getSchool().getCd());

	        return stmt.executeUpdate() > 0;
	    }
	}



	public int insertClass(ClassNum classNum) throws Exception {
	    Connection con = getConnection();

	    PreparedStatement st = con.prepareStatement("SELECT class_num FROM class_num WHERE class_num = ? AND school_cd = ?");
	    st.setString(1, classNum.getSchool().getCd());
	    st.setString(2, classNum.getClass_num());
	    ResultSet rs = st.executeQuery();

	    if (rs.next()) {
	        return 0;
	    }

	    st = con.prepareStatement("INSERT INTO class_num (school_cd, class_num) VALUES (?, ?)");
	    st.setString(1, classNum.getSchool().getCd());
	    st.setString(2, classNum.getClass_num());

	    int line = st.executeUpdate();

	    st.close();
	    con.close();
	    return line;
	}

	public boolean save(ClassNum classNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement st = null;
		int count = 0;

		try {
			ClassNum old = get(classNum.getClass_num());
			if (old == null) {
				st = connection.prepareStatement("insert into class_num(school_cd, class_num) values(?, ?)");
			    st.setString(1, classNum.getSchool().getCd());
			    st.setString(2, classNum.getClass_num());
			} else {
				st = connection.prepareStatement("update student set class_num=? where school_cd=?");
				st.setString(2, classNum.getClass_num());
			}
			count = st.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			if (st != null) {
				try {
					st.close();

				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();

				}catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exists(String classNum) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			statement = connection.prepareStatement("select class_num from class_num where school_cd=?");
			statement.setString(1, classNum);
			resultSet = statement.executeQuery();
			return resultSet.next();
		} finally {
			if (resultSet != null) resultSet.close();
			if (statement != null) statement.close();
			if (connection != null) connection.close();
		}
	}

}