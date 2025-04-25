package com.example.demo.Server.Controllers.Web;

import com.example.demo.Server.Models.Urzedy;
import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Service.AppUserService;
import com.example.demo.Server.Service.UrzedyService;
import com.example.demo.Server.Service.UzytkownicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {
    private final UzytkownicyService uzytkownicyService;
    private final UrzedyService urzedyService;

    @Autowired
    public LoginController(UzytkownicyService uzytkownicyService, UrzedyService urzedyService){
        this.uzytkownicyService = uzytkownicyService;
        this.urzedyService = urzedyService;
    }
    @GetMapping(value = "/login")
    public String showLoginForm(){
        return "logowanie/login";
    }

    @PostMapping(value = "/login")
    public String processLogin(@ModelAttribute("uzytkownik") Uzytkownicy uzytkownik, Model model) {
        Optional<Uzytkownicy> userOpt = uzytkownicyService.findByLogin(uzytkownik.getLogin());
        if (userOpt.isPresent()) {
            Uzytkownicy existingUser = userOpt.get();
            if (existingUser.getHaslo().equals(uzytkownik.getHaslo())) {
                String role = existingUser.getRola().toLowerCase();
                return "redirect:/dashboard/" + role;
            } else {
                model.addAttribute("error", "Nieprawidłowe hasło.");
                return "logowanie/login";
            }
        }

        model.addAttribute("error", "Użytkownik nie istnieje.");
        return "logowanie/login";
    }


    @GetMapping("/dashboard/admin")
    public String adminDashboard(Model model) {
        model.addAttribute("uzytkownik", new Uzytkownicy());
        model.addAttribute("uzytkownicy", uzytkownicyService.getAllUzytkownicy());
        model.addAttribute("urzad", new Urzedy());
        model.addAttribute("urzedy", urzedyService.findAll());
        return "admin/admin";
    }

    @PostMapping("/dashboard/admin/urzad")
    public String addUrzad(@ModelAttribute Urzedy urzad) {
        urzedyService.addUrzad(urzad);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/dashboard/admin/urzad/usun")
    public String deleteUrzad(@RequestParam("id") int id) {
        urzedyService.deleteUrzad(id);
        return "redirect:/dashboard/admin";
    }

    @PostMapping(value = "/dashboard/admin")
    public String addUserByAdmin(@ModelAttribute("uzytkownik") Uzytkownicy uzytkownik, Model model) {
        Optional<Uzytkownicy> istnieje = uzytkownicyService.findByLogin(uzytkownik.getLogin());
        if (istnieje.isPresent()) {
            model.addAttribute("error", "Użytkownik o takim loginie już istnieje.");
            return "admin/admin"; // wracasz na stronę z komunikatem o błędzie
        }
        uzytkownicyService.addUzytkownicy(uzytkownik);
        return "redirect:/dashboard/admin";
    }

    @PostMapping("/dashboard/admin/usun")
    public String usunUzytkownika(@RequestParam("id") int id) {
        uzytkownicyService.usunUzytkownikaPoId(id);
        return "redirect:/dashboard/admin";
    }

    @GetMapping(value = "/dashboard/podatnik")
    public String podatnikDashboard() {
        return "podatnik/podatnik";
    }

    @GetMapping(value = "/dashboard/urzednik")
    public String urzednikDashboard() {
        return "urzednik/urzednik";
    }
}
