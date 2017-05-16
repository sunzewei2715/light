package net.yeah.veda4085.algorithms;
import java.awt.Color;
import java.util.ArrayList;

import net.yeah.veda4085.view.InitColor;

public class NDAlg {
	double R; // 曲率半径
	double WL; // 波长waveLength
	int timePre;// 白光细分份数

	// double I = 0.5; //光强 I1 = I2 =0.5

	
	public NDAlg(double R, double WL, int pre) {
		this.R = R * Math.pow(10, 3); // 单位转化为1UM
		this.WL = WL * Math.pow(10, -3); // 单位转化为1UM
		this.timePre = pre;
	}

	private double relateLightIntensity(double Y) { // 计算屏上各点的光强

		double y = Y; // 屏上一点到屏中心距离,即半径,单位1UM
		double OPD; // 光程差opticalPathDifference
		double PD; // 相位差phaseDifference
		double RLI; // 相对光强 relateLightIntensity

		OPD = 2 * (R - Math.sqrt(R * R - y * y)) + WL / 2;
		PD = 2 * Math.PI * OPD / WL;
		RLI = 1 + Math.cos(PD);

		return RLI;
	}

	public ArrayList<Double> allIntensity(int size) {
		ArrayList<Double> A = new ArrayList<Double>();
		double temp = 0;

		for (int i = 0; i < size; i++) {
			temp = relateLightIntensity(i * 4); // 增量为4UM
			A.add(temp);
		}

		return A;
	}

	public ArrayList<Color> getColor(int length) {
		int step = 4; // 增量为4um
		double lightDiff, det;
		int wavelen = 575;
		Color temp;
		double detWavLen = wavelen * 0.339;
		int red, g, bl;
		ArrayList<Color> result = new ArrayList<Color>();
		for (int i = 0; i < length; i++) {
			int y = i * step;
			lightDiff = 2 * (R - Math.sqrt(R * R - y * y)) + WL / 2;// 此处计算单位为1um；
			red = 0;
			g = 0;
			bl = 0;
			for (int j = 0; j < timePre; j++) {
				det = 2 * Math.PI * lightDiff * 1000
						/ (wavelen - detWavLen + j * 2 * detWavLen / timePre);// 此处计算所用单位为1nm；
				temp = new InitColor().WavlenChangetoRGB((int) (wavelen
						- detWavLen + j * 2 * detWavLen / timePre),
						(float) (1 + Math.cos(det)) / 2);
				red += temp.getRed();
				g += temp.getGreen();
				bl += temp.getBlue();
			}
			temp = new Color((int) (red * 1.0 / timePre * 1.25), (int) (g * 1.0
					/ timePre * 1.25), (int) (bl * 1.0 / timePre * 1.25))
					.brighter();
			// 1.25是根据最终所得图像亮度不溢出为原则,在测试过程中所得的最大修正因子；
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
