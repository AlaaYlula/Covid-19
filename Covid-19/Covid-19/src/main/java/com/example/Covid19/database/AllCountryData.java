package com.example.Covid19.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/*
 Entity To store the Names for All countries to let the user know the name of the countries which are in the API
 */
@Entity
public class AllCountryData {
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long country_id;

    String country;
    String country_slug;

    public AllCountryData(){

    }

    public AllCountryData(String country_name, String country_slug) {
        this.country = country_name;
        this.country_slug = country_slug;
    }

    public String getCountry_slug() {
        return country_slug;
    }

    public void setCountry_slug(String country_slug) {
        this.country_slug = country_slug;
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
}
