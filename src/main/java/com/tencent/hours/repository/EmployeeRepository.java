package com.tencent.hours.repository;

import com.tencent.hours.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ghj
 * @Description
 * @date 2020/11/3
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findBypAccountIn(List<String> pAccountList);
}
