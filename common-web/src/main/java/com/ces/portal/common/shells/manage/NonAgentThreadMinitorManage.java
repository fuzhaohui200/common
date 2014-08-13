package com.ces.portal.common.shells.manage;

import com.ces.portal.common.shells.pojo.NonAgentResponseHistoryPojo;
import com.ces.portal.common.shells.pojo.NonAgentResponseIDPojo;
import com.ces.portal.common.shells.service.NonAgentResponseHistoryService;
import com.ces.portal.common.shells.service.NonAgentResponseIDService;
import com.ces.portal.common.shells.service.impl.NonAgentResponseHistoryServiceImpl;
import com.ces.portal.common.shells.service.impl.NonAgentResponseIDServiceImpl;

import java.util.Date;
import java.util.List;

public class NonAgentThreadMinitorManage {

    private static NonAgentResponseIDService nonAgentResponseIDService = new NonAgentResponseIDServiceImpl();
    private static NonAgentResponseHistoryService nonAgentResponseHistoryService = new NonAgentResponseHistoryServiceImpl();

    public static void nonAgentThreadHandle() {
        List<NonAgentResponseIDPojo> nonAgentResponseList = nonAgentResponseIDService.queryAll();
        for (NonAgentResponseIDPojo nonAgentResponseID : nonAgentResponseList) {
            String context = ""; // 调用webService接口
            NonAgentResponseHistoryPojo nonAgentResponseHistory = new NonAgentResponseHistoryPojo();
            nonAgentResponseHistory.setThreadIdNum(nonAgentResponseID.getThreadIdNum());
            nonAgentResponseHistory.setContext(context);
            nonAgentResponseHistory.setDateTime(new Date());
            nonAgentResponseHistoryService.saveOrUpdate(nonAgentResponseHistory);
            nonAgentResponseID.setStatus(1);
            nonAgentResponseIDService.saveOrUpdate(nonAgentResponseID);
        }
    }

}
