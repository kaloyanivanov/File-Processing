import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class FileProcessingController implements ActionListener {

	private LinkedList<String> lines = new LinkedList<String>();
	private File file = null;
	private FileProcessingUI UI = new FileProcessingUI();
	private FileProcessingModel model = new FileProcessingModel(lines);
	
	
	FileProcessingController (){
		UI.AddActionListener(this);
	}
	
	

	public void openFile(String path){
			file = new File(path);
			try (Scanner scanner = new Scanner(file)) {
				if (file.length() == 0) UI.emptyFileErrorMessage();
				while (scanner.hasNext()) {
					lines.add(scanner.nextLine());
				}
				UI.setLines(lines);
				UI.menu();
			} catch (Exception e) {
				UI.fileErrorMessage();
			}
	}

	private void editLines(String indexes) {
			boolean isListChanged;
			isListChanged = false;
			Integer firstIdx = null;
			Integer secondIdx = null;
			Integer thirdIdx = null;
			Integer fourthIdx = null;
			Boolean mode = null;
			try (Scanner scanner = new Scanner(indexes)) {
				firstIdx = scanner.nextInt();
				secondIdx = scanner.nextInt();
				if (firstIdx <= lines.size() - 1)
					mode = true;
				thirdIdx = scanner.nextInt();
				fourthIdx = scanner.nextInt();
				if (firstIdx <= lines.size() - 1)
					mode = false;

			} catch (Exception e) {
				UI.indexErrorMessage();
			}
			if (mode != null)
				isListChanged = model.editList(firstIdx, secondIdx, thirdIdx, fourthIdx, mode);
			if (!isListChanged)
				UI.indexErrorMessage();
			if (isListChanged)
				UI.changeContentPreview();
				UI.hideErrorMessage();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand(); 
        if (s.equals("Submit")) {
        	openFile(UI.text.getText());
            }
        if (s.equals("Switch lines or words")) {	
        	editLines(UI.text.getText());
            }
        if (s.equals("Save")) {	
        	model.writeFile(file);
            }
        if (s.equals("Load another file")) {
        	lines=new LinkedList<String>();
        	model.lines=lines;
        	UI.enterFilePath();
            }
		
	}

}
