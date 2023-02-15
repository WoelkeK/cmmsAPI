package pl.medos.cmmsApi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.medos.cmmsApi.model.UserModel;
import pl.medos.cmmsApi.service.IUserService;
    @Controller
    public class UserController {

        @Autowired
        private IUserService userService;

        // Go to Registration Page
        @GetMapping("/register")
        public String register() {
            return "pages/registerUser";
        }

        // Read Form data to save into DB
        @PostMapping("/saveUser")
        public String saveUser(
                @ModelAttribute UserModel user,
                Model model
        ) {
            Integer id = userService.saveUser(user);
            String message = "User '" + id + "' saved successfully !";
            model.addAttribute("msg", message);
            return "pages/registerUser";
        }
    }
