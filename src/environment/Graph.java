package environment;

import processing.core.PVector;
import utility.GameConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by ujansengupta on 3/11/17.
 */

@SuppressWarnings("WeakerAccess")

public class Graph
{

    public Map<Integer, Node> nodeMap = new HashMap<>();
    public Map<Integer, ArrayList<Edge>> adjacencyList;
    public Set<Integer> invalidNodes;

    float diagonalWeight = (float)Math.sqrt(2);
    float edgeWeight = 1f;

    public Graph()
    {
        adjacencyList = new HashMap<>();
    }

    public Map<Integer, ArrayList<Edge>> buildGraph(Set<Integer> invalidNodes)
    {
        this.invalidNodes = invalidNodes;

        int index;
        int tilesX = (int) GameConstants.NUM_TILES.x;
        int tilesY = (int) GameConstants.NUM_TILES.y;

        for (int i = 0; i < tilesY; i++)
        {
            for (int j = 0; j < tilesX; j++)
            {
                index = i * tilesY + j;

                if (invalidNodes.contains(index))
                    continue;

                ArrayList<Edge> edges = new ArrayList<>();

                nodeMap.put(index, new Node(index, new PVector(j, i)));

                /* Check neighbours clockwise */

                if (j - 1 >= 0 && (!invalidNodes.contains(index - 1)))
                    edges.add(new Edge(index, index - 1, edgeWeight));

                if (i + 1 < tilesY && (!invalidNodes.contains(index + tilesX)))
                    edges.add(new Edge(index, index + tilesX, edgeWeight));

                if (j + 1 < tilesX && (!invalidNodes.contains(index + 1)))
                    edges.add(new Edge(index, index + 1, edgeWeight));

                if (i - 1 >= 0 && (!invalidNodes.contains(index - tilesX)))
                    edges.add(new Edge(index, index - tilesX, edgeWeight));

                if (j - 1 >= 0 && i + 1 < tilesY && (!invalidNodes.contains(index - 1 + tilesX)))
                    edges.add(new Edge(index, index - 1 + tilesX, diagonalWeight));

                if (j + 1 < tilesX && i + 1 < tilesY && (!invalidNodes.contains(index + 1 + tilesX)))
                    edges.add(new Edge(index, index + 1 + tilesX, diagonalWeight));

                if (j + 1 < tilesX && i - 1 >= 0 && (!invalidNodes.contains(index + 1 - tilesX)))
                    edges.add(new Edge(index, index + 1 - tilesX, diagonalWeight));

                if (i - 1 >= 0 && j - 1 >= 0 && (!invalidNodes.contains(index - 1 - tilesX)))
                    edges.add(new Edge(index, index - 1 - tilesX, diagonalWeight));

                adjacencyList.put(index, edges);

            }
        }

        return adjacencyList;
    }



    /* Helper Methods */

    public class Node
    {
        int nodeID;
        PVector location;

        public Node(int ID, PVector loc)
        {
            this.nodeID = ID;
            this.location = loc;
        }
    }

    public class Edge
    {
        int src, dst;
        float weight;

        public Edge(int src, int dst, float weight)
        {
            this.src = src;
            this.dst = dst;
            this.weight = weight;
        }

        public int getSrc()
        {
            return src;
        }

        public int getDst()
        {
            return dst;
        }

        public float getWeight()
        {
            return weight;
        }

    }
}
