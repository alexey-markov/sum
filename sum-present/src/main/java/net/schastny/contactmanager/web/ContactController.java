package net.schastny.contactmanager.web;

import net.schastny.contactmanager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @RequestMapping("/index")
    public String listContacts(Map<String, Object> map) {

        SecurityProvider.MyUser user = (SecurityProvider.MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        map.put("username", user.getUsername());
        map.put("user_uid", user.getId());

        return "first";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }
}
