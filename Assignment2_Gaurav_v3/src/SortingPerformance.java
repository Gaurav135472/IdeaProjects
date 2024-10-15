import java.util.Arrays;
import java.util.Random;

/**
 * The SortingPerformance class demonstrates the use of different sorting algorithms
 * to sort an array of integers, measure the time taken, count the number of comparisons
 * made, and compute a checksum for verification. It also compares the efficiency of linear
 * search versus binary search after sorting the array.
 *
 * This class includes sorting algorithms such as QuickSort, SelectionSort, InsertionSort,
 * MergeSort, BubbleSort, and RadixSort. Each sorting algorithm is executed multiple
 * times to compute an average time, and the comparisons made by each algorithm are counted.
 * After sorting, linear and binary search performances are compared to assess which method
 * is more efficient.
 *
 * The class measures the time and comparisons for each search and sorting method, stores the
 * results, and displays them. Additionally, this class calculates a checksum for each sorted
 * array to ensure its integrity after sorting.
 *
 * Author: Your Name
 * Student ID: Your ID
 */
public class SortingPerformance {

    // Constant representing the seed for generating random numbers for consistent results
    private static final long RANDOM_SEED = 123456;

    // Constant representing the large size of the dataset for testing search methods
    private static final int LARGE_DATA_SIZE = 100000;

    public static void main(String[] args) {
        // The maximum number of operations for sorting
        final int MAX_OPERATIONS = 1000000;

        // Maximum value for random number generation
        final int MAX_RANDOM_VALUE = 100000;

        // Execute sorting for different array sizes and display results
        displaySortResults(executeAllSortAlgorithms(MAX_RANDOM_VALUE, 20, MAX_OPERATIONS / 20), 20, MAX_OPERATIONS / 20);
        displaySortResults(executeAllSortAlgorithms(MAX_RANDOM_VALUE, 400, MAX_OPERATIONS / 400), 400, MAX_OPERATIONS / 400);
        displaySortResults(executeAllSortAlgorithms(MAX_RANDOM_VALUE, 8000, MAX_OPERATIONS / 8000), 8000, MAX_OPERATIONS / 8000);

        // Compare the performance of linear search and binary search after sorting
        long[] searchTimes = compareSearchEfficiency(LARGE_DATA_SIZE);
        System.out.println("\nComparing linear and binary search efficiency after sorting:");
        System.out.printf("Time taken to sort:          %.7f ms\n", searchTimes[1] / 1_000_000.0);
        System.out.printf("Time taken for linear search: %.7f ms\n", searchTimes[0] / 1_000_000.0);
        System.out.printf("Time taken for binary search: %.7f ms\n", searchTimes[2] / 1_000_000.0);
    }

    /**
     * This method displays the results of the sorting algorithms by printing out the
     * algorithm's name, the time it took to execute, the number of comparisons it made,
     * the average time per comparison, and the checksum of the sorted array.
     *
     * @param results   A 2D array that contains the results of each sorting algorithm,
     *                  including time, comparisons, and checksum.
     * @param arraySize The size of the array that was sorted during testing.
     * @param runs      The number of times each sorting algorithm was executed.
     */
    public static void displaySortResults(long[][] results, int arraySize, int runs) {
        String[] sortingAlgorithms = {"QuickSort", "SelectionSort", "InsertionSort", "MergeSort", "BubbleSort", "RadixSort"};
        System.out.printf("Sorting Results (Array Size: %d, Number of Runs: %d)\n", arraySize, runs);
        System.out.println("==============================================================");
        System.out.printf("%-15s %-12s %-15s %-17s %-10s\n", "Algorithm", "Time (ms)", "Comparisons", "ms/Comparison", "Checksum");
        System.out.println("--------------------------------------------------------------");

        for (int index = 0; index < sortingAlgorithms.length; index++) {
            double timeMs = results[index][0] / 1_000_000.0;
            long comparisons = results[index][1];
            double timePerComparison = comparisons != 0 ? timeMs / comparisons : 0.0;
            long checksum = results[index][2];
            System.out.printf("%-15s %-12.7f %-15d %-17.7f %-10d\n", sortingAlgorithms[index], timeMs, comparisons, timePerComparison, checksum);
        }
        System.out.println();
    }

