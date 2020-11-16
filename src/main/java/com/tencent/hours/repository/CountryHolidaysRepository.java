package com.tencent.hours.repository;

import com.tencent.hours.entity.CountryHolidays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ghj
 * @Description
 * @date 2020/11/5
 */
@Repository
public interface CountryHolidaysRepository extends JpaRepository<CountryHolidays, Long> {

    List<CountryHolidays> findByDate(LocalDate date);
}
