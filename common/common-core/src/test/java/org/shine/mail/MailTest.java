package org.shine.mail;

public class MailTest {

    public static void main(String[] args) {
        // 这个类主要是设置邮件
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("fuzhaohui202@163.com");
        mailInfo.setPassword("shinelog93");// 您的邮箱密码
        mailInfo.setFromAddress("fuzhaohui202@163.com");
        mailInfo.setToAddress("fuzhaohui200@gmail.com");
        mailInfo.setSubject("设置邮箱标题 如http://www.guihua.org 中国桂花网");
        mailInfo.setContent("设置邮箱内容 如http://www.guihua.org 中国桂花网 是中国最大桂花网站==");
        // 这个类主要来发送邮件
        SimpleMailSender sms = new SimpleMailSender();
        sms.sendTextMail(mailInfo);// 发送文体格式
        sms.sendHtmlMail(mailInfo);// 发送html格式
    }

}
