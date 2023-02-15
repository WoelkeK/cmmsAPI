package pl.medos.cmmsApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String getHomePage() {
        return "pages/homePage";
    }

    @GetMapping("/welcome")
    public String getWelcomePage() {
        return "pages/welcomePage";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "pages/adminPage";
    }

    @GetMapping("/emp")
    public String getEmployeePage() {
        return "pages/empPage";
    }

    @GetMapping("/mgr")
    public String getManagerPage() {
        return "pages/mgrPage";
    }

    @GetMapping("/hr")
    public String getHrPage() {
        return "pages/hrPage";
    }

    @GetMapping("/common")
    public String getCommonPage() {
        return "pages/commonPage";
    }

    @GetMapping("/accessDenied")
    public String getAccessDeniedPage() {
        return "pages/accessDeniedPage";
    }
}