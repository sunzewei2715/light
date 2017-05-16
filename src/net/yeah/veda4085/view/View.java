/**
 * main method
 * 在每一个公共类的开头，都要写清楚引入的包，尽量不写*。
 *
 */
package net.yeah.veda4085.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int locationX = 50;			//窗口位置
	private static final int locationY = 50;
	private static final int WindowWidth = 1700;		//窗口大小
	private static final int WindowHeight = 800;

	private JMenuBar menuBar;

	private JTree catalogue = null;

	private JPanel westPanel = null;					//Tree

	DefaultMutableTreeNode root, young, time, space, Mc, ND, Mo, polarized,
			moireEffect, re, geo, reso, DRv, diffractionGrating,
			polarizedLight, fresenlDiffraction, polarizedLightPropagation,
			multipleBeamsInterference, singl, pris, disper,wed,filtr,odf,gf;

	private JPanel northPanel = null;					//Nanjing University

	private JPanel centerPanel = new JPanel();			//Light

	//创建20个对象
	YoungIntervention youngInt = new YoungIntervention();
	TimeIntervention timeInt = new TimeIntervention();
	SpaceIntervention spaceInt = new SpaceIntervention();
	McIntervention mcInt = new McIntervention();
	NDIntervention ndInt = new NDIntervention();
	PolarizedIntervention polInt = new PolarizedIntervention();
	DiffractionGratingI dgInt = new DiffractionGratingI();
	PolarizedLight polLight = new PolarizedLight();
	FresenlDiffraction fresenlDiff = new FresenlDiffraction();
	PolarizedLightPropagation polarPro = new PolarizedLightPropagation();
	MoireEffect morEfct = new MoireEffect();
	Moireview Moi = new Moireview();
	MultiInt mulBeamInter = new MultiInt();
	refe ref = new refe();
	Diffroundview DR = new Diffroundview();
	imagegeo geoi = new imagegeo();
	reso resol = new reso();
	Singleview single = new Singleview();
	Prismview prism = new Prismview();
	Dispersionview dispersion = new Dispersionview();
	WedIntervention wedInt = new WedIntervention();
	Filter flt = new Filter();
	OnedFlt of = new OnedFlt();
	generalFrhf gfrhf = new  generalFrhf();

	public void lunchFrame() {
		this.setTitle("光学仿真");
		this.setSize(WindowWidth, WindowHeight);
		setLocation(locationX, locationY);
		
		this.setLocationRelativeTo(null);

		produceWestPanel();
		add(westPanel, BorderLayout.WEST);
		produceNorthPanel();
		add(northPanel, BorderLayout.NORTH);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				System.exit(0);
			}
		});
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);
	}

	private void produceWestPanel() {
		westPanel = new JPanel(new GridLayout(1, 1));
//		westPanel = new JPanel();	Test 
		root = new DefaultMutableTreeNode("光学仿真");
		young = new DefaultMutableTreeNode("单色光杨氏双缝干涉");
		time = new DefaultMutableTreeNode("时间相干双缝干涉");
		space = new DefaultMutableTreeNode("空间相干双缝干涉");
		Mc = new DefaultMutableTreeNode("迈克尔逊干涉");
		ND = new DefaultMutableTreeNode("等厚干涉牛顿环");
		polarized = new DefaultMutableTreeNode("偏振光干涉");
		diffractionGrating = new DefaultMutableTreeNode("衍射光栅");
		polarizedLight = new DefaultMutableTreeNode("偏振光的几何表示");
		fresenlDiffraction = new DefaultMutableTreeNode("菲涅尔衍射");
		polarizedLightPropagation = new DefaultMutableTreeNode("偏振光传播");
		moireEffect = new DefaultMutableTreeNode("莫尔效应1");
		Mo = new DefaultMutableTreeNode("莫尔效应2");
		multipleBeamsInterference = new DefaultMutableTreeNode("多光束干涉");
		re = new DefaultMutableTreeNode("折射定律");
		geo = new DefaultMutableTreeNode("几何光学成像");
		reso = new DefaultMutableTreeNode("透镜成像分辨率");
		DRv = new DefaultMutableTreeNode("夫琅禾费圆孔衍射");
		singl = new DefaultMutableTreeNode("单球面成像");
		pris = new DefaultMutableTreeNode("三棱镜最小偏向角");
		disper = new DefaultMutableTreeNode("三棱镜色散");
		wed = new DefaultMutableTreeNode("楔形膜干涉");
		filtr = new DefaultMutableTreeNode("二维图像空间滤波");
		odf = new DefaultMutableTreeNode("一维光栅空间滤波");
        gf = new DefaultMutableTreeNode("一般夫琅禾费衍射");
		
		root.add(young);
		root.add(time);
		root.add(space);
		root.add(Mc);
		root.add(ND);
		root.add(diffractionGrating);
		root.add(polarizedLight);
		root.add(polarized);
		root.add(fresenlDiffraction);
		root.add(polarizedLightPropagation);
		root.add(moireEffect);
		root.add(Mo);
		root.add(multipleBeamsInterference);
		root.add(re);
		root.add(geo);
		root.add(reso);
		root.add(DRv);
		root.add(singl);
		root.add(pris);
		root.add(disper);
		root.add(wed);
		root.add(filtr);
		root.add(odf);
		root.add(gf);

		DefaultTreeModel model = new DefaultTreeModel(root);
		catalogue = new JTree(model);

		JScrollPane scroll = new JScrollPane(catalogue);

		// 设置westPanel大小，先获得Container			可以修改：第一行与第三行代码可以合并
		Container contentPane = this.getContentPane();
		westPanel.setPreferredSize(new Dimension(170, 170));
		contentPane.add(westPanel);
		westPanel.add(scroll);
		
		//添加事件监听器
		catalogue.addMouseListener(new TreeMouseClick());
	}

	private void produceNorthPanel() {
		northPanel = new JPanel();

		JLabel L1 = new JLabel("南京大学&&物理学院&&信息光子学实验室");
		L1.setForeground(Color.BLUE); // 标签颜色
		L1.setFont(new Font("Serif", Font.PLAIN, 15)); // 标签字体

		menuBar = new JMenuBar();
		JMenu aboutMenu = new JMenu("关于");
		menuBar.add(aboutMenu);
		JMenuItem tutor = new JMenuItem("指导老师: 丁剑平");
		JMenuItem author1 = new JMenuItem(
				"作者: 陈杰、周方成、金鑫、马达标、杨鑫、杨立衡、陈野、时代、陆遥、张汝宸、高俊森、吴德兵、陈遒正");

		JMenuItem time = new JMenuItem("完成时间: 2017年3月10日");
		aboutMenu.add(tutor);
		aboutMenu.add(author1);
		aboutMenu.add(time);

		northPanel.add(L1);
		northPanel.add(menuBar);

	}

	public void produceCenterPanel() {
		this.add(centerPanel);
		centerPanel.setVisible(true);
	}

	public void destroyCenterPanel() { // 覆盖原组件
		if (centerPanel != null) {

			centerPanel.setVisible(false);
			this.remove(centerPanel);
		}
	}

	public class TreeMouseClick extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			DefaultMutableTreeNode a = null;
			try {
				a = (DefaultMutableTreeNode) catalogue.getSelectionPath()
						.getLastPathComponent();
			} catch (Exception ex) {

			}

			if (e.getClickCount() == 1) {

				destroyCenterPanel();

				if (a == young) {
					centerPanel = youngInt.launchFrame();

				}
				if (a == time) {
					centerPanel = timeInt.launchFrame();
				}
				if (a == space) {
					centerPanel = spaceInt.launchFrame();
				}
				if (a == Mc) {
					centerPanel = mcInt.launchFrame();
				}
				if (a == ND) {
					centerPanel = ndInt.launchFrame();
				}
				if (a == polarized) {
					centerPanel = polInt.launchFrame();
				}
				if (a == diffractionGrating) {
					centerPanel = dgInt.launchFrame();
				}
				if (a == polarizedLight) {
					centerPanel = polLight.launchFrame();
				}
				if (a == fresenlDiffraction) {
					centerPanel = fresenlDiff.launchFrame();
				}
				if (a == polarizedLightPropagation) {
					centerPanel = polarPro.launchFrame();
				}
				if (a == moireEffect) {
					centerPanel = morEfct.launchFrame();
				}
				if (a == Mo) {
					centerPanel = Moi.launchFrame();
				}
				if (a == multipleBeamsInterference) {
					centerPanel = mulBeamInter.launchFrame();
				}
				if (a == re) {
					centerPanel = ref.launchFrame();
				}
				if (a == geo) {
					centerPanel = geoi.launchFrame();
				}
				if (a == reso) {
					centerPanel = resol.launchFrame();
				}
				if (a == DRv) {
					centerPanel = DR.launchFrame();
				}
				if (a == singl) {
					centerPanel = single.launchFrame();
				}
				if (a == pris) {
					centerPanel = prism.launchFrame();
				}
				if (a == disper) {
					centerPanel = dispersion.launchFrame();
				}
				
				if (a==wed) {
					centerPanel = wedInt.launchFrame();
				}
				if(a == filtr){
					centerPanel = flt.launchFrame();
				}
				if(a == odf){
					centerPanel = of.launchFrame();
				}
				if(a == gf){
					centerPanel = gfrhf.launchFrame();
				}
					
				produceCenterPanel();
				setVisible(true);
			}
		}
	}

	public static void main(String[] args) {
		View v = new View();
		v.lunchFrame();
	}
}
