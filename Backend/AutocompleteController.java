package com.milky.trackerWeb.controller;

import com.milky.trackerWeb.service.AtlasAutocompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/autocomplete")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AutocompleteController {

    @Autowired
    private AtlasAutocompleteService autocompleteService;

    @GetMapping("/suggest")
    public Map<String, String> getSuggestions(@RequestParam String prefix) {
        return autocompleteService.getAutoCompleteSuggestions(prefix);
    }

    @GetMapping("/search")
    public String searchByUsername(@RequestParam String username) {
        return autocompleteService.searchByUsername(username);
    }
}
