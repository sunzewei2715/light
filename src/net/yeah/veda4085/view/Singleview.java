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

import net.yeah.veda4085.algorithms.Singlealg;

public class Singleview {
	public JPanel centerPanel;
	private JPanel leftP;
	@SuppressWarnings("serial")
	private JPanel rightP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 0, 0, null);
		}
	};
	// 图片image
	Image bufferImg = null;

	private JPanel machineP, parameterP;

	private JTextField textn1;
	private JTextField textn2;
	private JTextField texts1;
	private JTextField texty1;
	private JLabel labn1;
	private JLabel labn2;
	private JLabel labs1;
	private JLabel laby1;
	private JLabel lab1;
	private JLabel lab2;
	private boolean isTrue = false;
	private JButton button;

	// centerpanel borderlayout
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

		machineP = new JPanel(new GridLayout(1, 1)); // 加入面板
		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		// 参数部分 背景色灰色
		parameterP.setBackground(Color.LIGHT_GRAY);

		try {
			Image image = ImageIO.read(getClass().getResource(
					"/picture/Single.jpg"));
			BufferedImage tag = new BufferedImage(330, 330,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(330, 330, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			// picture放入jlabel中
			ImageIcon picture = new ImageIcon(image);
			machineP.add(new JLabel(picture));
		} catch (Exception e) {
		}
		// 参数部分网格布局5，3
		parameterP.setLayout(new GridLayout(5, 3));
		// 创建具有指定类型的斜面边框
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scrolln1 = new JScrollBar(JScrollBar.HORIZONTAL, 10000, 0,
				10000, 20000);// 设置滚动条 第一个为初始值
		JScrollBar scrolln2 = new JScrollBar(JScrollBar.HORIZONTAL, 10000, 0,
				10000, 20000);
		JScrollBar scrolls1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				25000);
		JScrollBar scrolly1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				40000);

		// 给scroll添加监听
		scrolln1.addAdjustmentListener(new Scrolln1Listener());
		scrolln2.addAdjustmentListener(new Scrolln2Listener());
		scrolls1.addAdjustmentListener(new Scrolls1Listener());
		scrolly1.addAdjustmentListener(new Scrolly1Listener());

		textn1 = new JTextField(20);
		textn2 = new JTextField(20);
		texts1 = new JTextField(20);
		texty1 = new JTextField(20);

		textn1.setText("1.0");
		textn2.setText("1.0");
		texts1.setText("0.0");// cm
		texty1.setText("0.0");// cm

		labn1 = new JLabel("入射折射率n1:");// n1 and n2 don't work.
		labn2 = new JLabel("出射折射率n2:");
		labs1 = new JLabel("物距-s1/cm:");
		laby1 = new JLabel("像高y1/cm:");
		lab1 = new JLabel("文字说明");
		lab2 = new JLabel("文本区域");
		button = new JButton("切换折射or反射");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isTrue) {
					isTrue = false;
				} else {
					isTrue = true;
				}
				draw();
			}
		});

		parameterP.add(lab1);
		parameterP.add(lab2);
		parameterP.add(button);
		parameterP.add(labn1);
		parameterP.add(textn1);
		parameterP.add(scrolln1);
		parameterP.add(labn2);
		parameterP.add(textn2);
		parameterP.add(scrolln2);
		parameterP.add(labs1);
		parameterP.add(texts1);
		parameterP.add(scrolls1);
		parameterP.add(laby1);
		parameterP.add(texty1);
		parameterP.add(scrolly1);

		leftP.add(machineP, BorderLayout.CENTER); // 输入面板加入到窗口
		leftP.add(parameterP, BorderLayout.SOUTH);
	}

	public void produceRightP() {
		rightP.setBackground(Color.WHITE);
	}

	// scrol1改变，则改变文本区域显示，需要除以系数
	class Scrolln1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double n1 = e.getValue() / 10000.0;
			textn1.setText(String.valueOf(n1));
			draw();
		}
	}

	// 同上
	class Scrolln2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double n2 = e.getValue() / 10000.0;
			textn2.setText(String.valueOf(n2));
			draw();
		}
	}

	class Scrolls1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s1 = e.getValue() / 100.0;
			texts1.setText(String.valueOf(s1));
			draw();
		}
	}

	class Scrolly1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double y1 = e.getValue() / 1000.0;
			texty1.setText(String.valueOf(y1));
			draw();
		}
	}

	public void draw() {
		// 转换为double型
		double n1 = Double.parseDouble(textn1.getText());
		double n2 = Double.parseDouble(textn2.getText());
		double s1 = Double.parseDouble(texts1.getText());
		double y1 = Double.parseDouble(texty1.getText());

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// 以下3行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		Singlealg mysinglealg = new Singlealg(y1, n1, n2, -s1, 20, isTrue);
		double s2 = mysinglealg.S2();
		double y2 = mysinglealg.Y2();

		int X = rightP.getWidth() / 2;
		int Y = rightP.getHeight() / 2;
		int size = X;

		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // 设置画笔大小
		// 画图案

		c = Color.YELLOW;
		// 设置画笔颜色
		bufG.setColor(c);
		// 前两个参数为左上角位置，后两个参数为圆的直径，画球面镜弧
		bufG.drawArc(X, Y - 100, 200, 200, 90, 180);
		bufG.setColor(Color.BLUE);
		// 物
		bufG.drawLine(X + (int) (-s1), Y, X + (int) (-s1), Y - (int) (y1));
		// 左虚右实，黑实灰虚
		if (isTrue) {
			// isTrue=1 反射的情况, 为了演示效果，改变了r=200;
			mysinglealg = new Singlealg(y1, n1, n2, -s1, 200, isTrue);
			s2 = mysinglealg.S2();
			y2 = mysinglealg.Y2();
			bufG.setColor(Color.GRAY);
			bufG.drawLine(X + (int) (s2), Y, X + (int) (s2), Y - (int) (y2));
		} else {
			if (s2 < 0.0) {
				bufG.setColor(Color.GRAY);
				bufG.drawLine(X + (int) (s2), Y, X + (int) (s2), Y - (int) (y2));
			} else {
				bufG.setColor(Color.BLUE);
				bufG.drawLine(X + (int) (s2), Y, X + (int) (s2), Y - (int) (y2));
			}
		}
		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);
		// 坐标轴
		bufG.drawLine(X, Y - 200, X, Y + 200);
		bufG.drawLine(5, Y, rightP.getWidth() - 5, Y);

		// 改变字体大小
		bufG.setColor(Color.RED);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("Y", X + 50, Y - 200);
		bufG.drawString("单球面成像", 50, 50);
		bufG.drawString("X", rightP.getWidth() - 50, Y + 50);
		bufG.setFont(new Font("宋体", Font.BOLD, 20));
		bufG.setColor(Color.GRAY);
		bufG.drawString("—— 虚像", 50, 80);
		bufG.setColor(Color.BLUE);
		bufG.drawString("—— 实像", 50, 100);
		bufG.setColor(Color.RED);
		if (isTrue) {
			bufG.drawString("反射情况", 2 * X - 100, 2 * Y - 100);
		} else {
			bufG.drawString("折射情况", 2 * X - 100, 2 * Y - 100);
		}

		g.drawImage(bufferImg, 0, 0, null);
	}
}
