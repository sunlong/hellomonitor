package com.github.sunlong.hellomonitor.log.dao;

import com.github.sunlong.hellomonitor.log.model.UserLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: sunlong
 * Date: 13-2-28
 * Time: 上午9:07
 */
public interface IUserLogDao extends PagingAndSortingRepository<UserLog, Integer>, JpaSpecificationExecutor<UserLog> {
	UserLog findByUsername(String username);
}
