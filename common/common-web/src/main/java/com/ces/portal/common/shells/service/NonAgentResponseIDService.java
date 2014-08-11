package com.ces.portal.common.shells.service;

import com.ces.portal.common.shells.pojo.NonAgentResponseIDPojo;

import java.util.List;

public interface NonAgentResponseIDService {

    public void saveOrUpdate(NonAgentResponseIDPojo nonAgentResponseID);

    public List<NonAgentResponseIDPojo> queryAll();
}
