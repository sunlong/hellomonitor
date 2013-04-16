package com.github.sunlong.hellomonitor.common;

import org.springframework.data.domain.Sort;

/**
 * User: sunlong
 * Date: 13-4-12
 * Time: 下午2:16
 */
public class SortBean {
    private String sortName;
    private String sortDir = "ASC";

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    public Sort genSort(){
        if(sortName != null){
            return new Sort(Sort.Direction.fromString(sortDir), sortName);
        }
        return null;
    }
}