    /**
     * This method executes multiple sorting algorithms on an array, measures
     * the time it takes to execute each algorithm, counts the number of comparisons
     * made during sorting, and calculates the checksum to verify the correctness of the sorting.
     *
     * @param maxValue The maximum random value for elements in the array.
     * @param size     The size of the array to be sorted.
     * @param runs     The number of times each sorting algorithm will be executed.
     * @return A 2D array containing the time, comparisons, and checksum for each sorting algorithm.
     */
    public static long[][] executeAllSortAlgorithms(int maxValue, int size, int runs) {
        int[] primaryArray = new int[size];
        long[][] sortingResults = new long[6][3];
        int[] workingArray;
        Random random = new Random(RANDOM_SEED);

        for (int i = 0; i < size; i++) {
            primaryArray[i] = random.nextInt(maxValue);
        }

        for (int runCount = 0; runCount < runs; runCount++) {
            // QuickSort
            workingArray = Arrays.copyOf(primaryArray, primaryArray.length);
            long startTime = System.currentTimeMillis();
            long comparisons = performQuickSort(workingArray);
            long endTime = System.currentTimeMillis();
            sortingResults[0][0] += (endTime - startTime);
            sortingResults[0][1] += comparisons;
            sortingResults[0][2] = calculateChecksum(workingArray);

            // SelectionSort
            workingArray = Arrays.copyOf(primaryArray, primaryArray.length);
            startTime = System.currentTimeMillis();
            comparisons = performSelectionSort(workingArray);
            endTime = System.currentTimeMillis();
            sortingResults[1][0] += (endTime - startTime);
            sortingResults[1][1] += comparisons;
            sortingResults[1][2] = calculateChecksum(workingArray);

            // InsertionSort
            workingArray = Arrays.copyOf(primaryArray, primaryArray.length);
            startTime = System.currentTimeMillis();
            comparisons = performInsertionSort(workingArray);
            endTime = System.currentTimeMillis();
            sortingResults[2][0] += (endTime - startTime);
            sortingResults[2][1] += comparisons;
            sortingResults[2][2] = calculateChecksum(workingArray);

            // MergeSort
            workingArray = Arrays.copyOf(primaryArray, primaryArray.length);
            startTime = System.currentTimeMillis();
            comparisons = executeMergeSort(workingArray);
            endTime = System.currentTimeMillis();
            sortingResults[3][0] += (endTime - startTime);
            sortingResults[3][1] += comparisons;
            sortingResults[3][2] = calculateChecksum(workingArray);

            // BubbleSort
            workingArray = Arrays.copyOf(primaryArray, primaryArray.length);
            startTime = System.currentTimeMillis();
            comparisons = performBubbleSort(workingArray);
            endTime = System.currentTimeMillis();
            sortingResults[4][0] += (endTime - startTime);
            sortingResults[4][1] += comparisons;
            sortingResults[4][2] = calculateChecksum(workingArray);

            // RadixSort
            workingArray = Arrays.copyOf(primaryArray, primaryArray.length);
            startTime = System.currentTimeMillis();
            comparisons = performRadixSort(workingArray);
            endTime = System.currentTimeMillis();
            sortingResults[5][0] += (endTime - startTime);
            sortingResults[5][1] += comparisons;
            sortingResults[5][2] = calculateChecksum(workingArray);
        }

        for (int index = 0; index < 6; index++) {
            sortingResults[index][0] /= runs;
            sortingResults[index][1] /= runs;
        }

        return sortingResults;
    }

