package com.unn.logurtime.repository;

import com.unn.logurtime.model.Task;
import com.unn.logurtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(String status);

//    @Query(value = "SELECT * FROM tasks t WHERE t.creation_date > :start_date AND t.creation_date < :end_date",
//            nativeQuery = true)
    List<Task> findByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

//    @Query(value = "SELECT * FROM tasks t WHERE t.due_date > :start_date AND t.due_date < :end_date",
//            nativeQuery = true)
    List<Task> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Task> findByDueDateBefore(LocalDateTime date);

    List<Task> findByAssigneeIsNull();

    List<Task> findByCreator(User creator);

    List<Task> findByAssignee(User assignee);
}
