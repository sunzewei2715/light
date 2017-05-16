package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import net.yeah.veda4085.algorithms.PolarizedAlg;

public class PolarizedIntervention {
	public JPanel centerPanel;
	private JPanel leftP, rightP;
	private JPanel parameterP;
	@SuppressWarnings("serial")
	private JPanel machineP = new JPanel() { // 重载JPanel的paint方法，避免窗口最小化图片消失
		public void paint(Graphics g) {
			g.drawImage(t, 0, 0, null);
		}
	};
	@SuppressWarnings("serial")
	private JPanel rightUp = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 0, 0, null);
		}
	};

	private JPanel rightDown = new JPanel(new BorderLayout());

	private JTextField waveLength;
	private JTextField P1X;
	private JTextField P2X;
	private JTextField NONE;
	private Component label0;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;

	private JButton white;
	private boolean whiteIntervention = false;
	private JButton single;

	JButton openImage = null;
	JButton binary = null;
	JButton gray = null;

	JLabel labelImage = null;

	ArrayList<Double> data = null;
	BufferedImage image = null;
	// String url;
	double WL = 380.0; // 波长
	double p1x = 0; // p1与x的夹角
	double p2x = 0; // p2与x的夹角
	double none = 0.005; // |no-ne|
	int N = 256;
	Graphics gUp;
	Image bufferImg;
	Graphics bufG;
	BufferedImage bufImage;

	Image t = null;

	BufferedImage grayOrBinaryImage = null;
	int imageType = BufferedImage.TYPE_BYTE_GRAY;

	ArrayList<Double> tickness = null;

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

		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		parameterP.setBackground(Color.LIGHT_GRAY);

		openImage = new JButton("打开图片");
		binary = new JButton("原图二值化");
		gray = new JButton("原图灰度化");
		white = new JButton("白光");
		single = new JButton("单色光");

		openImage.addActionListener(new OpenImageListener());
		binary.addActionListener(new BinaryImageListener());
		gray.addActionListener(new GrayImageListener());
		white.addActionListener(new WhiteListener());
		single.addActionListener(new SingleListener());

		parameterP.setLayout(new GridLayout(7, 3));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0,
				3800, 7700);// 设置滚动条
		JScrollBar scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				3600);
		JScrollBar scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				3600);
		JScrollBar scroll4 = new JScrollBar(JScrollBar.HORIZONTAL, 5, 0, 5, 50);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll1.addMouseListener(new Scroll1MouseL());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll2.addMouseListener(new Scroll2MouseL());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll3.addMouseListener(new Scroll3MouseL());
		scroll4.addAdjustmentListener(new Scroll4Listener());
		scroll4.addMouseListener(new Scroll4MouseL());

		waveLength = new JTextField(); // 输入波长，距离
		P1X = new JTextField();
		P2X = new JTextField();
		NONE = new JTextField();
		waveLength.setText("380.0");
		P1X.setText("0");
		P2X.setText("0");
		NONE.setText("0.005");

		label0 = new JLabel("参数选择:"); // 标签
		label1 = new JLabel("lambda (380-770)nm");
		label2 = new JLabel("P1与X轴夹角(0-360)度");
		label3 = new JLabel("P2与X轴夹角(0-360)度");
		label4 = new JLabel("|no-ne| (0.005-0.05)");

		JLabel tL1 = new JLabel("样品图黑色代表厚度为");
		tL1.setForeground(Color.red);
		parameterP.add(tL1);
		JLabel tL2 = new JLabel("255,白色代表厚度为0");
		tL2.setForeground(Color.red);
		parameterP.add(tL2);
		parameterP.add(new JLabel());

		parameterP.add(openImage);
		parameterP.add(binary);
		parameterP.add(gray);
		parameterP.add(label0);
		parameterP.add(single);
		parameterP.add(white);
		parameterP.add(label1);
		parameterP.add(waveLength);
		parameterP.add(scroll1);
		parameterP.add(label2);
		parameterP.add(P1X);
		parameterP.add(scroll2);
		parameterP.add(label3);
		parameterP.add(P2X);
		parameterP.add(scroll3);
		parameterP.add(label4);
		parameterP.add(NONE);
		parameterP.add(scroll4);

		leftP.add(machineP, BorderLayout.CENTER);
		leftP.add(parameterP, BorderLayout.SOUTH); // 输入面板加入到窗口
	}

	public void produceRightP() {
		rightP = new JPanel(new GridLayout(2, 1));
		rightP.add(rightUp);
		rightP.add(rightDown);
		rightP.setBackground(Color.WHITE);
		rightUp.setBackground(Color.white);
		rightDown.setBackground(Color.white);

		try {
			Image image = ImageIO.read(getClass().getResource("/picture/Polarized.png"));
			BufferedImage tag = new BufferedImage(380, 220,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(380, 220, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			ImageIcon picture = new ImageIcon(image);
			rightDown.add(new JLabel(picture), BorderLayout.SOUTH);
		} catch (Exception e) {

		}
	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			waveLength.setText(String.valueOf(wave));
			WL = wave; // 波长
		}
	}

	class Scroll1MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (!whiteIntervention)
				draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 10.0;

			P1X.setText(String.valueOf(s));
			p1x = s; // 距离
		}
	}

	class Scroll2MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
			else
				draw();
		}
	}

	class Scroll3Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 10.0;

			P2X.setText(String.valueOf(s));
			p2x = s; // 缝宽
		}
	}

	class Scroll3MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
			else
				draw();
		}
	}

	class Scroll4Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 1000.0;

			NONE.setText(String.valueOf(s));
			none = s; // 缝宽
		}
	}

	class Scroll4MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
			else
				draw();
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

	class GrayImageListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (image != null) {
				imageType = BufferedImage.TYPE_BYTE_GRAY;
				produceImageData();
				initialImage();
			}
		}
	}

	class BinaryImageListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (image != null) {
				imageType = BufferedImage.TYPE_BYTE_BINARY;
				produceImageData();
				initialImage();
			}
		}
	}

	class OpenImageListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser chooser = new JFileChooser();
			if (chooser.showOpenDialog(centerPanel) == JFileChooser.APPROVE_OPTION) {
				try {
					image = ImageIO.read(chooser.getSelectedFile().toURI()
							.toURL());

					// 将图片缩小
					if (image != null) {
						// if(image.getWidth()>256 | image.getHeight()>256){
						BufferedImage tag = new BufferedImage(N, N,
								BufferedImage.TYPE_INT_RGB);
						tag.getGraphics().drawImage(
								image.getScaledInstance(N, N,
										Image.SCALE_SMOOTH), 0, 0, null);
						// tag.getGraphics().drawImage(image.getScaledInstance(256,
						// 256, Image.SCALE_AREA_AVERAGING), 0, 0, null);
						image = tag;
						// }
					}
				} catch (IOException ex) {
				}
			}

			if (image != null) {
				produceImageData();
				gUp = rightUp.getGraphics();

				// 以下3行利用缓冲消除闪烁
				bufferImg = rightUp.createImage(N, N);
				bufG = bufferImg.getGraphics();
				bufImage = (BufferedImage) bufferImg;

				initialImage();
			}
		}
	}

	private void initialImage() {
		if (data != null) {
			int[] temp = new int[data.size()];
			for (int i = 0; i < temp.length; i++) {
				double t = data.get(i);
				temp[i] = (int) t;
			}

			t = machineP.createImage(N, N);
			Graphics g = t.getGraphics();
			Graphics gImage = machineP.getGraphics();
			BufferedImage buf = (BufferedImage) t;
			buf.setRGB(0, 0, N, N, temp, 0, N);
			g.drawImage(buf, 0, 0, null);
			gImage.drawImage(t, 0, 0, null);
		}
	}

	public void produceImageData() {
		int width = image.getWidth();
		int height = image.getHeight();

		// 图像灰度化/二值化
		grayOrBinaryImage = new BufferedImage(width, height, imageType);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayOrBinaryImage.setRGB(i, j, rgb);
			}
		}

		int[][] pixels = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixels[i][j] = grayOrBinaryImage.getRGB(j, i);
			}
		}

		data = new ArrayList<Double>();

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				data.add((double) Color.black.getRGB());
			}
		}
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				data.set(i * N + j, (double) pixels[i][j]);
			}
		}
	}

	public void draw() {
		if (data != null) {
			ArrayList<Double> p = new ArrayList<Double>();
			for (int i = 0; i < data.size(); i++) {
				p.add(data.get(i));
			}
			if (tickness == null)
				tickness = p;

			PolarizedAlg palg = new PolarizedAlg(WL, p1x, p2x, none);

			ArrayList<Integer> intensity = palg.allIntensity(p);
			int[] temp = new int[intensity.size()];
			for (int i = 0; i < temp.length; i++) {
				temp[i] = intensity.get(i);
			}

			for (int i = 0; i < temp.length; i++) {
				int pixel = 0;
				if (WL < 445) { // 紫
					pixel = new Color(temp[i], 0, temp[i]).getRGB();
				} else if (WL < 510) { // 蓝
					pixel = new Color(0, 0, temp[i]).getRGB();
				} else if (WL < 575) { // 青
					pixel = new Color(0, temp[i], temp[i]).getRGB();
				} else if (WL < 640) { // 绿
					pixel = new Color(0, temp[i], 0).getRGB();
				} else if (WL < 705) { // 黄
					pixel = new Color(temp[i], temp[i], 0).getRGB();
				} else { // 红
					pixel = new Color(temp[i], 0, 0).getRGB();
				}

				temp[i] = pixel;
			}

			bufImage.setRGB(0, 0, N, N, temp, 0, N);
			BufferedImage tag = new BufferedImage(N, N,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					bufImage.getScaledInstance(N, N, Image.SCALE_SMOOTH), 0, 0,
					null);
			gUp.drawImage(tag, 0, 0, null);
		}
	}

	// 叠加色r=覆盖色r*覆盖alpha+底色r*（1-覆盖色alpha）；绿和蓝也是如此，然后再3色组合起来
	public Color overLay(Color basic, Color over) {
		int[] rgbA = new int[3];
		int pixelA = basic.getRGB();
		rgbA[0] = (pixelA & 0xff0000) >> 16;
		rgbA[1] = (pixelA & 0xff00) >> 8;
		rgbA[2] = (pixelA & 0xff);
		// int alphaA = basic.getAlpha();

		int[] rgbB = new int[3];
		int pixelB = over.getRGB();
		rgbB[0] = (pixelB & 0xff0000) >> 16;
		rgbB[1] = (pixelB & 0xff00) >> 8;
		rgbB[2] = (pixelB & 0xff);
		// int alphaB = over.getAlpha();

		int[] rgbC = new int[3];

		rgbC[0] = (int) (rgbB[0] * 0.5 + rgbA[0] * (1 - 0.5));
		rgbC[1] = (int) (rgbB[1] * 0.5 + rgbA[1] * (1 - 0.5));
		rgbC[2] = (int) (rgbB[2] * 0.5 + rgbA[2] * (1 - 0.5));

		return new Color(rgbC[0], rgbC[1], rgbC[2]).brighter();
	}

	public void drawWhite() {
		if (data != null) {
			ArrayList<Double> p = new ArrayList<Double>();
			ArrayList<Double> p2 = new ArrayList<Double>();
			ArrayList<Double> p3 = new ArrayList<Double>();
			ArrayList<Double> p4 = new ArrayList<Double>();
			ArrayList<Double> p5 = new ArrayList<Double>();
			ArrayList<Double> p6 = new ArrayList<Double>();
			for (int i = 0; i < data.size(); i++) {
				p.add(data.get(i));
				p2.add(data.get(i));
				p3.add(data.get(i));
				p4.add(data.get(i));
				p5.add(data.get(i));
				p6.add(data.get(i));
			}
			if (tickness == null)
				tickness = p;

			PolarizedAlg palgRed = new PolarizedAlg(738, p1x, p2x, none);
			PolarizedAlg palgYellow = new PolarizedAlg(673, p1x, p2x, none);
			PolarizedAlg palgGreen = new PolarizedAlg(608, p1x, p2x, none);
			PolarizedAlg palgCyan = new PolarizedAlg(543, p1x, p2x, none);
			PolarizedAlg palgBlue = new PolarizedAlg(478, p1x, p2x, none);
			PolarizedAlg palgPurple = new PolarizedAlg(413, p1x, p2x, none);

			ArrayList<Integer> red = palgRed.allIntensity(p);
			ArrayList<Integer> yellow = palgYellow.allIntensity(p2);
			ArrayList<Integer> green = palgGreen.allIntensity(p3);
			ArrayList<Integer> cyan = palgCyan.allIntensity(p4);
			ArrayList<Integer> blue = palgBlue.allIntensity(p5);
			ArrayList<Integer> purple = palgPurple.allIntensity(p6);

			int[] tempRed = new int[red.size()];
			int[] tempYellow = new int[yellow.size()];
			int[] tempGreen = new int[green.size()];
			int[] tempCyan = new int[cyan.size()];
			int[] tempBlue = new int[blue.size()];
			int[] tempPurple = new int[purple.size()];
			for (int i = 0; i < tempRed.length; i++) {
				tempRed[i] = red.get(i);
				tempYellow[i] = yellow.get(i);
				tempGreen[i] = green.get(i);
				tempCyan[i] = cyan.get(i);
				tempBlue[i] = blue.get(i);
				tempPurple[i] = purple.get(i);
			}

			int[] temp = new int[p.size()];
			for (int i = 0; i < temp.length; i++) {
				// 紫
				Color cPurple = new Color(tempPurple[i], 0, tempPurple[i]);
				// 蓝
				Color cBlue = new Color(0, 0, tempBlue[i]);
				// 青
				Color cCyan = new Color(0, tempCyan[i], tempCyan[i]);
				// 绿
				Color cGreen = new Color(0, tempGreen[i], 0);
				// 黄
				Color cYellow = new Color(tempYellow[i], tempYellow[i], 0);
				// 红
				Color cRed = new Color(tempRed[i], 0, 0);

				// Color t = overLay(cRed, cGreen);
				// t = overLay(t, cYellow);
				// t = overLay(t, cCyan);
				// t = overLay(t, cBlue);
				// t = overLay(t, cPurple);
				Color t = overLay(cRed, cPurple);
				Color t1 = overLay(cYellow, cBlue);
				Color t2 = overLay(cGreen, cCyan);
				Color t3 = overLay(t1, t2);
				t = overLay(t, t3);
				// c = overLay(c, cGreen);
				// c = overLay(c, cCyan);
				// c = overLay(c, cBlue);
				// c = overLay(c, cPurple);

				temp[i] = t.getRGB();
			}

			bufImage.setRGB(0, 0, N, N, temp, 0, N);
			BufferedImage tag = new BufferedImage(N, N,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					bufImage.getScaledInstance(N, N, Image.SCALE_SMOOTH), 0, 0,
					null);
			gUp.drawImage(tag, 0, 0, null);
		}
	}
}
