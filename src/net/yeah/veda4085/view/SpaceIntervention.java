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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import net.yeah.veda4085.algorithms.SpaceAlg;

public class SpaceIntervention {
	public JPanel centerPanel;
	private JPanel leftP, rightP;
	private JPanel machineP, parameterP;
	private int pre = 3, pre2 = 3;

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
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	private JTextField waveLength;
	private JTextField space;
	private JTextField spaceToScreen;
	private JTextField lightWidth;
	private JTextField lightToSpace;
	private JTextField jingdu;// 时间相干性精度设置
	private JTextField jingdu2;// 空间相干性精度设置
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
		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		parameterP.setBackground(Color.LIGHT_GRAY);

		try {
			Image image = ImageIO.read(getClass().getResource("/picture/Space.png"));
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

		parameterP.setLayout(new GridLayout(8, 3));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0,
				3800, 7700);// 设置滚动条
		JScrollBar scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				300);
		JScrollBar scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				500);
		JScrollBar scroll4 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				5000);// 设置滚动条
		JScrollBar scroll5 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				300);
		JScrollBar scroll6 = new JScrollBar(JScrollBar.HORIZONTAL, 3, 0, 3, 30);
		JScrollBar scroll7 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);

		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll2.addMouseListener(new Scroll2MouseL());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll3.addMouseListener(new Scroll3MouseL());
		scroll4.addAdjustmentListener(new Scroll4Listener());
		scroll4.addMouseListener(new Scroll4MouseL());
		scroll5.addAdjustmentListener(new Scroll5Listener());
		scroll5.addMouseListener(new Scroll5MouseL());
		scroll6.addAdjustmentListener(new Scroll6Listener());
		scroll6.addMouseListener(new Scroll6MouseL());
		scroll7.addAdjustmentListener(new Scroll7Listener());
		scroll7.addMouseListener(new Scroll7MouseL());
		waveLength = new JTextField(20); // 输入波长，双逢距离，屏到双缝距离的文本
		space = new JTextField(20);
		spaceToScreen = new JTextField(20);
		lightWidth = new JTextField(20);
		lightToSpace = new JTextField(20);
		jingdu = new JTextField(20);
		jingdu2 = new JTextField(20);

		waveLength.setText("380.0");
		space.setText("10.0");
		spaceToScreen.setText("10.0");
		lightWidth.setText("0.0");
		lightToSpace.setText("10.0");
		jingdu.setText("3");
		jingdu2.setText("0");

		label0 = new JLabel("参数选择:"); // 标签
		label1 = new JLabel("lambda (380-770)nm");
		label2 = new JLabel("双缝距离 d (10-30)um");
		label3 = new JLabel("缝屏距离 L (10-50)cm");
		label4 = new JLabel("光源宽度 b (0-500)um");
		label5 = new JLabel("源-双缝 l (10-30)mm");
		label6 = new JLabel("白光精度(3-30)");
		label7 = new JLabel("空间相干性精度(0-100)");
		white = new JButton("白光");
		white.addActionListener(new WhiteListener());
		single = new JButton("单色光");
		single.addActionListener(new SingleListener());

		parameterP.add(label0); // 输入数据面板
		parameterP.add(single);
		parameterP.add(white);
		parameterP.add(label1);
		parameterP.add(waveLength);
		parameterP.add(scroll1);
		parameterP.add(label2);
		parameterP.add(space);
		parameterP.add(scroll2);
		parameterP.add(label3);
		parameterP.add(spaceToScreen);
		parameterP.add(scroll3);
		parameterP.add(label4);
		parameterP.add(lightWidth);
		parameterP.add(scroll4);
		parameterP.add(label5);
		parameterP.add(lightToSpace);
		parameterP.add(scroll5);
		parameterP.add(label6);
		parameterP.add(jingdu);
		parameterP.add(scroll6);
		parameterP.add(label7);
		parameterP.add(jingdu2);
		parameterP.add(scroll7);

		leftP.add(machineP, BorderLayout.CENTER); // 输入面板加入到窗口
		leftP.add(parameterP, BorderLayout.SOUTH);

	}

	public void produceRightP() {
		rightP = new JPanel(new GridLayout(2, 1));
		rightP.add(imageUpP);
		rightP.add(imageDownP);
	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			waveLength.setText(String.valueOf(t));

			if (!whiteIntervention) {
				draw();
			}
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			space.setText(String.valueOf(t));

			if (whiteIntervention) {

			} else {
				draw();
			}
		}
	}

	class Scroll2MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
		}
	}

	class Scroll3Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			spaceToScreen.setText(String.valueOf(t));

			if (whiteIntervention) {

			} else {
				draw();
			}
		}
	}

	class Scroll3MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
		}
	}

	class Scroll4Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			lightWidth.setText(String.valueOf(t));

			if (whiteIntervention) {

			} else {
				draw();
			}
		}
	}

	class Scroll4MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
		}
	}

	class Scroll5Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			lightToSpace.setText(String.valueOf(t));

			if (whiteIntervention) {

			} else {
				draw();
			}
		}
	}

	class Scroll5MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
		}
	}

	class Scroll6Listener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			pre = e.getValue();
			jingdu.setText(String.valueOf(pre));
			if (whiteIntervention) {
				drawWhite();// TODO Auto-generated method stub
			} else {
				draw();
			}// TODO Auto-generated method stub

		}

	}

	class Scroll6MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
		}
	}

	class Scroll7Listener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			pre2 = e.getValue();
			jingdu2.setText(String.valueOf(pre2));
			if (whiteIntervention) {
				drawWhite();// TODO Auto-generated method stub
			} else {
				draw();
			}// TODO Auto-generated method stub

		}

	}

	class Scroll7MouseL extends MouseAdapter {
		public void mouseReleased(MouseEvent e) {
			if (whiteIntervention)
				drawWhite();
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
		double d = Double.parseDouble(space.getText()); // 双峰距离
		double L = Double.parseDouble(spaceToScreen.getText()); // 双缝到屏的距离
		double b = Double.parseDouble(lightWidth.getText()); // 光源宽度
		double l = Double.parseDouble(lightToSpace.getText()); // 光源到双缝距离

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

		SpaceAlg sAlg = new SpaceAlg(WL, d, L, b, l, pre, pre2);

		int initialDrawX = 80;
		int initialDrawY = 30;
		int size = imageUpP.getWidth() - 2 * initialDrawX;

		double[] temp = sAlg.getArray(size);
		System.out.println(temp[size / 2]);
		ArrayList<Double> A = new ArrayList<Double>();
		for (int i = 0; i < temp.length; i++) {
			A.add(temp[i]);
		}

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
		double d = Double.parseDouble(space.getText()); // 双峰距离
		double L = Double.parseDouble(spaceToScreen.getText()); // 双缝到屏的距离
		double b = Double.parseDouble(lightWidth.getText()); // 光源宽度
		double l = Double.parseDouble(lightToSpace.getText()); // 光源到双缝距离

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

		SpaceAlg sAlg = new SpaceAlg(WL, d, L, b, l, pre, pre2);// 初始化类

		int initialDrawX = 80;
		int initialDrawY = 30;
		int size = imageUpP.getWidth() - 2 * initialDrawX;
		ArrayList<Double> A = sAlg.getwhite(size); // 取得光强数组；
		ArrayList<Color> B = sAlg.getColor(size); // 取得颜色数组；

		BasicStroke su = (BasicStroke) bufGU.getStroke();
		BasicStroke sd = (BasicStroke) bufGD.getStroke();
		bufGU.setStroke(new BasicStroke(2)); // 设置画笔大小
		bufGD.setStroke(new BasicStroke(2));
		for (int i = 0; i < size; i++) {
			c = B.get(i);
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