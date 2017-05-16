package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.image.BufferedImage;
import java.lang.Math;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import net.yeah.veda4085.algorithms.Complex;
import net.yeah.veda4085.algorithms.OnedFltAlg;





public class OnedFlt {
    double a = 0.7;
    double d = 0.9;
    double L =26;
	int type = 1;
	int level =1;
	int sorc=1;
	int[] fp = new int[32768];
	double[] Fp = new double[32768];
	int[] Hp = new int[32768];
	double[] Gp = new double[32768];
	double[] fGp = new double[32768];
	int[] clevel = new int[]{0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0};
	

	
	public JPanel centerPanel,rightPanel;
	public JLabel fourf;
	public JLabel flb,glb;
	public JLabel shuruguangshan,shuchutuxiang;
	public JPanel imgjp;
	public JPanel parajp,pjp,parajp1;
	private JRadioButton m1,p1,m2,p2,m3,p3,m4,p4,m5,p5,m6,p6,m7,p7,zr;
	private JRadioButton pass,mask;
	private JRadioButton symmetry,custom;
	public JRadioButton jrb1,jrb2,jrb3,jrb4;
	public f2Panel fpk;
	public JLabel jilabel,alabel,dlabel,Llabel;
	public JTextField leveljtf;
	public JScrollBar asb,dsb,lsb;
	