    /**
     * This function utilizes the QuickSort algorithm, which efficiently sorts an array
     * by picking a pivot element and dividing the array into two parts: one containing elements
     * smaller than the pivot and another containing elements larger than the pivot.
     * It then recursively applies the same process to both parts until the entire array is sorted.
     *
     * @param inputArray The array that needs to be organized.
     * @return The total count of comparisons made during the sorting process.
     */
    public static long performQuickSort(int[] inputArray) {
        return quickSortHelper(inputArray, 0, inputArray.length - 1, 0);
    }

    private static long quickSortHelper(int[] array, int low, int high, long comparisonCount) {
        if (low < high) {
            int pivotIndex = partitionArrayForQuickSort(array, low, high);
            comparisonCount += (high - low);
            comparisonCount = quickSortHelper(array, low, pivotIndex - 1, comparisonCount);
            comparisonCount = quickSortHelper(array, pivotIndex + 1, high, comparisonCount);
        }
        return comparisonCount;
    }

    private static int partitionArrayForQuickSort(int[] array, int start, int end) {
        int pivotValue = array[start];
        int leftPointer = start;
        for (int currentIndex = start + 1; currentIndex <= end; currentIndex++) {
            if (array[currentIndex] < pivotValue) {
                leftPointer++;
                swapElementsInArray(array, currentIndex, leftPointer);
            }
        }
        swapElementsInArray(array, start, leftPointer);
        return leftPointer;
    }

    /**
     * This function carries out the SelectionSort technique, which systematically arranges
     * an array by repeatedly finding the smallest element from the unsorted section
     * and swapping it with the first element of that section.
     *
     * @param arr The input array that needs to be sorted.
     * @return The total count of comparisons performed during the sorting process.
     */
    public static long performSelectionSort(int[] arr) {
        long comparisonCounter = 0;
        for (int index = 0; index < arr.length - 1; index++) {
            int smallestIndex = index;
            for (int nextIndex = index + 1; nextIndex < arr.length; nextIndex++) {
                comparisonCounter++;
                if (arr[nextIndex] < arr[smallestIndex]) {
                    smallestIndex = nextIndex;
                }
            }
            swapElementsInArray(arr, index, smallestIndex);
        }
        return comparisonCounter;
    }

    /**
     * This function implements the InsertionSort algorithm. It sorts an array by gradually
     * building a sorted section and inserting each new element into its appropriate
     * position within that sorted section.
     *
     * @param arr The array that will be sorted.
     * @return The total number of comparisons counted during the sorting process.
     */
    public static long performInsertionSort(int[] arr) {
        long comparisonCounter = 0;
        for (int index = 1; index < arr.length; index++) {
            int currentElement = arr[index];
            int position = index;
            while (position > 0 && arr[position - 1] > currentElement) {
                comparisonCounter++;
                arr[position] = arr[position - 1];
                position--;
            }
            comparisonCounter++;
            arr[position] = currentElement;
        }
        return comparisonCounter;
    }

    /**
     * This function applies the MergeSort algorithm, which divides the array into two halves,
     * sorts both halves, and then merges them back together into a single sorted array.
     *
     * @param inputArr The array to be sorted.
     * @return The total count of comparisons made while sorting the array.
     */
    public static long executeMergeSort(int[] inputArr) {
        int[] auxiliaryArr = new int[inputArr.length];
        return mergeSortHelper(inputArr, auxiliaryArr, 0, inputArr.length - 1, 0);
    }

    private static long mergeSortHelper(int[] array, int[] tempArr, int start, int end, long comparisonCounter) {
        if (start < end) {
            int mid = (start + end) / 2;
            comparisonCounter = mergeSortHelper(array, tempArr, start, mid, comparisonCounter);
            comparisonCounter = mergeSortHelper(array, tempArr, mid + 1, end, comparisonCounter);
            comparisonCounter += mergeSortedSections(array, tempArr, start, mid, end);
        }
        return comparisonCounter;
    }

