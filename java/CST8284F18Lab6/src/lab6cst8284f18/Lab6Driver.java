package lab6cst8284f18;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Lab6Driver {

	public static void main(String[] args) {
		
		CalculatorGUI calculatorFrame = new CalculatorGUI(); //instantiate a reference
		calculatorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //specify how to close
		calculatorFrame.setSize(500, 500); //specify the size of the JFrame
		calculatorFrame.setVisible(true);
		JButton button = new JButton("Press");
		calculatorFrame.getContentPane().add(button); // Adds Button to content pane of frame
		calculatorFrame.setVisible(true);
	}
}
