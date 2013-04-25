package com.github.sunlong.hellomonitor.monitor.service;

import com.github.sunlong.hellomonitor.common.MessageCode;
import com.github.sunlong.hellomonitor.common.SortBean;
import com.github.sunlong.hellomonitor.exception.AppException;
import com.github.sunlong.hellomonitor.monitor.dao.ITemplateDao;
import com.github.sunlong.hellomonitor.monitor.model.Template;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
 * Date: 13-4-25
 * Time: 上午10:04
 */
@Service
public class TemplateService {
    @Resource
    private ITemplateDao templateDao;

    @Resource
    private Validator validator;

    public Page<Template> list(int page, int pageSize, final Map<String, Object> params, SortBean sortBean) {
        Specification<Template> spec = new Specification<Template>() {
            @Override
            public Predicate toPredicate(Root<Template> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
                if(params != null){
                    List<Predicate> predicates = new ArrayList<Predicate>();

                    String name = (String) params.get("name");
                    if(StringUtils.isNotBlank(name)){
                        predicates.add(builder.equal(root.get("name"), name));
                    }
                    predicates.add(builder.isNull(root.get("device")));
                    return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                }
                return builder.conjunction();
            }
        };

        return templateDao.findAll(spec, new PageRequest(page - 1, pageSize, sortBean.genSort()));
    }

    public void create(Template template) throws AppException {
        template.validate(validator);
        //判断用户名是否存在
        if(templateDao.findByName(template.getName())!=null){
            throw new AppException(MessageCode.TEMPLATE_EXIST_ERROR, template.getName());
        }
        templateDao.save(template);

    }
}
