package com.github.sunlong.hellomonitor.monitor.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-4-22
 * Time: 上午10:27
 */
@Entity
public class Graph {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    private Integer width;

    private Integer length;

    @ManyToOne
    @JoinColumn(name="template_id")
    private Template template;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "graph", orphanRemoval = true)
    private Set<GraphPoint> graphPoints = new HashSet<GraphPoint>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Set<GraphPoint> getGraphPoints() {
        return graphPoints;
    }

    public void setGraphPoints(Set<GraphPoint> graphPoints) {
        this.graphPoints = graphPoints;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    /**
     * 添加一个新的graph point
     * 此方法要在复制data source后执行
     */
    public void addGraphPoint(GraphPoint gp, Set<DataSource> dataSources) {
        GraphPoint tmp = new GraphPoint();
        tmp.setType(gp.getType());
        tmp.setName(gp.getName());
        tmp.setColor(gp.getColor());
        tmp.setGraph(this);
        tmp.setLineWidth(gp.getLineWidth());
        tmp.setStacked(gp.getStacked());

        //复制data point，设定graph point 与data point的关系
        //对于每一个data point，查看当前data source中的data points
        //如果存在一个相同的
        for(DataPoint dp: gp.getDataPoints()){
            for(DataSource ds : dataSources){
                for(DataPoint dp2: ds.getDataPoints()){
                    if(dp2.getName().equals(dp.getName())){//如果存在，建立双向关系
                        dp2.getGraphPoints().add(tmp);
                        tmp.getDataPoints().add(dp2);
                        break;
                    }
                }
            }
        }

        graphPoints.add(tmp);
    }
}
