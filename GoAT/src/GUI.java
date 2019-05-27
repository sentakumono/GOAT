    
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import java.util.*;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener, KeyListener {

	private static JPanel gameWindow, board, commentPanel, sidebar,timelineBox, controlPanel;
	private JButton[][] intersections = new JButton[19][19];
	private JButton undo, redo, commentButton;
	private JRadioButton nullBlack, nullWhite, normalPiece;
	private JTextArea commentInput;
	private static JTextArea timeline;
	private static JScrollPane scroll1, scroll2;
	private static ImageIcon center,star,black,white;
	private static ImageIcon[] corners, sides, blackCorners, whiteCorners,blackSides, whiteSides;
	private static JMenuBar menuBar,toolBar;
	private static JMenu file, view, game,showHide;
	private static JMenuItem newGame, save, saveAs, load, open, exit,coordinates, moveNum, timelineButton, toolbar, tutorial, boardSize, handicap, score, info, viewSave;
	private static JLabel turnTimer;
	public int turn;
	public static FileManager files;
	public ArrayList<move> memory = new ArrayList<>();
	public ArrayList<move>undoMove = new ArrayList<>();
	public move m;
	public static boolean isInit = false, hasUndone = false, userSaved = false;
	public static boolean[][] checked=new boolean[19][19];
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		super("GO");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		//initialize game information 
		files = new FileManager();		
		
		//Dropdown menus
		controlPanel= new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		//controlPanel.setBackground(new Color(240, 240, 240));
		controlPanel.setBorder(BorderFactory.createSoftBevelBorder(0));
		menuBar=new JMenuBar();
		
		file=new JMenu("File");
		newGame= new JMenuItem("New");
		newGame.addActionListener(this);
		save= new JMenuItem("Save");
		save.addActionListener(this);
		saveAs= new JMenuItem("Save as");
		saveAs.addActionListener(this);
		load= new JMenuItem("Load Recent");
		load.addActionListener(this);
		if(files.readFilepath() == null) {
			load.setEnabled(false);
		}
		open = new JMenuItem("Open");
		open.addActionListener(this);
		exit= new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(newGame);
		file.add(save);
		file.add(saveAs);
		file.add(load);
		file.add(open);
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
		viewSave = new JMenuItem("View Save");
		viewSave.addActionListener(this);
		
		showHide.add(coordinates);
		showHide.add(moveNum);
		showHide.add(timelineButton);
		showHide.add(toolbar);
		view.add(showHide);
		view.add(info);
		view.add(viewSave);
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
		board = new JPanel(new GridLayout(19,19,0,0));
		board.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		board.setPreferredSize(new Dimension(595, 595));
		board.setBounds(50,50,595,595);
		board.setVisible(true);
		board.setEnabled(true);
		board.setBackground(new Color(181,129,32));
		board.setCursor(new Cursor(Cursor.HAND_CURSOR));
		board.addKeyListener(this);
		board.setFocusable(true);
		
		center = new ImageIcon("PieceIcons/center.jpg");
		star = new ImageIcon("PieceIcons/star.jpg");
		black = new ImageIcon("PieceIcons/blackCenter.jpg");
		white = new ImageIcon("PieceIcons/whiteCenter.jpg");
		
		corners = new ImageIcon[4];
		sides= new ImageIcon[4];
		blackCorners=new ImageIcon[4];
		whiteCorners=new ImageIcon[4];
		blackSides=new ImageIcon[4];
		whiteSides=new ImageIcon[4];
		
		for(int i=0;i<4;i++) {
			corners[i]= new ImageIcon("PieceIcons/corner"+ (i+1) +".jpg");
			sides[i]= new ImageIcon("PieceIcons/side"+ (i+1) +".jpg");
			blackCorners[i]= new ImageIcon("PieceIcons/blackCorner"+ (i+1) +".jpg");
			whiteCorners[i]= new ImageIcon("PieceIcons/whiteCorner"+ (i+1) +".jpg");
			blackSides[i]= new ImageIcon("PieceIcons/blackSide"+ (i+1) +".jpg");
			whiteSides[i]= new ImageIcon("PieceIcons/whiteSide"+ (i+1) +".jpg");
		}
		
		turn = 0;
		
		gameWindow = new JPanel(new FlowLayout(1,30,40));
		gameWindow.setFocusable(true);
		gameWindow.addKeyListener(this);
		sidebar = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		
		//timeline and comments text fields
		commentPanel = new JPanel();
		commentPanel.setPreferredSize(new Dimension(300, 125));
		commentPanel.setVisible(true);
		commentPanel.setBackground(Color.WHITE);
		commentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		commentInput = new JTextArea(5, 25);
		commentInput.setEditable(true);
		commentInput.setVisible(true);
		commentInput.setMargin(new Insets(0, 2, 0, 0));
		commentInput.setLineWrap(true);
		commentInput.addKeyListener(this);
		commentPanel.add(commentInput);
		scroll1 = new JScrollPane(commentInput);
		scroll1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		commentPanel.add(scroll1);
		commentButton = new JButton();
		commentButton.setPreferredSize(new Dimension(125, 25));
		commentButton.setBorder(BorderFactory.createBevelBorder(0));
		commentButton.addActionListener(this);
		commentButton.setText("Add Comment");
		commentPanel.add(commentButton);
		
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		sidebar.add(commentPanel,c);
		
		timelineBox = new JPanel();
		timelineBox.setPreferredSize(new Dimension(300,400));
		timelineBox.setVisible(true);
		timelineBox.setBackground(Color.WHITE);
		timelineBox.setBorder(BorderFactory.createLineBorder(Color.black));
		timeline = new JTextArea(25,25);
		timeline.setEditable(false);
		timeline.setVisible(true);
		timeline.setMargin(new Insets(0, 2, 0, 0));
		scroll2= new JScrollPane(timeline);
		scroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll2.setVisible(true);
		timelineBox.add(scroll2);
		
		//undo and redo buttons
		undo = new JButton();
		undo.setPreferredSize(new Dimension(25, 25));
		undo.setBorder(BorderFactory.createBevelBorder(0));
		undo.addActionListener(this);
		int undoArrow = 8630;
		char u = (char) undoArrow;
		undo.setText(Character.toString(u));
		timelineBox.add(undo);
		
		redo = new JButton();
		redo.setPreferredSize(new Dimension(25, 25));
		redo.setBorder(BorderFactory.createBevelBorder(0));
		redo.addActionListener(this);
		int redoArrow = 8631;
		char r = (char) redoArrow;
		redo.setText(Character.toString(r));
		timelineBox.add(redo);
		
		nullBlack = new JRadioButton();
		int blackPiece = 0x26AB;
		char b = (char) blackPiece;
		nullBlack.setText(Character.toString(b));
		nullBlack.addActionListener(this);
		nullWhite = new JRadioButton();
		int whitePiece = 0x26AA;
		char w = (char) whitePiece;
		nullWhite.setText(Character.toString(w));
		nullWhite.addActionListener(this);
		
		normalPiece = new JRadioButton();
		normalPiece.setText("x");
		normalPiece.addActionListener(this);

		ButtonGroup radio = new ButtonGroup();
		radio.add(nullBlack);
		radio.add(nullWhite);
		radio.add(normalPiece);
		timelineBox.add(nullBlack);
		timelineBox.add(nullWhite);
		timelineBox.add(normalPiece);
		
		//dimensions of timeline panel
		c.ipady = 40;      //make this component tall
		c.weightx = 0.5;
		c.insets = new Insets(10,0,0,0);
		c.gridx = 0;
		c.gridy = 1;
		sidebar.add(timelineBox,c);


		
		//initialize board
		setBoard();
		setHandicap(files.getHA());
		
		gameWindow.setVisible(true);
		gameWindow.setFocusable(true);
		gameWindow.addKeyListener(this);
		gameWindow.setBorder(BorderFactory.createBevelBorder(0));
		
		sidebar.setVisible(true);
		gameWindow.add(board);
		gameWindow.add(sidebar);
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(gameWindow, BorderLayout.CENTER);
		setVisible(true);
	
	}
	
 
	public void actionPerformed(ActionEvent e) {
		//User saves to default directory
		if(e.getSource() == save) { 
			files.save(memory);
			userSaved = true;
			load.setEnabled(true);
		}
		
		//User chooses the directory in which to save the game
		if(e.getSource() == saveAs) { 
			JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			
			fc.setDialogTitle("Choose a directory to save to: ");
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = fc.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				if(fc.getSelectedFile().isDirectory()) {
					files.setFilepath(fc.getSelectedFile().getPath() + "/" + files.getFilepath().getName());
					files.save(memory);
					userSaved = true;
				}
			}
		}
		
		//User loads save game from previous file path
		if(e.getSource() == load) {
			timeline.setText("");
			try{
				memory = files.load();
				if(memory == null) {
					commentInput.setText("***Cannot open file***");
					newGame.doClick();
				}
			}catch(IOException j){				
			}
			undoMove.clear();
			setBoard();
			turn = 0;
			loadMove();
			
		}
		
		//User chooses a saved file to load
		if(e.getSource() == open) {
			JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fc.setDialogTitle("Choose a file to open: ");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fc.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION) {
				if(fc.getSelectedFile().isFile() && fc.getSelectedFile().getPath().contains(".txt")) {
					files.setFilepath(fc.getSelectedFile());
					load.doClick();
				}
			}
		}
		
		//User restarts game
		if(e.getSource() == newGame) { 
			files.saveFilepath();
			memory.clear();
			undoMove.clear();
			commentInput.setText("");
			commentInput.setText("");
			timeline.setText("");
			turnTimer.setText("Moves: 0");
			turn = 0;
			userSaved = false;
			if(files.readFilepath() == null) {
				load.setEnabled(false);
			}
			setBoard();
			
			files = new FileManager();
			setHandicap(files.getHA());
		}
		
		//User opens game info menu
		if(e.getSource() == info) { 
			JPanel editPanel = new JPanel();
			editPanel.setLayout(new GridLayout(2, 2));
			editPanel.setBorder(BorderFactory.createBevelBorder(0));
			
			JPanel titlePanel = new JPanel();
			titlePanel.setBorder(BorderFactory.createTitledBorder("Game Name "));
			JTextField title = new JTextField(20);
			title.setText(files.getGN());
			titlePanel.add(title);
			editPanel.add(titlePanel);
			
			JPanel playerInfo = new JPanel();
			playerInfo.setPreferredSize(new Dimension(125, 100));
			playerInfo.setBorder(BorderFactory.createTitledBorder("Player Info"));
			playerInfo.add(new JLabel("Black Player Name: "));
			JTextField bPlayer = new JTextField(15);
			bPlayer.setText(files.getBN());
			playerInfo.add(bPlayer);
			playerInfo.add(new JLabel("White Player Name: "));
			JTextField wPlayer = new JTextField(15);
			wPlayer.setText(files.getWN());
			playerInfo.add(wPlayer);
			editPanel.add(playerInfo);
			
			JPanel gameInfo = new JPanel();
			gameInfo.setPreferredSize(new Dimension(100, 125));
			gameInfo.setBorder(BorderFactory.createTitledBorder("Game Info"));
			gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));
			gameInfo.add(new JLabel("Ruleset: Chinese"));
			gameInfo.add(new JLabel("Handicap: " + files.getHA()));
			gameInfo.add(new JLabel("Komi: 7.5"));
			editPanel.add(gameInfo);
			
			int result = JOptionPane.showConfirmDialog(null, editPanel, " ", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION) {
				String gameName = title.getText();
				String blackName = bPlayer.getText();
				String whiteName = wPlayer.getText();
				if(gameName != null && gameName.length() < 50)
					files.setGN(gameName);
				if(blackName != null && blackName.length() < 20)
					files.setBN(blackName);
				if(whiteName != null && whiteName.length() < 20)
					files.setWN(whiteName);
			}
		}
		
		//Window to view the save file in the .sgf format
		if(e.getSource() == viewSave)  {
			try{
				files.read();
			}catch(IOException j) {
				JOptionPane.showMessageDialog(null, "Save file load failed.");
			}
		}
		
		//Uses the undo button
		if(e.getSource() == undo) {
			undo();
			hasUndone = true;
		}
		
		//uses the redo button
		if(e.getSource() == redo) {
			redo();
		}
		
		//appends a comment to a move 
		if(e.getSource() == commentButton) {
			if(commentInput.getText() != null) {
				String com = commentInput.getText();
				if(com.contains("]")) {
					commentInput.setText("Invalid Comment!");
				}

				else if(!memory.isEmpty())  {
					memory.get(memory.size()-1).addComment(com);
				}
			}
		}
		 
		//close output file and close program
		if(e.getSource() == exit) { 
			files.exit();
		}
		//check the board for an action
		setPiece(e);
		
		turnTimer.setText("Moves: " + turn);
	}

	//Basic key input
	/*
	 * Note: Keyboard input is only recognized when the comment box is selected,
	 * 		 due to it being the only focusable object in the GUI
	 */
	public void keyPressed(KeyEvent e) {
		//Pressing enter in the comment input box appends the comment
		if(e.getSource() == commentInput) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				commentButton.doClick(); 
			}
		}
		//control + z undos, control + y redos
		if(e.isControlDown()) {
			if(e.getKeyCode() == KeyEvent.VK_Z) 
				undo.doClick();
			else if(e.getKeyCode() == KeyEvent.VK_Y)
				redo.doClick();
		}
		
		
	}

	public void keyReleased(KeyEvent e) {
				
	}
	public void keyTyped(KeyEvent e) {
				
	}

	//store move made into memory
	public void storeMove(int x, int y, boolean c) { 
 		m = new move(x, y, c);
		memory.add(m); 
	}
	
	public void setPiece(ActionEvent e) {
		int whitePiece = 0x26AA;
		int blackPiece = 0x26AB;
		String s = "";
		boolean c = true;
		for(int i=0;i<19;i++) { 
			for(int j = 0; j < 19; j++) {
				if(emptyGrid(i, j)) {				
					if(e.getSource()==intersections[i][j]) { //check each piece if it's been clicked
						
						if(turn%2 == 0) { //if it is black's turn
							s = Character.toString((char)blackPiece); //unicode character
							addBlackPiece(i, j);
							c = true;
						}
						else if(turn%2 != 0) {			  //if it is white's turn
							s = Character.toString((char)whitePiece);
							addWhitePiece(i, j);
							c = false;
						}
						
						resetChecked();
						if(getColor(i-1,j)==turn%2&&!hasLiberty(i-1,j,turn%2))
							eatPieces(i-1,j,turn%2);
						
						resetChecked();
						if(getColor(i+1,j)==turn%2&&!hasLiberty(i+1,j,turn%2)) 
							eatPieces(i+1,j,turn%2);
						
						resetChecked();
						if(getColor(i,j-1)==turn%2&&!hasLiberty(i,j-1,turn%2)) 
							eatPieces(i,j-1,turn%2);
						
						resetChecked();
						if(getColor(i,j+1)==turn%2&&!hasLiberty(i,j+1,turn%2)) 
							eatPieces(i,j+1,turn%2);
						
						resetChecked();
						
						if(hasLiberty(i, j, 1-turn%2)) {
							commentInput.setText("");
							timeline.append(s);
							timeline.append((i+1) + ", " + (j+1) + "\n");
							turn++;
							storeMove(i, j, c);
							if(hasUndone) { //if a move is made after an undo, override the moves made previously
								undoMove.clear();
								hasUndone = false;
							}
						}
						else {
							setEmpty(i, j);
						}
					}
				}
			}
		}
	}
	
	//override method to manually place pieces using coordinates and colour
	public void setPiece(int i, int j, boolean c) {
		int whitePiece = 0x26AA;
		int blackPiece = 0x26AB;
		String s = "";
		if(c) { //if it is black's turn
			s = Character.toString((char)blackPiece); //unicode character
			addBlackPiece(i, j);
			c = true;
		}
		else {			  //if it is white's turn
			s = Character.toString((char)whitePiece);
			addWhitePiece(i, j);
			c = false;
		}
		resetChecked();
		if(getColor(i-1,j)==turn%2&&!hasLiberty(i-1,j,turn%2))
			eatPieces(i-1,j,turn%2);
		
		resetChecked();
		if(getColor(i+1,j)==turn%2&&!hasLiberty(i+1,j,turn%2)) 
			eatPieces(i+1,j,turn%2);
		
		resetChecked();
		if(getColor(i,j-1)==turn%2&&!hasLiberty(i,j-1,turn%2)) 
			eatPieces(i,j-1,turn%2);
		
		resetChecked();
		if(getColor(i,j+1)==turn%2&&!hasLiberty(i,j+1,turn%2)) 
			eatPieces(i,j+1,turn%2);
		
		resetChecked();
		
		if(hasLiberty(i, j, 1-turn%2)) {
			commentInput.setText("");
			
			String com = "";
			if(!memory.isEmpty()) {
				com = memory.get(memory.size()-1).getComment();
			}
			commentInput.setText(com);
			timeline.append(s);
			timeline.append((i+1) + ", " + (j+1) + "\n");

			turn++;
		}
		else {
			setEmpty(i, j);
		}
	}
	
	//loads handicap and moves made from memory
	public void loadMove() {
		for(int i = 0; i < files.getHA(); i++) {
			turn = 0;
			setPiece(memory.get(i).getX(), memory.get(i).getY(), memory.get(i).colour());
		}
		for(move m : memory) { //foreach loop to iterate on every move element
			if(memory.indexOf(m) >= files.getHA())
				setPiece(m.getX(), m.getY(), m.colour());
		}
	}

	//undoes the last move made
	public void undo() {
		if(!memory.isEmpty() && memory.size() -1 >= files.getHA()) {
			turn = 0;
			undoMove.add(memory.get(memory.size()-1));
			memory.remove(memory.size()-1);
			timeline.setText("");
			setBoard();
			loadMove();
		}
	}	
	
	//re-does the last move made
	public void redo() {
		if(!undoMove.isEmpty()) {
			turn = 0;
			memory.add(undoMove.get(undoMove.size()-1));
			undoMove.remove(undoMove.size()-1);
			timeline.setText("");
			setBoard();
			loadMove();
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
						intersections[i][j].setIcon(corners[0]);
					}
					else if (j == 18) {
						intersections[i][j].setIcon(corners[1]);
					}
					else {
						intersections[i][j].setIcon(sides[2]);
					}
				}
				
				else if (i == 18) {
					if(0 < j && j < 18) {
						intersections[i][j].setIcon(sides[3]);
					}
					if(j == 0) {
						intersections[i][j].setIcon(corners[2]);
					}
					if(j == 18) {
						intersections[i][j].setIcon(corners[3]);
					}
				}
				
				else if(j == 0) {
					if(0 < i && i < 18) {
						intersections[i][j].setIcon(sides[0]);
					}
				}
				else if(j == 18) {
					if(0 < i && i < 18	) {
						intersections[i][j].setIcon(sides[1]);
					}
				}
				else if ( i == 3 || i == 9 || i == 15) {
					if(j == 3 || j == 9 || j == 15) {
						intersections[i][j].setIcon(star);
					}
					else {
						intersections[i][j].setIcon(center);
					}
				}
				else {
					intersections[i][j].setIcon(center);
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
	
	//adds handicap pieces to memory
	public void setHandicap(int h) {
		for(int i = 0; i < h; i++) {
			if(i == 1) {
				memory.add(new move(3, 3, true));
				memory.add(new move(15, 15, true));
			}
			if(i == 2) 
				memory.add(new move(3, 15, true));
			if(i == 3) 
				memory.add(new move(15, 3, true));
			if(i == 4) 
				memory.add(new move(9, 9, true));
			if(i == 5) {
				memory.remove(4);
				memory.add(new move(9, 3, true));
				memory.add(new move(9, 15, true));
			}
			if(i == 6) 
				memory.add(new move(3, 9, true));
			if(i == 7 ) 
				memory.add(new move(15, 9, true));
			if(i == 8) 
				memory.add(new move(9, 9, true));
		}
		loadMove();
	}

	public void resetChecked() {
		for(int i=0;i<19;i++) {
			for(int j=0;j<19;j++) {
				checked[i][j]=false;
			}
		}
	}
	
	//checks if a piece or group of pieces has open liberties
	public boolean hasLiberty(int i, int j, int type) {
		
		if(i<0||j<0||j>18||j>18) 
			return false;
		if(emptyGrid(i,j)) 
			return true;
		if(getColor(i,j)!=type) 
			return false;
		checked[i][j]=true;
		 if(i > 0 &&!checked[i-1][j]&& hasLiberty(i-1, j,type)) 
			 return true;  
		    else if(i < 18 && !checked[i+1][j]&&hasLiberty(i+1, j,type)) 
		    	return true; 
		    else if(j > 0 &&!checked[i][j-1]&&  hasLiberty(i, j-1,type)) 
		    	return true;  
		    else if(j < 18 &&!checked[i][j+1]&& hasLiberty(i, j+1,type)) 
		    	return true;  
		    else return false;  
	}
	
	//removes pieces if they do not have liberties
	public void eatPieces(int i, int j, int type)  {  
	 if(getColor(i,j)!=type||i<0||i>18||j<0||j>18) 
	 	return;
	 
	    setEmpty(i,j);
	   
	    if(i >= 0) 
	    	eatPieces(i-1, j, type);  
	    if(i <= 18) 
	    	eatPieces(i+1, j,type); 
	    if(j >= 0) 
	    	eatPieces(i, j-1,type);  
	    if(j <= 18) 
	    	eatPieces(i, j+1,type); 
	}  

	//check if a given piece is black
	public boolean black(int i, int j) {
		boolean isBlack=false;
		Icon icon=intersections[i][j].getIcon();
		if(icon == black) {
			isBlack=true;
		}
		for(int p=0;p<4;p++) {
			if(icon.equals(blackCorners[p])||icon.equals(blackSides[p])) {
				isBlack=true;
				break;
			}
		}
		return isBlack;
	}
	
	//check if a given piece is white
	public boolean white(int i, int j) {
		boolean isWhite=false;
		Icon icon=intersections[i][j].getIcon();
		if(icon == white) {
			isWhite=true;
		}
		for(int p=0;p<4;p++) {
			if(icon.equals(whiteCorners[p])||icon.equals(whiteSides[p])) {
			
				isWhite=true;
				break;
			}
		}
		return isWhite;
	}
	
	public int getColor(int i, int j) {
		if(i>=0&&i<=18&&j>=0&&j<=18)  {
			if(black(i,j)) 
					return 1;
			else if(white(i,j)) 
					return 0;
			else
				return -1;
		}
		else return -1;
	}
	//check if the given grid space is empty
	public boolean emptyGrid(int i, int j) {
		boolean empty=false;
		Icon icon=intersections[i][j].getIcon();
		for(int p=0;p<4;p++)
			if(icon.equals(corners[p])||icon.equals(sides[p])) 
				empty=true;
			
		if(icon.equals(center)) 
			empty=true;
		else if(icon.equals(star)) 
			empty=true;
		return empty;
	}
	
	//adds black piece based on location on board
	public void addBlackPiece(int i,int j) {
		if(i==0&&j==0) {
			intersections[i][j].setIcon(blackCorners[0]);
			}
			else if(i==0&&j==18) {
				intersections[i][j].setIcon(blackCorners[1]);
			}
			else if(i==0) {
				intersections[i][j].setIcon(blackSides[2]);
			}
			else if(j==0&&0 < i && i < 18) {
				intersections[i][j].setIcon(blackSides[0]);
			}
			else if(j==18&&0 < i && i < 18) {
				intersections[i][j].setIcon(blackSides[1]);
			}
			else if(i==18&&j==0) {
				intersections[i][j].setIcon(blackCorners[2]);
			}
			else if(i==18&&j==18) {
				intersections[i][j].setIcon(blackCorners[3]);
			}
			else if(i==18) {
				intersections[i][j].setIcon(blackSides[3]);
			}
			else {
			intersections[i][j].setIcon(black);
			}
	}
	
	public void addWhitePiece(int i, int j) {
		if(i==0&&j==0) {
			intersections[i][j].setIcon(whiteCorners[0]);
			}
			else if(i==0&&j==18) {
				intersections[i][j].setIcon(whiteCorners[1]);
			}
			else if(i==0) {
				intersections[i][j].setIcon(whiteSides[2]);
			}
			else if(j==0&&0 < i && i < 18) {
				intersections[i][j].setIcon(whiteSides[0]);
			}
			else if(j==18&&0 < i && i < 18) {
				intersections[i][j].setIcon(whiteSides[1]);
			}
			else if(i==18&&j==0) {
				intersections[i][j].setIcon(whiteCorners[2]);
			}
			else if(i==18&&j==18) {
				intersections[i][j].setIcon(whiteCorners[3]);
			}
			else if(i==18) {
				intersections[i][j].setIcon(whiteSides[3]);
			}
			else {
			intersections[i][j].setIcon(white);
			}
	}
	public void setEmpty(int i, int j) {
		if(i == 0) {
			if(j == 0) {
				intersections[i][j].setIcon(corners[0]);
			}
			else if (j == 18) {
				intersections[i][j].setIcon(corners[1]);
			}
			else {
				intersections[i][j].setIcon(sides[2]);
			}
		}
		
		else if (i == 18) {
			if(0 < j && j < 18) {
				intersections[i][j].setIcon(sides[3]);
			}
			if(j == 0) {
				intersections[i][j].setIcon(corners[2]);
			}
			if(j == 18) {
				intersections[i][j].setIcon(corners[3]);
			}
		}
		
		else if(j == 0) {
			if(0 < i && i < 18) {
				intersections[i][j].setIcon(sides[0]);
			}
		}
		else if(j == 18) {
			if(0 < i && i < 18	) {
				intersections[i][j].setIcon(sides[1]);
			}
		}
		else if ( i == 3 || i == 9 || i == 15) {
			if(j == 3 || j == 9 || j == 15) {
				intersections[i][j].setIcon(star);
			}
			else {
				intersections[i][j].setIcon(center);
			}
		}
		else {
			intersections[i][j].setIcon(center);
		}
	}

	public static boolean getInit() {
		return isInit;
	}
	
	public static boolean hasUserSaved() {
		return userSaved;
	}

	public static void setUserSaved(boolean userSaved) {
		GUI.userSaved = userSaved;
	}
	
}
