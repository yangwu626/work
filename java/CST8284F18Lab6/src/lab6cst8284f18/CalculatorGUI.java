package lab6cst8284f18;


import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

//Create calculator GUI implementation.
public class CalculatorGUI extends JFrame implements ActionListener {

	//DECLARE FIELDS SUCH AS BUTTONS AND PANELS
	private JLabel display; // used to display result
	private String []num;   // input number
	private String curOp;	// input operator
	private int curNum;     //current numbers input
	
	public CalculatorGUI()  {
		super("CST8284_F18_GUI");
		// init states
		num = new String [2];
		num[0] = "";
		num[1] = "";
		curNum = 0;
		curOp = "";
		
		JPanel parent = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		parent.setLayout(layout);
		
		add(parent);
		
		// display area
		{
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(Color.black));

			c.ipady = 60;
			c.weightx = 0.0;
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 0;
					
			panel.setLayout(new GridLayout(1, 1));
			display = new JLabel("", JLabel.RIGHT);
			panel.add(display);
	
			parent.add(panel, c);
		}
		
		// operator panel
		{
			JPanel panel = new JPanel();
			panel.setLayout(new GridLayout(1, 5));
			String []ops = { "+", "-", "*", "/", "%", "c", "=" };
			for(int i=0; i< ops.length; i++) {
				JButton b = new JButton(ops[i]);
				b.addActionListener(this);
				panel.add(b);
			}
			c.ipady = 50;
			c.weightx = 0.0;
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 1;
			c.insets = new Insets(10,0,0,0);
			parent.add(panel, c);
		}

		// numbers panel
		{
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(Color.black));
			panel.setLayout(new GridLayout(2, 5));
			for(int i=0; i<=9; i++) {
				JButton b = new JButton(String.valueOf(i));
				b.addActionListener(this);
				panel.add(b);
			}
			
			c.ipady = 50;
			c.weightx = 0.0;
			c.gridwidth = 3;
			c.gridx = 0;
			c.gridy = 2;
			c.insets = new Insets(10,0,0,0);
			parent.add(panel, c);
		}
		
		//SPECIFY THE LAYOUT

		//INSTANTIATE THE FIELDS
		
		//START ASSEMBLING THE GUI:
		//PUT "WIDGETS BUTTONS OR TEXTFIELDS ON THE PANELS
		//PUT THE PANELS ON THE FRAME
		
	}//END MAIN METHOD	
	
	//action event handler
	public void actionPerformed(ActionEvent e) {
		// convert source to button object
		JButton b = (JButton)e.getSource();
		// get button's text
		String txt = b.getText();
		switch(txt) {
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			// handle 0 ~ 9
			// update current num & update display
			this.num[this.curNum] += txt;
			break;
		case "+":
		case "-":
		case "*":
		case "/":
		case "%":	
			if (++curNum == 2) {
				// if we already get two nums, then we need to calculate first,
				// then assign result to num[0], clear num[1] and set current num count to 1.
				num[0] = doCalculation();
				num[1] = "";
				curNum = 1;
			}
			// update current operator
			this.curOp = txt;
			break;
		case "c":
			// reset num[0] to 0 and clear all other data.
			num[0] = "0";
			num[1] = "";
			curNum = 0;
			curOp = "";
			break;
		case "=":
			num[0] = doCalculation();
			num[1] = "";
			curNum = 0;
			curOp = "";
			break;
		default:
			break;
		}
		// at last, update display to refresh the result.
		display.setText(this.num[0] + this.curOp + this.num[1]);
	}

	String doCalculation() {
		double res = 0;
		if (num[0].isEmpty()) {
			return String.valueOf(res);
		}
		res = Double.parseDouble(num[0]);
		if (num[1].isEmpty()) {
			return String.valueOf(res);
		}
		double n = Double.parseDouble(num[1]);
		
		switch(curOp) {
		case "+":
			res += n;
			break;
		case "-":
			res -= n;
			break;
		case "*":
			res *= n;
			break;
		case "/":
			res /= n;
			break;
		case "%":
			res %= n;
			break;
				
		default:
			break;
		}
		return String.valueOf(res);
	}
}//END CLASS
