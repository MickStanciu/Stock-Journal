package com.example.account.service;

import com.example.account.dao.RoleDao;
import com.example.account.model.RoleModel;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class RoleService {

    private static final Logger log = Logger.getLogger(RoleService.class);

    @Inject
    private RoleDao roleDao;

    public RoleModel getRole(String tenantId, int roleId) {
        return roleDao.getRole(tenantId, roleId);
    }
}
