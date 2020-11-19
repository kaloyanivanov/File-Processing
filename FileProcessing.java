import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FileProcessing {
	
	 private  LinkedList<String> lines = new LinkedList<String>();
	 private  File file=null;
	 private  FileProcessingUI UI=new FileProcessingUI();
	 

	 
	 public void openFile() throws InterruptedException {
			
		 boolean isFileOpened=false;
			do {
				UI.setFilePath(null);
				waitForPath();
				if(UI.isWindowClosed()) break;
				 String filePath=UI.getFilePath();
				 file = new File(filePath);
				 try (Scanner scanner = new Scanner(file)) {
					 if (file.length() == 0) {
						 UI.emptyFileErrorMessage();
						 continue;}
					 while (scanner.hasNext()) {
						  lines.add(scanner.nextLine());
						}
					  isFileOpened=true;
					  UI.setLines(lines);
					  UI.menu();
					  menu();
				   }catch(Exception e) {
					 UI.fileErrorMessage();
					 }
				}while(!isFileOpened);
	 }
	 
	 private void menu() throws InterruptedException {
		 	boolean isListChanged;
			do {
			 isListChanged=false;
			 waitForIndexes(isListChanged);
			 if(UI.isWindowClosed()) break;
			 if (UI.isWantToLoadAnotherFile()) break;
			 Integer firstIdx=null;
			 Integer secondIdx=null;
			 Integer thirdIdx=null;
			 Integer fourthIdx=null;
			 Boolean mode=null;
			 try (Scanner scanner = new Scanner(UI.getIndexes())) {
				 firstIdx=scanner.nextInt();
				 secondIdx=scanner.nextInt();
				 if(firstIdx<=lines.size()-1) mode=true;
				 thirdIdx=scanner.nextInt();
				 fourthIdx=scanner.nextInt();
				 if(firstIdx<=lines.size()-1) mode=false;
					
			 }catch(Exception e) {
					 UI.setIndexes(null);
				 }
			 if(mode!=null) isListChanged=editList(firstIdx,secondIdx,thirdIdx,fourthIdx,mode);
			 if(!isListChanged)UI.indexErrorMessage();
			 if(isListChanged)UI.hideErrorMessage();
			 UI.setIndexes(null);
			}while(true);
			if(!UI.isWindowClosed()) {
				lines.clear();
				UI.setWantToLoadAnotherFile(false);
				UI.enterFilePath ();
				openFile();
			}
			
		 
		 
	 }
	
	private void switchLine(int firstLineIdx, int secondLineIdx, String firstLine, String secondLine) {
		lines.set(firstLineIdx, secondLine);
		lines.set(secondLineIdx, firstLine);
		
	}
	
	private void switchWord (int firstLineIdx,int firstWordIdx, int secondLineIdx, int secondWordIdx){
		StringBuilder firstLine=new StringBuilder();
		StringBuilder secondLine=new StringBuilder();
		String firstToAppend=findWord(firstLineIdx,firstWordIdx);
		String secondToAppend=findWord(secondLineIdx,secondWordIdx);
		buildNewLine(firstLineIdx,firstWordIdx,firstLine,secondToAppend);
		if(firstLineIdx==secondLineIdx) lines.set(firstLineIdx, firstLine.toString());
		buildNewLine(secondLineIdx,secondWordIdx,secondLine,firstToAppend);
		lines.set(firstLineIdx, firstLine.toString());
		lines.set(secondLineIdx, secondLine.toString());
		}
	
	
	private void buildNewLine(int lineIdx,int wordIdx,StringBuilder temp,String word) {
		int index=0;
		try (Scanner scanner = new Scanner(lines.get(lineIdx))) {
			while (scanner.hasNext()) {
				String toAppend=scanner.next();
				if(index-1==wordIdx-1) toAppend=word;
				if(index==0)temp.append(toAppend+" ");
				else if(index==1)temp.append(toAppend);
				else temp.append(" "+toAppend);
				index++;
				}
		   }catch(Exception ex){
			   ex.printStackTrace();
		       System.out.println("Error in closing the scanner.");
		    }
		
	}
	
	private String findWord(int lineIdx, int wordIdx) {
		String word=new String();
		try (Scanner scanner = new Scanner(lines.get(lineIdx))){
		int index=0;
		while (scanner.hasNext()) {
			if(index-1==wordIdx-1) {
				word=scanner.next(); 
				break;
			}
			scanner.next();
			index++;
			 
			}
		}
		catch(Exception ex){
			   ex.printStackTrace();
		       System.out.println("Error in closing the scanner.");
		    }
		return word;
		
	}
	
	private void writeFile(File file) {
	    try (FileWriter fw = new FileWriter(file,false);
	    	BufferedWriter writer = new BufferedWriter(fw)){
		  for(int i=0; i < lines.size(); i++){
			  writer.write(lines.get(i));
			  writer.newLine();
		    }
	      } catch(IOException ioe){
		       System.out.println("Error in closing the BufferedWriter"+ioe);
		    }
	}
	
	private int countWords(String line) {
		int words=0;
		try (Scanner scanner = new Scanner(line)){
		while(scanner.hasNext()) {
			scanner.next();
			words++;
		}
		}
		   catch(Exception ex){
			   ex.printStackTrace();
		       System.out.println("Error in closing the scanner.");
		    }
		return words-1;
	}
	
	private void waitForPath() throws InterruptedException {
		do {
			TimeUnit.SECONDS.sleep(2);
			if(UI.isWindowClosed()) break;
		}while(UI.getFilePath()==null);
	}
	
	private void waitForIndexes(boolean isListChanged) throws InterruptedException{
		do {
			TimeUnit.SECONDS.sleep(2);
			if(UI.isSave()) {
				writeFile(file);
				UI.setSave(false);
			}
			 if(UI.isWindowClosed()) {
				if(isListChanged) writeFile(file);
				 break;}
			if (UI.isWantToLoadAnotherFile()) break;
				}	
			while(UI.getIndexes()==null);
	}
	
	private boolean editList (Integer firstIdx,Integer secondIdx,Integer thirdIdx,Integer fourthIdx,Boolean mode) {
		boolean isListChanged = false;
		if(mode==false&&thirdIdx<=lines.size()-1) {
			int firstLineWords=countWords(lines.get(firstIdx));
			int secondLineWords=countWords(lines.get(thirdIdx));
			if (secondIdx<=firstLineWords&&fourthIdx<=secondLineWords) {
				switchWord(firstIdx,secondIdx,thirdIdx,fourthIdx);
				 UI.changeContentPreview();	
				 isListChanged=true;
				}
		 }
		if(mode==true&&secondIdx<=lines.size()-1) {
			switchLine(firstIdx,secondIdx,lines.get(firstIdx),lines.get(secondIdx));
			 UI.changeContentPreview();
			 isListChanged=true;
		}
		return isListChanged;
	}

		
}
