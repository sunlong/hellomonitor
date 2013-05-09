package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.IDeviceDao;
import com.github.sunlong.hellomonitor.monitor.model.Device;
import com.github.sunlong.hellomonitor.monitor.model.DeviceClass;
import com.github.sunlong.hellomonitor.monitor.model.Template;
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
import java.util.*;

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
    private DeviceClassService deviceClassService;

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
    public void create(Device device) throws AppException {
        device.validate();
        DeviceClass deviceClass = deviceClassService.find(device.getDeviceClass().getId());
        Set<Template> templates = deviceClass.getTemplates();
        for(Template template: templates){
            device.addTemplate(template);
        }
        deviceDao.save(device);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void update(Device device) throws AppException {
        Device toUpdate = find(device.getId());

        Device deviceByIp = deviceDao.findByIp(device.getDeviceProperty().getIp());

        if(deviceByIp != null && deviceByIp != toUpdate){
            throw new AppException(MessageCode.DEVICE_EXIST_ERROR, device.getDeviceProperty().getIp());
        }

        Device deviceByName = deviceDao.findByName(device.getName());
        if(deviceByName != null && deviceByName != toUpdate){
            throw new AppException(MessageCode.DEVICE_EXIST_ERROR, device.getName());
        }

        toUpdate.copy(device);
        deviceDao.save(toUpdate);
    }

    public Device find(Integer id) throws AppException {
        Device device = deviceDao.findOne(id);
        if(device == null){
            throw new AppException(MessageCode.DEVICE_NOT_EXIST_ERROR, id);
        }
        return device;
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Integer id) throws AppException {
        Device device = find(id);
        deviceDao.delete(device);
    }

    public List<Device> listAll() {
        ArrayList<Device> devices = new ArrayList<Device>();
        for (Device device : deviceDao.findAll()) {
            devices.add(device);
        }
        return devices;
    }
}
