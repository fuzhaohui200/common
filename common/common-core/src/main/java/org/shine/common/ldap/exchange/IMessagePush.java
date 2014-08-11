package org.shine.common.ldap.exchange;

public interface IMessagePush {

    /*
     * (non-Javadoc)
     *
     * @see important.IMessagePush#addMessage(java.lang.String,
     * java.lang.String, java.lang.String, int)
     */
    public void addMessage(String content, String url, String jid, int mark);

}