package bean;

public class Student implements java.io.Serializable {

	private String no;
	private String name;
	private int ent_Year;
	private String class_num;
	private boolean is_attend;
	private School school;

	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEntYear() {
		return ent_Year;
	}
	public void setEntYear(int ent_Year) {
		this.ent_Year = ent_Year;
	}
	public String getClassNum() {
		return class_num;
	}
	public void setClassNum(String class_num) {
		this.class_num = class_num;
	}
	public boolean isAttend() {
		return is_attend;
	}
	public void setIsAttend(boolean is_attend) {
		this.is_attend = is_attend;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
}
