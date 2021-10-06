import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
		{ -1, 0 }, // North
		{ 1, 0 }, // South
		{ 0, 1 }, // East
		{ 0, -1 } // West
	};

	private class Triplet {
		private int first;
		private int second;
		private int third;

		public Triplet(int first, int second, int third) {
			this.first = first;
			this.second = second;
			this.third = third;
		}

		public int getFirst() {
			return this.first;
		}

		public int getSecond() {
			return this.second;
		}

		public int getThird() {
			return this.third;
		}
	}

	private Maze maze;
	private boolean[][][] superVisited;
	private boolean[][] visited;
	private int endRow, endCol;
	private int startRow, startCol;
	private Triplet[][][] parent;
	private ArrayList<Integer> findSteps;
	private int superpower = 0;

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		initializePathSearch(startRow, startCol, endRow, endCol);

		if (startRow == endRow && startCol == endCol) {
			solve(startRow, startCol);
			return 0;
		} else {
			return solve(startRow, startCol);
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		this.superpower = superpowers;
		initializePathSearch(startRow, startCol, endRow, endCol);

		if (startRow == endRow && startCol == endCol) {
			solve(startRow, startCol);
			return 0;
		} else {
			return solve(startRow, startCol);
		}
	}

	public void initializePathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		this.startRow = startRow;
		this.startCol = startCol;
		this.superVisited = new boolean[maze.getRows()][maze.getColumns()][this.superpower + 1];
		this.parent = new Triplet[maze.getRows()][maze.getColumns()][this.superpower + 1];
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		this.findSteps = new ArrayList<>();

		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search

		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				for (int k = 0; k < this.superpower + 1; k++) {
					this.superVisited[i][j][k] = false;
					this.visited[i][j] = false;
					maze.getRoom(i, j).onPath = false;
					this.parent[i][j][k] = null;
				}
			}
		}

		this.endRow = endRow;
		this.endCol = endCol;
	}

	private boolean canGo(int row, int col, int dir) {
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	private boolean canBreakWall(int row, int col, int dir, int superpower) {
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;

		switch (dir) {
			case NORTH:
				return maze.getRoom(row, col).hasNorthWall() && superpower > 0;
			case SOUTH:
				return maze.getRoom(row, col).hasSouthWall() && superpower > 0;
			case EAST:
				return maze.getRoom(row, col).hasEastWall() && superpower > 0;
			case WEST:
				return maze.getRoom(row, col).hasWestWall() && superpower > 0;
		}

		return false;
	}

	private Integer solve(int row, int col) {
		// initialize frontier to visit
		Queue<Triplet> frontier = new ArrayDeque<>();
		frontier.add(new Triplet(row, col, this.superpower));

		superVisited[row][col][this.superpower] = true;
		maze.getRoom(row, col).onPath = true;
		visited[row][col] = true;
		findSteps.add(1);

		int steps = 0;
		int finalSteps = 0;
		int finalPowers = 0;
		boolean solved = false;

		while (!frontier.isEmpty()) {
			Queue<Triplet> next = new ArrayDeque<>();
			findSteps.add(0);

			for (Triplet triplet : frontier) {
				int currRow = triplet.getFirst();
				int currCol = triplet.getSecond();
				int superpower = triplet.getThird();

				if (!visited[currRow][currCol]) {
					visited[currRow][currCol] = true;
					findSteps.set(steps, findSteps.get(steps) + 1);
				}

				if (currRow == endRow && currCol == endCol && !solved) {
					finalSteps = steps;
					finalPowers = superpower;
					solved = true;
				}

				for (int direction = 0; direction < 4; direction++) {
					if (canGo(currRow, currCol, direction)) {
						int r = currRow + DELTAS[direction][0];
						int c = currCol + DELTAS[direction][1];

						if (!superVisited[r][c][superpower]) {
							superVisited[r][c][superpower] = true;
							parent[r][c][superpower] = triplet;
							next.add(new Triplet(r, c, superpower));
						}
					} else {
						if (canBreakWall(currRow, currCol, direction, superpower)) {
							int r = currRow + DELTAS[direction][0];
							int c = currCol + DELTAS[direction][1];

							if (!superVisited[r][c][superpower-1]) {
								superVisited[r][c][superpower-1] = true;
								parent[r][c][superpower-1] = triplet;
								next.add(new Triplet(r, c, superpower-1));
							}
						}
					}
				}
			}
			steps++;
			frontier = next;
		}

		Room currRoom = maze.getRoom(this.endRow, this.endCol);
		currRoom.onPath = solved;

		Triplet parentInfo = parent[endRow][endCol][finalPowers];

		while (parentInfo != null) {
			int parentRow = parentInfo.getFirst();
			int parentCol = parentInfo.getSecond();
			int parentPow = parentInfo.getThird();

			Room parentRoom = maze.getRoom(parentRow, parentCol);
			parentRoom.onPath = true;

			parentInfo = parent[parentRow][parentCol][parentPow];
		}


		return solved ? finalSteps : null;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		if (k >= findSteps.size() - 1) {
			return 0;
		}

		return findSteps.get(k);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-dense.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 2, 6));
//			MazePrinter.printMaze(maze);
//			ImprovedMazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
