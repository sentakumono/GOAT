import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private static JPanel gameWindow, board, commentBox, sidebar,timelineBox, boardMargin;
	private static JPanel[][] intersections = new JPanel[18][18];
	private static GridBagConstraints gbc;
	private static JTextArea comment,timeline;
	private static JScrollPane scroll1,scroll2;
	private static ImageIcon black;
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
		board.setPreferredSize(new Dimension(774, 774));
		board.setBounds(50,50,774,774);
		board.setVisible(true);
		board.setEnabled(true);
		board.addMouseListener(this);
		board.setBackground(new Color(181,129,32));
		
		
		black = new ImageIcon("PieceIcons/GoStoneBlack.png");
		
		for(int i = 0; i < 18; i++) {
			for(int j = 0; j < 18; j++) {
				intersections[i][j] = new JPanel();
				intersections[i][j].setPreferredSize(new Dimension(42,42));
				intersections[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
				intersections[i][j].setVisible(true);
				//intersections[i][j].add(new JLabel(black));
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
		add(gameWindow);
		setVisible(true);
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
		
	}

	public void mouseMoved(MouseEvent arg0) {
		
	}

	public void mouseClicked(MouseEvent e) {
		printCoordinate(e);
	}


	public void mouseEntered(MouseEvent arg0) {
		
	}


	public void mouseExited(MouseEvent arg0) {

		
	}


	public void mousePressed(MouseEvent e) {
		//printQuadrant(e);
	}

	public void mouseReleased(MouseEvent e) {
		placePiece(e);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	void placePiece(MouseEvent e) {
		int x = (int)Math.floor((double)e.getX()/43);
		int y = (int)Math.floor((double)e.getY()/43);
		System.out.println(x + "," + y);
		intersections[x][y].add(new JLabel(black));
		
	}
	void printCoordinate(MouseEvent e) {
		Component c = board.getComponentAt(new Point(e.getX(), e.getY()));
		int mouseIndexX = (int)Math.floor(e.getX());
		int mouseIndexY = (int)Math.floor(e.getY());
		Dimension panelSize = c.getSize();
		
		timeline.append(((1 + (mouseIndexX+17)/panelSize.width)) + "," +(1 + (mouseIndexY+17)/panelSize.height) + "\n");
		
		
	}

}