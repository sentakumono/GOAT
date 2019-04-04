import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private static JPanel gameWindow, board, commentBox, sidebar,timelineBox, boardMargin, pieceLayer;
	private static JPanel[][] intersections = new JPanel[18][18];
	private static GridBagConstraints gbc;
	private static JTextArea comment,timeline;
	private static JScrollPane scroll1,scroll2;
	
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		super("GO");
		setSize(2000,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		gameWindow = new JPanel();
		gameWindow.setLayout(null);
		board = new JPanel(new GridLayout(18,18,0,0));
		board.setPreferredSize(new Dimension(773, 773));
		board.setBounds(50,50,773,773);
		board.setVisible(true);
		board.setEnabled(true);
		board.addMouseListener(this);
		board.setBackground(new Color(181,129,32));
		
		pieceLayer = new JPanel();
		pieceLayer.setBounds(50, 50,  773, 773);
		pieceLayer.setVisible(true);
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 18; j++) {
				intersections[i][j] = new JPanel();
				intersections[i][j].setPreferredSize(new Dimension(42,42));
				intersections[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				intersections[i][j].setVisible(true);
				//intersections[i][j].addMouseListener(this);
					
				intersections[i][j].setBackground(new Color(181, 129, 32));
				
				board.add(intersections[i][j]);
			}
		}
		boardMargin = new JPanel();
		boardMargin.setBounds(50, 50, 850, 850);
		boardMargin.setBackground(new Color(181, 129, 32));
		boardMargin.setVisible(true);
		boardMargin.setEnabled(true);
		boardMargin.setPreferredSize(new Dimension(850, 850));
		//addConstraint(boardMargin, board, 18, 18, 18, 18, GridBagConstraints.BOTH, GridBagConstraints.PAGE_END);
		
		

		gameWindow = new JPanel(new FlowLayout(1,30,40));
		sidebar = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

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
		gameWindow.add(board);
		sidebar.setVisible(true);
		
		//gameWindow.add(boardMargin);
		gameWindow.add(sidebar);
		gameWindow.add(board);
		gameWindow.add(pieceLayer);
		add(gameWindow);
		setVisible(true);
		System.out.println(intersections[1][1].getSize());
	}

	public void addConstraint(JPanel p, Component c, int x, int y, int w, int h, int f, int a) {
		gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = f;
		gbc.anchor = a;

		p.add(c, gbc);
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		printQuadrant(e);
		placePiece(e);
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//printQuadrant(e);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	void placePiece(MouseEvent e) {
		JLabel piece = new JLabel();
		
		Component c = board.getComponentAt(new Point(e.getX(), e.getY()));
		int mouseIndexX = ((int)Math.floor(e.getX()) + 17)/ c.getSize().width;
		int mouseIndexY = ((int)Math.floor(e.getY()) + 17)/ c.getSize().height;
		
		if(mouseIndexX < 18 ||mouseIndexY < 18) {
			
			piece.setIcon(new ImageIcon("PieceIcons/GoStoneBlack.png"));
			piece.setLocation(c.getLocation().x, c.getLocation().y);
		}
		else{
			if(mouseIndexX >= 18 && mouseIndexY <18){
				
			}
			if(mouseIndexX < 18 && mouseIndexY >=18){
				
			}
			if(mouseIndexX >= 18 && mouseIndexY >=18){
				
			}
		}
		
		pieceLayer.add(piece);
		
	}
	void printQuadrant(MouseEvent e) {
		Component c = board.getComponentAt(new Point(e.getX(), e.getY()));
		int mouseIndexX = (int)Math.floor(e.getX());
		int mouseIndexY = (int)Math.floor(e.getY());
		Dimension panelSize = c.getSize();
		Point panelQuad = c.getLocation();
		if(mouseIndexX < (panelSize.width/2) + panelQuad.x && mouseIndexY < (panelSize.height/2) + panelQuad.y) {
			System.out.println("The mouse is in the first quadrant");
		}
		if(mouseIndexX > (panelSize.width/2) + panelQuad.x && mouseIndexY < (panelSize.height/2) + panelQuad.y) {
			System.out.println("The mouse is in the second quadrant");
		}
		if(mouseIndexX < (panelSize.width/2) + panelQuad.x && mouseIndexY > (panelSize.height/2) + panelQuad.y) {
			System.out.println("The mouse is in the third quadrant");
		}
		if(mouseIndexX > (panelSize.width/2) + panelQuad.x && mouseIndexY > (panelSize.height/2) + panelQuad.y) {
			System.out.println("the mouse is in the fourth quadrant");
		}
		
		timeline.append(((1 + (mouseIndexX+17)/panelSize.width)) + "," +(1 + (mouseIndexY+17)/panelSize.height) + "\n");
		
		
	}

}