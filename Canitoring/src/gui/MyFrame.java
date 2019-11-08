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
import org.opencv.videoio.VideoCapture;
 
public class MyFrame{
 
    private JFrame frame;
    public VideoPanel v_panel;
    public JButton e_btn;
    public JButton close_btn;
    private JLabel r_label;
    private JLabel t_label;
    private LineBorder lb;
    private LineBorder lb2;
    Video_thread vt;
    Thread v_thread;
    
    public static Process yolo_process;
    public static int threadNum=0;
    
    public MyFrame() {
        frame = new JFrame("카페 좌석 관리 시스템 Canitoring");
        frame.setBounds(10, 10, 1600, 900);
        frame.getContentPane().setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        lb=new LineBorder(Color.black, 1, true);
        lb2=new LineBorder(Color.black, 1, true);	
        
        v_panel = new VideoPanel();
        v_panel.setBorder(lb);
        v_panel.setBounds(20, 20, 1024, 768);
        frame.getContentPane().add(v_panel);
        
        
        r_label = new JLabel("결과");
        r_label.setFont(new Font("굴림", Font.BOLD, 31));
        r_label.setHorizontalAlignment(SwingConstants.CENTER);
		r_label.setBounds(1100, 50, 400, 250);
		r_label.setBorder(lb);
		frame.getContentPane().add(r_label);
		
		TitledBorder t_border=new TitledBorder(new LineBorder(Color.black),"TIME");
		t_label = new JLabel("TIME");
		t_label.setLayout(null);
		t_label.setBorder(t_border);
		t_label.setFont(new Font("굴림", Font.BOLD, 31));
		t_label.setHorizontalAlignment(SwingConstants.CENTER);
		t_label.setBounds(1100, 400, 400, 250);
		t_label.setBorder(lb2);
		frame.getContentPane().add(t_label);
		
		ExecutorService executorService = Executors.newFixedThreadPool(2);
        e_btn=new JButton("실행");
        e_btn.setBounds(1125,725,150,50);
        frame.getContentPane().add(e_btn);
        e_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		try {
        			vt=new Video_thread(frame,v_panel);
        			//executorService.submit(vt);
        			v_thread=new Thread(vt);
        			v_thread.start();
        			Thread.sleep(3000);
        			
        			Result_thread rt = new Result_thread(frame,r_label,t_label);
        			executorService.submit(rt);
        		} catch(Exception e) {
        			e.printStackTrace();
        		}
        				
        	}
        });

        close_btn=new JButton("종료");
        close_btn.setBounds(1325,725,150,50);
        frame.getContentPane().add(close_btn);
        close_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent a) {
        		yolo_process.destroy();
        		System.exit(0);

        	}
        });
    }
 
    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    
}
