package com.example.demo.Face;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Attendance.AttendanceDao;

@Controller
public class FaceAttendanceController {
	private final AttendanceDao attendanceDao;

	public FaceAttendanceController(AttendanceDao attendanceDao) {
		this.attendanceDao = attendanceDao;
	}
	
	@PostMapping("/face/attendance/start")
	public String faceStartAttendance() {
		int userId = 1;
		attendanceDao.startAttendance(userId);
		 return "redirect:/face/complete?type=start";
	}
	@PostMapping("/face/attendance/end")
	public String faceEndAttendance() {
		int userId = 1;
		attendanceDao.endAttendance(userId);
		 return "redirect:/face/complete?type=end";
	}
}
