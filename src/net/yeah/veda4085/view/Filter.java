package net.yeah.veda4085.view;

import net.yeah.veda4085.algorithms.FFTAlg;
import net.yeah.veda4085.algorithms.Complex;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.lang.Math;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;


public class Filter  {
	int type=1,w,h;
	int lasttype = 1;
	int fltds =2;
	double min=100 , max=500,ang=0 ;
	int[][] input;
	private File f;
	double wid=0;
	public int phstype=1;
	public JPanel centerPanel,leftPanel,rightPanel,midPanel,extraPanel,wholerightPanel;
	
	private JPanel panel1,panel2,panel3,panel4,panel5,panel6,panel7;
    private JLabel label1,label2,minlabel,maxlabel,fltlb,fsysl,anglabel,widlabel;
    private JLabel inputtext,outputtext,filtertext;
    private JButton button1,button2;
    private JTextField tf1,tf2;
    private JRadioButton jrb1,jrb2,jrb3,jrb4,phsjrb,nphsjrb,oneds,twods,phsopen,phsclose,phs_1level,phs_2level,phs_3level,phs_4level;
    private JScrollBar angsb,widsb;
    private jiemianPanel drawpanel;
    
    int[] input_blue_ver;
    int[] input_blue_hor;
    int[] out_blue_ver;
    int[] out_blue_hor;
    int imgwid,imghgt;
      
    
	
