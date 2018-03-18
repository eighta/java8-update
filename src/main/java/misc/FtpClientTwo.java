package misc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.net.ssl.SSLException;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

public class FtpClientTwo {

	public static void main(String[] args) throws Exception {
		method();
	}

	private static void method() throws Exception {
		FTPSClient ftp = new FTPSClient("TLS");
		ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));

		//String server = "186.80.180.163";
		String server = "demo.wftpserver.com";
		ftp.connect(server);
		System.out.println("###CONNECTED");

		//String username = "ftpuser";
		String username = "demo-user";
		//String password = "pass";
		String password = "demo-user";
		
		ftp.login(username, password);
		System.out.println("###AUTH OK");

		
		ftp.sendCommand("CWD","upload");
		//readFiles(ftp);
		storeFile(ftp);
		//retrieve(ftp);

		// ftp.sendCommand("PORT", "192,168,0,17,239,251");
		// ftp.sendCommand("LIST");

		// ftp.listFiles();
		// SYST
		// 215 UNIX Type: L8
		// PORT 192,168,0,17,239,251
		// 200 PORT command successful
		// LIST
		// 150 Opening ASCII mode data connection for file list
		// 226 Transfer complete

	}

	private static void retrieve(FTPClient ftp) throws Exception {
		String remoteFileName = "songlist.txt";
		File downloadFile = new File("C:/Yesta/songlist_DOWNLOADED_FTP.txt");
		OutputStream outputStream1 = new BufferedOutputStream(new FileOutputStream(downloadFile));
		boolean success = ftp.retrieveFile(remoteFileName, outputStream1);
		outputStream1.close();
		System.out.println("success: " + success);
//		PORT 192,168,0,17,241,47
//		200 PORT command successful
//		RETR songlist.txt
//		150 Opening ASCII mode data connection for songlist.txt (8055 bytes)
//		226 Transfer complete
	}

	private static void readFiles(FTPSClient ftp) throws Exception {
		
		
		FTPFile[] listFiles = ftp.listFiles();
		for (FTPFile ftpFile : listFiles) {
			System.out.println("file: " + ftpFile.getName());
		}
	}

	private static void storeFile(FTPSClient ftp) throws Exception {
		String remoteFileName = "songlist.txt";
		File localFile = new File("C:/Yesta/" + remoteFileName);
		InputStream localFileIS = new FileInputStream(localFile);
		
		sslOperationNeeded(ftp);
		boolean storeFile = ftp.storeFile("taste", localFileIS);
		System.out.println("storeFile: " + storeFile);

	}
	
	private static void sslOperationNeeded(FTPSClient ftp) throws Exception {
		// Set protection buffer size
		//ftp.execPBSZ(0); NO REQUERIDO
		// Set data channel protection to private
		ftp.execPROT("P");
		ftp.enterLocalPassiveMode();
	}
}
