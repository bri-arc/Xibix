package de.bri.codingchallange;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Class representing an element in the Json file
 * 
 * @author benjamin
 *
 */
@Getter @Setter @NoArgsConstructor @ToString 
public class Element implements Comparable<Element> {
	
	public static final String ATTRIBUTE_ID = "id";
	public static final String ATTRIBUTE_NODES = "nodes";
	
	private Integer id;
	
	private Double height;
	
	private List<Node> nodes = new ArrayList<>();
	
	/**
	 * Checks if {@code this} and the element in the parameter have a common node
	 * 
	 * @param element - Element to check
	 * @return {@code true} if a common node {@code false} if not
	 */
	public boolean areNeighours(Element element) {
		return this.getNodes().stream().anyMatch(el -> element.getNodes().contains(el));
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Element)obj).getId().equals(this.getId());
	}

	@Override
	public int compareTo(Element other) {
		return this.getHeight().compareTo(other.getHeight()) * -1;
	}
}
