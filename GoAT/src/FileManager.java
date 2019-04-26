import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class FileManager extends JFrame implements ActionListener {
	private int index;
	private String filepath;
	
	public FileManager() {
		 filepath = "SaveGame.txt";
		 
	}
	
	public void initiate() { //asks user for game & player information to append to file
		JTextField name = new JTextField(5);
		JTextField ruleset = new JTextField(5);
		JTextField blackName = new JTextField(5);
		JTextField whiteName = new JTextField(5);
		JTextField timer = new JTextField(5);
		JTextField handicap = new JTextField(5);
			
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel("Game Name: \n"));
		p.add(name);
		p.add(new JLabel(" "));
		p.add(new JLabel("Black Player: \n"));
		p.add(blackName);
		p.add(new JLabel(" "));
		p.add(new JLabel("White Player: \n"));
		p.add(whiteName);
		p.add(new JLabel(" "));
		p.add(new JLabel("Handicap: "));
		p.add(handicap);
		
		int result = JOptionPane.showConfirmDialog(null, p, " ", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
//			IO.println("GN[" + name.getText() + "]");
//			IO.println("PB[" + blackName.getText() + "]");
//			IO.println("HA[" + handicap.getText() + "]");
//			IO.println("PW[" + whiteName.getText() + "]");
//			IO.println("KM[7.5]");
//			IO.println("RU[Chinese] \n");
		}
		else if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

	/*
	 * Issue:
	 * This algorithm only checks the first instance of ;B[ in the document
	 * - Skips white move in the same line, as it must return one object
	 * - isn't progressing to the next line and is getting stuck in while loop in GUI
	 */
	public ArrayList load() throws IOException{
		ArrayList<move> a = new ArrayList();
		IO.openInputFile(filepath);
		String line = IO.readLine();
		System.out.println(line);
		while(line != null) {
			System.out.println(line);
			if(line.contains(";B[")) {
				char x = line.charAt(line.indexOf("B") + 2);
				char y = line.charAt(line.indexOf("B") + 3);
				boolean c = true;
				move m = sgfRev(x, y, c);
				
				a.add(m);
			}
			if(line.contains(";W[")) {
				char x = line.charAt(line.indexOf("W") + 2);
				char y = line.charAt(line.indexOf("W") + 3);
				boolean c = false;
				move m = sgfRev(x, y, c);
				
				a.add(m); 
			}
			line = IO.readLine();
		}
		IO.closeInputFile();
		return a;
		
	}
	
	public void save(ArrayList a) { //appends moves made to .sgf formatted text document
		IO.createOutputFile(filepath);
		IO.println("(;FF[4]GM[1]SZ[19]");
		int x, y;
		move o;
		boolean c;
		for(int i = 0; i < a.size(); i++) {
			o = (move)a.get(i);
			x = o.getX();
			y = o.getY();
			c = o.colour();
			
			if(c) {
				IO.print("(;B[" + sgf(x, y) + "]");
			}
			else {
				IO.println(";W[" + sgf(x, y) + "]");
			}
		}
	}
	
	public void exit() {
		int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", " ", JOptionPane.OK_CANCEL_OPTION);
		if(r == JOptionPane.OK_OPTION) {
			try{
				IO.closeOutputFile();
			}catch(NullPointerException e) {
				
			}
			System.exit(0);
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	public move sgfRev(char x, char y, boolean c) { 
		int xInt, yInt;
		xInt = (int) x;
		xInt -= 97;
		
		yInt = (int) y;
		yInt -= 97;
		
		move m = new move(xInt, yInt, c);
		return m;
	}
	public String sgf(int x, int y) {
		char xChar = 'a';
		char yChar = 'a';
		
		xChar += x;
		yChar += y;
		
		String c = "";
		c += xChar;
		c += yChar;
		return c;
	}
}
