//package com.twjitm.email;////import javax.mail.Authenticator;//import javax.mail.PasswordAuthentication;//import javax.mail.Session;//import javax.mail.Transport;//import javax.mail.internet.InternetAddress;//import javax.mail.internet.MimeMessage;//import java.util.ArrayList;//import java.util.Date;//import java.util.List;//import java.util.Properties;/////**// * @author twjitm - [Created on 2018-08-24 12:16]// * @company https://github.com/twjitm/// * @jdk java version "1.8.0_77"// *///public class MailServer {////    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com//    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com//    public static String myEmailSMTPHost = "smtp.163.com";////    public static void main(String[] args) {//        MailMessage mailMessage = new MailMessage();//        mailMessage.setContext("我交了电费");//        List<User> list = new ArrayList<User>();//        User user = new User();//        user.setEmail("1603502649@qq.com");//        list.add(user);//        sendMail(mailMessage, list);//    }////    // 收件人邮箱（替换为自己知道的有效邮箱）//    public static boolean  sendMail(MailMessage mailMessage, List<User> users) {//        boolean success=true;//        ClassLoader clzzload = Thread.currentThread().getContextClassLoader();//        Properties props = new Properties();//        try {//            props.init(clzzload.getResourceAsStream("mail/mail.properties"));//            final String myEmailAccount = props.getProperty("myEmailAccount");//            String myEmailPassword = props.getProperty("myEmailPassword");//            final String authCode = props.getProperty("authCode");//            Session session = Session.getInstance(props, new Authenticator() {//                @Override//                protected PasswordAuthentication getPasswordAuthentication() {//                    return new PasswordAuthentication(myEmailAccount, authCode);//                }//            });//            session.setDebug(true);////            // 设置为debug模式, 可以查看详细的发送 log//            MimeMessage message = createMineContext(session, myEmailAccount, users, mailMessage);//            // 4. 根据 Session 获取邮件传输对象//            Transport transport = null;//            transport = session.getTransport();//            // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错//            ////            //    PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,//            //           仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误//            //           类型到对应邮件服务器的帮助网站上查看具体失败原因。//            //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码://            //           (1) 邮箱没有开启 SMTP 服务;//            //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;//            //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;//            //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;//            //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。//            ////            //    PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。//            transport.connect(myEmailAccount, myEmailPassword);////            // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人//            transport.sendMessage(message, message.getAllRecipients());//            // 7. 关闭连接//            transport.close();//        } catch (Exception e) {//            success=false;//            e.printStackTrace();//        }//        return success;////    }////    private static MimeMessage createMineContext(Session session, String sendMail, List<User> users, MailMessage context) {////        // 1. 创建一封邮件//        MimeMessage message = new MimeMessage(session);////        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）//        try {//            message.setFrom(new InternetAddress(sendMail, "602小助手", "UTF-8"));////            // 3. To: 收件人（可以增加多个收件人、抄送、密送）//            InternetAddress[] addresses = new InternetAddress[users.size()];//            for (int i = 0; i < users.size(); i++) {//                addresses[i] = new InternetAddress(users.get(i).getEmail(), users.get(i).getUsername(), "UTF-8");//            }//            message.setRecipients(MimeMessage.RecipientType.TO, addresses);//            // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）//            message.setSubject("新增采购记录", "UTF-8");////            // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）//            message.setContent(context.getContext(), "text/html;charset=UTF-8");//            // 6. 设置发件时间//            message.setSentDate(new Date());//            // 7. 保存设置//            message.saveChanges();//        } catch (Exception e) {//            e.printStackTrace();//        }//        return message;//    }////}