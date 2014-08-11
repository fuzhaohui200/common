package com.ces.portal.common.shells.service;

import com.ces.portal.common.shells.pojo.ShellScriptPojo;

import java.util.List;

public interface ShellScriptService {

    public void add(ShellScriptPojo shellScriptPojo);

    public void update(ShellScriptPojo shellScriptPojo);

    public void delete(long shellScriptPojoId);

    public ShellScriptPojo queryShellScriptPojoById(long shellScriptId);

    public List<ShellScriptPojo> queryShellScriptPojosByGroupId(String groupId);

    public List<ShellScriptPojo> queryAllShellScriptsByFileName(String fileName, String groupId);

    public List<Object[]> queryAllShellScriptFileNames();


}
