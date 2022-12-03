package com.example.Covid19.database;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;

/*
Entity to store the result of the country dates statistics information searched by the user
 */
@Entity
public class Countryfilter {
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long country_id;

    private String country;
    private int confirmedCases;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private Date date;
//    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss.SSS",shape = JsonFormat.Shape.STRING)
    private String dateString;
    public Countryfilter() {
    }

    public Countryfilter(String country, int confirmedCases, Date date,String dateString) {
        this.country = country;
        this.confirmedCases = confirmedCases;
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

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
