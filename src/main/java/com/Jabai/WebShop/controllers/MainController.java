package com.Jabai.WebShop.controllers;

import com.Jabai.WebShop.domain.Comix;
import com.Jabai.WebShop.service.ComixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MainController {

    private final ComixService comixService;

    @Autowired
    public MainController(ComixService comixService) {
        this.comixService = comixService;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("content", "firstContent");
        return "test";
    }

    @GetMapping("/comixList")
    public String showComixList(Model model) {
        List<Comix> comixList = comixService.findAll();
        model.addAttribute("comixList", comixList);
        return "comixList";
    }

    @GetMapping("/comixForm")
    public String showComixForm(Model model) {
        List<Comix> comixList = comixService.findAll();
        model.addAttribute("comixForm", comixList);
        return "comixForm";
    }
}
