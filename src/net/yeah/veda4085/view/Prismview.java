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

import net.yeah.veda4085.algorithms.Prismalg;

public class Prismview {
	public JPanel centerPanel;
	private JPanel leftP;
	@SuppressWarnings("serial")
	private JPanel rightP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 0, 0, null);
		}
	};
	// ͼƬimage
	Image bufferImg = null;

	private JPanel machineP, parameterP;

	private JTextField textn;
	private JTextField texti1;
	private JLabel labn;
	private JLabel labi1;

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

		machineP = new JPanel(new GridLayout(1, 1)); // �������
		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		// �������� ����ɫ��ɫ
		parameterP.setBackground(Color.LIGHT_GRAY);

		try {
			Image image = ImageIO.read(getClass().getResource(
					"/picture/Prism.jpg"));
			BufferedImage tag = new BufferedImage(330, 330,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(330, 330, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			// piccture����jalbel��
			ImageIcon picture = new ImageIcon(image);
			machineP.add(new JLabel(picture));
		} catch (Exception e) {
		}
		// �����������񲼾�3��3
		parameterP.setLayout(new GridLayout(2, 3));
		// ��������ָ�����͵�б��߿�
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scrolln = new JScrollBar(JScrollBar.HORIZONTAL, 10000, 0,
				10000, 20000);// ���ù����� ��һ��Ϊ��ʼֵ
		JScrollBar scrolli1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				9000);

		// ��scroll��Ӽ���
		scrolln.addAdjustmentListener(new ScrollnListener());
		scrolli1.addAdjustmentListener(new Scrolli1Listener());

		textn = new JTextField(20);
		texti1 = new JTextField(20);

		textn.setText("1.0");
		texti1.setText("0.0");

		labn = new JLabel("���⾵������n:");
		labi1 = new JLabel("�����i1/����:");

		parameterP.add(labn);
		parameterP.add(textn);
		parameterP.add(scrolln);
		parameterP.add(labi1);
		parameterP.add(texti1);
		parameterP.add(scrolli1);

		leftP.add(machineP, BorderLayout.CENTER); // ���������뵽����
		leftP.add(parameterP, BorderLayout.SOUTH);
	}

	public void produceRightP() {
		rightP.setBackground(Color.WHITE);
	}

	// scrol1�ı䣬��ı��ı��������ʾ����Ҫ����ϵ������֤�ı���ӳ��ʵ��ֵ
	class ScrollnListener implements AdjustmentListener { // ����������
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double n = e.getValue() / 10000.0;
			textn.setText(String.valueOf(n));
			draw();
		}
	}

	// ͬ��
	class Scrolli1Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double i1 = e.getValue() / 100.0;
			texti1.setText(String.valueOf(i1));
			draw();
		}
	}

	// ����ת��Ϊ�Ƕ�
	public double Arctojd(double a) {
		double jd;
		jd = a * 180 / (Math.PI);
		return jd;
	}

	// �Ƕ�ת��Ϊ����
	public double Jdtoarc(double a) {
		double arc;
		arc = a * Math.PI / 180;
		return arc;
	}

	public void draw() {
		// ת��Ϊdouble��
		double n = Double.parseDouble(textn.getText());
		double i1 = Double.parseDouble(texti1.getText());
		i1 = this.Jdtoarc(i1);
		double r = 200.0;
		double R = 250.0;

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// ����3�����û���������˸
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// ����

		Prismalg myprismalg = new Prismalg(n, Math.PI / 3, i1);
		double i2 = myprismalg.I2();
		double i2_ = myprismalg.I2_();
		double i1_ = myprismalg.I1_();
		double delta = myprismalg.Delta();
		double ic = Math.asin(1 / n);
		int X = rightP.getWidth() / 2;
		int Y = rightP.getHeight() / 2;
		int X1;
		int Y1;
		// �����
		int X0 = X - 43;
		int Y0 = Y - 25;
		// �����
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = Color.YELLOW;
		// ���û�����ɫ
		bufG.setColor(c);
		// ������
		bufG.drawLine(X, Y - 100, X - 87, Y + 50);
		bufG.drawLine(X, Y - 100, X + 87, Y + 50);
		bufG.drawLine(X + 87, Y + 50, X - 87, Y + 50);
		bufG.setStroke(s);
		// ����·
		bufG.setColor(Color.BLACK);
		bufG.drawLine(X0, Y0, (int) (X0 - r * Math.cos(i1 - Math.PI / 6)),
				(int) (Y0 + r * Math.sin(i1 - Math.PI / 6)));
		bufG.drawLine(X0, Y0, X1, Y1);
		// ��ȫ����ʱ��
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));
			bufG.setColor(Color.BLUE);
			// ����·�ӳ��ߣ��ཻ��delta
			bufG.drawLine(X0, Y0, (int) (X0 + R * Math.cos(i1 - Math.PI / 6)),
					(int) (Y0 - R * Math.sin(i1 - Math.PI / 6)));
			bufG.drawLine(X1, Y1, (int) (X1 - R * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 - R * Math.sin(i1_ - Math.PI / 6)));
			bufG.setColor(Color.RED);
			// ���ܹ���ʾdelta�仯��ͼ
			bufG.drawArc(X - 75, 2 * Y - 200, 200, 200, 90,
					(int) (this.Arctojd(delta)));
			bufG.drawLine(X + 25, 2 * Y - 100, X + 25, 2 * Y - 200);
			bufG.drawLine(X + 25, 2 * Y - 100,
					(int) (X + 25 - 100 * Math.sin(delta)),
					(int) (2 * Y - 100 - 100 * Math.cos(delta)));
			bufG.setFont(new Font("����", Font.BOLD, 15));
			bufG.drawString("ƫ���delta�仯", X - 70, 2 * Y - 50);
		}

		// �ı������С
		bufG.setColor(Color.RED);
		bufG.setFont(new Font("����", Font.BOLD, 30));
		bufG.drawString("���⾵��Сƫ���", 50, 50);

		g.drawImage(bufferImg, 0, 0, null);
	}
}
