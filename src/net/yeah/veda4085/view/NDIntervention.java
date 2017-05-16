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

import net.yeah.veda4085.algorithms.NDAlg;

public class NDIntervention {
	final int timeToRepaint = 40;
	int pre = 3, pre2 = 0;
	public JPanel centerPanel;
	private JPanel leftP;
	@SuppressWarnings("serial")
	private JPanel rightP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 0, 0, null);
		}
	};

	Image bufferImg = null;

	private JPanel machineP, parameterP;

	private JTextField waveLength;
	JTextField radius;
	private JTextField jingdu;
	private JTextField jingdu2;
	private Component label0;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;

	private JButton white;
	private boolean whiteIntervention = false;
	private JButton single;
	
	private JScrollBar scroll1,scroll2,scroll3,scroll4;

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
		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		parameterP.setBackground(Color.LIGHT_GRAY);
		try {
			Image image = ImageIO.read(getClass().getResource("/picture/ND.gif"));
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

		parameterP.setLayout(new GridLayout(5, 3));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0,
				3800, 7700);// 设置滚动条
		scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0,
				1000, 4000);
		scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 3, 0, 3, 100);
		scroll4 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 10);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll4.addAdjustmentListener(new Scroll4Listener());

		waveLength = new JTextField(); // 输入波长，距离
		radius = new JTextField();
		jingdu = new JTextField();
		jingdu2 = new JTextField();

		waveLength.setText("380.0");
		radius.setText("100.0");
		jingdu.setText("");
		jingdu2.setText("");

		white = new JButton("白光");
		single = new JButton("单色光");

		white.addActionListener(new WhiteListener());
		single.addActionListener(new SingleListener());

		label0 = new JLabel("参数选择:"); // 标签
		label1 = new JLabel("lambda (380-770)nm");
		label2 = new JLabel("半径 r (100-400)mm");
		label3 = new JLabel("白光精度细调(3-100)");
		label4 = new JLabel("白光精度粗调(0-1000)");
		parameterP.add(label0); // 输入数据面板
		parameterP.add(single);
		parameterP.add(white);
		parameterP.add(label1);
		parameterP.add(waveLength);
		parameterP.add(scroll1);
		parameterP.add(label2);
		parameterP.add(radius);
		parameterP.add(scroll2);
		parameterP.add(label3);
		parameterP.add(jingdu);
		parameterP.add(scroll3);
		parameterP.add(label4);
		parameterP.add(jingdu2);
		parameterP.add(scroll4);

		leftP.add(machineP, BorderLayout.CENTER);
		leftP.add(parameterP, BorderLayout.SOUTH); // 输入面板加入到窗口
	}

	public void produceRightP() {
		rightP.setBackground(Color.WHITE);
	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			waveLength.setText(String.valueOf(wave));

			if (!whiteIntervention)
				draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			radius.setText(String.valueOf(wave));

			if (whiteIntervention)
				drawWhite();
			else
				draw();
		}
	}

	class Scroll3Listener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			pre = e.getValue();
			jingdu.setText(String.valueOf(pre));
			if (whiteIntervention) {
				drawWhite();// TODO Auto-generated method stub
			}
		}

	}

	class Scroll4Listener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			pre2 = e.getValue();
			jingdu2.setText(String.valueOf(pre2 * 100));
			if (whiteIntervention) {
				drawWhite();// TODO Auto-generated method stub
			}
		}
	}

	class WhiteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			whiteIntervention = true;
			drawWhite();
		}
	}

	class SingleListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			whiteIntervention = false;
			draw();
		}
	}

	public void draw() {
		double WL = Double.parseDouble(waveLength.getText()); // 波长
		double R = Double.parseDouble(radius.getText()); // 曲率半径

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// 以下3行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		NDAlg ndAlg = new NDAlg(R, WL, (pre + 100 * pre2));

		int initialDrawX = rightP.getWidth() / 2;
		int initialDrawY = rightP.getHeight() / 3;
		int size = initialDrawY;

		int initialCurveX = rightP.getWidth() / 2;
		int initialCurveY = rightP.getHeight() / 3 * 2 + 30;

		ArrayList<Double> A = ndAlg.allIntensity(size);

		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // 设置画笔大小
		for (int i = 0; i < A.size(); i++) {
			c = new InitColor().WavlenChangetoRGB((int) WL,
					(float) (A.get(i) / 2));
			bufG.setColor(c);
			bufG.drawOval(initialDrawX - i, initialDrawY - i, 2 * i, 2 * i);
			bufG.drawOval(initialDrawX + 1 - i, initialDrawY + 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX - 1 - i, initialDrawY - 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX + 1 - i, initialDrawY - 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX - 1 - i, initialDrawY + 1 - i, 2 * i,
					2 * i);
			if (i > 0) {
				bufG.drawLine(i - 1 + initialCurveX, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92, i + initialCurveX,
						rightP.getHeight() - (int) (A.get(i) * 35) - 92);
				bufG.drawLine(i - 1 + initialCurveX + 1, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92,
						i + initialCurveX + 1,
	    				rightP.getHeight() - (int) (A.get(i) * 35) - 92);

				bufG.drawLine(-(i - 1) + initialCurveX, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92, -i + initialCurveX,
						rightP.getHeight() - (int) (A.get(i) * 35) - 92);
				bufG.drawLine(-(i - 1) + initialCurveX + 1, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92, -i + initialCurveX
	     				+ 1, rightP.getHeight() - (int) (A.get(i) * 35) - 92);
			}
		}

		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);

		// 画坐标轴
		bufG.drawLine(initialDrawX, 2, initialDrawX, initialDrawY * 2 + 10);
		bufG.drawLine(5, initialDrawY, rightP.getWidth() - 20, initialDrawY);

		bufG.drawLine(initialCurveX, initialCurveY - 10, initialCurveX,
				rightP.getHeight() - 20);
		bufG.drawLine(5, initialCurveY + 72, rightP.getWidth() - 20,
				initialCurveY + 72);
		// 改变字体大小
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", initialDrawX + 100, 30);
		bufG.drawString("X", rightP.getWidth() - 25, initialDrawY);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("I", initialCurveX, initialCurveY - 5);
		bufG.drawString("X", rightP.getWidth() - 20, initialCurveY + 72);

		g.drawImage(bufferImg, 0, 0, null);
	}

	public void drawWhite() {
		double WL = Double.parseDouble(waveLength.getText()); // 波长
		double R = Double.parseDouble(radius.getText()); // 曲率半径

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// 以下3行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		NDAlg ndAlg = new NDAlg(R, WL, (pre + 100 * pre2)); // 初始化类；

		int initialDrawX = rightP.getWidth() / 2;
		int initialDrawY = rightP.getHeight() / 3;
		int size = initialDrawY;

		int initialCurveX = rightP.getWidth() / 2;
		int initialCurveY = rightP.getHeight() / 3 * 2 + 30;
		double test = 0;
		ArrayList<Double> A = ndAlg.getwhite(size);
		ArrayList<Color> B = ndAlg.getColor(size);
		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // 设置画笔大小
		for (int i = 0; i < A.size(); i++) {
			c = B.get(i);
			if (A.get(i) > test) {
				test = A.get(i);
			}
			bufG.setColor(c);
			bufG.drawOval(initialDrawX - i, initialDrawY - i, 2 * i, 2 * i);
			bufG.drawOval(initialDrawX + 1 - i, initialDrawY + 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX - 1 - i, initialDrawY - 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX + 1 - i, initialDrawY - 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX - 1 - i, initialDrawY + 1 - i, 2 * i,
					2 * i);
			if (i > 0) {
				bufG.drawLine(i - 1 + initialCurveX, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92, i + initialCurveX,
						rightP.getHeight() - (int) (A.get(i) * 35) - 92);
				bufG.drawLine(i - 1 + initialCurveX + 1, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92,
						i + initialCurveX + 1,
						rightP.getHeight() - (int) (A.get(i) * 35) - 92);

				bufG.drawLine(-(i - 1) + initialCurveX, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92, -i + initialCurveX,
						rightP.getHeight() - (int) (A.get(i) * 35) - 92);
				bufG.drawLine(-(i - 1) + initialCurveX + 1, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) - 92, -i + initialCurveX
						+ 1, rightP.getHeight() - (int) (A.get(i) * 35) - 92);
			}
		}

		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);

		// 画坐标轴
		bufG.drawLine(initialDrawX, 2, initialDrawX, initialDrawY * 2 + 10);
		bufG.drawLine(5, initialDrawY, rightP.getWidth() - 20, initialDrawY);

		bufG.drawLine(initialCurveX, initialCurveY - 10, initialCurveX,
				rightP.getHeight() - 20);
		bufG.drawLine(5, initialCurveY + 72, rightP.getWidth() - 20,
				initialCurveY + 72);
		// 改变字体大小
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", initialDrawX + 100, 30);
		bufG.drawString("X", rightP.getWidth() - 25, initialDrawY);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("I", initialCurveX, initialCurveY - 5);
		bufG.drawString("X", rightP.getWidth() - 20, initialCurveY + 72);

		g.drawImage(bufferImg, 0, 0, null);
		System.out.println(test);
	}
}
