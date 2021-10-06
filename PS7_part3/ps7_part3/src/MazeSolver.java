import java.util.*;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][]{
			{-1, 0}, // North
			{0, 1}, // East
			{0, -1}, // West
			{1, 0} // South
	};

	private Maze maze;
	private boolean[][] visited;
	private boolean solved;
	private int startFear = 0;

	public MazeSolver() {
		// TODO: Initialize variables.
		this.maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		this.solved = false;
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		this.solved = false;

		if (startRow == endRow && startCol == endCol) {
			return 0;
		}

		PriorityQueue<Entry> pQueue = new PriorityQueue<>();
		Map<Integer, Entry> map = new HashMap<Integer, Entry>();

		// set all visited flag to false before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				Entry entry = new Entry(i, j, Integer.MAX_VALUE);
				Pair pair = new Pair(i, j);
				map.put(pair.hashCode(), entry);
				pQueue.add(entry);
			}
		}

		Pair pair = new Pair(startRow, startCol);

		Entry currEntry = map.get(pair.hashCode());
		pQueue.remove(currEntry);
		pQueue.add(new Entry(startRow, startCol, 0));
		Entry curr = pQueue.peek();

		while (!(curr.getRow() == endRow && curr.getCol() == endCol)) {
			int row = curr.getRow();
			int col = curr.getCol();
			int estimate = curr.getEstimate();

			for (int dir = 0; dir < 4; dir++) {
				int r = row + DELTAS[dir][0];
				int c = col + DELTAS[dir][1];

				if (r >= 0 && r < maze.getRows()
						&& c >= 0 && c < maze.getColumns() && !visited[r][c]) {
					Pair p = new Pair(r, c);
					Function<Room, Integer> transformer = WALL_FUNCTIONS.get(dir);
					int scary = transformer.apply(maze.getRoom(row, col));

					if (scary == EMPTY_SPACE) {
						scary += 1;
					}

					if (scary != TRUE_WALL) {
						currEntry = map.get(p.hashCode());

						// relax
						if (scary + estimate < currEntry.getEstimate()) {
							//decreaseKey
							pQueue.remove(currEntry);
							pQueue.add(new Entry(r, c, scary + estimate));
						}
					}
				}
			}
			pQueue.remove(curr);
			visited[row][col] = true;
			curr = pQueue.poll();

			// if there is no next smallest estimate
			if (curr.getEstimate() == Integer.MAX_VALUE) {
				break;
			}

			// if reaches end room
			if (curr.getRow() == endRow && curr.getCol() == endCol && curr.getEstimate() != TRUE_WALL) {
				solved = true;
			}
		}

		int result = curr.getEstimate();

		return solved ? result : null;
	}


	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		this.solved = false;

		if (startRow == endRow && startCol == endCol) {
			return 0;
		}

		PriorityQueue<Entry> pQueue = new PriorityQueue<>();
		HashMap<Integer, Entry> map = new HashMap<>();

		// set all visited flag to false before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				Entry entry = new Entry(i, j, Integer.MAX_VALUE);
				Pair pair = new Pair(i, j);
				map.put(pair.hashCode(), entry);
				pQueue.add(entry);
			}
		}

		Pair pair = new Pair(startRow, startCol);
		pQueue.add(new Entry(startRow, startCol, this.startFear));
		Entry curr = pQueue.peek();

		while (!(curr.getRow() == endRow && curr.getCol() == endCol)) {
			int row = curr.getRow();
			int col = curr.getCol();
			int estimate = curr.getEstimate();
			visited[row][col] = true;

			for (int dir = 0; dir < 4; dir++) {
				int r = row + DELTAS[dir][0];
				int c = col + DELTAS[dir][1];

				if (r >= 0 && r < maze.getRows()
						&& c >= 0 && c < maze.getColumns() && !visited[r][c]) {
					Pair p = new Pair(r, c);
					Function<Room, Integer> transformer = WALL_FUNCTIONS.get(dir);
					int scary = transformer.apply(maze.getRoom(row, col));

					if (scary == EMPTY_SPACE) {
						Entry newEntry = new Entry(r, c, estimate + 1);
						pQueue.add(newEntry);
						map.put(pair.hashCode(), newEntry);
					}

					if (scary != TRUE_WALL && scary != EMPTY_SPACE) {
						Entry currEntry = map.get(p.hashCode());
						int newEstimate = Math.max(scary, estimate);

						if (newEstimate < currEntry.getEstimate()) {
							//decreaseKey
							Entry newEntry = new Entry(r, c, newEstimate);
							pQueue.add(newEntry);
							map.put(pair.hashCode(), newEntry);
						}
					}
				}
			}
			curr = pQueue.poll();

			// if there is no next smallest estimate
			if (curr.getEstimate() == Integer.MAX_VALUE) {
				break;
			}

			// if reaches end room
			if (curr.getRow() == endRow && curr.getCol() == endCol && curr.getEstimate() != TRUE_WALL) {
				solved = true;
			}
		}
		int result = curr.getEstimate();

		return solved ? result : null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		if (startRow == endRow && startCol == endCol) {
			return 0;
		}

		// normal path search from start to end
		Integer normalPath = bonusSearch(startRow, startCol, endRow, endCol);
		boolean normalSolved = this.solved;

		if (!normalSolved) { return null; }

		// check if special room can be reached
		bonusSearch(startRow, startCol, sRow, sCol);
		boolean specialReached = this.solved;

		if (!specialReached) { return normalPath; }

		// normal path search from special to end
		this.startFear = -1;
		Integer specialPath = bonusSearch(sRow, sCol, endRow, endCol);
		boolean specialSolved = this.solved;
		this.startFear = 0;

		return specialSolved ? Math.min(normalPath, specialPath) : normalPath;
	}

	private class Pair {
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

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Pair) {
				Pair pair = (Pair) obj;
				return this.row == pair.getRow()
						&& this.col == pair.getCol();
			}
			return false;
		}

		@Override
		public int hashCode() {
			return (this.row << 16) + this.col;
		}
	}

	private class Entry implements Comparable<Entry> {
		private int row, col, estimate;

		public Entry(int row, int col, int estimate) {
			this.row = row;
			this.col = col;
			this.estimate = estimate;
		}

		public int getRow() { return this.row; }
		public int getCol() { return this.col; }
		public int getEstimate() { return this.estimate; }

		@Override
		public int compareTo(Entry other) {
			if (this.estimate > other.getEstimate()) {
				return 1;
			} else if (this.estimate < other.getEstimate()) {
				return -1;
			} else {
				return 0;
			}
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Entry) {
				Entry ent = (Entry) obj;
				return this.row == ent.getRow()
						&& this.col == ent.getCol()
						&& this.estimate == ent.getEstimate();
			}
			return false;
		}

		@Override
		public String toString() {
			return "row: " + this.row + " col: " + this.col + " est: " + this.estimate;
		}
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-sample.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.bonusSearch(0, 0, 0, 5, 0, 3));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

