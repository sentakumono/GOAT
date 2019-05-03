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
	private String filepath, n, b, w;
	private int h;
	
	public FileManager() {
		 filepath = "SaveGame.sgf";
		 
	}
	
	public void initiate() { //asks user for game & player information to append to file
		JTextField name = new JTextField(4);
		JTextField ruleset = new JTextField(4);
		JTextField blackName = new JTextField(4);
		JTextField whiteName = new JTextField(4);
		JTextField timer = new JTextField(4);
		SpinnerModel j = new SpinnerNumberModel(0, 0, 9, 1);
		JSpinner handicap = new JSpinner(j);
	
		
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(new JLabel("Game Name: \n"));
		p.add(name);
		p.add(new JLabel(" "));
		p.add(new JLabel("Black Player: \n"));
		p.add(blackName);
		p.add(new JLabel(" "));
		p.add(new JLabel("Black Handicap: "));
		p.add(handicap);
		p.add(new JLabel(" "));
		p.add(new JLabel("White Player: \n"));
		p.add(whiteName);
		
		
		int result = JOptionPane.showConfirmDialog(null, p, " ", JOptionPane.OK_CANCEL_OPTION);
		
		if(result == JOptionPane.OK_OPTION) {
		if(name.getText().length() < 20)	
			n = name.getText();
		if(blackName.getText().length() < 20)
			b = blackName.getText();
		if(whiteName.getText().length() < 20)
			w = whiteName.getText();
			h = (int)handicap.getValue();
		}
		else if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}

	public ArrayList load() throws IOException{
		ArrayList<move> a = new ArrayList();
		IO.openInputFile(filepath);
		String line = IO.readLine();
		while(line != null) {
			System.out.println(line);
			if(line.contains("GN["))  //pulls game information from save file
				n = line.substring(line.indexOf("GN") + 3, line.indexOf("]"));
			if(line.contains("PB[")) 
				b = line.substring(line.indexOf("PB") + 3, line.indexOf("]"));
			if(line.contains("HA[")) 
				h = line.charAt(line.indexOf("[") + 1);
			if(line.contains("PW[")) 
				w = line.substring(line.indexOf("PW") + 3, line.indexOf("]"));
				
			if(line.contains(";B[")) { //checks if a black move was made on this line
				char x = line.charAt(line.indexOf("B") + 2); //takes alphabetical coordinates
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
		IO.println("GN[" + n + "]");
		IO.println("PB[" + b + "]");
		IO.println("HA[" + h + "]");
		IO.println("PW[" + w + "]");
		IO.println("KM[7.5]");
		IO.println("RU[Chinese] \n");
		for(int i = 0; i < a.size(); i++) {
			o = (move)a.get(i);
			x = o.getX();
			y = o.getY();
			c = o.colour();
			
			if(c) {
				if(i+1 < a.size()) {
					move m = (move)a.get(i+1);
					if(m.colour()) 
						IO.println("(;B[" + sgf(x, y) + "]");
					else
						IO.print("(;B[" + sgf(x, y) + "]");
				}
				else if(i+1 == a.size()) {
					IO.print("(;B[" + sgf(x, y) + "]");
				}
			}
			
			else {
				IO.println(";W[" + sgf(x, y) + "]");
			}
		}
		IO.closeOutputFile();
	}
	
	public void exit() {
		int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", " ", JOptionPane.OK_CANCEL_OPTION);
		if(r == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
	}
	
	//converts char coordinate to integer
	public move sgfRev(char x, char y, boolean c) { 
		int xInt, yInt;
		xInt = (int) x;
		xInt -= 97;
		
		yInt = (int) y;
		yInt -= 97;
		
		move m = new move(xInt, yInt, c);
		return m;
	}
	
	//converts integer coordinate to char
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

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getW() {
		return w;
	}

	public void setW(String w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	
}