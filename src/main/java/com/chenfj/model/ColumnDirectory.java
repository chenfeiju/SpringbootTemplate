package com.chenfj.model;

import java.util.Date;

public class ColumnDirectory {
    private Integer id;

    private String directoryId;

    private String columnId;

    private String title;

    private Boolean trialRead;

    private Boolean del;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(String directoryId) {
        this.directoryId = directoryId == null ? null : directoryId.trim();
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId == null ? null : columnId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getTrialRead() {
        return trialRead;
    }

    public void setTrialRead(Boolean trialRead) {
        this.trialRead = trialRead;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}