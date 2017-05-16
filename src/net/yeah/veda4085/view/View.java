/**
 * main method
 * ��ÿһ��������Ŀ�ͷ����Ҫд�������İ���������д*��
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

	private static final int locationX = 50;			//����λ��
	private static final int locationY = 50;
	private static final int WindowWidth = 1700;		//���ڴ�С
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

	//����20������
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
		this.setTitle("��ѧ����");
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
		root = new DefaultMutableTreeNode("��ѧ����");
		young = new DefaultMutableTreeNode("��ɫ������˫�����");
		time = new DefaultMutableTreeNode("ʱ�����˫�����");
		space = new DefaultMutableTreeNode("�ռ����˫�����");
		Mc = new DefaultMutableTreeNode("���˶�ѷ����");
		ND = new DefaultMutableTreeNode("�Ⱥ����ţ�ٻ�");
		polarized = new DefaultMutableTreeNode("ƫ������");
		diffractionGrating = new DefaultMutableTreeNode("�����դ");
		polarizedLight = new DefaultMutableTreeNode("ƫ���ļ��α�ʾ");
		fresenlDiffraction = new DefaultMutableTreeNode("����������");
		polarizedLightPropagation = new DefaultMutableTreeNode("ƫ��⴫��");
		moireEffect = new DefaultMutableTreeNode("Ī��ЧӦ1");
		Mo = new DefaultMutableTreeNode("Ī��ЧӦ2");
		multipleBeamsInterference = new DefaultMutableTreeNode("���������");
		re = new DefaultMutableTreeNode("���䶨��");
		geo = new DefaultMutableTreeNode("���ι�ѧ����");
		reso = new DefaultMutableTreeNode("͸������ֱ���");
		DRv = new DefaultMutableTreeNode("���ź̷�Բ������");
		singl = new DefaultMutableTreeNode("���������");
		pris = new DefaultMutableTreeNode("���⾵��Сƫ���");
		disper = new DefaultMutableTreeNode("���⾵ɫɢ");
		wed = new DefaultMutableTreeNode("Ш��Ĥ����");
		filtr = new DefaultMutableTreeNode("��άͼ��ռ��˲�");
		odf = new DefaultMutableTreeNode("һά��դ�ռ��˲�");
        gf = new DefaultMutableTreeNode("һ����ź̷�����");
		
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

		// ����westPanel��С���Ȼ��Container			�����޸ģ���һ��������д�����Ժϲ�
		Container contentPane = this.getContentPane();
		westPanel.setPreferredSize(new Dimension(170, 170));
		contentPane.add(westPanel);
		westPanel.add(scroll);
		
		//����¼�������
		catalogue.addMouseListener(new TreeMouseClick());
	}

	private void produceNorthPanel() {
		northPanel = new JPanel();

		JLabel L1 = new JLabel("�Ͼ���ѧ&&����ѧԺ&&��Ϣ����ѧʵ����");
		L1.setForeground(Color.BLUE); // ��ǩ��ɫ
		L1.setFont(new Font("Serif", Font.PLAIN, 15)); // ��ǩ����

		menuBar = new JMenuBar();
		JMenu aboutMenu = new JMenu("����");
		menuBar.add(aboutMenu);
		JMenuItem tutor = new JMenuItem("ָ����ʦ: ����ƽ");
		JMenuItem author1 = new JMenuItem(
				"����: �½ܡ��ܷ��ɡ����Ρ����ꡢ���Ρ������⡢��Ұ��ʱ����½ң������巡��߿�ɭ����±���������");

		JMenuItem time = new JMenuItem("���ʱ��: 2017��3��10��");
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

	public void destroyCenterPanel() { // ����ԭ���
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
