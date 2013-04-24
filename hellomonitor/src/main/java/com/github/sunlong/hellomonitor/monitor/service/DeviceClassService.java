package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.monitor.dao.IDeviceClassDao;
import com.github.sunlong.hellomonitor.monitor.model.DeviceClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-4-24
 * Time: 上午9:23
 */
@Service
@Transactional(readOnly = true)
public class DeviceClassService {
    @Resource
    private IDeviceClassDao deviceClassDao;

    public List<DeviceClass> list(final Integer parentId) {
        Specification<DeviceClass> spec = new Specification<DeviceClass>() {
            @Override
            public Predicate toPredicate(Root<DeviceClass> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path expression = root.get("parent");
                if(parentId == null){
                    return criteriaBuilder.isNull(expression);
                }else{
                    return criteriaBuilder.equal(expression, parentId);
                }
            }
        };
        return deviceClassDao.findAll(spec);
    }

    public boolean hasChildren(final Integer parentId) {
        Specification<DeviceClass> spec = new Specification<DeviceClass>() {
            @Override
            public Predicate toPredicate(Root<DeviceClass> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("parent"), parentId);
            }
        };

        return deviceClassDao.count(spec) > 0;
    }
}