    private static long mergeSortedSections(int[] arr, int[] auxArr, int low, int mid, int high) {
        long comparisonCounter = 0;
        for (int i = low; i <= high; i++) {
            auxArr[i] = arr[i];
        }
        int leftIndex = low, rightIndex = mid + 1, currentIndex = low;
        while (leftIndex <= mid && rightIndex <= high) {
            comparisonCounter++;
            if (auxArr[leftIndex] <= auxArr[rightIndex]) {
                arr[currentIndex] = auxArr[leftIndex];
                leftIndex++;
            } else {
                arr[currentIndex] = auxArr[rightIndex];
                rightIndex++;
            }
            currentIndex++;
        }
        while (leftIndex <= mid) {
            arr[currentIndex] = auxArr[leftIndex];
            leftIndex++;
            currentIndex++;
        }
        return comparisonCounter;
    }

    /**
     * This function uses the BubbleSort algorithm to arrange an array in increasing order.
     * It repeatedly checks adjacent elements and swaps them if they are in the wrong order.
     * The process continues until no more swaps are needed, indicating that the array is sorted.
     *
     * @param inputArr The array that needs to be sorted.
     * @return The total count of comparisons performed during the sorting process.
     */
    public static long performBubbleSort(int[] inputArr) {
        long comparisonCount = 0;
        for (int lastIndex = inputArr.length - 1; lastIndex > 0; lastIndex--) {
            boolean wasSwapped = false;
            for (int currentIndex = 0; currentIndex < lastIndex; currentIndex++) {
                comparisonCount++;
                if (inputArr[currentIndex] > inputArr[currentIndex + 1]) {
                    swapElementsInArray(inputArr, currentIndex, currentIndex + 1);
                    wasSwapped = true;
                }
            }
            if (!wasSwapped) break;
        }
        return comparisonCount;
    }

    /**
     * This function implements the RadixSort algorithm, which arranges numbers in an array
     * by sorting their individual digits. The process begins with the least significant digit
     * and proceeds towards the most significant digit. RadixSort applies the counting sort
     * algorithm for each digit in order to arrange the numbers correctly.
     *
     * @param inputArray The array containing numbers that need to be sorted.
     * @return The total number of comparisons made during the sorting process.
     */
    public static long performRadixSort(int[] inputArray) {
        long comparisonCount = 0;
        if (inputArray.length == 0) return comparisonCount;
        int maxValue = findMaximumValue(inputArray);
        int exponent = 1;
        while (maxValue / exponent > 0) {
            comparisonCount += applyCountingSort(inputArray, exponent);
            exponent *= 10;
        }
        return comparisonCount;
    }

    private static int findMaximumValue(int[] inputArray) {
        int maximum = inputArray[0];
        for (int index = 1; index < inputArray.length; index++) {
            if (inputArray[index] > maximum) {
                maximum = inputArray[index];
            }
        }
        return maximum;
    }

    private static long applyCountingSort(int[] inputArray, int exponent) {
        long comparisonCount = 0;
        int[] outputArray = new int[inputArray.length];
        int[] countArray = new int[10];

        for (int index = 0; index < inputArray.length; index++) {
            int digit = (inputArray[index] / exponent) % 10;
            countArray[digit]++;
            comparisonCount++;
        }

        for (int index = 1; index < 10; index++) {
            countArray[index] += countArray[index - 1];
        }

        for (int index = inputArray.length - 1; index >= 0; index--) {
            int digit = (inputArray[index] / exponent) % 10;
            outputArray[countArray[digit] - 1] = inputArray[index];
            countArray[digit]--;
            comparisonCount++;
        }

        for (int index = 0; index < inputArray.length; index++) {
            inputArray[index] = outputArray[index];
        }

        return comparisonCount;
    }

