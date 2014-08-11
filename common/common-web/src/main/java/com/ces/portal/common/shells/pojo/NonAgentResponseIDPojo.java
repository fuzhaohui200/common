package com.ces.portal.common.shells.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NON_AGENT_RESP_ID_LIST")
public class NonAgentResponseIDPojo {

    private long id;
    private String threadIdNum;
    private Date dateTime;
    private int status;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getThreadIdNum() {
        return threadIdNum;
    }

    public void setThreadIdNum(String threadIdNum) {
        this.threadIdNum = threadIdNum;
    }

    @Column
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Column
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
