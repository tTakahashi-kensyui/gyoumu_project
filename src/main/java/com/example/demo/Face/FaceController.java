package com.example.demo.Face;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/face")
public class FaceController {

    // 顔認証（指選択）ページ
    @GetMapping("/authenticate")
    public String showAuthenticatePage() {
        return "face/authenticate";  // HTML: face/authenticate.html
    }

    // 指の本数の処理
    @PostMapping("/authenticate")
    public String processFinger(@RequestParam("finger") int finger) {
        if (finger == 2) {
            return "redirect:/face/attendance";  // 指が2なら出退勤ページへ
        } else {
            return "redirect:/";  // 指が1や3ならトップへ
        }
    }

    // 出退勤ページ
    @GetMapping("/attendance")
    public String showAttendancePage() {
        return "face/attendance";
    }

    @PostMapping("/attendance/start")
    public String registerStart() {
        return "redirect:/face/complete?type=start";
    }

    @PostMapping("/attendance/end")
    public String registerEnd() {
        return "redirect:/face/complete?type=end";
    }

    @GetMapping("/complete")
    public String showCompletePage(@RequestParam String type, Model model) {
        if ("start".equals(type)) {
            model.addAttribute("message", "出勤完了しました！");
        } else {
            model.addAttribute("message", "退勤完了しました！");
        }
        return "face/complete";
    }
}
