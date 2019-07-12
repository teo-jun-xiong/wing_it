package com.example.myapplication;

class SSSP_Solver {

    int size = 0;

    SSSP_Solver(int[][] adj_mtx) {
          executeSSSP(adj_mtx);
    }

    // Dijkstra's algorithm
    private void executeSSSP(int[][] adj_mtx) {

        size = adj_mtx.length;
        boolean[] visited = new boolean[size];
        int[] dist = new int[size];

        for (int i = 0; i < size; i++){ dist[i] = Integer.MAX_VALUE; }
        dist[0] = 0;

        for (int j = 0; j < size-1; j++){
            int u = minDistance(dist, visited);
            visited[u] = true;

            for (int v = 0; v < size; v++){
                if (!visited[v] && adj_mtx[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u]+adj_mtx[u][v] < dist[v]){
                    dist[v] = dist[u] + adj_mtx[u][v];
                }
            }
        }
    }

    private int minDistance(int[] dist, boolean[] visited) {

        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < size; v++)
            if (!visited[v] && dist[v] <= min)
            {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }
}
