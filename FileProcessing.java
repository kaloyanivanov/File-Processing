import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FileProcessing {
	
	 private static Scanner scanner = null;
	 private static LinkedList<String> lines = new LinkedList<String>();
	 private static File file=null;
	 private static FileProcessingUI UI=new FileProcessingUI();
	 
	 public static void main(String[] args) {
			openFile();
		}
	 
	 private static void openFile() {
			boolean isFileOpened=false;
			do {
				UI.setFilePath(null);
			try {
				do {
					TimeUnit.SECONDS.sleep(2);
					if(UI.isWindowClosed()) break;
				}while(UI.getFilePath()==null);
				if(UI.isWindowClosed()) break;
				 String filePath=UI.getFilePath();
				 UI.setFilePath(null);
				file = new File(filePath);
				 scanner = new Scanner(file);
				  while (scanner.hasNext()) {
						  lines.add(scanner.nextLine());
						}
				  isFileOpened=true;
				  UI.setLines(lines);
				  UI.menu();
				  menu();
				 
				} catch (IOException ioe) {
					UI.fileErrorMessage();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					 try{
					      if(scanner!=null)
						 scanner.close();
					   }catch(Exception ex){
						   ex.printStackTrace();
					       System.out.println("Error in closing the scanner.");
					    }
				}
			}while(!isFileOpened);
	 }
	 
	 private static void menu() {
		 boolean isFileChanged=false;
			do {
				
			do {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(UI.isSave()) {
					writeFile(file);
					UI.setSave(false);
				}
				 if(UI.isWindowClosed()) {
					if(isFileChanged) writeFile(file);
					 break;}
				if (UI.isWantToLoadAnotherFile()) break;
			   }
			while(UI.getIndexes()==null);
			 if(UI.isWindowClosed()) break;
			 if (UI.isWantToLoadAnotherFile()) break;
			 scanner = new Scanner(UI.getIndexes());
			 Integer firstIdx=null;
			 Integer secondIdx=null;
			 Integer thirdIdx;
			 Integer fourthIdx=null;
			 try {
				 firstIdx=scanner.nextInt();
				 secondIdx=scanner.nextInt();
			}catch(NoSuchElementException ne) {
					 UI.indexErrorMessage();
					 UI.setIndexes(null);
				 }
			 try {
				thirdIdx=scanner.nextInt();
				fourthIdx=scanner.nextInt();
			 }catch(NoSuchElementException ne) {
				thirdIdx=null;
			 }
			 try{
			      if(scanner!=null)
				 scanner.close();
			   }catch(Exception ex){
				   ex.printStackTrace();
			       System.out.println("Error in closing the scanner.");
			    }
			if(firstIdx!=null&&secondIdx!=null&&thirdIdx!=null&&fourthIdx!=null&&firstIdx<=lines.size()-1&&thirdIdx<=lines.size()-1) {
				int firstLineWords=countWords(lines.get(firstIdx));
				int secondLineWords=countWords(lines.get(thirdIdx));
				if (secondIdx<=firstLineWords&&fourthIdx<=secondLineWords) {
					switchWord(firstIdx,secondIdx,thirdIdx,fourthIdx);
					 UI.changeContentPreview();	
					}
				isFileChanged=true;
			}
			if(firstIdx!=null&&secondIdx!=null&&firstIdx<=lines.size()-1&&secondIdx<=lines.size()-1&&thirdIdx==null&&fourthIdx==null) {
				switchLine(firstIdx,secondIdx,lines.get(firstIdx),lines.get(secondIdx));
				 UI.changeContentPreview();
				 isFileChanged=true;
			}
			 UI.setIndexes(null);
			}while(true);
			if(!UI.isWindowClosed()) {
				 try{
				      if(scanner!=null)
					 scanner.close();
				   }catch(Exception ex){
					   ex.printStackTrace();
				       System.out.println("Error in closing the scanner.");
				    }
				lines.clear();
				UI.setWantToLoadAnotherFile(false);
				UI.enterFilePath ();
				openFile();
			}
			
		 
		 
	 }
	
	private static void switchLine(int firstLineIdx, int secondLineIdx, String firstLine, String secondLine) {
		lines.set(firstLineIdx, secondLine);
		lines.set(secondLineIdx, firstLine);
		
	}
	
	private static void switchWord (int firstLineIdx,int firstWordIdx, int secondLineIdx, int secondWordIdx){
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
	
	
	private static void buildNewLine(int lineIdx,int wordIdx,StringBuilder temp,String word) {
		scanner=new Scanner(lines.get(lineIdx));
		int index=0;
		while (scanner.hasNext()) {
			if(index-1==wordIdx-1)  {
				if(index==0)temp.append(word+" ");
				else if(index==1)temp.append(word);
				else temp.append(" "+word);
				scanner.next();
				index++;
				continue;
				}
			if(index==0)temp.append(scanner.next()+" ");
			else if(index==1)temp.append(scanner.next());
			else temp.append(" "+scanner.next());
			 index++;
			 
			}
		 try{
		      if(scanner!=null)
			 scanner.close();
		   }catch(Exception ex){
			   ex.printStackTrace();
		       System.out.println("Error in closing the scanner.");
		    }
		
	}
	
	private static String findWord(int lineIdx, int wordIdx) {
		String word=new String();
		scanner=new Scanner(lines.get(lineIdx));
		int index=0;
		while (scanner.hasNext()) {
			if(index-1==wordIdx-1) {
				word=scanner.next(); 
				break;
			}
			scanner.next();
			 index++;
			 
			}
		 try{
		      if(scanner!=null)
			 scanner.close();
		   }catch(Exception ex){
			   ex.printStackTrace();
		       System.out.println("Error in closing the scanner.");
		    }
		return word;
		
	}
	
	private static void writeFile(File file) {
		BufferedWriter writer = null;
	      try {
		 FileWriter fw = new FileWriter(file,false);
		  writer = new BufferedWriter(fw);
		  for(int i=0; i < lines.size(); i++){
			  
			  writer.write(lines.get(i));
			  writer.newLine();
		    }
	      } catch (IOException ioe) {
		   ioe.printStackTrace();
		}
		finally
		{ 
		   try{
		      if(writer!=null)
			 writer.close();
		   }catch(Exception ex){
		       System.out.println("Error in closing the BufferedWriter"+ex);
		    }
		}
		
		
	}
	
	private static int countWords(String line) {
		scanner=new Scanner(line);
		int words=0;
		while(scanner.hasNext()) {
			scanner.next();
			words++;
		}
		 try{
		      if(scanner!=null)
			 scanner.close();
		   }catch(Exception ex){
			   ex.printStackTrace();
		       System.out.println("Error in closing the scanner.");
		    }
		return words-1;
	}

		
}
