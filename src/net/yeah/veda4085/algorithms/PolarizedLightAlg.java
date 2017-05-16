package net.yeah.veda4085.algorithms;

public class PolarizedLightAlg {
	double xA; // X方向振幅
	double yA; // Y方向振幅
	double pd; // 相位差

	double a; // 要返回的椭圆长轴与x轴的夹角
	double width; // 要返回的椭圆长轴
	double height; // 要返回的椭圆短轴

	public PolarizedLightAlg(double xA, double yA, double pd) {
		this.xA = xA;
		this.yA = yA;
		this.pd = pd;

		compute();
	}

	public double ovalWidth() { // 长轴
		double temp1 = Math.pow(Math.sin(pd), 2);
		double temp2 = Math.pow(Math.sin(a), 2) / Math.pow(yA, 2);
		double temp3 = Math.pow(Math.cos(a), 2) / Math.pow(xA, 2);
		double temp4 = 2 * Math.cos(pd) * Math.sin(a) * Math.cos(a) / (xA * yA);
		return Math.sqrt(temp1 / (temp2 + temp3 + temp4));
	}

	public double ovalHeight() { // 短轴
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
			// 正椭圆，而ovalWidth() = ovalHeight()不符合
			a = 0;
			width = xA;
			height = yA;
			return;
		} else {
			if (pd == 0 || pd == 180) { // ovalWidth(),ovalHeight()中分子为0，结果为0
				pd = pd / 180 * Math.PI;
				a = Math.atan(yA / xA); // 正为顺时针旋转，负为逆时针旋转

				if (pd == Math.PI) {
					a = 3.14 - a;
				}
				width = Math.sqrt(Math.pow(xA, 2) + Math.pow(yA, 2));
				height = 0;
				return;
			}

			pd = pd / 180 * Math.PI;

			if (xA == yA) { // a角度公式不能用（分母为0）
				a = Math.PI / 4; // 45度

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
