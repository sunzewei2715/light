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

import net.yeah.veda4085.algorithms.TimeAlg;

public class TimeIntervention {
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
	private JLabel label5;
	private JTextField waveLength;
	private JTextField space;
	private JTextField spaceToScreen;
	private JTextField fluctuate;
	private JTextField jingdu;
	private JButton white;
	private boolean whiteIntervention = false;
	private JButton single;
	private int Pre = 3;

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

		machineP = new JPanel(); // �������
		parameterP = new JPanel();
		machineP.setBackground(Color.WHITE);
		parameterP.setBackground(Color.LIGHT_GRAY);

		try {
			Image image = ImageIO.read(getClass().getResource("/picture/Time.png"));
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

		parameterP.setLayout(new GridLayout(6, 3));
		parameterP.setBorder(new BevelBorder(BevelBorder.RAISED));

		JScrollBar scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 3800, 0,
				3800, 7700);// ���ù�����
		JScrollBar scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				300);
		JScrollBar scroll3 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				500);
		JScrollBar scroll4 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 340);// ���ù�����
		JScrollBar scroll5 = new JScrollBar(JScrollBar.HORIZONTAL, 3, 0, 3, 500);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll4.addAdjustmentListener(new Scroll4Listener());
		scroll5.addAdjustmentListener(new Scroll5Listener());

		waveLength = new JTextField(20); // ���벨����˫����룬����˫�������ı�
		space = new JTextField(20);
		spaceToScreen = new JTextField(20);
		fluctuate = new JTextField(20);
		jingdu = new JTextField(20);
		waveLength.setText("380.0");
		space.setText("10.0");
		spaceToScreen.setText("10.0");
		fluctuate.setText("0");
		jingdu.setText("3");

		label0 = new JLabel("����ѡ��:"); // ��ǩ
		label1 = new JLabel("lambda (380-770)nm");
		label2 = new JLabel("˫����� d (10-30)um");
		label3 = new JLabel("�������� L (10-50)cm");
		double b = Double.parseDouble(waveLength.getText());
		b = 1000 * ((b < 575) ? ((b - 380) / b) : ((770 - b) / b));
		double c = ((int) b) / 10.0;
		label4 = new JLabel("�������� " + "(Ӧ<" + c + "%):");
		label5 = new JLabel("����ѡ��(3-500)");
		white = new JButton("�׹�");
		white.addActionListener(new WhiteListener());
		single = new JButton("��ɫ��");
		single.addActionListener(new SingleListener());

		parameterP.add(label0); // �����������
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
		parameterP.add(fluctuate);
		parameterP.add(scroll4);
		parameterP.add(label5);
		parameterP.add(jingdu);
		parameterP.add(scroll5);

		leftP.add(machineP, BorderLayout.CENTER); // ���������뵽����
		leftP.add(parameterP, BorderLayout.SOUTH);

	}

	public void produceRightP() {
		rightP = new JPanel(new GridLayout(2, 1));
		rightP.add(imageUpP);
		rightP.add(imageDownP);
	}

	class Scroll1Listener implements AdjustmentListener { // ����������
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			waveLength.setText(String.valueOf(t));

			if (!whiteIntervention) {
				double b = Double.parseDouble(waveLength.getText());
				b = 1000 * ((b < 575) ? ((b - 380) / b) : ((770 - b) / b));
				double c = ((int) b) / 10.0;
				label4.setText("�������� " + "(Ӧ<" + c + "%):");
				draw();
			}
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			space.setText(String.valueOf(t));

			if (whiteIntervention) {
				drawWhite();
			} else {
				draw();
			}
		}
	}

	class Scroll3Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 10.0;

			spaceToScreen.setText(String.valueOf(t));
			if (whiteIntervention) {
				drawWhite();
			} else {
				draw();
			}
		}
	}

	class Scroll4Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double t = e.getValue() / 1000.0;
			double b = Double.parseDouble(waveLength.getText());
			b = (b < 575) ? ((b - 380) / b) : ((770 - b) / b);
			b = ((int) (1000 * b)) / 1000.0;// ��bת��Ϊ��λС��
			t = (t < b) ? t : b;
			fluctuate.setText(String.valueOf(t));
			if (whiteIntervention) {
				drawWhite();
			} else {
				draw();
			}
		}
	}

	class Scroll5Listener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			Pre = e.getValue();
			jingdu.setText(String.valueOf(Pre));
			if (whiteIntervention) {
				drawWhite();// TODO Auto-generated method stub
			} else {
				draw();
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
		double b = Double.parseDouble(waveLength.getText());
		b = 1000 * ((b < 575) ? ((b - 380) / b) : ((770 - b) / b));
		double g = ((int) b) / 1000.0;
		double WL = Double.parseDouble(waveLength.getText()); // ����
		double d = Double.parseDouble(space.getText()); // ˫�����
		double L = Double.parseDouble(spaceToScreen.getText()); // ˫�쵽���ľ���
		double f = Double.parseDouble(fluctuate.getText()); // ��Դ��������
		f = (f < g) ? f : g;
		fluctuate.setText(String.valueOf(f));
		Graphics gu = imageUpP.getGraphics();
		Graphics gd = imageDownP.getGraphics();

		Color c = Color.WHITE;

		// ����8�����û���������˸
		bufferImgUp = imageUpP.createImage(imageUpP.getWidth(),
				imageUpP.getHeight());
		Graphics2D bufGU = (Graphics2D) bufferImgUp.getGraphics();
		bufGU.setColor(c);
		bufGU.fillRect(0, 0, bufferImgUp.getWidth(null),
				bufferImgUp.getHeight(null));// ����

		bufferImgDown = imageDownP.createImage(imageDownP.getWidth(),
				imageDownP.getHeight());
		Graphics2D bufGD = (Graphics2D) bufferImgDown.getGraphics();
		bufGD.setColor(c);
		bufGD.fillRect(0, 0, bufferImgDown.getWidth(null),
				bufferImgDown.getHeight(null));// ����

		TimeAlg tAlg = new TimeAlg(WL, f, d, L, Pre);

		int initialDrawX = 80;
		int initialDrawY = 30;
		int size = imageUpP.getWidth() - 2 * initialDrawX;

		double[] temp = tAlg.getArray(size);
		ArrayList<Color> color = tAlg.getColor(size);
		ArrayList<Double> A = new ArrayList<Double>();
		for (int i = 0; i < temp.length; i++) {
			A.add(temp[i]);
		}

		BasicStroke su = (BasicStroke) bufGU.getStroke();
		BasicStroke sd = (BasicStroke) bufGD.getStroke();
		bufGU.setStroke(new BasicStroke(2)); // ���û��ʴ�С
		bufGD.setStroke(new BasicStroke(2));
		for (int i = 0; i < A.size(); i++) {
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

		bufGU.setStroke(su); // ���û��ʴ�С
		bufGD.setStroke(sd);
		bufGU.setColor(Color.BLACK);
		bufGD.setColor(Color.BLACK);

		// ��������
		bufGU.drawLine(imageUpP.getWidth() / 2, 5, imageUpP.getWidth() / 2,
				imageUpP.getHeight() - 50);
		bufGU.drawLine(initialDrawX - 50, initialDrawY + 75,
				imageUpP.getWidth() - initialDrawX + 50, initialDrawY + 75);

		bufGD.drawLine(imageDownP.getWidth() / 2, 2, imageDownP.getWidth() / 2,
				imageDownP.getHeight() - 130);
		bufGD.drawLine(initialDrawX - 50, initialDrawY + 105,
				imageUpP.getWidth() - initialDrawX + 50, initialDrawY + 105);

		// �ı������С
		bufGU.setFont(new Font("����", Font.BOLD, 30));
		bufGU.drawString("Y", imageUpP.getWidth() / 2 + 10, 25);
		bufGU.drawString("X", imageUpP.getWidth() - initialDrawX + 50,
				initialDrawY + 105);
		bufGD.setFont(new Font("����", Font.BOLD, 30));
		bufGD.drawString("I", imageDownP.getWidth() / 2 + 10, 30);
		bufGD.drawString("X", imageDownP.getWidth() - initialDrawX + 55,
				initialDrawY + 105);

		gu.drawImage(bufferImgUp, 0, 0, null);
		gd.drawImage(bufferImgDown, 0, 0, null);
	}

	public void drawWhite() {
		waveLength.setText(String.valueOf(575));
		fluctuate.setText(String.valueOf(0.339));// �׹��Ч�����Ĳ���Ϊ575,��������Ϊ33.9%��ʵ�������
		draw();
	}

}