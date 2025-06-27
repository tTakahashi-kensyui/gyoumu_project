package com.example.demo.Attendance;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attendance")
public class AttendanceController {

	private final AttendanceDao attendanceDao;

	public AttendanceController(AttendanceDao attendanceDao) {
		this.attendanceDao = attendanceDao;
	}

	@GetMapping
	public String showAttendanceList(Model model) {
	    List<AttendanceWithUserEntity> list = attendanceDao.findAttendanceWithUser();
	    model.addAttribute("attendanceList", list);
		model.addAttribute("title", "出退勤記録");
	    return "attendance/attendance";
	}
}