package misc;

import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPCommand;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.ftp.FTPSCommand;

public class FtpClient {
	
	{
		
		
		//22: Server Reply: SSH-2.0-OpenSSH_6.4
		//2222: Server Reply: SSH-2.0-WingFTPServer
		String server = "demo.wftpserver.com";
		int port = 2222;
		String username = "demo-user";
		String password= "demo-user";
		
		//APACHE
		//Si el servidor es SSH, y se utiliza FTPClient, se lanza:
		//org.apache.commons.net.MalformedServerReplyException: Could not parse response code.
		//Server Reply: SSH-2.0-OpenSSH_6.4
		//FTPClient ftp = new FTPClient();
		
		String protocol = "TLS";
		boolean isSSL = true;
		
		FTPClientConfig configuration = new FTPClientConfig(FTPClientConfig.SYST_NT);
		FTPSClient ftp = new FTPSClient(protocol, isSSL);
		ftp.configure(configuration);
		
		
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out),true));
		
		//If desired, the JVM property 
		//-Djavax.net.debug=all 
		//can be used to see wire-level SSL details.
		//System.setProperty("javax.net.debug","all");
		
		try {
			//https://stackoverflow.com/questions/36302985/how-to-connect-to-ftp-over-tls-ssl-ftps-server-in-java
			//FAIL WHEN SPECIFIC PORT
			//ftp.connect(server, port);
			
			ftp.connect(server);
			System.out.println("CONECTADO AL FTP: " + server);
			
			 // After connection attempt, you should check the reply code to verify success
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)){
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
			
			//SE REQUIERE COLOCARLO EN PASSIVE MODE PARA PODER EJECUTAR LOS COMANDOS
			ftp.enterLocalPassiveMode();
			//ftp.enterRemotePassiveMode();
			
			//AUTH
			boolean authOk = ftp.login(username, password);
			if(authOk) {
				System.out.println("AUTHENTICATION IS OK");
				System.out.println("Remote system is: " + ftp.getSystemType());
				
				String workingDirectory = ftp.printWorkingDirectory();
				System.out.println("workingDirectory: " + workingDirectory);
				
				ftp.changeWorkingDirectory("upload");
				
				System.out.println(">>>>>>>>>>>>>>>>>>inicio");
				//ftp.enterLocalActiveMode();
				//ftp.listFiles();
				//ftp.sendCommand("LIST /");
				ftp.listDirectories("/");
				//ftp.sendCommand(FTPCmd.LIST,"/");
				
				System.out.println(">>>>>>>>>>>>>>>fin");
				//BAD SEQUENCE OF COMMANDS
				//int list = ftp.list();
				//System.out.println("INT: " + list);
				
				
				System.out.println("Listando archivos");
				/*
				for (String s : ftp.listNames(".")) {
                    System.out.println(s);
                }
				*/
				/*
				FTPFile[] files = ftp.listFiles();
				System.out.println("FILES: " +files.length);
				Arrays.asList(files).forEach(file->{
					System.out.println(file);
				});
				*/
				//ftp.setFileType(FTP.BINARY_FILE_TYPE);
				//ftp.setUseEPSVwithIPv4(true);
				
//				System.out.println("ftp.listDirectories()...");
//				FTPFile[] listDirectories = ftp.listDirectories();
//				System.out.println("ftp.listDirectories()...END");
				
				/*
				String remote = ".";
				 for (FTPFile f : ftp.listFiles(remote)) {
                     System.out.println(f.getRawListing());
                 }
				 */
				
				System.out.println("Listando archivos (END)");
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*JSCH
		JSch jsch = new JSch();
		Session session = null;
		try {
			session = jsch.getSession(username, server, 2222);
			session.setPassword(password);
			
			
			java.util.Properties config = new java.util.Properties(); 
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			
			
			//UserInfo ui = new MyUserInfo();
			//session.setUserInfo(ui);
			
			session.connect(180);
			
		} catch (JSchException e) {
			e.printStackTrace();
		}
		*/
		
		/* NOT WORK
		try {
		JSch jsch = new JSch();
		
		String knownHostPublicKey = "demo.wftpserver.com ssh-rsa AAAAB3NzaC1.....XL4Jpmp/";
		jsch.setKnownHosts(new ByteArrayInputStream(knownHostPublicKey.getBytes()));
		
		Session session = jsch.getSession(username, server);
		session.setPassword(password);
		
		
		session.connect();
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
	}
	
	

	public static void main(String[] args) {
		new FtpClient();
	}
}
