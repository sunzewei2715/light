/**
 * Õ£÷π∞¥≈• ”Îø™ º∞¥≈•
 */
package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.BasicStroke;
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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import net.yeah.veda4085.algorithms.PolarizedLightPropagationAlg;

public class PolarizedLightPropagation {
	public JPanel centerPanel;
	private Image bufferImg = null;
	private JPanel leftP = new JPanel();
	PolLiProDraw pLPD = new PolLiProDraw();
	boolean drawOrNot = false;

	double[] z = new double[5000];
	double[] x = new double[5000];
	double[] y = new double[5000];

	double[] rotX = new double[5000];
	double[] rotY = new double[5000];

	List<Integer> drawX = new ArrayList();
	List<Integer> drawY = new ArrayList();
	int tempX;
	int tempY;
	double axisXX;
	double axisXY;
	double axisXZ;
	double axisYX;
	double axisYY;
	double axisYZ;
	double axisZX;
	double axisZY;
	double axisZZ;
	double rotAxisXX;
	double rotAxisXY;
	double rotAxisYX;
	double rotAxisYY;
	double rotAxisZX;
	double rotAxisZY;
	double xAmVal;
	double xWlVal;
	double xFqVal;
	double xPhVal;
	double yAmVal;
	double yWlVal;
	double yFqVal;
	double yPhVal;
	double vRVal;
	double vTVal;
	double vFVal;
	double zMaVal;
	double zMiVal;
	double[] parameters = new double[13];

	private JPanel rightP = new JPanel() {
		public void paint(Graphics g) {
			g.drawImage(PolarizedLightPropagation.this.bufferImg, 0, 0, null);
		}
	};
	private JPanel machineP;
	private JPanel parameterP;
	private JTextField xAmplitude;
	private JTextField xWaveLength;
	private JTextField xFrequency;
	private JTextField xPhase;
	private JTextField yAmplitude;
	private JTextField yWaveLength;
	private JTextField yFrequency;
	private JTextField yPhase;
	private JTextField viewR;
	private JTextField viewTeta;
	private JTextField viewFi;
	private JTextField zMin;
	private JTextField zMax;
	private JLabel xLabel;
	private JLabel yLabel;
	private JLabel viewLabel;
	private JLabel zLabel;
	private JLabel xAmplitudeL;
	private JLabel xWaveLengthL;
	private JLabel xFrequencyL;
	private JLabel xPhaseL;
	private JLabel yAmplitudeL;
	private JLabel yWaveLengthL;
	private JLabel yFrequencyL;
	private JLabel yPhaseL;
	private JLabel viewRL;
	private JLabel viewTetaL;
	private JLabel viewFiL;
	private JLabel zMinL;
	private JLabel zMaxL;
	private JButton stop;

	public JPanel launchFrame() {
		this.centerPanel = new JPanel(new BorderLayout());

		produceLeftP();
		this.centerPanel.add(this.leftP, "West");

		produceRightP();
		this.centerPanel.add(this.rightP, "Center");

		return this.centerPanel;
	}

