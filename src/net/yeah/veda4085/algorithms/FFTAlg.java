package net.yeah.veda4085.algorithms;

import java.lang.Math;
import net.yeah.veda4085.algorithms.Complex;
import java.awt.Image;
import java.awt.image.BufferedImage;


public class FFTAlg {
//	public static int black =0xFF000000;
	int w; //the width of fixed input imag
	int phstype;
	int flag;
	Complex[][] flt;//filter matrix
    int TYPE_flt=1; //the type of filter(default highpass)
    double[][] fftimgr;
    double[][] fftimgg;
    double[][] fftimgb;
   
    double max=50;
    double min=0;
    int m,n,pixel,clr,clg,clb;
    Complex[][] interval;
    Complex[][] intervalg;
    Complex[][] intervalb;
    int clr_max=255;int clg_max=255;int clb_max=255; 
    
    public FFTAlg(BufferedImage img,int type,double min,double max,int phstype){
    	m = img.getWidth();
    	n = img.getHeight();
    	
    	TYPE_flt = type;
    	this.min = min;
    	this.max = max;
    	this.phstype= phstype;
    	
    	 w = become2power(m>n? m:n);
    	 
    	
    	 
    	 flt = new Complex[w][w];
    	 for (int i = 0;i < w;i++){
    		 for(int j = 0; j < w; j++){
    			 flt[i][j]=new Complex(0,0);
    		 }
    	 }
    	 fftimgr = new double[w][w];
    	 fftimgg = new double[w][w];
    	 fftimgb = new double[w][w];
         interval = new Complex[w][w];
         intervalg = new Complex[w][w];
         intervalb = new Complex[w][w];
         
         
         if(type!=5){
         crtflt(w,TYPE_flt,this.min,this.max);
         
         //process the input img
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j < w; j++){
    			if(i < m && j < n){
    				pixel = img.getRGB(i, j);
    		    	if((i+j)%2==0){
    					clr = (pixel&0x00ff0000)>>16; if(clr>clr_max) clr_max=clr;
    			        clg = (pixel&0x0000ff00)>>8;  if(clg>clg_max) clg_max=clg;
    		            clb = (pixel&0x000000ff);     if(clb>clb_max) clb_max=clb;
    					}
    				else{
    					clr =-((pixel&0x00ff0000)>>16);  if(-clr>clr_max) clr_max=clr;
    					clg =-((pixel&0x0000ff00)>>8);   if(-clg>clg_max) clg_max=clg;
    		            clb =-((pixel&0x000000ff));      if(-clb>clb_max) clb_max=clb;
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
    	 
    	 double[][][] eimgr=new double[w][w][2];
    	 double[][][] eimgg=new double[w][w][2];
    	 double[][][] eimgb=new double[w][w][2];
    	 if(type==5){
    		 for(int i = 0; i < w; i++){
    			 for(int j = 0; j < w; j++){
    				 double a =(double)fftimgr[i][j]*3.1415926/(double)clr_max;
    				 eimgr[i][j][0]=Math.cos(a);eimgr[i][j][1]=Math.sin(a);
    				 double b =(double)fftimgg[i][j]*3.1415926/(double)clg_max;
    				 eimgg[i][j][0]=Math.cos(b);eimgg[i][j][1]=Math.sin(b);
    				 double c =(double)fftimgb[i][j]*3.1415926/(double)clb_max;
    				 eimgb[i][j][0]=Math.cos(c);eimgb[i][j][1]=Math.sin(c);
    				 
    			 }
    		 }
    	 }
    	 
    	 
    	 
    	// System.out.println(fftimgr[30][30]+"  "+fftimgg[30][30]+"  "+fftimgb[30][30]);
    	 
    	 //fft(img)
    	 Complex[] temp1 = new Complex[w];
    	 Complex[] temp1g = new Complex[w];
    	 Complex[] temp1b = new Complex[w];
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j < w; j++){
    			 Complex c;
    			if(type!=5) {c = new Complex(fftimgr[i][j],0);}
    			else { c = new Complex(eimgr[i][j][0],eimgr[i][j][1]);}
    			 temp1[j] = c;
    			 Complex d;
    			if(type!=5) {d = new Complex(fftimgg[i][j],0);}
    			else { d = new Complex(eimgg[i][j][0],eimgg[i][j][1]);}
    			 temp1g[j] = d;
    			 Complex e;
    			 if(type!=5) {e = new Complex(fftimgb[i][j],0);}
    			 else {  e = new Complex(eimgb[i][j][0],eimgb[i][j][1]);}
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
    	 
    //	 System.out.println(interval[30][30].getR()+"  "+intervalg[30][30].getR()+"  "+intervalb[30][30].getR());
    	 
    	 
    	 //combine the ffted img and the filter
    	 Complex[][] g = new Complex[w][w];
    	 Complex[][] gg = new Complex[w][w];
    	 Complex[][] gb = new Complex[w][w];
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j < w; j++){
    			 g[i][j] = interval[i][j].times(flt[i][j]);
    			 gg[i][j] = intervalg[i][j].times(flt[i][j]);
    			 gb[i][j] = intervalb[i][j].times(flt[i][j]);
    			 
    		 }
    	 }
    	 
    	 
    	 //ifft the combination
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j < w; j++){
    			 Complex c = new Complex(g[i][j].getR(), g[i][j].getI());
    			 temp1[j] = c;
    			 c=gg[i][j];
    			 temp1g[j]=c;
    			 c=gb[i][j];
    			 temp1b[j]=c;
    		 }
    		 g[i] = ifft(temp1);
    		 gg[i] =ifft(temp1g);
    		 gb[i] = ifft(temp1b);
    	 }
    
