package com.example.demo.Attendance;

public class AttendanceWithStoreEntity {
	private int attendanceId;
	private int userId;
	private String date;
	private String status;
	private String startTime;
	private String endTime;
	public Double getBreakTime() {
		return breakTime;
	}
	public void setBreakTime(Double breakTime) {
		this.breakTime = breakTime;
	}
	public Double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private Double breakTime;
	private Double workTime;
	private String remark;
	private String storeName;


	// Getter Setter

	public int getAttendanceId() {
		return attendanceId;
	}
	public void setAttendanceId(int attendanceId) {
		this.attendanceId = attendanceId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
}
