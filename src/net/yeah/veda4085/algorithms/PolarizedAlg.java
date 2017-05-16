package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class PolarizedAlg {
	double WL; // 波长waveLength
	double a; // P1与X轴夹角
	double B; // P2与X轴夹角
	double neno; // |NE - NO|

	// double A = 1; //振幅 A1 = A2 =1

	public PolarizedAlg(double WL, double a, double B, double neno) {
		this.WL = WL * Math.pow(10, -3); // 单位转化为1UM
		this.a = a / 180 * 3.14; // 单位度
		this.B = B / 180 * 3.14; // 单位度
		this.neno = neno;
	}

	private double relateLightIntensity(double D) { // 计算屏上各点的光强

		double d = D; // 厚度,单位1UM
		double PD; // 相位差phaseDifference
		double RLI; // 相对光强 relateLightIntensity

		PD = 2 * Math.PI * d * neno / WL;
		RLI = Math.pow(Math.cos(a - B), 2) - Math.sin(2 * a) * Math.sin(2 * B)
				* Math.pow(Math.sin(PD / 2), 2);

		return RLI;
	}

	public ArrayList<Integer> allIntensity(ArrayList<Double> tick) {
		double max = 0;
		for (int i = 0; i < tick.size(); i++) { // 黑色代表无穷厚，白色代表厚度为0
			tick.set(i, -tick.get(i) - 1);
		}
		for (int i = 0; i < tick.size(); i++) {
			if (tick.get(i) > max) {
				max = tick.get(i);
			}
		}
		for (int i = 0; i < tick.size(); i++) {
			tick.set(i, tick.get(i) / max * 255);
		}

		ArrayList<Integer> A = new ArrayList<Integer>();
		ArrayList<Double> E = new ArrayList<Double>();
		int size = tick.size();

		double temp = 0;
		max = 0.01;

		for (int i = 0; i < size; i++) {
			temp = relateLightIntensity(tick.get(i));
			E.add(temp);
			if (temp > max) {
				max = temp;
			}
		}
		for (int i = 0; i < size; i++) {
			double t = E.get(i) / max * 255;
			A.add((int) t);
		}

		return A;
	}
}
