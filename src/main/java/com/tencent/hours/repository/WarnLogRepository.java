package com.tencent.hours.repository;

import com.tencent.hours.entity.WarnLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Repository
public interface WarnLogRepository extends JpaRepository<WarnLog, Long> {
    /**
     * 分组查询告警概率
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @Query(value = "SELECT\n" +
            "\tt1.department ,\n" +
            "\tCOUNT( DISTINCT t2.p_account ) warnCount,\n" +
            "\tCOUNT( DISTINCT t1.p_account ) empCount \n" +
            "FROM\n" +
            "\tt_employee t1\n" +
            "\tLEFT JOIN t_warn_log t2 ON t1.p_account = t2.p_account \n" +
            "\tAND t2.lack_date BETWEEN :startDate \n" +
            "\tAND :endDate \n" +
            "GROUP BY\n" +
            "\tt1.department", nativeQuery = true)
    List<Map<String, Object>> findGroupByEmp(LocalDate startDate, LocalDate endDate);

    List<WarnLog> findByLackDateAndSendStatus(LocalDate now,Boolean sendStatus);

    /**
     * 获取日志补填数据
     * @return
     */
    @Query(value="SELECT\n" +
            "\tt1.id\n" +
            "FROM\n" +
            "\t`t_warn_log` t1\n" +
            "\tINNER JOIN t_hours_temp t2 ON t1.p_account = t2.p_account \n" +
            "\tAND t1.lack_date = t2.spend_date",nativeQuery = true)
    List<Map<String, Object>> findSupplement();

    void deleteAllById(List<Long> idList);


    @Query(value="SELECT\n" +
            "\t@num \\:= @num + 1 number,\n" +
            "\ta.department,\n" +
            "\ta.NAME,\n" +
            "\ta.lackDate \n" +
            "FROM\n" +
            "\t(\n" +
            "\tSELECT\n" +
            "\t\tt1.department department,\n" +
            "\t\tt1.NAME AS NAME,\n" +
            "\t\tt2.lack_date lackDate \n" +
            "\tFROM\n" +
            "\t\tt_employee t1\n" +
            "\t\tRIGHT JOIN t_warn_log t2 ON t1.p_account = t2.p_account \n" +
            "\tWHERE\n" +
            "\t\tt2.lack_date BETWEEN :startDate \n" +
            "\t\tAND :endDate \n" +
            "\tORDER BY\n" +
            "\t\tdepartment ASC \n" +
            "\t) AS a,\n" +
            "\t( SELECT @num \\:= 0 ) n",nativeQuery = true)
    List<Map<String, Object>> findEmpListByLackDate(@Param("startDate") LocalDate startDate, @Param("endDate")LocalDate endDate);
}
