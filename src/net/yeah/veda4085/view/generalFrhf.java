package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import net.yeah.veda4085.algorithms.Complex;
import net.yeah.veda4085.algorithms.genrealFA;



public class generalFrhf {

	public JPanel centerPanel,rightPanel,leftPanel;
	public JPanel panel1,panel2;
	public JLabel label1;
	public JLabel label2;
	public JButton button1;
	public JPanel outimgpanel,timespanel;
	private JScrollBar timesbar;
	private JLabel timeslabel;
	private File f;
	int w;
	int[][] r_2arr;
	int[][] g_2arr;
	int[][] b_2arr;
	
	
	public JPanel launchFrame() {
		centerPanel = new JPanel(new BorderLayout());
		//centerPanel.setPreferredSize(new Dimension(1000, 700));
		leftPanel = new JPanel(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(700, centerPanel.getHeight()));
		rightPanel = new JPanel(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(700, centerPanel.getHeight()));
		outimgpanel = new JPanel(new BorderLayout());
		outimgpanel.setPreferredSize(new Dimension(rightPanel.getWidth(), rightPanel.getHeight()-35));
		timespanel = new JPanel(new GridLayout(1,2));
		timespanel.setPreferredSize(new Dimension(rightPanel.getWidth()-50, 35));
		
		panel1 = new JPanel(new BorderLayout());  
	    label1 = new JLabel();  
	    label1.setText("                                                                    请选择一张图片！");  
	    panel1.add(label1, BorderLayout.CENTER);
	    panel2 = new JPanel(); 
	    button1 = new JButton("打开");
	    button1.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                openFile();  
                getarry(f);
                drawimg(1);
               
            }  
        }) ;  
       panel2.add(button1);
	    
       
       timeslabel =new JLabel("放大倍数：1.00");
       timeslabel.setPreferredSize(new Dimension(timespanel.getWidth()/5,35));
       timesbar= new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100, 500);
       timesbar.addAdjustmentListener(new timesbarListener());
       label2 = new JLabel();
       timespanel.add(timeslabel);
       timespanel.add(timesbar);
       outimgpanel.add(label2, BorderLayout.CENTER);
	   rightPanel.add(outimgpanel, BorderLayout.CENTER);
	   rightPanel.add(timespanel,BorderLayout.SOUTH);
       
		leftPanel.add(panel1, BorderLayout.CENTER);  
	    leftPanel.add(panel2, BorderLayout.SOUTH);
		centerPanel.add(leftPanel, BorderLayout.WEST);
		centerPanel.add(rightPanel, BorderLayout.CENTER);
		return centerPanel;
	}
	
	public void openFile() {  
        JFileChooser  chooser = new JFileChooser();  
        chooser.showOpenDialog(null);  
        f = chooser.getSelectedFile();  
        //文件是否存在或者是否选择  
        if(f == null) {  
            return;  
        }  
          
       BufferedImage bi = null;  
        try {  
            bi = ImageIO.read(f);  
            /*获取文件是否为图片，如果能够正常的获取到一张图片的宽高属性， 
            那肯定这是一张图片，因为非图片文件我们是获取不到它的宽高属性的*/  
            if(bi == null || bi.getHeight() <=0 || bi.getWidth() <=0){  
                label1.setText("您选择的不是一张图片，请从新选择！");  
                return;  
            } else {  
                String path = f.getPath(); 
                
                int a,b;
                a=bi.getWidth();b=bi.getHeight();
                if (a>leftPanel.getWidth()) {a =leftPanel.getWidth();}
                if (b>centerPanel.getHeight()) {b = centerPanel.getHeight();}
                BufferedImage tag = new BufferedImage(a, b,
        				BufferedImage.TYPE_INT_RGB);
        		tag.getGraphics().drawImage(
        				bi.getScaledInstance(a, b, Image.SCALE_SMOOTH), 0,
        				0, null);
        		bi = tag;
                ImageIcon image2 = new ImageIcon(bi); 
                label1.setIcon(image2); //设置JLabel的显示图片
                label1.setText("");
                button1.setText("输入");
               
                
                  
                         
            }  
        } catch (IOException e) {  
//          e.printStackTrace();  
            return;  
        } 
        
          
    }
	
	
	public void getarry(File f){
		BufferedImage bi = null;
		try {
			 bi = ImageIO.read(f);
			
			 genrealFA fftexmp1 = new genrealFA(bi);
			 this.w=fftexmp1.w;
			 r_2arr = new int[w][w];
			 g_2arr = new int[w][w];
			 b_2arr = new int[w][w];
			 for(int i = 0;i < w; i++){
				 for(int j = 0; j < w;j++){
					 r_2arr[i][j]=(int)(fftexmp1.interval[i][j].getR());
					 g_2arr[i][j]=(int)(fftexmp1.intervalg[i][j].getR());
					 b_2arr[i][j]=(int)(fftexmp1.intervalb[i][j].getR());
				/*	 r_2arr[i][j]=r_2arr[i][j]*r_2arr[i][j];
					 g_2arr[i][j]=g_2arr[i][j]*g_2arr[i][j];
					 b_2arr[i][j]=b_2arr[i][j]*b_2arr[i][j];*/
				//	 if(j==40&&(i<=80&&i>=30)) {System.out.println(r_2arr[i][j]);}
					 if((i+j)%2!=0){
						 r_2arr[i][j]=-r_2arr[i][j];
						 g_2arr[i][j]=-g_2arr[i][j];
						 b_2arr[i][j]=-b_2arr[i][j];
					 }
				 }
			 }
			 int max=0,min=255;
			 for(int i = 0; i < w; i++){
				 for(int j = 0; j < w; j++){
					 if(r_2arr[i][j]>max) max=r_2arr[i][j];
					 if(g_2arr[i][j]>max) max=g_2arr[i][j];
					 if(b_2arr[i][j]>max) max=b_2arr[i][j];
					 if(r_2arr[i][j]<min) min=r_2arr[i][j];
					 if(g_2arr[i][j]<min) min=g_2arr[i][j];
					 if(b_2arr[i][j]<min) min=b_2arr[i][j];
					 
				 }
			 }
			 for(int i = 0; i < w; i++){
				 for(int j = 0; j < w; j++){
					 r_2arr[i][j]=(int)((double)(r_2arr[i][j]-min)*255/(double)(max-min));
					 g_2arr[i][j]=(int)((double)(g_2arr[i][j]-min)*255/(double)(max-min));
					 b_2arr[i][j]=(int)((double)(b_2arr[i][j]-min)*255/(double)(max-min));
					
					 
				 }
			 }
			 
     /*   BufferedImage shuchu = fftexmp1.outpt(); 
        int a,b;
        a=shuchu.getWidth();b=shuchu.getHeight();
        if (a>outimgpanel.getWidth()) {a =outimgpanel.getWidth();}
        if (b>outimgpanel.getHeight()) {b = outimgpanel.getHeight();}
        
        BufferedImage tag = new BufferedImage(a, b,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(
				shuchu.getScaledInstance(a, b, Image.SCALE_SMOOTH), 0,
				0, null);
		shuchu = tag;
        
        ImageIcon image = new ImageIcon((Image)shuchu);  
          
        label2.setIcon(image); */
        
       
     //   ImageIO.write(shuchu, "JPEG", new File("D:\\bspic/my2.jpg"));
		} catch (Exception e) {
	}
}
	public void drawimg(double times){
		BufferedImage shuchu = createimg(times); 
        int a,b;
       // a=shuchu.getWidth();b=shuchu.getHeight();
        a =rightPanel.getWidth();
        b = rightPanel.getHeight();
      
        BufferedImage tag = new BufferedImage(a, b,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(
				shuchu.getScaledInstance(a, b, Image.SCALE_SMOOTH), 0,
				0, null);
		shuchu = tag;
        
        ImageIcon image = new ImageIcon((Image)shuchu);  
          
        label2.setIcon(image);
	}

	
	public BufferedImage createimg(double times){
		int w2 =(int)((double)w/times);
		BufferedImage dest =new BufferedImage(w2,w2,BufferedImage.TYPE_INT_ARGB);
		int mid = w/2;
		int half_range =w2/2;
		int alpha,newred,newgreen,newblue,newrgb;
		alpha = (-1) << 24;
	//	System.out.println(w2+"   "+mid+"    "+half_range);
    	for(int i = mid-half_range ; i < mid-half_range+w2; i++){
    		for(int j = mid-half_range; j < mid-half_range+w2; j++){
    			newred = (int)(this.r_2arr[i][j]);
    			newgreen = (int)(this.g_2arr[i][j]);
    			newblue = (int)(this.b_2arr[i][j]);
    			if((i+j)%2!=0){
    				newred = -newred;
    				newgreen = -newgreen;
    				newblue = - newblue;
    				
    				
    				
    			}
    			
    	
    			
    			if(newred>255) newred=255;if(newred<0) newred=0;
    			if(newgreen>255) newgreen=255;if(newgreen<0) newgreen=0;
    			if(newblue>255) newblue=255; if(newblue<0) newblue=0;
    			
    			
    			
    			
    			newgreen = newgreen<<8;
    			newred = newred<<16;
    			newrgb = alpha | newred | newgreen | newblue;
    		//	newrgb = alpha+(newred*256+newgreen)*256+newblue;
    			dest.setRGB(i-(mid-half_range), j-(mid-half_range), newrgb);
    			
    		}
    	}
	  
		return dest;
	}
	
	class timesbarListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double v1 = (double)e.getValue()/(double)100;
			timeslabel.setText("放大倍数： "+v1);
            
			drawimg(v1);
		}					
	}
}