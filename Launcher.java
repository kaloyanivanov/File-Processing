
public class Launcher {

	public static void main(String[] args) {
		FileProcessing app= new FileProcessing();
		try {
			app.openFile();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
