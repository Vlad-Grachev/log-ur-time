package com.unn.logurtime.repository;

import com.unn.logurtime.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    //User findByUsername(String username);
    //@Query("select u from User u where u.username = :username") - hql
    //@Query(value = "SELECT * FROM users u WHERE u.username = :username", nativeQuery = true)
    User findByUsername(String username);
}
