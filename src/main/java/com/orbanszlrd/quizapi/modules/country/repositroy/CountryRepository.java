package com.orbanszlrd.quizapi.modules.country.repositroy;

import com.orbanszlrd.quizapi.modules.country.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
