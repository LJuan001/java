package mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class MyAuthenticator extends Authenticator{
		private String user;
		private String pwd;
		public MyAuthenticator(String user, String pwd) {
			super();
			this.user = user;
			this.pwd = pwd;
		}
		public MyAuthenticator() {}
		
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pwd);
		}
	}
