package client;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Client side one of the most important classes, it will create a File object, pass it to WatcherThread, starts the threading and stores it in variable array
 * FileProcessing.java
 * 
 * @author Ervinas
 * @version 1.0
 */
public class FileProcessing {
	
	public static ArrayList<File> files = new ArrayList<File>();
	private static ArrayList<WatcherThread> threads = new ArrayList<WatcherThread>();
	
	/**
	 * Contructor sets File's path , start the threading of watching files
	 * @param serverComm Server communication object to allow this class to send Message to Server
	 * @throws IOException I/O Exception
	 */
	public FileProcessing(ServerComm serverComm) throws IOException{
		setFilesPath();
		startThreadingFiles(serverComm);
		System.out.println("File watching service started");
	}

	/**
	 * A method which sets File's path and add to files array variable
	 * @throws IOException I/O Exception
	 */
	private void setFilesPath() throws IOException{
		
		JSONArray array = (JSONArray) Config.getJsonObject().get("watchlist");
        @SuppressWarnings("rawtypes")
		Iterator i = array.iterator();

        while (i.hasNext()) {
            JSONObject fileInfo = (JSONObject) i.next();
            
            String path = (String) fileInfo.get("file");
            String type = (String) fileInfo.get("type");
            
            files.add(new File(path,type));

            }
        
        return;
		
	}
	
	/**
	 * Threading method, which will start the threading by passing variables to WatcherThread object
	 * @param serverComm Server communication object to allow this class to send Message to Server
	 * @throws UnknownHostException Error will shown if there was a problem finding files
	 * @throws IOException I/O Exception
	 */
	private void startThreadingFiles(ServerComm serverComm) throws UnknownHostException, IOException{
		
		for(File file : files){
			
			String path = file.getPath();
			String type = file.getType();

			WatcherThread watcher = new WatcherThread(path,type,serverComm);
			watcher.start();
			threads.add(watcher);
			
		}	
	}
}
