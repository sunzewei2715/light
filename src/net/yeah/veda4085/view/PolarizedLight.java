package net.yeah.veda4085.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import net.yeah.veda4085.algorithms.PolarizedLightAlg;

public class PolarizedLight {
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

	private JTextField xAmplitudeT, yAmplitudeT, phaseDiffrenceT;
	private JLabel label0;
	private JLabel xAmplitudeL, yAmplitudeL, phaseDiffrenceL;

	public int step = 0;

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
			Image image = ImageIO.read(getClass().getResource("/picture/PolarizedLight.jpg"));
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

		parameterP.setLayout(new GridLayout(4, 4));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				2000);// 设置滚动条
		JScrollBar scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				2000);
		JScrollBar scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 180);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll3.addAdjustmentListener(new Scroll3Listener());

		xAmplitudeT = new JTextField(); // 输入波长，距离
		yAmplitudeT = new JTextField();
		phaseDiffrenceT = new JTextField();
		xAmplitudeT.setText("0.0");
		yAmplitudeT.setText("0.0");
		phaseDiffrenceT.setText("0.0");

		label0 = new JLabel("参数选择:"); // 标签
		xAmplitudeL = new JLabel("X振幅(0~2500)");
		yAmplitudeL = new JLabel("Y振幅(0~2500)");
		phaseDiffrenceL = new JLabel("相位差x-y(0~PI)");

		parameterP.add(label0); // 输入数据面板
		parameterP.add(new JLabel());
		parameterP.add(new JLabel());
		parameterP.add(xAmplitudeL);
		parameterP.add(xAmplitudeT);
		parameterP.add(scroll1);
		parameterP.add(yAmplitudeL);
		parameterP.add(yAmplitudeT);
		parameterP.add(scroll2);
		parameterP.add(phaseDiffrenceL);
		parameterP.add(phaseDiffrenceT);
		parameterP.add(scroll3);

		leftP.add(machineP, BorderLayout.CENTER);
		leftP.add(parameterP, BorderLayout.SOUTH); // 输入面板加入到窗口
	}

	public void produceRightP() {
		rightP.setBackground(Color.WHITE);
	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			xAmplitudeT.setText(String.valueOf(wave));

			draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue() / 10.0;

			yAmplitudeT.setText(String.valueOf(wave));

			draw();
		}
	}

	class Scroll3Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double wave = e.getValue();

			phaseDiffrenceT.setText(String.valueOf(wave));

			draw();
		}
	}

	public void draw() {
		double xA = Double.parseDouble(xAmplitudeT.getText()); // X方向振幅
		double yA = Double.parseDouble(yAmplitudeT.getText()); // Y方向振幅
		double pd = Double.parseDouble(phaseDiffrenceT.getText()); // 相位差xPhase
																	// - yPhase

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// 以下3行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();

		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		int initialDrawX = rightP.getWidth() / 2;
		int initialDrawY = rightP.getHeight() / 3;

		PolarizedLightAlg polL = new PolarizedLightAlg(xA, yA, pd);

		double width = polL.getOvalWidth(); // 长轴
		double height = polL.getOvalHeight(); // 短轴

		bufG.setColor(Color.BLACK);

		bufG.drawLine(initialDrawX, 30, initialDrawX, initialDrawY * 2 - 50);
		bufG.drawLine(100, initialDrawY, rightP.getWidth() - 100, initialDrawY);

		bufG.rotate(polL.rotateAngle(), initialDrawX, initialDrawY);

		bufG.setStroke(new BasicStroke(3)); // 设置画笔大小
		bufG.setColor(Color.RED);

		bufG.drawOval((int) (initialDrawX - width / 2),
				(int) (initialDrawY - height / 2), (int) (width),
				(int) (height));

		g.drawImage(bufferImg, 0, 0, null);
	}
}
