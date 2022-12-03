package com.example.Covid19.BluePrint;

import java.util.Date;
/*
Blueprint for reading from API end point as : https://api.covid19api.com/country/south-africa/status/confirmed?from=2020-03-01T00:00:00Z&to=2020-04-01T00:00:00Z
 to let the user search between the dates
 */
public class Filter {
        public String Country;
        public int Cases;
        public Date Date;
}
