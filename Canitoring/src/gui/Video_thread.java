package gui;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.swing.JFrame;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Video_thread extends MyFrame implements Runnable{
	JFrame frame;
	VideoPanel panel;
	
	Runtime rt=Runtime.getRuntime();
	public Video_thread(JFrame frame, VideoPanel panel) {
		this.frame=frame;
		this.panel=panel;
	}
	@Override
	public void run() {
		try {
			yolo_process=Runtime.getRuntime().exec("cmd.exe /c darknet detector demo data/coco.data cfg/yolov3.cfg ./yolov3.weights http://192.168.1.74:8080/video?dummy=param.mjpg -i 0 -dont_show -mjpeg_port 8090 -ext_output <data/train.txt> ../gui/Canitoring/result.txt",null,new File("C:\\Users\\User\\Desktop\\Training\\build\\darknet\\x64"));
			// http://192.168.1.74:8080/video?dummy=param.mjpg
			VideoCapture cap = new VideoCapture("http://localhost:8090/show_img.mjpg");
			//C:\Users\User\Desktop\Training¿ë\build\darknet\x64
			if (!cap.isOpened()) {
	            System.exit(-1);
	        }
			Mat image = new Mat();
			
			while (true) {
	            // Read current camera frame into matrix
	            cap.read(image);
	            // Render frame if the camera is still acquiring images
	            if (!image.empty()) {
	                render(image);
	            } else {
	                System.out.println("No captured frame -- camera disconnected");
	            }
	        }
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void render(Mat image) {
        Image i = toBufferedImage(image);
        panel.setImage(i);
        panel.repaint();
 
    }
 
    public static Image toBufferedImage(Mat m){
 
          //int type = BufferedImage.TYPE_BYTE_GRAY;
          //if ( m.channels() > 1 ) {
    	int type = BufferedImage.TYPE_3BYTE_BGR;
          //}
 
          int bufferSize = m.channels()*m.cols()*m.rows();
          byte [] b = new byte[bufferSize];
          m.get(0,0,b); // get all the pixels
          BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
          final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
          System.arraycopy(b, 0, targetPixels, 0, b.length);
          return image;
      }
}
