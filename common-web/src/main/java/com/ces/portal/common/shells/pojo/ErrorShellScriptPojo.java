package com.ces.portal.common.shells.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_errorshellscript")
public class ErrorShellScriptPojo {

    private long scriptId;
    private String errorMessage;
    private String fileName;
    private String groupId;
    private Date dateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public long getScriptId() {
        return scriptId;
    }

    public void setScriptId(long scriptId) {
        this.scriptId = scriptId;
    }

    @Column
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Column
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
