package com.github.sunlong.hellomonitor.log.service;

import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.log.dao.IUserLogDao;
import com.github.sunlong.hellomonitor.log.model.UserLog;
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
 * Date: 13-2-28
 * Time: 上午9:07
 */
@Service
@Transactional(readOnly = true)
public class UserLogService {
    @Resource
    private IUserLogDao userLogDao;

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void create(UserLog userLog){
        userLogDao.save(userLog);
    }
    
    public Page<UserLog> list(int pageNumber, int pageSize, final Map<String, Object> params, SortBean sortBean) {
        Specification<UserLog> spec = new Specification<UserLog>() {
            @Override
            public Predicate toPredicate(Root<UserLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                if(params!=null){
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    String username = (String) params.get("username");
                    String ip = (String) params.get("ip");
                    String message = (String) params.get("message");
                    Boolean like = (Boolean) params.get("like");
                    if(StringUtils.isNotBlank(username)){
                        if(like == null || like){
                            predicates.add(builder.like(root.<String>get("username"), "%" + username + "%"));
                        }else{
                            predicates.add(builder.equal(root.<String>get("username"), username));
                        }
                    }
                    if(StringUtils.isNotBlank(ip)){
                        predicates.add(builder.equal(root.get("ip"), ip));
                    }
                    if(StringUtils.isNotBlank(message)){
                          predicates.add(builder.like(root.<String>get("message"), "%" + message + "%"));
                    }
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return builder.conjunction();
            }
        };

        return userLogDao.findAll(spec, new PageRequest(pageNumber - 1, pageSize, sortBean.genSort()));
    }
}
