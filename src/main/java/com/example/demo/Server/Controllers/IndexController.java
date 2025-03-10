package com.example.demo.Server.Controllers;

import com.example.demo.Server.Models.AppUsers;
import com.example.demo.Server.Service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class IndexController {

    private final AppUserService appUserService;

    @Autowired
    public IndexController(AppUserService appUserService){
        this.appUserService = appUserService;
    }
    @GetMapping(value = "/")
    public String index(){
        return "index";
    }

    @GetMapping(value = "/users")
    public String getAllUsers(Model model) {
        List<AppUsers> users = appUserService.allAppUsers();

        if (users == null) {
            users = List.of(); // Tworzymy pustą listę zamiast null
        }
        model.addAttribute("users", users);
        return "users";  // Odpowiada nazwie pliku HTML (users.html)
    }


    @PostMapping("/addUser")
    public String addUser(@Valid @ModelAttribute("user") AppUsers user,
                          BindingResult result,
                          RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "add-user";
        }

        appUserService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Użytkownik dodany pomyślnie!");
        return "redirect:/users";
    }

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new AppUsers()); // Inicjalizacja pustego obiektu
        return "add-user";
    }

}
