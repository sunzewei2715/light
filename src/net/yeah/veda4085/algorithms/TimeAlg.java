package net.yeah.veda4085.algorithms;

import java.awt.Color;
import java.util.ArrayList;

import net.yeah.veda4085.view.InitColor;

public class TimeAlg {

	double wavLen, detWavLen, d, l2;//
	int timePre;
	final int l = 100000;

	public TimeAlg(double wavLen, double detWavLen, double d, double l2, int Pre) {
		this.wavLen = wavLen;
		if (detWavLen < 0 || detWavLen >= 1) {
			detWavLen = 0;
		}
		this.detWavLen = wavLen * detWavLen;
		this.d = d;
		this.l2 = l2;
		this.timePre = Pre;
	}

	private double[] work(int length) {

		double[] lightSity = new double[length];
		int i, j;
		int y;
		double lightDiff, det;
		int r = l / 2;

		for (i = 0; i < length; i++) {
			y = i * l / length;
			lightSity[i] = 0;
			lightDiff = Math.hypot(r - y - d / 2, l2 * 10000)
					- Math.hypot(r - y + d / 2, l2 * 10000);

			for (j = 0; j < timePre; j++) {
				det = 2 * Math.PI * lightDiff * 1000
						/ (wavLen - detWavLen + j * 2 * detWavLen / timePre);
				lightSity[i] += 1 + Math.cos(det);
			}
			lightSity[i] = lightSity[i] / timePre;
		}
		return lightSity;
	}

	public double[] getArray(int length) {
		return work(length);
	}

	public ArrayList<Color> getColor(int length) {
		int i, j;
		int y;
		double lightDiff, det;
		int r = l / 2;
		Color temp;
		int red, g, b;
		ArrayList<Color> result = new ArrayList<Color>();
		for (i = 0; i < length; i++) {
			y = i * l / length;
			lightDiff = Math.hypot(r - y - d / 2, l2 * 10000)
					- Math.hypot(r - y + d / 2, l2 * 10000);
			red = 0;
			g = 0;
			b = 0;
			for (j = 0; j < timePre; j++) {

				det = 2 * Math.PI * lightDiff * 1000
						/ (wavLen - detWavLen + j * 2 * detWavLen / timePre);
				temp = new InitColor().WavlenChangetoRGB((int) (wavLen
						- detWavLen + j * 2 * detWavLen / timePre),
						(float) (1 + Math.cos(det)) / 2);
				red += temp.getRed();
				g += temp.getGreen();
				b += temp.getBlue();
			}
			temp = new Color(red / timePre, g / timePre, b / timePre)
					.brighter();
			result.add(temp);
		}
		return result;
	}

}