	public JPanel launchFrame(){
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(1000, 700));
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(500, centerPanel.getHeight()));
		 imgjp = new JPanel(new GridLayout(4,1));
        imgjp.setPreferredSize(new Dimension(rightPanel.getWidth(),230));
		try {
			Image img7 = ImageIO.read(getClass().getResource(
					"/picture/4f.PNG"));
			BufferedImage tag1 = new BufferedImage(480, 180,
					BufferedImage.TYPE_INT_RGB);
			tag1.getGraphics().drawImage(
					img7.getScaledInstance(480, 180, Image.SCALE_SMOOTH), 0,
					0, null);
			img7 = tag1;
			// picture放入jlabel中
			ImageIcon picture1 = new ImageIcon(img7);
			fourf = new JLabel(picture1);
			
		} catch (Exception e) {
		}
		
		
		
		pjp = new JPanel(new BorderLayout());
		pjp.setPreferredSize(new Dimension(rightPanel.getWidth(),330));
		parajp1 = new JPanel(new GridLayout(3,5));parajp = new JPanel(new GridLayout(8, 2));
		parajp1.setPreferredSize(new Dimension(pjp.getWidth(),90));
		parajp.setPreferredSize(new Dimension(pjp.getWidth(),240));
		
		m1 = new JRadioButton("-1级");
		p1 = new JRadioButton("+1级");
		m2 = new JRadioButton("-2级");
		p2 = new JRadioButton("+2级");
		m3 = new JRadioButton("-3级");
		p3 = new JRadioButton("+3级");
		m4 = new JRadioButton("-4级");
		p4 = new JRadioButton("+4级");
		m5 = new JRadioButton("-5级");
		p5 = new JRadioButton("+5级");
		m6 = new JRadioButton("-6级");
		p6 = new JRadioButton("+6级");
		m7 = new JRadioButton("-7级");
		p7 = new JRadioButton("+7级");
		zr = new JRadioButton("0级");
		parajp1.add(m7);parajp1.add(m6);parajp1.add(m5);parajp1.add(m4);parajp1.add(m3);parajp1.add(m2);parajp1.add(m1);
		parajp1.add(zr);
		parajp1.add(p1);parajp1.add(p2);parajp1.add(p3);parajp1.add(p4);parajp1.add(p5);parajp1.add(p6);parajp1.add(p7);
		  m1.addActionListener(new m1Listener());
		  m2.addActionListener(new m2Listener());
		  m3.addActionListener(new m3Listener());
		  m4.addActionListener(new m4Listener());
		  m5.addActionListener(new m5Listener());
		  m6.addActionListener(new m6Listener());
		  m7.addActionListener(new m7Listener());
		  p1.addActionListener(new p1Listener());
		  p2.addActionListener(new p2Listener());
		  p3.addActionListener(new p3Listener());
		  p4.addActionListener(new p4Listener());
		  p5.addActionListener(new p5Listener());
		  p6.addActionListener(new p6Listener());
		  p7.addActionListener(new p7Listener());
		  zr.addActionListener(new zrListener());
		  
		pass = new JRadioButton("通过");
		mask = new JRadioButton("屏蔽");
		symmetry = new JRadioButton("对称");
		custom = new JRadioButton("自定义");
		jrb1 = new JRadioButton("只通过某级");
	     jrb2 = new JRadioButton("只屏蔽某级");
	     jrb3 = new JRadioButton("通过某级及以内");
	     jrb4 = new JRadioButton("屏蔽某级及以内");
	     symmetry.addActionListener(new symmetryListener());
	     custom.addActionListener(new customListener());
	     pass.addActionListener(new passListener());
	     mask.addActionListener(new maskListener());
	     jrb1.addActionListener(new jrb1Listener());
	     jrb2.addActionListener(new jrb2Listener());
	     jrb3.addActionListener(new jrb3Listener());
	     jrb4.addActionListener(new jrb4Listener());
	     ButtonGroup ds = new ButtonGroup();
	     ButtonGroup sc = new ButtonGroup();
	     ButtonGroup pm = new ButtonGroup();
	     pm.add(pass);pm.add(mask);
	     sc.add(symmetry);sc.add(custom);
	     ds.add(jrb1);ds.add(jrb2);ds.add(jrb3);ds.add(jrb4);
	     jrb1.setSelected(true);
	     symmetry.setSelected(true);
	     
	     parajp.add(pass);parajp.add(mask);parajp.add(symmetry);parajp.add(custom);parajp.add(jrb1);parajp.add(jrb2);parajp.add(jrb3);parajp.add(jrb4);
	     jilabel = new JLabel("级数（±1）");
	     alabel = new JLabel("缝宽         a (cm) = 0.3");
	     dlabel = new JLabel("光栅常数 d (cm) = 0.8");
	     Llabel = new JLabel("光栅长度 L (cm) = 20");
	     leveljtf = new JTextField("1");
	     leveljtf.addActionListener(new leveljtfListener());
	     parajp.add(jilabel);parajp.add(leveljtf);
	     asb = new JScrollBar(JScrollBar.HORIZONTAL, 30, 0, 10, 60);
	     asb.addAdjustmentListener(new asbListener());
	     dsb = new JScrollBar(JScrollBar.HORIZONTAL, 80, 0, 40, 130);
	     dsb.addAdjustmentListener(new dsbListener());
	     lsb = new JScrollBar(JScrollBar.HORIZONTAL, 20, 0, 10, 80);
	     lsb.addAdjustmentListener(new lsbListener());
	     parajp.add(alabel);parajp.add(asb);
	     parajp.add(dlabel);parajp.add(dsb);
	     parajp.add(Llabel);parajp.add(lsb);
	     pass.setEnabled(false);mask.setEnabled(false);
	     m1.setEnabled(false); m2.setEnabled(false); m3.setEnabled(false); m4.setEnabled(false); m5.setEnabled(false); m6.setEnabled(false); m7.setEnabled(false);
	     zr.setEnabled(false);
	     p1.setEnabled(false); p2.setEnabled(false); p3.setEnabled(false); p4.setEnabled(false); p5.setEnabled(false); p6.setEnabled(false); p7.setEnabled(false);
	     
       
        rightPanel.add(fourf,BorderLayout.NORTH);
        
       
    //    fLabel = new JLabel();FLabel = new JLabel();
        
        refresh();
        
        
        fpk = new f2Panel();
       
        
   
        
        flb = new JLabel();
      //  flb.setPreferredSize(new Dimension(imgjp.getWidth(),imgjp.getHeight()/2-8));
        glb = new JLabel();
     //   glb.setPreferredSize(new Dimension(imgjp.getWidth(),imgjp.getHeight()/2-8));
	    paintimg(1,flb);
	    paintimg(2,glb);
	    shuruguangshan =new JLabel();
	    shuruguangshan.setText("                                                                  输入光栅");
	    shuchutuxiang =new JLabel();
	    shuchutuxiang.setText("                                                                   输出光场");
	     imgjp.add(flb);
	     imgjp.add(shuruguangshan);
	     imgjp.add(glb);
	     imgjp.add(shuchutuxiang);
         rightPanel.add(imgjp, BorderLayout.CENTER);
         pjp.add(parajp1, BorderLayout.NORTH);pjp.add(parajp, BorderLayout.SOUTH);
        rightPanel.add(pjp, BorderLayout.SOUTH);
        centerPanel.add(fpk);
	     centerPanel.add(rightPanel,BorderLayout.EAST);
        return centerPanel;
	}
	
	
	class f2Panel extends JPanel  
	{   
	    protected void paintComponent(Graphics g)  
	    {   
	        super.paintComponent(g);  
	        int lth = 520;
	        //画x轴  
	        g.drawLine(0,110,getWidth()-1,110);  
	        //x箭头  
	        g.drawLine(getWidth()-11,110-10,getWidth()-1,110);  
	        g.drawLine(getWidth()-11,110+10,getWidth()-1,110);  
	        //“x”  
	        g.drawString("X1",getWidth()-25,110+20); 
	        
	       
	        g.drawLine(0,210,getWidth()-1,210);   
	        g.drawLine(getWidth()-11,210-10,getWidth()-1,210);  
	        g.drawLine(getWidth()-11,210+10,getWidth()-1,210);  
	        g.drawString("X2",getWidth()-25,210+20);
	        
	        g.drawLine(0,300,getWidth()-1,300);   
	        g.drawLine(getWidth()-11,300-10,getWidth()-1,300);  
	        g.drawLine(getWidth()-11,300+10,getWidth()-1,300);  
	        g.drawString("X2",getWidth()-25,300+20);
	        
	        g.drawLine(0,420,getWidth()-1,420);   
	        g.drawLine(getWidth()-11,420-10,getWidth()-1,420);  
	        g.drawLine(getWidth()-11,420+10,getWidth()-1,420);  
	        g.drawString("X2",getWidth()-25,420+20);
	        
	        g.drawLine(0,540,getWidth()-1,540);   
	        g.drawLine(getWidth()-11,540-10,getWidth()-1,540);  
	        g.drawLine(getWidth()-11,540+10,getWidth()-1,540);  
	        g.drawString("X3",getWidth()-25,540+20);
	        
	        g.drawLine(0,680,getWidth()-1,680);   
	        g.drawLine(getWidth()-11,680-10,getWidth()-1,680);  
	        g.drawLine(getWidth()-11,680+10,getWidth()-1,680);  
	        g.drawString("X3",getWidth()-25,680+20);
	        
	  
	  
	        //画y轴  
	        g.drawLine(lth+3,getHeight()-20,lth+3,20);  
	        g.drawLine(lth+3-10,30,lth+3,20);  
	        g.drawLine(lth+3+10,30,lth+3,20);  
	        g.drawString("Y",lth+3+20,30);  
	        g.drawString("输入光振幅分布",lth-3-80,45);
	        g.drawString("f(X1)",lth+3+20,45);
	        g.drawString("输入光分布的傅立叶变换",lth-3-130,135);
	        g.drawString("F(X2/λf)",lth+3+20,135);
	        g.drawString("滤波器分布",lth-3-60,260);
	        g.drawString("H(X2/λf)",lth+3+20,260);
	        g.drawString("滤波后频域分布",lth-3-80,340);
	        g.drawString("F(X2/λf)H(X2/λf)",lth+3+20,340);
	        g.drawString("输出光振幅分布",lth-3-80,475);
	        g.drawString("g(X3)",lth+3+20,475);
	        g.drawString("输出光强分布",lth-3-70,565);
	        g.drawString("I(X3)",lth+3+20,565);
	        
	        
	        //画函数图像  
	       Polygon p=new Polygon();  
	       
	        for(int x=-lth;x<lth;x++)  
	        {  
	            p.addPoint(x+lth+3,110-60*fp[3*x+16384]);  
	        }  
	        
	        Polygon q=new Polygon();  
	        
	        for(int x = -lth; x < lth;x++){
	        	q.addPoint(x+lth+3, (int)(210-0.09*Fp[8*x+16384]));
	        }
	        
	        Polygon hp=new Polygon(); 
	        for(int x = -lth; x < lth;x++){
	        	hp.addPoint(x+lth+3, (int)(300-30*Hp[8*x+16384]));
	        }
	        
	        Polygon fbp=new Polygon(); 
	        for(int x = -lth; x < lth;x++){
	        	fbp.addPoint(x+lth+3, (int)(420-0.09*(fGp[8*x+16384])));
	        } 
	        
	        Polygon bp=new Polygon(); 
	        for(int x = -lth; x < lth;x++){
	        	bp.addPoint(x+lth+3, (int)(540-40*a/d*(Gp[3*x+16384])));
	        } 
	        
	        Polygon gp=new Polygon(); 
	        for(int x = -lth; x < lth;x++){
	        	gp.addPoint(x+lth+3, (int)(680-88*(Gp[3*x+16384]*Gp[3*x+16384])));
	        } 
	  
	        
	        
	        g.drawPolyline(p.xpoints,p.ypoints,p.npoints); 
	        g.drawPolyline(q.xpoints,q.ypoints,q.npoints); 
	        g.drawPolyline(hp.xpoints,hp.ypoints,hp.npoints);
	        g.drawPolyline(gp.xpoints,gp.ypoints,gp.npoints);
	        g.drawPolyline(bp.xpoints,bp.ypoints,bp.npoints);
	        g.drawPolyline(fbp.xpoints,fbp.ypoints,fbp.npoints);
	    }  
	
}
    
	public void refresh(){
		OnedFltAlg ofl = new OnedFltAlg(a,d,L,type,level,sorc,clevel);
        fp = ofl.f;
        
      
        for(int i = 0; i < 32768; i++){
        	Fp[i]=ofl.interval[i].getR();
        }
        Hp = ofl.H;
        for(int i = 0; i < 32768; i++){
        	Gp[i]=ofl.g[i].getR();
        }
        for(int i = 0; i < 32768; i++){
        	fGp[i]=ofl.G[i].getR();
        }
	}
	
	public class passListener implements ActionListener { 
	public void actionPerformed(ActionEvent f) {
		type =1;
		 
		refresh();
		 paintimg(1,flb);
		    paintimg(2,glb);
		 fpk = new f2Panel();
        centerPanel.add(fpk);
        centerPanel.updateUI();
	}
   }
	
	public class maskListener implements ActionListener { 
	public void actionPerformed(ActionEvent f) {
		type=2;
		
		refresh();
		 paintimg(1,flb);
		    paintimg(2,glb);
		 fpk = new f2Panel();
        centerPanel.add(fpk);
        centerPanel.updateUI();
	}
   }
	
	public class symmetryListener implements ActionListener { 
	public void actionPerformed(ActionEvent f) {
		 sorc =1;
		
		 jrb1.setEnabled(true);jrb2.setEnabled(true);jrb3.setEnabled(true);jrb4.setEnabled(true);leveljtf.setEnabled(true);
		 pass.setEnabled(false);mask.setEnabled(false);
		 m1.setEnabled(false); m2.setEnabled(false); m3.setEnabled(false); m4.setEnabled(false); m5.setEnabled(false);m6.setEnabled(false); m7.setEnabled(false);
	     zr.setEnabled(false);
	     p1.setEnabled(false); p2.setEnabled(false); p3.setEnabled(false); p4.setEnabled(false); p5.setEnabled(false);p6.setEnabled(false); p7.setEnabled(false);
	}
   }
	
	public class customListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			 sorc =2;
			 type=1;
			 jrb1.setEnabled(false);jrb2.setEnabled(false);jrb3.setEnabled(false);jrb4.setEnabled(false);leveljtf.setEnabled(false);
			 pass.setEnabled(true);mask.setEnabled(true);
			 m1.setEnabled(true); m2.setEnabled(true); m3.setEnabled(true); m4.setEnabled(true); m5.setEnabled(true);m6.setEnabled(true); m7.setEnabled(true);
		     zr.setEnabled(true);
		     p1.setEnabled(true); p2.setEnabled(true); p3.setEnabled(true); p4.setEnabled(true); p5.setEnabled(true);p6.setEnabled(true); p7.setEnabled(true);
		     pass.setSelected(true);
		}
	   }
	
	public class m1Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[0]==0) {
				clevel[0]=1;
			}
			else{
				m1.setSelected(false);
				clevel[0]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class m2Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[1]==0) {
				clevel[1]=1;
			}
			else{
				m2.setSelected(false);
				clevel[1]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class m3Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[2]==0) {
				clevel[2]=1;
			}
			else{
				m3.setSelected(false);
				clevel[2]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class m4Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[3]==0) {
				clevel[3]=1;
			}
			else{
				m4.setSelected(false);
				clevel[3]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class m5Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[4]==0) {
				clevel[4]=1;
			}
			else{
				m5.setSelected(false);
				clevel[4]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class m6Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[5]==0) {
				clevel[5]=1;
			}
			else{
				m6.setSelected(false);
				clevel[5]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class m7Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[6]==0) {
				clevel[6]=1;
			}
			else{
				m7.setSelected(false);
				clevel[6]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p1Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[8]==0) {
				clevel[8]=1;
			}
			else{
				p1.setSelected(false);
				clevel[8]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p2Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[9]==0) {
				clevel[9]=1;
			}
			else{
				p2.setSelected(false);
				clevel[9]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p3Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[10]==0) {
				clevel[10]=1;
			}
			else{
				p3.setSelected(false);
				clevel[10]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p4Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[11]==0) {
				clevel[11]=1;
			}
			else{
				p3.setSelected(false);
				clevel[11]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p5Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[12]==0) {
				clevel[12]=1;
			}
			else{
				p5.setSelected(false);
				clevel[12]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p6Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[13]==0) {
				clevel[13]=1;
			}
			else{
				p6.setSelected(false);
				clevel[13]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class p7Listener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[14]==0) {
				clevel[14]=1;
			}
			else{
				p7.setSelected(false);
				clevel[14]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class zrListener implements ActionListener { 
		public void actionPerformed(ActionEvent f) {
			if(clevel[7]==0) {
				clevel[7]=1;
			}
			else{
				zr.setSelected(false);
				clevel[7]=0;
			}
			
			refresh();
			 paintimg(1,flb);
			    paintimg(2,glb);
			 fpk = new f2Panel();
	         centerPanel.add(fpk);
	         centerPanel.updateUI();
		}
	   }
	
	public class jrb1Listener implements ActionListener { 
	public void actionPerformed(ActionEvent f) {
		 type =1;
		
		 refresh();
		 paintimg(1,flb);
		    paintimg(2,glb);
		 fpk = new f2Panel();
         centerPanel.add(fpk);
         centerPanel.updateUI();
	}
   }
    
	 public class jrb2Listener implements ActionListener { 
			public void actionPerformed(ActionEvent f) {
				type =2;
				
				 refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
				
			}
		   }
	 
	 public class jrb3Listener implements ActionListener { 
			public void actionPerformed(ActionEvent f) {
				type =3;
				
				 refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
			}
		   }
	 
	 public class jrb4Listener implements ActionListener { 
			public void actionPerformed(ActionEvent f) {
				type =4;
				
				 refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
			}
		   }
	 
	 public class leveljtfListener implements ActionListener { 
			public void actionPerformed(ActionEvent f) {
				String w1 = f.getActionCommand();
				
				level = Integer.parseInt(w1);
				if(level!=0){
				jilabel.setText("级数（±"+level+"）");}
				else{jilabel.setText("级数（0）");}
				refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
			}
		}
	 
	 class asbListener implements AdjustmentListener { // 滚动条监视
			public void adjustmentValueChanged(AdjustmentEvent e) {
				double v1 = e.getValue();
				a = v1/100;
				alabel.setText("缝宽         a (cm) = "+v1/100);
				refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
	         
			}					
		}
	 
	 class dsbListener implements AdjustmentListener { // 滚动条监视
			public void adjustmentValueChanged(AdjustmentEvent e) {
				double v1 = e.getValue();
				d = v1/100;
				dlabel.setText("光栅常数 d (cm) = "+v1/100);
				refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
	         
			}					
		}
	 
	 class lsbListener implements AdjustmentListener { // 滚动条监视
			public void adjustmentValueChanged(AdjustmentEvent e) {
				double v1 = e.getValue();
				L = v1;
				Llabel.setText("光栅宽度 L (cm) = "+(int)L);
				refresh();
				 paintimg(1,flb);
				    paintimg(2,glb);
				 fpk = new f2Panel();
		         centerPanel.add(fpk);
		         centerPanel.updateUI();
	         
			}					
		}
	 
	 public BufferedImage generateimg(int tag){
		 int l=500;
		 int w = 122;
		 int[][] img_int = new int[l][w];
		
		 int mid_imgint = l/2;int mid_r = (32768)/2;
		 int[] r =new int[32768];
		 int i,j;
		 double max=-1000;
		 for(i=0;i<32768;i++){
			 if(tag ==1){
				 if(fp[i]>max)  max=fp[i];
			 }
			 else{
				 if(Gp[i]>max)  max=Gp[i];
			 }
		 }
		 for(i=0;i<32768;i++){
			 if(tag==1){//tag == 1 means f
				 r[i]=(int)(fp[i]*fp[i]*255/(max*max));
			 }
			 else{
				 r[i]=(int)(Gp[i]*Gp[i]*255/(max*max));
			 }
			 
		 }
		 for(j=0;j<w;j++){
		 for(i=mid_imgint;i<l;i++){
			 img_int[i][j]=r[mid_r+(i-mid_imgint)*3];
			 img_int[l-1-i][j]=img_int[i][j];
		 }
		 }
		 
		
		 int lenth = img_int.length;
	//	 int wid = img_int[0].length;
			BufferedImage dest = new BufferedImage(lenth,w,BufferedImage.TYPE_BYTE_GRAY);
	    	//int alpha, newred, newgreen, newblue, newrgb;
	    	//alpha = (-1) << 24;
	    	for(i = 0; i < lenth; i++){
	    		for(j = 0; j < w; j++){
	    			int k = img_int[i][j];
	    			dest.setRGB(i, j, (k*256+k)*256+k);
	    			
	    		}
	    	}
	    	
	    	return dest;
	 }
	 
	 public void paintimg(int tag,JLabel lb){
			BufferedImage flt = generateimg(tag);
			ImageIcon image = new ImageIcon((Image)flt);  
	        
	        lb.setIcon(image); 
			
		}
	 
}




