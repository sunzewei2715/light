package net.yeah.veda4085.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import net.yeah.veda4085.algorithms.MultiIntervention;

public class MultiInt {

	public JPanel centerPanel;
	private JPanel leftP;
	@SuppressWarnings("serial")
	private JPanel rightP = new JPanel(){
		public void paint(Graphics g){
			g.drawImage(bufferImg, 0, 0, null);
		}
	};
	
	Image bufferImg = null;
	
	int re = 1;
	int num = 1;
	double n1T = 1.0;
	int integ = 0;
	private JPanel machineP, parameterP;
	private JLabel wl1L, wl2L, wl3L, n2L, rL, tL;
	private JTextField wl1T, wl2T, wl3T, n2T, rT, tT;
	private JButton refl, refr, one, two, three, inte;
	
	public JPanel launchFrame() {
		centerPanel = new JPanel(new BorderLayout());

		produceLeftP();
		centerPanel.add(leftP, BorderLayout.WEST);

		produceRightP();
		centerPanel.add(rightP, BorderLayout.CENTER);

		return centerPanel;
	}
	
	public void produceLeftP() {
		leftP = new JPanel(new BorderLayout());
		leftP.setPreferredSize(new Dimension(400, centerPanel.getHeight()));
		
		machineP = new JPanel(); // 加入面板
		machineP.setBackground(Color.WHITE);

		try {
			Image image = ImageIO.read(getClass().getResource("/picture/multi.png"));
			BufferedImage tag = new BufferedImage(330, 330,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(330, 330, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			ImageIcon picture = new ImageIcon(image);
			machineP.add(new JLabel(picture));
		} catch (Exception e) {
	}
		
		
		parameterP = new JPanel(new GridLayout(8, 3)); // 加入面板
		parameterP.setBackground(Color.LIGHT_GRAY);
		
		
		parameterP.setPreferredSize(new Dimension(460, 200));
		
		wl1L = new JLabel("波长1");
		wl2L = new JLabel("波长2");
		wl3L = new JLabel("波长3");
		n2L = new JLabel("折射率");
		rL = new JLabel("反射系数");
		tL = new JLabel("厚度");
		
		refl = new JButton("反射光");
		refr = new JButton("透射光");
		one = new JButton("单光波");
		two = new JButton("双光波");
		three = new JButton("三光波");
		inte = new JButton("反射亮度归一");
		refl.addActionListener(new ReflListener());
		refr.addActionListener(new RefrListener());
		one.addActionListener(new OneListener());
		two.addActionListener(new TwoListener());
		three.addActionListener(new ThreeListener());
		inte.addActionListener(new InteListener());
		
		wl1T = new JTextField("380");
		wl2T = new JTextField("450");
		wl3T = new JTextField("620");
		n2T = new JTextField("1.0");
		rT = new JTextField("0.3");
		tT = new JTextField("0.1");
		
		wl1T.addActionListener(new Wl1TListerer());
		wl2T.addActionListener(new Wl2TListerer());
		wl3T.addActionListener(new Wl3TListerer());
		n2T.addActionListener(new N2TListerer());
		rT.addActionListener(new RTListerer());
		tT.addActionListener(new TTListerer());
		
		JScrollBar wl1s = new JScrollBar(JScrollBar.HORIZONTAL, 380, 0, 380,
				770);
		JScrollBar wl2s = new JScrollBar(JScrollBar.HORIZONTAL, 450, 0, 380,
				770);
		JScrollBar wl3s = new JScrollBar(JScrollBar.HORIZONTAL, 620, 0, 380,
				770);
		JScrollBar n2s = new JScrollBar(JScrollBar.HORIZONTAL, 50, 0, 50,
				150);
		JScrollBar rs = new JScrollBar(JScrollBar.HORIZONTAL, 30, 0, 30,
				90);
		JScrollBar ts = new JScrollBar(JScrollBar.HORIZONTAL, 10, 0, 10,
				300);
		
		wl1s.addAdjustmentListener(new Wl1sListener());
		wl2s.addAdjustmentListener(new Wl2sListener());
		wl3s.addAdjustmentListener(new Wl3sListener());
		n2s.addAdjustmentListener(new N2sListener());
		rs.addAdjustmentListener(new RsListener());
		ts.addAdjustmentListener(new TsListener());
		
		parameterP.add(one);
		parameterP.add(two);
		parameterP.add(three);
		parameterP.add(inte);
		parameterP.add(refl);
		parameterP.add(refr);
		parameterP.add(wl1L);
		parameterP.add(wl1T);
		parameterP.add(wl1s);
		parameterP.add(wl2L);
		parameterP.add(wl2T);
		parameterP.add(wl2s);
		parameterP.add(wl3L);
		parameterP.add(wl3T);
		parameterP.add(wl3s);
		parameterP.add(n2L);
		parameterP.add(n2T);
		parameterP.add(n2s);
		parameterP.add(rL);
		parameterP.add(rT);
		parameterP.add(rs);
		parameterP.add(tL);
		parameterP.add(tT);
		parameterP.add(ts);
		
		leftP.add(machineP, BorderLayout.CENTER); 
		leftP.add(parameterP, BorderLayout.SOUTH);

	}
		
	public void produceRightP() {
		rightP.setBackground(Color.WHITE);
	}
	
	class Wl1TListerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String w1 = f.getActionCommand();
			
			wl1T.setText(String.valueOf(w1));
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
			
		}
	}
		
