package com.unn.logurtime.repository;

import com.unn.logurtime.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //@Query(value = "SELECT * FROM roles WHERE name = :newRole", nativeQuery = true)
    Role getRoleByName(@Param("name") String name);
}