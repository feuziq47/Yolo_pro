package gui;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Result_thread implements Runnable{
	private JFrame frame;
	private JLabel label;
	private LineBorder lb;
	public Result_thread(JFrame frame, JLabel label) {
		this.frame=frame;
		this.label=label;
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
				while((result = br.readLine()) != null) {
					System.out.println(result);
					label.setText(result);
					label.setBorder(lb);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					if(result.equals("EMPTY")) {
						label.setForeground(Color.red);
					}else if(result.equals("NOT ORDER")) {
						label.setForeground(Color.blue);
					}else if(result.equals("USING")) {
						label.setForeground(Color.green);
					}else if(result.equals("GHOST")) {
						label.setForeground(Color.gray);
					}
					frame.getContentPane().add(label);
				}
				while((result = br1.readLine()) != null) {
					System.out.println(result);
				}
				
				Calendar time=Calendar.getInstance();
				int hour=time.get(Calendar.HOUR_OF_DAY);
				int minute=time.get(Calendar.MINUTE);
				int sec=time.get(Calendar.SECOND);
				
				
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyFrame.threadNum--;
		}
		
		
	}

}
