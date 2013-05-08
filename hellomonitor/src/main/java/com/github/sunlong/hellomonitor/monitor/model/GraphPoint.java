package com.github.sunlong.hellomonitor.monitor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: sunlong
 * Date: 13-5-6
 * Time: 上午11:16
 */
@Entity
@JsonIgnoreProperties({"graph", "dataPoints"})
public class GraphPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 32, nullable = false)
    private String name;

    public static final String GRAPH_TYPE_LINE = "line";
    public static final String GRAPH_TYPE_AREA = "area";

    @Column(nullable = false, length = 4)
    private String type;

    private Boolean stacked = false;

    private Integer lineWidth = 1;

    @Column(length = 6)
    private String color;//RRGGBB形式

    @ManyToOne
    @JoinColumn(name = "graph_id")
    private Graph graph;

    @ManyToMany
    @JoinTable(name = "graph_data_point",
            joinColumns = @JoinColumn(name="graph_point_id"),
            inverseJoinColumns = @JoinColumn(name="data_point_id")
    )
    private Set<DataPoint> dataPoints = new HashSet<DataPoint>();

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

    public static String getGraphTypeLine() {
        return GRAPH_TYPE_LINE;
    }

    public static String getGraphTypeArea() {
        return GRAPH_TYPE_AREA;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStacked() {
        return stacked;
    }

    public void setStacked(Boolean stacked) {
        this.stacked = stacked;
    }

    public Integer getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(Integer lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(Set<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public void addDataPoint(DataPoint dataPoint) {
        dataPoint.getGraphPoints().add(this);
        dataPoints.add(dataPoint);
    }
}
