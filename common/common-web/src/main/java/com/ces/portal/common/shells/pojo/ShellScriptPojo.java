package com.ces.portal.common.shells.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_shellscript")
public class ShellScriptPojo {

    private long scriptId;
    private long sequence;
    private String fileName;
    private Date lastExcuteTime;
    private String groupId;
    private String shellCommand;
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

    @Column(name = "scriptSeq")
    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    @Column
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Column
    public Date getLastExcuteTime() {
        return lastExcuteTime;
    }

    public void setLastExcuteTime(Date lastExcuteTime) {
        this.lastExcuteTime = lastExcuteTime;
    }

    @Column
    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Column(length = 4000)
    public String getShellCommand() {
        return shellCommand;
    }

    public void setShellCommand(String shellCommand) {
        this.shellCommand = shellCommand;
    }

    @Column
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
