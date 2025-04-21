package bean;

import java.io.Serializable;

public class TestListStudent implements Serializable {

	private String studentName;

	private String studentCd;

	private int num;

	private int point;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentCd() {
		return studentCd;
	}
	public void setStudentCd(String studentCd) {
		this.studentCd = studentCd;
	}

	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}





}