package com.example.Covid19.Repositories;

import com.example.Covid19.database.AllCountryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AllCountryRepository extends JpaRepository<AllCountryData,Long> {
    AllCountryData findByCountry(String country);

}
