package com.example.demo.Server.Controllers.Api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/szablony")
public class SzablonyController {

    private final String SZABLONY_FOLDER = "src/main/resources/static/szablony";

    @GetMapping
    public ResponseEntity<List<Map<String, String>>> getSzablony() {
        File folder = new File(SZABLONY_FOLDER);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".pdf"));

        if (files == null || files.length == 0) {
            return ResponseEntity.noContent().build();
        }

        List<Map<String, String>> szablony = Arrays.stream(files)
                .map(file -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("nazwa", file.getName());
                    map.put("url", "/szablony/" + file.getName());
                    return map;
                })
                .toList();

        return ResponseEntity.ok(szablony);
    }
}
