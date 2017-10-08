import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class JTicTacToe extends JFrame
					implements ActionListener, MouseListener, MouseMotionListener, WindowListener {

	//*** Componentes ****************************************************************************************************************************
	private JMenuBar menubarPrincipal;
	
	private JMenu menuArquivo, menuOpcoes, menuTipoJogo;
	
	private JMenuItem menuitemSair, menuitemNovoJogo, menuitemCancelarJogo, menuitemEstatisticas;
	private JRadioButtonMenuItem rbtitemHumanoHumano, rbtitemHumanoComputador, rbtitemComputadorHumano;
	private ButtonGroup btngroupTipoJogo;
	
	private JPanel pnlEsquerda, pnlDireita, pnlCentro;
	private JPanel pnlJogador01, pnlJogador02;
	
	private JButton[][] btnLances;
	private JButton[] btnX, btnO;
	private JButton btnJogador01, btnJogador02;
	
	private int lances[][] = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
	private int tipo = 0;
	private int[] jogo = {0, 0, 0};
	private int[] jogada = {0, 0, 0};
	private int[] vitoria01 = {0, 0, 0};
	private int[] vitoria02 = {0, 0, 0};
	private int[] empate = {0, 0, 0};
	
	private int draw = 0;
	private Point drawBegin = new Point(-1, -1);
	private Point drawEnd = new Point(-1, -1);
	//********************************************************************************************************************************************
	
	//*** Construtor *****************************************************************************************************************************
	public JTicTacToe() {
	
		this.inicializaComponentes();
		this.carregaINI();
		
	}
	//********************************************************************************************************************************************
	
	//*** Procedimentos de Inicializacao *********************************************************************************************************
	private void initializeComponentes() {
	
		//Instanciar os Componentes
		this.setLayout(null);
		this.setResizable(false);
		
		this.menubarPrincipal = new JMenuBar();
		
		this.menuArquivo = new JMenu("Arquivo");
		this.menuOpcoes = new JMenu("Opções");
		this.menuTipoJogo = new JMenu("Tipo de Jogo");
		
		this.menuitemSair = new JMenuItem("Sair");
		this.menuitemNovoJogo = new JMenuItem("Novo Jogo");
		this.menuitemCancelarJogo = new JMenuItem("Cancelar Jogo");
		this.menuitemEstatisticas = new JMenuItem("Estatísticas");
		
		this.btngroupTipoJogo = new ButtonGroup();
		this.rbtitemHumanoHumano = new JRadioButtonMenuItem("Humano VS. Humano");
		this.rbtitemHumanoComputador = new JRadioButtonMenuItem("Humano VS. Computador");
		this.rbtitemComputadorHumano = new JRadioButtonMenuItem("Computador VS. Humano");
		this.btngroupTipoJogo.add(this.rbtitemHumanoHumano);
		this.btngroupTipoJogo.add(this.rbtitemHumanoComputador);
		this.btngroupTipoJogo.add(this.rbtitemComputadorHumano);
		
		this.pnlEsquerda = new JPanel();
		this.pnlEsquerda.setLayout(null);
		this.pnlEsquerda.setBounds(20,10,100,400);
		this.pnlEsquerda.setBackground(new Color(120,120,120));
		
		this.pnlDireita = new JPanel();
		this.pnlDireita.setLayout(null);
		this.pnlDireita.setBounds(480,10,100,400);
		this.pnlDireita.setBackground(new Color(120,120,120));
		
		this.pnlCentro = new JPanel();
		this.pnlCentro.setLayout(null);
		this.pnlCentro.setBounds(120,30,360,360);
		this.pnlCentro.setBackground(new Color(120,120,120));
		
		this.pnlJogador01 = new JPanel();
		this.pnlJogador01.setLayout(new BorderLayout());
		this.pnlJogador01.setBounds(120,10,180,20);
		this.pnlJogador01.setBackground(new Color(120,120,120));
		
		this.pnlJogador02 = new JPanel();
		this.pnlJogador02.setLayout(new BorderLayout());
		this.pnlJogador02.setBounds(300,390,180,20);
		this.pnlJogador02.setBackground(new Color(120,120,120));
		
		this.btnX = new JButton[5];
		for (int i=0; i<5; i++) {
			this.btnX[i] = new JButton();
			this.btnX[i].setBounds(10,80*i,80,80);
			this.btnX[i].setBackground(new Color(120,120,120));
			this.btnX[i].setIcon(new ImageIcon("fig/X.gif"));
			this.btnX[i].setBorder(null);
			this.pnlEsquerda.add(this.btnX[i]);
		}
		
		this.btnO = new JButton[4];
		for (int i=0; i<4; i++) {
			this.btnO[i] = new JButton();
			this.btnO[i].setBounds(10,10+100*i,80,80);
			this.btnO[i].setBackground(new Color(120,120,120));
			this.btnO[i].setIcon(new ImageIcon("fig/O.gif"));
			this.btnO[i].setBorder(null);
			this.pnlDireita.add(this.btnO[i]);
		}
		
		this.btnLances = new JButton[3][3];
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				this.btnLances[i][j] = new JButton();
				this.btnLances[i][j].setBounds(130*j,130*i,100,100);
				this.btnLances[i][j].setBackground(new Color(200,200,200));
				this.btnLances[i][j].setBorder(null);
				this.pnlCentro.add(this.btnLances[i][j]);
			}
		}
		
		this.btnJogador01 = new JButton("<< Jogador 01");
		this.btnJogador01.setBackground(new Color(120,120,120));
		this.btnJogador01.setBorder(null);
		this.pnlJogador01.add(this.btnJogador01);
		
		this.btnJogador02 = new JButton("Jogador 02 >>");
		this.btnJogador02.setBackground(new Color(120,120,120));
		this.btnJogador02.setBorder(null);
		this.pnlJogador02.add(this.btnJogador02);
		
		this.add(this.pnlEsquerda);
		this.add(this.pnlDireita);
		this.add(this.pnlCentro);
		this.add(this.pnlJogador01);
		this.add(this.pnlJogador02);
		
		//Adicionar Componentes Itens a Cmoponentes Menus
		this.menuArquivo.add(this.menuitemSair);
		
		this.menuOpcoes.add(this.menuitemNovoJogo);
		this.menuOpcoes.add(this.menuitemCancelarJogo);
		this.menuOpcoes.addSeparator();
		this.menuOpcoes.add(this.menuitemEstatisticas);
		this.menuOpcoes.addSeparator();
		this.menuOpcoes.add(this.menuTipoJogo);
		
		this.menuTipoJogo.add(this.rbtitemHumanoHumano);
		this.menuTipoJogo.add(this.rbtitemHumanoComputador);
		this.menuTipoJogo.add(this.rbtitemComputadorHumano);
		
		this.menubarPrincipal.add(this.menuArquivo);
		this.menubarPrincipal.add(this.menuOpcoes);
		
		this.setJMenuBar(this.menubarPrincipal);
		
		//Adicionar Mnemonicos aos Componentes
		this.menuArquivo.setMnemonic('A');
		this.menuOpcoes.setMnemonic('O');
		this.menuitemSair.setMnemonic('r');
		this.menuitemNovoJogo.setMnemonic('N');
		this.menuitemCancelarJogo.setMnemonic('C');
		this.menuitemEstatisticas.setMnemonic('E');
		this.menuTipoJogo.setMnemonic('T');
		
		//Adicionar Handlers de Eventos
		this.addWindowListener(this);
		
		this.menuitemSair.addActionListener(this);
		this.menuitemNovoJogo.addActionListener(this);
		this.menuitemCancelarJogo.addActionListener(this);
		this.menuitemEstatisticas.addActionListener(this);
		
		for (int i=0; i<5; i++) {
			this.btnX[i].addMouseListener(this);
			this.btnX[i].addMouseMotionListener(this);
		}
		
		for (int i=0; i<4; i++) {
			this.btnO[i].addMouseListener(this);
			this.btnO[i].addMouseMotionListener(this);
		}
		
		return;
	
	}
	//********************************************************************************************************************************************

	//*** Carregar Configuracoes *****************************************************************************************************************
	private void carregaINI() {
	
		try {
			int msgIdx = 0;
			int msgCod[] = {this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.tipo, this.jogo[0], this.jogo[1], this.jogo[2], this.vitoria01[0], this.vitoria01[1], this.vitoria01[2], this.vitoria02[0], this.vitoria02[1], this.vitoria02[2], this.empate[0], this.empate[1], this.empate[2]};
			String msgDesc[] = {"UserPositionX=", "UserPositionY=", "UserWidth=", "UserHeight=", "TP=", "JG[0]=", "JG[1]=", "JG[2]=", "V01[0]=", "V01[1]=", "V01[2]=", "V02[0]=", "V02[1]=", "V02[2]=", "E[0]=", "E[1]=", "E[2]="};
			String numericos = "-+*/^0123456789";
			
			BufferedReader BR = new BufferedReader(new FileReader("JTicTacToe.ini"));
			String linhaLida = BR.readLine();
			while (linhaLida != null) {
				for (msgIdx=0; msgIdx<17; msgIdx++) {
					if (linhaLida.indexOf(msgDesc[msgIdx]) != -1) {
						char caracterLido = '0';
						String numeroLido = "";
						int idxLinhaLida = linhaLida.indexOf(msgDesc[msgIdx]) + msgDesc[msgIdx].length();
						while ((idxLinhaLida < linhaLida.length()) && (numericos.indexOf(caracterLido) != -1)) {
							caracterLido = linhaLida.charAt(idxLinhaLida);
							numeroLido = numeroLido + caracterLido;
							idxLinhaLida = idxLinhaLida + 1;
						}
						msgCod[msgIdx] = Integer.parseInt(numeroLido);
					}
				}
				linhaLida = BR.readLine();
			}
			BR.close();
			
			this.setBounds(msgCod[0], msgCod[1], 610, 480);
			this.tipo = 0;
			for (msgIdx=0; msgIdx<3; msgIdx++) {
				this.jogo[msgIdx] = msgCod[msgIdx+5];
				this.vitoria01[msgIdx] = msgCod[msgIdx+8];
				this.vitoria02[msgIdx] = msgCod[msgIdx+11];
				this.empate[msgIdx] = msgCod[msgIdx+14];
			}
			this.rbtitemHumanoHumano.setSelected(true);
			this.rbtitemHumanoComputador.setEnabled(false);
			this.rbtitemComputadorHumano.setEnabled(false);
		}
		catch (Exception e) {
			this.setBounds(0,0,610,480);
			this.tipo = 0;
			for (int msgIdx=0; msgIdx<3; msgIdx++) {
				this.jogo[msgIdx] = 0;
				this.vitoria01[msgIdx] = 0;
				this.vitoria02[msgIdx] = 0;
				this.empate[msgIdx] = 0;
			}
			this.rbtitemHumanoHumano.setSelected(true);
			this.rbtitemHumanoComputador.setEnabled(false);
			this.rbtitemComputadorHumano.setEnabled(false);
		}
		
		return;
	
	}
	//********************************************************************************************************************************************

	//*** Salvar Configuracoes *******************************************************************************************************************
	private void salvaINI() {
	
		try {
			int msgIdx = 0;
			int msgCod[] = {this.getX(), this.getY(), this.getWidth(), this.getHeight(), this.tipo, this.jogo[0], this.jogo[1], this.jogo[2], this.vitoria01[0], this.vitoria01[1], this.vitoria01[2], this.vitoria02[0], this.vitoria02[1], this.vitoria02[2], this.empate[0], this.empate[1], this.empate[2]};
			String msgDesc[] = {"UserPositionX=%s", "UserPositionY=%s", "UserWidth=%s", "UserHeight=%s", "TP=%s", "JG[0]=%s", "JG[1]=%s", "JG[1]=%s", "V01[0]=%s", "V01[1]=%s", "V01[2]=%s", "V02[0]=%s", "V02[1]=%s", "V02[2]=%s", "E[0]=%s", "E[1]=%s", "E[2]=%s"};
			
			BufferedWriter BW = new BufferedWriter(new FileWriter("JTicTacToe.ini"));
			for (msgIdx=0; msgIdx<17; msgIdx++) {
				BW.write(String.format(msgDesc[msgIdx], msgCod[msgIdx]));
				BW.newLine();
			}
			BW.close();
		}
		catch (Exception e) {
			//Nao faz nada
		}
		
		return;
	
	}
	//********************************************************************************************************************************************
	
	//*** Procedimentos de Atualizacao de Componentes ******************************************************************************************
	public void paint(Graphics g) {
	
		super.paint(g);
		
		this.menuitemNovoJogo.setEnabled(((this.jogada[this.tipo] > 0) && (this.jogada[this.tipo] < 10))? false : true);
		this.menuitemCancelarJogo.setEnabled(((this.jogada[this.tipo] > 0) && (this.jogada[this.tipo] < 10))? true : false);
		
		if ((this.jogada[this.tipo] == 0) || (this.jogada[this.tipo] == 1)) {
			for (int i=0; i<3; i++) {
				for (int j=0; j<3; j++) {
					this.lances[i][j] = 0;
					this.btnLances[i][j].setIcon(null);
				}
			}
		}
		
		if ((this.jogada[this.tipo] == 0) || (this.jogada[this.tipo] == 10)) {
			for (int i=0; i<5; i++) {
				this.btnX[i].setVisible(false);
			}
			for (int i=0; i<4; i++) {
				this.btnO[i].setVisible(false);
			}
		}
		
		if (this.jogada[this.tipo] == 1) {
			for (int i=0; i<5; i++) {
				this.btnX[i].setVisible(true);
			}
			for (int i=0; i<4; i++) {
				this.btnO[i].setVisible(true);
			}
		}
		
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(4,53);
		
		if ((draw > 10) && (draw < 20)) {
			ImageIcon IC = new ImageIcon("fig/X.gif");
			IC.paintIcon(this, g2, 30 + drawEnd.x - drawBegin.x, 10 + 80*((draw%10)-1) + drawEnd.y - drawBegin.y);
		}
		if ((draw > 20) && (draw < 30)) {
			ImageIcon IC = new ImageIcon("fig/O.gif");
			IC.paintIcon(this, g2, 490 + drawEnd.x - drawBegin.x, 20 + 100*((draw%10)-1) + drawEnd.y - drawBegin.y);
		}
		
		g2.translate(-4,-53);
		
		return;
	
	}
	//********************************************************************************************************************************************
	
	//*** Procedimentos de Verificacao de Vitoria **********************************************************************************************
	public boolean verificaVitoria(int n) {
	
		boolean b = false;
		
		for (int i=0; i<3; i++) {
			if ((this.lances[0][i] == n) && (this.lances[1][i] == n) && (this.lances[2][i] == n))
				b = true;
			if ((this.lances[i][0] == n) && (this.lances[i][1] == n) && (this.lances[i][2] == n))
				b = true;
		}
		
		if ((this.lances[0][0] == n) && (this.lances[1][1] == n) && (this.lances[2][2] == n))
			b  = true;
			
		if ((this.lances[0][2] == n) && (this.lances[1][1] == n) && (this.lances[2][0] == n))
			b  = true;
		
		return b;
		
	}
	//********************************************************************************************************************************************
	
	//*** Metodos de ActionListener **************************************************************************************************************
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource() == this.menuitemSair) {
			this.salvaINI();
			System.exit(0);
		}
		
		if (ae.getSource() == this.menuitemNovoJogo) {
			this.jogada[this.tipo] = 1;
			this.repaint();
		}
		
		if (ae.getSource() == this.menuitemCancelarJogo) {
			this.jogada[this.tipo] = 0;
			this.repaint();
		}
		
		if (ae.getSource() == this.menuitemEstatisticas) {
			String S = new String();
			if (this.tipo == 2) {
				S = S + "Computador VS. Humano:\n";
				S = S + "Jogos Disputados: " + this.jogo[this.tipo] + "\n";
				S = S + "Vitorias Computador: " + this.vitoria01[this.tipo] + "\n";
				S = S + "Vitorias Humano: " + this.vitoria02[this.tipo] + "\n";
				S = S + "Empates: " + this.empate[this.tipo];
			}
			else if (this.tipo == 1) {
				S = S + "Humano VS. Computador:\n";
				S = S + "Jogos Disputados: " + this.jogo[this.tipo] + "\n";
				S = S + "Vitorias Humano: " + this.vitoria01[this.tipo] + "\n";
				S = S + "Vitorias Computador: " + this.vitoria02[this.tipo] + "\n";
				S = S + "Empates: " + this.empate[this.tipo];
			}
			else {
				S = S + "Humano VS. Humano:\n";
				S = S + "Jogos Disputados: " + this.jogo[this.tipo] + "\n";
				S = S + "Vitorias Jogador 1: " + this.vitoria01[this.tipo] + "\n";
				S = S + "Vitorias Jogador 2: " + this.vitoria02[this.tipo] + "\n";
				S = S + "Empates: " + this.empate[this.tipo];
			}
			JOptionPane.showMessageDialog(this, S, "JTicTacToe", 1);
		}
		
		return;
		
	}
	//********************************************************************************************************************************************
	
	//*** Metodos de MouseListener e MouseMotionListener *****************************************************************************************
	public void mouseClicked(MouseEvent me) {}
	public void mousePressed(MouseEvent me) {
	
		for (int i=0; i<5; i++) {
			if (me.getSource() == this.btnX[i]) {
				if ((this.jogada[this.tipo] > 0) && (this.jogada[this.tipo] < 10)) {
					if (this.jogada[this.tipo]%2 == 1) {
						drawBegin = me.getPoint();
						drawEnd = me.getPoint();
						draw = 11+i;
					}
					else {
						JOptionPane.showMessageDialog(this, "Jogador 1, NAO eh a sua vez.", "JTicTacToe", 0);
					}
				}
			}
		}
		for (int i=0; i<4; i++) {
			if (me.getSource() == this.btnO[i]) {
				if ((this.jogada[this.tipo] > 0) && (this.jogada[this.tipo] < 10)) {
					if (this.jogada[this.tipo]%2 == 0) {
						drawBegin = me.getPoint();
						drawEnd = me.getPoint();
						draw = 21+i;
					}
					else {
						JOptionPane.showMessageDialog(this, "Jogador 2, NAO eh a sua vez.", "JTicTacToe", 0);
					}
				}
			}
		}
		
		this.repaint();
		return;
		
	}
	public void mouseReleased(MouseEvent me) {
	
		for (int i=0; i<5; i++) {
			if (me.getSource() == this.btnX[i]) {
				for (int j=0; j<3; j++) {
					for (int k=0; k<3; k++) {
						if ((drawEnd.x >= 120+this.btnLances[j][k].getX() - 20-this.btnX[i].getX()) && (drawEnd.x <= 120+this.btnLances[j][k].getX() - 20-this.btnX[i].getX() + this.btnLances[j][k].getWidth())) {
							if ((drawEnd.y >= 30+this.btnLances[j][k].getY() - 10-this.btnX[i].getY()) && (drawEnd.y <= 30+this.btnLances[j][k].getY() - 10-this.btnX[i].getY() + this.btnLances[j][k].getHeight())) {
								if (this.btnLances[j][k].getIcon() == null) {
									this.lances[j][k] = 1;
									this.btnLances[j][k].setIcon(new ImageIcon("fig/X.gif"));
									this.btnX[i].setVisible(false);
									if (this.verificaVitoria(1)) {
										this.jogada[this.tipo] = 10;
										this.jogo[this.tipo] = this.jogo[this.tipo] + 1;
										this.vitoria01[this.tipo] = this.vitoria01[this.tipo] + 1;
										JOptionPane.showMessageDialog(this, "Parabens, Jogador 1!!!", "JTicTacToe", 1);
									}
									else {
										this.jogada[this.tipo] = this.jogada[this.tipo] + 1;
										if (this.jogada[this.tipo] == 10) {
											this.jogo[this.tipo] = this.jogo[this.tipo] + 1;
											this.empate[this.tipo] = this.empate[this.tipo] + 1;
											JOptionPane.showMessageDialog(this, "Empate!!!", "JTicTacToe", 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for (int i=0; i<4; i++) {
			if (me.getSource() == this.btnO[i]) {
				for (int j=0; j<3; j++) {
					for (int k=0; k<3; k++) {
						if ((drawEnd.x >= 120+this.btnLances[j][k].getX() - 480-this.btnO[i].getX()) && (drawEnd.x <= 120+this.btnLances[j][k].getX() - 480-this.btnO[i].getX() + this.btnLances[j][k].getWidth())) {
							if ((drawEnd.y >= 30+this.btnLances[j][k].getY() - 10-this.btnO[i].getY()) && (drawEnd.y <= 30+this.btnLances[j][k].getY() - 10-this.btnO[i].getY() + this.btnLances[j][k].getHeight())) {
								if (this.btnLances[j][k].getIcon() == null) {
									this.lances[j][k] = 2;
									this.btnLances[j][k].setIcon(new ImageIcon("fig/O.gif"));
									this.btnO[i].setVisible(false);
									if (this.verificaVitoria(2)) {
										this.jogada[this.tipo] = 10;
										this.jogo[this.tipo] = this.jogo[this.tipo] + 1;
										this.vitoria02[this.tipo] = this.vitoria02[this.tipo] + 1;
										JOptionPane.showMessageDialog(this, "Parabens, Jogador 2!!!", "JTicTacToe", 1);
									}
									else {
										this.jogada[this.tipo] = this.jogada[this.tipo] + 1;
										if (this.jogada[this.tipo] == 10) {
											this.jogo[this.tipo] = this.jogo[this.tipo] + 1;
											this.empate[this.tipo] = this.empate[this.tipo] + 1;
											JOptionPane.showMessageDialog(this, "Empate!!!", "JTicTacToe", 1);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		draw = 0;
		drawBegin.setLocation(-1, -1);
		drawEnd.setLocation(-1, -1);
		this.repaint();
		return;
	
	}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
	public void mouseDragged(MouseEvent me) {
		
		for (int i=0; i<5; i++) {
			if (me.getSource() == this.btnX[i]) {
				drawEnd = me.getPoint();
				draw = 11+i;
			}
		}
		for (int i=0; i<4; i++) {
			if (me.getSource() == this.btnO[i]) {
				drawEnd = me.getPoint();
				draw = 21+i;
			}
		}
		
		this.repaint();
		return;
	
	}
	public void mouseMoved(MouseEvent me) {}
	//********************************************************************************************************************************************
	
	//*** Metodos de WindowListener ************************************************************************************************************
	public void windowClosing(WindowEvent we) {
		this.salvaINI();
		System.exit(0);
	}	
	public void windowClosed(WindowEvent we) {}
	public void windowOpened(WindowEvent we) {}
	public void windowIconified(WindowEvent we) {}
	public void windowDeiconified(WindowEvent we) {}
	public void windowActivated(WindowEvent we) {}
	public void windowDeactivated(WindowEvent we) {}
	//********************************************************************************************************************************************
	
}
