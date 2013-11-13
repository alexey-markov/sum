package ru.avm.sum.view.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class WController {

    @RequestMapping("/index")
    public String listContacts(Map<String, Object> map) {

        CSecurity.MyUser user = (CSecurity.MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("username", user.getUsername());
        map.put("user_uid", user.getId());

        return "first";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }
}
