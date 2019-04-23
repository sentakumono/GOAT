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
	
	public FileManager() {
		 String filepath = 	"SaveGame.txt";
		 IO.createOutputFile(filepath);
		 
		 IO.println("(;FF[4]GM[1]SZ[19]");
	}
	
	public void initiate() {
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
			System.out.println("Game Name: " + name.getText());
			System.out.println("Ruleset: " + handicap.getText());
			System.out.println("Black guy: " + blackName.getText());
			System.out.println("White Guy: " + whiteName.getText());
		}
		else if (result == JOptionPane.CANCEL_OPTION) {
			System.exit(0);
		}
	}
	
	public void load() throws IOException {
		
	}
	
	public void save(ArrayList a) {
		
	}
	
	public void setInput(int x, int y, String colour) {
		
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}
}
