package com.example.Covid19.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.util.Date;

/*
Entity to store the information of  All countries to display to the user in the /countries end point
 */
@Entity
public class Countrydata {

    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long country_id;

    private String country;
    private int totalConfirmed;
    private int totalDeaths;
    private int totalRecovered;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;

    private String dateString;
    // https://attacomsian.com/blog/spring-data-jpa-one-to-one-mapping
//    @OneToOne(mappedBy = "country", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    Record record;

    public Countrydata(){

    }
    public Countrydata(String country, int totalConfirmed, int totalDeaths, int totalRecovered, Date date,String dateString){
        this.country = country;
        this.totalConfirmed = totalConfirmed;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.date = date;
        this.dateString = dateString;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Long getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Long country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(int totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
