import java.util.ArrayList;

public class MazeSolver implements IMazeSolver {

	public class Pair {
		private int row;
		private int col;

		public Pair(int row, int col) {
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return this.row;
		}

		public int getCol() {
			return this.col;
		}
	}

	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static final int[][] DELTAS = new int[][]{
			{-1, 0}, // North
			{1, 0}, // South
			{0, 1}, // East
			{0, -1} // West
	};

	private Maze maze;
	private boolean[][] visited;
	private int endRow, endCol;
	private Pair[][] parent;
	ArrayList<Integer> findSteps;

	public MazeSolver() {
		// TODO: Initialize variables.
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
	cthis.parent = new Pair[maze.getRows()][maze.getColumns()];
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
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
				this.parent[i][j] = null;
			}
		}

		this.endRow = endRow;
		this.endCol = endCol;

		if (startRow == endRow && startCol == endCol) {
			solve(startRow, startCol);
			return 0;
		} else {
			return solve(startRow, startCol);
		}
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

	private Integer solve(int row, int col) {
		// initialize frontier to visit
		ArrayList<Pair> frontier = new ArrayList<>(); // by default null
		frontier.add(new Pair(row, col));

		visited[row][col] = true;
		maze.getRoom(row, col).onPath = true;
		findSteps.add(1);

		int steps = 0;
		int finalSteps = 0;
		int index = 1;
		boolean solved = false;

		while (!frontier.isEmpty()) {
			ArrayList<Pair> next = new ArrayList<>(); // by default null
			findSteps.add(0);

			for (Pair pair : frontier) {
				int currRow = pair.getRow();
				int currCol = pair.getCol();

				if (currRow == endRow && currCol == endCol) {
					finalSteps = steps;
					solved = true;
				}

				ArrayList<Pair> nbrPairs = findNeighborPairs(currRow, currCol);

				for (Pair nbPair : nbrPairs) {
					int r = nbPair.getRow();
					int c = nbPair.getCol();

					if (!visited[r][c]) {
						visited[r][c] = true;
						parent[r][c] = pair;
						next.add(new Pair(r, c));
						findSteps.set(index, findSteps.get(index) + 1);
					}
				}
			}
			steps++;
			index++;
			frontier = next;
		}


		Pair parentPair = parent[this.endRow][this.endCol];
		while (parentPair != null) {
			int parentRow = parentPair.getRow();
			int parentCol = parentPair.getCol();

			Room parentRoom = maze.getRoom(parentRow, parentCol);
			parentRoom.onPath = true;

			parentPair = parent[parentRow][parentCol];
		}

		if (!solved) {
			return null;
		} else {
			return finalSteps;
		}
	}

	public ArrayList<Pair> findNeighborPairs(int r, int c) {
		ArrayList<Pair> nbrPairs = new ArrayList<>();
		for (int direction = 0; direction < 4; ++direction) {
			if (canGo(r, c, direction)) { // can we go in that direction?
				// yes we can :)
				nbrPairs.add(new Pair(r + DELTAS[direction][0], c + DELTAS[direction][1]));
			}
		}
		return nbrPairs;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.

		if (k >= findSteps.size() - 1) {
			return 0;
		}

		return findSteps.get(k);
	}

}

