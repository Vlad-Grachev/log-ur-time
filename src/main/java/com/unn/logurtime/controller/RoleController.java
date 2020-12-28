package com.unn.logurtime.controller;

import com.unn.logurtime.model.Role;
import com.unn.logurtime.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/role/{roleId}")
    Role getRoleById(@PathVariable Long roleId){
        return roleService.getRole(roleId);
    }
}
