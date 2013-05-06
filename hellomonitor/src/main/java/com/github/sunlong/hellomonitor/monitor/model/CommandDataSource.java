package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.Entity;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:23
 */
@Entity
public class CommandDataSource extends DataSource{
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
