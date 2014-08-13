package org.shine.common.soap;

import org.w3c.dom.NodeList;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;

public class WSUtils {
    public static void main(String[] args) {
        String strURL = "http://localhost:8081/ControlAIX";
        String strHost = "182.2.183.186";
        String strUser = "root";
        String strPasswd = "root";
        String strCmd = "ls";
        String strCmdPrompt = "linux:~ #";
        String strThreadID = ExecuteTelnetCmd(strURL, strHost, strUser,
                strPasswd, strCmd, strCmdPrompt);

        System.out.println(GetCMDStatus(strURL, strThreadID));
    }

    public static String ExecuteTelnetCmd(String strServiceURL, String strHost, String strUsername, String strPasswd, String strCmd, String strCmdPrompt) {
        String SoapFormat = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://localhost:8081/ControlAIX\"><soapenv:Header/><soapenv:Body><con:paramsTelnetPwd><host>%s</host><user>%s</user><passwd>%s</passwd><command>%s</command><cmd_prompt>%s</cmd_prompt></con:paramsTelnetPwd></soapenv:Body></soapenv:Envelope>";

        String strSoapEnvelop = String.format(SoapFormat, new Object[]{strHost, strUsername,
                strPasswd, strCmd, strCmdPrompt});
        System.out.println("Send the following soap message to " +
                strServiceURL);
        System.out.println(strSoapEnvelop);
        try {
            MessageFactory mf = MessageFactory.newInstance();
            SOAPConnection con = SOAPConnectionFactory.newInstance()
                    .createConnection();

            SOAPMessage reqMessage = mf.createMessage(null,
                    new ByteArrayInputStream(strSoapEnvelop.getBytes()));

            URL endpoint = new URL(strServiceURL);
            MimeHeaders headers = reqMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", strServiceURL + "/TelnetCmd");
            SOAPMessage response = con.call(reqMessage, endpoint);

            NodeList nl = response.getSOAPBody()
                    .getElementsByTagName("returns");
            String ThreadID = nl.item(0).getTextContent();

            return ThreadID;
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String GetCMDResponse(String strServiceURL, String strThreadID) {
        String SoapFormat = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://localhost:8081/ControlAIX\"><soapenv:Header/><soapenv:Body><con:paramsGetCommandResponse><ThreadID>%s</ThreadID></con:paramsGetCommandResponse></soapenv:Body></soapenv:Envelope>";

        String strSoapEnvelop = String.format(SoapFormat, new Object[]{strThreadID});
        try {
            MessageFactory mf = MessageFactory.newInstance();
            SOAPConnection con = SOAPConnectionFactory.newInstance()
                    .createConnection();

            SOAPMessage reqMessage = mf.createMessage(null,
                    new ByteArrayInputStream(strSoapEnvelop.getBytes()));

            URL endpoint = new URL(strServiceURL);
            MimeHeaders headers = reqMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", strServiceURL +
                    "/GetCommandResponse");
            SOAPMessage response = con.call(reqMessage, endpoint);

            NodeList nl_CmdOutput = response.getSOAPBody()
                    .getElementsByTagName("CommandOutput");
            NodeList nl_CmdExitCode = response.getSOAPBody()
                    .getElementsByTagName("ExitCode");
            String strCmdOutput = nl_CmdOutput.item(0).getTextContent();
            String strCmdExitCode = nl_CmdExitCode.item(0).getTextContent();

            return strCmdOutput;
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String GetCMDStatus(String strServiceURL, String strThreadID) {
        String SoapFormat = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:con=\"http://localhost:8081/ControlAIX\"><soapenv:Header/><soapenv:Body><con:paramsGetCommandResponse><ThreadID>%s</ThreadID></con:paramsGetCommandResponse></soapenv:Body></soapenv:Envelope>";

        String strSoapEnvelop = String.format(SoapFormat, new Object[]{strThreadID});
        try {
            MessageFactory mf = MessageFactory.newInstance();
            SOAPConnection con = SOAPConnectionFactory.newInstance()
                    .createConnection();

            SOAPMessage reqMessage = mf.createMessage(null,
                    new ByteArrayInputStream(strSoapEnvelop.getBytes()));

            URL endpoint = new URL(strServiceURL);
            MimeHeaders headers = reqMessage.getMimeHeaders();
            headers.addHeader("SOAPAction", strServiceURL +
                    "/GetStatus");
            SOAPMessage response = con.call(reqMessage, endpoint);

            NodeList nl_CmdOutput = response.getSOAPBody()
                    .getElementsByTagName("returns");
            String strCmdOutput = nl_CmdOutput.item(0).getTextContent();

            return strCmdOutput;
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}