    	 for(int j = 0; j < w; j++){
    		 for(int i = 0; i < w; i++){
    			 Complex c = g[i][j];
    			 temp2[i]= c;
    			 c=gg[i][j];
    			 temp2g[i]=c;
    			 c=gb[i][j];
    			 temp2b[i]=c;
    		 }
    		 temp2 = ifft(temp2);
    		 temp2g =ifft(temp2g);
    		 temp2b = ifft(temp2b);
    		 for(int k = 0; k < w; k++){
    			 g[k][j] = temp2[k];
    			 gg[k][j] = temp2g[k];
    			 gb[k][j] = temp2b[k];
    		 }
    	 }
    	// double imgmax=255;
    	// double imgmin=0;
    	 //getR
    	if(type!=5){
    		for(int i = 0; i < w; i++){
    	
    		 for(int j = 0; j <w; j++){
    			 fftimgr[i][j] = (g[i][j].getR());
    			 fftimgg[i][j] = (gg[i][j].getR());
    			 fftimgb[i][j] = (gb[i][j].getR());
    		//	 if(TYPE_flt == 5 && fftimg[i][j]>=95 && fftimg[i][j]<215) {fftimg[i][j]+=40;}
    		//	 if(TYPE_flt == 5 && fftimg[i][j]<=45 && fftimg[i][j]>25) {fftimg[i][j]-=25;}
    		//	 if (fftimg[i][j]>=imgmax ) { imgmax=fftimg[i][j];}
    		//	 if (fftimg[i][j]<0) { imgmin=fftimg[i][j];}
    		 }
    	 }
    	}else if(type==5){
    	//	double rmax=1;double gmax=1;double bmax=1;
    	//	for(int i = 0; i < w; i++){
    	//		for(int j = 0; j <w; j++){
    	//			if(rmax<g[i][j].getR()) rmax=g[i][j].getR();
    	//			if(gmax<gg[i][j].getR()) gmax=gg[i][j].getR();
    	//			if(bmax<gb[i][j].getR()) bmax=gb[i][j].getR();
    	//		}
    	//	}
    		for(int i = 0; i < w; i++){
    			for(int j = 0; j < w; j++){
    				if(phstype==1){
    				fftimgr[i][j]=((g[i][j].getR()*g[i][j].getR())+(g[i][j].getI()*g[i][j].getI()))*230;
    				fftimgg[i][j]=((gg[i][j].getR()*gg[i][j].getR())+(gg[i][j].getI()*gg[i][j].getI()))*230;
    				fftimgb[i][j]=((gb[i][j].getR()*gb[i][j].getR())+(gb[i][j].getI()*gb[i][j].getI()))*230;
    				}
    				else{
    					fftimgr[i][j]=230;
        				fftimgg[i][j]=230;
        				fftimgb[i][j]=230;
    				}
    				
    			}
    		}
    	//	System.out.println("a"+fftimgr[100][100]+"  "+fftimgg[100][100]+"  "+fftimgb[100][100]);
    	//	System.out.println("b"+fftimgr[95][95]+"  "+fftimgg[95][95]+"  "+fftimgb[95][95]);
    	}
         }
         else{
        	 for(int i = 0; i < m; i++){
        		 for(int j = 0; j < n; j++){
        			
        				pixel = img.getRGB(i, j);
        		    	
        				fftimgr[i][j] = (pixel&0x00ff0000)>>16; 
        		       fftimgg[i][j] = (pixel&0x0000ff00)>>8;  
    			       fftimgb[i][j]  = (pixel&0x000000ff);   
        		            
        			
        			}		
        	     }
        	 
           
        //	System.out.println(phstype);
        	 
        	 for(int i = 0; i < w; i++){
        		 for(int j = 0; j < w; j++){
        			  if(phstype==1){
        		     fftimgr[i][j] = 220-(fftimgr[i][j]*220/255)*2/3;
        			 fftimgg[i][j] = 220-(fftimgg[i][j]*220/255)*2/3;
        			 fftimgb[i][j] = 220-(fftimgb[i][j]*220/255)*2/3;
        			
        			  
        			  }
        			  else if(phstype==2){
             		     fftimgr[i][j] = 190-(fftimgr[i][j]*190/255)*2/3;
            			 fftimgg[i][j] = 190-(fftimgg[i][j]*190/255)*2/3;
            			 fftimgb[i][j] = 190-(fftimgb[i][j]*190/255)*2/3;
            			
            			  }
        			  else if(phstype==3){
              		     fftimgr[i][j] = 160-(fftimgr[i][j]*160/255)*2/3;
             			 fftimgg[i][j] = 160-(fftimgg[i][j]*160/255)*2/3;
             			 fftimgb[i][j] = 160-(fftimgb[i][j]*160/255)*2/3;
             			
             			  }
        			  else if(phstype==4){
               		     fftimgr[i][j] = 130-(fftimgr[i][j]*130/255)*2/3;
              			 fftimgg[i][j] = 130-(fftimgg[i][j]*130/255)*2/3;
              			 fftimgb[i][j] = 130-(fftimgb[i][j]*130/255)*2/3;
              			
              			  }
        			 else{
        		 fftimgr[i][j] = 220;
    			 fftimgg[i][j] = 220;
    			 fftimgb[i][j] = 220;
        	 }
        		 
        		 }}}
        	
        	 
         
    	 
    	/* if(imgmin<0){
    	 for(int i = 0; i < w; i++){
    		 for(int j = 0; j <w; j++){
    			 
    			 fftimg[i][j]=(fftimg[i][j]-imgmin)/(imgmax-imgmin)*255;
    		 }
    	 }
    	 }*/
    	 
    			
    	 
    	 
    	 
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
   
    public void crtflt(int w,int type, double min, double max){
    	double mid = ((double)(w-1)/2.0);
    	
    	double minc,maxc;
    	if(type!=5){
    	if(type < 4){
    	if(type==1){  //highpass
    		minc = min; maxc = 1.5 * w;
    		
    	}
    	else if(type==0||type==2){ //bandpass or bandstop
    		minc = min; maxc = max;
    	}
    	else{  //lowpass
    		minc = 0; maxc = max;
    	}
    	
    	for(int i = 0; i < w; i++){
    		for(int j = 0; j < w; j++){
    			double l = ((double)i-mid)*((double)i-mid)+((double)j-mid)*((double)j-mid);
    			if(l >= minc*minc && l <= maxc*maxc){
    				if(type==2)  {flt[i][j].setR(0);}
    				else  {flt[i][j].setR(1);}
    			}
    			else{
    				if(type==2)  {flt[i][j].setR(1);}
    				else  {flt[i][j].setR(0);}
    			}
    		}
    	}
    	
    	}
    	else if(type >7){
    		if(type == 11){
    			int md =(int)mid;
    			double n = min;
    			minc= maxc=0;
    			double rad = n*3.14159265358979/180;
    			double width2 = (max+1)/2;
    			double t = Math.tan(rad);
    			double s = Math.sin(rad);
    			double c = Math.cos(rad);
    			if(n<90 && n >0){
    			
    			for(int i = 0; i < w; i++){
    				for(int j = 0; j < w; j++){
    					int k =w-1-j;
    					if(i>=md && k >=md) {
    						int x1 = i - md;int y = k - md;
    						if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
    							flt[i][j].setR(0);
    							flt[w-1-i][k].setR(0);
    						}
    						else {flt[i][j].setR(1);flt[w-1-i][k].setR(1);}
    						}
    					else if(i < md && k >= md){
    						int x1 = md -i;int y = k-md;
    						if(y<=-t*x1+width2/c){
    							flt[i][j].setR(0);
    							flt[w-1-i][k].setR(0);
    						}
    						else {flt[i][j].setR(1);flt[w-1-i][k].setR(1);}
    					}
    					
    					
    				}
    			}}
    			else if(n==90){
    				for(int i = 0; i < w; i++){
    					for(int j = 0; j < w; j++){
    					if(Math.abs(i-md)<width2){
    						flt[i][j].setR(0);
    					}
    					else { flt[i][j].setR(1);}
    					}
    				}
    				
    			}
    			else if(n==0||n==180){
    				for(int i = 0; i < w; i++){
    					for(int j = 0; j < w; j++){
    					if(Math.abs(j-md)<width2){
    						flt[i][j].setR(0);
    					}
    					else { flt[i][j].setR(1);}
    					}
    				}
    				
    			}
    			else if(n>90 && n <180){
    				t = Math.tan(3.14159265358979-rad);
    				s = Math.sin(3.14159265358979-rad);
    				c = Math.cos(3.14159265358979-rad);
    				
    				for(int i = 0; i < w; i++){
    					for(int j = 0; j < w; j++){
    						int k =w-1-j;
    						if(i>=md && k >=md) {
    							int x1 = i - md;int y = k - md;
    							if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
    								flt[w-1-i][j].setR(0);
    								flt[i][k].setR(0);
    							}
    							else {flt[w-1-i][j].setR(1);flt[i][k].setR(1);}
    							}
    						else if(i < md && k >= md){
    							int x1 = md -i;int y = k-md;
    							if(y<=-t*x1+width2/c){
    								flt[w-1-i][j].setR(0);
    								flt[i][k].setR(0);
    							}
    							else {flt[w-1-i][j].setR(1);flt[i][k].setR(1);}
    						}
    						
    						
    					}
    				}
    			}
    			
    		}
    		else if(type == 9){
    			
    			int md =(int)mid;
    			double n = min;
    			minc= maxc=0;
    			double rad = n*3.14159265358979/180;
    			double width2 = (max+1)/2;
    			double t = Math.tan(rad);
    			double s = Math.sin(rad);
    			double c = Math.cos(rad);
    			if(n<90 && n >0){
    			
    			for(int i = 0; i < w; i++){
    				for(int j = 0; j < w; j++){
    					int k =w-1-j;
    					if(i>=md && k >=md) {
    						int x1 = i - md;int y = k - md;
    						if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
    							flt[i][j].setR(1);
    							flt[w-1-i][k].setR(1);
    						}
    						else {flt[i][j].setR(0);flt[w-1-i][k].setR(0);}
    						}
    					else if(i < md && k >= md){
    						int x1 = md -i;int y = k-md;
    						if(y<=-t*x1+width2/c){
    							flt[i][j].setR(1);
    							flt[w-1-i][k].setR(1);
    						}
    						else {flt[i][j].setR(0);flt[w-1-i][k].setR(0);}
    					}
    					
    					
    				}
    			}}
    			else if(n==90){
    				for(int i = 0; i < w; i++){
    					for(int j = 0; j < w; j++){
    					if(Math.abs(i-md)<width2){
    						flt[i][j].setR(1);
    					}
    					else { flt[i][j].setR(0);}
    					}
    				}
    				
    			}
    			else if(n==0||n==180){
    				for(int i = 0; i < w; i++){
    					for(int j = 0; j < w; j++){
    					if(Math.abs(j-md)<width2){
    						flt[i][j].setR(1);
    					}
    					else { flt[i][j].setR(0);}
    					}
    				}
    				
    			}
    			else if(n>90 && n <180){
    				t = Math.tan(3.14159265358979-rad);
    				s = Math.sin(3.14159265358979-rad);
    				c = Math.cos(3.14159265358979-rad);
    				
    				for(int i = 0; i < w; i++){
    					for(int j = 0; j < w; j++){
    						int k =w-1-j;
    						if(i>=md && k >=md) {
    							int x1 = i - md;int y = k - md;
    							if(y>=t*(x1-width2/s) && y <=t*x1+width2/c) {
    								flt[w-1-i][j].setR(1);
    								flt[i][k].setR(1);
    							}
    							else {flt[w-1-i][j].setR(0);flt[i][k].setR(0);}
    							}
    						else if(i < md && k >= md){
    							int x1 = md -i;int y = k-md;
    							if(y<=-t*x1+width2/c){
    								flt[w-1-i][j].setR(1);
    								flt[i][k].setR(1);
    							}
    							else {flt[w-1-i][j].setR(0);flt[i][k].setR(0);}
    						}
    						
    						
    					}
    				}
    			}
    			
    		}
    	}
    	}
    	else if(type==5){
    	for(int i = 0; i < w; i++){
    		for(int j = 0; j < w; j++){
    			flt[i][j].setR(1);
    			flt[i][j].setI(0);
    		}
    	}
    if(phstype==1){
    	System.out.println("ÏàÎ»");
    	flt[(int)mid-1][(int)mid].setR(0);
    	flt[(int)mid-1][(int)mid].setI(1);
    	flt[(int)mid-1][(int)mid+1].setR(0);
    	flt[(int)mid-1][(int)mid+1].setI(1);
    	flt[(int)mid][(int)mid-1].setR(0);
    	flt[(int)mid][(int)mid-1].setI(1);
    	flt[(int)mid][(int)mid+2].setR(0);
    	flt[(int)mid][(int)mid+2].setI(1);
    	flt[(int)mid+1][(int)mid-1].setR(0);
    	flt[(int)mid+1][(int)mid-1].setI(1);
    	flt[(int)mid+1][(int)mid+2].setR(0);
    	flt[(int)mid+1][(int)mid+2].setI(1);
    	flt[(int)mid+2][(int)mid].setR(0);
    	flt[(int)mid+2][(int)mid].setI(1);
    	flt[(int)mid+2][(int)mid+1].setR(0);
    	flt[(int)mid+2][(int)mid+1].setI(1);
    	
    	
    	
     	flt[(int)mid][(int)mid].setR(0);
    	flt[(int)mid][(int)mid].setI(1);
    	flt[(int)mid+1][(int)mid].setR(0);
    	flt[(int)mid+1][(int)mid].setI(1);
    	flt[(int)mid][(int)mid+1].setR(0);
    	flt[(int)mid][(int)mid+1].setI(1);
    	flt[(int)mid+1][(int)mid+1].setR(0);
    	flt[(int)mid+1][(int)mid+1].setI(1);}
   
    	
    	}
    		
    	
    }
    
    public  BufferedImage outpt(){
    	int kuan,chang;
    	kuan = this.m;chang=this.n;

    	BufferedImage dest = new BufferedImage(kuan,chang,BufferedImage.TYPE_INT_ARGB);
    		int alpha, newred, newgreen, newblue, newrgb;
    	
    	
    	alpha = (-1) << 24;
    	for(int i = 0; i < this.m; i++){
    		for(int j = 0; j < this.n; j++){
    			newred = (int)this.fftimgr[i][j];
    			newgreen = (int)this.fftimgg[i][j];
    			newblue = (int)this.fftimgb[i][j];
    			if(this.TYPE_flt!=5){
    				if((i+j)%2!=0){
    			
    				newred = -newred;
    				newgreen = -newgreen;
    				newblue = - newblue;
    				
    				
    				
    			}}
    			
    	//		if(newred>255||newred<0) {System.out.println(newred);}
    	//		if(newgreen>255||newgreen<0) {System.out.println(newgreen);}
    	//		if(newblue>255||newblue<0) {System.out.println(newblue);}
    			
    			if(newred>255) newred=255;if(newred<0) newred=0;
    			if(newgreen>255) newgreen=255;if(newgreen<0) newgreen=0;
    			if(newblue>255) newblue=255; if(newblue<0) newblue=0;
    			
    			
    			
    			//	if(ct==50){ System.out.println(newred+"  "+newgreen+"  "+newblue);ct=0;}
    				
    			
    		//	newblue = newred;
    		//	newgreen = newred << 8;
    		//	newred =  newred << 16;
    			newgreen = newgreen<<8;
    			newred = newred<<16;
    			newrgb = alpha | newred | newgreen | newblue;
    		//	newrgb = alpha+(newred*256+newgreen)*256+newblue;
    			dest.setRGB(i, j, newrgb);
    			
    		}
    	}
    /*	else{
    		alpha = (-1) << 24;
        	for(int i = 0; i < w; i++){
        		for(int j = 0; j < w; j++){
        			newred = (int)(this.interval[i][j].getR());
        			newgreen = (int)(this.intervalg[i][j].getR());
        			newblue = (int)(this.intervalb[i][j].getR());
        			if((i+j)%2!=0){
        				newred = -newred;
        				newgreen = -newgreen;
        				newblue = - newblue;
        				
        				
        				
        			}
        			
        	//		if(newred>255||newred<0) {System.out.println(newred);}
        	//		if(newgreen>255||newgreen<0) {System.out.println(newgreen);}
        	//		if(newblue>255||newblue<0) {System.out.println(newblue);}
        			
        			if(newred>255) newred=255;if(newred<0) newred=0;
        			if(newgreen>255) newgreen=255;if(newgreen<0) newgreen=0;
        			if(newblue>255) newblue=255; if(newblue<0) newblue=0;
        			
        			
        			
        			//	if(ct==50){ System.out.println(newred+"  "+newgreen+"  "+newblue);ct=0;}
        				
        			
        		//	newblue = newred;
        		//	newgreen = newred << 8;
        		//	newred =  newred << 16;
        			newgreen = newgreen<<8;
        			newred = newred<<16;
        			newrgb = alpha | newred | newgreen | newblue;
        		//	newrgb = alpha+(newred*256+newgreen)*256+newblue;
        			dest.setRGB(i, j, newrgb);
        			
        		}
        	}
        	
    	}*/
    	
    	return dest;
    }
    
}
 

