package net.yeah.veda4085.algorithms;

import java.awt.Color;
import java.util.ArrayList;

import net.yeah.veda4085.view.InitColor;

public class McAlg {
	double L;          //M1到M2的距离
	double WL;          //波长waveLength
	double sd = 5;     //光源到中心玻璃板D的距离,单位1MM
	double dm1 = 5;    //中心玻璃板到平面镜M1的距离,单位1MM
	double  dp= 40;       //中心玻璃板到接收屏的距离,单位1MM
	int timePre;
	
//	double I = 0.5;  //光强 I1 = I2 =0.5
	
	public McAlg(double L, double WL, int Pre){
		this.L = L;        //单位转化为1UM                        
		this.WL = WL * Math.pow(10, -3);         //单位转化为1UM
		sd = sd*Math.pow(10, 3);			 //单位转化为1UM  
		dm1 = dm1*Math.pow(10, 3);				 //单位转化为1UM  
		dp = dp*Math.pow(10, 3);				 //单位转化为1UM  
		this.timePre=Pre;
	}

	private double relateLightIntensity(double Y){                       //计算屏上各点的光强
	        
		double y = Y; // 屏上一点到屏中心距离,即半径,单位1UM
		double OPD; // 光程差opticalPathDifference
		double PD; // 相位差phaseDifference
		double RLI; // 相对光强 relateLightIntensity

		OPD = Math.hypot(sd+2*dm1+dp+2*L,y)-Math.hypot(sd+2*dm1+dp,y); 
		
		PD = 2 * Math.PI * OPD / WL;
		
		RLI = 2 + 2*Math.cos(PD);  
		
		return RLI;
	}
	
	public ArrayList<Double> allIntensity(int size){
		ArrayList<Double> A = new ArrayList<Double>();	
		double temp = 0;
		
		for(int i = 0 ; i < size ; i++){                   
			temp = relateLightIntensity(i * 20);			//增量为20UM
			A.add(temp);
		}
		
		return A;
	}
	public ArrayList<Color> getColor(int length){
		int step=20;  //增量为10um
        double lightDiff,det;
        int wavelen=575;
        Color temp;
        double detWavLen=wavelen*0.339;
        int red,g,bl;
        ArrayList<Color> result=new ArrayList<Color>();
        for(int i=0;i<length;i++){
        	int y=i*step;
        	lightDiff=Math.hypot(sd+2*dm1+dp+2*L,y)-Math.hypot(sd+2*dm1+dp,y);
        	red=0;g=0;bl=0;
        	for (int j=0;j<timePre;j++){
                det=2*Math.PI*lightDiff*1000/(wavelen-detWavLen+j*2*detWavLen/timePre);//此处计算所用单位为1nm；
                temp = new InitColor().WavlenChangetoRGB((int)(wavelen
                		-detWavLen+j*2*detWavLen/timePre),(float)(2+2*Math.cos(det))/4);
                red+=temp.getRed();
                g+=temp.getGreen();
                bl+=temp.getBlue();
            }
            temp= new Color((int)(red*1.0*1.25/timePre),(int)(g*1.0*1.25/timePre),(int)(bl*1.0*1.25/timePre))
            			.brighter();
            result.add(temp);
        }
        return result;
    }
	 public ArrayList<Double> getwhite(int length){
		ArrayList<Color> c = getColor(length);
		ArrayList<Double> result = new ArrayList<Double>();
		float temp[]={0,0,0};
		for(int i=0;i<length;i++){
			temp = Color.RGBtoHSB(c.get(i).getRed(), c.get(i).getGreen(), c.get(i).getBlue(), temp);
			result.add((double)temp[2]);
		}
		return result;
	 }
}
