import java.util.ArrayList;
import java.util.Random;

public class SortingTester {

    public static boolean checkSort(ISort sorter, int size) {
        Random rand = new Random(2);
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            int key = rand.nextInt();
            testArray[i] = new KeyValuePair(key, 20);
        }

        // Sorting random keys
        StopWatch watch = new StopWatch();
        watch.start();
        sorter.sort(testArray);
        watch.stop();

        System.out.println("Random keys, " + "Size: " + size + ", Time: " + watch.getTime());

        // check if array is sorted correctly
        boolean result = false;
        for (int i = 0; i < size - 1; i++) {
            if (testArray[i].compareTo(testArray[i+1]) == 1) {
                result = false;
                break;
            } else {
                result = true;
            }
        }

        // Sorting ascending keys
        watch.start();
        sorter.sort(testArray);
        watch.stop();

        System.out.println("Ascending keys, " + "Size: " + size + ", Time: " + watch.getTime());

        // Sorting descending keys
        KeyValuePair[] reversedArray = testArray.clone();

        for (int i = 0; i < size; i++) {
            reversedArray[i] = testArray[size-1-i];
        }

        watch.start();
        sorter.sort(reversedArray);
        watch.stop();

        System.out.println("Descending keys, " + "Size: " + size + ", Time: " + watch.getTime());

        System.out.println("Array is sorted: " + result);

        return result;
    }

    public static boolean isStable(ISort sorter, int size) {
        ArrayList<Integer> list = new ArrayList<Integer>(size);
        for (int i = 1; i <= size/2; i++) {
            list.add(i);
            list.add(i);
        }

        // generate random keyValuePair
        Random rand = new Random();
        KeyValuePair[] testArray = new KeyValuePair[size];
        while (list.size() > 0) {
            for (int i = 0; i < size; i++) {
                int index = rand.nextInt(list.size());
                testArray[i] = new KeyValuePair(list.get(index), i);
                list.remove(index);
            }
        }

        sorter.sort(testArray);

        // check if array is stable
        boolean result = true;
        for (int i = 0; i < size - 1; i++) {
            if (testArray[i].compareTo(testArray[i + 1]) == 0) {
                if (testArray[i].getValue() < testArray[i + 1].getValue()) {
                    result = true;
                } else {
                    result = false;
                    break;
                }
            }
        }

        System.out.println("Sorter is stable: " + result);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Sorter A");
        ISort sorterA = new SorterA();
        SortingTester.checkSort(sorterA, 10000);
        SortingTester.isStable(sorterA, 10000);

        System.out.println("Sorter B");
        ISort sorterB = new SorterB();
        SortingTester.checkSort(sorterB, 10000);
        SortingTester.isStable(sorterB, 10000);

        System.out.println("Sorter C");
        ISort sorterC = new SorterC();
        SortingTester.checkSort(sorterC, 10000);
        SortingTester.isStable(sorterC, 10000);

        System.out.println("Sorter D");
        ISort sorterD = new SorterD();
        SortingTester.checkSort(sorterD, 10000);
        SortingTester.isStable(sorterD, 10000);

        System.out.println("Sorter E");
        ISort sorterE = new SorterE();
        SortingTester.checkSort(sorterE, 10000);
        SortingTester.isStable(sorterE, 10000);

        System.out.println("Sorter F");
        ISort sorterF = new SorterF();
        SortingTester.checkSort(sorterF, 10000);
        SortingTester.isStable(sorterF, 10000);
    }
}