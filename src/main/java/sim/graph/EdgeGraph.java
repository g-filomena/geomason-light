package sim.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.planargraph.Edge;

import sim.util.geo.AttributeValue;
import sim.util.geo.MasonGeometry;

/**
 * An edge that extends the GeomPlanarGraphEdge (GeoMason) and Edge (Jts)
 * classes. This is one of the main components, along with NodeGraph, of graphs
 * belonging to the class Graph.
 *
 */
public class EdgeGraph extends Edge {

	public int edgeID;
	public int regionID;
	public double deflectionDegrees;
	public MasonGeometry masonGeometry;
	private LineString line; // line that corresponds to this edge

	public NodeGraph fromNode, toNode;
	public NodeGraph dualNode;

	public List<Integer> positiveBarriers = new ArrayList<>();
	public List<Integer> negativeBarriers = new ArrayList<>();
	public List<Integer> barriers = new ArrayList<>(); // all the barriers
	public List<Integer> waterBodies = new ArrayList<>();
	public List<Integer> parks = new ArrayList<>();

	public HashMap<String, Integer> volumes = new HashMap<>();
	public Map<String, AttributeValue> attributes;
	private Coordinate centroidCoords;
	private Double length;
	public boolean isKnown = false;

	/**
	 * Sets the attributes for this EdgeGraph.
	 *
	 * @param attributes A map of attribute names and values.
	 */
	public void setAttributes(final Map<String, AttributeValue> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Constructs an EdgeGraph with the provided LineString.
	 *
	 * @param line The LineString representing the edge.
	 */
	public EdgeGraph(LineString line) {
		this.line = line;
		length = line.getLength();
		centroidCoords = line.getCentroid().getCoordinate();
	}

	/**
	 * Gets the LineString representing this edge.
	 *
	 * @return The LineString for this edge.
	 */
	public LineString getLine() {
		return line;
	}

	/**
	 * Sets the nodes connected by this edge.
	 *
	 * @param fromNode The starting node of the edge.
	 * @param toNode   The ending node of the edge.
	 */
	public void setNodes(final NodeGraph fromNode, final NodeGraph toNode) {
		this.fromNode = fromNode;
		this.toNode = toNode;
	}

	/**
	 * Sets the ID of the edge.
	 *
	 * @param edgeID The unique identifier to assign to the edge.
	 */
	public void setID(int edgeID) {
		this.edgeID = edgeID;
	}

	/**
	 * Gets the ID of this edge.
	 *
	 * @return The ID of this edge.
	 */
	public Integer getID() {
		return this.edgeID;
	}

	/**
	 * Returns the edge's corresponding dual node.
	 *
	 * @return The dual node associated with this edge.
	 */
	public NodeGraph getDual() {
		return this.dualNode;
	}

	/**
	 * Returns the edge's length.
	 *
	 * @return The length of the edge.
	 */
	public double getLength() {
		return length;
	}

	/**
	 * Returns the deflection angle if this edge is a dual edge and represents a
	 * link between two dual nodes (street segment).
	 *
	 * @return The deflection angle of the dual edge, if applicable; otherwise, it
	 *         returns 0.0.
	 */
	public double getDeflectionAngle() {
		return deflectionDegrees;
	}

	/**
	 * Returns the coordinate of the edge's centroid.
	 *
	 * @return The coordinate of the edge's centroid.
	 */
	public Coordinate getCoordsCentroid() {
		return centroidCoords;
	}

	/**
	 * Resets the volumes when called during the simulation (e.g., at the start of a
	 * new run).
	 */
	public void resetVolumes() {

		for (final String key : volumes.keySet())
			volumes.replace(key, 0);
	}

	/**
	 * Given one of the nodes of this edge, it returns the other one.
	 *
	 * @param node One of the nodes connected to this edge.
	 * @return The other node connected to this edge, or null if the provided node
	 *         is not connected to this edge.
	 */
	public NodeGraph getOtherNode(NodeGraph node) {
		if (fromNode == node)
			return toNode;
		else if (toNode == node)
			return fromNode;
		else
			return null;
	}

	/**
	 * Gets the common node between this edge and another edge.
	 *
	 * @param edge Another edge to check for a common node.
	 * @return The common node if found, or null if there is no common node between
	 *         the edges.
	 */
	public NodeGraph getCommonNode(EdgeGraph edge) {

		if (fromNode == edge.toNode || fromNode == edge.fromNode)
			return fromNode;
		if (toNode == edge.toNode || toNode == edge.fromNode)
			return toNode;
		else
			return null;
	}

	/**
	 * Gets an integer attribute, given the name of the field.
	 *
	 * @param name The name of the field for which to retrieve the integer
	 *             attribute.
	 * @return The integer attribute associated with the specified field name.
	 */
	public Integer getIntegerAttribute(String name) {
		return attributes.get(name).getInteger();
	}

	/**
	 * Gets a double attribute, given the name of the field.
	 *
	 * @param name The name of the field for which to retrieve the double attribute.
	 * @return The double attribute associated with the specified field name.
	 */
	public Double getDoubleAttribute(String name) {
		return this.attributes.get(name).getDouble();
	}

	/**
	 * Get a string attribute, given the name of the field.
	 *
	 * @param name The name of the field for which to retrieve the string attribute.
	 * @return The string attribute associated with the specified field name.
	 */
	public String getStringAttribute(String name) {
		return attributes.get(name).getString();
	}
}
