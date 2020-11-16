import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileProcessingUI implements ActionListener{
	  private JLabel label;
	  private JLabel errorMessage;
	  private JTextField text=new JTextField();
	  private JTextArea content=new JTextArea();
	  private JScrollPane scroll = new JScrollPane(content);
	  private JFrame window= new JFrame();
	  private JButton firstButton=new JButton(); 
	  private JButton secondButton=new JButton(); 
	  private JButton thirdButton=new JButton("Load another file");
	  private JLabel info=new JLabel("<html>If you want to switch two words enter first line index, first word index, second line index, second word index.<br>If you want to switch two lines enter first and second line indexes.Indexes start with 0.</html>");
	  private String filePath;
	  private LinkedList<String> lines = new LinkedList<String>();
	  private String indexes;
	  private boolean save=false;
	  private boolean isWindowClosed=false;
	  private boolean wantToLoadAnotherFile=false;

	 public FileProcessingUI () {
		 label=new JLabel();  
		 label.setFont(new Font("Serif", Font.PLAIN, 25));
		 label.setText("Enter path of file to be processed:");
		 label.setBounds(310,0,1000,30); 
		 window.add(label);
		 label.setVisible(true);
		 window.setSize(1000, 600);
		 window.setResizable(false);
		 window.setLayout(null);  
		 window.setVisible(true);
		 text.setBounds(380,40,200,30); 
		 text.setVisible(true);
		 window.add(text);
		 firstButton.setBounds(430,100,95,30); 
		 firstButton.addActionListener(this);
		 firstButton.setText("Submit");
		 window.add(firstButton);
		 firstButton.setVisible(true);
		 secondButton.setBounds(490,60,100,30); 
		 secondButton.addActionListener(this);
		 secondButton.setVisible(false);
		 secondButton.setText("Save");
		 window.add(secondButton);
		 thirdButton.setBounds(600,60,150,30); 
		 thirdButton.addActionListener(this);
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
	         window.addWindowListener(new WindowAdapter() {
		   public void windowClosing(WindowEvent e) {
			    	 isWindowClosed=true;
			    }
			});	 
		 }
	  
	  public void actionPerformed(ActionEvent e) 
	    { 
	        String s = e.getActionCommand(); 
	        if (s.equals("Submit")) {
	        	setFilePath(text.getText());
	            }
	        if (s.equals("Switch lines or words")) {	
	        	setIndexes(text.getText());
	            }
	        if (s.equals("Save")) {	
	        	save=true;
	            }
	        if (s.equals("Load another file")) {	
	        	setWantToLoadAnotherFile(true);
	            }
	           
	            
	        }
	        
	        

	 
	 public void enterFilePath () {
		 errorMessage.setVisible(false);
		 window.setSize(1000, 600);
		 label.setText("Enter path of file to be processed:");
		 label.setBounds(310,0,1000,30); 
		 label.setVisible(true);
		 text.setText("");
		 text.setBounds(380,40,200,30);
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
        	label.setBounds(20,0,1000,30);
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
	
	 public String getFilePath() {
		return filePath;
		}
	
	
	public void setFilePath(String filePath) {
		this.filePath=filePath;
		}
	 
	 
	 public void setLines(LinkedList<String> lines) {
		this.lines = lines;
		}
	 
	 public void fileErrorMessage() {
		errorMessage.setText("Please enter a valid file path.");;  
		errorMessage.setBounds(150,150,1000,30); 
		errorMessage.setVisible(true);	 
	 	}
	 
	 public void indexErrorMessage() {
		 errorMessage.setBounds(600,150,1000,30);
		 errorMessage.setText("Please enter valid indexes.");	
		 errorMessage.setVisible(true);	 
	 	}




	public String getIndexes() {
		return indexes;
	}




	public void setIndexes(String indexes) {
		this.indexes = indexes;
	}
	
	public void changeContentPreview() {
		content.setText("");
        	for(int i=0; i < lines.size(); i++){
        	
			content.append(lines.get(i)+"\n");
		    }
	}




	public boolean isSave() {
		return save;
	}




	public void setSave(boolean save) {
		this.save = save;
	}




	public boolean isWindowClosed() {
		return isWindowClosed;
	}

	
	public boolean isWantToLoadAnotherFile() {
		return wantToLoadAnotherFile;
	}

	
	public void setWantToLoadAnotherFile(boolean wantToLoadAnotherFile) {
		this.wantToLoadAnotherFile = wantToLoadAnotherFile;
	}




}
