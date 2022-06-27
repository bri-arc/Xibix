package de.bri.codingchallange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonValue;

import lombok.Getter;
import lombok.Setter;

/**
 * Helper class for handling a list of Nodes.
 * 
 * @author benjamin
 *
 */
@Getter @Setter
public class NodeHelper extends AbstractBase {
	
	/**
	 * HashMap containing all {@link Node} in the Json file.
	 * The key is the id of the {@link Node} and value the {@link Node} itselfs. 
	 * 
	 */
	private Map<Integer, Node> nodes;
	
	public NodeHelper() {
		super();
		this.init();
	}
	
	public void init() {
		this.nodes = new HashMap<>();
	}

	/**
	 * Parsing the given JsonArray and filling them into the HashMap.
	 *  
	 * @param array - {@link JsonArray} containing the Json elements
	 */
	public void parseNodes(final JsonArray array) {
		this.nodes = array.parallelStream().map(Node::new).collect(Collectors.toMap(e -> e.getId(), e-> e));
//		array.parallelStream().forEach(entry -> { 
//			var n = new Node(entry); 
//			this.nodes.put(n.getId(), n); 
//		});
	}
	
	/**
	 * By passing an array of node ids this function will return a list 
	 * to the coresponding nodes. 
	 *  
	 * @param nodesIds - Ids of the desired {@link Node}
	 * 
	 * @return - {@link List} with the found {@link Node}
	 */
	public List<Node> getNodesByIds(final JsonArray nodesIds) {
		return nodesIds
				.parallelStream()
				.filter(value -> this.findByJsonValue(value) != null)
				.map(this::findByJsonValue)
				.collect(Collectors.toList());
	}
	
	/**
	 * Search in its list of nodes for the given id and returns the node.
	 * 
	 * @param id - Id of the node to be found in form of JsonValue
	 * 
	 * @return - {@link Node} or {@code null}
	 */
	private Node findByJsonValue(JsonValue value) {
		return this.findById(((JsonNumber)value).intValue());
	}
	
	/**
	 * Search in its list of nodes for the given id and returns the node.
	 * 
	 * @param id - Id of the node to be found
	 * 
	 * @return - {@link Node} or {@code null}
	 */
	public Node findById(final Integer id) {
		return this.nodes.get(id);
	}
}
