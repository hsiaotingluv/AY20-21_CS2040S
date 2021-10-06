public class MazeTester {
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final IMazeSolverWithPower solver = new MazeSolverWithPower();

  public static void main(String[] args) {
    test1();
    test2();
    test3();
    test4();
    test5();
  }

  private static void test1() {
    try {
      Maze bigMaze = Maze.readMaze("maze-sample.txt");
      solver.initialize(bigMaze);
      assertEquals(10, solver.pathSearch(3, 3, 4, 0, 0));
      assertEquals(6, solver.pathSearch(3, 3, 4, 0, 1));
      assertEquals(4, solver.pathSearch(3, 3, 4, 0, 2));
      assertEquals(1, solver.numReachable(0));
      assertEquals(4, solver.numReachable(1));
      assertEquals(6, solver.numReachable(2));
      assertEquals(6, solver.numReachable(3));
      assertEquals(5, solver.numReachable(4));
      assertEquals(2, solver.numReachable(5));
      assertEquals(1, solver.numReachable(6));
      assertEquals(0, solver.numReachable(7));
      assertEquals(5, solver.pathSearch(4, 1, 1, 1, 1));
      assertEquals(1, solver.numReachable(0));
      assertEquals(3, solver.numReachable(1));
      assertEquals(3, solver.numReachable(2));
      assertEquals(3, solver.numReachable(3));
      assertEquals(4, solver.numReachable(4));
      assertEquals(5, solver.numReachable(5));
      assertEquals(4, solver.numReachable(6));
      assertEquals(2, solver.numReachable(7));
      assertEquals(0, solver.numReachable(8));
      assertEquals(0, solver.numReachable(9));
      System.out.println("Test 1: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 1: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test2() {
    try {
      Maze bigMaze = Maze.readMaze("maze-dense.txt");
      solver.initialize(bigMaze);
      assertEquals(0, solver.pathSearch(0, 0, 0, 0, 10));
      assertEquals(null, solver.pathSearch(3, 3, 1, 0, 0));
      assertEquals(1, solver.numReachable(0));
      assertEquals(0, solver.numReachable(1));
      assertEquals(0, solver.numReachable(2));
      assertEquals(0, solver.numReachable(3));
      assertEquals(0, solver.numReachable(4));
      assertEquals(null, solver.pathSearch(3, 1, 1, 1, 1));
      assertEquals(1, solver.numReachable(0));
      assertEquals(3, solver.numReachable(1));
      assertEquals(0, solver.numReachable(2));
      assertEquals(0, solver.numReachable(3));
      assertEquals(0, solver.numReachable(4));
      assertEquals(null, solver.pathSearch(3, 3, 1, 0, 2));
      assertEquals(1, solver.numReachable(0));
      assertEquals(2, solver.numReachable(1));
      assertEquals(3, solver.numReachable(2));
      assertEquals(0, solver.numReachable(3));
      assertEquals(0, solver.numReachable(4));
      assertEquals(null, solver.pathSearch(2, 2, 0, 0, 3));
      assertEquals(4, solver.pathSearch(2, 2, 0, 0, 4));
      assertEquals(1, solver.numReachable(0));
      assertEquals(4, solver.numReachable(1));
      assertEquals(6, solver.numReachable(2));
      assertEquals(4, solver.numReachable(3));
      assertEquals(1, solver.numReachable(4));
      assertEquals(0, solver.numReachable(5));
      assertEquals(0, solver.numReachable(6));
      System.out.println("Test 2: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 2: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test3() {
    try {
      Maze bigMaze = Maze.readMaze("maze-big.txt");
      solver.initialize(bigMaze);
      assertEquals(14, solver.pathSearch(2, 7, 8, 7, 2));
      assertEquals(1, solver.numReachable(0));
      assertEquals(4, solver.numReachable(1));
      assertEquals(8, solver.numReachable(2));
      assertEquals(9, solver.numReachable(3));
      assertEquals(8, solver.numReachable(4));
      assertEquals(7, solver.numReachable(5));
      assertEquals(7, solver.numReachable(6));
      assertEquals(7, solver.numReachable(7));
      assertEquals(10, solver.numReachable(8));
      assertEquals(12, solver.numReachable(9));
      assertEquals(10, solver.numReachable(10));
      assertEquals(8, solver.numReachable(11));
      assertEquals(5, solver.numReachable(12));
      assertEquals(7, solver.numReachable(13));
      assertEquals(7, solver.numReachable(14));
      assertEquals(5, solver.numReachable(15));
      assertEquals(3, solver.numReachable(16));
      assertEquals(1, solver.numReachable(17));
      assertEquals(1, solver.numReachable(18));
      assertEquals(0, solver.numReachable(19));
      assertEquals(0, solver.numReachable(24));
      assertEquals(23, solver.pathSearch(11, 0, 0, 0, 0));
      assertEquals(1, solver.numReachable(0));
      assertEquals(1, solver.numReachable(1));
      assertEquals(1, solver.numReachable(2));
      assertEquals(1, solver.numReachable(3));
      assertEquals(2, solver.numReachable(4));
      assertEquals(4, solver.numReachable(5));
      assertEquals(4, solver.numReachable(6));
      assertEquals(5, solver.numReachable(7));
      assertEquals(4, solver.numReachable(8));
      assertEquals(4, solver.numReachable(9));
      assertEquals(6, solver.numReachable(10));
      assertEquals(8, solver.numReachable(11));
      assertEquals(10, solver.numReachable(12));
      assertEquals(10, solver.numReachable(13));
      assertEquals(9, solver.numReachable(14));
      assertEquals(9, solver.numReachable(15));
      assertEquals(8, solver.numReachable(16));
      assertEquals(5, solver.numReachable(17));
      assertEquals(6, solver.numReachable(18));
      assertEquals(5, solver.numReachable(19));
      assertEquals(4, solver.numReachable(20));
      assertEquals(3, solver.numReachable(21));
      assertEquals(3, solver.numReachable(22));
      assertEquals(3, solver.numReachable(23));
      assertEquals(1, solver.numReachable(24));
      assertEquals(2, solver.numReachable(25));
      assertEquals(1, solver.numReachable(26));
      assertEquals(0, solver.numReachable(27));
      System.out.println("Test 3: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 3: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test4() {
    try {
      Maze bigMaze = Maze.readMaze("maze-custom.txt");
      solver.initialize(bigMaze);
      assertEquals(8, solver.pathSearch(0, 0, 4, 0, 2));
      assertEquals(1, solver.numReachable(0));
      assertEquals(2, solver.numReachable(1));
      assertEquals(3, solver.numReachable(2));
      assertEquals(3, solver.numReachable(3));
      assertEquals(2, solver.numReachable(4));
      assertEquals(1, solver.numReachable(5));
      assertEquals(1, solver.numReachable(6));
      assertEquals(1, solver.numReachable(7));
      assertEquals(1, solver.numReachable(8));
      assertEquals(0, solver.numReachable(9));
      assertEquals(0, solver.numReachable(19));
      assertEquals(0, solver.numReachable(24));
      System.out.println("Test 4: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 4: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void test5() {
    try {
      Maze bigMaze = Maze.readMaze("maze-skip.txt");
      solver.initialize(bigMaze);
      assertEquals(4, solver.pathSearch(1, 1, 0, 2, 1));
      assertEquals(1, solver.numReachable(0));
      assertEquals(3, solver.numReachable(1));
      assertEquals(1, solver.numReachable(2));
      assertEquals(0, solver.numReachable(3));
      assertEquals(1, solver.numReachable(4));
      assertEquals(0, solver.numReachable(5));
      System.out.println("Test 5: " + ANSI_GREEN + "pass" + ANSI_RESET);
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Test 5: " + ANSI_RED + "fail" + ANSI_RESET);
    }
  }

  private static void assertEquals(Integer value1, Integer value2) throws CustomException {
    if (value1 != value2) {
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
