/**
 * Geometric optical imaging
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
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class imagegeo {
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

	private JLabel f1l, f2l, xl, yl;
	private JTextField f1t, f2t, xt, yt;

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
		upP.setPreferredSize(new Dimension(centerPanel.getWidth(), 140));

		machineP = new JPanel(); // 加入面板
		machineP.setBackground(Color.WHITE);

		try {
			Image image = ImageIO.read(getClass().getResource("/picture/geoimage.png"));
			BufferedImage tag = new BufferedImage(400, 150,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(400, 150, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			ImageIcon picture = new ImageIcon(image);
			machineP.add(new JLabel(picture));
		} catch (Exception e) {

		}

		parameterP = new JPanel(new GridLayout(4, 3)); // 加入面板
		parameterP.setBackground(Color.LIGHT_GRAY);

		parameterP.setPreferredSize(new Dimension(460, 100));

		f1l = new JLabel("焦距f1");
		f2l = new JLabel("焦距f2");
		xl = new JLabel("物体横坐标x");
		yl = new JLabel("物体纵坐标y");

		f1t = new JTextField("100");
		f2t = new JTextField("100");
		xt = new JTextField("300");
		yt = new JTextField("100");

		f1t.addActionListener(new f1tListener());
		f2t.addActionListener(new f2tListener());
		xt.addActionListener(new xtListener());
		yt.addActionListener(new ytListener());

		JScrollBar f1s = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 10, 350);
		JScrollBar f2s = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 10, 350);
		JScrollBar xs = new JScrollBar(JScrollBar.HORIZONTAL, 300, 0, 10, 350);
		JScrollBar ys = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 10, 150);

		f1s.addAdjustmentListener(new f1sListener());
		f2s.addAdjustmentListener(new f2sListener());
		xs.addAdjustmentListener(new xsListener());
		ys.addAdjustmentListener(new ysListener());

		parameterP.add(xl);
		parameterP.add(xt);
		parameterP.add(xs);
		parameterP.add(yl);
		parameterP.add(yt);
		parameterP.add(ys);
		parameterP.add(f1l);
		parameterP.add(f1t);
		parameterP.add(f1s);
		parameterP.add(f2l);
		parameterP.add(f2t);
		parameterP.add(f2s);

		upP.add(machineP, BorderLayout.WEST);
		upP.add(parameterP, BorderLayout.EAST);
	}

	public void producedownP() {
		downP.setPreferredSize(new Dimension(centerPanel.getWidth(), 420));
		downP.setBackground(Color.WHITE);
	}

	class f1tListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			f1t.setText(String.valueOf(s));

			draw();

		}
	}

	class f2tListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			f2t.setText(String.valueOf(s));

			draw();
		}
	}

	class xtListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			xt.setText(String.valueOf(s));

			draw();
		}
	}

	class ytListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			yt.setText(String.valueOf(s));

			draw();
		}
	}

	class f1sListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			f1t.setText(String.valueOf(m));

			draw();
		}
	}

	class f2sListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			f2t.setText(String.valueOf(m));

			draw();
		}
	}

	class xsListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			xt.setText(String.valueOf(m));

			draw();
		}
	}

	class ysListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			yt.setText(String.valueOf(m));

			draw();
		}
	}

	public void draw() {
		int a = (int) (downP.getWidth() / 2);
		int b = (int) (downP.getHeight() / 2);
		double x = a - 60 - Double.parseDouble(xt.getText());
		double y = Double.parseDouble(yt.getText());
		double f1 = Double.parseDouble(f1t.getText());
		double f2 = Double.parseDouble(f2t.getText());

		Graphics g = downP.getGraphics();

		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImg = downP.createImage(downP.getWidth(), downP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景
		bufG.setColor(Color.BLACK);
		bufG.setStroke(new BasicStroke(2));

		bufG.drawLine(0, b, downP.getWidth(), b);
		bufG.drawLine(a - 60, 10, a - 60, downP.getHeight() - 10);
		bufG.drawLine(a + 60, 10, a + 60, downP.getHeight() - 10);
		bufG.setFont(new Font("宋体", Font.BOLD, 30));
		bufG.drawString("H1", a - 75, b + 30);
		bufG.drawString("H2", a + 40, b + 30);
		bufG.drawString("O", 0, b + 30);
		bufG.drawString("光轴", downP.getWidth() - 80, b - 10);
		bufG.drawString("F1", a - 65 - (int) f1, b + 30);
		bufG.drawString("F2", a + 65 + (int) f2, b + 30);
		bufG.setStroke(new BasicStroke(7));
		bufG.drawLine(a - 60, b, a - 60, b);
		bufG.drawLine(a + 60, b, a + 60, b);
		bufG.drawLine((int) (a - 60 - f1), b, (int) (a - 60 - f1), b);
		bufG.drawLine((int) (a + 60 + f2), b, (int) (a + 60 + f2), b);
		bufG.setStroke(new BasicStroke(2));
		if (x < (a - 60 - f1)) {
			bufG.setColor(Color.BLUE);
			bufG.drawLine((int) x, (int) (b - y), a - 60, b
					+ (int) (f1 * y / (a - 60 - x - f1)));
			bufG.drawLine(a + 60, b + (int) (f1 * y / (a - 60 - x - f1)),
					downP.getWidth(), b + (int) (f1 * y / (a - 60 - x - f1)));
			for (int i = 1; i < 9; i += 2) {
				bufG.drawLine(a - 60 + 15 * (i - 1), b
						+ (int) (f1 * y / (a - 60 - x - f1)),
						a - 60 + 15 * (i), b
								+ (int) (f1 * y / (a - 60 - x - f1)));
			}
			bufG.setColor(Color.GREEN);
			bufG.drawLine((int) x, (int) (b - y), a - 60, (int) (b - y));
			bufG.drawLine(a + 60, (int) (b - y), (int) f2 + a + 60 + 100
					* (int) f2, b + 100 * (int) (y));
			for (int i = 1; i < 9; i += 2) {
				bufG.drawLine(a - 60 + 15 * (i - 1), (int) (b - y), a - 60 + 15
						* (i), (int) (b - y));
			}
			bufG.setColor(Color.RED);
			bufG.setStroke(new BasicStroke(3));
			bufG.drawLine((int) x, (int) (b - y), (int) x, b);
			bufG.drawLine(a + 60 + (int) (f2 + f1 * f2 / (a - 60 - x - f1)), b
					+ (int) (f1 * y / (a - 60 - x - f1)), a + 60
					+ (int) (f2 + f1 * f2 / (a - 60 - x - f1)), b);

		}

		else {
			bufG.setColor(Color.BLUE);
			bufG.drawLine((int) x, (int) (b - y), a - 60,
					(int) (b - (y * f1 / (f1 + x - a + 60))));
			bufG.drawLine(a + 60, (int) (b - (y * f1 / (f1 + x - a + 60))),
					downP.getWidth(), (int) (b - (y * f1 / (f1 + x - a + 60))));
			for (int i = 1; i < 9; i += 2) {
				bufG.drawLine((int) ((a - 60 - f1) + (i - 1)
						* (x - a + 60 + f1) / 8), (int) (b - (i - 1) * y / 8),
						(int) ((a - 60 - f1) + i * (x - a + 60 + f1) / 8),
						(int) (b - i * y / 8));
				bufG.drawLine(a - 60 + 15 * (i - 1), (int) (b - (y * f1 / (f1
						+ x - a + 60))), a - 60 + 15 * i,
						(int) (b - (y * f1 / (f1 + x - a + 60))));
			}
			for (int k = 0; a - 75 - (k + 1) * 15 > 0; k += 2) {
				bufG.drawLine(a - 75 - k * 15,
						(int) (b - (y * f1 / (f1 + x - a + 60))), a - 75
								- (k + 1) * 15, (int) (b - (y * f1 / (f1 + x
								- a + 60))));

			}
			bufG.setColor(Color.GREEN);
			bufG.drawLine((int) x, (int) (b - y), a - 60, (int) (b - y));
			bufG.drawLine(a + 60, (int) (b - y), (int) f2 + a + 60 + 100
					* (int) f2, b + 100 * (int) (y));
			for (int j = 1; j < 9; j += 2) {
				bufG.drawLine(a - 60 + 15 * (j - 1), (int) (b - y), a - 60 + 15
						* (j), (int) (b - y));
			}
			double h = Math.sqrt(Math.pow(f2, 2) + Math.pow(y, 2));
			for (int n = 0; b - y - 15 * (n + 1) * y / h > 0; n += 2) {
				bufG.drawLine((int) (a + 60 - 15 * n * f2 / h),
						(int) (b - y - 15 * n * y / h), (int) (a + 60 - 15
								* (n + 1) * f2 / h), (int) (b - y - 15
								* (n + 1) * y / h));
			}
			bufG.setColor(Color.RED);
			bufG.setStroke(new BasicStroke(3));
			bufG.drawLine((int) x, (int) (b - y), (int) x, b);
			bufG.drawLine((int) (a + 60 + f2 - f1 * f2 / (f1 + x - a + 60)),
					(int) (b - (y * f1 / (f1 + x - a + 60))), (int) (a + 60
							+ f2 - f1 * f2 / (f1 + x - a + 60)), b);
		}

		g.drawImage(bufferImg, 0, 0, null);

	}
}
