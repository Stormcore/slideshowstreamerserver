package by.stormnet.slideshowstreamerserver;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.imageio.ImageIO;

public class Server {

	private static ServerSocket s;

	public static void main(String[] args) throws IOException, AWTException {
		
		s = new ServerSocket(1290);

	    while (true) {
	    	Robot myRobot = new Robot();
	    	int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
	        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
	        Rectangle myRect = new Rectangle(screenWidth, screenHeight);
	        BufferedImage screenShot = myRobot.createScreenCapture(myRect);
	        ByteArrayOutputStream bScrn = new ByteArrayOutputStream();
	        ImageIO.write(screenShot, "PNG", bScrn);
	        byte imgBytes[] = bScrn.toByteArray();
	        bScrn.close();
	        
	        try {
	        	Socket connection = s.accept();
		        OutputStream out = connection.getOutputStream();    
		        out.write(imgBytes, 0, imgBytes.length);
		        out.flush();
		        out.close();
			} catch (SocketException e) {
				e.printStackTrace();
				System.out.println(e.toString() + " The client has been disconnected during the operation");
			}
	        
	    }
	}

}
