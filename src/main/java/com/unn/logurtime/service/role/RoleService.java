package com.unn.logurtime.service.role;

import com.unn.logurtime.model.Role;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface RoleService {
    Role addRole(Role role);
    @Secured({"ROLE_ADMIN"})
    Role getRole(Long id);
    void deleteRole(Role role);
    Role updateRole(Role role);
    List<Role> getAll();
}
