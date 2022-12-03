package com.example.Covid19.Repositories;

import com.example.Covid19.database.Countryfilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FilterRepository extends JpaRepository<Countryfilter,Long> {

    // https://www.bezkoder.com/jpa-repository-query/
    List<Countryfilter> findByDateGreaterThanEqual(Date date);
    List<Countryfilter> findByDateLessThanEqual(Date date);

         }
