package com.example.demo.Shift;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shift")
public class ShiftController {

	@Autowired
	private ShiftDao shiftdao;

	//シフト一覧表示
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("shifts", shiftdao.findAll());
		return "shift/shift_list";
	}

	//シフト提出
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("shift", new ShiftEntity());
		return "shift/shift_form";
	}

	//登録処理
	@PostMapping("/save")
	public String save(@ModelAttribute ShiftEntity shift) {
		if (shift.getShiftId() == null) {
			shiftdao.insert(shift);
		} else {
			shiftdao.update(shift);
		}
		return "redirect:/shift/list";
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

}
