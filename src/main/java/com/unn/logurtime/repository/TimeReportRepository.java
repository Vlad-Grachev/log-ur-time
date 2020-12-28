package com.unn.logurtime.repository;

import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.TimeReport;
import com.unn.logurtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TimeReportRepository extends JpaRepository<TimeReport, Long> {
    List<TimeReport> findByUser(User user);
    List<TimeReport> findByTask(Task task);
}
