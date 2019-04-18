import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private static JPanel gameWindow, board, commentBox, sidebar,timelineBox, boardMargin;
	private static JButton[][] intersections = new JButton[19][19];
	private static GridBagConstraints gbc;
	private static JTextArea comment,timeline;
	private static JScrollPane scroll1,scroll2;
	private static ImageIcon black, white;
	public static int turn;
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		super("GO");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		
		gameWindow = new JPanel();
		gameWindow.setLayout(null);
		board = new JPanel(new GridLayout(19,19,0,0));
		board.setPreferredSize(new Dimension(595, 595));
		board.setBounds(50,50,595,595);
		board.setVisible(true);
		board.setEnabled(true);
		board.addMouseListener(this);
		board.setBackground(new Color(181,129,32));
				
		black = new ImageIcon("PieceIcons/blackCenter.jpg");
		white = new ImageIcon("PieceIcons/whiteCenter.jpg");
		
		turn = 0;
		
		for(int i = 0; i < 19; i++) {
			for(int j = 0; j < 19; j++) {
				intersections[i][j] = new JButton();
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

		boardMargin = new JPanel();
		boardMargin.setBounds(50, 50, 850, 850);
		boardMargin.setBackground(new Color(181, 129, 32));
		boardMargin.setPreferredSize(new Dimension(850, 850));
		//addConstraint(boardMargin, board, 1, 1, 1, 1, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
		
		

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
		
		sidebar.setVisible(true);
		gameWindow.add(board);
		//gameWindow.add(boardMargin);
		gameWindow.add(sidebar);
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
	}


	public void mouseEntered(MouseEvent arg0) {
		
	}


	public void mouseExited(MouseEvent arg0) {

		
	}


	public void mousePressed(MouseEvent e) {
		//printQuadrant(e);
	}

	public void mouseReleased(MouseEvent e) {

		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int whitePiece = 0x26AA;
		int blackPiece = 0x26AB;
		String s;
		for(int i=0;i<19;i++) {
			for(int j = 0; j < 19; j++) {
				if(arg0.getSource()==intersections[i][j]) {
					if(turn%2 == 0) {
						s = Character.toString((char)blackPiece);
						intersections[i][j].setIcon(black);
					}
					else {
						s = Character.toString((char)whitePiece);
						intersections[i][j].setIcon(white);
					}
					timeline.append(s);
					timeline.append((i+1) + ", " + (j+1) + "\n");
					turn++;
				}
			}
		}
	}
	


}