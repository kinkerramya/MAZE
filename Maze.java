package Graphs;

import java.awt.Color;
import java.util.Scanner;
import java.util.Random;

public class Maze {
    private int N;                 // dimension of maze
    private boolean[][] north;     // is there a wall to north of cell i, j
    private boolean[][] east;
    private boolean[][] south;
    private boolean[][] west;
    private boolean[][] visited;
    private boolean done = false;

    public Maze(int N) {
        this.N = N;
        DrawKit.setScale(0, N+2);
        init();
        generate();
    }

    private void init() {
        // initialize border cells as already visited
        visited = new boolean[N+2][N+2];
        for (int x = 0; x < N+2; x++) {
            visited[x][0] = true;
            visited[x][N+1] = true;
        }
        for (int y = 0; y < N+2; y++) {
            visited[0][y] = true;
            visited[N+1][y] = true;
        }


        // initialze all walls as present
        north = new boolean[N+2][N+2];
        east  = new boolean[N+2][N+2];
        south = new boolean[N+2][N+2];
        west  = new boolean[N+2][N+2];
        for (int x = 0; x < N+2; x++) {
            for (int y = 0; y < N+2; y++) {
                north[x][y] = true;
                east[x][y]  = true;
                south[x][y] = true;
                west[x][y]  = true;
            }
        }
    }


    // generate the maze
    private void generate(int x, int y) {
        visited[x][y] = true;

        // while there is an unvisited neighbor
        while (!visited[x][y+1] || !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            // pick random neighbor (could use Knuth's trick instead)
            while (true) {
            	Random random=new Random();
                double r = random.nextInt(4);
                if (r == 0 && !visited[x][y+1]) {
                    north[x][y] = false;
                    south[x][y+1] = false;
                    generate(x, y + 1);
                    break;
                }
                else if (r == 1 && !visited[x+1][y]) {
                    east[x][y] = false;
                    west[x+1][y] = false;
                    generate(x+1, y);
                    break;
                }
                else if (r == 2 && !visited[x][y-1]) {
                    south[x][y] = false;
                    north[x][y-1] = false;
                    generate(x, y-1);
                    break;
                }
                else if (r == 3 && !visited[x-1][y]) {
                    west[x][y] = false;
                    east[x-1][y] = false;
                    generate(x-1, y);
                    break;
                }
            }
        }
    }

    // generate the maze starting from lower left
    private void generate() {
        generate(1, 1);


     
    }



    // solve the maze using depth-first search
    private void solve(int x, int y) {
        if (x == 0 || y == 0 || x == N+1 || y == N+1) return;
        if (done || visited[x][y]) return;
        visited[x][y] = true;

        DrawKit.setColor(Color.GREEN);
        DrawKit.drawCircle(x + 0.5, y + 0.5, 0.25);
        DrawKit.show(80);

        // reached middle
        if (x == N/2 && y == N/2) done = true;

        if (!north[x][y]) solve(x, y + 1);
        if (!east[x][y])  solve(x + 1, y);
        if (!south[x][y]) solve(x, y - 1);
        if (!west[x][y])  solve(x - 1, y);

        if (done) return;

        DrawKit.setColor(Color.GRAY);
        DrawKit.drawCircle(x + 0.5, y + 0.5, 0.25);
        DrawKit.show(100);
    }

    // solve the maze starting from the start state
    public void solve() {
        for (int x = 1; x <= N; x++)
            for (int y = 1; y <= N; y++)
                visited[x][y] = false;
        done = false;
        solve(1, 1);
    }

    // draw the maze
    public void draw() {
    	DrawKit.setColor(Color.YELLOW);
    	DrawKit.drawCircle(N/2.0 + 0.5, N/2.0 + 0.5, 0.375);
    	DrawKit.drawCircle(1.5, 1.5, 0.375);

    	DrawKit.setColor(Color.BLACK);
        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                if (south[x][y]) DrawKit.drawLine(x, y, x + 1, y);
                if (north[x][y]) DrawKit.drawLine(x, y + 1, x + 1, y + 1);
                if (west[x][y])  DrawKit.drawLine(x, y, x, y + 1);
                if (east[x][y])  DrawKit.drawLine(x + 1, y, x + 1, y + 1);
            }
        }
        DrawKit.show(1000);
    }

    public static void main(String[] args) {
    	Scanner sc=new Scanner(System.in);
    	System.out.println("Number of Nodes:");
    	int N = sc.nextInt();
    	final Maze maze = new Maze(N);
    	DrawKit.show(0);
    	maze.draw();
        DrawKit.start.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(java.awt.event.ActionEvent evt) {
		    	 
		        maze.solve(); 	
		}
		});
        
        sc.close();
    }

}
