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
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import net.yeah.veda4085.algorithms.FresenlAlg;

public class FresenlDiffraction {
	public JPanel centerPanel;
	private JPanel leftP, rightP;
	private JPanel parameterP;
	@SuppressWarnings("serial")
	private JPanel machineP = new JPanel() { // 重载JPanel的paint方法，避免窗口最小化图片消失
		public void paint(Graphics g) {
			g.drawImage(t, 100, 0, null);
		}
	};
	@SuppressWarnings("serial")
	private JPanel rightUp = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 80, 0, null);
		}
	};
	@SuppressWarnings("serial")
	private JPanel rightDown = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImgFFT, 80, 10, null);
		}
	};

	private JTextField waveLength;
	private JTextField distence;
	private JTextField space;
	private Component label0;
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;

	JButton buttonOpenImage = null;
	JButton buttonFFT = null;
	JButton buttonBinary = null;
	JButton buttonGray = null;

	JLabel labelImage = null;

	ArrayList<Double> data = null;
	BufferedImage image = null;

	double WL = 380.0; // 波长
	double L = 10; // 距离
	double D = 0.8; // 缝宽
	int N = 1;
	Graphics gUp;
	Graphics gDown;
	Image bufferImg;
	Graphics bufG;
	Image bufferImgFFT;
	Graphics bufGFFT;
	FresenlAlg fresenlAlg = new FresenlAlg();
	BufferedImage bufImage;
	BufferedImage bufImageFFT;

	Image t = null;

	BufferedImage grayOrBinaryImage = null;
	int imageType = BufferedImage.TYPE_BYTE_BINARY;

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

		buttonOpenImage = new JButton("打开图片");
		buttonFFT = new JButton("原图的FFT");
		buttonBinary = new JButton("原图二值化");
		buttonGray = new JButton("原图灰度化");

		buttonOpenImage.addActionListener(new OpenImageListener());
		buttonFFT.addActionListener(new FFTListener());
		buttonBinary.addActionListener(new BinaryImageListener());
		buttonGray.addActionListener(new GrayImageListener());

		parameterP.setLayout(new GridLayout(6, 3));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0,
				3800, 7700);// 设置滚动条
		JScrollBar scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				50000);
		JScrollBar scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 80, 0, 80,
				8000);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll1.addMouseListener(new Scroll1MouseL());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll2.addMouseListener(new Scroll2MouseL());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll3.addMouseListener(new Scroll3MouseL());

		waveLength = new JTextField(); // 输入波长，距离
		distence = new JTextField();
		space = new JTextField();
		waveLength.setText("380.0");
		distence.setText("10.0");
		space.setText("0.8");

		label0 = new JLabel("参数选择:"); // 标签
		label1 = new JLabel("lambda (380-770)nm");
		label2 = new JLabel("距离 L (10-5000)mm");
		label3 = new JLabel("缝宽 d (8-80)mm");

		parameterP.add(new JLabel()); // 输入数据面板
		parameterP.add(buttonOpenImage);
		parameterP.add(buttonFFT);
		parameterP.add(new JLabel());
		parameterP.add(buttonBinary);
		parameterP.add(buttonGray);
		parameterP.add(label0);
		parameterP.add(new JLabel());
		parameterP.add(new JLabel());
		parameterP.add(label1);
		parameterP.add(waveLength);
		parameterP.add(scroll1);
		parameterP.add(label2);
		parameterP.add(distence);
		parameterP.add(scroll2);
		parameterP.add(label3);
		parameterP.add(space);
		parameterP.add(scroll3);

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
			draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 10.0;

			distence.setText(String.valueOf(s));
			L = s; // 距离
		}
	}

	class Scroll2MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			draw();
		}
	}

	class Scroll3Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double d = e.getValue() / 100.0;

			space.setText(String.valueOf(d));
			D = d; // 缝宽
		}
	}

	class Scroll3MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			draw();
		}
	}

	class FFTListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (data != null) {
				ArrayList<Double> p = new ArrayList<Double>();
				for (int i = 0; i < data.size(); i++) {
					p.add(data.get(i));
				}
				ArrayList<Integer> intensityFFT = fresenlAlg.fftIntensity(p, N);
				int[] tempFFT = new int[intensityFFT.size()];
				for (int i = 0; i < tempFFT.length; i++) {
					tempFFT[i] = intensityFFT.get(i);
				}

				bufImageFFT.setRGB(0, 0, N, N, tempFFT, 0, N);
				bufGFFT.drawImage(bufferImgFFT, 0, 0, null);
				gDown.drawImage(bufferImgFFT, 80, 10, null);
			}
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
					// if(image.getWidth()>256 | image.getHeight()>256){
					BufferedImage tag = new BufferedImage(256, 256,
							BufferedImage.TYPE_INT_RGB);
					tag.getGraphics().drawImage(
							image.getScaledInstance(256, 256,
									Image.SCALE_SMOOTH), 0, 0, null);
					// tag.getGraphics().drawImage(image.getScaledInstance(256,
					// 256, Image.SCALE_AREA_AVERAGING), 0, 0, null);
					image = tag;
					// }
				} catch (IOException ex) {
				}
			}

			if (image != null) {
				N = produceImageData();
				gUp = rightUp.getGraphics();
				gDown = rightDown.getGraphics();

				// 以下6行利用缓冲消除闪烁
				bufferImg = rightUp.createImage(N, N);
				bufG = bufferImg.getGraphics();
				bufferImgFFT = rightDown.createImage(N, N);
				bufGFFT = bufferImgFFT.getGraphics();
				bufImage = (BufferedImage) bufferImg;
				bufImageFFT = (BufferedImage) bufferImgFFT;

				initialImage();
			}
		}
	}

	private void initialImage() {
		if (data != null) {
			int[] temp = new int[data.size() / 2];
			for (int i = 0; i < temp.length; i++) {
				double t = data.get(i * 2);
				temp[i] = (int) t;
			}

			t = machineP.createImage(N, N);
			Graphics g = t.getGraphics();
			Graphics gImage = machineP.getGraphics();
			BufferedImage buf = (BufferedImage) t;
			buf.setRGB(0, 0, N, N, temp, 0, N);
			g.drawImage(buf, 0, 0, null);
			gImage.drawImage(t, 100, 0, null);
		}
	}

	public int produceImageData() {
		int width = image.getWidth();
		int height = image.getHeight();

		// 图像灰度化

		grayOrBinaryImage = new BufferedImage(width, height, imageType);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = image.getRGB(i, j);
				grayOrBinaryImage.setRGB(i, j, rgb);
			}
		}

		// 截取图像
		if (width > 256) {
			width = 256;
		}
		if (height > 256) {
			height = 256;
		}

		int[][] pixels = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pixels[i][j] = grayOrBinaryImage.getRGB(j, i);
			}
		}

		// 将图像边界化为2的幂次，java中强制转换为int时将小数点后的舍弃
		int n = (int) (Math.log(width) / Math.log(2) + 0.999);
		int N = 1;
		for (int i = 0; i < n; i++) {
			N = N * 2;
		}

		data = new ArrayList<Double>();
		// 延展图像为2的幂次
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				data.add((double) Color.black.getRGB()); // 实部
				data.add(0.0); // 虚部
			}
		}
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[0].length; j++) {
				data.set((i * N + j) * 2, (double) pixels[i][j]);// 实部
			}
		}

		return N;
	}

	public void draw() {
		if (data != null) {
			ArrayList<Double> p = new ArrayList<Double>();
			for (int i = 0; i < data.size(); i++) {
				p.add(data.get(i));
			}
			ArrayList<Integer> intensity = fresenlAlg.allIntensity(WL, L, D, p,
					N);

			int[] temp = new int[intensity.size()];
			for (int i = 0; i < intensity.size(); i++) {
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
			bufG.drawImage(bufImage, 0, 0, null);
			gUp.drawImage(bufferImg, 80, 0, null);
		}
	}
}
