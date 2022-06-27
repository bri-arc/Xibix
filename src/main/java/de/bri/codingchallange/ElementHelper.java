package de.bri.codingchallange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import org.apache.commons.lang3.ArrayUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * Helper class for handling a list of Elements.
 * 
 * @author benjamin
 *
 */
@Getter @Setter
public class ElementHelper extends AbstractBase {
	
	public static final String VALUE_ATTRIBUTE_ELEMENT_ID = "element_id";
	public static final String VALUE_ATTRIBUTE_VALUE = "value";
	
	private NodeHelper nodehelper;

	/**
	 * List containing all {@link Element} in the Json file.
	 * 
	 */
	private List<Element> list;
	
	private List<Element> spots;
	
	public ElementHelper() {
		super();
		this.init();
	}
	
	public ElementHelper(final NodeHelper nodeHelper) {
		this();
		this.init();
		this.setNodehelper(nodehelper);
	}
	
	private void init() {
		this.list = new ArrayList<>();
		this.spots = new ArrayList<>();
	}

	/**
	 * Sorts the list of elements and prints out the desired number of view spots
	 * 
	 * @param numberOfSpots - number of spots to be printed
	 */
	public void findViewspots(final Integer numberOfSpots) {
		PriorityQueue<Element> subset = new PriorityQueue<Element>(this.getList());
		Element[] removed = new Element[0];

		while (this.getSpots().size() < numberOfSpots) {
			Element spot = this.findSpot(subset);
			if (spot != null) {
				if (removed.length == 0 || !this.hasNeighbours(spot, removed)) {
					this.spots.add(spot);
				}
				removed = ArrayUtils.add(removed, spot);
				subset.remove(spot);
			} else {
				// no spots left; this means that the number of desired spots is bigger than the available spots
				break;
			}
		}
		spots.stream().forEach(entry -> System.out.println(entry.toString()));
	}
	
	private boolean hasNeighbours(Element element, Element[] removed) {
		return Arrays.stream(removed).parallel().filter(el -> element.areNeighours(el)).findAny().orElse(null) != null;
	}
	
	private Element findSpot(PriorityQueue<Element> list) {
		return list.peek();
	}

	/**
	 * Parsing the given JsonArray and filling them into the list.
	 *  
	 * @param array - {@link JsonArray} containing the Json elements
	 */
	public void parseElements(final JsonArray elements, final JsonArray values) {
		List<JsonObject> list = elements.stream().map(JsonValue::asJsonObject).collect(Collectors.toList());
		list.parallelStream().forEach(element -> {
			// get id of element
			Integer id = element.getInt(Element.ATTRIBUTE_ID);
			// find scalar with height of element
			JsonObject height = this.findJsonElementById(values, VALUE_ATTRIBUTE_ELEMENT_ID, id);
			// get nodes ids for element
			var ids = element.getJsonArray(Element.ATTRIBUTE_NODES);
			
			// create Element
			Element e = new Element();
			e.setId(id);
			e.setHeight(height.getJsonNumber(VALUE_ATTRIBUTE_VALUE).doubleValue());
			List<Node> nodes = this.getNodehelper().getNodesByIds(ids); 
			e.setNodes(nodes);
			// and put it in the list
			this.getList().add(e);
		});
	}
	
	/**
	 * This function is searching for the scalar value of an element in corseponding Json array.
	 * 
	 * @param array - Array to be searched for the id of element
	 * @param id_element_name - name of the attribute in Json-Entry containing the id value of element
	 * @param id - id of the element
	 * @return {@link JsonObject} representing the value-element or {@code null} when no element was found
	 */
	private JsonObject findJsonElementById(final JsonArray array, final String id_element_name, final Integer id) {
		JsonValue result = array.stream().filter(node -> id.equals(node.asJsonObject().getInt(id_element_name))).findAny().orElse(null); 
		return (result != null) ? result.asJsonObject() : null;
	}
}
