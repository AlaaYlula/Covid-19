package com.example.Covid19.BluePrint.Countries;

import java.util.Date;
/*
Blueprint for API  https://api.covid19api.com/summary
to make the /countries endpoint
 */
public class Country {
    public String Country;
    public int TotalConfirmed;
    public int TotalDeaths;
    public int TotalRecovered;
    public Date Date;
}
