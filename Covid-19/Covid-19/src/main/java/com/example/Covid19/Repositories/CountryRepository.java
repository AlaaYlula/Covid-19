package com.example.Covid19.Repositories;

import com.example.Covid19.database.Countrydata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Countrydata,Long> {
}
