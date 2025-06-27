package com.example.demo.Shift;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.staff.StaffDao;
import com.example.demo.staff.StaffEntity;

@Controller
public class ShiftController {

	@Autowired
	private ShiftDao shiftdao;
	@Autowired
	private StaffDao staffdao;

	@GetMapping("/shift/list")
	public String showShiftMatrix(
			@RequestParam(required = false) Integer year,
			@RequestParam(required = false) Integer month,
			Model model) {

		// 年月の取得（指定がなければ今月）
		LocalDate now = LocalDate.now();
		int y = (year != null) ? year : now.getYear();
		int m = (month != null) ? month : now.getMonthValue();

		// 日付一覧を作成（1日〜月末）
		List<LocalDate> days = createDatesFor(y, m);

		// スタッフ全員を取得
		List<StaffEntity> staffList = staffdao.findAll();

		// その月の全シフトを取得（JOINでstaffNameも含める）
		List<ShiftEntity> shiftList = shiftdao.findAllWithStaffName(); // ←または findAll() でもOK

		// スタッフごとの日付別シフトを作成
		Map<StaffEntity, Map<LocalDate, ShiftEntity>> shiftMatrix = new LinkedHashMap<>();

		for (StaffEntity staff : staffList) {
			System.out.println("スタッフ名：" + staff.getUser_name());
			Map<LocalDate, ShiftEntity> dailyMap = new LinkedHashMap<>();
			for (LocalDate day : days) {
				ShiftEntity shift = shiftList.stream()
						.filter(s -> s.getStaffId() == staff.getId() && s.getShiftDate().equals(day))
						.findFirst()
						.orElse(null);
				dailyMap.put(day, shift);
			}
			shiftMatrix.put(staff, dailyMap);
		}

		// ビューに渡す
		model.addAttribute("targetYear", y);
		model.addAttribute("targetMonth", m);
		model.addAttribute("days", days);
		model.addAttribute("shiftMatrix", shiftMatrix);

		return "shift/shift_list";
	}

	//登録画面へ
	@GetMapping("/shift/form")
	public String showShiftForm(Model model) {
		model.addAttribute("shiftForm", new ShiftForm()); // フォーム用DTO
		model.addAttribute("staffList", staffdao.findAll());
		return "shift/shift_form";
	}

	// 登録処理
    @PostMapping("/shift/save")
    public String saveShift(@ModelAttribute ShiftForm shiftForm) {
    	System.out.println("id:"+shiftForm.getStaffId());
    	System.out.println("timeslot:"+shiftForm.getTimeSlot());
    	
        // シフトエンティティに変換して保存（必要に応じて変換処理を書く）
        ShiftEntity shift = new ShiftEntity();
        shift.setStaffId(shiftForm.getStaffId());
        shift.setShiftDate(shiftForm.getShiftDate());
        shift.setTimeSlot(shiftForm.getTimeSlot());
        shift.setStatus("希望"); // デフォルト

        shiftdao.insert(shift);
        return "redirect:/shift/list"; // 一覧に戻る
    }

	//編集画面
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		ShiftEntity shift = shiftdao.findById(id);
		model.addAttribute("shift", shift);
		return "shift/shift_form";
	}

	// 削除処理
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		shiftdao.delete(id);
		return "redirect:/shift/list";
	}

	public List<LocalDate> createDatesFor(int year, int month) {
		YearMonth ym = YearMonth.of(year, month);
		LocalDate start = ym.atDay(1);
		int days = ym.lengthOfMonth();

		List<LocalDate> result = new ArrayList<>();
		for (int i = 0; i < days; i++) {
			result.add(start.plusDays(i));
		}
		return result;

	}

}
