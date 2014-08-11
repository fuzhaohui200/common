package com.ces.portal.common.shells.pojo;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NON_AGENT_RESP_HISTORY")
public class NonAgentResponseHistoryPojo {

    private long id;
    private String threadIdNum;
    private String context;
    private Date dateTime;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getThreadIdNum() {
        return threadIdNum;
    }

    public void setThreadIdNum(String threadIdNum) {
        this.threadIdNum = threadIdNum;
    }

    @Column
    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Column
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
