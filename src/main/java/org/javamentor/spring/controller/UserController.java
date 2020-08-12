package org.javamentor.spring.controller;

import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private static boolean isInit = false;

    @Autowired
    public UserService userService;

    public UserController() {
    }

    @GetMapping("/index")
    public String indexPage() {
        if (!isInit) {
            insertDataToDatabase();
            isInit = true;
        }
        return "index";
    }

    @GetMapping("/start")
    public String startMVCApp(ModelMap modelMap) {
        List<User> userList = userService.usersList();
        modelMap.addAttribute("listUsers", userList);
        return "start";
    }

    @GetMapping("/create")
    public String newUserForm(Map<String, Object> model) {
        model.put("user", new User());
        return "create_user";
    }

    @GetMapping("/find")
    public String findUserByIdForm(Map<String, Object> model) {
        return "find_form";
    }

    @GetMapping("/findResult")
    public ModelAndView findUserResultForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        User user = userService.readUser(id);
        ModelAndView mav = new ModelAndView("find_result_form");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.createNewUser(user);
        return "redirect:/user/start";
    }

    @GetMapping("/edit")
    public ModelAndView updateUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        ModelAndView mav = new ModelAndView("update_user");
        User user = userService.readUser(id);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/user/start";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        userService.deleteUser(id);
        return "redirect:/user/start";
    }

    private void insertDataToDatabase() {
        User john = new User("Elton", "John", 1960);
        User jackson = new User("Michael", "Jackson", 1965);
        User jagger = new User("Mick", "Jagger", 1956);
        User marlo = new User("Marlon", "Brando", 1955);
        User tiger = new User("Tiger", "Woods", 1970);

        userService.createNewUser(marlo);
        userService.createNewUser(john);
        userService.createNewUser(jackson);
        userService.createNewUser(jagger);
        userService.createNewUser(tiger);
    }
}

