package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookPagesController {

    @GetMapping("/")
    public String listPage() {
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam long id, Model model) {
        model.addAttribute("id", id);
        return "edit";
    }

    @GetMapping("/create")
    public String createBookPage() {
        return "create";
    }
}
