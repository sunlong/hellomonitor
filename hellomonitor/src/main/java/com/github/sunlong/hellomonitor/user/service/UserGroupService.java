package com.github.sunlong.hellomonitor.user.service;

import com.github.sunlong.hellomonitor.common.Config;
import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.dao.IUserGroupDao;
import com.github.sunlong.hellomonitor.user.model.UserGroup;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.validation.Validator;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-2-5
 * Time: 上午9:02
 */
@Service
@Transactional(readOnly = true)
public class UserGroupService {
    @Resource
    private IUserGroupDao userGroupDao;

    @Resource
    private Validator validator;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void create(UserGroup userGroup) throws AppException {
        userGroup.setId(null);
        userGroup.validate(validator);
        //判断用户名是否存在
        if(userGroupDao.findByName(userGroup.getName())!=null){
            throw new AppException(MessageCode.USER_GROUP_EXIST_ERROR, userGroup.getName());
        }
        userGroupDao.save(userGroup);
    }

    public List<UserGroup> list(final Integer parentUserGroupId) {
        Specification<UserGroup> spec = new Specification<UserGroup>() {
            @Override
            public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path expression = root.get("parentUserGroup");
                if(parentUserGroupId == null){
                    return criteriaBuilder.isNull(expression);
                }else{
                    return criteriaBuilder.equal(expression, parentUserGroupId);
                }
            }
        };
        return userGroupDao.findAll(spec);
    }

    public boolean hasChildren(final Integer parentUserGroupId) {
        Specification<UserGroup> spec = new Specification<UserGroup>() {
            @Override
            public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("parentUserGroup"), parentUserGroupId);
            }
        };

        return userGroupDao.count(spec) > 0;
    }

    /**
     * 不能修改默认用户组名称
     * @param userGroup
     * @throws AppException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(UserGroup userGroup) throws AppException {
        UserGroup toSave = userGroupDao.findOne(userGroup.getId());
        if(toSave == null){
            throw new AppException(MessageCode.USER_GROUP_NOT_EXIST_ERROR, userGroup.getName());
        }

        if(toSave.getName().equals(Config.USER_GROUP_DEFAULT)){
            throw new AppException(MessageCode.USER_GROUP_UPDATE_DEFAULT_NAME_ERROR);
        }

        UserGroup entity = userGroupDao.findByName(userGroup.getName());
        if(entity!=null && !entity.getId().equals(userGroup.getId())){
            throw new AppException(MessageCode.USER_GROUP_EXIST_ERROR, userGroup.getName());
        }

        toSave.setName(userGroup.getName());
        toSave.validate(validator);

        userGroupDao.save(toSave);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer id) throws AppException {
        UserGroup entity = userGroupDao.findOne(id);
        if(entity == null){
            throw new AppException(MessageCode.USER_GROUP_NOT_EXIST_ERROR, id);
        }
        if(entity.getName().equals(Config.USER_GROUP_DEFAULT)){
            throw new AppException(MessageCode.USER_GROUP_DELETE_DEFAULT_ERROR, entity.getName());
        }
        delete(entity);
    }

    private void delete(UserGroup userGroup){
        List<UserGroup> subUserGroups = userGroup.getSubUserGroups();
        if(subUserGroups.size()>0){
            for(UserGroup tmp : subUserGroups){
                this.delete(tmp);
            }
        }
        userGroupDao.delete(userGroup);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void updateParent(Integer id, Integer parentId) throws AppException {
        UserGroup userGroup = userGroupDao.findOne(id);
        if(userGroup == null){
            throw new AppException(MessageCode.USER_GROUP_NOT_EXIST_ERROR, id);
        }

        UserGroup parent = userGroupDao.findOne(parentId);
        if(parent == null){
            throw new AppException(MessageCode.USER_GROUP_PARENT_NOT_EXIST_ERROR, parentId);
        }

        userGroup.setParentUserGroup(parent);
        userGroupDao.save(userGroup);
    }

    public UserGroup findDefaultUserGroup() {
        Specification<UserGroup> spec = new Specification<UserGroup>() {
            @Override
            public Predicate toPredicate(Root<UserGroup> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("name"), Config.USER_GROUP_DEFAULT);
            }
        };

        return userGroupDao.findOne(spec);
    }
}