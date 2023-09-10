package com.milky.trackerWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.util.Map;

@Service
public class AtlasAutocompleteService {

    @Autowired
    private TrieService trieService;
    
    @PostConstruct
    public void init() {
        try {
            File file = new File("C:\\Users\\toadh\\OneDrive\\Desktop\\Projects\\AtlasParticipants.json");
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> userData = objectMapper.readValue(file, new TypeReference<Map<String, String>>() {});
            loadData(userData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadData(Map<String, String> data) {
        for (Map.Entry<String, String> entry : data.entrySet()) {
            trieService.insert(entry.getKey(), entry.getValue());
        }
    }

    public Map<String, String> getAutoCompleteSuggestions(String prefix) {
        return trieService.getSuggestions(prefix);
    }

    public String searchByUsername(String username) {
        return trieService.search(username);
    }
}
