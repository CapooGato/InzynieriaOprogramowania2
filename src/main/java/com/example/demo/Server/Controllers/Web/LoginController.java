package com.example.demo.Server.Controllers.Web;

import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Service.AppUserService;
import com.example.demo.Server.Service.UzytkownicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private final UzytkownicyService uzytkownicyService;

    @Autowired
    public LoginController(UzytkownicyService uzytkownicyService){
        this.uzytkownicyService = uzytkownicyService;
    }
    @GetMapping()
    public String showLoginForm(){
        return "logowanie/login";
    }

    /**
     * redirect zmienia URL, a return nie.
     * trzeba dorobić kontroler od dashboard żeby działał
     * */
    @PostMapping()
    public String processLogin(@Valid @ModelAttribute Uzytkownicy uzytkownik){
        if(uzytkownicyService.findUserByLogin(uzytkownik.getLogin())){
            return "redirect:admin/admin";//uzytkownicyService.getViewByRole(uzytkownik);
        }
        return "logowanie/login";
    }


}
