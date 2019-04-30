import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {

	private static JPanel gameWindow, board, commentBox, sidebar,timelineBox, controlPanel;
	private JButton[][] intersections = new JButton[19][19];
	private static JTextArea comment, timeline;
	private static JScrollPane scroll1, scroll2;
	private static ImageIcon black, white;
	private static JMenuBar menuBar,toolBar;
	private static JMenu file, view, game,showHide;
	private static JMenuItem newGame, save, saveAs, load, exit,coordinates, moveNum, timelineButton, toolbar, tutorial, boardSize, handicap, score, info;
	private static JLabel turnTimer;
	public int turn;
	public static FileManager files;
	public ArrayList<move> memory = new ArrayList<>();
	public move m;
	public boolean isInit = false;
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		super("GO");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
 
	
		files = new FileManager();
		//Dropdown menus
		controlPanel= new JPanel(new FlowLayout(1,20,15));
		menuBar=new JMenuBar();
		
		file=new JMenu("File");
		newGame= new JMenuItem("New");
		newGame.addActionListener(this);
		save= new JMenuItem("Save");
		save.addActionListener(this);
		saveAs= new JMenuItem("Save as");
		saveAs.addActionListener(this);
		load= new JMenuItem("Load");
		load.addActionListener(this);
		exit= new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(newGame);
		file.add(save);
		file.add(saveAs);
		file.add(load);
		file.add(exit);
		
		menuBar.add(file);
		
		view=new JMenu("View");
		showHide= new JMenu("Show/Hide");
		coordinates = new JMenuItem("coordinates");
		moveNum=new JMenuItem("total moves");
		timelineButton = new JMenuItem("timeline");
		toolbar = new JMenuItem("toolbar");
		info = new JMenuItem("Game Info");
		info.addActionListener(this);
		showHide.add(coordinates);
		showHide.add(moveNum);
		showHide.add(timelineButton);
		showHide.add(toolbar);
		view.add(showHide);
		view.add(info);
		menuBar.add(view);
		
		game=new JMenu("Game");
		tutorial= new JMenuItem("Tuturial");
		boardSize=new JMenuItem("Board Size");
		handicap=new JMenuItem("Handicap");
		score = new JMenuItem("Score");
		game.add(tutorial);
		game.add(boardSize);
		game.add(handicap);
		game.add(score);
		
		menuBar.add(game);
		
		toolBar= new JMenuBar();
		menuBar.add(toolBar);
		
		turnTimer = new JLabel();
		turn = 0;
		turnTimer.setText("Moves: " + turn);
		
		controlPanel.add(menuBar);
		controlPanel.add(turnTimer);
		
		
		//Main GUI window
		gameWindow = new JPanel();
		gameWindow.setLayout(null);
		board = new JPanel(new GridLayout(19,19,0,0));
		board.setPreferredSize(new Dimension(595, 595));
		board.setBounds(50,50,595,595);
		board.setVisible(true);
		board.setEnabled(true);
		board.setBackground(new Color(181,129,32));
		board.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		black = new ImageIcon("PieceIcons/blackCenter.jpg");
		white = new ImageIcon("PieceIcons/whiteCenter.jpg");
		
		turn = 0;
		
		
		gameWindow = new JPanel(new FlowLayout(1,30,40));
		sidebar = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		//timeline and comments text fields
		commentBox = new JPanel();
		commentBox.setPreferredSize(new Dimension(300,175));
		commentBox.setVisible(true);
		commentBox.setBackground(Color.WHITE);
		commentBox.setBorder(BorderFactory.createLineBorder(Color.black));
		comment = new JTextArea(10,25);
		comment.setEditable(true);
		comment.setVisible(true);
		scroll1 = new JScrollPane(comment);
		
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scroll1.setVisible(true);
		commentBox.add(scroll1);
		
		c.weightx = 0.5;
	
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		sidebar.add(commentBox,c);
		
		timelineBox = new JPanel();
		timelineBox.setPreferredSize(new Dimension(300,375));
		timelineBox.setVisible(true);
		timelineBox.setBackground(Color.WHITE);
		timelineBox.setBorder(BorderFactory.createLineBorder(Color.black));
		timeline = new JTextArea(25,25);
		timeline.setEditable(true);
		timeline.setVisible(true);
		scroll2= new JScrollPane(timeline);
		
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setVisible(true);
		timelineBox.add(scroll2);
		
		c.ipady = 40;      //make this component tall
		c.weightx = 0.5;
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		sidebar.add(timelineBox,c);

		files = new FileManager();
		files.initiate();		
		
		setBoard();
		setHandicap(files.getH());
		
		sidebar.setVisible(true);
		gameWindow.add(board);
		//gameWindow.add(boardMargin);
		gameWindow.add(sidebar);
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(gameWindow, BorderLayout.CENTER);
		setVisible(true);
		

	}
	
	public void mouseDragged(MouseEvent e) {

		
	}

	public void mouseMoved(MouseEvent e) {
	}
		
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	
	}

	public void mousePressed(MouseEvent e) {
		//printQuadrant(e);
	}

	public void mouseReleased(MouseEvent e) {

		
	}
 
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) { //call save function
			files.save(memory);
		}
		if(e.getSource() == load) { //call load function
			timeline.setText("");
			try{
				memory = files.load();
			}catch(IOException j){				
			}
			setBoard();
			turn = 0;
			loadMove();
			
		}
		if(e.getSource() == newGame) {
			memory.clear();
			timeline.setText("");
			turnTimer.setText("Moves: 0");
			turn = 0;
			setBoard();
			files = new FileManager();
			files.initiate();
			setHandicap(files.getH());
		}
		
		if(e.getSource() == info) {
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.add(new JLabel("Game Name: " + files.getN()));
			p.add(new JLabel("Black Player Name: " + files.getB()));
			p.add(new JLabel("Handicap: " + files.getH()));
			p.add(new JLabel("White Player Name: " + files.getW()));
			p.add(new JLabel("Komi: 7.5"));
			p.add(new JLabel("Rules: Chinese"));
			JOptionPane.showMessageDialog(null, p, "Game Info", JOptionPane.PLAIN_MESSAGE);
		}
		
		if(e.getSource() == exit) { //close output file and close program
			files.exit();
		}
		setPiece(e);
		turnTimer.setText("Moves: " + turn);
	}
	
	public void storeMove(int x, int y, boolean c) { //store move made into memory
 		m = new move(x, y, c);
		memory.add(m); 
	}
	
	public void printMemory() {
		for(move printMove : memory) {
			System.out.println(	printMove.getX() + ", " + printMove.getY() + " " + printMove.colour());
		}
	}
	
	public void setPiece(ActionEvent e) {
		int whitePiece = 0x26AA;
		int blackPiece = 0x26AB;
		String s;
		for(int i=0;i<19;i++) { 
			for(int j = 0; j < 19; j++) {
				
				if(e.getSource()==intersections[i][j]) { //check each piece if it's been clicked
					if(turn%2 == 0) { //if it is black's turn
						if(intersections[i][j].getIcon() != black && intersections[i][j].getIcon() != white) {
							s = Character.toString((char)blackPiece); //unicode character
							intersections[i][j].setIcon(black); 
							timeline.append(s);
							timeline.append((i+1) + ", " + (j+1) + "\n");
							turn++;
							storeMove(i, j, true);
						}
					}
					else {			  //if it is white's turn
						if(intersections[i][j].getIcon() != black && intersections[i][j].getIcon() != white) {
							s = Character.toString((char)whitePiece);
							intersections[i][j].setIcon(white);
							timeline.append(s);
							timeline.append((i+1) + ", " + (j+1) + "\n");
							turn++;
							storeMove(i, j, false);
						}
					}
				}
			}
		}
	}
	
	public void loadMove() {
		int whitePiece = 0x26AA;
		int blackPiece = 0x26AB;
		String s;
		for(move m : memory) {
					if(m.colour()) {
						s = Character.toString((char)blackPiece);
						intersections[m.getX()][m.getY()].setIcon(black);
						timeline.append(s);
						timeline.append((m.getX()+1) + ", " + (m.getY()+1) + "\n");
						turn++;
					}
					else if(!m.colour()) {
						s = Character.toString((char)whitePiece);
						intersections[m.getX()][m.getY()].setIcon(white);
						timeline.append(s);
						timeline.append((m.getX()+1) + ", " + (m.getY()+1) + "\n");
						turn++;
			}
		}
	}

	public void setBoard() { //Initializing board objects & icons
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				if(!isInit) {
					intersections[i][j] = new JButton();
				}
				if(i == 0) {
					if(j == 0) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/corner1.jpg"));
					}
					else if (j == 18) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/corner2.jpg"));
					}
					else {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/top.jpg"));
					}
				}
				
				else if (i == 18) {
					if(0 < j && j < 18) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/bottom.jpg"));
					}
					if(j == 0) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/corner3.jpg"));
					}
					if(j == 18) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/corner4.jpg"));
					}
				}
				
				else if(j == 0) {
					if(0 < i && i < 18) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/left.jpg"));
					}
				}
				else if(j == 18) {
					if(0 < i && i < 18	) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/right.jpg"));
					}
				}
				else if ( i == 3 || i == 9 || i == 15) {
					if(j == 3 || j == 9 || j == 15) {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/star.jpg"));
					}
					else {
						intersections[i][j].setIcon(new ImageIcon("PieceIcons/center.jpg"));
					}
				}
				else {
					intersections[i][j].setIcon(new ImageIcon("PieceIcons/center.jpg"));
				}
				
				
				intersections[i][j].setBorder(BorderFactory.createEmptyBorder());
				intersections[i][j].setVisible(true);
				//intersections[i][j].add(new JLabel(black));
			
				intersections[i][j].addActionListener(this);
				board.add(intersections[i][j]);
			}
		}
		isInit = true;
	}
	
	public void setHandicap(int h) {
		for(int i = 0; i < h; i++) {
			if(i == 1) {
				memory.add(new move(3, 3, true));
				memory.add(new move(15, 15, true));
			}
			if(i == 2) {
				memory.add(new move(3, 15, true));
			}
			if(i == 3) {
				memory.add(new move(15, 3, true));
			}
			if(i == 4) {
				memory.add(new move(9, 9, true));
			}
			if(i == 5) {
				memory.remove(4);
				memory.add(new move(9, 3, true));
				memory.add(new move(9, 15, true));
			}
			if(i == 6) {
				memory.add(new move(3, 9, true));
			}
			if(i == 7 ) {
				memory.add(new move(15, 9, true));
			}
			if(i == 8) {
				memory.add(new move(9, 9, true));
			}
		}
		
		loadMove();
		turn = 0;
	}


}