package misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.SocketException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;

public class FtpClientOne {
	
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
			//ftp.enterLocalActiveMode();
			ftp.enterLocalPassiveMode();
			//ftp.enterRemoteActiveMode(null, 0);
			//ftp.enterRemotePassiveMode();
			
			//ftp.completePendingCommand();
			
			//AUTH
			boolean authOk = ftp.login(username, password);
			if(authOk) {
				System.out.println("###AUTHENTICATION IS OK");
				System.out.println("###Remote system is: " + ftp.getSystemType());
				
				String workingDirectory = ftp.printWorkingDirectory();
				System.out.println("###workingDirectory: " + workingDirectory);
				
				System.out.println("###ChangingWorkingDirectory");
				ftp.changeWorkingDirectory("upload");
				
				System.out.println("###Downloading file");
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				
				String fileName = "mail-editor.png";
				
				String localFilePath = "C:/Yesta/ftp/" + fileName;
				//FileOutputStream fos = new FileOutputStream(localFilePath);
				//boolean b = ftp.retrieveFile(fileName, fos);
				//System.out.println("b: " + b);
System.out.println("a");				
//ftp.remoteRetrieve(fileName);
//InputStream retrieveFileStream = ftp.retrieveFileStream(fileName);

System.out.println("b");
//int passivePort = ftp.getPassivePort();
//System.out.println(passivePort);
//ftp.listFiles();
ftp.listDirectories();
//byte[] buffer = new byte[16471];
//int read = retrieveFileStream.read(buffer);
//ftp.completePendingCommand();
System.out.println("c");
				
					
				
				//Si supieramos lo bajaramos de un solo totaso
				//int available = retrieveFileStream.available();
				//System.out.println("available: " + available);
				
				//reading until we reach the end of the stream
				/*
				File targetFile = new File("C:/Yesta/ftp/" + fileName);
			    OutputStream localFileOutputStream = new FileOutputStream(targetFile);
				
				byte[] buffer = new byte[8 * 1024];
				int bytesRead;
				
				int hey = remoteFileInputStream.read(buffer);
				
			    while ((bytesRead = remoteFileInputStream.read(buffer)) != -1) {
			    	localFileOutputStream.write(buffer, 0, bytesRead);
			    }
			    
			    localFileOutputStream.close();
			    remoteFileInputStream.close();
			    */
				
				//ftp.enterLocalActiveMode();
				
			//Socket socket = _openDataConnection_(FTPCmd.LIST, n);
				
				//ftp.setBufferSize(1000);
				//ftp.setFileType(FTP.BINARY_FILE_TYPE);
				
				//
				
				
				//OKSystem.out.println("###LIST FILES");
				//ftp.listFiles();
				
//				PASV
//				227 Entering Passive Mode (199,71,215,197,4,1)
//				LIST
//				150 Opening data channel for directory list.
				
				//ftp.listDirectories();
//				PASV
//				227 Entering Passive Mode (199,71,215,197,4,0)
//				LIST
//				150 Opening data channel for directory list.
				
				//ftp.listDirectories("/upload");
//				PASV
//				227 Entering Passive Mode (199,71,215,197,4,30)
//				LIST /upload
//				150 Opening data channel for directory list.
				
				//ftp.list();
				//503 Bad sequence of commands

				//ftp.list("/upload");
//				LIST /upload
//				503 Bad sequence of commands.
				
				//ftp.sendCommand(FTPCmd.LIST,"/");
//				LIST /
//				503 Bad sequence of commands.
				
				
				System.out.println("######################################################################");
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
	
	

	public static void main(String[] args) throws Exception {
		//new FtpClient();
		ftpMethod();
	}
	
	private static void ftpMethod() throws SocketException, IOException {
		String server = "demo.wftpserver.com";
		String username = "demo-user";
		String password= "demo-user";
		
		boolean isImplicit = true;
		FTPSClient ftp = new FTPSClient(isImplicit);
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out),true));
		
		ftp.connect(server);
		int reply = ftp.getReplyCode();
		System.out.println("###connect reply: " + reply);
		
		boolean authOk = ftp.login(username, password);
		System.out.println("###authOk: " + authOk);
		
		//ftp.list();
		//503 Bad sequence of commands.
		
		//ftp.listDirectories();
//		SYST
//		215 UNIX Type: L8
//		PORT 192,168,0,17,212,118
//		530 Illegal IP address for the PORT command
		
		
		//WORKS ftp.sendCommand(FTPCmd.HELP);
		
		//ftp.sendCommand(FTPCmd.PORT,"181,61,208,157,4,10");
		//ftp.enterLocalPassiveMode();
		//ftp.sendCommand(FTPCmd.LIST);
		//ftp.listDirectories();
		
		//ftp.sendCommand(FTPCmd.LIST, ".");
		
		//ftp.enterLocalPassiveMode();
		//ftp.listNames();

		//WORKS ftp.sendCommand(FTPCmd.STATUS);
		
		//ftp.sendCommand(FTPCmd.CHANGE_WORKING_DIRECTORY,"download");
		//ftp.sendCommand("CWD","download");
		//ftp.sendCommand("PORT", "181,61,208,157,4,15");
		//ftp.sendCommand("LIST");
		//WORKDS ftp.sendCommand("BYE");
		//ftp.sendCommand("BINARY");
		
		
		//ftp.sendCommand(FTPCmd.RETRIEVE,"manual_en.pdf");
		//ftp.sendCommand("LIST",".");
		
		System.out.println("###PassiveMode");
		ftp.enterLocalPassiveMode();
		System.out.println("###listFiles");
		ftp.listFiles();
		
		
//		150 Opening data channel for directory list.
//		Exception in thread "main" org.apache.commons.net.ftp.FTPConnectionClosedException: Connection closed without indication.
//			at org.apache.commons.net.ftp.FTP.__getReply(FTP.java:324)
		
//		SYST
//		215 UNIX Type: L8
//		PORT 192,168,0,17,212,124
//		530 Illegal IP address for the PORT command
	}
}
