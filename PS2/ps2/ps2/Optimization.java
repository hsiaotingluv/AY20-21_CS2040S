/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */

public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        int totalLength = dataArray.length;
        int begin = 0;
        int end = totalLength - 1;
        int mid = (end-begin) / 2; // round down

        while (begin < end) {
            mid = begin + ((end - begin) / 2); // round down
            if (dataArray[mid - 1] < dataArray[mid + 1]) {
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        if (dataArray[begin] > dataArray[mid] && dataArray[begin] > dataArray[totalLength-1]) {
            return dataArray[begin];
        } else if (dataArray[mid] > dataArray[totalLength-1]) {
            return dataArray[mid];
        } else {
            return dataArray[totalLength-1];
        }
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
