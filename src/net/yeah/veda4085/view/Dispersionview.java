/**
 * Dispersive prism
 */
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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import net.yeah.veda4085.algorithms.Dispersionalg;

public class Dispersionview {

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

	private JTextField texti1;

	private JLabel labi1;
	private JLabel lab2;
	private JLabel lab1;
	private JButton button;
	private boolean isTrue = false;

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
					"/picture/Prismdispersion.jpg"));
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
		// �����������񲼾�1��3
		parameterP.setLayout(new GridLayout(2, 3));
		// ��������ָ�����͵�б��߿�
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		// ���ù����� ��һ��Ϊ��ʼֵ
		JScrollBar scrolli1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0,
				9000);

		// ��scroll��Ӽ���
		scrolli1.addAdjustmentListener(new Scrolli1Listener());

		texti1 = new JTextField(20);

		texti1.setText("0.0");

		labi1 = new JLabel("�����i1/����:");
		lab1 = new JLabel("����˵��");
		lab2 = new JLabel("��ֵ����");
		button = new JButton("��ʵor���");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (isTrue) {
					isTrue = false;
				} else {
					isTrue = true;
				}
				if (isTrue) {
					draw1();
				} else {
					draw();
				}
			}
		});

		parameterP.add(lab1);
		parameterP.add(lab2);
		parameterP.add(button);
		parameterP.add(labi1);
		parameterP.add(texti1);
		parameterP.add(scrolli1);

		leftP.add(machineP, BorderLayout.CENTER); // ���������뵽����
		leftP.add(parameterP, BorderLayout.SOUTH);
	}

	public void produceRightP() {
		rightP.setBackground(Color.BLACK);
	}

	public double lamda[] = new double[] { 680.0, 610.0, 565.0, 530.0, 490.0,
			460.0, 410.0 };
	public double listn[];

	// ��ɫɢ��ʽ

	public double lamdaton(double bc) {
		double result;
		result = 1.4580 + 0.00354 / Math.pow(bc * Math.pow(10, -3), 2);
		return result;
	}

	// scrol1�ı䣬��ı��ı�����Ĳ�����ʾ��Ҫ����10��3800-7700��,draw
	// ͬ��
	class Scrolli1Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double i1 = e.getValue() / 100.0;
			texti1.setText(String.valueOf(i1));

			if (isTrue) {
				draw1();
			} else {
				draw();
			}
		}
	}

	public double Arctojd(double a) {
		double jd;
		jd = a * 180 / (Math.PI);
		return jd;
	}

	public double Jdtoarc(double a) {
		double arc;
		arc = a * Math.PI / 180;
		return arc;
	}

	// ������������nm

	public void draw() {
		// ת��Ϊdouble��
		double i1 = Double.parseDouble(texti1.getText());
		i1 = this.Jdtoarc(i1);
		double r = 200.0;
		double k = 3.0;

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// ����3�����û���������˸
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// ����

		Dispersionalg mydispersionalg = new Dispersionalg(1.5805, Math.PI / 3,
				i1);
		double i2 = mydispersionalg.I2();
		double i2_ = mydispersionalg.I2_();
		double i1_ = mydispersionalg.I1_();
		double delta = mydispersionalg.Delta();
		double ic = Math.asin(1 / 1.5805);

		int X = rightP.getWidth() / 2;
		int Y = rightP.getHeight() / 2;
		int X1;
		int Y1;
		int X0 = X - 43;
		int Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = Color.BLACK;
		// ���û�����ɫ
		bufG.setColor(c);
		// ��������
		bufG.drawLine(X, Y - 100, X - 87, Y + 50);
		bufG.drawLine(X, Y - 100, X + 87, Y + 50);
		bufG.drawLine(X + 87, Y + 50, X - 87, Y + 50);
		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);
		// ���������
		bufG.drawLine(X0, Y0, (int) (X0 - r * Math.cos(i1 - Math.PI / 6)),
				(int) (Y0 + r * Math.sin(i1 - Math.PI / 6)));
		c = new InitColor().WavlenChangetoRGB((int) 680, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		// �ڶ��γ������
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));
		}

		mydispersionalg = new Dispersionalg(1.5833, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5833);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 610, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic)
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		mydispersionalg = new Dispersionalg(1.5856, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5856);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 565, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic)
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		mydispersionalg = new Dispersionalg(1.5879, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5879);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 530, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		}
		mydispersionalg = new Dispersionalg(1.5911, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5911);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 490, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));
		}
		mydispersionalg = new Dispersionalg(1.5941, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5941);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 460, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic)
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		mydispersionalg = new Dispersionalg(1.6006, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.6006);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 410, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		}

		bufG.setColor(Color.RED);
		// �ı������С
		bufG.setFont(new Font("����", Font.BOLD, 30));
		bufG.drawString("���⾵ɫɢ", 50, 50);

		g.drawImage(bufferImg, 0, 0, null);
	}

	public void draw1() {
		// ת��Ϊdouble��
		double i1 = Double.parseDouble(texti1.getText());
		i1 = this.Jdtoarc(i1);
		double r = 200.0;
		double k = 3.0;

		Graphics g = rightP.getGraphics();
		Color c = Color.WHITE;

		// ����3�����û���������˸
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);

		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// ����

		Dispersionalg mydispersionalg = new Dispersionalg(1.46, Math.PI / 3, i1);
		double i2 = mydispersionalg.I2();
		double i2_ = mydispersionalg.I2_();
		double i1_ = mydispersionalg.I1_();
		double delta = mydispersionalg.Delta();
		double ic = Math.asin(1 / 1.5805);

		int X = rightP.getWidth() / 2;
		int Y = rightP.getHeight() / 2;
		int X1;
		int Y1;
		int X0 = X - 43;
		int Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = Color.BLACK;
		// ���û�����ɫ
		bufG.setColor(c);
		// ��������
		bufG.drawLine(X, Y - 100, X - 87, Y + 50);
		bufG.drawLine(X, Y - 100, X + 87, Y + 50);
		bufG.drawLine(X + 87, Y + 50, X - 87, Y + 50);
		bufG.setStroke(s);
		bufG.setColor(Color.BLACK);
		// ���������
		bufG.drawLine(X0, Y0, (int) (X0 - r * Math.cos(i1 - Math.PI / 6)),
				(int) (Y0 + r * Math.sin(i1 - Math.PI / 6)));
		c = new InitColor().WavlenChangetoRGB((int) 680, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		// �ڶ��γ������
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));
		}

		mydispersionalg = new Dispersionalg(1.48, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5833);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 610, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic)
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		mydispersionalg = new Dispersionalg(1.50, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5856);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 565, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic)
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		mydispersionalg = new Dispersionalg(1.52, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5879);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 530, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		}
		mydispersionalg = new Dispersionalg(1.54, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5911);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 490, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));
		}
		mydispersionalg = new Dispersionalg(1.56, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.5941);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 460, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic)
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		mydispersionalg = new Dispersionalg(1.58, Math.PI / 3, i1);
		i2 = mydispersionalg.I2();
		i2_ = mydispersionalg.I2_();
		i1_ = mydispersionalg.I1_();
		delta = mydispersionalg.Delta();
		ic = Math.asin(1 / 1.6006);
		X = rightP.getWidth() / 2;
		Y = rightP.getHeight() / 2;
		X0 = X - 43;
		Y0 = Y - 25;
		X1 = (int) ((Y0 - X0 * Math.tan(Math.PI / 6 - i2) + X
				* Math.tan(Math.PI / 3) - Y + 100) / (Math.tan(Math.PI / 3) - Math
				.tan(Math.PI / 6 - i2)));
		Y1 = (int) ((X1 - X0) * Math.tan(Math.PI / 6 - i2) + Y0);
		bufG.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		// ��ͼ��

		c = new InitColor().WavlenChangetoRGB((int) 410, 1);
		bufG.setColor(c);
		// �����γ������
		bufG.drawLine(X0, Y0, X1, Y1);
		if (i2_ < ic) {
			bufG.drawLine(X1, Y1, (int) (X1 + r * Math.cos(i1_ - Math.PI / 6)),
					(int) (Y1 + r * Math.sin(i1_ - Math.PI / 6)));

		}

		bufG.setColor(Color.RED);
		// �ı������С
		bufG.setFont(new Font("����", Font.BOLD, 30));
		bufG.drawString("���⾵ɫɢ", 50, 50);

		g.drawImage(bufferImg, 0, 0, null);
	}
}
