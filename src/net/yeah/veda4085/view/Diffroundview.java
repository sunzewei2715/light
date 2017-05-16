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
import java.awt.event.ItemEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemListener;
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
import javax.swing.JCheckBox;

import net.yeah.veda4085.algorithms.Diffround;

public class Diffroundview {
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

	private JLabel waveLengthL, slitWidthL;
	private JTextField waveLengthT, slitWidthT;
	private JCheckBox sqr;
	private JScrollBar scroll1, scroll2;
	private JButton white;
	private boolean whiteIntervention = false;
	private JButton single;

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
			Image image = ImageIO.read(getClass().getResource("/picture/Diffraction11.png"));
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

		parameterP = new JPanel(new GridLayout(3, 3)); // 加入面板
		parameterP.setBackground(Color.LIGHT_GRAY);

		parameterP.setPreferredSize(new Dimension(460, 100));

		waveLengthL = new JLabel("lambda (380-770)nm");
		slitWidthL = new JLabel("孔径 D (1000-50000)nm");
		sqr = new JCheckBox("光强开平方", true);
		sqr.addItemListener(new sqrListener());

		// slitNumL = new JLabel("缝数(1-500)");
		// slitPeriodL = new JLabel("缝周期 d (1000-20000)nm");

		white = new JButton("白光");
		single = new JButton("单色光");

		white.addActionListener(new WhiteListener());
		single.addActionListener(new SingleListener());

		waveLengthT = new JTextField("380.0");
		slitWidthT = new JTextField("1000.0");
		waveLengthT.addActionListener(new waveLengthTListener());
		slitWidthT.addActionListener(new slitWidthTListener());
		// slitNumT = new JTextField("1");
		// slitPeriodT = new JTextField("1000.0");

		scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 380, 0, 380, 770);// 设置滚动条
		scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 1000, 50000);
		// scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 500);
		// scroll4 = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 1000,
		// 20000);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		// scroll3.addAdjustmentListener(new Scroll3Listener());
		// scroll4.addAdjustmentListener(new Scroll4Listener());

		parameterP.add(sqr);
		parameterP.add(new JLabel("当前值"));
		parameterP.add(new JLabel("选择"));
		// parameterP.add(single);
		// parameterP.add(white);
		parameterP.add(waveLengthL);
		parameterP.add(waveLengthT);
		parameterP.add(scroll1);
		parameterP.add(slitWidthL);
		parameterP.add(slitWidthT);
		parameterP.add(scroll2);
		// parameterP.add(slitNumL);
		// parameterP.add(slitNumT);
		// parameterP.add(scroll3);
		// parameterP.add(slitPeriodL);
		// parameterP.add(slitPeriodT);
		// parameterP.add(scroll4);

		leftP.add(machineP, BorderLayout.CENTER); // 输入面板加入到窗口
		leftP.add(parameterP, BorderLayout.SOUTH);

	}

	public void produceRightP() {
		rightP.setBackground(Color.WHITE);

	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue();

			waveLengthT.setText(String.valueOf(wave));

			if (!whiteIntervention)
				draw();
		}
	}

	class waveLengthTListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			scroll1.setValue(Integer.parseInt(s));

			draw();
		}
	}

	class slitWidthTListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			scroll2.setValue(Integer.parseInt(s));
			draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue();

			slitWidthT.setText(String.valueOf(wave));
			// if(scroll2.getValue()>=scroll4.getValue())
			// scroll4.setValue(e.getValue()+2000);//使得缝宽小于缝周期
			if (whiteIntervention)
				drawWhite();
			else
				draw();
		}
	}

	/*
	 * class Scroll3Listener implements AdjustmentListener { // 滚动条监视 public
	 * void adjustmentValueChanged(AdjustmentEvent e) { int wave = e.getValue();
	 * 
	 * slitNumT.setText(String.valueOf(wave));
	 * 
	 * if(whiteIntervention) drawWhite(); else draw(); } }
	 * 
	 * class Scroll4Listener implements AdjustmentListener { public void
	 * adjustmentValueChanged(AdjustmentEvent e) { double wave = e.getValue();
	 * 
	 * slitPeriodT.setText(String.valueOf(wave));
	 * if(scroll2.getValue()>=scroll4.getValue())
	 * scroll2.setValue(e.getValue()-2000);//使得缝宽小于缝周期
	 * 
	 * if(whiteIntervention) drawWhite(); else draw(); } }
	 */
	class sqrListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			draw();

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
		double WL = Double.parseDouble(waveLengthT.getText()); // 波长
		double b = Double.parseDouble(slitWidthT.getText()); // 缝宽
		double n = 1; // 缝数
		double d = 0; // 缝周期

		Graphics g = rightP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		Diffround dgAlg = new Diffround(WL, b, n, d);
		/*
		 * int rightw=(int)(centerPanel.getHeight()*0.9);
		 * rightP.setPreferredSize(new Dimension(rightw,
		 * centerPanel.getHeight()));
		 * 
		 * System.out.println(centerPanel.getHeight());
		 */

		// int windowwidth=rightP.getWidth();

		// *System.out.println(rightP.getWidth());

		// System.out.println(centerPanel.getHeight());*/
		// int size = rightP.getWidth() - 2 * initialDrawX;
		int initialDrawX = rightP.getWidth() / 2;
		// 图像中心在1/3处
		int initialDrawY = rightP.getHeight() / 3;
		// size为半圆大小
		int size = initialDrawY;

		int initialCurveX = rightP.getWidth() / 2;
		// 曲线中心y在高度2/3+30处
		int initialCurveY = rightP.getHeight() / 3 * 2 + 30;

		ArrayList<Double> A = new ArrayList<Double>();
		ArrayList<Double> B = dgAlg.allIntensity(size * 200);

		for (int i = B.size() / 2; i < B.size(); i = i + 100)

		{
			double sum = 0;
			double temp;
			int num1 = 0;
			for (int j = 0; j < 100; j++) {
				if (i + j < B.size()) {
					sum = sum + B.get(i + j);
					num1++;
				}

				// if(temp<B.get(i+j))
				// temp=B.get(i+j);
			}

			temp = sum / num1;
			A.add(temp);
		}

		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // 设置画笔大小

		// 归一化，使条纹和曲线表示的更清楚，否则有的峰过小无法表示,归一化后进一步开根号使条纹更清晰
		double max = 0;
		for (int j = 0; j < A.size(); j++) {
			if (max < A.get(j)) {
				max = A.get(j);
			}
		}
		if (sqr.isSelected()) {
			for (int j = 0; j < A.size(); j++) {
				A.set(j, Math.sqrt(A.get(j) / max));
			}

		} else {
			for (int j = 0; j < A.size(); j++) {
				A.set(j, A.get(j) / max);
			}

		}

		// 画图案
		for (int i = 0; i < A.size(); i++) {
			// 通过initcolor将波长-rgb-hsb,结合光强即亮度-rgb-颜色c
			c = new InitColor().WavlenChangetoRGB((int) WL,
					(float) (A.get(i) / 1));
			// 设置画笔颜色
			bufG.setColor(c);
			// c=new InitColor().WavlenChangetoRGB((int)WL,(float)(A.get(i)/2));
			// bufG.setColor(c);
			// 前两个参数为左上角位置，后两个参数为圆的直径
			// 多画几个更清晰吗？
			bufG.drawOval(initialDrawX - i, initialDrawY - i, 2 * i, 2 * i);
			bufG.drawOval(initialDrawX + 1 - i, initialDrawY + 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX - 1 - i, initialDrawY - 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX + 1 - i, initialDrawY - 1 - i, 2 * i,
					2 * i);
			bufG.drawOval(initialDrawX - 1 - i, initialDrawY + 1 - i, 2 * i,
					2 * i);
			// 画光强曲线
			if (i > 0) {
				bufG.drawLine(i - 1 + initialCurveX, rightP.getHeight()
						- (int) (A.get(i - 1) * 2 * 35) - 62,
						i + initialCurveX, rightP.getHeight()
								- (int) (A.get(i) * 2 * 35) - 62);
				bufG.drawLine(i - 1 + initialCurveX + 1, rightP.getHeight()
						- (int) (A.get(i - 1) * 2 * 35) - 62, i + initialCurveX
						+ 1, rightP.getHeight() - (int) (A.get(i) * 35) * 2
						- 62);

				bufG.drawLine(-(i - 1) + initialCurveX, rightP.getHeight()
						- (int) (A.get(i - 1) * 2 * 35) - 62, -i
						+ initialCurveX, rightP.getHeight()
						- (int) (A.get(i) * 35) * 2 - 62);
				bufG.drawLine(-(i - 1) + initialCurveX + 1, rightP.getHeight()
						- (int) (A.get(i - 1) * 35) * 2 - 62, -i
						+ initialCurveX + 1,
						rightP.getHeight() - (int) (A.get(i) * 35) * 2 - 62);
			}

		}

		// bufG.setStroke(s);
		// float[] arr = {4.0f,2.0f};
		float[] arr = { 3.0f, 15.0f, 3.0f, 15.0f }; // 另一种虚线模型
		BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 1.0f, arr, 0);
		bufG.setStroke(stroke);
		bufG.setColor(Color.gray);

		// 画坐标轴
		bufG.drawLine(initialDrawX, 0, initialDrawX, initialDrawY * 2 + 10);
		bufG.drawLine(5, initialDrawY, rightP.getWidth() - 20, initialDrawY);

		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);
		bufG.drawLine(initialCurveX, initialCurveY - 10, initialCurveX,
				rightP.getHeight() - 20);
		bufG.drawLine(5, rightP.getHeight() - 62, rightP.getWidth() - 20,
				rightP.getHeight() - 62);
		// 改变字体大小
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", initialDrawX + 100, 30);
		bufG.drawString("X", rightP.getWidth() - 25, initialDrawY);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("I", initialCurveX, initialCurveY);
		bufG.drawString("X", rightP.getWidth() - 20, rightP.getHeight() - 62);
		bufG.setFont(new Font("宋体", Font.BOLD, 20));

		g.drawImage(bufferImg, 0, 0, null);
	}

	// 叠加色r=覆盖色r*覆盖alpha+底色r*（1-覆盖色alpha）；绿和蓝也是如此，然后再3色组合起来
	public Color overLay(Color basic, Color over) {
		int[] rgbA = new int[3];
		rgbA[0] = basic.getRed();
		rgbA[1] = basic.getGreen();
		rgbA[2] = basic.getBlue();
		// int alphaA = basic.getAlpha();

		int[] rgbB = new int[3];
		rgbB[0] = over.getRed();
		rgbB[1] = over.getGreen();
		rgbB[2] = over.getBlue();
		// int alphaB = over.getAlpha();

		int[] rgbC = new int[3];
		rgbC[0] = (int) (rgbB[0] * 0.5 + rgbA[0] * (1 - 0.5));
		rgbC[1] = (int) (rgbB[1] * 0.5 + rgbA[1] * (1 - 0.5));
		rgbC[2] = (int) (rgbB[2] * 0.5 + rgbA[2] * (1 - 0.5));

		return new Color(rgbC[0], rgbC[1], rgbC[2], 255).brighter();
	}

	public void drawWhite() {
		double b = Double.parseDouble(slitWidthT.getText()); // 缝宽
		double n = 1; // 缝数
		double d = 0; // 缝周期

		Graphics g = rightP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		Diffround dgAlgRed = new Diffround(738, b, n, d);
		Diffround dgAlgYellow = new Diffround(673, b, n, d);
		Diffround dgAlgGreen = new Diffround(608, b, n, d);
		Diffround dgAlgCyan = new Diffround(543, b, n, d);
		Diffround dgAlgBlue = new Diffround(478, b, n, d);
		Diffround dgAlgPurple = new Diffround(413, b, n, d);

		int initialDrawX = 80;
		int initialDrawY = 30;
		// int initialCurveX = initialDrawX;
		int size = rightP.getWidth() - 2 * initialDrawX;

		ArrayList<Double> red = dgAlgRed.allIntensity(size + 2);
		ArrayList<Double> yellow = dgAlgYellow.allIntensity(size + 2);
		ArrayList<Double> green = dgAlgGreen.allIntensity(size + 2);
		ArrayList<Double> cyan = dgAlgCyan.allIntensity(size + 2);
		ArrayList<Double> blue = dgAlgBlue.allIntensity(size + 2);
		ArrayList<Double> purple = dgAlgPurple.allIntensity(size + 2);

		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // 设置画笔大小

		// 归一化，使条纹和曲线表示的更清楚，否则有的峰过小无法表示
		double maxred = 0;
		double maxyellow = 0;
		double maxgreen = 0;
		double maxcyan = 0;
		double maxblue = 0;
		double maxpurple = 0;
		for (int j = 0; j < red.size(); j++) {
			if (maxred < red.get(j)) {
				maxred = red.get(j);
			}
			if (maxyellow < yellow.get(j)) {
				maxyellow = yellow.get(j);
			}
			if (maxgreen < green.get(j)) {
				maxgreen = green.get(j);
			}
			if (maxcyan < cyan.get(j)) {
				maxcyan = cyan.get(j);
			}
			if (maxblue < blue.get(j)) {
				maxblue = blue.get(j);
			}
			if (maxpurple < purple.get(j)) {
				maxpurple = purple.get(j);
			}
		}
		for (int j = 0; j < red.size(); j++) {
			red.set(j, red.get(j) / maxred);
			yellow.set(j, yellow.get(j) / maxyellow);
			green.set(j, green.get(j) / maxgreen);
			cyan.set(j, cyan.get(j) / maxcyan);
			blue.set(j, blue.get(j) / maxblue);
			purple.set(j, purple.get(j) / maxpurple);
		}
		for (int i = 0; i < red.size(); i++) {
			Color cRed = new InitColor().WavlenChangetoRGB(738,
					(float) (red.get(i) / 2));
			Color cYellow = new InitColor().WavlenChangetoRGB(673,
					(float) (yellow.get(i) / 2));
			Color cGreen = new InitColor().WavlenChangetoRGB(608,
					(float) (green.get(i) / 2));
			Color cCyan = new InitColor().WavlenChangetoRGB(543,
					(float) (cyan.get(i) / 2));
			Color cBlue = new InitColor().WavlenChangetoRGB(478,
					(float) (blue.get(i) / 2));
			Color cPurple = new InitColor().WavlenChangetoRGB(413,
					(float) (purple.get(i) / 2));

			c = overLay(cRed, cPurple);
			Color t1 = overLay(cYellow, cBlue);
			Color t2 = overLay(cGreen, cCyan);
			Color t3 = overLay(t1, t2);
			c = overLay(c, t3);
			//int y = Math.abs(i - red.size() / 2);
			// c = overLay(c, cGreen);
			// c = overLay(c, cCyan);
			// c = overLay(c, cBlue);
			// c = overLay(c, cPurple);

			bufG.setColor(c);
			bufG.drawLine(i + initialDrawX, initialDrawY + 30,
					i + initialDrawX, initialDrawY + 120);
			// bufG.drawOval(initialDrawX +red.size() / 2-y, initialDrawY + 160
			// - y, 2*y, 2*y);
			// bufG.drawOval(initialDrawX+1 +red.size() / 2-y, initialDrawY +
			// 160+1 - y, 2*y, 2*y);
			// bufG.drawOval(initialDrawX-1 +red.size() / 2-y, initialDrawY +
			// 160-1 - y, 2*y, 2*y);
			// bufG.drawOval(initialDrawX+1 +red.size() / 2-y, initialDrawY +
			// 160-1 - y, 2*y, 2*y);
			// bufG.drawOval(initialDrawX-1 +red.size() / 2-y, initialDrawY +
			// 160+1 - y, 2*y, 2*y);
		}
		/*
		 * for (int i = 0; i < A.size(); i++) { if (i > 0) {
		 * bufG.setColor(Color.RED);
		 * 
		 * bufG.drawLine(i - 1 + initialCurveX, rightP.getHeight()-(int)(A.get(i
		 * - 1) * 150)-140, i + initialCurveX, rightP.getHeight()-(int)(A.get(i)
		 * * 150)-140); bufG.drawLine(i - 1 + initialCurveX + 1,
		 * rightP.getHeight()-(int)( A.get(i - 1) *150)-140, i + initialCurveX +
		 * 1,rightP.getHeight()-(int)(A.get(i) * 150)-140); } }
		 */
		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);

		// 画坐标轴
		bufG.drawLine(rightP.getWidth() / 2, 5, rightP.getWidth() / 2,
				rightP.getHeight() - 50);
		bufG.drawLine(initialDrawX - 50, initialDrawY + 75, rightP.getWidth()
				- initialDrawX + 50, initialDrawY + 75);

		bufG.drawLine(rightP.getWidth() / 2, rightP.getHeight() / 3 * 2,
				rightP.getWidth() / 2, rightP.getHeight() - 130);
		bufG.drawLine(initialDrawX - 50, rightP.getHeight() / 3 * 2 + 52,
				rightP.getWidth() - initialDrawX + 50,
				rightP.getHeight() / 3 * 2 + 52);
		// 改变字体大小
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", rightP.getWidth() / 2, 30);
		bufG.drawString("X", rightP.getWidth() - initialDrawX + 50,
				initialDrawY + 75);

		g.drawImage(bufferImg, 0, 0, null);
	}
}
