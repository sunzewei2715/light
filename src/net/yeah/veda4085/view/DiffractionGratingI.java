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

import net.yeah.veda4085.algorithms.DiffractionGratingAlg;

public class DiffractionGratingI {
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

	private JLabel waveLengthL, slitWidthL, slitNumL, slitPeriodL;
	private JTextField waveLengthT, slitWidthT, slitNumT, slitPeriodT;
	private JScrollBar scroll1, scroll2, scroll3, scroll4;
	private JButton white, sqrt, changeN;
	private boolean whiteIntervention = false, issqrt = false,
			isChangeN = false;
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
			Image image = ImageIO.read(getClass().getResource("/picture/Diffraction.png"));
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

		parameterP = new JPanel(new GridLayout(6, 3)); // 加入面板
		parameterP.setBackground(Color.LIGHT_GRAY);

		parameterP.setPreferredSize(new Dimension(460, 100));

		waveLengthL = new JLabel("lambda (380-770)nm");
		slitWidthL = new JLabel("缝宽 b (1000-20000)nm");
		slitNumL = new JLabel("缝数(1-20)");
		slitPeriodL = new JLabel("缝周期 d (1000-20000)nm");

		white = new JButton("白光");
		single = new JButton("单色光");
		sqrt = new JButton("开根号切换");
		changeN = new JButton("缝数范围切换");

		white.addActionListener(new WhiteListener());
		single.addActionListener(new SingleListener());
		sqrt.addActionListener(new SqrtListener());
		changeN.addActionListener(new ChangeNListener());
		waveLengthT = new JTextField("380.0");
		slitWidthT = new JTextField("1000.0");
		slitNumT = new JTextField("1");
		slitPeriodT = new JTextField("1000.0");

		scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 380, 0, 380, 770);// 设置滚动条
		scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 1000, 20000);
		scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 1, 0, 1, 15);
		scroll4 = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 1000, 20000);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll4.addAdjustmentListener(new Scroll4Listener());
		parameterP.add(new JLabel("图像处理"));
		parameterP.add(sqrt);
		parameterP.add(changeN);
		parameterP.add(new JLabel("参数选择"));
		parameterP.add(single);
		parameterP.add(white);
		parameterP.add(waveLengthL);
		parameterP.add(waveLengthT);
		parameterP.add(scroll1);
		parameterP.add(slitWidthL);
		parameterP.add(slitWidthT);
		parameterP.add(scroll2);
		parameterP.add(slitNumL);
		parameterP.add(slitNumT);
		parameterP.add(scroll3);
		parameterP.add(slitPeriodL);
		parameterP.add(slitPeriodT);
		parameterP.add(scroll4);

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

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue();

			slitWidthT.setText(String.valueOf(wave));
			if (scroll2.getValue() >= scroll4.getValue())
				scroll4.setValue(e.getValue() + 2000);// 使得缝宽小于缝周期
			if (whiteIntervention)
				drawWhite();
			else
				draw();
		}
	}

	class Scroll3Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			int wave = e.getValue();

			slitNumT.setText(String.valueOf(wave));

			if (whiteIntervention)
				drawWhite();
			else
				draw();
		}
	}

	class Scroll4Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue();

			slitPeriodT.setText(String.valueOf(wave));
			if (scroll2.getValue() >= scroll4.getValue())
				scroll2.setValue(e.getValue() - 2000);// 使得缝宽小于缝周期

			if (whiteIntervention)
				drawWhite();
			else
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

	class SqrtListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (issqrt == false)
				issqrt = true;
			else
				issqrt = false;// issqurt = !issqrt;
			draw();
		}
	}

	class ChangeNListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (isChangeN == false) {
				slitNumL.setText("缝数(1-500)");
				scroll3.setMaximum(500);
				isChangeN = true;
			}

			else {
				slitNumL.setText("缝数(1-20)");
				scroll3.setMaximum(20);
				isChangeN = false;
			}

		}
	}

	public void draw() {
		double WL = Double.parseDouble(waveLengthT.getText()); // 波长
		double b = Double.parseDouble(slitWidthT.getText()); // 缝宽
		double n = Double.parseDouble(slitNumT.getText()); // 缝数
		double d = Double.parseDouble(slitPeriodT.getText()); // 缝周期

		Graphics g = rightP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		DiffractionGratingAlg dgAlg = new DiffractionGratingAlg(WL, b, n, d);

		int initialDrawX = 80;
		int initialDrawY = 30;
		int initialCurveX = initialDrawX;
		int size = rightP.getWidth() - 2 * initialDrawX;

		// 将显示的区域扩大，以便显示出更多的波峰，此处选取100倍，每100个数值中取一个最大值
		ArrayList<Double> A = new ArrayList<Double>();
		ArrayList<Double> B = dgAlg.allIntensity(size * 100);

		for (int i = 0; i < B.size(); i = i + 100)

		{
			double temp = B.get(i);
			for (int j = 1; j < 100; j++) {
				if (i + j < B.size())
					if (temp < B.get(i + j))
						temp = B.get(i + j);
			}
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

		for (int j = 0; j < A.size(); j++) {
			A.set(j, A.get(j) / max);
		}
		if (issqrt == true) {

			for (int i = 0; i < A.size(); i++) {
				A.set(i, Math.sqrt(A.get(i)));
			}

		}
		for (int i = 0; i < A.size(); i++) {
			c = new InitColor().WavlenChangetoRGB((int) WL,
					(float) (A.get(i) / 1));

			bufG.setColor(c);
			bufG.drawLine(i + initialDrawX, initialDrawY + 30,
					i + initialDrawX, initialDrawY + 120);
			int rPHeight = rightP.getHeight();
			if (i > 0) {
				bufG.drawLine(i - 1 + initialCurveX, rPHeight / 5 * 4
						- (int) (A.get(i - 1) * 150), i + initialCurveX,
						rPHeight / 5 * 4 - (int) (A.get(i) * 150));
				bufG.drawLine(i - 1 + initialCurveX + 1, rPHeight / 5 * 4
						- (int) (A.get(i - 1) * 150), i + initialCurveX + 1,
						rPHeight / 5 * 4 - (int) (A.get(i) * 150));
			}
		}

		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);

		// 画坐标轴
		bufG.drawLine(rightP.getWidth() / 2, 5, rightP.getWidth() / 2,
				rightP.getHeight() - 10);
		bufG.drawLine(initialDrawX - 50, initialDrawY + 75, rightP.getWidth()
				- initialDrawX + 50, initialDrawY + 75);

		bufG.drawLine(initialDrawX - 50, rightP.getHeight() / 5 * 4,
				rightP.getWidth() - initialDrawX + 50,
				rightP.getHeight() / 5 * 4);
		// 改变字体大小
		if (issqrt == true) {
			bufG.setFont(new Font("宋体", Font.BOLD, 15));
			bufG.drawString("图像数据已开根号", rightP.getWidth() / 2 + 5,
					rightP.getHeight() - 30);
		}
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", rightP.getWidth() / 2, 30);
		bufG.drawString("X", rightP.getWidth() - initialDrawX + 50,
				initialDrawY + 75);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("I", rightP.getWidth() / 2,
				rightP.getHeight() / 3 * 2 - 120);
		bufG.drawString("X", rightP.getWidth() - initialDrawX + 50,
				rightP.getHeight() / 3 * 2 + 52);

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
		double n = Double.parseDouble(slitNumT.getText()); // 缝数
		double d = Double.parseDouble(slitPeriodT.getText()); // 缝周期

		Graphics g = rightP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		DiffractionGratingAlg dgAlgRed = new DiffractionGratingAlg(738, b, n, d);
		DiffractionGratingAlg dgAlgYellow = new DiffractionGratingAlg(673, b,
				n, d);
		DiffractionGratingAlg dgAlgGreen = new DiffractionGratingAlg(608, b, n,
				d);
		DiffractionGratingAlg dgAlgCyan = new DiffractionGratingAlg(543, b, n,
				d);
		DiffractionGratingAlg dgAlgBlue = new DiffractionGratingAlg(478, b, n,
				d);
		DiffractionGratingAlg dgAlgPurple = new DiffractionGratingAlg(413, b,
				n, d);

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
			yellow.set(j, yellow.get(j) / maxred);
			green.set(j, green.get(j) / maxred);
			cyan.set(j, cyan.get(j) / maxred);
			blue.set(j, blue.get(j) / maxred);
			purple.set(j, purple.get(j) / maxred);
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
			// c = overLay(c, cGreen);
			// c = overLay(c, cCyan);
			// c = overLay(c, cBlue);
			// c = overLay(c, cPurple);

			bufG.setColor(c);
			bufG.drawLine(i + initialDrawX, initialDrawY + 30,
					i + initialDrawX, initialDrawY + 120);
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
