package client;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;

/**
 * Thread which will look for changed date on file
 * 
 * @author Ervinas
 * @version 1.0
 */
public class WatcherThread extends Thread{
	
	String path;
	String type;
    private Path folderPath;
    protected String watchFile;
    
    private ServerComm serverComm;
	
    /**
     * 
     * @param watchFile A file's path to be watched
     * @param type A file's type 
     * @param serverComm Server communication object to allow this class to send Message to Server
     * @throws UnknownHostException Will be thrown if there was a problem finding a file
     * @throws IOException I/O Exception
     */
	public WatcherThread(String watchFile, String type, ServerComm serverComm) throws UnknownHostException, IOException{
		
		this.path = watchFile;
		this.type = type;
		
		this.serverComm = serverComm;
		
        Path filePath = Paths.get(watchFile);

        boolean isRegularFile = Files.isRegularFile(filePath);

        if (!isRegularFile)
        {
            throw new IllegalArgumentException(watchFile + " is not a regular file");
        }

        folderPath = filePath.getParent();

        this.watchFile = watchFile.replace(folderPath.toString() + File.separator, "");
		
	}

	/**
	 * Main thread method, will run in a loop. If there was a change inside a file, it will commit it
	 */
	public void run(){
		
	        FileSystem fileSystem = folderPath.getFileSystem();
	        WatchService service = null;
			try {
				service = fileSystem.newWatchService();
			} catch (IOException e) {
				e.printStackTrace();
			}
	            try {
					folderPath.register(service, ENTRY_MODIFY);
				} catch (IOException e) {
					e.printStackTrace();
				}

	            while (true)
	            {
	                WatchKey watchKey = null;
					try {
						watchKey = service.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

	                for (WatchEvent<?> watchEvent : watchKey.pollEvents())
	                {
	                    Kind<?> kind = watchEvent.kind();

	                    if (kind == ENTRY_MODIFY)
	                    {
	                        Path watchEventPath = (Path) watchEvent.context();

	                        if (watchEventPath.toString().equals(watchFile))
	                        {
	                            this.onModified();
	                        }
	                    }
	                }

	                if (!watchKey.reset())
	                {
	                    break;
	                }
	            }
	        }
	
	/**
	 * Triggering event, if there was a change on date of file, it will start this method
	 */
	@SuppressWarnings("resource")
	public void onModified()
    {
    	FileInputStream in;
    	BufferedReader br;
		try {
			in = new FileInputStream(path);
			
			br = new BufferedReader(new InputStreamReader(in));
    	         
	    	String strLine = null, tmp;
	    	 
	    	while ((tmp = br.readLine()) != null)
	    	{
	    	   strLine = tmp;
	    	}
	    	         
	    	String lastLine = strLine;
	    	
	    	serverComm.sendLine(lastLine, this.type);
	    	
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			return;
		}
		return;
    }

}
