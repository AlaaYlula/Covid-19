package com.example.Covid19.Controller;

import com.example.Covid19.BluePrint.Countries.Country;
import com.example.Covid19.BluePrint.Countries.Root;
import com.example.Covid19.Repositories.CountryRepository;
import com.example.Covid19.database.Countrydata;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;
import java.util.List;

import static com.example.Covid19.Covid19Application.ReadFromAPI;

@Controller
public class CountriesController {

    private final CountryRepository countryRepository;

    public CountriesController(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @GetMapping("/countries")
    public String getCountriesPage(Model model) throws FileNotFoundException {

//        //  Convert To Object From JSON Format
//        String dataJson = ReadFromAPI("https://api.covid19api.com/summary");
//        Gson gson = new Gson();
//
//        // Write On DATABASE
//        if(countryRepository.findAll().size() == 0)
//        {
//            //Get the results Countries
//            Root countryResult = gson.fromJson(dataJson, Root.class); // All Countries
//            for (Country r:countryResult.Countries
//            ) {
//                Countrydata country = new Countrydata(r.Country,r.TotalConfirmed,r.TotalDeaths,r.TotalRecovered,r.Date);
//                countryRepository.save(country);
//            }
//
//        }

        List<Countrydata> countries = countryRepository.findAll();
        model.addAttribute("countriesList", countries);

        return "countries";
    }
}
