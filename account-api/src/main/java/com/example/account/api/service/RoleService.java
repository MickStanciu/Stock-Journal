package com.example.account.api.service;

import com.example.account.api.repository.RoleRepository;
import com.example.account.api.spec.model.RoleModel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AccountService.class);

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleModel getRole(String tenantId, int roleId) {
        return roleRepository.getRole(tenantId, roleId);
    }
}
