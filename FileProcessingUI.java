import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileProcessingUI{
	  private JLabel label;
	  private JLabel errorMessage;
	  JTextField text=new JTextField();
	  private JTextArea content=new JTextArea();
	  private JScrollPane scroll = new JScrollPane(content);
	  private JFrame window= new JFrame();
	  private JButton firstButton=new JButton(); 
	  private JButton secondButton=new JButton(); 
	  private JButton thirdButton=new JButton("Load another file");
	  private JLabel info=new JLabel("<html>If you want to switch two words enter first line index, first word index, second line index, second word index.<br>If you want to switch two lines enter first and second line indexes.Indexes start with 0.</html>");
	  private LinkedList<String> lines = new LinkedList<String>();

	 public FileProcessingUI () {
		 	label=new JLabel();  
			label.setFont(new Font("Serif", Font.PLAIN, 25));
			label.setText("Enter path of file to be processed:");
			label.setBounds(310,0,1000,50); 
			window.add(label);
			label.setVisible(true);
			text.setBounds(380,50,200,30); 
			text.setVisible(true);
			window.add(text);
		    firstButton.setBounds(430,100,95,30); 
		    
		    firstButton.setText("Submit");
		    window.add(firstButton);
		    firstButton.setVisible(true);
		    secondButton.setBounds(490,60,100,30); 
		    secondButton.setVisible(false);
		    secondButton.setText("Save");
		    window.add(secondButton);
		    thirdButton.setBounds(600,60,150,30); 
		    thirdButton.setVisible(false);
		    window.add(thirdButton);
		    content.setEditable(false);
		    scroll.setBounds(50, 150, 500, 500);
	        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	        scroll.setVisible(false);
	        window.add(scroll);
	        errorMessage=new JLabel("Please enter a valid file path.");  
			errorMessage.setBounds(150,150,1000,30); 
			errorMessage.setFont(new Font("Serif", Font.PLAIN, 25));
			errorMessage.setForeground(Color.red);
			errorMessage.setVisible(false);
	        window.add(errorMessage);
	        info.setBounds(20,110,1000,30);
	        info.setVisible(false);
	        window.add(info);
	        window.setSize(1000, 600);
			window.setResizable(false);
			window.setLayout(null);  
		    window.setVisible(true);
		    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
	 }
	  
	 
	 public void enterFilePath () {
		 errorMessage.setVisible(false);
		 window.setSize(1000, 600);
		 label.setText("Enter path of file to be processed:");
		 label.setBounds(310,0,1000,50); 
		 label.setVisible(true);
		 text.setText("");
		 text.setBounds(380,50,200,30);
		 text.setVisible(true);
		 firstButton.setVisible(true);
		 firstButton.setBounds(430,100,95,30);
		 firstButton.setText("Submit");
		 info.setVisible(false);
		 secondButton.setVisible(false);
		 thirdButton.setVisible(false);
		 scroll.setVisible(false);
		  
	  }
	 
	 
	
	public void menu() {
		errorMessage.setVisible(false);
		window.setSize(950, 750);
		info.setVisible(true);
        label.setText("You can switch two lines or switch two words in your text document.");
        label.setBounds(20,0,1000,50);
        changeContentPreview();
        text.setText("");
        text.setBounds(100,60,200,30);
        text.setVisible(true);
        scroll.setVisible(true);
	    firstButton.setText("Switch lines or words");
	    firstButton.setBounds(310,60,170,30);
	    secondButton.setVisible(true);
	    thirdButton.setVisible(true);
		}
	 
	 
	 public void setLines(LinkedList<String> lines) {
			this.lines = lines;
		}
	 
	 public void fileErrorMessage() {
		    errorMessage.setText("Please enter a valid file path.");;  
			errorMessage.setBounds(150,150,1000,50); 
			errorMessage.setVisible(true);
		 
	 }
	 
	 public void emptyFileErrorMessage() {
		    errorMessage.setText("File empty");;  
			errorMessage.setBounds(150,150,1000,50); 
			errorMessage.setVisible(true);
		 
	 }
	 
	 public void indexErrorMessage() {
		 errorMessage.setBounds(600,150,1000,50);
		 errorMessage.setText("Please enter valid indexes.");	
		 errorMessage.setVisible(true);
		 
	 }
	 
	 public void hideErrorMessage() {
		 errorMessage.setVisible(false);
		 
	 }

	public void changeContentPreview() {
		content.setText("");
        for(int i=0; i < lines.size(); i++)
		   {
        	content.append(lines.get(i)+"\n");
		    }
	}
	
	public void AddActionListener(ActionListener listener) {
		firstButton.addActionListener(listener);
		secondButton.addActionListener(listener);
		thirdButton.addActionListener(listener);
	}




}
