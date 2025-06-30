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

	@RequestMapping("/login")
	public String showLoginPage() {
		return "kaikei/login"; // login.htmlがtemplates内に必要です
	}

	@RequestMapping("/hattyuu")
	public String hatyuu(Model model) {
		return "hattyuu/hattyuu";
	}

	@RequestMapping("/honbu")
	public String honbu(Model model) {
		return "hattyuu/honbu";
	}

	@RequestMapping("/home")
	public String showHomePage() {
		return "kaikei/home"; // home.html を表示
	}

	@RequestMapping("/summary")
	public String showSummaryPage() {
		return "kaikei/summary"; // summary.html を表示
	}

	@RequestMapping("/faceTop")
	public String faceTopPage(Model model) {
		model.addAttribute("title", "トップページ");
		return "face/faceTop";
	}
}
