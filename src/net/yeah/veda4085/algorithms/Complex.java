package net.yeah.veda4085.algorithms;

import java.lang.Math;

public class Complex {
   private double r;
   private double i;
   
   public Complex(double r,double i){
	   this.r = r;
	   this.i = i;
   }
   
   public void setR(double r){
	   this.r = r;
   }
   
   public void setI(double i){
	   this.i = i;
   }
   
   public double abs(){
	   return Math.sqrt(r*r+i*i);
   }
   
   public double phase(){
	   return Math.atan2(i, r);
   }
   
   public Complex plus(Complex c){
	   return new Complex(this.r + c.r, this.i + c.i);
   }
   
   public Complex minus(Complex c){
	   return new Complex(this.r - c.r, this.i - c.i);
   }
   
   public Complex times(Complex c){
	   return new Complex(this.r * c.r - this.i * c.i, this.r * c.i +this.i * c.r);
   }
   
   public Complex times(double c){
	   return new Complex(this.r * c, this.i * c);
   }
   
   public Complex conjugate() {
	   return new Complex(r, -i);
   }
   
   public double getR(){
	   return r;
   }
   
   public double getI(){
	    return i;
   }
   
   
}
