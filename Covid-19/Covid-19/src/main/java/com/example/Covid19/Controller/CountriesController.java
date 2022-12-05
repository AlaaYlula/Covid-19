package com.example.Covid19.Controller;

import com.example.Covid19.Repositories.CountryRepository;
import com.example.Covid19.database.Countrydata;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.util.List;

@Controller
public class CountriesController {

    private final CountryRepository countryRepository;

    public CountriesController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries")
    public String getCountriesPage(Model model) throws FileNotFoundException {

        List<Countrydata> countries = countryRepository.findAll();
        model.addAttribute("countriesList", countries);

        return "countries";
    }
}
