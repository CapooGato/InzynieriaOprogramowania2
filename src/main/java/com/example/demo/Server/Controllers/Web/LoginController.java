package com.example.demo.Server.Controllers.Web;

import com.example.demo.Server.Models.Urzedy;
import com.example.demo.Server.Models.Uzytkownicy;
import com.example.demo.Server.Models.Wnioski;
import com.example.demo.Server.Repository.UzytkownicyRepository;
import com.example.demo.Server.Repository.WnioskiRepository;
import com.example.demo.Server.Service.UrzedyService;
import com.example.demo.Server.Service.UzytkownicyService;
import com.example.demo.Server.Service.WnioskiService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {
    private final UzytkownicyService uzytkownicyService;
    private final UrzedyService urzedyService;
    private final UzytkownicyRepository uzytkownicyRepository;
    private final WnioskiService wnioskiService;

    @Autowired
    public LoginController(UzytkownicyService uzytkownicyService, UrzedyService urzedyService,
                           UzytkownicyRepository uzytkownicyRepository, WnioskiService wnioskiService){
        this.uzytkownicyService = uzytkownicyService;
        this.urzedyService = urzedyService;
        this.uzytkownicyRepository = uzytkownicyRepository;
        this.wnioskiService = wnioskiService;
    }

    @GetMapping(value = "/login")
    public String showLoginForm(Model model){
        model.addAttribute("uzytkownik", new Uzytkownicy());
        return "logowanie/login";
    }

    @PostMapping(value = "/login")
    public String processLogin(
            @ModelAttribute("uzytkownik") Uzytkownicy uzytkownik,
            Model model,
            HttpSession session) {

        Optional<Uzytkownicy> userOpt = uzytkownicyService.findByLogin(uzytkownik.getLogin());

        if (userOpt.isPresent()) {
            Uzytkownicy existingUser = userOpt.get();
            if (existingUser.getHaslo().equals(uzytkownik.getHaslo())) {
                // Zapisujemy ID jako Integer
                session.setAttribute("uzytkownikId", existingUser.getUzytkownikId());
                session.setAttribute("userRole", existingUser.getRola());

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

    // Dodajemy wylogowanie
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWniosek(
            @RequestParam("file") MultipartFile file,
            @RequestParam("uzytkownikId") Integer uzytkownikId,
            @RequestParam(value = "komentarz", required = false) String komentarz) {

        try {
            Wnioski wniosek = wnioskiService.createWniosek(file, uzytkownikId, komentarz);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("wniosekId", wniosek.getWniosekId());
            response.put("message", "Wniosek został pomyślnie złożony");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/dashboard/admin")
    public String adminDashboard(Model model, HttpSession session) {
        // Sprawdzenie sesji
        if (!"admin".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            return "redirect:/login";
        }

        model.addAttribute("uzytkownik", new Uzytkownicy());
        model.addAttribute("uzytkownicy", uzytkownicyService.getAllUzytkownicy());
        model.addAttribute("urzad", new Urzedy());
        model.addAttribute("urzedy", urzedyService.findAll());
        return "admin/admin";
    }

    @GetMapping("/dashboard/urzednik")
    public String urzednikDashboard(HttpSession session, Model model) {
        if (!"urzednik".equalsIgnoreCase((String) session.getAttribute("userRole"))) {
            return "redirect:/login";
        }
        Integer uzytkownikIdInt = (Integer) session.getAttribute("uzytkownikId");
        Optional<Uzytkownicy> uzytkownicy = uzytkownicyRepository.findById(Long.valueOf(uzytkownikIdInt));
        List<Wnioski> wnioskiList = wnioskiService.getWnioskiByUrzadId(uzytkownicy.get().getUrzad().getUrzadId());
        model.addAttribute("nazwisko",uzytkownicy.get().getUrzad().getMiejscowosc());
        model.addAttribute("wnioski",wnioskiList);
        return "urzednik/urzednik";
    }

    @GetMapping("/dashboard/podatnik")
    public String podatnikDashboard(Model model, HttpSession session) {
        // Pobierz ID jako Integer i rzutuj na Long
        Integer uzytkownikIdInt = (Integer) session.getAttribute("uzytkownikId");
        String role = (String) session.getAttribute("userRole");

        if (uzytkownikIdInt == null || !"podatnik".equalsIgnoreCase(role)) {
            return "redirect:/login";
        }

        Long uzytkownikId = uzytkownikIdInt.longValue();

        Uzytkownicy uzytkownik = uzytkownicyRepository.findById(uzytkownikId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Użytkownik nie znaleziony"));

        model.addAttribute("uzytkownik", uzytkownik);
        return "podatnik/podatnik";
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
}
