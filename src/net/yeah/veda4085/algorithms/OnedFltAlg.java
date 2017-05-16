package net.yeah.veda4085.algorithms;

import java.lang.Math;
import net.yeah.veda4085.algorithms.Complex;

public class OnedFltAlg{
	double a = 0.3*100;
	double d = 1*100;
	double L = 10*100;
	int type = 1;
	int level = 0;
	public int[] f;
	public Complex[] g;
	public int[] H;
	final int points= 32768;
	final int mid = (points)/2;
	final int extp = 32768;
	final int midp = extp/2;
	public Complex[] interval = new Complex[extp];
	public Complex[] G = new Complex[extp];
	final double xs = 3.3*10000;
	int[] clevel = new int[15];
	int sorc;
	
	public OnedFltAlg(double a, double d, double L,int type, int level,int flag,int[] clevel){
		this.a = a*100;this.d = d*100;this.L = L*100;this.type = type;this.level = level;
		f = new int[points];
		H = new int[extp];
        g = new Complex[extp];
		sorc =flag;
		for(int i=0;i<15;i++){
			this.clevel[i]= clevel[i];
		}
		
		//create f;
		createf();
		
		getfft();
		
		createflt();
		for(int i = 0; i < extp; i++){
			Complex c = new Complex(0,0);
			G[i]=c;
		    G[i]=interval[i].times(H[i]);
			
		}
		g=ifft(G);
		for(int i = 0; i < points; i++){
			if(i%2!=0){
				double c = g[i].getR();
				g[i].setR(-c);
			}
		}
		
	}
	
	public void createflt(){
		double k = xs/(d);
		double xishu=1.7;
		
		if(sorc==1){
		int local = level * (int)k;
		for(int i = 0; i < extp; i++){
			if(type ==1 || type==3) {H[i] = 0;}
			else{H[i]= 1;}
		}
		if(type == 1){  //only pass level
			for(int i = 0; i < xishu*d; i++){
				H[midp+local-i] = 1;
				H[midp+local+i] = 1;
				H[midp-local-i] = 1;
				H[midp-local+i] = 1;
			}
		}
		else if(type == 2){ //not pass level only
			for(int i = 0; i < xishu*d; i++){
				H[midp+local-i] = 0;
				H[midp+local+i] = 0;
				H[midp-local-i] = 0;
				H[midp-local+i] = 0;
			}
		}
		else if(type == 3){ //pass under level
			for(int i = midp-local-(int)(xishu*d);i <= midp+local+(int)(xishu*d); i++){
				H[i]=1;
			}
		}
		else{ //type ==4 not pass under level
			for(int i = midp-local-(int)(xishu*d);i <= midp+local+(int)(xishu*d); i++){
				H[i]=0;
			}
		}
		}
		else{
			int[] local = new int[15];
			for(int i=0;i<15;i++){local[i]=(i-7)*(int)k;}
			for(int i = 0; i < extp; i++){
				if(type ==1 || type==3) {H[i] = 0;}
				else{H[i]= 1;}
			}
			
			if(type == 1){//only pass level
				for(int j=0;j<15;j++){
					if(clevel[j]==1){
				for(int i = 0; i < xishu*d; i++){
					H[midp+local[j]-i] = 1;
					H[midp+local[j]+i] = 1;
					
				}}}
			}
			else if(type == 2){ //not pass level only
				for(int j = 0;j<15;j++){
				  if(clevel[j]==1){
				for(int i = 0; i < xishu*d; i++){
					H[midp+local[j]-i] = 0;
					H[midp+local[j]+i] = 0;
					
				}}
			}}
			
		}
		
	}
	
	public void createf(){
		for(int i = 0; i < points; i++){
		    f[i] = 0;
	   }
		for(int i = 0; ((i <= L/2) && (i < points/2-1)); i++){
			int rest = i%(int)d;
			int k = i/(int)d;
			if(rest<=a/2) {
				f[mid+k*(int)d+rest-1]=1;
				f[mid-(k*(int)d+rest)+1]=1;
				if((k+1)*(int)d-rest<=L/2){
					f[mid+((k+1)*(int)d-rest)-1]=1;
					f[mid-((k+1)*(int)d-rest)+1]=1;
				}
				
			}
		}
	}
		
	public void getfft(){
		//extend the f;
		Complex[] extendf = new Complex[32768];
		for(int i = 0; i < 32768; i++){
			if(i < points){
				if(i%2==0){
					Complex c = new Complex(f[i],0);
					extendf[i]=c;
				}
				else {
					Complex c = new Complex(-f[i],0);
					extendf[i]=c;
				}
			}
			else {
				Complex c = new Complex(0,0);
				extendf[i]=c;
			}
		}
		
		interval=fft(extendf);
		
	}
	
    public static Complex[] fft(Complex[] x){
    	int N = x.length;
    	if(N==1){
    		return x;
    	}
    	
    	Complex[] even = new Complex[N/2];
    	  for(int k = 0;k < N/2; k++){
    		  even[k] = x[2*k];
    	  }
    	 Complex[] q = fft(even);
    	 
    	 Complex[] odd = new Complex[N/2];
    	 for(int k = 0; k < N/2; k++){
    		 odd[k] = x[2*k+1];
    	 }
    	 Complex[] r = fft(odd);
    		 
    	Complex[] s = new Complex[N];
    	for(int k = 0; k < N/2; k++){
    		double kth = -2 * k * Math.PI / N;
    		Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
    		s[k] = q[k].plus(wk.times(r[k]));
    		s[k + N/2] = q[k].minus(wk.times(r[k])); 
    	}
    	return s;
    }

    public static Complex[] ifft(Complex[] x){
    	int N = x.length;
    	Complex[] y = new Complex[N];
    	
    	for(int k = 0; k < N;k++){
    		y[k] = x[k].conjugate();
    	}
    	
    	y = fft(y);
    	
    	for(int i = 0; i < N; i++){
    		y[i] = y[i].conjugate();
    		y[i] = y[i].times(1.0/N);
    	}
    	
    	
    	return y;	
    }
    
}