	class Wl2TListerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String w2 = f.getActionCommand();
			
			wl2T.setText(String.valueOf(w2));
			
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
		}
	}
		
	class Wl3TListerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String w3 = f.getActionCommand();
			
			wl3T.setText(String.valueOf(w3));
			
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==3)
				draw(6);
		}
	}
			
		    
	class N2TListerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String nn2 = f.getActionCommand();
			
			n2T.setText(String.valueOf(nn2));
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
		}
	}
					
	class RTListerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String rr = f.getActionCommand();
			
			rT.setText(String.valueOf(rr));
			
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
		}						
	}
						
	class TTListerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String tt  = f.getActionCommand();
			
			tT.setText(String.valueOf(tt));
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
			
		}					
	}
							
	class Wl1sListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wll1 = e.getValue();

			wl1T.setText(String.valueOf(wll1));
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
			
		}					
	}
								
	class Wl2sListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wll2 = e.getValue();

			wl2T.setText(String.valueOf(wll2));
			
			
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
			
		}				
	}
									
	class Wl3sListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wll3 = e.getValue();

			wl3T.setText(String.valueOf(wll3));
			
			if(re==1&&num==3)
				draw(3);
			
			if(re==2&&num==3)
				draw(6);
			
		}				
	}	
		
	class N2sListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double n22 = e.getValue()/50.0;
			
			n2T.setText(String.valueOf(n22));
			
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
		}
	}
			
	class RsListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double rrs = e.getValue()/100.0;
			
			rT.setText(String.valueOf(rrs));
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
					
		}
	}
	
	class TsListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double tts = e.getValue()/100.0;
			
			tT.setText(String.valueOf(tts));
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
		}
	}
		
	class ReflListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			re = 1;
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
		}
	}
	
	class RefrListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			re = 2;
			
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
		}
	}
						
	class OneListener implements ActionListener{
		public void actionPerformed(ActionEvent e){				
			num = 1;
		}
	}
	
	class TwoListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			num = 2;
		}		
	}
	
	class ThreeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			num = 3;
		}
	}

	class InteListener implements ActionListener{
		public void actionPerformed(ActionEvent e){	
			integ = 1;
			if(re==1&&num==1)
				draw(1);
			if(re==1&&num==2)
				draw(2);
			if(re==1&&num==3)
				draw(3);
			if(re==2&&num==1)
				draw(4);
			if(re==2&&num==2)
				draw(5);
			if(re==2&&num==3)
				draw(6);
			integ = 0;
		}
	}
		
	public void drawOral(int i, Graphics2D bufG, int initialDrawX, int initialDrawY) {
		bufG.drawOval(initialDrawX - i, initialDrawY - i, 2*i, 2*i);
		bufG.drawOval(initialDrawX + 1 - i, initialDrawY + 1 - i, 2*i, 2*i);
		bufG.drawOval(initialDrawX - 1 - i, initialDrawY - 1 - i, 2*i, 2*i);
		bufG.drawOval(initialDrawX + 1 - i, initialDrawY - 1 - i, 2*i, 2*i);
		bufG.drawOval(initialDrawX - 1 - i, initialDrawY + 1 - i, 2*i, 2*i);    
	}
	
	public void drawLine(ArrayList<Double> K, int i, Graphics2D bufG, int initialDrawX, int initialDrawY) {
		bufG.drawLine(initialDrawX + i -1, rightP.getHeight()-30 - (int)(200 * K.get(i - 1)), initialDrawX + i, rightP.getHeight()-30 - (int)(200 * K.get(i)));
		bufG.drawLine(initialDrawX + i, rightP.getHeight() -30- (int)(200 * K.get(i - 1)), initialDrawX + i + 1, rightP.getHeight()-30 - (int)(200 * K.get(i)));
	    bufG.drawLine(initialDrawX - i + 1, rightP.getHeight()-30 - (int)(200 * K.get(i - 1)), initialDrawX - i, rightP.getHeight() - 30-(int)(200 * K.get(i)));
	    bufG.drawLine(initialDrawX - i + 2, rightP.getHeight() - 30-(int)(200 * K.get(i - 1)), initialDrawX - i + 1, rightP.getHeight() -30- (int)(200 * K.get(i)));
	}
		
	public void drawBackGround(Graphics2D bufG, int initialDrawX, int initialDrawY) {
		bufG.setColor(Color.BLACK);
		bufG.setStroke(new BasicStroke(2));
		bufG.drawLine(10, rightP.getHeight()-30, rightP.getWidth() - 10, rightP.getHeight()-30);
		bufG.drawLine(initialDrawX, rightP.getHeight(), initialDrawX, rightP.getHeight() - 150);
		bufG.setFont(new Font("宋体",Font.BOLD,30));
		bufG.drawString("Y", rightP.getWidth() - 30, rightP.getHeight() - 40);
		bufG.drawString("I", rightP.getWidth()/2 + 10, rightP.getHeight() - 150);
	}
	
	public void norm(ArrayList<Double> K) {
		if(K==null) {
			return;
		}
		double max = 0;
		for(int j = 0; j < K.size(); j++){
			if(max < K.get(j)){
				max = K.get(j);
			}
		}
		for(int j = 0; j < K.size(); j++){
			K.set(j, K.get(j)/max);
		}
		return;
	}
	
    public void draw(int sign) {
    	
		double wl1 = Double.parseDouble(wl1T.getText());
		double wl2 = Double.parseDouble(wl2T.getText());
		double wl3 = Double.parseDouble(wl3T.getText());
	    	double n1 = n1T;
	    	double n2 = Double.parseDouble(n2T.getText());
	    	double r = Double.parseDouble(rT.getText());
	    	double t = Double.parseDouble(tT.getText());
	    	
	    	Graphics g = rightP.getGraphics();
	    	g.drawImage(null, 0, 0, null);
		Color c = Color.WHITE;
	
		// 以下8行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(),
				rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));
		
		MultiIntervention alg31 = new MultiIntervention(wl1, n1, n2, r, t);
		MultiIntervention alg32 = new MultiIntervention(wl2, n1, n2, r, t);
		MultiIntervention alg33 = new MultiIntervention(wl3, n1, n2, r, t);
		ArrayList<Double> K1=null, K2=null, K3=null;
		
		if(sign<=3) 
			K1 = alg31.allIntensity2(200);
		if(sign<=3 && sign>=2)
			K2 = alg32.allIntensity2(200);			
		if(sign==3)
			K3 = alg33.allIntensity2(200);			
		if(sign>=4)
			K1 = alg31.allIntensity1(200);
		if(sign>=5)
			K2 = alg32.allIntensity1(200);			
		if(sign==6)
			K3 = alg33.allIntensity1(200);			
		
		//归一化
		norm(K1);
		norm(K2);
		norm(K3);
		 
		int initialDrawX = rightP.getWidth()/2;
		int initialDrawY = rightP.getHeight()/3 + 10;
		
		for (int i = 0; i < 200; i++) {
			if(sign%3==1) {
				c = new InitColor().WavlenChangetoRGB((int) wl1, (float)(K1.get(i)/1));
			    bufG.setColor(c);  
			} else if(sign%3==2) {
				Color c1 = new InitColor().WavlenChangetoRGB((int) wl1,
						(float)(K1.get(i)/1));
				Color c2 = new InitColor().WavlenChangetoRGB((int) wl2,
						(float)(K2.get(i)/1));
				Color c3 = overLay(c1, c2);
				bufG.setColor(c3);
			} else {
				Color c1 = new InitColor().WavlenChangetoRGB((int) wl1,
						(float)(K1.get(i)/1));
				Color c2 = new InitColor().WavlenChangetoRGB((int) wl2,
						(float)(K2.get(i)/1));
				Color c3 = new InitColor().WavlenChangetoRGB((int) wl3,
						(float)(K3.get(i)/1));
				Color c4 = overLay(c1, c2);
				Color c5 = overLay(c4, c3);
				bufG.setColor(c5);
			}
			
			bufG.setStroke(new BasicStroke(2));
			drawOral(i, bufG, initialDrawX, initialDrawY);
			bufG.setStroke(new BasicStroke(1));
		    if(i > 0){
		    		if(sign%3==1)
		    			drawLine(K1, i, bufG, initialDrawX, initialDrawY);
		    		if(sign%3==2) {
		 		   	bufG.setColor(Color.blue);
		 		   	drawLine(K1, i, bufG, initialDrawX, initialDrawY);   
			 		bufG.setColor(Color.green);
				 	drawLine(K2, i, bufG, initialDrawX, initialDrawY);
		    			
		    		}
		    	}
		}
		if(sign%3!=0)
			drawBackGround(bufG, initialDrawX, initialDrawY);
		g.drawImage(bufferImg, 0, 0, null);
    }
		
	public Color overLay(Color basic, Color over){ 
		int[] rgbA = new int[3];
		rgbA[ 0 ]  =  basic.getRed();
        rgbA[ 1 ]  =  basic.getGreen();
        rgbA[ 2 ]  =  basic.getBlue();
        //int alphaA = basic.getAlpha();
        
        int[] rgbB = new int[3];
		rgbB[ 0 ]  = over.getRed();
        rgbB[ 1 ]  = over.getGreen();
        rgbB[ 2 ]  = over.getBlue();
        //int alphaB = over.getAlpha();
        
        int[] rgbC = new int[3];
        rgbC[0] = (int) (rgbB[0] * 0.5 + rgbA[0]*(1-0.5));
        rgbC[1] = (int) (rgbB[1] * 0.5 + rgbA[1]*(1-0.5));
        rgbC[2] = (int) (rgbB[2] * 0.5 + rgbA[2]*(1-0.5));
        
        return new Color(rgbC[0], rgbC[1], rgbC[2], 255).brighter();
	}
}

