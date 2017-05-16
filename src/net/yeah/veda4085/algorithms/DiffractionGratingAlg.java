package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class DiffractionGratingAlg {
	double WL; // ����
	double b; // ���
	double n; // ����
	double d; // ������
	double L = 4; // ��դ�������͸�������ľ���,��λMM
	double I0 = 1; // ÿһ����������ⷽ���ǿ

	public DiffractionGratingAlg(double WL, double b, double n, double d) {
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
		r = Math.PI * d * sinQ / WL;

		RLI = 255 * Math.pow(Math.sin(B), 2) * Math.pow(Math.sin(n * r), 2)
				/ (Math.pow(B, 2) * Math.pow(Math.sin(r), 2));

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
}
