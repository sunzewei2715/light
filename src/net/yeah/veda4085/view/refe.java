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

public class refe {
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

	private JTextField ind1, ind2, ang1;
	private JLabel parameter, lind1, lind2, lang1;

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
			Image image = ImageIO.read(getClass().getResource("/picture/ref.png"));
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

		parameterP = new JPanel(new GridLayout(4, 3)); // 加入面板
		parameterP.setBackground(Color.LIGHT_GRAY);

		parameterP.setPreferredSize(new Dimension(460, 100));

		parameter = new JLabel("参数");
		lind1 = new JLabel("n1");
		lind2 = new JLabel("n2");
		lang1 = new JLabel("θ1");

		ind1 = new JTextField("1.0");
		ind2 = new JTextField("1.0");
		ang1 = new JTextField("0.0");

		ind1.addActionListener(new Text1Listerer());
		ind2.addActionListener(new Text2Listerer());
		ang1.addActionListener(new Text3Listerer());

		JScrollBar sind1 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				300);
		sind1.addAdjustmentListener(new Scroll1Listener());
		JScrollBar sind2 = new JScrollBar(JScrollBar.HORIZONTAL, 100, 0, 100,
				300);
		sind2.addAdjustmentListener(new Scroll2Listener());
		JScrollBar sang1 = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 900);
		sang1.addAdjustmentListener(new Scroll3Listener());

		parameterP.add(parameter);
		parameterP.add(new JLabel());
		parameterP.add(new JLabel());
		parameterP.add(lind1);
		parameterP.add(ind1);
		parameterP.add(sind1);
		parameterP.add(lind2);
		parameterP.add(ind2);
		parameterP.add(sind2);
		parameterP.add(lang1);
		parameterP.add(ang1);
		parameterP.add(sang1);

		leftP.add(machineP, BorderLayout.CENTER); // 输入面板加入到窗口
		leftP.add(parameterP, BorderLayout.SOUTH);

	}

	public void produceRightP() {
		rightP.setBackground(Color.GRAY);
	}

	class Text1Listerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();
			ind1.setText(String.valueOf(s));

			draw();
		}
	}

	class Text2Listerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();
			ind2.setText(String.valueOf(s));

			draw();

		}
	}

	class Text3Listerer implements ActionListener { // 文本监视
		public void actionPerformed(ActionEvent f) {
			String s = f.getActionCommand();
			ang1.setText(String.valueOf(s));

			draw();
		}
	}

	class Scroll1Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 100.0;

			ind1.setText(String.valueOf(s));

			draw();
		}
	}

	class Scroll2Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 100.0;

			ind2.setText(String.valueOf(s));

			draw();
		}
	}

	class Scroll3Listener implements AdjustmentListener { // 滚动条监视
		public void adjustmentValueChanged(AdjustmentEvent e) {
			double s = e.getValue() / 10.0;

			ang1.setText(String.valueOf(s));

			draw();
		}
	}

	public void draw() {
		double n1 = Double.parseDouble(ind1.getText());
		double n2 = Double.parseDouble(ind2.getText());
		double ang = Double.parseDouble(ang1.getText());

		Graphics g = rightP.getGraphics();

		Color c = Color.YELLOW;

		// 以下8行利用缓冲消除闪烁
		bufferImg = rightP.createImage(rightP.getWidth(), rightP.getHeight());
		Graphics2D bufG = (Graphics2D) bufferImg.getGraphics();
		bufG.setColor(c);
		bufG.fillRect(0, 0, bufferImg.getWidth(null),
				bufferImg.getHeight(null) / 2);// 背景
		bufG.setColor(c = Color.GREEN);
		bufG.fillRect(0, bufferImg.getHeight(null) / 2,
				bufferImg.getWidth(null), bufferImg.getHeight(null) / 2);// 背景
		int x = (int) rightP.getWidth() / 2;
		int y = (int) rightP.getHeight() / 2;

		BasicStroke s = (BasicStroke) bufG.getStroke();
		bufG.setStroke(new BasicStroke(2));

		bufG.setColor(Color.BLACK);

		bufG.drawLine(50, y, rightP.getWidth() - 50, y);

		bufG.drawLine(x, 100, x, rightP.getHeight() - 100);
		bufG.drawArc(x - 80, y - 80, 160, 160, 90, (int) (ang));
		bufG.setFont(new Font("宋体", Font.BOLD, 20));
		bufG.drawString("θ1", (int) (x - 90 * Math.sin(ang * Math.PI / (360))),
				(int) (y - 90 * Math.cos(ang * Math.PI / (360))));

		bufG.setColor(Color.RED);
		bufG.drawLine(x, y,
				(int) (x - 15 * Math.sin((ang - 20) * Math.PI / (180))),
				(int) (y - 15 * Math.cos((ang - 20) * Math.PI / (180))));
		bufG.drawLine(x, y,
				(int) (x - 15 * Math.sin((ang + 20) * Math.PI / (180))),
				(int) (y - 15 * Math.cos((ang + 20) * Math.PI / (180))));
		if (n1 * Math.sin(ang * Math.PI / 180) < n2) {
			bufG.drawLine((int) (x - 200 * Math.sin(ang * Math.PI / 180)),
					(int) (y - 200 * Math.cos(ang * Math.PI / 180)), x, y);
			bufG.drawLine((int) (x + 200 * Math.sin(ang * Math.PI / 180)),
					(int) (y - 200 * Math.cos(ang * Math.PI / 180)), x, y);
			double sin2 = n1 * Math.sin(ang * Math.PI / 180) / n2;
			double cos2 = Math.sqrt(1 - Math.pow(sin2, 2));
			bufG.drawLine((int) (x + 200 * sin2), (int) (y + 200 * cos2), x, y);

			bufG.setColor(Color.BLACK);
			bufG.setFont(new Font("宋体", Font.BOLD, 20));
			double ang2 = Math.asin(sin2);
			bufG.drawArc(x - 80, y - 80, 160, 160, 270,
					(int) (ang2 * 180 / Math.PI));
			bufG.drawString("θ2", (int) (x + 90 * Math.sin(ang2 / 2)),
					(int) (y + 90 * Math.cos(ang2 / 2)));
			bufG.setColor(Color.RED);
			bufG.drawLine(
					(int) (x + 200 * sin2),
					(int) (y + 200 * cos2),
					(int) ((x + 200 * sin2) - 15 * Math.sin(ang2 - 20 * Math.PI
							/ (180))),
					(int) ((y + 200 * cos2) - 15 * Math.cos(ang2 - 20 * Math.PI
							/ (180))));
			bufG.drawLine(
					(int) (x + 200 * sin2),
					(int) (y + 200 * cos2),
					(int) ((x + 200 * sin2) - 15 * Math.sin(ang2 + 20 * Math.PI
							/ (180))),
					(int) ((y + 200 * cos2) - 15 * Math.cos(ang2 + 20 * Math.PI
							/ (180))));
		}

		else {
			bufG.drawLine((int) (x - 200 * Math.sin(ang * Math.PI / 180)),
					(int) (y - 200 * Math.cos(ang * Math.PI / 180)), x, y);
			bufG.drawLine((int) (x + 200 * Math.sin(ang * Math.PI / 180)),
					(int) (y - 200 * Math.cos(ang * Math.PI / 180)), x, y);
		}
		bufG.drawLine((int) (x + 200 * Math.sin(ang * Math.PI / 180)),
				(int) (y - 200 * Math.cos(ang * Math.PI / 180)),
				(int) ((x + 200 * Math.sin(ang * Math.PI / 180)) - 15 * Math
						.sin((ang + 20) * Math.PI / (180))),
				(int) ((y - 200 * Math.cos(ang * Math.PI / 180)) + 15 * Math
						.cos((ang + 20) * Math.PI / (180))));
		bufG.drawLine((int) (x + 200 * Math.sin(ang * Math.PI / 180)),
				(int) (y - 200 * Math.cos(ang * Math.PI / 180)),
				(int) ((x + 200 * Math.sin(ang * Math.PI / 180)) - 15 * Math
						.sin((ang - 20) * Math.PI / (180))),
				(int) ((y - 200 * Math.cos(ang * Math.PI / 180)) + 15 * Math
						.cos((ang - 20) * Math.PI / (180))));
		bufG.setColor(Color.BLUE);
		bufG.setFont(new Font("宋体", Font.BOLD, 20));
		bufG.drawString("n1", 50, y - 7);
		bufG.drawString("n2", 50, y + 18);
		bufG.drawString("入射光", 50, 90);
		bufG.drawString("折射光", rightP.getWidth() - 75, rightP.getHeight() - 95);
		bufG.drawString("反射光", rightP.getWidth() - 75, 90);
		g.drawImage(bufferImg, 0, 0, null);
	}

}
