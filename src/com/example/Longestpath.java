package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Longestpath {

	public static void main(String[] args) {

		// 1. Create vertices
		Vertex v1 = new Vertex(1);
		Vertex v2 = new Vertex(2);
		Vertex v3 = new Vertex(3);
		Vertex v4 = new Vertex(4);

		List<Vertex> vertices = Arrays.asList(v1, v2, v3, v4);

		// 2. Create edges
		List<Edge> edges = new ArrayList<>();
		edges.add(new Edge(v1, v2));
		edges.add(new Edge(v1, v3));
		edges.add(new Edge(v2, v4));
		edges.add(new Edge(v3, v4));

		// 3. Build adjacency list + indegree
		Map<Vertex, List<Vertex>> adjacencyList = buildAdjacencyList(vertices, edges);
		Map<Vertex, Integer> indegree = buildIndegree(vertices, edges);

		// 4. Topological sort
		List<Vertex> topoOrder = topologicalSort(vertices, adjacencyList, indegree);

		// 5. Print topo order
		System.out.println("Topological order:");
		for (Vertex v : topoOrder) {
			System.out.print(v + " ");
		}

		// 6. Longest path
		Vertex start = v1;
		int longest = longestPath(vertices, adjacencyList, topoOrder, start);

		System.out.println("\nLongest path length from " + start + " = " + longest);
	}

	// ---------------- Helper Methods ----------------

	// now lets build Adjacency list (for each vertex store the list of vertex its pointing to)
	//give every vertex an empty list of neighbors
	public static Map<Vertex, List<Vertex>> buildAdjacencyList(List<Vertex> vertices, List<Edge> edges) {
		Map<Vertex, List<Vertex>> adjacencyList = new HashMap<>();
		for (Vertex v : vertices) {
			adjacencyList.put(v, new ArrayList<>());
		}
		// fill the adjacency list using the edges
		for (Edge e : edges) {
			adjacencyList.get(e.from).add(e.to);
		}
		return adjacencyList;
	}

	///now count indegree : (for each vertex, count how many incoming edges it has)
	//initialize every vertex with indegree 0
	public static Map<Vertex, Integer> buildIndegree(List<Vertex> vertices, List<Edge> edges) {
		Map<Vertex, Integer> indegree = new HashMap<>();
		for (Vertex v : vertices)
			indegree.put(v, 0);
		// For each edge (u → v), increase indegree of v by 1.
		for (Edge e : edges)
			indegree.put(e.to, indegree.get(e.to) + 1);
		return indegree;
	}

	public static List<Vertex> topologicalSort(List<Vertex> vertices, Map<Vertex, List<Vertex>> adjacencyList,
			Map<Vertex, Integer> indegree) {

		Queue<Vertex> queue = new LinkedList<>();
		for (Vertex v : vertices)
			if (indegree.get(v) == 0) // add all indegree 0 with out any no prerequisites 
				queue.add(v);

		List<Vertex> topoOrder = new ArrayList<>();

		while (!queue.isEmpty()) {
			Vertex u = queue.poll();
			topoOrder.add(u);
			
        // "Remove" u by decreasing indegree of its neighbors
			for (Vertex neighbor : adjacencyList.get(u)) {
				indegree.put(neighbor, indegree.get(neighbor) - 1);
				if (indegree.get(neighbor) == 0)
					queue.add(neighbor);
			}
		}
		return topoOrder;
	}

	public static int longestPath(List<Vertex> vertices, Map<Vertex, List<Vertex>> adjacencyList, List<Vertex> topoOrder, Vertex start) {
		Map<Vertex, Integer> dist = new HashMap<>();
		// Initialize all distances to -∞
		for (Vertex v : vertices) {
			dist.put(v, Integer.MIN_VALUE);
		}
		dist.put(start, 0);
		//Relax edge  process vertices in topo order so all prerequisites come first
		for (Vertex u : topoOrder) {
			if (dist.get(u) == Integer.MIN_VALUE)
				continue;
     //dist(neighbor) = max(dist(neighbor), dist(u) + 1),  dist(v) = max(dist(v), dist(u)+1)
			for (Vertex neighbor : adjacencyList.get(u)) {
				int candidate = dist.get(u) + 1;
				if (candidate > dist.get(neighbor)) {
					dist.put(neighbor, candidate);
				}
			}
		}
		int maxDist = 0;
	    for (Vertex v : vertices) {
	        int d = dist.get(v);
	        if (d > maxDist) {
	            maxDist = d;
	        }
	    }
		return maxDist;
	}
}