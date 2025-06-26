package com.example.demo.Shift;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shift")
public class ShiftController {

	private ShiftDao shiftdao;

	//シフト一覧表示
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("shifts", shiftdao.findAll());
		return "shift_list";
	}

	//シフト提出
	@GetMapping("/form")
	public String form(Model model) {
		model.addAttribute("shift", new ShiftEntity());
		return "shift_form";
	}

	//
	@PostMapping("/save")
	public String save(@ModelAttribute ShiftEntity shift) {
		//shiftdao.save(shift);
		return "redirect:/shift/list";
	}
}
