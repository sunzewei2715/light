package net.yeah.veda4085.algorithms;

import java.lang.Math;
import net.yeah.veda4085.algorithms.Complex;
import java.awt.image.BufferedImage;

public class genrealFA {
      public int m,n,w;
     double[][] fftimgr;
      double[][] fftimgg;
      double[][] fftimgb;
     
     public Complex[][] interval;
      public Complex[][] intervalg;
      public Complex[][] intervalb;
	
	public genrealFA(BufferedImage img){
		m = img.getWidth();
    	n = img.getHeight();
    	 w = become2power(m>n? m:n)*1;
    	 
    	 fftimgr = new double[w][w];
    	 fftimgg = new double[w][w];
    	 fftimgb = new double[w][w];
         interval = new Complex[w][w];
         intervalg = new Complex[w][w];
         intervalb = new Complex[w][w];
         
         int pixel,clr,clg,clb;
         //process the input img
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j < w; j++){
    			if(i < m && j < n){
    				pixel = img.getRGB(i, j);
    		    	if((i+j)%2==0){
    					clr = (pixel&0x00ff0000)>>16;
    			        clg = (pixel&0x0000ff00)>>8;
    		            clb = (pixel&0x000000ff); 
    					}
    				else{
    					clr =-((pixel&0x00ff0000)>>16);
    					clg =-((pixel&0x0000ff00)>>8);  
    		            clb =-((pixel&0x000000ff));  
    				}
    				fftimgr[i][j] = clr;
    				fftimgg[i][j] = clg;
    				fftimgb[i][j] = clb;
    			
    			}
    			else{
    				fftimgr[i][j] = 0;
    				fftimgg[i][j] = 0;
    				fftimgb[i][j] = 0;
    			}		
    	     }
    	 }
    	 
    	 
    	 Complex[] temp1 = new Complex[w];
    	 Complex[] temp1g = new Complex[w];
    	 Complex[] temp1b = new Complex[w];
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j < w; j++){
    			 Complex c;
    			c = new Complex(fftimgr[i][j],0);
    			 temp1[j] = c;
    			 Complex d;
    			d = new Complex(fftimgg[i][j],0);
    			 temp1g[j] = d;
    			 Complex e;
    			e = new Complex(fftimgb[i][j],0);
    			 temp1b[j] = e;
    		 }
    		 interval[i] = fft(temp1);
    		 intervalg[i] = fft(temp1g);
    		 intervalb[i] = fft(temp1b);
    	 }
    	 
    	 Complex[] temp2 = new Complex[w];
    	 Complex[] temp2g = new Complex[w];
    	 Complex[] temp2b = new Complex[w];
    	 for(int j = 0; j < w; j++){
    		 for(int i = 0; i < w; i++){
    			 
    			 Complex c = interval[i][j];
    			 temp2[i] = c;
    			 
    			 Complex d = intervalg[i][j];
    			 temp2g[i] = d;
    			
    			 Complex e = intervalb[i][j];
    			 temp2b[i] = e;
    			
    		 }
    		 temp2 = fft(temp2);
    		 temp2g = fft(temp2g);
    		 temp2b = fft(temp2b);
    		 
    		 for(int k = 0; k < w; k++){
    			 interval[k][j] = temp2[k];
    			 intervalg[k][j] = temp2g[k];
    			 intervalb[k][j] = temp2b[k];
    		 }
    	 }
	}
	
	
	public static int become2power(int a){
        if(a == 1) return 1;
        int g = 1;
        while(true){
        	if(a > g && a <= 2 * g)
        		return 2*g;
        	else
        		g*=2;
        }
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
}
