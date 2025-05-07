package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestListSubject implements Serializable {
    private static final long serialVersionUID = 1L;

    private int entYear;
    private String studentNo;
    private String studentName;
    private String classNum;
    private Map<Integer, Integer> points = new HashMap<>();

    // Getters and Setters
    public int getEntYear() {
        return entYear;
    }
    public void setEntYear(int entYear) {
        this.entYear = entYear;
    }
    public String getStudentNo() {
        return studentNo;
    }
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
    public String getStudentName() {
        return studentName;
    }
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getClassNum() {
        return classNum;
    }
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    // 試験成績を安全に取得する
    public Integer getPoint(int key) {
        return points != null ? points.getOrDefault(key, 0) : 0;
    }

    // 試験成績を設定する
    public void setPoint(int key, int value) {
        if (points == null) {
            points = new HashMap<>();
        }
        points.put(key, value);
    }

    // すべての成績を安全に取得する
    public Map<Integer, Integer> getPoints() {
        return points != null ? new HashMap<>(points) : new HashMap<>();
    }

    // すべての成績を安全に設定する
    public void setPoints(Map<Integer, Integer> points) {
        this.points = points != null ? new HashMap<>(points) : new HashMap<>();
    }
}
