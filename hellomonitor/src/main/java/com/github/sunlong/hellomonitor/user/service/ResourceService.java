package com.github.sunlong.hellomonitor.user.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.user.dao.IActionDao;
import com.github.sunlong.hellomonitor.user.dao.IResourceDao;
import com.github.sunlong.hellomonitor.user.model.Action;
import com.github.sunlong.hellomonitor.user.model.Resource;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * User: sunlong
 * Date: 13-2-18
 * Time: 上午10:18
 */
@Service
@Transactional(readOnly = true)
public class ResourceService {
    @javax.annotation.Resource
    private IResourceDao resourceDao;

    @javax.annotation.Resource
    private IActionDao actionDao;

    @javax.annotation.Resource
    private Validator validator;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void save(Resource resource) throws AppException {
        if(resourceDao.findByName(resource.getName())!=null){
            throw new AppException(MessageCode.RESOURCE_EXIST_ERROR, resource.getName());
        }
        if(resource.getActions()!=null){
            for(Action action: resource.getActions()){
                action.setResource(resource);
            }
        }
        resource.validate(validator);
        resourceDao.save(resource);
    }

    public Page<Resource> list(int pageNumber, int pageSize, final Map<String, Object> params) {
        Specification<Resource> spec = new Specification<Resource>() {
            @Override
            public Predicate toPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            	if(params != null){
            		List<Predicate> predicates = new ArrayList<Predicate>();

                    String name = (String) params.get("name");
                    if(StringUtils.isNotBlank(name)){
                        predicates.add(criteriaBuilder.like(root.<String>get("name"), "%" + name + "%"));
                    }
     
                    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            	}
                return criteriaBuilder.conjunction();
            }
        };    
        return resourceDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize));
    }

    public Resource find(Integer id) throws AppException {
        Resource resource = resourceDao.findOne(id);
        if(resource == null){
            throw new AppException(MessageCode.RESOURCE_NOT_EXIST_ERROR, id);
        }
        Hibernate.initialize(resource.getActions());
        return resource;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(Resource resource) throws AppException {
        Resource entity = resourceDao.findByName(resource.getName());
        if(entity!=null && !entity.getId().equals(resource.getId())){
            throw new AppException(MessageCode.RESOURCE_EXIST_ERROR, resource.getName());
        }

        Resource toSave = resourceDao.findOne(resource.getId());
        toSave.setName(resource.getName());
        toSave.setDescription(resource.getDescription());
        //更新action
        List<Action> actions = toSave.getActions();
        List<Action> newActions = resource.getActions();
        List<Action> toAdd = new ArrayList<Action>();
        List<Action> toRemove = new ArrayList<Action>();
        boolean exist;
        for(Action action: actions){
            exist = false;
            for(Action newAction: newActions){
                if(action.getName().equals(newAction.getName())){
                    exist = true;
                    break;
                }
            }
            if(!exist){
                toRemove.add(action);
            }
        }
        if(newActions != null){
            for(Action newAction: newActions){
                exist = false;
                for(Action action: actions){
                    if(newAction.getName().equals(action.getName())){
                        exist = true;
                        break;
                    }
                }
                if(!exist){
                    newAction.setResource(toSave);
                    toAdd.add(newAction);
                }
            }
        }

        actions.addAll(toAdd);
        actions.removeAll(toRemove);

        toSave.validate(validator);

        resourceDao.save(toSave);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer[] ids) throws AppException {
        for(Integer id: ids){
            if(resourceDao.findOne(id) == null){
                throw new AppException(MessageCode.RESOURCE_NOT_EXIST_ERROR, id);
            }
            resourceDao.delete(id);
        }
    }

    public List<Resource> listAll() {
        return (List<Resource>)resourceDao.findAll();
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void addAction(Action action) throws AppException {
        Resource resource = resourceDao.findOne(action.getResource().getId());
        if(resource == null){
            throw new AppException(MessageCode.RESOURCE_NOT_EXIST_ERROR, action.getResource().getId());
        }

        List<Action> actions = resource.getActions();
        for(Action tmp: actions){//判读action是否已经存在
            if(tmp.getName().equals(action.getName())){
                throw new AppException(MessageCode.ACTION_EXIST_ERROR, action.getName());
            }
        }
        actions.add(action);
        resourceDao.save(resource);
    }

    /**
     * @param actionId
     * @throws AppException
     */
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void deleteAction(Integer actionId) throws AppException {
        Action action = actionDao.findOne(actionId);
        if(action == null){
            throw new AppException(MessageCode.ACTION_NOT_EXIST_ERROR, actionId);
        }

        actionDao.delete(action);
    }
}
