package com.example.demo.Menu;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.MenuDao;

@Controller
public class MenuController {

	private final MenuDao menuDao;
	
	public MenuController(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@RequestMapping("/")
	public String topPage(Model model) {
		model.addAttribute("title", "トップページ");
		return "Menu/index";
	}
	@RequestMapping("/hattyuu")
	public String hatyuu(Model model) {
		return "hattyuu/hattyuu";
	}

}
