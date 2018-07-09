package mail;

import java.net.URLDecoder;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.internet.MimeMessage.RecipientType;

import mail.MyAuthenticator;

public class MailUtil {
	private static Properties prop = new Properties();
	private static String user = "**@163.com"; // 登录邮箱的用户名
	private static String pwd = "*******"; // 登录邮箱的密码
	
	private static String from ="**@163.com"; // 发件人
	private static String to ="**@163.com";  // 收件人
	private static String cc ="**@163.com";  // 抄送人

	static {
		// 设置连接邮件服务器的参数
		prop.put("mail.transport.protocol", "smtp");	// 指定协议
		prop.put("mail.smtp.host", "smtp.163.com");		// 主机  
		prop.put("mail.smtp.port", 25);					// 端口
		prop.put("mail.smtp.auth", "true");				// 用户密码认证
	}
	
	/**
	 * 1，纯文本邮件
	 */
	public static void sendEmail() throws Exception {
		//创建会话对象
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user,pwd));
		//邮件对象
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(from));
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		message.setRecipient(RecipientType.CC, new InternetAddress(cc));
		message.setSentDate(new Date());
		message.setSubject("测试纯文本邮件");
		message.setText("这个是一个纯文本邮件");
		message.saveChanges();
		
		// 发送邮件
		Transport.send(message);
	}
	

	/**
	 * 2，发送html格式邮件
	 */
	public static void sendHtmlEmail() throws Exception {
		//创建会话对象
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user,pwd));
		//邮件对象
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		message.setRecipient(RecipientType.CC, new InternetAddress(cc));
		message.setSentDate(new Date());
		message.setSubject("测试html格式邮件");
		
		// 构造html格式文本
		StringBuilder builder = new StringBuilder();
		builder.append("<h1>百度一下</h1>");
		builder.append("<a href='http://www.baidu.com'>百度一下</a>");
		builder.append("<br>");

		message.setContent(builder.toString(), "text/html;charset=utf-8");
		message.saveChanges();
		// 发送
		Transport.send(message);
	}
	
	/**
	 * html + img 格式邮件
	 */
	public static void sendHtmlAndImgEmail() throws Exception {
		// 创建会话对象
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user, pwd));
		// 邮件对象
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		message.setRecipient(RecipientType.CC, new InternetAddress(cc));
		message.setSentDate(new Date());
		message.setSubject("测试图片邮件");
		message.setSentDate(new Date());

		// 设置多功能邮件
		MimeMultipart multipart = new MimeMultipart("related");
		message.setContent(multipart);

		// 邮件内容： html + 文本
		MimeBodyPart body = new MimeBodyPart();
		// 邮件“内嵌资源”
		MimeBodyPart source = new MimeBodyPart();

		// 添加邮件内容，到多功能用途邮件
		multipart.addBodyPart(body);
		multipart.addBodyPart(source);

		// 设置资源
		DataSource ds = new FileDataSource(MailUtil.class.getResource("example/angelababy.jpeg").getPath());
		DataHandler handler = new DataHandler(ds);
		source.setDataHandler(handler);
		source.setContentID("angelababy"); // 设置资源id，供邮件内容引用

		// 设置内容
		body.setContent("<img src='cid:angelababy' />angelababy", "text/html;charset=UTF-8");

		// 保存邮件、发送
		message.saveChanges();
		Transport.send(message);
	}

	/**
	 * 发送html + img + 附件 格式邮件邮件
	 */
	public static void sendHtmlWithImgAndAttacheEmail() throws Exception {
		// 创建会话对象
		Session session = Session.getDefaultInstance(prop, new MyAuthenticator(user, pwd));
		// 邮件对象
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(RecipientType.TO, new InternetAddress(to));
		message.setRecipient(RecipientType.CC, new InternetAddress(cc));
		message.setSentDate(new Date());
		message.setSubject("测试html + img + 附件邮件");
		message.setSentDate(new Date());
		
		
		MimeMultipart mix = new MimeMultipart("mixed");
		message.setContent(mix);
		MimeBodyPart left = new MimeBodyPart();
		MimeBodyPart right = new MimeBodyPart();
		
		mix.addBodyPart(left);
		mix.addBodyPart(right);
		
		DataSource fileDs = new FileDataSource(URLDecoder.decode(MailUtil.class.getResource("example/readme.txt").getPath(), "UTF-8"));
		DataHandler fileHandler = new DataHandler(fileDs);
		right.setDataHandler(fileHandler);
		
		// 设置文件
		right.setFileName(MimeUtility.encodeText("readme.txt"));
		
		
		// 设置多功能邮件
		MimeMultipart multipart = new MimeMultipart("related");
		left.setContent(multipart);

		// 邮件内容： html + 文本
		MimeBodyPart body = new MimeBodyPart();
		// 邮件“内嵌资源”
		MimeBodyPart source = new MimeBodyPart();

		// 添加邮件内容，到多功能用途邮件
		multipart.addBodyPart(body);
		multipart.addBodyPart(source);

		// 设置资源
		DataSource ds = new FileDataSource(MailUtil.class.getResource("example/angelababy.jpeg").getPath());
		DataHandler handler = new DataHandler(ds);
		source.setDataHandler(handler);
		source.setContentID("angelababy"); // 设置资源id，供邮件内容引用

		// 设置内容 cid的值，与设置的ContentID保持一致
		body.setContent("<img src='cid:angelababy' />angelababy", "text/html;charset=UTF-8");

		// 保存邮件、发送
		message.saveChanges();
		Transport.send(message);
	}
}
