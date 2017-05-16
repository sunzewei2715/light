package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class FresenlAlg {
	double WL;
	double L;
	double D;
	double En = 1.0;

	public ArrayList<Integer> fftIntensity(ArrayList<Double> data, int N) {
		int nn[] = new int[2];
		nn[0] = N;
		nn[1] = N;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				data.set((i * N + j) * 2,
						data.get((i * N + j) * 2) * Math.pow(-1, i + j));
				data.set((i * N + j) * 2 + 1, data.get((i * N + j) * 2 + 1)
						* Math.pow(-1, i + j));
			}
		}

		fourn(data, nn, 1);

		ArrayList<Integer> E = new ArrayList<Integer>();
		ArrayList<Double> EA = new ArrayList<Double>();
		double max = 0;
		for (int i = 1; i < data.size(); i += 2) {
			double t = Math.sqrt(Math.pow(data.get(i), 2)
					+ Math.pow(data.get(i - 1), 2));

			if (max < t) {
				max = t;
			}
			EA.add(t);
		}

		for (int i = 0; i < EA.size(); i++) {
			int t = (int) (EA.get(i) / max * (256 * 256 * 256 - 1));
			E.add(-t);
		}

		return E;
	}

	public ArrayList<Integer> allIntensity(double x, double z, double d,
			ArrayList<Double> data, int N) {

		// z乘以10的-5使L变化幅度小，便于图像观察，真实计算应把单位化为一样,x和d同理
		this.WL = x * Math.pow(10, -11);
		this.L = z * Math.pow(10, -5);
		this.D = d * Math.pow(10, -5);

		int nn[] = new int[2];
		nn[0] = N;
		nn[1] = N;

		// 原data存有图像的像素，为负数，现改为正的，
		// 则代表有光的白色区域data值大，无光的黑色区域data值小
		for (int i = 0; i < data.size(); i += 2) {
			double t = 0;
			if (256 * 256 * 256 + data.get(i) < 1) {
				t = 1;
			} else {
				t = 256 * 256 * 256 + data.get(i);
			}
			data.set(i, t);
		}

		// 以下for循环等价swap，交换象限
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				data.set((i * N + j) * 2,
						data.get((i * N + j) * 2) * Math.pow(-1, i + j));
				data.set((i * N + j) * 2 + 1, data.get((i * N + j) * 2 + 1)
						* Math.pow(-1, i + j));
			}
		}

		fourn(data, nn, 1);

		// if(d>=0.8){
		// 点扩展抽样
		for (int j = 0, n = 1; j < N; j++) {
			int jj = j - N / 2;

			for (int k = 0; k < N; k++) {
				int kk = k - N / 2;

				double temp1 = kk * L;
				double temp2 = 3.14 * WL * L * (jj * jj + kk * kk) / (D * D);
				double re = Math.cos(temp1) * Math.cos(temp2) + Math.sin(temp1)
						* Math.sin(temp2);
				double im = -Math.cos(temp1) * Math.sin(temp2)
						+ Math.sin(temp1) * Math.cos(temp2);
				double temp = data.get(n - 1);
				data.set(n - 1, data.get(n - 1) * re - data.get(n) * im);
				data.set(n, temp * im + data.get(n) * re);
				n += 2;
			}
		}

		// }
		// else{
		/*
		 * //传递函数抽样 for(int j = 0, n = 1; j < N; j++){ int jj=j-N/2;
		 * 
		 * for(int k = 0; k < N; k++){ int kk=k-N/2;
		 * 
		 * double temp = 2*3.14*L*Math.sqrt(1/WL/WL - (jj*jj+kk*kk)/(D*D));
		 * double re = Math.cos(temp) ; double im = Math.sin(temp); double t =
		 * data.get(n-1); data.set(n-1, data.get(n-1) * re - data.get(n) * im);
		 * data.set(n, t * im + data.get(n) * re); n += 2; } }
		 */
		// }

		fourn(data, nn, -1);

		ArrayList<Integer> E = new ArrayList<Integer>();
		ArrayList<Double> EA = new ArrayList<Double>();
		double max = 0;
		for (int i = 1; i < data.size(); i += 2) {
			double t = Math.sqrt(Math.pow(data.get(i), 2)
					+ Math.pow(data.get(i - 1), 2));

			if (max < t) {
				max = t;
			}
			EA.add(t);
		}

		// 除以最大值归一化，然后转化到RGB的颜色范围
		for (int i = 0; i < EA.size(); i++) {
			int t = (int) (EA.get(i) / max * 255);
			E.add(t);
		}

		return E;
	}

	void fourn(ArrayList<Double> data, int nn[], int isign) {
		int idim;
		int i1, i2, i3, i2rev, i3rev, ip1, ip2, ip3, ifp1, ifp2;
		int ibit, k1, k2, n, nprev, nrem, ntot;
		double tempi, tempr;
		double theta, wi, wpi, wpr, wr, wtemp;

		int ndim = nn.length;
		ntot = data.size() / 2;
		nprev = 1;
		for (idim = ndim - 1; idim >= 0; idim--) {
			n = nn[idim];
			nrem = ntot / (n * nprev);
			ip1 = nprev << 1;
			ip2 = ip1 * n;
			ip3 = ip2 * nrem;
			i2rev = 1;
			for (i2 = 1; i2 <= ip2; i2 += ip1) {
				if (i2 < i2rev) {
					for (i1 = i2; i1 <= i2 + ip1 - 2; i1 += 2) {
						for (i3 = i1; i3 <= ip3; i3 += ip2) {
							i3rev = i2rev + i3 - i2;
							// SWAP(data[i3],data[i3rev]);
							double temp;
							temp = data.get(i3 - 1);
							data.set(i3 - 1, data.get(i3rev - 1));
							data.set(i3rev - 1, temp);
							// SWAP(data[i3+1],data[i3rev+1]);
							temp = data.get(i3);
							data.set(i3, data.get(i3rev));
							data.set(i3rev, temp);
						}
					}
				}
				ibit = ip2 >> 1;
				while (ibit >= ip1 && i2rev > ibit) {
					i2rev -= ibit;
					ibit >>= 1;
				}
				i2rev += ibit;
			}
			ifp1 = ip1;
			while (ifp1 < ip2) {
				ifp2 = ifp1 << 1;
				theta = isign * 6.28318530717959 / (ifp2 / ip1);
				wtemp = Math.sin(0.5 * theta);
				wpr = -2.0 * wtemp * wtemp;
				wpi = Math.sin(theta);
				wr = 1.0;
				wi = 0.0;
				for (i3 = 1; i3 <= ifp1; i3 += ip1) {
					for (i1 = i3; i1 <= i3 + ip1 - 2; i1 += 2) {
						for (i2 = i1; i2 <= ip3; i2 += ifp2) {
							k1 = i2;
							k2 = k1 + ifp1;

							tempr = wr * data.get(k2 - 1) - wi * data.get(k2);
							tempi = wr * data.get(k2) + wi * data.get(k2 - 1);
							data.set(k2 - 1, data.get(k1 - 1) - tempr);
							data.set(k2, data.get(k1) - tempi);
							data.set(k1 - 1, data.get(k1 - 1) + tempr);
							data.set(k1, data.get(k1) + tempi);
						}
					}
					wr = (wtemp = wr) * wpr - wi * wpi + wr;
					wi = wi * wpr + wtemp * wpi + wi;
				}
				ifp1 = ifp2;
			}
			nprev *= n;
		}
	}

	public ArrayList<Double> swap(ArrayList<Double> T, int N) {
		for (int i = 0; i < N / 2; i++) {
			for (int j = 0; j < N / 2; j++) {
				double t = 0;

				t = T.get((i) * N * 2 + (j) * 2);
				T.set((i) * N * 2 + (j) * 2,
						T.get((i + N / 2) * N * 2 + (j + N / 2) * 2));
				T.set((i + N / 2) * N * 2 + (j + N / 2) * 2, t);

				t = T.get((i) * N * 2 + (j) * 2 + 1);
				T.set((i) * N * 2 + (j) * 2 + 1,
						T.get((i + N / 2) * N * 2 + (j + N / 2) * 2 + 1));
				T.set((i + N / 2) * N * 2 + (j + N / 2) * 2 + 1, t);

				t = T.get((i + N / 2) * N * 2 + (j) * 2);
				T.set((i + N / 2) * N * 2 + (j) * 2,
						T.get((i) * N * 2 + (j + N / 2) * 2));
				T.set((i) * N * 2 + (j + N / 2) * 2, t);

				t = T.get((i + N / 2) * N * 2 + (j) * 2 + 1);
				T.set((i + N / 2) * N * 2 + (j) * 2 + 1,
						T.get((i) * N * 2 + (j + N / 2) * 2 + 1));
				T.set((i) * N * 2 + (j + N / 2) * 2 + 1, t);
			}
		}
		return T;
	}
}
