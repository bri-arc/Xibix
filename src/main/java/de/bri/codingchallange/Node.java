package de.bri.codingchallange;

import lombok.Getter;

import javax.json.JsonValue;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing a node in the Json file
 * 
 * @author benjamin
 *
 */
@Getter @Setter @NoArgsConstructor @ToString @AllArgsConstructor
public class Node extends AbstractBase {
	
	public static final String ATTRIBUTE_ID = "id";
	public static final String ATTRIBUTE_X = "x";
	public static final String ATTRIBUTE_Y = "y";

	private Integer id;
	
	private Integer x;
	
	private Integer y;
	
	/**
	 * Public constructor with JsonValue as parameter.
	 * The attributes in the JsonValue are used to initialize the Node instance.  
	 * 
	 * @param item JsonValue representing a node in the Json file
	 */
	public Node(final JsonValue item) {
		this();
		this.id = this.getJsonValueAsInteger(item, ATTRIBUTE_ID); 
		this.x = this.getJsonValueAsInteger(item, ATTRIBUTE_X);
		this.y = this.getJsonValueAsInteger(item, ATTRIBUTE_Y);
		
		if (this.id == null || this.x == null || this.y == null) {
			String msg = "At least one of the reqiured attributes in the Json file is not present. \n";
			msg += String.format("Present are (id: %b, x: %b, y: %b)", this.id != null, this.x != null, this.y != null); 
			this.getLogger().severe(msg);
		}
	}
	
	
	
	/**
	 * Wrapper function to easily extract an integer value from the JsonValue.
	 * 
	 * @param value - JsonValue containing the desired integer
	 * @param name - Name of the attribute to extract
	 * @return {@link Integer} or {@code null}
	 */
	private Integer getJsonValueAsInteger(JsonValue value, String name) {
		return (value != null) ? value.asJsonObject().getInt(name) : null;
	}

	@Override
	public boolean equals(Object obj) {
		return ((Node)obj).getId().equals(this.getId());
	}
}