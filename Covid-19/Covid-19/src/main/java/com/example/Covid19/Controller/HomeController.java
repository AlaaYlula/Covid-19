package com.example.Covid19.Controller;

import com.example.Covid19.BluePrint.Filter;
import com.example.Covid19.BluePrint.Statistics;
import com.example.Covid19.Repositories.AllCountryRepository;
import com.example.Covid19.Repositories.FilterRepository;
import com.example.Covid19.database.AllCountryData;
import com.example.Covid19.database.Countryfilter;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


import static com.example.Covid19.Covid19Application.ReadFromAPI;

@Controller
public class HomeController {

    private final FilterRepository filterRepository;
    private final AllCountryRepository allCountryRepository;

    public HomeController(FilterRepository filterRepository, AllCountryRepository allCountryRepository) {
        this.filterRepository = filterRepository;
        this.allCountryRepository = allCountryRepository;
    }

    @GetMapping("/")
    public String getHome(Model model) throws IOException {

            //Convert To Object From JSON Format
            String dataJson = ReadFromAPI("https://api.covid19api.com/world/total");
            Gson gson = new Gson();
            //Get the results Object
            Statistics statisticsResult = gson.fromJson(dataJson, Statistics.class);
            int confirmed = statisticsResult.TotalConfirmed;
            int death = statisticsResult.TotalDeaths;
            int recover = statisticsResult.TotalRecovered;

            // Check if the country appears many times, I need it only one country name string in the select option
            List<AllCountryData> countries = allCountryRepository.findAll();
            Set<String> countryName = new HashSet<>();
            for (AllCountryData country: countries
                 ) {
                countryName.add(country.getCountry());
            }
            model.addAttribute("countries",countryName);
            model.addAttribute("confirmed",confirmed);
            model.addAttribute("death",death);
            model.addAttribute("recover",recover);
        return "home";
    }

    @PostMapping("/search")
    public String userFilter(@RequestParam String country , String start , String end , Model model,
                                   RedirectAttributes attributes) throws IOException, ParseException {
        // https://java2blog.com/format-date-to-yyyy-mm-dd-java/
        //object of SimpleDateFormat class
        // Specify format as "yyyy-MM-dd"
        // Use format method on SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //check if the user input the all fields
        String wrong;
        if(country.equals("")  ||start.equals("")  || end.equals("") ){
            wrong = "input";
            attributes.addFlashAttribute("wrong", wrong);
            attributes.addFlashAttribute("countryName", country);
            attributes.addFlashAttribute("start",start);
            attributes.addFlashAttribute("end",end);
            return "redirect:/";
        }else {
            //dates to be compare
            Date date1 = sdf.parse(start);
            Date date2 = sdf.parse(end);
            if( date1.compareTo(date2)>0){
                wrong = "input";
                attributes.addFlashAttribute("wrong", wrong);
                attributes.addFlashAttribute("countryName", country);
                attributes.addFlashAttribute("start",start);
                attributes.addFlashAttribute("end",end);
                return "redirect:/";

            }
        }

        //dates to be compare
        Date date1 = sdf.parse(start);
        Date date2 = sdf.parse(end);


        AllCountryData slugCountry = allCountryRepository.findByCountry(country);
        if(filterRepository.findAll().size()!=0){
        filterRepository.deleteAll();
        }
        //  Convert To String From JSON Format
        String dataJsonFilter = ReadFromAPI("https://api.covid19api.com/country/"+slugCountry.getCountry_slug()+"/status/confirmed");
        Gson gsonFilter = new Gson();
        //Get the results Countries which we want filter on it
        // SAVE to DATABASE
        Filter[] countryResult = gsonFilter.fromJson(dataJsonFilter, Filter[].class); // All Countries
            for (Filter r: countryResult
            ) {
                SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                String stringDate = DateFor.format(r.Date);
                Countryfilter countrySaved = new Countryfilter(r.Country,r.Cases,r.Date,stringDate);
                filterRepository.save(countrySaved);
            }

            // Query to get the countries' data between range of two dates
        List<Countryfilter> countriesQuery1 = filterRepository.findByDateGreaterThanEqual(date1);
        List<Countryfilter> countriesQuery2 = filterRepository.findByDateLessThanEqual(date2);

        // check if the date2 in the Query1 result so include the date2 data in the List result
        boolean flag = false;
        Countryfilter endDateCountry = new Countryfilter();
        // https://java2blog.com/format-date-to-yyyy-mm-dd-java/
        for (Countryfilter c: countriesQuery1
        ) {
            String formattedDateStr = sdf.format(c.getDate());
            Date date = sdf.parse(formattedDateStr);
            if( date.compareTo(date2)==0){
                endDateCountry = c;
                flag = true;
                break;
            }
        }

        // return the common date in the both Query1 and Query2 and put the result on the Query1
        countriesQuery1.retainAll(countriesQuery2);
//        List<Date> dates = countriesQuery1.stream()
//                .map(s -> s.getDate()).toList();
        // check if the date2 in the Query1 result so include the date2 data in the List result
        if(flag){
            countriesQuery1.add(endDateCountry);
        }

        /*
        List<Countryfilter> countryFiltered = new ArrayList<>();
        for (Countryfilter country1: countries
        ) {
            if(country1.getCountry().equals(country) ){
                // Specify format as "yyyy-MM-dd"
                SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
                // Use format method on SimpleDateFormat
                String formattedDateStr = dmyFormat.format(country1.getDate());
                //System.out.println("Formatted Date in String format: "+formattedDateStr);
                Date date = sdf.parse(formattedDateStr);
                // https://www.javatpoint.com/how-to-compare-dates-in-java
                if((date.compareTo(date1)>0 || date.compareTo(date1)==0) &&
                        (date.compareTo(date2) < 0 ||date.compareTo(date2)==0) ){
                    // https://stackoverflow.com/questions/5937017/how-to-convert-a-date-in-this-format-tue-jul-13-000000-cest-2010-to-a-java-d
//                    DateFormat dateFormat = new SimpleDateFormat(
//                            "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//                    dateFormat.parse(formattedDateStr);
//                    System.out.println(dateFormat.format(new Date()));
                    countryFiltered.add(country1);
                }
            }
        }
                //attributes.addFlashAttribute("countriesList", countryFiltered);

        */

        attributes.addFlashAttribute("countriesList", countriesQuery1);
        // Check if there is records between the dates which the user input
        if (countriesQuery1.size()==0){
            wrong = "empty";
            attributes.addFlashAttribute("wrong", wrong);
        }else{
            attributes.addFlashAttribute("wrong", "list");

        }
        attributes.addFlashAttribute("countryName", country);
        attributes.addFlashAttribute("start",start);
        attributes.addFlashAttribute("end",end);

        return "redirect:/";
    }

}
