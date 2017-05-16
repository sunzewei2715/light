package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.image.Kernel;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class Moireview extends Frame {
	public JPanel centerPanel;
	private JPanel upP;
	@SuppressWarnings("serial")
	private JPanel downP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(bufferImg, 0, 0, null);
		}
	};

	Image bufferImg = null;

	private JPanel machineP, parameterP;

	private JLabel parameter, angle, period;

	private JTextField anglet, periodt;

	public JPanel launchFrame() {
		centerPanel = new JPanel(new BorderLayout());

		produceupP();
		centerPanel.add(upP, BorderLayout.NORTH);

		producedownP();
		centerPanel.add(downP, BorderLayout.SOUTH);

		return centerPanel;
	}

	public void produceupP() {
		upP = new JPanel(new BorderLayout());
		upP.setPreferredSize(new Dimension(centerPanel.getWidth(), 150));

		machineP = new JPanel(); // 加入面板
		machineP.setBackground(Color.WHITE);

		try { // 加图片
			Image image = ImageIO.read(getClass().getResource("/picture/Moire.png"));
			BufferedImage tag = new BufferedImage(350, 350,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(350, 350, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			ImageIcon picture = new ImageIcon(image);
			machineP.add(new JLabel(picture));
		} catch (Exception e) {

		}

		parameterP = new JPanel(new GridLayout(3, 3));
		parameterP.setBackground(Color.LIGHT_GRAY);

		parameterP.setPreferredSize(new Dimension(400, 100));

		parameter = new JLabel("参数选择");
		angle = new JLabel("夹角(0-90)度");
		period = new JLabel("周期(80-1000)"); // 范围未定

		anglet = new JTextField("90");
		periodt = new JTextField("80");
		anglet.addActionListener(new Text1Listerer());
		periodt.addActionListener(new Text2Listerer());

		JScrollBar scroll1 = new JScrollBar(JScrollBar.HORIZONTAL, 90, 0, 0, 90);
		JScrollBar scroll2 = new JScrollBar(JScrollBar.HORIZONTAL, 80, 0, 80,
				600);
		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());

		parameterP.add(parameter);
		parameterP.add(new JLabel());
		parameterP.add(new JLabel());
		parameterP.add(angle);
		parameterP.add(anglet);
		parameterP.add(scroll1);
		parameterP.add(period);
		parameterP.add(periodt);
		parameterP.add(scroll2);

		upP.add(machineP, BorderLayout.WEST);
		upP.add(parameterP, BorderLayout.EAST);

	}

	public void producedownP() {
		downP.setPreferredSize(new Dimension(centerPanel.getWidth(), 360));
		downP.setBackground(Color.WHITE);
	}

	class Scroll1Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double angle = e.getValue();

			anglet.setText(String.valueOf(angle));

			draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double period = e.getValue();

			periodt.setText(String.valueOf(period));

			draw();
		}
	}

	class Text1Listerer implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String angl = f.getActionCommand();

			anglet.setText(String.valueOf(angl));

			draw();
		}
	}

	class Text2Listerer implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String perio = f.getActionCommand();

			periodt.setText(String.valueOf(perio));

			draw();
		}
	}

	public void draw() {
		int an = (int) (Double.parseDouble(anglet.getText()));
		int p1 = (int) (Double.parseDouble(periodt.getText()) / 10);
		int p2 = (int) (p1 / 2);
		int a = downP.getWidth();
		int b = downP.getHeight();

		Graphics g = downP.getGraphics();

		Color c = Color.BLACK;

		// 以下8行利用缓冲消除闪烁
		bufferImg = downP.createImage(downP.getWidth(), downP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		bufG.setColor(Color.GREEN);

		bufG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		bufG.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);

		if (an == 0) {
			int[] x1 = { -(int) (p2 / 2), (int) (p2 / 2), (int) (p2 / 2),
					-(int) (p2 / 2) };
			int[] y1 = { b + 3, b + 3, -3, -3 };

			for (int k1 = 0; -(int) (p2 / 2) + k1 * p1 < a; k1++) {
				int[] r1 = new int[4];
				r1[0] = x1[0] + k1 * p1;
				r1[1] = x1[1] + k1 * p1;
				r1[2] = x1[2] + k1 * p1;
				r1[3] = x1[3] + k1 * p1;

				bufG.fillPolygon(r1, y1, 4);
			}
		}

		if (an == 90) {
			int[] x2 = { -3, -3, a + 3, a + 3 };
			int[] y2 = { -(int) (p2 / 2), (int) (p2 / 2), (int) (p2 / 2),
					-(int) (p2 / 2) };

			for (int k2 = 0; -(int) (p2 / 2) + k2 * p1 < b; k2++) {
				int[] s2 = new int[4];
				s2[0] = y2[0] + k2 * p1;
				s2[1] = y2[1] + k2 * p1;
				s2[2] = y2[2] + k2 * p1;
				s2[3] = y2[3] + k2 * p1;

				bufG.fillPolygon(x2, s2, 4);
			}
		}

		if (0 < an && an < 90) {
			int[] x3 = { -(int) (p2 / (2 * Math.cos(an * Math.PI / 180))), 0,
					(int) (p2 / (2 * Math.cos(an * Math.PI / 180))), 0 };
			int[] y3 = { 0, -(int) (p2 / (2 * Math.sin(an * Math.PI / 180))),
					0, (int) (p2 / (2 * Math.sin(an * Math.PI / 180))) };

			for (int k3 = 0; -(int) (p2 / (2 * Math.cos(an * Math.PI / 180)))
					+ k3 * (int) (p1 / (Math.cos(an * Math.PI / 180))) < a; k3++) {
				for (int k4 = 0; (-(int) (p2 / (2 * Math
						.cos(an * Math.PI / 180)))
						+ k4
						* (int) (p1 / (2 * Math.cos(an * Math.PI / 180))) < a)
						|| (-(int) (p2 / (2 * Math.sin(an * Math.PI / 180)))
								+ k4
								* (int) (p1 / (2 * Math.sin(an * Math.PI / 180))) < b); k4++) {
					int[] r3 = new int[4];
					r3[0] = x3[0] + k3
							* (int) (p1 / (Math.cos(an * Math.PI / 180))) + k4
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));
					r3[1] = x3[1] + k3
							* (int) (p1 / (Math.cos(an * Math.PI / 180))) + k4
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));
					r3[2] = x3[2] + k3
							* (int) (p1 / (Math.cos(an * Math.PI / 180))) + k4
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));
					r3[3] = x3[3] + k3
							* (int) (p1 / (Math.cos(an * Math.PI / 180))) + k4
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));

					int[] s3 = new int[4];
					s3[0] = y3[0] + k4
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));
					s3[1] = y3[1] + k4
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));
					s3[2] = y3[2] + k4
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));
					s3[3] = y3[3] + k4
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));

					bufG.fillPolygon(r3, s3, 4);

				}
			}
			for (int k5 = 1; (-(int) (p2 / (2 * Math.cos(an * Math.PI / 180)))
					+ k5 * (int) (p1 / (2 * Math.cos(an * Math.PI / 180))) < a)
					|| (-(int) (p2 / (2 * Math.sin(an * Math.PI / 180))) + k5
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180))) < b); k5++) {
				for (int k6 = 1; ((int) (p2 / (2 * Math.cos(an * Math.PI / 180)))
						+ k5
						* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)))
						- k6 * (int) (p1 / (2 * Math.cos(an * Math.PI / 180))) > 0); k6++) {
					int[] r3 = new int[4];
					r3[0] = x3[0] + k5
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)))
							- k6
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));
					r3[1] = x3[1] + k5
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)))
							- k6
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));
					r3[2] = x3[2] + k5
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)))
							- k6
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));
					r3[3] = x3[3] + k5
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)))
							- k6
							* (int) (p1 / (2 * Math.cos(an * Math.PI / 180)));

					int[] s3 = new int[4];
					s3[0] = y3[0] + k5
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)))
							+ k6
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));
					s3[1] = y3[1] + k5
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)))
							+ k6
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));
					s3[2] = y3[2] + k5
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)))
							+ k6
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));
					s3[3] = y3[3] + k5
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)))
							+ k6
							* (int) (p1 / (2 * Math.sin(an * Math.PI / 180)));

					bufG.fillPolygon(r3, s3, 4);
				}
			}
		}

		g.drawImage(bufferImg, 0, 0, null);

	}

}
