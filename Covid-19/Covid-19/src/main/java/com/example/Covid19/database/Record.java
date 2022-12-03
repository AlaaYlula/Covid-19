package com.example.Covid19.database;

import jakarta.persistence.*;

/*
Entity to store the records which added by the user from the /countries end point and display on /records end point
 */
@Entity
public class Record {
    @Id
    @GeneratedValue//(strategy = GenerationType.IDENTITY)
    private Long record_id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "country_id", nullable = false)
    private Countrydata country;

    public Record(){

    }
    public Record(Countrydata country){
        this.country = country;
    }

    public Long getRecord_id() {
        return record_id;
    }


    public void setRecord_id(Long record_id) {
        this.record_id = record_id;
    }

    public Countrydata getCountry() {
        return country;
    }

    public void setCountry(Countrydata country) {
        this.country = country;
    }
}
