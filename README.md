## How the algorithm works

This program computes the longest path (measured in number of edges) starting from a chosen
start vertex in a Directed Acyclic Graph (DAG). The graph is defined by a list of vertices
and a list of directed edges.

1 → 2  
1 → 3  
2 → 4  
3 → 4
--- High‑level steps

1. Build the adjacency list and indegree map from the list of edges.
2. Run a topological sort using Kahn’s algorithm to get a valid ordering of the vertices.
3. Use dynamic programming over the topological order:
   - Set dist[start] = 0 and all other distances to negative infinity.
   - For each edge u → v (in topological order), update:
     dist[v] = max(dist[v], dist[u] + 1)
4. The longest path length is simply the maximum value in the distance map.

### Assumptions - The graph must be a DAG.
 
 -- Does the solution work for larger graphs?
         - Yes the algorithm scales well because it uses - Adjacency lists, - Kahn’s algorithm for topological sort and DP over the topological order
 -- Can you think of any optimizations?
	-- Right now my vertices are objects. if it was simple numbers, I could use arrays instead of HashMaps, which would make the algorithm faster.
	-- Detect cycles early during topological sort — since the algorithm only works on DAGs, catching a cycle early prevents doing unnecessary work.

 -- What’s the computational complexity of your solution? - O(V + E)

 -- Are there any unusual cases that aren't handled?
   - Yes - Graphs with cycles: The algorithm assumes the graph is a DAG.
   - Empty or null inputs
   - start vertex is not given. 	