	private void produceLeftP() {
		this.leftP = new JPanel(new BorderLayout());
		this.leftP.setPreferredSize(new Dimension(400, this.centerPanel
				.getHeight()));
		this.machineP = new JPanel();
		this.parameterP = new JPanel();
		this.machineP.setBackground(Color.WHITE);
		this.parameterP.setBackground(Color.LIGHT_GRAY);
		this.parameterP.setLayout(new GridLayout(18, 1));
		this.parameterP.setBorder(new BevelBorder(0));

		JScrollBar scroll1 = new JScrollBar(0, 20, 0, 0, 50);
		JScrollBar scroll2 = new JScrollBar(0, 25, 0, 0, 100);
		JScrollBar scroll3 = new JScrollBar(0, 10, 0, 0, 100);
		JScrollBar scroll4 = new JScrollBar(0, 0, 0, 0, 200);
		JScrollBar scroll5 = new JScrollBar(0, 20, 0, 0, 50);
		JScrollBar scroll6 = new JScrollBar(0, 25, 0, 0, 100);
		JScrollBar scroll7 = new JScrollBar(0, 10, 0, 0, 100);
		JScrollBar scroll8 = new JScrollBar(0, 50, 0, 0, 200);
		JScrollBar scroll9 = new JScrollBar(0, 100, 0, 30, 300);
		JScrollBar scroll10 = new JScrollBar(0, 42, 0, 0, 100);
		JScrollBar scroll11 = new JScrollBar(0, 64, 0, 0, 200);
		JScrollBar scroll12 = new JScrollBar(0, -100, 0, -100, 0);
		JScrollBar scroll13 = new JScrollBar(0, 100, 0, 0, 100);
		this.stop = new JButton("Õ£÷π");

		scroll1.addAdjustmentListener(new Scroll1Listener());
		scroll2.addAdjustmentListener(new Scroll2Listener());
		scroll3.addAdjustmentListener(new Scroll3Listener());
		scroll4.addAdjustmentListener(new Scroll4Listener());
		scroll5.addAdjustmentListener(new Scroll5Listener());
		scroll6.addAdjustmentListener(new Scroll6Listener());
		scroll7.addAdjustmentListener(new Scroll7Listener());
		scroll8.addAdjustmentListener(new Scroll8Listener());
		scroll9.addAdjustmentListener(new Scroll9Listener());
		scroll10.addAdjustmentListener(new Scroll10Listener());
		scroll11.addAdjustmentListener(new Scroll11Listener());
		scroll12.addAdjustmentListener(new Scroll12Listener());
		scroll13.addAdjustmentListener(new Scroll13Listener());
		this.stop.addActionListener(new Action1Listener());

		this.xAmplitude = new JTextField();
		this.xWaveLength = new JTextField();
		this.xFrequency = new JTextField();
		this.xPhase = new JTextField();

		this.yAmplitude = new JTextField();
		this.yWaveLength = new JTextField();
		this.yFrequency = new JTextField();
		this.yPhase = new JTextField();

		this.viewR = new JTextField();
		this.viewTeta = new JTextField();
		this.viewFi = new JTextField();

		this.zMin = new JTextField();
		this.zMax = new JTextField();

		this.xAmplitude.setText("0.2");
		this.xFrequency.setText("1.0");
		this.xWaveLength.setText("0.25");
		this.xPhase.setText("0.0");

		this.yAmplitude.setText("0.2");
		this.yFrequency.setText("1.0");
		this.yWaveLength.setText("0.25");
		this.yPhase.setText("0.5");

		this.viewR.setText("1.0");
		this.viewTeta.setText("0.42");
		this.viewFi.setText("0.64");

		this.zMin.setText("-1.0");
		this.zMax.setText("1.0");

		this.xLabel = new JLabel("x’Ò∂Ø");
		this.yLabel = new JLabel("y’Ò∂Ø");
		this.viewLabel = new JLabel(" ”Ω«");
		this.zLabel = new JLabel("z÷·«¯º‰");

		this.xAmplitudeL = new JLabel("x’Ò∑˘");
		this.xFrequencyL = new JLabel("x∆µ¬ ");
		this.xWaveLengthL = new JLabel("x≤®≥§");
		this.xPhaseL = new JLabel("xœ‡Œª");

		this.yAmplitudeL = new JLabel("y’Ò∑˘");
		this.yFrequencyL = new JLabel("y∆µ¬ ");
		this.yWaveLengthL = new JLabel("y≤®≥§");
		this.yPhaseL = new JLabel("yœ‡Œª");

		this.viewRL = new JLabel("r");
		this.viewTetaL = new JLabel("¶»");
		this.viewFiL = new JLabel("¶’");

		this.zMinL = new JLabel("zmin");
		this.zMaxL = new JLabel("zmax");

		this.parameterP.add(new JLabel());
		this.parameterP.add(this.xLabel);
		this.parameterP.add(new JLabel());
		this.parameterP.add(this.xAmplitudeL);
		this.parameterP.add(this.xAmplitude);
		this.parameterP.add(scroll1);
		this.parameterP.add(this.xWaveLengthL);
		this.parameterP.add(this.xWaveLength);
		this.parameterP.add(scroll2);
		this.parameterP.add(this.xFrequencyL);
		this.parameterP.add(this.xFrequency);
		this.parameterP.add(scroll3);
		this.parameterP.add(this.xPhaseL);
		this.parameterP.add(this.xPhase);
		this.parameterP.add(scroll4);

		this.parameterP.add(new JLabel());
		this.parameterP.add(this.yLabel);
		this.parameterP.add(new JLabel());
		this.parameterP.add(this.yAmplitudeL);
		this.parameterP.add(this.yAmplitude);
		this.parameterP.add(scroll5);
		this.parameterP.add(this.yWaveLengthL);
		this.parameterP.add(this.yWaveLength);
		this.parameterP.add(scroll6);
		this.parameterP.add(this.yFrequencyL);
		this.parameterP.add(this.yFrequency);
		this.parameterP.add(scroll7);
		this.parameterP.add(this.yPhaseL);
		this.parameterP.add(this.yPhase);
		this.parameterP.add(scroll8);

		this.parameterP.add(new JLabel());
		this.parameterP.add(this.viewLabel);
		this.parameterP.add(new JLabel());
		this.parameterP.add(this.viewRL);
		this.parameterP.add(this.viewR);
		this.parameterP.add(scroll9);
		this.parameterP.add(this.viewTetaL);
		this.parameterP.add(this.viewTeta);
		this.parameterP.add(scroll10);
		this.parameterP.add(this.viewFiL);
		this.parameterP.add(this.viewFi);
		this.parameterP.add(scroll11);

		this.parameterP.add(new JLabel());
		this.parameterP.add(this.zLabel);
		this.parameterP.add(new JLabel());
		this.parameterP.add(this.zMinL);
		this.parameterP.add(this.zMin);
		this.parameterP.add(scroll12);
		this.parameterP.add(this.zMaxL);
		this.parameterP.add(this.zMax);
		this.parameterP.add(scroll13);
		this.parameterP.add(this.stop);

		this.leftP.add(this.machineP, "Center");
		this.leftP.add(this.parameterP, "South");
	}

