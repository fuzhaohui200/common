package com.ces.portal.common.shells.service;

import com.ces.portal.common.shells.pojo.NonAgentResponseHistoryPojo;

import java.util.List;

public interface NonAgentResponseHistoryService {

    public void saveOrUpdate(NonAgentResponseHistoryPojo nonAgentResponseHistory);

    public List<NonAgentResponseHistoryPojo> queryAll();
}
