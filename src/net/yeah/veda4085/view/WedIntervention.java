package net.yeah.veda4085.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.border.BevelBorder;

public class WedIntervention {
	
	Image bufferImg = null;
	public JPanel centerPanel;
	private JPanel leftP;
	@SuppressWarnings("serial")
	private JPanel rightP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 0, 0, null);
		}
	};
	private JPanel machineP, parameterP;

	private JLabel label1, label2, label3, label4, label5,label6;
	private JTextField waveLength,height,light;
	

	private JScrollBar scroll1, scroll2,scroll3;
	
	private double h=400,w=380,l=200;

	public JPanel launchFrame() {

		centerPanel = new JPanel(new BorderLayout());

		produceLeftP();
		centerPanel.add(leftP, BorderLayout.WEST);

		produceRightP();
		centerPanel.add(rightP, BorderLayout.CENTER);

		return centerPanel;
	}

	public void produceRightP() {
		rightP.setBackground(Color.WHITE);
	}

	public void produceLeftP() {
		// TODO Auto-generated method stub
		leftP = new JPanel(new BorderLayout());
		leftP.setPreferredSize(new Dimension(400, centerPanel.getHeight()));

		machineP = new JPanel(); // 加入面板
		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		parameterP.setBackground(Color.LIGHT_GRAY);
		
		try {
			Image image = ImageIO.read(getClass().getResource("/picture/楔形板干涉.jpg"));
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

		parameterP.setLayout(new GridLayout(4, 3));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0, 3800, 7700);// 设置滚动条
		scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 4000, 0, 4000, 10000);
		scroll3=new JScrollBar(JScrollBar.HORIZONTAL,200,0,200,500);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll3.addAdjustmentListener(new Scroll3Listener());

		waveLength = new JTextField();
		height = new JTextField();
		light = new JTextField();

		waveLength.setText("380.0");
		height.setText("400.0");
		light.setText("200");

		label1 = new JLabel("参数列表：");
		label2 = new JLabel("当前值");
		label3 = new JLabel("选择");
		label4 = new JLabel("lambda(380-770)nm");
		label5 = new JLabel("末端高度差(10-100)nm");
		label6= new JLabel("光强");

		parameterP.add(label1);
		parameterP.add(label2);
		parameterP.add(label3);
		parameterP.add(label4);
		parameterP.add(waveLength);
		parameterP.add(scroll1);
		parameterP.add(label5);
		parameterP.add(height);
		parameterP.add(scroll2);
		parameterP.add(label6);
		parameterP.add(light);
		parameterP.add(scroll3);

		leftP.add(machineP, BorderLayout.CENTER);
		leftP.add(parameterP, BorderLayout.SOUTH); // 输入面板加入到窗口
	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;
			
			w=wave;

			waveLength.setText(String.valueOf(wave));

			draw();

		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;
			
			h=wave;

			height.setText(String.valueOf(wave));

			draw();
		}
	}
	
	class Scroll3Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 1.0;
			
			l=(int)wave;

			light.setText(String.valueOf(l));

			draw();
		}
	}

	private void draw() {
		// TODO Auto-generated method stub
		Graphics g = rightP.getGraphics();
		
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(Color.BLACK);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景
		
		int initialDrawX = rightP.getWidth() / 2;
		int initialDrawY = rightP.getHeight() / 3;
		
		int initialCurveX = rightP.getWidth() / 2;
		int initialCurveY = rightP.getHeight() / 3 * 2 + 30;
		
		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // 设置画笔大小
		
		bufG.setStroke(s);
		bufG.setColor(Color.WHITE);

		// 画坐标轴
		bufG.drawLine(5, 2, 5, initialDrawY * 2);
		bufG.drawLine(5, initialDrawY * 2, rightP.getWidth() - 20, initialDrawY * 2);

		bufG.drawLine(5, initialCurveY - 10, 5, rightP.getHeight() - 20);
		bufG.drawLine(5, rightP.getHeight() - 20, rightP.getWidth() - 20,
				rightP.getHeight() - 20);
		// 改变字体大小
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", 20, 30);
		bufG.drawString("X", rightP.getWidth() - 25, initialDrawY*2-20);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("I", 20, initialCurveY - 5);
		bufG.drawString("X", rightP.getWidth() - 20, rightP.getHeight()-40);
		
		for(int i=0; i<480; i++){
			double I = (1.9216+1.92*Math.cos(4*Math.PI*i*h/w/120-Math.PI))/3.8316;
			int IP = (int)(I*150*l/500.0);
			Color c = new InitColor().WavlenChangetoColor((int) w);
			bufG.setColor(c);
			bufG.drawLine(5+i, rightP.getHeight()-20-IP, 5+i, rightP.getHeight()-30-IP);
			c = new InitColor().WavlenChangetoRGB((int) w, (float) (I));
			bufG.setColor(c);
			if(i>0)
				bufG.drawLine(5+i, initialDrawY*2-1, 5+i, initialDrawY*2-340);
		}
		
		

		g.drawImage(bufferImg, 0, 0, null);

	}

}