	private void produceRightP() {
		this.rightP.setBackground(Color.WHITE);
	}

	public void getParameters() {
		this.parameters[0] = Double.parseDouble(this.xAmplitude.getText());
		this.xAmVal = this.parameters[0];
		this.parameters[1] = Double.parseDouble(this.xWaveLength.getText());
		this.xWlVal = this.parameters[1];
		this.parameters[2] = Double.parseDouble(this.xFrequency.getText());
		this.xFqVal = this.parameters[2];
		this.parameters[3] = (Double.parseDouble(this.xPhase.getText()) * Math.PI);
		this.xPhVal = this.parameters[3];

		this.parameters[4] = Double.parseDouble(this.yAmplitude.getText());
		this.yAmVal = this.parameters[4];
		this.parameters[5] = Double.parseDouble(this.yWaveLength.getText());
		this.yWlVal = this.parameters[5];
		this.parameters[6] = Double.parseDouble(this.yFrequency.getText());
		this.yFqVal = this.parameters[6];
		this.parameters[7] = (Double.parseDouble(this.yPhase.getText()) * Math.PI);
		this.yPhVal = this.parameters[7];

		this.parameters[8] = Double.parseDouble(this.viewR.getText());
		this.vRVal = this.parameters[8];
		this.parameters[9] = Double.parseDouble(this.viewTeta.getText());
		this.vTVal = this.parameters[9];
		this.parameters[10] = Double.parseDouble(this.viewFi.getText());
		this.vFVal = this.parameters[10];

		this.parameters[11] = Double.parseDouble(this.zMin.getText());
		this.zMiVal = this.parameters[11];
		this.parameters[12] = Double.parseDouble(this.zMax.getText());
		this.zMaVal = this.parameters[12];
	}

	class Action1Listener implements ActionListener {
		Action1Listener() {
			// To do nothing
		}

