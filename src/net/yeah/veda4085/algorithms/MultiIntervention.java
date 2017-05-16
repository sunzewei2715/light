package net.yeah.veda4085.algorithms;

import java.util.ArrayList;

public class MultiIntervention {
     double WL;
     double n1;
     double n2;
     double r;
     double t;
     double j = 5100;
     
     public MultiIntervention(double wl, double N1, double N2, double R, double T){
    	 WL = wl/1000.0;
    	 n1 = N1;
    	 n2 = N2;
    	 r = R;
    	 t = T;
     }
     
     private double relateLightIntensity1(double Y){
	    	 double y = Y, T = t * 1000.0, F, s, delta, cos, RLI1;
	    	 s = Math.hypot(y, j);
	    	 cos = Math.sqrt(1 - Math.pow(n1*y/(n2*s), 2));
	    	 delta = 4 * Math.PI * n2 * T * cos/WL;
	    	 F = 4 * Math.pow(r, 2)/Math.pow(1-Math.pow(r, 2), 2);
	    	 RLI1 = 1/(1 + F * Math.pow(Math.sin(delta/2), 2));
	    	 return RLI1;
     }

     private double relateLightIntensity2(double Y){
    	 	return 1-relateLightIntensity1(Y);
     }


     public ArrayList<Double> allIntensity1(int size){
    	 ArrayList<Double> A = new ArrayList<Double>();	
 		 double temp = 0;
 		
 		for(int i = 0 ; i < size ; i++){                   
			temp = relateLightIntensity1(i);			
			A.add(temp);
		}
		
		return A;
     }

     public ArrayList<Double> allIntensity2(int size){
    	 ArrayList<Double> B = new ArrayList<Double>();	
 		 double temp = 0;
 		
 		for(int i = 0 ; i < size ; i++){                   
			temp = relateLightIntensity2(i);			
	        B.add(temp);
		}
		
		return B;
     }
     
     }