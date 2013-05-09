package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * User: sunlong
 * Date: 13-4-23
 * Time: 上午10:23
 */
@Entity
public class CommandDataSource extends DataSource{
    @Column(length = 64)
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void copy(DataSource dataSource){
        super.copy(dataSource);
        CommandDataSource commandDataSource = (CommandDataSource)dataSource;
        this.command = commandDataSource.getCommand();
    }

    @Override
    public void collect(DeviceProperty deviceProperty){

    }

    @Override
    public CommandDataSource clone() throws CloneNotSupportedException {
        return (CommandDataSource)super.clone();
    }
}
