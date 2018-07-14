package com.example.accountapi.service;

import com.example.account.model.RoleModel;
import com.example.accountapi.repository.RoleRepository;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RoleService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AccountService.class);

    private RoleRepository roleRepository;

    @Inject
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleModel getRole(String tenantId, int roleId) {
        return roleRepository.getRole(tenantId, roleId);
    }
}
