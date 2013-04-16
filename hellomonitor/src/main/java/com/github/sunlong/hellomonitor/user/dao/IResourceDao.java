package com.github.sunlong.hellomonitor.user.dao;

import com.github.sunlong.hellomonitor.user.model.Resource;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: sunlong
 * Date: 13-2-18
 * Time: 上午10:18
 */
public interface IResourceDao extends PagingAndSortingRepository<Resource, Integer>, JpaSpecificationExecutor<Resource> {
    Resource findByName(String name);
}
