package net.yeah.veda4085.algorithms;

public class Prismalg {
	double delta;
	double i2_;
	double i2;
	double i1_;
	double i1; // 均为弧度单位
	double alpha;
	double n;

	public Prismalg(double n, double alpha, double i1) {
		this.n = n;
		this.alpha = alpha;
		this.i1 = i1;

	}

	public double I2() {
		i2 = Math.asin(Math.sin(i1) / n);
		return i2;
	}

	public double I2_() {
		i2_ = alpha - i2;
		return i2_;
	}

	public double I1_() {
		i1_ = Math.asin(n * Math.sin(alpha - Math.asin(Math.sin(i1) / n)));
		return i1_;
	}

	public double Delta() {
		delta = i1 + i1_ - alpha;
		return delta;
	}
}