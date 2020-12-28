package com.unn.logurtime.service.role;

import com.unn.logurtime.model.Role;
import com.unn.logurtime.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getRole(Long id) {
        return roleRepository.getOne(id);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
