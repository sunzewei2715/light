package net.yeah.veda4085.algorithms;

public class Dispersionalg {

	double delta;
	double i2_; // 出射点入射角
	double i2; // 入射点出射角
	double i1_; // 出射点出射角
	double i1; // 入射点入射角，均为弧度单位
	double alpha; // 出射入射光线之间的偏向角
	double n; // 折射率

	public Dispersionalg(double n, double alpha, double i1) {
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