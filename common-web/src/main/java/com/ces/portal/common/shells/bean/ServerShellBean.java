package com.ces.portal.common.shells.bean;

import java.util.HashMap;
import java.util.Map;

public class ServerShellBean {


    private String ipAddress;
    private String name;
    private String telnetCmdPrompt;
    private String username;
    private String password;
    private String description;
    private String label;
    private Map<String, String> shellScript = new HashMap<String, String>();

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelnetCmdPrompt() {
        return telnetCmdPrompt;
    }

    public void setTelnetCmdPrompt(String telnetCmdPrompt) {
        this.telnetCmdPrompt = telnetCmdPrompt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getShellScript() {
        return shellScript;
    }

    public void setShellScript(Map<String, String> shellScript) {
        this.shellScript = shellScript;
    }


}
