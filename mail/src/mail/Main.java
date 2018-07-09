package mail;

public class Main {

	public static void main(String[] args) throws Exception{
//		MailUtil.sendEmail(); // 普通文本测试
//		MailUtil.sendHtmlEmail();  // html格式数据测试
//		MailUtil.sendHtmlAndImgEmail();  // html + 图片测试
		MailUtil.sendHtmlWithImgAndAttacheEmail();  // html+ 图片+ 附件
	}

}


