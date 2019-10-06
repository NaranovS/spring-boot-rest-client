package com.naranov.rest_client.controller;

import com.naranov.rest_client.model.User;
import com.naranov.rest_client.model.UserProfile;
import com.naranov.rest_client.service.RoleService;
import com.naranov.rest_client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
//@SessionAttributes("roles")
//@EnableOAuth2Sso
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    MessageSource messageSource;

    // List of ALL users
    @RequestMapping(value = { "/list-users" }, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        List<User> users = userService.findAllUsers();

        model.addAttribute("users", users);
//        model.addAttribute("loggedinuser", getPrincipal());
        return "userlist";
    }


    // create new User
    @RequestMapping(value = { "/newUser" }, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        List<UserProfile> roles = roleService.findAllUserProfiles();
        User user = new User();

        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("allRoles", roles);
//        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    @RequestMapping(value = { "/newUser" }, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result,
                           ModelMap model) {
        if (result.hasErrors()) {
            List<UserProfile> roles = roleService.findAllUserProfiles();

            model.addAttribute("user", user);
            model.addAttribute("edit", false);
            model.addAttribute("allRoles", roles);
            return "registration";
        }
        userService.createNewUser(user);

        model.addAttribute("success", "User " + user.getName() + " registered successfully");
//        model.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }

    // edit user
    @GetMapping("/editUser-{id}")
    public String showFormEditUser(@PathVariable int id, Model model) {
        User user = userService.findUserById(id);
        List<UserProfile> roles = roleService.findAllUserProfiles();

        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        model.addAttribute("edit", true);
        return "registration";
    }

    @PostMapping("/editUser-{id}")
    public String editUser(@Valid User user, BindingResult result, Model model, @PathVariable int id){
//        if(user.getPassword().isEmpty()) {
//            result.rejectValue("password", "error.user", "password may not be null");
//        }
        if (result.hasErrors()){
            List<UserProfile> roles = roleService.findAllUserProfiles();

            model.addAttribute("user", user);
            model.addAttribute("allRoles", roles);
            model.addAttribute("edit", true);
            return "registration";
        }
        try {
            userService.updateUser(id, user);
            model.addAttribute("success", "User " + user.getName() + " registered successfully");
            return "registrationsuccess";
        } catch (HttpClientErrorException ex) {
            model.addAttribute("status", "User not updated: ERROR: " + ex.getRawStatusCode());
            return "notfound";
        }



//        ResponseErrorHandler response = restService.updateUser(id, user);

//        model.addAttribute("success", "User " + user.getName() + " registered successfully");
//        return "registrationsuccess";
    }

    @GetMapping("/deleteUser-{id}")
    public String deleteUser(@PathVariable int id){
        userService.deleteById(id);
        return "redirect:/list-users";
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }


    @RequestMapping(value = {"/logout"}, method = RequestMethod.POST)
    public String logoutDo(HttpServletRequest request, HttpServletResponse response){
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "/";
    }


}