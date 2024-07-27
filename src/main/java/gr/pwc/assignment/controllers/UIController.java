package gr.pwc.assignment.controllers;

import gr.pwc.assignment.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UIController {

    private final UserService userService;

    @GetMapping("/ui")
    public String html(Model model) {
        model.addAttribute("users", userService.listUsers(PageRequest.of(0, 10)));
        return "index";
    }

}
