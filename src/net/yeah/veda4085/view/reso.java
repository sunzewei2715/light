package net.yeah.veda4085.view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import net.yeah.veda4085.algorithms.resoalg;

public class reso {
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

	private JLabel lamdal, dl, rl;

	private JTextField lamdat, dt, rt;

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
		upP.setPreferredSize(new Dimension(centerPanel.getWidth(), 100));

		machineP = new JPanel(); // 加入面板
		machineP.setBackground(Color.WHITE);

		try {
			Image image = ImageIO.read(getClass().getResource("/picture/036.png"));
			BufferedImage tag = new BufferedImage(300, 90,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(
					image.getScaledInstance(300, 90, Image.SCALE_SMOOTH), 0,
					0, null);
			image = tag;
			ImageIcon picture = new ImageIcon(image);
			machineP.add(new JLabel(picture));
		} catch (Exception e) {

		}
		parameterP = new JPanel(new GridLayout(3, 3)); // 加入面板
		parameterP.setBackground(Color.LIGHT_GRAY);

		parameterP.setPreferredSize(new Dimension(460, 100));

		lamdal = new JLabel("波长(10^(-9)m)");
		dl = new JLabel("两物点间距(10^(-2)m)");
		rl = new JLabel("孔径(10^(-4)m)");

		lamdat = new JTextField("400");
		dt = new JTextField("0");
		rt = new JTextField("40");

		lamdat.addActionListener(new lamdatListener());
		dt.addActionListener(new dtListener());
		rt.addActionListener(new rtListener());

		JScrollBar lamdas = new JScrollBar(JScrollBar.HORIZONTAL, 400, 0, 400,
				760);
		JScrollBar ds = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 100);
		JScrollBar rs = new JScrollBar(JScrollBar.HORIZONTAL, 40, 0, 40, 240);

		lamdas.addAdjustmentListener(new lamdasListener());
		ds.addAdjustmentListener(new dsListener());
		rs.addAdjustmentListener(new rsListener());

		parameterP.add(lamdal);
		parameterP.add(lamdat);
		parameterP.add(lamdas);
		parameterP.add(dl);
		parameterP.add(dt);
		parameterP.add(ds);
		parameterP.add(rl);
		parameterP.add(rt);
		parameterP.add(rs);

		upP.add(machineP, BorderLayout.WEST);
		upP.add(parameterP, BorderLayout.EAST);
	}

	public void producedownP() {
		downP.setPreferredSize(new Dimension(centerPanel.getWidth(), 450));
		downP.setBackground(Color.WHITE);
	}

	class lamdatListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			lamdat.setText(String.valueOf(s));

			draw();
		}
	}

	class dtListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			dt.setText(String.valueOf(s));

			draw();
		}
	}

	class rtListener implements ActionListener {
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();

			rt.setText(String.valueOf(s));

			draw();
		}
	}

	class lamdasListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			lamdat.setText(String.valueOf(m));

			draw();
		}
	}

	class dsListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			dt.setText(String.valueOf(m));

			draw();
		}
	}

	class rsListener implements AdjustmentListener {
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double m = e.getValue();

			rt.setText(String.valueOf(m));

			draw();
		}
	}

	public void draw() {
		Graphics g = downP.getGraphics();
		Color c = Color.WHITE;

		// 以下8行利用缓冲消除闪烁
		bufferImg = downP.createImage(downP.getWidth(), downP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null), bufferImg.getHeight(null));// 背景

		double lamda = Double.parseDouble(lamdat.getText());
		double d = Double.parseDouble(dt.getText()) / 10;
		double r = Double.parseDouble(rt.getText());

		resoalg m = new resoalg();
		double[][] k = m.resoalg1(lamda, d, r);
		double[] s = m.resoalg2(lamda, d, r);
		double[] t = m.resoalg3(lamda, d, r);
		for (int i = 0; i < 450; i++) {
			for (int j = 0; j < 450; j++) {
				c = new InitColor().WavlenChangetoRGB((int) (lamda),
						(float) (k[i][j] / 1.0));
				bufG.setColor(c);
				bufG.drawLine(i, j, i, j);
			}
		}
		int a = centerPanel.getWidth() - 450;
		int b = 400;
		Color p = Color.BLUE;
		bufG.setStroke(new BasicStroke(3));
		bufG.setColor(p);
		for (int j = 0; j < 449; j++) {
			bufG.drawLine(a + j, b - (int) (200 * s[j]), a + j + 1, b
					- (int) (200 * s[j + 1]));
		}
		bufG.drawLine(a - 200, 100, a - 140, 100);
		bufG.drawString("物点1光强", a - 120, 100);
		Color q = Color.GREEN;
		bufG.setColor(q);
		bufG.setStroke(new BasicStroke(3));
		for (int j = 0; j < 449; j++) {
			bufG.drawLine(a + j, b - (int) (200 * t[j]), a + j + 1, b
					- (int) (200 * t[j + 1]));
		}
		bufG.drawLine(a - 200, 200, a - 140, 200);
		bufG.drawString("物点2光强", a - 120, 200);
		Color w = Color.BLACK;
		bufG.setColor(w);
		bufG.setStroke(new BasicStroke(5));
		for (int j = 0; j < 449; j++) {
			bufG.drawLine(a + j, b - (int) (200 * t[j] + 200 * s[j]),
					a + j + 1, b - (int) (200 * t[j + 1] + 200 * s[j + 1]));
		}
		bufG.drawLine(a - 200, 300, a - 140, 300);
		bufG.drawString("总光强", a - 120, 300);

		g.drawImage(bufferImg, 0, 0, null);

	}

}
