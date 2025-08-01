package com.jtspringproject.JtSpringProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError() {
        // You can add more sophisticated error handling here based on status codes
        return "error"; // Renders error.html
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // Renders 403.html
    }
}
