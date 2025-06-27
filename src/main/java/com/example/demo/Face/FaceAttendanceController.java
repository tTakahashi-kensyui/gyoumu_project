package com.example.demo.Face;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Attendance.AttendanceDao;

@RestController
public class FaceAttendanceController {
	private final AttendanceDao attendanceDao;

	public FaceAttendanceController(AttendanceDao attendanceDao) {
		this.attendanceDao = attendanceDao;
	}
	
	//@PostMapping("face/attendance/start")
	//public String faceStartAttendance() {
		//int userId = 1;
		//attendanceDao.startAttendance(userId);
		//return "出勤しました";
	//}
	//@PostMapping("face/attendance/end")
	//public String faceEndAttendance() {
		//int userId = 1;
		//attendanceDao.startAttendance(userId);
		//return "退勤しました";
	//}
}
