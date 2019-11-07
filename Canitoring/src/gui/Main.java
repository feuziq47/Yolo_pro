package gui;

import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class Main {
	static {
        // Load the native OpenCV library
        System.loadLibrary("opencv_java410");
    }
	public static void main(String[] args) throws IOException, InterruptedException{
		
		VideoCapture cap = new VideoCapture(0);
		//if (!cap.isOpened()) {
        //    System.exit(-1);
       // }
		Mat image = new Mat();
		
		MyFrame frame = new MyFrame();
		frame.setVisible(true);
        // Main loop
        while (true) {
            // Read current camera frame into matrix
            cap.read(image);
            // Render frame if the camera is still acquiring images
            if (!image.empty()) {
                frame.render(image);
            } else {
                System.out.println("No captured frame -- camera disconnected");
            }
        }
	}
}
