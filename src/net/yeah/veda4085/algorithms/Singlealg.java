package net.yeah.veda4085.algorithms;

import java.awt.Color;
import java.util.ArrayList;

public class Singlealg {
	double r; // �����澵�뾶
	double s1; // ���
	double s2; // ���
	double y1; // ���
	double y2; // ���
	double n1; // ���������
	double n2; // �ұ�������
	boolean isTrue; // ���仹�Ƿ���

	public Singlealg(double y1, double n1, double n2, double s1, double r,
			boolean isTrue) {
		this.n1 = n1;
		this.n2 = n2;
		this.s1 = s1;
		this.r = r;
		this.y1 = y1;
		this.isTrue = isTrue;
		if (this.isTrue)
			this.n2 = -n1;

	}

	public double S2() {

		double s2;
		s2 = n2 / ((n2 - n1) / r + n1 / s1);
		return s2;
	}

	public double Y2() {

		double y2;
		y2 = y1 * n1 * (n2 / ((n2 - n1) / r + n1 / s1)) / (n2 * s1);
		return y2;
	}
}
