package com.example.demo.kaikeikanri;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountingController {

    @GetMapping("/home")
    public String showHomePage() {
        return "home";  // home.html を表示
    }

    @GetMapping("/summary")
    public String showSummaryPage() {
        return "summary";  // summary.html を表示
    }
}

