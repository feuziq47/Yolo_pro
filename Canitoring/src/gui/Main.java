package gui;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Main {
	static {
        // Load the native OpenCV library
        System.loadLibrary("opencv_java412");
    }
	public static void main(String[] args) throws IOException, InterruptedException{
		
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
		
        
	}
}
