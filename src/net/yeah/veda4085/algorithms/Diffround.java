package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class Diffround {
	double WL; // ����
	double b; // ���
	double n; // ����
	double d; // ������
	double L = 4; // ��դ�������͸�������ľ���,��λMM
	double I0 = 1; // ÿһ����������ⷽ���ǿ

	public Diffround(double WL, double b, double n, double d) {
		this.WL = WL * Math.pow(10, -3); // ��λת��ΪUM
		this.b = b * Math.pow(10, -3); // ��λת��ΪUM
		this.n = n;
		this.d = d * Math.pow(10, -3); // ��λת��ΪUM

		this.L = this.L * Math.pow(10, 3); // ��λת��ΪUM
	}

	private double relateLightIntensity(double Y) { // �������ϸ���Ĺ�ǿ

		double y = Y; // ����һ�㵽�����ľ���,��λ1UM
		double B;
		double sinQ;
		double r;
		double RLI; // ��Թ�ǿ relateLightIntensity

		sinQ = y / Math.sqrt((y * y + L * L));
		B = Math.PI * b * sinQ / WL;

		RLI = 255 * Math.pow(j1(B), 2) / Math.pow(B, 2);

		return RLI;
	}

	public ArrayList<Double> allIntensity(int size) {
		ArrayList<Double> A = new ArrayList<Double>();
		ArrayList<Double> B = new ArrayList<Double>();

		for (int i = 0; i < size / 2 + 1; i++) { // ��ʼ��
			A.add(0.0);
			B.add(0.0);
		}

		double temp = 0;

		for (int i = 1; size / 2 + 1 - i >= 0; i++) {
			temp = relateLightIntensity(i * 0.02); // ����Ϊ0.02UM
			A.set(size / 2 + 1 - i, temp);
			B.set(i - 1, temp);
		}

		A.addAll(B);

		// ��һ��
		for (int i = 0; i < A.size(); i++) {
			A.set(i, A.get(i));
		}

		return A;
	}

	static public double j1(double x) throws ArithmeticException {

		double ax;
		double y;
		double ans1, ans2;

		if ((ax = Math.abs(x)) < 8.0) {
			y = x * x;
			ans1 = x
					* (72362614232.0 + y
							* (-7895059235.0 + y
									* (242396853.1 + y
											* (-2972611.439 + y
													* (15704.48260 + y
															* (-30.16036606))))));
			ans2 = 144725228442.0
					+ y
					* (2300535178.0 + y
							* (18583304.74 + y
									* (99447.43394 + y
											* (376.9991397 + y * 1.0))));
			return ans1 / ans2;
		} else {
			double z = 8.0 / ax;
			double xx = ax - 2.356194491;
			y = z * z;

			ans1 = 1.0
					+ y
					* (0.183105e-2 + y
							* (-0.3516396496e-4 + y
									* (0.2457520174e-5 + y * (-0.240337019e-6))));
			ans2 = 0.04687499995
					+ y
					* (-0.2002690873e-3 + y
							* (0.8449199096e-5 + y
									* (-0.88228987e-6 + y * 0.105787412e-6)));
			double ans = Math.sqrt(0.636619772 / ax)
					* (Math.cos(xx) * ans1 - z * Math.sin(xx) * ans2);
			if (x < 0.0)
				ans = -ans;
			return ans;
		}
	}

}
