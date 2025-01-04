package com.orbanszlrd.quizapi.modules.country.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orbanszlrd.quizapi.modules.country.model.Country;
import com.orbanszlrd.quizapi.modules.country.repositroy.CountryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CountryImportService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CountryRepository countryRepository;

    // Issues with the restcountries.com
    private Country[] getDataFromTheApi() {
        Country[] countries = null;

        try {
            String url = "https://restcountries.com/v2/all";
            ResponseEntity<Country[]> response = restTemplate.getForEntity(url, Country[].class);

            if (response.getStatusCode().is2xxSuccessful()) {
                countries = response.getBody();
            } else {
                throw new RuntimeException("API Error");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return countries;
    }

    private Country[] getDataFromResourceFile() {
        Country[] countries = null;
        String path = "data/countries.json";

        try (InputStream inputStream = CountryImportService.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("File not found in resources: " + path);
            }
            ObjectMapper objectMapper = new ObjectMapper();

            countries = objectMapper.readValue(inputStream, Country[].class);

            System.out.println(Arrays.stream(countries).collect(Collectors.toList()));
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }

        return countries;
    }

    public void fillDatabase() {
        long countryCount = countryRepository.count();

        if (countryRepository.count() == 0) {
            Country[] countries = getDataFromResourceFile();

            log.info("Import {} Countries", countries.length);

            Arrays.stream(countries).forEach(country -> {
                log.info("Import {}", country);

                try {
                    Country c = countryRepository.save(country);
                    log.info("{} imported successfully", c);

                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
            });
        } else {
            log.info("{} countries already in the database.", countryCount);
        }
    }
}
