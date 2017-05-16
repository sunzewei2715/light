package net.yeah.veda4085.algorithms;

import java.awt.Color;
import java.util.ArrayList;

import net.yeah.veda4085.view.InitColor;

public class SpaceAlg {
	double wavLen, s, d, l1, l2;
	int spacePre;
	final int l = 100000;
	int timePre;

	public SpaceAlg(double wavLen, double d, double l2, double s, double l1,
			int jingdu, int jingdu2) {
		this.wavLen = wavLen;
		this.s = s;
		this.d = d;
		this.l1 = l1;
		this.l2 = l2;
		this.timePre = jingdu;
		this.spacePre = jingdu2;
	}

	private double[] work(int length) {
		double[] lightSity = new double[length];
		double y, lightDiff;
		for (int i = 0; i < length; i++) {
			y = i * l / length;
			lightSity[i] = 0;
			lightDiff = Math.hypot(l / 2 - y - d / 2, l2 * 10000)
					- Math.hypot(l / 2 - y + d / 2, l2 * 10000);
			double lightDiff2, det;
			for (int j = 0; j < spacePre; j++) {
				y = s * j / spacePre;
				lightDiff2 = lightDiff
						+ Math.hypot(s / 2 - y + d / 2, l1 * 1000)
						- Math.hypot(s / 2 - y - d / 2, l1 * 1000);
				det = 2 * Math.PI * lightDiff2 * 1000 / wavLen;
				lightSity[i] += 1 + Math.cos(det);
			}
			lightSity[i] = lightSity[i] / spacePre;
		}
		return lightSity;
	}

	public double[] getArray(int length) {
		return work(length);
	}

	public ArrayList<Color> getColor(int length) { // 计算时距离单位为1um；
		ArrayList<Color> result = new ArrayList<Color>();
		double y, lightDiff;
		int red = 0, g = 0, b = 0;
		Color temp;
		int wavelen = 575;
		double detWavLen = wavelen * 0.339;
		for (int i = 0; i < length; i++) {
			y = i * l / length; // 转化y单位为1um
			lightDiff = Math.hypot(l / 2 - y - d / 2, l2 * 10000)
					- Math.hypot(l / 2 - y + d / 2, l2 * 10000);
			double lightDiff2, det;
			red = 0;
			g = 0;
			b = 0;
			for (int j = 0; j < spacePre; j++) {
				y = s * j / spacePre;
				lightDiff2 = lightDiff
						+ Math.hypot(s / 2 - y + d / 2, l1 * 1000)
						- Math.hypot(s / 2 - y - d / 2, l1 * 1000);

				for (int k = 0; k < timePre; k++) {
					det = 2
							* Math.PI
							* lightDiff2
							* 1000
							/ (wavelen - detWavLen + k * 2 * detWavLen
									/ timePre);
					temp = new InitColor().WavlenChangetoRGB((int) (wavelen
							- detWavLen + k * 2 * detWavLen / timePre),
							(float) (1 + Math.cos(det)) / 2);
					red += temp.getRed();
					g += temp.getGreen();
					b += temp.getBlue();
				}

			}
			temp = new Color((int) (red * 1.0 / timePre / spacePre), (int) (g
					* 1.0 / timePre / spacePre),
					(int) (b * 1.0 / timePre / spacePre)).brighter();
			result.add(temp);
		}

		return result;
	}

	public ArrayList<Double> getwhite(int length) {
		ArrayList<Color> c = getColor(length);
		ArrayList<Double> result = new ArrayList<Double>();
		float temp[] = { 0, 0, 0 };
		for (int i = 0; i < length; i++) {
			temp = Color.RGBtoHSB(c.get(i).getRed(), c.get(i).getGreen(), c
					.get(i).getBlue(), temp);
			result.add((double) temp[2]);
		}
		return result;
	}
}
