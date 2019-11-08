package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Result_thread extends MyFrame implements Runnable{
   private JFrame frame;
   private JLabel label;
   private JLabel label2;
   private LineBorder lb;
   private Timer g_timer;
   int hour=0;
   int minute=0;
   int sec=0;
   public Result_thread(JFrame frame, JLabel label,JLabel label2) {
      this.frame=frame;
      this.label=label;
      this.label2=label2;
      lb=new LineBorder(Color.black, 1, true);
   }
   @Override
   public void run() {
      MyFrame.threadNum++;
      try {
         while(true) {
            Process rslt_process=Runtime.getRuntime().exec("python result_get2.py");
            BufferedReader br = new BufferedReader(new InputStreamReader(rslt_process.getInputStream()));
            BufferedReader br1 = new BufferedReader(new InputStreamReader(rslt_process.getErrorStream()));
            String result=null;
            label.repaint();
            label2.getIgnoreRepaint();
            while((result = br.readLine()) != null) {
            	System.out.println(result);
            	label.setBorder(lb);
            	label.setHorizontalAlignment(SwingConstants.CENTER);
            	if(result.equals("EMPTY")) {
            		label.setText(result);
            		label.setForeground(Color.red);
            		hour=0;
            		minute=0;
            		sec=0;
            	}else if(result.equals("NOT ORDER")) {
            		label.setText(result);
            		label.setForeground(Color.blue);
            	}else if(result.equals("USING")) {
            		label.setText(result);
            		label.setForeground(Color.green);
            	}else if(result.equals("occupied")) {
            		label.setText("occupied");
            		g_timer=new Timer();
            		TimerTask g_task=new TimerTask() {
            			public void run() {
            				label.setText("GHOST");
            				label.setForeground(Color.yellow);
            			}
            		};
            		g_timer.schedule(g_task, 5000);
            		if(!label.getText().equals("occupied")) {
            			g_timer.cancel();
            		}  
            	}
            }
            while((result = br1.readLine()) != null) {
               System.out.println(result);
            }
            label2.setText((Integer.toString(hour))+":"+(Integer.toString(minute))+":"+(Integer.toString(sec)));
            sec++;
            if(sec==60) {
               sec=0;
               minute++;
            }
            if(minute==60) {
               minute=0;
               hour++;
            }
        	frame.getContentPane().add(label);
            frame.getContentPane().add(label2);
            frame.setVisible(true);
            rslt_process.destroy();
            try {
            	 Thread.sleep(1000);
            }catch(InterruptedException e) {
            	
            }
           
            
         }
      } catch (Exception e) {
         e.printStackTrace();
         MyFrame.threadNum--;
      }
      
      
   }
   
   
}