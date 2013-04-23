package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.monitor.dao.IDeviceDao;
import com.github.sunlong.hellomonitor.monitor.model.Device;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午11:07
 */
@Service
@Transactional(readOnly = true)
public class DeviceService {
    private IDeviceDao deviceDao;

    @Resource
    public void setDeviceDao(IDeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    public Page<Device> list(int page, int pageSize, final Map<String, Object> params, SortBean sortBean) {
        Specification<Device> spec = new Specification<Device>() {
            @Override
            public Predicate toPredicate(Root<Device> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                if(params != null){
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    String ip = (String) params.get("ip");
                    if(StringUtils.isNotBlank(ip)){
                        predicates.add(builder.equal(root.get("ip"), ip));
                    }
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return builder.conjunction();
            }
        };

        return deviceDao.findAll(spec, new PageRequest(page - 1, pageSize, sortBean.genSort()));
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void create(Device device) {
        device.validate();
        deviceDao.save(device);
    }
}
