package gui;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.opencv.core.Mat;
 
public class MyFrame{
 
    private JFrame frame;
    private VideoPanel v_panel;
    private JButton e_btn;
    private JLabel r_label;
    private JLabel t_label;
    private Process yolo_process;
    private LineBorder lb;
    private LineBorder lb2;
    public static int threadNum=0;
    
    public MyFrame() {
        frame = new JFrame("카페 좌석 관리 시스템 Canitoring");
        frame.setBounds(10, 10, 1600, 900);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        lb=new LineBorder(Color.black, 1, true);
        lb2=new LineBorder(Color.black, 1, true);	
        
        v_panel = new VideoPanel();
        v_panel.setBounds(10,20,1280,960);
        v_panel.setBorder(lb);
        frame.getContentPane().add(v_panel);
        
        
        r_label = new JLabel("결과");
        r_label.setFont(new Font("굴림", Font.BOLD, 31));
        r_label.setHorizontalAlignment(SwingConstants.CENTER);
		r_label.setBounds(1100, 50, 450, 300);
		r_label.setBorder(lb);
		frame.getContentPane().add(r_label);
		
		TitledBorder t_border=new TitledBorder(new LineBorder(Color.black),"TIME");
		t_label = new JLabel("TIME");
		t_label.setLayout(null);
		t_label.setBorder(t_border);
		t_label.setFont(new Font("굴림", Font.BOLD, 31));
		t_label.setHorizontalAlignment(SwingConstants.CENTER);
		t_label.setBounds(1100, 400, 450, 300);
		t_label.setBorder(lb2);
		frame.getContentPane().add(t_label);
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);
        e_btn=new JButton("실행");
        e_btn.setBounds(1200,750,200,50);
        frame.getContentPane().add(e_btn);
        e_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		try {
        			//yolo_process=new ProcessBuilder("C:\\Program Files\\Bandizip\\Bandizip.exe").start();
        			//Thread.sleep(10000);
        			
        			Result_thread rt = new Result_thread(frame,r_label);
        			executorService.submit(rt);
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
        				
        	}
        });
    }
 
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
 
    public void render(Mat image) {
        Image i = toBufferedImage(image);
        v_panel.setImage(i);
        v_panel.repaint();
 
    }
 
    public static Image toBufferedImage(Mat m){
 
              // Check if image is grayscale or color
          int type = BufferedImage.TYPE_BYTE_GRAY;
          if ( m.channels() > 1 ) {
              type = BufferedImage.TYPE_3BYTE_BGR;
          }
 
              // Transfer bytes from Mat to BufferedImage
          int bufferSize = m.channels()*m.cols()*m.rows();
          byte [] b = new byte[bufferSize];
          m.get(0,0,b); // get all the pixels
          BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
          final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
          System.arraycopy(b, 0, targetPixels, 0, b.length);
          return image;
      }
    
    
}
