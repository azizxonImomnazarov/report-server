package com.example.reportserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.reportserver.constants.SesseionConstants.LOCALE_SESSION_ATTRIBUTE;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    @GetMapping("/get-time")
    public String getTime(HttpSession session) {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @PostMapping("/lang/{name}")
    public ResponseEntity<String> getTime(HttpSession session, @PathVariable String name) {
        session.setAttribute(LOCALE_SESSION_ATTRIBUTE, name);
        return ResponseEntity.ok("success");
    }
}
