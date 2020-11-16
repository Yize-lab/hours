package com.tencent.hours.repository;

import com.tencent.hours.entity.HoursTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Repository
public interface HoursTempRepository extends JpaRepository<HoursTemp, Long> {
    /**
     * 获取员工最近一次填报工时日期
     *
     * @return
     */
    @Query(value = "SELECT \n" +
            "t1.p_account pAccount,\n" +
            "t1.department,\n" +
            "t1.`name`,\n" +
            "t1.email,\n" +
            "MAX(t2.spend_date) lastSpendDate\n" +
            "FROM\n" +
            "\tt_employee t1\n" +
            "\tLEFT JOIN t_hours_temp t2 ON t1.p_account = t2.p_account\n" +
            "\tGROUP BY t1.p_account", nativeQuery = true)
    List<Map<String, String>> findLastSpendDate();
}
