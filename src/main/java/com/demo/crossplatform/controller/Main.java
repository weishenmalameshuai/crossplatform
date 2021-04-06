package com.demo.crossplatform.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/crossplatform/main")
@RestController
public class Main {

    @RequestMapping("mainPage")
    public Object mainPage() {
        return new ModelAndView("main/mainPage");
    }
}