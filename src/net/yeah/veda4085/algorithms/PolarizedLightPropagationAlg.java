package net.yeah.veda4085.algorithms;

public class PolarizedLightPropagationAlg {
	private static double xAmplitude;
	private static double xFrequency;
	private static double xPhase;
	private static double yAmplitude;
	private static double yFrequency;
	private static double yPhase;
	private static double viewTeta;
	private static double viewFi;
	private static double sinTeta;
	private static double cosTeta;
	private static double sinFi;
	private static double cosFi;

	public static void setParameters(double[] parameters) {
		xAmplitude = parameters[0];
		xFrequency = parameters[2];
		xPhase = parameters[3];

		yAmplitude = parameters[4];
		yFrequency = parameters[6];
		yPhase = parameters[7];

		viewTeta = parameters[9];
		viewFi = parameters[10];
		sinTeta = Math.sin(viewTeta * Math.PI);
		cosTeta = Math.cos(viewTeta * Math.PI);
		sinFi = Math.sin(viewFi * Math.PI);
		cosFi = Math.cos(viewFi * Math.PI);				//3.141592653589793D±»Ìæ»»³ÉMath.PI
	}

	public static double[] xAlg() {
		double[] x = new double[5000];

		for (int i = 0; i < 5000; i++) {				//?			D 		2.0D / 10.0D		
			x[i] = (Math.cos(xFrequency * (i * 2.0D / 10.0D) + xPhase) * xAmplitude);
		}

		return x;
	}

	public static double[] yAlg() {
		double[] y = new double[5000];
		for (int i = 0; i < 5000; i++) {
			y[i] = (Math.cos(yFrequency * (i * 2.0D / 5000.0D) + yPhase) * yAmplitude);
		}
		return y;
	}

	public static double rotationX(double x, double y, double z) {
		double rotX = cosTeta * x + sinTeta * y;
		return rotX;
	}

	public static double rotationY(double x, double y, double z) {
		double rotY = cosFi * (-sinTeta * x + cosTeta * y) + sinFi * z;
		return rotY;
	}

	public static double rotationZ(double x, double y, double z) {
		double rotZ = -sinFi * (-sinTeta * x + cosTeta * y) + cosFi * z;
		return rotZ;
	}
}
