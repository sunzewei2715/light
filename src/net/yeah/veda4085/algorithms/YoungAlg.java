package net.yeah.veda4085.algorithms;

import java.awt.Color;
import java.util.ArrayList;

import net.yeah.veda4085.view.InitColor;

public class YoungAlg {
	double L; // ˫�쵽������
	double b; // ˫�����
	double WL; // ����waveLength
	int timePre;

	// double I = 0.5; //��ǿ I1 = I2 =0.5

	public YoungAlg(double L, double b, double WL, int jingdu) {
		this.L = L * Math.pow(10, 3); // ��λת��Ϊ10UM
		this.b = b / 10;
		this.WL = WL * Math.pow(10, -4); // ��λת��Ϊ10UM
		this.timePre = jingdu;
	}

	private double relateLightIntensity(double Y) { // �������ϸ���Ĺ�ǿ

		double y = Y; // ����һ�㵽�����ľ���,��λ1UM
		double OPD; // ��̲�opticalPathDifference
		double PD; // ��λ��phaseDifference
		double RLI; // ��Թ�ǿ relateLightIntensity

		OPD = Math.hypot(y - b / 2, L) - Math.hypot(y + b / 2, L);
		PD = 2 * Math.PI * OPD / WL;
		RLI = 1 + Math.cos(PD);

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

		for (int i = 0; size / 2 - i >= 0; i++) {
			temp = relateLightIntensity(i * 5); // ����Ϊ50UM
			A.set(size / 2 - i, temp);
			B.set(i, temp);
		}

		A.addAll(B);

		return A;
	}

	public ArrayList<Color> getColor(int length) {
		int i, j;
		int y;
		int step = 5;// ���ڼ���ʱ���뵥λΪ10um������step=5��������Ļ��һ�����ؼ������������ʾ�ľ���Ϊ50um��
		double lightDiff, det;
		int r = length / 2 * step;
		Color temp;
		int red, g, bl;
		double wavelen = 575;
		double detWavLen = 0.339 * wavelen;// �׹�˫�����ȼ������Ĳ���Ϊ575����������Ϊ0.339��ʱ������Ը��棻
		ArrayList<Color> result = new ArrayList<Color>();
		for (i = 0; i < length; i++) {
			y = i * step;// ���ڵ�λΪ10um������step=5��������Ļ��һ�����ؼ������������ʾ�ľ���Ϊ50um��
			lightDiff = Math.hypot(r - y - b / 2, L)
					- Math.hypot(r - y + b / 2, L);
			red = 0;
			g = 0;
			bl = 0;
			for (j = 0; j < timePre; j++) {

				det = 2 * Math.PI * lightDiff * 10000
						/ (wavelen - detWavLen + j * 2 * detWavLen / timePre);
				temp = new InitColor().WavlenChangetoRGB((int) (wavelen
						- detWavLen + j * 2 * detWavLen / timePre),
						(float) (1 + Math.cos(det)) / 2);
				red += temp.getRed();
				g += temp.getGreen();
				bl += temp.getBlue();
			}
			temp = new Color(red / timePre, g / timePre, bl / timePre)
					.brighter();
			result.add(temp);
		}
		return result;
	}

	public ArrayList<Double> getwhite(int length) {
		ArrayList<Color> c = getColor(length);
		ArrayList<Double> result = new ArrayList<Double>();
		float temp[] = { 0, 0, 0 };
		for (int i = 0; i < length; i++) {
			temp = Color.RGBtoHSB(c.get(i).getRed(), c.get(i).getGreen(), c
					.get(i).getBlue(), temp);
			result.add((double) temp[2]);
		}
		return result;
	}
}
