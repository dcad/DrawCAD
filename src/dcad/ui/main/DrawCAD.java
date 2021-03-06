package dcad.ui.main;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import dcad.util.GConstants;
import dcad.util.GMethods;
import dcad.Prefs;

public class DrawCAD{
	private static JFrame frame = null;
	
	/**Function to set information like segmentation scheme,
	 * Speed and Curvature scaling factors to a DrawCAD.properties file
	 * @author Sunil Kumar
	 */
	public void setDrawCADProperties() throws IOException{
		    BufferedReader reader = null;
		    PrintWriter writer = null;
		    String absPath = System.getProperty("user.dir");
		    // to create a new file
		   
		    File f;
		      f = new File(absPath + "/src/DrawCAD.txt");
		      if(!f.exists()){
		          f.createNewFile();
		      }
		     
		      // copying modified data + Previous data (DrawCAD.properties) to DrawCAD.txt
		      
		    try {
		        reader = new BufferedReader(new FileReader(absPath + "/src/DrawCAD.properties"));
		        writer = new PrintWriter(new FileWriter(absPath + "/src/DrawCAD.txt"));
		        String  line;
		        int segScheme = Prefs.getSegScheme();
		        int lineNumber = 1;
		        while ((line = reader.readLine()) != null) {
		        	if(lineNumber == 7){
		        		writer.println("segScheme="+ segScheme);
		        	}
		        	else if(lineNumber == 15){
		        		writer.println("speedScalingFactor="+Double.toString(Prefs.getSpeedScalingFactor()));
		        	}
		        	else if(lineNumber == 17){
		        		writer.println("curvatureScalingFactor="+Double.toString(Prefs.getCurvatureScalingFactor()));
		        	}
		        	else{
		            writer.println(line);
		        	}
		            lineNumber++;
		        }
		        writer.close();
		    } catch (IOException ex1) {
		    } finally {
		        try { if (reader!=null) reader.close(); } catch(IOException fex1) {}
		        writer.close();
		    }
		    
		    // delete file DrawCAD.properties
		    f = new File(absPath + "/src/DrawCAD.properties");
		    f.delete();
		    
		    //rename DrawCAD.txt to DrawCAD.properties
		    f = new File(absPath + "/src/DrawCAD.txt"); 
		    f.renameTo(new File(absPath + "/src/DrawCAD.properties"));
	}
	
	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				GMethods.init(System.getProperty("user.dir")+"/src/DrawCAD.properties");
				
		        frame = new JFrame();
				frame.setTitle(MainWindow.setAppName("III"));
				JFrame.setDefaultLookAndFeelDecorated(false);
		      //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
				frame.setVisible(true);
		        frame.setIconImage(new ImageIcon(GConstants.IMAGE_SRC+"pencil.gif", "DrawCAD").getImage());
		        
				frame.setContentPane(MainWindow.getApplicationContentPane());
				frame.setJMenuBar(MainWindow.getApplicationMenuBar());
				MainWindow.startApplication();
				frame.addWindowListener(new java.awt.event.WindowAdapter() {
				    public void windowClosing(WindowEvent winEvt) {
				    	DrawCAD d = new DrawCAD();
				    	try{
				    	d.setDrawCADProperties();
				    	}catch (IOException ex1){
				    		
				    	}
				        System.exit(0); 
				    }
				});
			}
		});
		
	}
	
	
}
