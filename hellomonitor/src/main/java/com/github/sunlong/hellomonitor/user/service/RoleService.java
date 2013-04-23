package com.github.sunlong.hellomonitor.user.service;

import com.github.sunlong.hellomonitor.common.Config;
import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.dao.IRoleDao;
import com.github.sunlong.hellomonitor.user.model.Permission;
import com.github.sunlong.hellomonitor.user.model.Role;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-2-2
 * Time: 上午11:20
 */
@Service
@Transactional(readOnly = true)
public class RoleService {
    @Resource
    private IRoleDao roleDao;
    @Resource
    private Validator validator;

    public Page<Role> list(int pageNumber, int pageSize) {
        Specification<Role> spec = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.conjunction();
            }
        };

        return roleDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize));
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void create(Role role) throws AppException {
        if(roleDao.findByName(role.getName())!=null){
            throw new AppException(MessageCode.ROLE_EXIST_ERROR, role.getName());
        }
        role.setDefault(false);
        role.validate(validator);
        roleDao.save(role);
    }

    /**
     * 默认角色不能删除
     * @param ids
     * @throws AppException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer[] ids) throws AppException {
        for(Integer id: ids){
            Role role = roleDao.findOne(id);
            if(role == null){
                throw new AppException(MessageCode.ROLE_NOT_EXIST_ERROR, id);
            }
            if(role.getDefault()){
                throw new AppException(MessageCode.ROLE_DELETE_DEFAULT_ERROR, role.getName());
            }
            roleDao.delete(id);
        }
    }

    /**
     * 默认角色不能修改名称
     * @param role
     * @throws AppException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(Role role) throws AppException {
        Role entity = roleDao.findByName(role.getName());
        if(entity!=null && !entity.getId().equals(role.getId())){
            throw new AppException(MessageCode.ROLE_EXIST_ERROR, role.getName());
        }

        Role toSave = roleDao.findOne(role.getId());
        if(toSave.getDefault()){
            throw new AppException(MessageCode.ROLE_UPDATE_DEFAULT_ROLE_NAME_ERROR);
        }
        toSave.setName(role.getName());
        toSave.setDescription(role.getDescription());

        toSave.validate(validator);

        roleDao.save(toSave);
    }

    public Role find(Integer id) throws AppException {
        Role role = roleDao.findOne(id);
        if(role == null){
            throw new AppException(MessageCode.ROLE_NOT_EXIST_ERROR, id);
        }
        Hibernate.initialize(role.getPermissions());
        return role;
    }

    public List<Role> listAll() {
        return (List<Role>)roleDao.findAll();
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updatePermission(Role role) throws AppException {
        Role entity = roleDao.findOne(role.getId());
        if(entity == null){
            throw new AppException(MessageCode.ROLE_NOT_EXIST_ERROR, role);
        }

        List<Permission> oldPermissions = entity.getPermissions();
        List<Permission> newPermissions = role.getPermissions();
        List<Permission> toAdd = new ArrayList<Permission>();
        List<Permission> toRemove = new ArrayList<Permission>();

        boolean exist;
        for(Permission permission: oldPermissions){
            exist = false;
            for(Permission newPermission: newPermissions){
                if(permission.getName().equals(newPermission.getName())){
                    exist = true;
                    break;
                }
            }
            if(!exist){
                toRemove.add(permission);
            }
        }

        if(newPermissions != null){
            for(Permission newPermission: newPermissions){
                exist = false;
                for(Permission permission: oldPermissions){
                    if(newPermission.getName().equals(permission.getName())){
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    newPermission.setRole(entity);
                    toAdd.add(newPermission);
                }
            }
        }

        oldPermissions.addAll(toAdd);
        oldPermissions.removeAll(toRemove);

        roleDao.save(entity);
    }

    public Role findDefaultRole() {
        Specification<Role> spec = new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), Config.ROLE_DEFAULT_NORMAL);
            }
        };

        return roleDao.findOne(spec);
    }
}
