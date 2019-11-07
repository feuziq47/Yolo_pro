package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
 
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
 
public class VideoPanel extends JPanel {
 
    private Image img;
    private LineBorder lb;
    public VideoPanel() {
    	lb=new LineBorder(Color.black, 1, true);
    	this.setBorder(lb);
    	
    }
 
    public void setImage(Image img) {
                // Image which we will render later
        this.img = img;
 
                // Set JPanel size to image size
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }
 
        @Override
    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