    /**
     * This function compares the efficiency of linear search and binary search
     * after the array has been sorted. It performs multiple search iterations and
     * records the time taken for each method to evaluate which search strategy is
     * more effective.
     *
     * @param arraySize The size of the array to be used for testing the search methods.
     * @return An array containing the execution times for linear search, sorting, and binary search.
     */
    public static long[] compareSearchEfficiency(int arraySize) {
        int[] dataArray = new int[arraySize];
        Random randomGenerator = new Random(RANDOM_SEED);
        long[] searchTimings = new long[3];

        for (int index = 0; index < arraySize; index++) {
            dataArray[index] = randomGenerator.nextInt(LARGE_DATA_SIZE);
        }

        long initialChecksum = calculateChecksum(dataArray);

        long sortStart = System.currentTimeMillis();
        performQuickSort(dataArray);
        long sortEnd = System.currentTimeMillis();
        searchTimings[1] = sortEnd - sortStart;

        long sortedChecksum = calculateChecksum(dataArray);
        if (initialChecksum != sortedChecksum) {
            System.out.println("Error: Checksum mismatch after sorting.");
        }

        int searchIterations = 10000;
        long totalLinearSearchTime = 0;
        long totalBinarySearchTime = 0;

        for (int iteration = 0; iteration < searchIterations; iteration++) {
            long linearStart = System.currentTimeMillis();
            performLinearSearch(dataArray, -1);
            long linearEnd = System.currentTimeMillis();
            totalLinearSearchTime += (linearEnd - linearStart);

            long binaryStart = System.currentTimeMillis();
            performBinarySearch(dataArray, 0, dataArray.length - 1, -1);
            long binaryEnd = System.currentTimeMillis();
            totalBinarySearchTime += (binaryEnd - binaryStart);
        }

        searchTimings[0] = totalLinearSearchTime / searchIterations;
        searchTimings[2] = totalBinarySearchTime / searchIterations;

        return searchTimings;
    }

    /**
     * This function implements a linear search, which sequentially checks each element
     * in the array to find the target value. The search continues until the target is found
     * or the end of the array is reached.
     *
     * @param inputArray  The array that will be searched.
     * @param targetValue The value being searched for.
     * @return The index of the target if found, or -1 if not found.
     */
    public static int performLinearSearch(int[] inputArray, int targetValue) {
        for (int index = 0; index < inputArray.length; index++) {
            if (inputArray[index] == targetValue) {
                return index;
            }
        }
        return -1;
    }

    /**
     * This function implements a binary search on a sorted array. It repeatedly divides
     * the search range in half and compares the target value with the middle element
     * of the current range. The process continues until the target is found or the
     * search range is exhausted.
     *
     * @param sortedArray The sorted array to search through.
     * @param lowerBound  The starting index of the search range.
     * @param upperBound  The ending index of the search range.
     * @param targetValue The value being searched for.
     * @return The index of the target if found, or -1 if not found.
     */
    public static int performBinarySearch(int[] sortedArray, int lowerBound, int upperBound, int targetValue) {
        while (lowerBound <= upperBound) {
            int midIndex = (lowerBound + upperBound) / 2;
            if (sortedArray[midIndex] == targetValue) {
                return midIndex;
            } else if (targetValue > sortedArray[midIndex]) {
                lowerBound = midIndex + 1;
            } else {
                upperBound = midIndex - 1;
            }
        }
        return -1;
    }

    /**
     * This function swaps two elements within an array at the specified positions.
     * It exchanges the values found at the provided indices.
     *
     * @param array       The array in which the elements will be swapped.
     * @param firstIndex  The index of the first element.
     * @param secondIndex The index of the second element.
     */
    private static void swapElementsInArray(int[] array, int firstIndex, int secondIndex) {
        int temp = array[firstIndex];
        array[firstIndex] = array[secondIndex];
        array[secondIndex] = temp;
    }

    /**
     * This function calculates the checksum of an array, which is the total sum
     * of all elements. The checksum is used to verify that the array has been
     * sorted correctly without any unintended modifications.
     *
     * @param inputArray The array for which the checksum will be calculated.
     * @return The total checksum (sum of all elements in the array).
     */
    public static long calculateChecksum(int[] inputArray) {
        long checksumTotal = 0;
        for (int index = 0; index < inputArray.length; index++) {
            checksumTotal += inputArray[index];
        }
        return checksumTotal;
    }
}
