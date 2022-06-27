package de.bri.codingchallange;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

//import com.amazonaws.services.lambda.runtime.Context;
//import com.amazonaws.services.lambda.runtime.RequestHandler;

import lombok.Getter;

@Getter
@Named("main")
public class Main extends AbstractBase { // implements RequestHandler<String, String>{
	
	private ElementHelper elementhelper;
	
	private NodeHelper nodehelper;
	
	private StopWatch stopwatch;
	
	// the three sections of data in the Json file
	private JsonArray elements = null;
	private JsonArray nodes = null;
	private JsonArray values = null;
	
	public static final String SECTION_ELEMENTS = "elements";
	public static final String SECTION_NODES = "nodes";
	public static final String SECTION_VALUES = "values";
	
	public Main() {
		super();
		this.init();
	}
	
//	@Override
//	public String handleRequest(String input, Context context) {
//		this.doTheWork(input, null);
//		return "Done";
//	}

	
	/**
	 * Initializes the main class 
	 */
	private void init() {
		this.stopwatch = new StopWatch();
		this.nodehelper = new NodeHelper();
		this.elementhelper = new ElementHelper();
		this.elementhelper.setNodehelper(this.getNodehelper());
	}
	
	/**
	 * Loads the file given by the filename and 
	 * returns its Json content as JsonObject. 
	 * 
	 * @param filename - name of the file to be loaded
	 * @return {@link JsonObject} or {@code null}
	 */
	private JsonObject loadJsonFromFilename(final String filename)  {
		try (InputStream is = new FileInputStream(filename); JsonReader reader = Json.createReader(is)) {
			return reader.readObject();
		} catch (FileNotFoundException fnfe) {
			this.getLogger().severe(String.format("The file %s could not be found", filename));
			return null;
		} catch (IOException e) {
			this.getLogger().severe(String.format("The file %s could not be parsed as a Json file", filename));
			return null;
		}
	}
	
	/**
	 * Main function to show view spots in a mesh.
	 * 
	 * @param filename - name of the Json file
	 * @param numberOfSpots - Number of spots to be find
	 */
	public void doTheWork(final String filename, final Integer numberOfSpots) {
		this.init();
		// start time measuring
		this.stopwatch.start();
		// Load Json file
		JsonObject array = this.loadJsonFromFilename(filename);
		// when there is no array then exit
		if (array == null)
			System.exit(1);
		
		if (numberOfSpots == null || numberOfSpots < 0) {
			this.getLogger().info("The number of view spots should be equals or greater than 1");
			System.exit(1);
		}
		
		// get the three sections of data in the Json file
		this.elements = array.getJsonArray(SECTION_ELEMENTS);
		this.nodes = array.getJsonArray(SECTION_NODES);
		this.values = array.getJsonArray(SECTION_VALUES);
		
		// check if necessary content is included
		if (this.getElements() == null || this.getNodes() == null || this.getValues() == null) {
			String msg = "At least one of the reqiured sections in the Json file is not present. \n";
			msg += String.format("Present are (Element: %b, Nodes: %b, Values: %b)", this.getElements() != null, this.getNodes() != null, this.getValues() != null); 
			this.getLogger().severe(msg);
			System.exit(1);
		}
		
		// start parsing content with filling node helper instance
		this.getNodehelper().parseNodes(this.getNodes());
		// start parsing content
		this.getElementhelper().parseElements(this.getElements(), this.getValues());
		//this.parseElements(this.getElements(), this.getValues());
		// find view spots
		this.getElementhelper().findViewspots(numberOfSpots);
		// stop time measuring
		this.stopwatch.stop();
		// and show execution time
		this.stopwatch.printExecutionTime();
	}
	
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.out.println("Two parameters are excepted");
			System.out.println("---------------------------");
			System.out.println("1) Mesh File ");
			System.out.println("2) Number of view spots");
			System.exit(1);
		}
		Main p = new Main();
		p.doTheWork(args[0], Integer.valueOf(args[1]));
	}
	
	public static void main1(String[] args) throws Exception {
		String filename_0 = "/home/benjamin/eclipse-workspace/Xibix/src/main/resources/mesh.json";
    	String filename_1 = "/home/benjamin/eclipse-workspace/Xibix/src/main/resources/mesh_10000.json";
    	String filename_2 = "/home/benjamin/eclipse-workspace/Xibix/src/main/resources/mesh_20000.json";
    	
		Main p = new Main();
		
		p.doTheWork(filename_0, 20);
		p.doTheWork(filename_1, 20);
		p.doTheWork(filename_2, 20);
	}
}