	public JPanel launchFrame() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(1000, 700));
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(400, centerPanel.getHeight()));
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(400, centerPanel.getHeight()));
        midPanel = new JPanel(new BorderLayout());
        midPanel.setPreferredSize(new Dimension(300, centerPanel.getHeight()));
        extraPanel = new JPanel(new BorderLayout());
        extraPanel.setPreferredSize(new Dimension(380, centerPanel.getHeight()));
        wholerightPanel = new JPanel(new BorderLayout());
        wholerightPanel.setPreferredSize(new Dimension(780, centerPanel.getHeight()));
		
		 panel1 = new JPanel(new BorderLayout());  
	     label1 = new JLabel();  
	     inputtext = new JLabel();
	     label1.setText("                                                 请选择一张图片！"); 
	     inputtext.setText("                                                  输入图像");
	     panel1.add(label1, BorderLayout.CENTER);
	     panel1.add(inputtext, BorderLayout.NORTH);
	     
	     panel6 = new JPanel(new BorderLayout());
	     label2 = new JLabel();
	     panel6.add(label2, BorderLayout.CENTER);
	     
	     filtertext = new JLabel();
	     filtertext.setText("                               滤波器示意图");
	     
	     panel4 = new JPanel(new GridLayout(11, 2));
	     panel4.setBackground(Color.LIGHT_GRAY);
	     
	     panel5 = new JPanel(new BorderLayout());
	     fltlb = new JLabel();
	     panel5.add(fltlb, BorderLayout.CENTER);
	     panel5.add(filtertext, BorderLayout.SOUTH);
	     
	   
	     
	    /* Image img7 = null;
	     try {
	         
	         File sourceimage = new File("/picture/4f.PNG"); 
	         img7 = ImageIO.read(sourceimage);
	     
	       
	     } catch (IOException e) {
	     }*/
	     
	     try { 
				Image img7 = ImageIO.read(getClass().getResource(
						"/picture/4f.PNG"));
				BufferedImage tag1 = new BufferedImage(300, 200,
						BufferedImage.TYPE_INT_RGB);
				tag1.getGraphics().drawImage(
						img7.getScaledInstance(300, 200, Image.SCALE_SMOOTH), 0,
						0, null);
				img7 = tag1;
				// picture放入jlabel中
				ImageIcon picture1 = new ImageIcon(img7);
				fsysl = new JLabel(picture1);
				
			} catch (Exception e) {
			}
	   //  fsysl = new JLabel(new ImageIcon(img7));
	
	    
	     
	     panel7 = new JPanel();
	     panel7.add(fsysl);
	     
	     
	     
	     
	     phsjrb = new JRadioButton("相位滤波");
	     nphsjrb = new JRadioButton("振幅滤波");
	     phsopen = new JRadioButton("泽尼可开启(反相衬)");
	     phsclose = new JRadioButton("泽尼可关闭");
	     phs_1level = new JRadioButton("最小");
	     phs_2level = new JRadioButton("较小");
	     phs_3level = new JRadioButton("较大");
	     phs_4level = new JRadioButton("最大");
	     oneds = new JRadioButton("一维");
	     twods = new JRadioButton("二维");
	     jrb1 = new JRadioButton("高通");
	     jrb2 = new JRadioButton("低通");
	     jrb3 = new JRadioButton("带通");
	     jrb4 = new JRadioButton("带阻");
	     phsjrb.addActionListener(new phsjrbListener());
	     nphsjrb.addActionListener(new nphsjrbListener());
	     phsopen.addActionListener(new phsopenListener());
	     phsclose.addActionListener(new phscloseListener());
	     phs_1level.addActionListener(new phs_1levelListner());
	     phs_2level.addActionListener(new phs_2levelListner());
	     phs_3level.addActionListener(new phs_3levelListner());
	     phs_4level.addActionListener(new phs_4levelListner());
	     oneds.addActionListener(new onedsListener());
	     twods.addActionListener(new twodsListener());
	     jrb1.addActionListener(new jrb1Listener());
	     jrb2.addActionListener(new jrb2Listener());
	     jrb3.addActionListener(new jrb3Listener());
	     jrb4.addActionListener(new jrb4Listener());
	     jrb1.setSelected(true);
	     nphsjrb.setSelected(true);
	     twods.setSelected(true);
	     phsopen.setEnabled(false);phsclose.setEnabled(false);
	     phs_1level.setEnabled(false);phs_2level.setEnabled(false);
	     phs_3level.setEnabled(false);phs_4level.setEnabled(false);
	     ButtonGroup phslevel = new ButtonGroup();
	     ButtonGroup ds = new ButtonGroup();
	     ButtonGroup bg = new ButtonGroup();
	     ButtonGroup pon = new ButtonGroup();
	     ButtonGroup phs = new ButtonGroup();
	     phslevel.add(phs_1level);phslevel.add(phs_2level);phslevel.add(phs_3level);phslevel.add(phs_4level);
	     bg.add(oneds);bg.add(twods);phs.add(phsclose);phs.add(phsopen);
	     ds.add(jrb1);ds.add(jrb2);ds.add(jrb3);ds.add(jrb4);
	     pon.add(phsjrb);pon.add(nphsjrb);
	     
	     
	     minlabel = new JLabel();maxlabel = new JLabel();anglabel = new JLabel();widlabel = new JLabel();
	     minlabel.setText("内圈半径");maxlabel.setText("外圈半径");anglabel.setText("角度（0°~180°）");widlabel.setText("宽度");
	     angsb = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 180);
	     angsb.setEnabled(false);
	     widsb = new JScrollBar(JScrollBar.HORIZONTAL, 20, 0, 10, 80 );
	     widsb.setEnabled(false);
	     tf1 = new JTextField("100");
	     tf2 = new JTextField("100000");
	     tf2.setEditable(false);
	     tf1.addActionListener(new tf1Listener());
	     tf2.addActionListener(new tf2Listener());
	     angsb.addAdjustmentListener(new angsbListener());
	     widsb.addAdjustmentListener(new widsbListener());
	     
	     panel4.add(nphsjrb);panel4.add(phsjrb);
	     panel4.add(phsopen);panel4.add(phsclose);
	     panel4.add(phs_1level);panel4.add(phs_2level);
	     panel4.add(phs_3level);panel4.add(phs_4level);
	     panel4.add(twods);panel4.add(oneds);
	     panel4.add(jrb1);panel4.add(jrb2);
	     panel4.add(jrb3);panel4.add(jrb4);
	     panel4.add(minlabel);panel4.add(tf1);
	     panel4.add(maxlabel);panel4.add(tf2);
	     panel4.add(anglabel);panel4.add(angsb);
	     panel4.add(widlabel);panel4.add(widsb);
	     
	     
	     panel2 = new JPanel(); 
	     panel3 = new JPanel();
	     button1 = new JButton("打开");
	     button2 = new JButton("输出");
	     button1.addActionListener(new ActionListener() {  
	            public void actionPerformed(ActionEvent e) {  
	                openFile();  
	                
	               
	            }  
	        }) ;  
	     panel2.add(button1);
	     panel3.add(button2);
	     outputtext = new JLabel();
	     outputtext.setText("                                                         输出图像");
	     panel6.add(outputtext, BorderLayout.NORTH);
	     button2.addActionListener(new ActionListener() {  
	           public void actionPerformed(ActionEvent e) {
	        	   showimg(f);
	        	   drawpanel = new jiemianPanel();
	        	   extraPanel.add(drawpanel);   
	        	   extraPanel.updateUI();
	        	   
	           }
	           });
	     
	   
	     leftPanel.add(panel1, BorderLayout.CENTER);  
	     leftPanel.add(panel2, BorderLayout.SOUTH); 
	     rightPanel.add(panel3, BorderLayout.SOUTH);
	     rightPanel.add(panel6, BorderLayout.CENTER);
	     midPanel.add(panel7, BorderLayout.NORTH);
	     midPanel.add(panel4, BorderLayout.SOUTH);
	     midPanel.add(panel5, BorderLayout.CENTER);
	     wholerightPanel.add(rightPanel, BorderLayout.WEST);
	     wholerightPanel.add(extraPanel, BorderLayout.EAST);
	     centerPanel.add(leftPanel,BorderLayout.WEST);
	     centerPanel.add(wholerightPanel,BorderLayout.EAST);
	     centerPanel.add(midPanel,BorderLayout.CENTER);
	     
	     paintflt();

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
	                input_blue_ver = new int[b];
	                input_blue_hor = new int[a];
	                for(int i = 0; i < b; i++){input_blue_ver[i]=bi.getRGB(a/2,i)&0x000000ff;}
	                for(int i = 0; i < a; i++){input_blue_hor[i]=bi.getRGB(i,b/2)&0x000000ff;}
	                
	                  
	                         
	            }  
	        } catch (IOException e) {  
//	          e.printStackTrace();  
	            return;  
	        } 
	        
	          
	    }
	 
	public void showimg(File f){
		BufferedImage bi = null;
		try {
			 bi = ImageIO.read(f);
			 
			 FFTAlg fftexmp1 = new FFTAlg(bi,type,min,max,phstype);
        BufferedImage shuchu = fftexmp1.outpt();
        int a,b;
        a=shuchu.getWidth();b=shuchu.getHeight();
        if (a>rightPanel.getWidth()) {a =rightPanel.getWidth();}
        if (b>centerPanel.getHeight()) {b = centerPanel.getHeight();}
        BufferedImage tag = new BufferedImage(a, b,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(
				shuchu.getScaledInstance(a, b, Image.SCALE_SMOOTH), 0,
				0, null);
		shuchu = tag;
        
        ImageIcon image = new ImageIcon((Image)shuchu);  
          
        label2.setIcon(image); 
        
        out_blue_ver = new int[b];
        out_blue_hor = new int[a];
        for(int i = 0; i < b; i++){out_blue_ver[i]=shuchu.getRGB(a/2,i)&0x000000ff;}
        for(int i = 0; i < a; i++){out_blue_hor[i]=shuchu.getRGB(i,b/2)&0x000000ff;}
     //   ImageIO.write(shuchu, "JPEG", new File("D:\\bspic/my2.jpg"));
		} catch (Exception e) {
	}
		 
	}
	
	
	
	public class tf1Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			String w1 = f.getActionCommand();
			
			min = Double.parseDouble(w1);
			if(type == 1) {max=3*min+20;}
		//	System.out.println(min);
			paintflt();
		}
	}
	
	public class tf2Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			String w1 = f.getActionCommand();
			
			max = Double.parseDouble(w1);
		//	System.out.println(max);
			paintflt();
		}
	}
	
	public class nphsjrbListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			type = lasttype;
			phsopen.setEnabled(false);phsclose.setEnabled(false);
			jrb1.setEnabled(true);jrb2.setEnabled(true);jrb3.setEnabled(true);jrb4.setEnabled(true);
			twods.setEnabled(true);oneds.setEnabled(true);
			tf1.setEditable(true);
			tf2.setEditable(true);
			if(type == 1) {jrb1.setSelected(true);}
			else if(type == -1) {jrb2.setSelected(true);}
			else if(type == 0) {jrb3.setSelected(true);}
			else if(type == 2) {jrb4.setSelected(true);}
			if(fltds == 2) {twods.setSelected(true);}
			else if(fltds == 1) {oneds.setSelected(true);angsb.setEnabled(true);widsb.setEnabled(true);}
			phs_1level.setEnabled(false);phs_2level.setEnabled(false);
		     phs_3level.setEnabled(false);phs_4level.setEnabled(false);
			
		
			
		}
	}
	
	public class phsjrbListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			lasttype = type;
			type = 5;
			phsopen.setEnabled(true);phsclose.setEnabled(true);
			if(phstype==1) {phsopen.setSelected(true);}
			else {phsclose.setSelected(true);}
			jrb1.setEnabled(false);jrb2.setEnabled(false);jrb3.setEnabled(false);jrb4.setEnabled(false);
			twods.setEnabled(false);oneds.setEnabled(false);
			tf1.setEditable(false);
			tf2.setEditable(false);
			angsb.setEnabled(false);
			widsb.setEnabled(false);
			max =21;
			paintflt();
			phs_1level.setEnabled(true);phs_2level.setEnabled(true);
		     phs_3level.setEnabled(true);phs_4level.setEnabled(true);
		     phs_1level.setSelected(true);
		}
	}
	
	public class phsopenListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			phstype=1;
			phs_1level.setSelected(true);
			phs_1level.setEnabled(true);phs_2level.setEnabled(true);
		     phs_3level.setEnabled(true);phs_4level.setEnabled(true);
			paintflt();
		}
	}
	
	public class phscloseListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			phstype=0;
			phs_1level.setEnabled(false);phs_2level.setEnabled(false);
		     phs_3level.setEnabled(false);phs_4level.setEnabled(false);
			paintflt();
			
		}
	}
	
	
	public class phs_1levelListner implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			phstype=1;
			paintflt();
			
		}
	}
	public class phs_2levelListner implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			
			phstype=2;
			paintflt();
		}
	}
	public class phs_3levelListner implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			
			phstype=3;
			paintflt();
		}
	}
	public class phs_4levelListner implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			
			phstype=4;
			paintflt();
		}
	}
	
	
	public class onedsListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			type = lasttype;
			if(type <9) {type+=10;}
			fltds = 1;
			jrb1.setEnabled(true);jrb2.setEnabled(true);jrb3.setEnabled(false);jrb4.setEnabled(false);
			tf1.setEditable(false);
			tf2.setEditable(false);
			angsb.setEnabled(true);
			widsb.setEnabled(true);
			if(type == 1 || type ==11) {jrb1.setSelected(true);}
			else if(type == -1 || type == 9) {jrb2.setSelected(true);}
			else if(type == 0 ) {jrb3.setSelected(true);}
			else if(type == 10 || type == 12) {jrb1.setSelected(true);}
			else if(type == 2 ) {jrb4.setSelected(true);}
			tf1.setText("0");tf2.setText("0");
			paintflt();
			
			
		
			
		}
	}
	
	public class twodsListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			type = lasttype;
			if(type>8) {type-=10;}
			fltds = 2;
			jrb1.setEnabled(true);jrb2.setEnabled(true);jrb3.setEnabled(true);jrb4.setEnabled(true);
			tf1.setEditable(true);
			tf2.setEditable(true);
			angsb.setEnabled(false);
			widsb.setEnabled(false);
			if(type == 1 || type ==11) {jrb1.setSelected(true);}
			else if(type == -1 || type == 9) {jrb2.setSelected(true);}
			else if(type == 0 || type == 10) {jrb3.setSelected(true);}
			else if(type == 2 || type == 12) {jrb4.setSelected(true);}
			paintflt();
			
		
			
		}
	}
	
	public class jrb1Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(fltds == 2) {type = 1; wid = 0;}
			else if(fltds == 1) {type =11;}
			tf2.setText("100000");
			min = 100;
			tf1.setText("100");
			max =4*min+20;
			
			tf2.setEditable(false);
			if(fltds == 2) {tf1.setEditable(true);angsb.setEnabled(false);widsb.setEnabled(false);}
			else {tf1.setText("0");tf2.setText("0");tf1.setEditable(false);angsb.setEnabled(true);widsb.setEnabled(true);}
			
			if(type == 11) {min=ang;max=21;if(wid!=0) {max = wid;}}
			paintflt();
			lasttype = type;
			
		}
	}
	
	public class jrb2Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(fltds == 2) {type = -1; wid=0;}
			else if(fltds == 1) {type =9;}
			tf1.setText("0");
		    min = 0;
		    max = 30;
		    
		    tf2.setText("30");
			tf1.setEditable(false);
			if(fltds == 2) {tf2.setEditable(true);angsb.setEnabled(false);widsb.setEnabled(false);}
			else {tf1.setText("0");tf2.setText("0");tf2.setEditable(false);angsb.setEnabled(true);widsb.setEnabled(true);}
		    if(type == 9) {min=ang;max=21;if(wid!=0) {max = wid;}}
			paintflt();
			lasttype = type;
			
		}
	}
	
	public class jrb3Listener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			
			if(fltds == 2) {type = 0;}
			else if(fltds == 1) {type =10;}
			wid =0;
			tf2.setEditable(true);
			tf1.setEditable(true);
		    min = 30;
		    max = 50;
		    tf1.setText("30");
		    tf2.setText("50");
			paintflt();
			lasttype = type;
		}
	}
	
	public class jrb4Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			
			if(fltds == 2) {type = 2; wid = 0;}
			else if(fltds == 1) {type =12;}
			tf2.setEditable(true);
			tf1.setEditable(true);
		    min = 30;
			 max = 50;
		    tf1.setText("30");
			tf2.setText("50");
			paintflt();
			lasttype = type;
			
		}
	}
	
	class angsbListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double v1 = e.getValue();
			anglabel.setText("角度（0°~180°）值："+v1+"°");
            if(wid==0) {max = 21;}
            min =v1;
            ang = v1;
			paintflt();
		}					
	}
	
	class widsbListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double v1 = e.getValue();
			widlabel.setText("宽度     目前值： "+v1);
			
            max = v1;
            wid = max;
         //   min =v1;
         //   ang = v1;
			paintflt();
		}					
	}
	
	public BufferedImage generateflt(int tp, double min, double max,int ftp){
		int[][] flt = new int[258][258];
		double n,x;
		n = min;x = max;
		int w =256;
		if(tp ==1) {
		//	int w = 261;
			double mid = ((double)(w-1)/2.0);
			n=w*n/x;
			for(int i = 0; i < w; i++){
	    		for(int j = 0; j < w; j++){
	    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
	    			if(l>=n*n) {flt[i][j]=0xffffffff;}
	    			else {flt[i][j]=0xff000000;}
	    		}
			}
		}
		if(tp ==-1) {
		//	int w = 261;
			double mid = ((double)(w-1)/2.0);
			x =x/3+10;
			
			for(int i = 0; i < w; i++){
	    		for(int j = 0; j < w; j++){
	    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
	    			if(l<=x*x) {flt[i][j]=0xffffffff;}
	    			else {flt[i][j]=0xff000000;}
	    		}
			}
		}
		if(tp ==0) {
		//	int w = 301;
			double mid = ((double)(w-1)/2.0);
			x = x/3+10;
			n = n/3 +3;
			for(int i = 0; i < w; i++){
	    		for(int j = 0; j < w; j++){
	    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
	    			if(l>=n*n&&l<=x*x) {flt[i][j]=0xffffffff;}
	    			else {flt[i][j]=0xff000000;}
	    		}
			}
		}
		if(tp ==2) {
		//	int w = 301;
			double mid = ((double)(w-1)/2.0);
			x = x/3+10;
			n = n/3+3;
			for(int i = 0; i < w; i++){
	    		for(int j = 0; j < w; j++){
	    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
	    			if(l>=n*n&&l<=x*x) {flt[i][j]=0xff000000;}
	    			else {flt[i][j]=0xffffffff;}
	    		}
			}
		}
		if(tp == 11){
			
		//	int w = 301;
			int mid = (int)((double)(w-1)/2.0);
			double rad = n*3.14159265358979/180;
			double width2 = (max+1)/2;
			double t = Math.tan(rad);
			double s = Math.sin(rad);
			double c = Math.cos(rad);
			if(n<90 && n >0){
			
			for(int i = 0; i < w; i++){
				for(int j = 0; j < w; j++){
					int k =w-1-j;
					if(i>=mid && k >=mid) {
						int x1 = i - mid;int y = k - mid;
						if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
							flt[i][j]=0xff000000;
							flt[w-1-i][k]=0xff000000;
						}
						else {flt[i][j]=0xffffffff;flt[w-1-i][k]=0xffffffff;}
						}
					else if(i < mid && k >= mid){
						int x1 = mid -i;int y = k-mid;
						if(y<=-t*x1+width2/c){
							flt[i][j]=0xff000000;
							flt[w-1-i][k]=0xff000000;
						}
						else {flt[i][j]=0xffffffff;flt[w-1-i][k]=0xffffffff;}
					}
					
					
				}
			}}
			else if(n==90){
				for(int i = 0; i < w; i++){
					for(int j = 0; j < w; j++){
					if(Math.abs(i-mid)<width2){
						flt[i][j]=0xff000000;
					}
					else { flt[i][j]=0xffffffff;}
					}
				}
				
			}
			else if(n==0||n==180){
				for(int i = 0; i < w; i++){
					for(int j = 0; j < w; j++){
					if(Math.abs(j-mid)<width2){
						flt[i][j]=0xff000000;
					}
					else { flt[i][j]=0xffffffff;}
					}
				}
				
			}
			else if(n>90 && n <180){
				t = Math.tan(3.14159265358979-rad);
				s = Math.sin(3.14159265358979-rad);
				c = Math.cos(3.14159265358979-rad);
				
				for(int i = 0; i < w; i++){
					for(int j = 0; j < w; j++){
						int k =w-1-j;
						if(i>=mid && k >=mid) {
							int x1 = i - mid;int y = k - mid;
							if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
								flt[w-1-i][j]=0xff000000;
								flt[i][k]=0xff000000;
							}
							else {flt[w-1-i][j]=0xffffffff;flt[i][k]=0xffffffff;}
							}
						else if(i < mid && k >= mid){
							int x1 = mid -i;int y = k-mid;
							if(y<=-t*x1+width2/c){
								flt[w-1-i][j]=0xff000000;
								flt[i][k]=0xff000000;
							}
							else {flt[w-1-i][j]=0xffffffff;flt[i][k]=0xffffffff;}
						}
						
						
					}
				}
			}
			
				
			
		}
		
		if(tp == 9){
		//	int w = 301;
			int mid = (int)((double)(w-1)/2.0);
			double rad = n*3.14159265358979/180;
			double width2 = (max+1)/2;
			double t = Math.tan(rad);
			double s = Math.sin(rad);
			double c = Math.cos(rad);
			if(n<90 && n >0){
			
			for(int i = 0; i < w; i++){
				for(int j = 0; j < w; j++){
					int k =w-1-j;
					if(i>=mid && k >=mid) {
						int x1 = i - mid;int y = k - mid;
						if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
							flt[i][j]=0xffffffff;
							flt[w-1-i][k]=0xffffffff;
						}
						else {flt[i][j]=0xff000000;flt[w-1-i][k]=0xff000000;}
						}
					else if(i < mid && k >= mid){
						int x1 = mid -i;int y = k-mid;
						if(y<=-t*x1+width2/c){
							flt[i][j]=0xffffffff;
							flt[w-1-i][k]=0xffffffff;
						}
						else {flt[i][j]=0xff000000;flt[w-1-i][k]=0xff000000;}
					}
					
					
				}
			}}
			else if(n==90){
				for(int i = 0; i < w; i++){
					for(int j = 0; j < w; j++){
					if(Math.abs(i-mid)<width2){
						flt[i][j]=0xffffffff;
					}
					else { flt[i][j]=0xff000000;}
					}
				}
				
			}
			else if(n==0||n==180){
				for(int i = 0; i < w; i++){
					for(int j = 0; j < w; j++){
					if(Math.abs(j-mid)<width2){
						flt[i][j]=0xffffffff;
					}
					else { flt[i][j]=0xff000000;}
					}
				}
				
			}
			else if(n>90 && n <180){
				t = Math.tan(3.14159265358979-rad);
				s = Math.sin(3.14159265358979-rad);
				c = Math.cos(3.14159265358979-rad);
				
				for(int i = 0; i < w; i++){
					for(int j = 0; j < w; j++){
						int k =w-1-j;
						if(i>=mid && k >=mid) {
							int x1 = i - mid;int y = k - mid;
							if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
								flt[w-1-i][j]=0xffffffff;
								flt[i][k]=0xffffffff;
							}
							else {flt[w-1-i][j]=0xff000000;flt[i][k]=0xff000000;}
							}
						else if(i < mid && k >= mid){
							int x1 = mid -i;int y = k-mid;
							if(y<=-t*x1+width2/c){
								flt[w-1-i][j]=0xffffffff;
								flt[i][k]=0xffffffff;
							}
							else {flt[w-1-i][j]=0xff000000;flt[i][k]=0xff000000;}
						}
						
						
					}
				}
			}
			
		}
		if(tp==5){
			if(ftp==0){
				for(int i = 0; i < w; i++){
		    		for(int j = 0; j < w; j++){
		    		flt[i][j]=0xffffffff;	
		    		}
		    		}
			}
			if(ftp==1){
				double mid = ((double)(w-1)/2.0);
				n=2;
				for(int i = 0; i < w; i++){
		    		for(int j = 0; j < w; j++){
		    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
		    			if(l>=n*n) {flt[i][j]=0xffffffff;}
		    			else {flt[i][j]=0xff000000;}
		    		}
				}
			}
			if(ftp==2){
				double mid = ((double)(w-1)/2.0);
				n=4;
				for(int i = 0; i < w; i++){
		    		for(int j = 0; j < w; j++){
		    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
		    			if(l>=n*n) {flt[i][j]=0xffffffff;}
		    			else {flt[i][j]=0xff000000;}
		    		}
				}
			}
			if(ftp==3){
				double mid = ((double)(w-1)/2.0);
				n=8;
				for(int i = 0; i < w; i++){
		    		for(int j = 0; j < w; j++){
		    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
		    			if(l>=n*n) {flt[i][j]=0xffffffff;}
		    			else {flt[i][j]=0xff000000;}
		    		}
				}
			}
			if(ftp==4){
				double mid = ((double)(w-1)/2.0);
				n=12;
				for(int i = 0; i < w; i++){
		    		for(int j = 0; j < w; j++){
		    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
		    			if(l>=n*n) {flt[i][j]=0xffffffff;}
		    			else {flt[i][j]=0xff000000;}
		    		}
				}
			}
			
		}
	
		int lenth = flt.length;
		BufferedImage dest = new BufferedImage(lenth,lenth,BufferedImage.TYPE_INT_ARGB);
    	//int alpha, newred, newgreen, newblue, newrgb;
    	//alpha = (-1) << 24;
    	for(int i = 0; i < lenth; i++){
    		for(int j = 0; j < lenth; j++){
    			dest.setRGB(i, j, flt[i][j]);
    			
    		}
    	}
    	
    	return dest;
	}

	public void paintflt(){
		BufferedImage flt = generateflt(type,min,max,phstype);
		ImageIcon image = new ImageIcon((Image)flt);  
        
        fltlb.setIcon(image); 
		
	}
	 
	class jiemianPanel extends JPanel  
	{   
	    protected void paintComponent(Graphics g)  
	    {   
	        super.paintComponent(g);  
	  
	        double times_int=1.6;
	        int ep_wid=extraPanel.getWidth();
	        //画函数图像 
	        int loc1=150,loc2=320,loc3=490,loc4=660;
	       Polygon p1=new Polygon(); 
	      int a,b;
	      a=input_blue_ver.length;b=input_blue_hor.length;
	       times_int = input_blue_ver.length/ep_wid+1.7;if(times_int<2.4) times_int=1.6;
	       imghgt = (int)(input_blue_ver.length/times_int-1);
	        if(type!=5){
	        	for(int x=0;x<imghgt;x++)  
	        {  
	            p1.addPoint(x+30,(int)(loc1-0.43*input_blue_ver[(int)(times_int*x)]));  
	        }  
	        }
	        else{
	        	
	        	for(int x=0;x<imghgt;x++) p1.addPoint(x+30,(int)(loc1-0.43*240));
	        }
	        g.drawLine(30, loc1, 30, (int)(loc1-0.43*255-20));
	        g.drawLine(30-3, (int)(loc1-0.43*255-20)+7, 30, (int)(loc1-0.43*255-20));
	        g.drawLine(30+3, (int)(loc1-0.43*255-20)+7, 30, (int)(loc1-0.43*255-20));
	        g.drawLine(30, loc1, imghgt+55, loc1);
	        g.drawLine(imghgt+48, loc1-3, imghgt+55, loc1);
	        g.drawLine(imghgt+48, loc1+3, imghgt+55, loc1);
	        g.drawString("输入图像蓝光强度纵中轴截面", 30, loc1+12);
	        
	         
	         
	       Polygon p2=new Polygon();
	       times_int = input_blue_hor.length/ep_wid+1.7;if(times_int<2.4) times_int=1.6;
	       imgwid = (int)(input_blue_hor.length/times_int-1);
	        if(type!=5){ 
	       for(int x=0;x<imgwid;x++)  
	        {  
	            p2.addPoint(x+30,(int)(loc2-0.43*input_blue_hor[(int)(times_int*x)]));  
	        }
	       }
	        else{
	     
       	  for(int x=0;x<imghgt;x++) p2.addPoint(x+30,(int)(loc2-0.43*240));
	       }
	        g.drawLine(30, loc2, 30, (int)(loc2-0.43*255-20));
	        g.drawLine(30-3, (int)(loc2-0.43*255-20)+7, 30, (int)(loc2-0.43*255-20));
	        g.drawLine(30+3, (int)(loc2-0.43*255-20)+7, 30, (int)(loc2-0.43*255-20));
	        g.drawLine(30, loc2, imgwid+55, loc2);
	        g.drawLine(imgwid+48, loc2-3, imgwid+55, loc2);
	        g.drawLine(imgwid+48, loc2+3, imgwid+55, loc2);
	        g.drawString("输入图像蓝光强度横中轴截面", 30, loc2+12);
	        
	         
	        Polygon p3=new Polygon();  
	        times_int = out_blue_ver.length/ep_wid+1.7;if(times_int<2.4) times_int=1.6;
		    imghgt = (int)(out_blue_ver.length/times_int-1);
		        for(int x=0;x<imghgt;x++)  
		        {  
		            p3.addPoint(x+30,(int)(loc3-0.43*out_blue_ver[(int)(times_int*x)]));  
		        }  
		    
		     g.drawLine(30, loc3, 30, (int)(loc3-0.43*255-20));
		     g.drawLine(30-3, (int)(loc3-0.43*255-20)+7, 30, (int)(loc3-0.43*255-20));
		     g.drawLine(30+3, (int)(loc3-0.43*255-20)+7, 30, (int)(loc3-0.43*255-20));
		     g.drawLine(30, loc3, imghgt+55, loc3);
		     g.drawLine(imghgt+48, loc3-3, imghgt+55, loc3);
             g.drawLine(imghgt+48, loc3+3, imghgt+55, loc3);   
             g.drawString("输出图像蓝光强度纵中轴截面", 30, loc3+12);
            /* System.out.print("c"+out_blue_ver[(int)times_int*5]);
             System.out.print("c"+out_blue_ver[(int)times_int*6]);
             System.out.print("c"+out_blue_ver[(int)times_int*7]);
             System.out.print("c"+out_blue_ver[(int)times_int*8]);
             System.out.print("c"+out_blue_ver[(int)times_int*9]);
             System.out.println("c"+out_blue_ver[(int)times_int*10]);*/
             
		        
		    Polygon p4=new Polygon();
		    times_int = out_blue_hor.length/ep_wid+1.7;if(times_int<2.4) times_int=1.6;
		    imgwid = (int)(out_blue_hor.length/times_int-1);
		        for(int x=0;x<imgwid;x++)  
		        {  
		            p4.addPoint(x+30,(int)(loc4-0.43*out_blue_hor[(int)(times_int*x)]));  
		        } 
		    g.drawLine(30, loc4, 30, (int)(loc4-0.43*255-20));
		    g.drawLine(30-3, (int)(loc4-0.43*255-20)+7, 30, (int)(loc4-0.43*255-20));
		    g.drawLine(30+3, (int)(loc4-0.43*255-20)+7, 30, (int)(loc4-0.43*255-20));
		    g.drawLine(30,loc4, imgwid+55, loc4);
		    g.drawLine(imgwid+48, loc4-3, imgwid+55, loc4);
		    g.drawLine(imgwid+48, loc4+3, imgwid+55, loc4);
		    g.drawString("输出图像蓝光强度横中轴截面", 30, loc4+12);
	        
	        g.drawPolyline(p1.xpoints,p1.ypoints,p1.npoints); 
	        g.drawPolyline(p2.xpoints,p2.ypoints,p2.npoints); 
	        g.drawPolyline(p3.xpoints,p3.ypoints,p3.npoints); 
	        g.drawPolyline(p4.xpoints,p4.ypoints,p4.npoints); 
	    }  
	
} 
    
   
       
}
