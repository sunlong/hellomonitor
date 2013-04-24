package com.github.sunlong.hellomonitor.common;

import com.github.sunlong.hellomonitor.user.model.Action;
import com.github.sunlong.hellomonitor.user.model.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * User: sunlong
 * Date: 13-2-5
 * Time: 下午3:16
 */
public class TreeNode {
    private String name;
    private boolean isParent;//if it is leaf, isParent is true
    private String value;
    private boolean open;
    private List<TreeNode> children;

    public TreeNode(String value, String name ,boolean hasChildren) {
        this.name = name;
        this.isParent = hasChildren;
        this.value = value;
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode() {
    }

    public TreeNode(Action action) {
        this.name = action.getName();
        this.isParent = false;
        this.value = action.getId().toString();
        this.children = new ArrayList<TreeNode>();
    }

    public TreeNode(Resource resource) {
        this.name = resource.getName();
        this.isParent = true;
        this.value = resource.getId().toString();
        this.children = new ArrayList<TreeNode>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