		public void actionPerformed(ActionEvent e) {
			if (PolarizedLightPropagation.this.drawOrNot) {
				PolarizedLightPropagation.this.drawOrNot = false;
				PolarizedLightPropagation.this.stop.setText("ø™ º");
			} else {
				PolarizedLightPropagation.this.drawOrNot = true;
				PolarizedLightPropagation.this.stop.setText("Õ£÷π");
				synchronized (PolarizedLightPropagation.this.pLPD) {
					PolarizedLightPropagation.this.pLPD.notify();
				}
			}
		}
	}

	class PolLiProDraw extends Thread {
		PolLiProDraw() {
		}

		public void run() {
			Graphics g = PolarizedLightPropagation.this.rightP.getGraphics();
			Color c = Color.WHITE;

			PolarizedLightPropagation.this.bufferImg = PolarizedLightPropagation.this.rightP
					.createImage(
							PolarizedLightPropagation.this.rightP.getWidth(),
							PolarizedLightPropagation.this.rightP.getHeight());
			Graphics2D bufG = (Graphics2D) PolarizedLightPropagation.this.bufferImg
					.getGraphics();

			bufG.setColor(c);

			bufG.fillRect(0, 0,
					PolarizedLightPropagation.this.bufferImg.getWidth(null),
					PolarizedLightPropagation.this.bufferImg.getHeight(null));
			int center = PolarizedLightPropagation.this.rightP.getWidth() / 2;
			PolarizedLightPropagation.this.axisXX = (PolarizedLightPropagation.this.rightP
					.getHeight() / 5.0D * 4.0D);
			PolarizedLightPropagation.this.axisXY = 0.0D;
			PolarizedLightPropagation.this.axisXZ = 0.0D;
			PolarizedLightPropagation.this.axisYX = 0.0D;
			PolarizedLightPropagation.this.axisYY = (PolarizedLightPropagation.this.rightP
					.getHeight() / 5.0D * 4.0D);
			PolarizedLightPropagation.this.axisYZ = 0.0D;
			PolarizedLightPropagation.this.axisZX = 0.0D;
			PolarizedLightPropagation.this.axisZY = 0.0D;
			PolarizedLightPropagation.this.axisZZ = (PolarizedLightPropagation.this.rightP
					.getHeight() / 5.0D * 4.0D);

			bufG.setColor(Color.BLACK);

			double time = 0.0D;
			while (true) {
				time += 0.4D;
				if (PolarizedLightPropagation.this.drawOrNot) {
					for (int i = 0; i < 5000; i++) {
						if ((i > PolarizedLightPropagation.this.zMiVal * 2500.0D + 2500.0D)
								&& (i < PolarizedLightPropagation.this.zMaVal * 2500.0D + 2500.0D)) {
							PolarizedLightPropagation.this.y[i] = ((0.0004D * i - 1.0D) / PolarizedLightPropagation.this.vRVal);
							PolarizedLightPropagation.this.x[i] = (Math
									.cos(PolarizedLightPropagation.this.xFqVal
											* time
											- 2
											* Math.PI
											/ PolarizedLightPropagation.this.xWlVal
											* PolarizedLightPropagation.this.y[i]
											+ PolarizedLightPropagation.this.xPhVal)
									* PolarizedLightPropagation.this.xAmVal / PolarizedLightPropagation.this.vRVal);
							PolarizedLightPropagation.this.z[i] = (Math
									.cos(PolarizedLightPropagation.this.yFqVal
											* time
											- 2
											* Math.PI
											/ PolarizedLightPropagation.this.yWlVal
											* PolarizedLightPropagation.this.y[i]
											+ PolarizedLightPropagation.this.yPhVal)
									* PolarizedLightPropagation.this.yAmVal / PolarizedLightPropagation.this.vRVal);
						} else {
							PolarizedLightPropagation.this.x[i] = 0.0D;
							PolarizedLightPropagation.this.y[i] = 0.0D;
							PolarizedLightPropagation.this.z[i] = 0.0D;
						}

						PolarizedLightPropagation.this.rotX[i] = PolarizedLightPropagationAlg
								.rotationX(PolarizedLightPropagation.this.x[i],
										PolarizedLightPropagation.this.y[i],
										PolarizedLightPropagation.this.z[i]);
						PolarizedLightPropagation.this.rotY[i] = PolarizedLightPropagationAlg
								.rotationY(PolarizedLightPropagation.this.x[i],
										PolarizedLightPropagation.this.y[i],
										PolarizedLightPropagation.this.z[i]);

						PolarizedLightPropagation.this.tempX = ((int) (PolarizedLightPropagation.this.rotX[i] * 200.0D + center));
						PolarizedLightPropagation.this.tempY = ((int) (PolarizedLightPropagation.this.rotY[i] * 200.0D + center));
						if ((PolarizedLightPropagation.this.tempX != center)
								|| (PolarizedLightPropagation.this.tempY != center)) {
							PolarizedLightPropagation.this.drawX
									.add(Integer
											.valueOf(PolarizedLightPropagation.this.tempX));
							PolarizedLightPropagation.this.drawY
									.add(Integer
											.valueOf(PolarizedLightPropagation.this.tempY));
						}
					}
					PolarizedLightPropagation.this.rotAxisXX = PolarizedLightPropagationAlg
							.rotationX(PolarizedLightPropagation.this.axisXX,
									PolarizedLightPropagation.this.axisXY,
									PolarizedLightPropagation.this.axisXZ);
					PolarizedLightPropagation.this.rotAxisXY = PolarizedLightPropagationAlg
							.rotationY(PolarizedLightPropagation.this.axisXX,
									PolarizedLightPropagation.this.axisXY,
									PolarizedLightPropagation.this.axisXZ);
					PolarizedLightPropagation.this.rotAxisYX = PolarizedLightPropagationAlg
							.rotationX(PolarizedLightPropagation.this.axisYX,
									PolarizedLightPropagation.this.axisYY,
									PolarizedLightPropagation.this.axisYZ);
					PolarizedLightPropagation.this.rotAxisYY = PolarizedLightPropagationAlg
							.rotationY(PolarizedLightPropagation.this.axisYX,
									PolarizedLightPropagation.this.axisYY,
									PolarizedLightPropagation.this.axisYZ);
					PolarizedLightPropagation.this.rotAxisZX = PolarizedLightPropagationAlg
							.rotationX(PolarizedLightPropagation.this.axisZX,
									PolarizedLightPropagation.this.axisZY,
									PolarizedLightPropagation.this.axisZZ);
					PolarizedLightPropagation.this.rotAxisZY = PolarizedLightPropagationAlg
							.rotationY(PolarizedLightPropagation.this.axisZX,
									PolarizedLightPropagation.this.axisZY,
									PolarizedLightPropagation.this.axisZZ);
					bufG.setStroke(new BasicStroke(1));
					bufG.setColor(Color.BLACK);
					bufG.drawLine(center
							- (int) PolarizedLightPropagation.this.rotAxisXX
							/ 2, center
							- (int) PolarizedLightPropagation.this.rotAxisXY
							/ 2, center
							+ (int) PolarizedLightPropagation.this.rotAxisXX
							/ 2, center
							+ (int) PolarizedLightPropagation.this.rotAxisXY
							/ 2);
					bufG.drawString("x", center
							- (int) PolarizedLightPropagation.this.rotAxisXX
							/ 2, center
							- (int) PolarizedLightPropagation.this.rotAxisXY
							/ 2);
					bufG.drawLine(center
							- (int) PolarizedLightPropagation.this.rotAxisYX
							/ 2, center
							- (int) PolarizedLightPropagation.this.rotAxisYY
							/ 2, center
							+ (int) PolarizedLightPropagation.this.rotAxisYX
							/ 2, center
							+ (int) PolarizedLightPropagation.this.rotAxisYY
							/ 2);
					bufG.drawString("z", center
							- (int) PolarizedLightPropagation.this.rotAxisYX
							/ 2, center
							- (int) PolarizedLightPropagation.this.rotAxisYY
							/ 2);
					bufG.drawLine(center
							- (int) PolarizedLightPropagation.this.rotAxisZX
							/ 2, center
							- (int) PolarizedLightPropagation.this.rotAxisZY
							/ 2, center
							+ (int) PolarizedLightPropagation.this.rotAxisZX
							/ 2, center
							+ (int) PolarizedLightPropagation.this.rotAxisZY
							/ 2);
					bufG.drawString("y", center
							- (int) PolarizedLightPropagation.this.rotAxisZX
							/ 2, center
							- (int) PolarizedLightPropagation.this.rotAxisZY
							/ 2);
					bufG.setStroke(new BasicStroke(3));
					bufG.setColor(Color.red);
					int[] drawXInt = new int[PolarizedLightPropagation.this.drawX
							.size()];
					int[] drawYInt = new int[PolarizedLightPropagation.this.drawY
							.size()];
					for (int i = 0; i < PolarizedLightPropagation.this.drawX
							.size(); i++) {
						drawXInt[i] = ((Integer) PolarizedLightPropagation.this.drawX
								.get(i)).intValue();
						drawYInt[i] = ((Integer) PolarizedLightPropagation.this.drawY
								.get(i)).intValue();
					}

					bufG.drawPolyline(drawXInt, drawYInt,
							PolarizedLightPropagation.this.drawX.size());
					BasicStroke bs = new BasicStroke(9, BasicStroke.CAP_ROUND,
							BasicStroke.JOIN_ROUND);
					bufG.setStroke(bs);
					bufG.setColor(Color.black);
					bufG.drawPolyline(drawXInt, drawYInt, 10);
					try {
						Thread.sleep(40L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					g.drawImage(PolarizedLightPropagation.this.bufferImg, 0, 0,
							null);
					bufG.clearRect(0, 0,
							PolarizedLightPropagation.this.rightP.getWidth(),
							PolarizedLightPropagation.this.rightP.getHeight());
					PolarizedLightPropagation.this.drawX.clear();
					PolarizedLightPropagation.this.drawY.clear();
				} else {
					try {
						synchronized (PolarizedLightPropagation.this.pLPD) {
							PolarizedLightPropagation.this.pLPD.wait();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	class Scroll10Listener implements AdjustmentListener {
		Scroll10Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.viewTeta.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll11Listener implements AdjustmentListener {
		Scroll11Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.viewFi.setText(String.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);
			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll12Listener implements AdjustmentListener {
		Scroll12Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.zMin.setText(String.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll13Listener implements AdjustmentListener {
		Scroll13Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.zMax.setText(String.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll1Listener implements AdjustmentListener {
		Scroll1Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.xAmplitude.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll2Listener implements AdjustmentListener {
		Scroll2Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.xWaveLength.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll3Listener implements AdjustmentListener {
		Scroll3Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 10.0D;
			PolarizedLightPropagation.this.xFrequency.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll4Listener implements AdjustmentListener {
		Scroll4Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.xPhase.setText(String.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll5Listener implements AdjustmentListener {
		Scroll5Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.yAmplitude.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll6Listener implements AdjustmentListener {
		Scroll6Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.yWaveLength.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll7Listener implements AdjustmentListener {
		Scroll7Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 10.0D;
			PolarizedLightPropagation.this.yFrequency.setText(String
					.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll8Listener implements AdjustmentListener {
		Scroll8Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.yPhase.setText(String.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}

	class Scroll9Listener implements AdjustmentListener {
		Scroll9Listener() {
		}

		public void adjustmentValueChanged(AdjustmentEvent e) {
			double temp = e.getValue() / 100.0D;
			PolarizedLightPropagation.this.viewR.setText(String.valueOf(temp));
			PolarizedLightPropagation.this.getParameters();
			PolarizedLightPropagationAlg
					.setParameters(PolarizedLightPropagation.this.parameters);

			PolarizedLightPropagation.this.drawOrNot = true;
			synchronized (PolarizedLightPropagation.this.pLPD) {
				if (!PolarizedLightPropagation.this.pLPD.isAlive())
					PolarizedLightPropagation.this.pLPD.start();
				else
					PolarizedLightPropagation.this.pLPD.notify();
			}
			PolarizedLightPropagation.this.stop.setText("Õ£÷π");
		}
	}
}
