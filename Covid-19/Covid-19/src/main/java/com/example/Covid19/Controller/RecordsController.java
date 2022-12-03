package com.example.Covid19.Controller;

import com.example.Covid19.BluePrint.Countries.Country;
import com.example.Covid19.Repositories.CountryRepository;
import com.example.Covid19.Repositories.RecordRepository;
import com.example.Covid19.database.Countrydata;
import com.example.Covid19.database.Record;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class RecordsController {

    private final RecordRepository recordRepository;
    private final CountryRepository countryRepository;

    public RecordsController(RecordRepository recordRepository, CountryRepository countryRepository) {
        this.recordRepository = recordRepository;
        this.countryRepository = countryRepository;
    }

    @GetMapping("/records")
    public String getRecordsPage(Model model){
        List<Record> recordsList = recordRepository.findAll();
        if (recordsList.size()==0){
            String flag = "false";
            model.addAttribute("flag",flag);
        }else{
            List<Countrydata> countriesList = new ArrayList<>();
            for (int i = 0; i < recordsList.size() ; i++) {
                countriesList.add(recordsList.get(i).getCountry());
            }
            String flag = "true";
            model.addAttribute("flag",flag);
            model.addAttribute("countriesList",countriesList);
        }
        return "records";
    }

    @PostMapping("/addrecord")
    public RedirectView addRecord(@RequestParam Long country_id){

        Countrydata country = countryRepository.findById(country_id).orElseThrow();
        // Check if the country already in the Records and stay on countries page
        List<Record> records = recordRepository.findAll();
        Boolean flag = true;
        for (int i = 0; i < records.size(); i++) {
            if (Objects.equals(records.get(i).getCountry().getCountry_id(), country_id)){
                flag = false;
                return new RedirectView("/countries");

            }
        }
        // If NOT Add and redirect to the records page:
        if(flag) {
            Record record = new Record();
            record.setCountry(country);
            recordRepository.save(record);
        }
        return new RedirectView("/records");
    }

    @PostMapping("/deleterecord")
    public RedirectView deleteRecord(@RequestParam Long country_id){

        Countrydata country = countryRepository.findById(country_id).orElseThrow();
        List<Record> recordList = recordRepository.findAll();
        for (int i = 0; i < recordList.size(); i++) {
            if(recordList.get(i).getCountry().getCountry_id()==country_id){
               recordRepository.deleteById(recordList.get(i).getRecord_id());
            }
        }

        countryRepository.save(country);
        return new RedirectView("/records");
    }
}
