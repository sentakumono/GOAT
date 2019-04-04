import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.lang.NullPointerException;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener, MouseListener, MouseMotionListener{

	private static JPanel gameWindow, board, boardMargin;
	private static JPanel[][] intersections = new JPanel[18][18];
	private static GridBagConstraints gbc;
	public static void main(String[] args) {
		new GUI();
	}
	
	public GUI(){
		super("GO");
		setSize(1000,500);
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
		
		
		//gameWindow.add(boardMargin);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseClicked(MouseEvent e) {
		printQuadrant(e);
		
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
		
		System.out.println(((mouseIndexX+17)/42) + "," +((mouseIndexY+17)/42));
		
		
	}

}