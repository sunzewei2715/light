package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class DiffractionGratingAlg {
	double WL; // 波长
	double b; // 缝宽
	double n; // 缝数
	double d; // 缝周期
	double L = 4; // 光栅与屏间的透镜到屏的距离,单位MM
	double I0 = 1; // 每一单缝在入射光方向光强

	public DiffractionGratingAlg(double WL, double b, double n, double d) {
		this.WL = WL * Math.pow(10, -3); // 单位转化为UM
		this.b = b * Math.pow(10, -3); // 单位转化为UM
		this.n = n;
		this.d = d * Math.pow(10, -3); // 单位转化为UM

		this.L = this.L * Math.pow(10, 3); // 单位转化为UM
	}

	private double relateLightIntensity(double Y) { // 计算屏上各点的光强

		double y = Y; // 屏上一点到屏中心距离,单位1UM
		double B;
		double sinQ;
		double r;
		double RLI; // 相对光强 relateLightIntensity

		sinQ = y / Math.sqrt((y * y + L * L));
		B = Math.PI * b * sinQ / WL;
		r = Math.PI * d * sinQ / WL;

		RLI = 255 * Math.pow(Math.sin(B), 2) * Math.pow(Math.sin(n * r), 2)
				/ (Math.pow(B, 2) * Math.pow(Math.sin(r), 2));

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

		for (int i = 1; size / 2 + 1 - i >= 0; i++) {
			temp = relateLightIntensity(i * 0.02); // 增量为0.02UM
			A.set(size / 2 + 1 - i, temp);
			B.set(i - 1, temp);
		}

		A.addAll(B);

		// 归一化
		for (int i = 0; i < A.size(); i++) {
			A.set(i, A.get(i));
		}

		return A;
	}
}
