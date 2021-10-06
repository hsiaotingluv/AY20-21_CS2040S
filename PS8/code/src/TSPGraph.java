import java.util.*;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        int numOfPoints = map.getCount();
        double[][] adjMatrix = new double[numOfPoints][numOfPoints];

        for (int i = 0; i < numOfPoints; i++) {
            for (int j = 0; j < numOfPoints; j++) {
                adjMatrix[i][j] = map.pointDistance(i, j);
            }
        }

        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        // Initialize priority queue
        for (int i = 0; i < numOfPoints; i++) {
            pq.add(i, Double.MAX_VALUE);
        }

        pq.decreasePriority(0, 0.0);

        // Initialise visited set
        HashSet<Integer> visited = new HashSet<>();
        visited.add(0);

        // Initialize parent hash table
        HashMap<Integer, Integer> parent = new HashMap<>();
        parent.put(0, null);

        while (!pq.isEmpty()) {
            int currIndex = pq.extractMin();
            visited.add(currIndex);

            for (int adjIndex = 0; adjIndex < numOfPoints; adjIndex++) {
                double dist = adjMatrix[currIndex][adjIndex];
                if (dist == 0.0) {
                    continue;
                }

                if (!visited.contains(adjIndex)) {
                    pq.decreasePriority(adjIndex, dist);
                    if (!parent.containsKey(adjIndex)
                            || dist < adjMatrix[adjIndex][parent.get(adjIndex)]) {
                        parent.put(adjIndex, currIndex);
                    }
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : parent.entrySet()) {
            if (entry.getValue() != null) {
                map.setLink(entry.getKey(), entry.getValue(), false);
            }
        }
        map.redraw();

    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);

        int numOfPoints = map.getCount();
        int[] parent = new int[numOfPoints];
        boolean[] visited = new boolean[numOfPoints];
        HashMap<Integer, ArrayList<Integer>> hashmap = new HashMap<>();

        for (int i = -1 ; i < numOfPoints; i++) {
            hashmap.put(i, new ArrayList<Integer>());
            if (i != -1) {
                visited[i] = false;
            }
        }

        for (int i = 0 ; i < numOfPoints; i++) {
            parent[i] = map.getLink(i);
            Integer key = map.getLink(i);
            hashmap.get(key).add(i);
            map.eraseLink(i, false);
        }

        Integer curr = 0;
        Integer temp = -1;

        while (!(curr == 0 && hashmap.get(0).isEmpty())) {
            if (hashmap.get(curr).isEmpty()) {
                Integer prev = parent[curr];
                // if is leaf
                if (!visited[curr]) {
                    visited[curr] = true;
                    temp = curr;
                }
                curr = prev;
            } else {
                Integer next = hashmap.get(curr).remove(0);
                if (!visited[curr]) {
                    visited[curr] = true;
                    map.setLink(curr, next, false);
                } else {
                    map.setLink(temp, next, false);
                }
                curr = next;
            }
        }
        map.setLink(temp, 0, false);

        map.redraw();
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        int numOfPoints = map.getCount();
        int[] visited = new int[numOfPoints];

        // initialize visited
        for (int i = 0; i < numOfPoints; i++) {
            visited[i] = -1;
        }

        for (int i = 0; i < numOfPoints; i++) {
            int parent = map.getLink(i);

            if (parent < 0 || visited[parent] != -1) {
                return false;
            } else {
                visited[parent] = parent;
            }
        }

        int iter = 0;
        final int start = 0;
        int curr = 0;
        int parent = -1;

        while (start != parent) {
            parent = map.getLink(curr);
            curr = parent;
            iter++;
        }

        return iter == numOfPoints;
    }

    @Override
    public double tourDistance(TSPMap map) {
        if (!isValidTour(map)) {
            return -1;
        }

        int numOfPoints = map.getCount();
        double sum = 0.0;

        for (int i = 0; i < numOfPoints; i++) {
            sum += map.pointDistance(i, map.getLink(i));
        }

        return sum;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "/Users/hsiaotingluv/Desktop/CS2040S/PS/PS8/code/hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        // graph.MST(map);
         graph.TSP(map);
         System.out.println(graph.isValidTour(map));
         System.out.println(graph.tourDistance(map));
    }
}
