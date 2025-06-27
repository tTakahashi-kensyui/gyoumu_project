package com.example.demo.Attendance;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
//	// 勤怠一覧ページ
//	@GetMapping
//	public String attendanceList(Model model) {
//		List<AttendanceWithStoreEntity> list = attendanceDao.findAllWithStore();
//		model.addAttribute("attendanceList", list);
//		model.addAttribute("title", "出退勤管理");
//		return "Attendance/attendance";
//	}

	// 出勤処理
	@PostMapping("/start")
	public String startAttendance(@RequestParam("userId") int userId) {
		attendanceDao.startAttendance(userId);
		return "redirect:/attendance";
	}

	// 退勤処理
	@PostMapping("/end")
	public String endAttendance(@RequestParam("userId") int userId) {
		attendanceDao.endAttendance(userId);
		return "redirect:/attendance";
	}
}