package misc;

import java.io.PrintWriter;
import java.security.GeneralSecurityException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLSocketClient {

	{
		//http://blog.palominolabs.com/2011/10/18/java-2-way-tlsssl-client-certificates-and-pkcs12-vs-jks-keystores/index.html

		PrintWriter out = null;
		try {
			
			//this.disableCertificateValidation();
			
			
			System.setProperty("javax.net.debug","all");
			
			String host = "demo.wftpserver.com";
			int port = 2222; 
			
System.out.println("LOCAL:-------------------------------------------------------------");
			
			SSLSocketFactory factory =
	                (SSLSocketFactory) SSLSocketFactory.getDefault();

System.out.println("SERVER:-------------------------------------------------------------");			
			SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

/*			
System.out.println("SESSION:-------------------------------------------------------------");			
			SSLSession session = socket.getSession();
			System.out.println("session: " + session);
System.out.println("END SESSION.........................................................");			
*/
			socket.setNeedClientAuth(true);
			
			//PROTOCOLOS ADMINITIDOS POR EL HOST
			System.out.println("\n[[[Enabled Protocols:]]] ");
			String[] protocols = socket.getEnabledProtocols(); 
	        for (int i = 0; i < protocols.length; i++) {
	            System.out.println(protocols[i]);
	        }
	        
	        //PROTOCOLOS A ACEPTAR
	        String[] goodProtocols = new String[1];
	        goodProtocols[0] = "TLSv1.2";
	        socket.setEnabledProtocols(goodProtocols);
	        
	        //AGAIN
	        protocols = socket.getEnabledProtocols();
	        System.out.println("\n[[[Set Protocols:]]]");
	        for (int i = 0; i < protocols.length; i++) {
	            System.out.println(protocols[i]);
	        }
	        
	        /*
	          send http request
	         
	          Before any application data is sent or received, 
	          the SSL socket will do SSL handshaking first to set up
	          the security attributes.
	         
	          SSL handshaking can be initiated by either flushing data
	          down the pipe, or by starting the handshaking by hand.
	         
	          Handshaking is started manually in this example because
	          PrintWriter catches all IOExceptions (including
	          SSLExceptions), sets an internal error flag, and then
	          returns without rethrowing the exception.
	         
	          Unfortunately, this means any error messages are lost,
	          which caused lots of confusion for others using this code.  
	          
	          The only way to tell there was an error is to call
	          PrintWriter.checkError()
	         */
	        //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider( ));
	        
	        /*
	        out = new PrintWriter(
	                new BufferedWriter(
	                        new OutputStreamWriter(
	                                socket.getOutputStream())));
	        */
	        
	        
	        
	        System.out.println("\n[[[HANDSHAKE]]]");
	        socket.startHandshake();
	        
	        
		
		}catch(Exception e) {
			
			/*
			boolean error = out.checkError();
			System.out.println("ERROR: "  + error);
			*/
			System.out.println(e.getSuppressed().length);
			System.out.println(e.getCause());
			e.printStackTrace();
			
		}
	}
	
	public static void main(String[] args) {
		new SSLSocketClient();
	}

	private void disableCertificateValidation() {
		
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] {
		    
			new X509TrustManager() {     
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
		            return new java.security.cert.X509Certificate[0];
		        } 
		        public void checkClientTrusted( 
		            java.security.cert.X509Certificate[] certs, String authType) {
		            } 
		        public void checkServerTrusted( 
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		    } 
		};
		
		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("SSL"); 
		    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} 
		
	}
}
