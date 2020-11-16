package com.tencent.hours.repository;

import com.tencent.hours.entity.EmployeeLeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Gaohj
 * @package com.tencent.hours.repository
 * @Description
 * @date 2020/11/13 15:54
 */
@Repository
public interface EmployeeLeaderRepository extends JpaRepository<EmployeeLeader, Long> {
}
