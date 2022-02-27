package com.xsz.system.dao;

import com.xsz.common.config.MyMapper;
import com.xsz.system.domain.Role;
import com.xsz.system.domain.RoleWithMenu;

import java.util.List;


public interface RoleMapper extends MyMapper<Role> {
	
	List<Role> findUserRole(String userName);
	
	List<RoleWithMenu> findById(Long roleId);
}