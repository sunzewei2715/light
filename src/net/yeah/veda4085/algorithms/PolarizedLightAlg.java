package net.yeah.veda4085.algorithms;

public class PolarizedLightAlg {
	double xA; // X�������
	double yA; // Y�������
	double pd; // ��λ��

	double a; // Ҫ���ص���Բ������x��ļн�
	double width; // Ҫ���ص���Բ����
	double height; // Ҫ���ص���Բ����

	public PolarizedLightAlg(double xA, double yA, double pd) {
		this.xA = xA;
		this.yA = yA;
		this.pd = pd;

		compute();
	}

	public double ovalWidth() { // ����
		double temp1 = Math.pow(Math.sin(pd), 2);
		double temp2 = Math.pow(Math.sin(a), 2) / Math.pow(yA, 2);
		double temp3 = Math.pow(Math.cos(a), 2) / Math.pow(xA, 2);
		double temp4 = 2 * Math.cos(pd) * Math.sin(a) * Math.cos(a) / (xA * yA);
		return Math.sqrt(temp1 / (temp2 + temp3 + temp4));
	}

	public double ovalHeight() { // ����
		double temp1 = Math.pow(Math.sin(pd), 2);
		double temp2 = Math.pow(Math.cos(a), 2) / Math.pow(yA, 2);
		double temp3 = Math.pow(Math.sin(a), 2) / Math.pow(xA, 2);
		double temp4 = 2 * Math.cos(pd) * Math.sin(a) * Math.cos(a) / (xA * yA);
		return Math.sqrt(temp1 / (temp2 + temp3 - temp4));
	}

	public double getOvalWidth() {
		return width;
	}

	public double getOvalHeight() {
		return height;
	}

	public double rotateAngle() {
		return a;
	}

	public void compute() {
		if (xA == 0 || yA == 0) {
			a = 0;
			width = xA;
			height = yA;
			return;
		}

		if (pd == 90 || pd == -90 || pd == 270 || pd == -270) {
			// ����Բ����ovalWidth() = ovalHeight()������
			a = 0;
			width = xA;
			height = yA;
			return;
		} else {
			if (pd == 0 || pd == 180) { // ovalWidth(),ovalHeight()�з���Ϊ0�����Ϊ0
				pd = pd / 180 * Math.PI;
				a = Math.atan(yA / xA); // ��Ϊ˳ʱ����ת����Ϊ��ʱ����ת

				if (pd == Math.PI) {
					a = 3.14 - a;
				}
				width = Math.sqrt(Math.pow(xA, 2) + Math.pow(yA, 2));
				height = 0;
				return;
			}

			pd = pd / 180 * Math.PI;

			if (xA == yA) { // a�Ƕȹ�ʽ�����ã���ĸΪ0��
				a = Math.PI / 4; // 45��

				width = ovalWidth();
				height = ovalHeight();

				if (pd > 0 && pd <= 1.57) {
					a = -a;
				}
				if (pd > 1.57 && pd <= 3.14) {
					a = 3.14 - a;
				}
				return;
			} else {
				a = 0.5 * Math.atan(2 * xA * yA * Math.cos(pd)
						/ (Math.pow(xA, 2) - Math.pow(yA, 2)));

				if (pd > 0 && pd <= 1.57) {
					if (xA < yA) {
						a = 1.57 + a;
						width = ovalWidth();
						height = ovalHeight();
					} else {
						width = ovalWidth();
						height = ovalHeight();
					}
				}
				if (pd > 1.57 && pd <= 3.14) {
					if (xA < yA) {
						a = 3.14 + a;
						width = ovalWidth();
						height = ovalHeight();
					} else {
						a = a - 1.57;
						width = ovalWidth();
						height = ovalHeight();
					}

				}

				return;
			}
		}
	}
}
