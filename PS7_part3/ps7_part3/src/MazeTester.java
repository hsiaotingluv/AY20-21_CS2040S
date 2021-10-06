public class MazeTester {
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final IMazeSolver solver = new MazeSolver();

  public static void main(String[] args) {
    test1();
    test2();
    test3();
    test4();
  }

  private static void test1() {
    try {
      Maze maze = Maze.readMaze("haunted-maze-sample.txt");
      solver.initialize(maze);
      assertEquals(401, solver.pathSearch(0, 0, 1, 4));
      assertEquals(498, solver.pathSearch(0, 0, 1, 5));
      assertEquals(212, solver.pathSearch(0, 1, 1, 3));
      assertEquals(219, solver.pathSearch(1, 2, 1, 4));
      assertEquals(0, solver.pathSearch(1, 0, 1, 0));
      assertEquals(99, solver.pathSearch(0, 5, 1, 5));
      assertEquals(165, solver.pathSearch(0, 0, 1, 0));
      assertEquals(0, solver.pathSearch(0, 0, 0, 0));
      System.out.println("Test 1: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 1: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test2() {
    try {
      Maze maze = Maze.readMaze("maze-empty.txt");
      solver.initialize(maze);
      assertEquals(1, solver.pathSearch(0, 2, 0, 3));
      assertEquals(5, solver.pathSearch(0, 0, 3, 2));
      assertEquals(2, solver.pathSearch(1, 2, 0, 3));
      assertEquals(2, solver.pathSearch(3, 1, 1, 1));
      assertEquals(5, solver.pathSearch(3, 3, 0, 1));
      assertEquals(0, solver.pathSearch(0, 2, 0, 2));
      assertEquals(3, solver.pathSearch(0, 0, 0, 3));
      assertEquals(3, solver.pathSearch(1, 1, 0, 3));
      assertEquals(1, solver.pathSearch(2, 1, 2, 0));
      System.out.println("Test 2: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 2: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test3() {
    try {
      Maze maze = Maze.readMaze("haunted-maze-simple.txt");
      solver.initialize(maze);
      assertEquals(null, solver.pathSearch(0, 3, 0, 5));
      assertEquals(87, solver.pathSearch(0, 2, 0, 1));
      assertEquals(0, solver.pathSearch(0, 1, 0, 1));
      assertEquals(null, solver.pathSearch(0, 1, 0, 5));
      assertEquals(252, solver.pathSearch(0, 4, 0, 2));
      assertEquals(1, solver.pathSearch(0, 3, 0, 4));
      assertEquals(null, solver.pathSearch(0, 5, 0, 3));
      assertEquals(251, solver.pathSearch(0, 3, 0, 2));
      assertEquals(null, solver.pathSearch(0, 4, 0, 5));
      System.out.println("Test 3: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 3: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test4() {
    try {
      Maze maze = Maze.readMaze("maze-dense.txt");
      solver.initialize(maze);
      assertEquals(0, solver.pathSearch(0, 0, 0, 0));
      assertEquals(null, solver.pathSearch(0, 2, 0, 1));
      assertEquals(null, solver.pathSearch(3, 1, 0, 1));
      assertEquals(null, solver.pathSearch(0, 1, 0, 3));
      assertEquals(0, solver.pathSearch(0, 2, 0, 2));
      assertEquals(0, solver.pathSearch(3, 2, 3, 2));
      assertEquals(null, solver.pathSearch(2, 2, 2, 3));
      System.out.println("Test 4: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 4: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void assertEquals(Integer value1, Integer value2) throws CustomException {
    if ((value1 != null && !value1.equals(value2)) || (value1 == null && value2 != null)) {
      int lineNo = Thread.currentThread().getStackTrace()[2].getLineNumber();
      throw new CustomException("Line " + lineNo + ": Expected <" + value1 + "> but got <" + value2 + ">");
    }
  }

  private static class CustomException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String message;

    private CustomException(String message) {
      this.message = message;
    }

    @Override
    public String toString() {
      return this.message;
    }
  }
}
