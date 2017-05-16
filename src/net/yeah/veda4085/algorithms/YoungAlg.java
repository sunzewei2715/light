package net.yeah.veda4085.algorithms;

import java.awt.Color;
import java.util.ArrayList;

import net.yeah.veda4085.view.InitColor;

public class YoungAlg {
	double L; // 双缝到屏距离
	double b; // 双缝距离
	double WL; // 波长waveLength
	int timePre;

	// double I = 0.5; //光强 I1 = I2 =0.5

	public YoungAlg(double L, double b, double WL, int jingdu) {
		this.L = L * Math.pow(10, 3); // 单位转化为10UM
		this.b = b / 10;
		this.WL = WL * Math.pow(10, -4); // 单位转化为10UM
		this.timePre = jingdu;
	}

	private double relateLightIntensity(double Y) { // 计算屏上各点的光强

		double y = Y; // 屏上一点到屏中心距离,单位1UM
		double OPD; // 光程差opticalPathDifference
		double PD; // 相位差phaseDifference
		double RLI; // 相对光强 relateLightIntensity

		OPD = Math.hypot(y - b / 2, L) - Math.hypot(y + b / 2, L);
		PD = 2 * Math.PI * OPD / WL;
		RLI = 1 + Math.cos(PD);

		return RLI;
	}

	public ArrayList<Double> allIntensity(int size) {
		ArrayList<Double> A = new ArrayList<Double>();
		ArrayList<Double> B = new ArrayList<Double>();

		for (int i = 0; i < size / 2 + 1; i++) { // 初始化
			A.add(0.0);
			B.add(0.0);
		}

		double temp = 0;

		for (int i = 0; size / 2 - i >= 0; i++) {
			temp = relateLightIntensity(i * 5); // 增量为50UM
			A.set(size / 2 - i, temp);
			B.set(i, temp);
		}

		A.addAll(B);

		return A;
	}

	public ArrayList<Color> getColor(int length) {
		int i, j;
		int y;
		int step = 5;// 由于计算时距离单位为10um，所以step=5可以令屏幕上一个像素间距的亮点间所表示的距离为50um；
		double lightDiff, det;
		int r = length / 2 * step;
		Color temp;
		int red, g, bl;
		double wavelen = 575;
		double detWavLen = 0.339 * wavelen;// 白光双缝干涉等价于中心波长为575，波长波动为0.339的时间相干性干涉；
		ArrayList<Color> result = new ArrayList<Color>();
		for (i = 0; i < length; i++) {
			y = i * step;// 由于单位为10um，所以step=5可以令屏幕上一个像素间距的亮点间所表示的距离为50um；
			lightDiff = Math.hypot(r - y - b / 2, L)
					- Math.hypot(r - y + b / 2, L);
			red = 0;
			g = 0;
			bl = 0;
			for (j = 0; j < timePre; j++) {

				det = 2 * Math.PI * lightDiff * 10000
						/ (wavelen - detWavLen + j * 2 * detWavLen / timePre);
				temp = new InitColor().WavlenChangetoRGB((int) (wavelen
						- detWavLen + j * 2 * detWavLen / timePre),
						(float) (1 + Math.cos(det)) / 2);
				red += temp.getRed();
				g += temp.getGreen();
				bl += temp.getBlue();
			}
			temp = new Color(red / timePre, g / timePre, bl / timePre)
					.brighter();
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
