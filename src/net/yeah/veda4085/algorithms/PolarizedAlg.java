package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class PolarizedAlg {
	double WL; // ����waveLength
	double a; // P1��X��н�
	double B; // P2��X��н�
	double neno; // |NE - NO|

	// double A = 1; //��� A1 = A2 =1

	public PolarizedAlg(double WL, double a, double B, double neno) {
		this.WL = WL * Math.pow(10, -3); // ��λת��Ϊ1UM
		this.a = a / 180 * 3.14; // ��λ��
		this.B = B / 180 * 3.14; // ��λ��
		this.neno = neno;
	}

	private double relateLightIntensity(double D) { // �������ϸ���Ĺ�ǿ

		double d = D; // ���,��λ1UM
		double PD; // ��λ��phaseDifference
		double RLI; // ��Թ�ǿ relateLightIntensity

		PD = 2 * Math.PI * d * neno / WL;
		RLI = Math.pow(Math.cos(a - B), 2) - Math.sin(2 * a) * Math.sin(2 * B)
				* Math.pow(Math.sin(PD / 2), 2);

		return RLI;
	}

	public ArrayList<Integer> allIntensity(ArrayList<Double> tick) {
		double max = 0;
		for (int i = 0; i < tick.size(); i++) { // ��ɫ��������񣬰�ɫ������Ϊ0
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
