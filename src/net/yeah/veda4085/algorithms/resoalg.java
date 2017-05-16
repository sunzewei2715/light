package net.yeah.veda4085.algorithms;

public class resoalg {
	double nx = 450;
	double ny = 450;
	double l = 9.843637;

	public double[][] resoalg1(double lamda1, double d1, double r1) {
		double dx = 0.2 / nx;
		double dd = d1 / 100;
		double lamda = lamda1;
		double cst = Math.PI * r1 * 1000 / lamda;
		double[][] I = new double[450][450];
		for (int i = 0; i < nx; i++) {
			double x = dx * i;
			for (int j = 0; j < ny; j++) {
				double y = dx * j;
				double ra = Math.sqrt((x - dd - 0.1) * (x - dd - 0.1)
						+ (y - 0.1) * (y - 0.1));
				double rb = Math.sqrt((x + dd - 0.1) * (x + dd - 0.1)
						+ (y - 0.1) * (y - 0.1));
				double al1 = cst * ra / l;
				double al2 = cst * rb / l;
				double ration1 = Math.sin(al1) / al1;
				double ration2 = Math.sin(al2) / al2;
				if (al1 == 0)
					ration1 = 1;
				if (al2 == 0)
					ration2 = 1;
				I[i][j] = (ration1 * ration1 + ration2 * ration2);
			}
		}
		double max = 0;
		for (int i = 0; i < 450; i++) {
			for (int j = 0; j < 450; j++) {
				if (max < I[i][j])
					max = I[i][j];
			}
		}
		for (int i = 0; i < 450; i++) {
			for (int j = 0; j < 450; j++) {
				I[i][j] = I[i][j] / max;
			}
		}
		return I;
	}

	public double[] resoalg2(double lamda1, double d1, double r1) {
		double dx = 0.2 / nx;
		double dd = d1 / 100;
		double lamda = lamda1;
		double cst = Math.PI * r1 * 1000 / lamda;
		double[] J = new double[450];
		for (int i = 0; i < nx; i++) {
			double x = dx * i;
			double y = 0.1;
			double ra = Math.sqrt((x - dd - 0.1) * (x - dd - 0.1) + (y - 0.1)
					* (y - 0.1));
			double al1 = cst * ra / l;
			double ration1 = Math.sin(al1) / al1;
			if (al1 == 0)
				ration1 = 1;
			J[i] = (ration1 * ration1);
		}
		double max = 0;
		for (int i = 0; i < 450; i++) {
			if (max < J[i])
				max = J[i];
		}
		for (int i = 0; i < 450; i++) {
			J[i] = J[i] / max;
		}
		return J;
	}

	public double[] resoalg3(double lamda1, double d1, double r1) {
		double dx = 0.2 / nx;
		double dd = d1 / 100;
		double lamda = lamda1;
		double cst = Math.PI * r1 * 1000 / lamda;
		double[] K = new double[450];
		for (int i = 0; i < nx; i++) {
			double x = dx * i;

			double y = 0.1;

			double rb = Math.sqrt((x + dd - 0.1) * (x + dd - 0.1) + (y - 0.1)
					* (y - 0.1));

			double al2 = cst * rb / l;

			double ration2 = Math.sin(al2) / al2;
			if (al2 == 0)
				ration2 = 1;
			K[i] = (ration2 * ration2);
		}
		double max = 0;
		for (int i = 0; i < 450; i++) {

			if (max < K[i])
				max = K[i];
		}

		for (int i = 0; i < 450; i++) {

			K[i] = K[i] / max;
		}

		return K;
	}

}
