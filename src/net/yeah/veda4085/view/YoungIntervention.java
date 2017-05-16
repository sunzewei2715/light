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

import net.yeah.veda4085.algorithms.YoungAlg;

public class YoungIntervention {
	public JPanel centerPanel;
	private JPanel leftP, rightP;
	private JPanel machineP, parameterP;

	@SuppressWarnings("serial")
	private JPanel imageUpP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImgUp, 0, 0, null);
		}
	};
	@SuppressWarnings("serial")
	private JPanel imageDownP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImgDown, 0, 0, null);
		}
	};
	Image bufferImgUp = null;
	Image bufferImgDown = null;

	private JLabel label0;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JTextField waveLength;
	private JTextField shuangfeng;
	private JTextField Pre;
	private JTextField juli;
	private JButton white;
	private boolean whiteIntervention = false;
	private JButton single;
	private int jingdu = 3;

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
			Image image = ImageIO.read(getClass().getResource("/picture/Young.png"));
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

		JScrollBar scrollU = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0,
				3800, 7700);// 设置滚动条
		JScrollBar scrollM = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				500);
		JScrollBar scrollD = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				500);
		JScrollBar scrollPre = new JScrollBar(JScrollBar.HORIZONTAL, 3, 0, 3,
				500);
		scrollU.addAdjustmentListener(new ScrollUListener());
		scrollM.addAdjustmentListener(new ScrollMListener());
		scrollD.addAdjustmentListener(new ScrollDListener());
		scrollPre.addAdjustmentListener(new ScrollPreListener());

		waveLength = new JTextField(20); // 输入波长，双逢距离，屏到双缝距离的文本
		shuangfeng = new JTextField(20);
		juli = new JTextField(20);
		Pre = new JTextField(20);
		waveLength.setText("380.0");
		shuangfeng.setText("10.0");
		juli.setText("10.0");
		Pre.setText("3");

		label0 = new JLabel("参数选择:"); // 标签
		label1 = new JLabel("lambda (380-770)nm");
		label2 = new JLabel("双缝距离 d (10-50)um");
		label3 = new JLabel("缝屏距离 L (10-50)cm");
		label4 = new JLabel("白光精度(3-500)份");
		white = new JButton("白光");
		white.addActionListener(new WhiteListener());
		single = new JButton("单色光");
		single.addActionListener(new SingleListener());

		parameterP.add(label0); // 输入数据面板
		parameterP.add(single);
		parameterP.add(white);
		parameterP.add(label1);
		parameterP.add(waveLength);
		parameterP.add(scrollU);
		parameterP.add(label2);
		parameterP.add(shuangfeng);
		parameterP.add(scrollM);
		parameterP.add(label3);
		parameterP.add(juli);
		parameterP.add(scrollD);
		parameterP.add(label4);
		parameterP.add(Pre);
		parameterP.add(scrollPre);
		leftP.add(machineP, BorderLayout.CENTER); // 输入面板加入到窗口
		leftP.add(parameterP, BorderLayout.SOUTH);

	}

	public void produceRightP() {
		rightP = new JPanel(new GridLayout(2, 1));
		rightP.add(imageUpP);
		rightP.add(imageDownP);
	}

	class ScrollUListener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			waveLength.setText(String.valueOf(wave));

			if (whiteIntervention) {
				drawWhite();
			} else {
				draw();
			}
		}
	}

	class ScrollMListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			shuangfeng.setText(String.valueOf(wave));

			if (whiteIntervention) {
				drawWhite();
			} else {
				draw();
			}
		}
	}

	class ScrollDListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			juli.setText(String.valueOf(wave));

			if (whiteIntervention) {
				drawWhite();
			} else {
				draw();
			}
		}
	}

	class ScrollPreListener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			jingdu = e.getValue();
			Pre.setText(String.valueOf(jingdu));
			if (whiteIntervention) {
				drawWhite();
			}
		}

	}

	class WhiteListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			whiteIntervention = true;
			drawWhite();
		}
	}

	class SingleListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			whiteIntervention = false;
			draw();
		}
	}

	public void draw() {

		double WL = Double.parseDouble(waveLength.getText()); // 波长
		double b = Double.parseDouble(shuangfeng.getText()); // 双峰距离
		double L = Double.parseDouble(juli.getText()); // 双缝到屏的距离

		Graphics gu = imageUpP.getGraphics();
		Graphics gd = imageDownP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImgUp = imageUpP.createImage(imageUpP.getWidth(),
				imageUpP.getHeight());
		Graphics2D bufGU = (Graphics2D) bufferImgUp.getGraphics();
		bufGU.setColor(c);
		bufGU.fillRect(0, 0, bufferImgUp.getWidth(null),
				bufferImgUp.getHeight(null));// 背景

		bufferImgDown = imageDownP.createImage(imageDownP.getWidth(),
				imageDownP.getHeight());
		Graphics2D bufGD = (Graphics2D) bufferImgDown.getGraphics();
		bufGD.setColor(c);
		bufGD.fillRect(0, 0, bufferImgDown.getWidth(null),
				bufferImgDown.getHeight(null));// 背景

		YoungAlg YAlg = new YoungAlg(L, b, WL, jingdu);

		int initialDrawX = 80;
		int initialDrawY = 30;
		int size = imageUpP.getWidth() - 2 * initialDrawX;

		ArrayList<Double> A = YAlg.allIntensity(size);

		BasicStroke su = (BasicStroke) bufGU.getStroke();
		BasicStroke sd = (BasicStroke) bufGD.getStroke();
		bufGU.setStroke(new BasicStroke(2)); // 设置画笔大小
		bufGD.setStroke(new BasicStroke(2));
		for (int i = 0; i < A.size(); i++) {
			c = new InitColor().WavlenChangetoRGB((int) WL,
					(float) (A.get(i) / 2));
			bufGU.setColor(c);
			bufGU.drawLine(i + initialDrawX, initialDrawY, i + initialDrawX,
					initialDrawY + 150);
			bufGD.setColor(c);

			if (i > 0) {
				bufGD.drawLine(i - 1 + initialDrawX, imageDownP.getHeight()
						- (int) (A.get(i - 1) * 50) - 155, i + initialDrawX,
						imageDownP.getHeight() - (int) (A.get(i) * 50) - 155);
				bufGD.drawLine(i - 1 + initialDrawX + 1, imageDownP.getHeight()
						- (int) (A.get(i - 1) * 50) - 155,
						i + initialDrawX + 1,
						imageDownP.getHeight() - (int) (A.get(i) * 50) - 155);
			}
		}

		bufGU.setStroke(su); // 设置画笔大小
		bufGD.setStroke(sd);
		bufGU.setColor(Color.BLACK);
		bufGD.setColor(Color.BLACK);

		// 画坐标轴
		bufGU.drawLine(imageUpP.getWidth() / 2, 5, imageUpP.getWidth() / 2,
				imageUpP.getHeight() - 50);
		bufGU.drawLine(initialDrawX - 50, initialDrawY + 75,
				imageUpP.getWidth() - initialDrawX + 50, initialDrawY + 75);

		bufGD.drawLine(imageDownP.getWidth() / 2, 2, imageDownP.getWidth() / 2,
				imageDownP.getHeight() - 130);
		bufGD.drawLine(initialDrawX - 50, initialDrawY + 105,
				imageUpP.getWidth() - initialDrawX + 50, initialDrawY + 105);

		// 改变字体大小
		bufGU.setFont(new Font("宋体", Font.BOLD, 30));
		bufGU.drawString("Y", imageUpP.getWidth() / 2 + 10, 25);
		bufGU.drawString("X", imageUpP.getWidth() - initialDrawX + 50,
				initialDrawY + 105);
		bufGD.setFont(new Font("宋体", Font.BOLD, 30));
		bufGD.drawString("I", imageDownP.getWidth() / 2 + 10, 30);
		bufGD.drawString("X", imageDownP.getWidth() - initialDrawX + 55,
				initialDrawY + 105);

		gu.drawImage(bufferImgUp, 0, 0, null);
		gd.drawImage(bufferImgDown, 0, 0, null);
	}

	public void drawWhite() {
		double WL = Double.parseDouble(waveLength.getText()); // 波长
		double b = Double.parseDouble(shuangfeng.getText()); // 双峰距离
		double L = Double.parseDouble(juli.getText()); // 双缝到屏的距离

		Graphics gu = imageUpP.getGraphics();
		Graphics gd = imageDownP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImgUp = imageUpP.createImage(imageUpP.getWidth(),
				imageUpP.getHeight());
		Graphics2D bufGU = (Graphics2D) bufferImgUp.getGraphics();
		bufGU.setColor(c);
		bufGU.fillRect(0, 0, bufferImgUp.getWidth(null),
				bufferImgUp.getHeight(null));// 背景

		bufferImgDown = imageDownP.createImage(imageDownP.getWidth(),
				imageDownP.getHeight());
		Graphics2D bufGD = (Graphics2D) bufferImgDown.getGraphics();
		bufGD.setColor(c);
		bufGD.fillRect(0, 0, bufferImgDown.getWidth(null),
				bufferImgDown.getHeight(null));// 背景

		YoungAlg YAlg = new YoungAlg(L, b, WL, jingdu);

		int initialDrawX = 80;
		int initialDrawY = 30;
		int size = imageUpP.getWidth() - 2 * initialDrawX;

		ArrayList<Double> A = YAlg.getwhite(size);
		ArrayList<Color> color = YAlg.getColor(size);
		BasicStroke su = (BasicStroke) bufGU.getStroke();
		BasicStroke sd = (BasicStroke) bufGD.getStroke();
		bufGU.setStroke(new BasicStroke(2)); // 设置画笔大小
		bufGD.setStroke(new BasicStroke(2));
		for (int i = 0; i < size; i++) {
			c = color.get(i);
			bufGU.setColor(c);
			bufGU.drawLine(i + initialDrawX, initialDrawY, i + initialDrawX,
					initialDrawY + 150);
			bufGD.setColor(c);

			if (i > 0) {
				bufGD.drawLine(i - 1 + initialDrawX, imageDownP.getHeight()
						- (int) (A.get(i - 1) * 50) - 155, i + initialDrawX,
						imageDownP.getHeight() - (int) (A.get(i) * 50) - 155);
				bufGD.drawLine(i - 1 + initialDrawX + 1, imageDownP.getHeight()
						- (int) (A.get(i - 1) * 50) - 155,
						i + initialDrawX + 1,
						imageDownP.getHeight() - (int) (A.get(i) * 50) - 155);
			}
		}

		bufGU.setStroke(su); // 设置画笔大小
		bufGD.setStroke(sd);
		bufGU.setColor(Color.BLACK);
		bufGD.setColor(Color.BLACK);

		// 画坐标轴
		bufGU.drawLine(imageUpP.getWidth() / 2, 5, imageUpP.getWidth() / 2,
				imageUpP.getHeight() - 50);
		bufGU.drawLine(initialDrawX - 50, initialDrawY + 75,
				imageUpP.getWidth() - initialDrawX + 50, initialDrawY + 75);

		bufGD.drawLine(imageDownP.getWidth() / 2, 2, imageDownP.getWidth() / 2,
				imageDownP.getHeight() - 130);
		bufGD.drawLine(initialDrawX - 50, initialDrawY + 105,
				imageUpP.getWidth() - initialDrawX + 50, initialDrawY + 105);

		// 改变字体大小
		bufGU.setFont(new Font("宋体", Font.BOLD, 30));
		bufGU.drawString("Y", imageUpP.getWidth() / 2 + 10, 25);
		bufGU.drawString("X", imageUpP.getWidth() - initialDrawX + 50,
				initialDrawY + 105);
		bufGD.setFont(new Font("宋体", Font.BOLD, 30));
		bufGD.drawString("I", imageDownP.getWidth() / 2 + 10, 30);
		bufGD.drawString("X", imageDownP.getWidth() - initialDrawX + 55,
				initialDrawY + 105);

		gu.drawImage(bufferImgUp, 0, 0, null);
		gd.drawImage(bufferImgDown, 0, 0, null);
	}
}