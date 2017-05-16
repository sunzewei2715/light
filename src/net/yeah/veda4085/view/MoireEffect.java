package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

public class MoireEffect {
	public JPanel centerPanel;
	private Image bufferImg = null;

	private JPanel topPanel = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(MoireEffect.this.bufferImg, 0, 0, null);
		}
	};
	private JPanel botPanel;
	private JLabel angleLabel;
	private JLabel lineWidthLabel;
	private JLabel gapLabel;
	private JScrollBar angleScroll;
	private JScrollBar lineWidthScroll;
	private JScrollBar gapScroll;
	private double angle = 0.25D;
	private double lineWidth = 5.0D;
	private double gap = 5.0D;
	private int topPHeight;
	private int topPWidth;
	private Polygon polygon1 = new Polygon();
	private Polygon polygon2 = new Polygon();

	public JPanel launchFrame() {
		this.centerPanel = new JPanel(new BorderLayout());

		produceTopPanel();
		this.centerPanel.add(this.topPanel, "North");

		produceBotPanel();
		this.centerPanel.add(this.botPanel, "South");

		return this.centerPanel;
	}

	private void produceBotPanel() {
		this.botPanel = new JPanel(new BorderLayout());
		this.botPanel.setPreferredSize(new Dimension(600, 25));
		this.botPanel.setLayout(new GridLayout(1, 1));
		this.botPanel.setBorder(new BevelBorder(0));

		this.angleScroll = new JScrollBar(0, 25, 0, 1, 49);
		this.lineWidthScroll = new JScrollBar(0, 5, 0, 5, 40);
		this.gapScroll = new JScrollBar(0, 5, 0, 5, 40);

		this.angleScroll.addAdjustmentListener(new AngScrListener());
		this.lineWidthScroll.addAdjustmentListener(new LiWidListener());
		this.gapScroll.addAdjustmentListener(new GapListener());

		this.angleLabel = new JLabel("¼Ð½Ç£¨¦Ð£©£º0.25");
		this.lineWidthLabel = new JLabel("Ïß¿í£º5");
		this.gapLabel = new JLabel("·ì¿í£º5");

		this.botPanel.add(this.angleLabel);
		this.botPanel.add(this.angleScroll);
		this.botPanel.add(new JLabel());
		this.botPanel.add(this.lineWidthLabel);
		this.botPanel.add(this.lineWidthScroll);
		this.botPanel.add(new JLabel());
		this.botPanel.add(this.gapLabel);
		this.botPanel.add(this.gapScroll);
	}

	private void produceTopPanel() {
		this.topPanel.setBackground(Color.WHITE);

		this.topPanel.setPreferredSize(new Dimension(600, 400));
	}

	private void draw() {
		Graphics g = this.topPanel.getGraphics();

		this.topPWidth = this.topPanel.getWidth();
		this.topPHeight = this.topPanel.getHeight();

		this.bufferImg = this.topPanel.createImage(this.topPanel.getWidth(),
				this.topPanel.getHeight());
		Graphics2D bufG = (Graphics2D) this.bufferImg.getGraphics();

		bufG.clearRect(0, 0, this.topPWidth, this.topPHeight);
		bufG.setColor(Color.BLUE);

		this.polygon1.reset();
		this.polygon1.addPoint(0, 0);
		this.polygon1.addPoint(0, (int) this.lineWidth);
		this.polygon1.addPoint(this.topPWidth, (int) this.lineWidth);
		this.polygon1.addPoint(this.topPWidth, 0);
		for (int i = 0; i < this.topPWidth; i = (int) (i + (this.lineWidth + this.gap))) {
			this.polygon1.translate(0, (int) (this.lineWidth + this.gap));
			bufG.fillPolygon(this.polygon1);
			bufG.drawPolygon(this.polygon1);
		}

		this.polygon2.reset();
		int gapX = (int) (this.gap / Math.sin(this.angle * 3.141592653589793D));
		int gapY = (int) (this.gap / Math.cos(this.angle * 3.141592653589793D));
		int widthX = (int) (this.lineWidth / Math
				.sin(this.angle * 3.141592653589793D));
		int widthY = (int) (this.lineWidth / Math
				.cos(this.angle * 3.141592653589793D));
		for (int i = -10; i < 1.0D * this.topPWidth * 30 / (gapX + widthX); i++) {
			this.polygon2.reset();
			this.polygon2.addPoint((gapX + widthX) * i, 0);
			this.polygon2.addPoint((gapX + widthX) * i + gapX, 0);
			this.polygon2.addPoint(0, (gapY + widthY) * i + gapY);
			this.polygon2.addPoint(0, (gapY + widthY) * i);
			bufG.fillPolygon(this.polygon2);
			bufG.drawPolygon(this.polygon2);
		}

		g.drawImage(this.bufferImg, 0, 0, null);
	}

	class AngScrListener implements AdjustmentListener {
		AngScrListener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			MoireEffect.this.angle = (MoireEffect.this.angleScroll.getValue() / 100.0D);
			MoireEffect.this.angleLabel.setText("¼Ð½Ç£¨¦Ð£©£º"
					+ String.valueOf(MoireEffect.this.angle));
			MoireEffect.this.draw();
		}
	}

	class GapListener implements AdjustmentListener {
		GapListener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			MoireEffect.this.gap = MoireEffect.this.gapScroll.getValue();
			MoireEffect.this.gapLabel.setText("·ì¿í£º"
					+ String.valueOf(MoireEffect.this.gap));
			MoireEffect.this.draw();
		}
	}

	class LiWidListener implements AdjustmentListener {
		LiWidListener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			MoireEffect.this.lineWidth = MoireEffect.this.lineWidthScroll
					.getValue();
			MoireEffect.this.lineWidthLabel.setText("Ïß¿í£º"
					+ String.valueOf(MoireEffect.this.lineWidth));
			MoireEffect.this.draw();
		}
	}
}
