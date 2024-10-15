import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/** I Gaurav Patel, 000898120 certify that this material is my original work. No other person's work has been used without due acknowledgement. I have not made my work available to anyone else */
/**
 * IceSheetAnalyzer class is to analyzes ice sheets for weak spots and cracks.
 * It reads data from a file, processes each ice sheet, and reports on the number of weak spots and the fraction that has cracks.
 */
public class IceSheetAnalyzer {

    /**
     * The main method that run the ice sheet analysis through various methods.
     *
     * @param args  Command-line arguments for main method.
     */
    public static void main(String[] args) {
        File dataFile = new File("src/ICESHEETS_F24.TXT");

        try (Scanner fileScanner = new Scanner(dataFile)) {
            int totalIceSheets = fileScanner.nextInt(); // Total number of ice sheets
            int totalWeakSpots = 0; // total numbers of weak spots
            int totalCracksFound = 0; // total number of cracks
            int sheetWithMostWeakSpots = -1; // Number of sheet with most week spots
            int highestWeakSpots = 0; // highest number of weak spot

            //
            for (int currentSheetIndex = 0; currentSheetIndex < totalIceSheets; currentSheetIndex++) {
                int noCols = fileScanner.nextInt();
                int noRows = fileScanner.nextInt();
                int[][] iceMatrix = new int[noRows][noCols];
                fillIceData(fileScanner, iceMatrix);

                int[] analysisResults = analyzeIceSheet(iceMatrix);
                int weakSpots = analysisResults[0];
                int cracks = analysisResults[1];

                System.out.printf("Sheet %d has %d weak spots\n", currentSheetIndex, weakSpots);

                totalWeakSpots += weakSpots;
                totalCracksFound += cracks;

                if (weakSpots > highestWeakSpots) {
                    highestWeakSpots = weakSpots;
                    sheetWithMostWeakSpots = currentSheetIndex;
                }
            }

            // Prints all the results derived from ice sheet
            System.out.println("Total weak spots detected across all ice sheets: " + totalWeakSpots);
            System.out.println("The sheet number with the highest weak spots is " + sheetWithMostWeakSpots +
                    " with a total of " + highestWeakSpots + " weak spots.");
            if (totalWeakSpots > 0) {
                System.out.printf("Out of all weak spots, the fraction that have cracks is: %.3f\n",
                        (double) totalCracksFound / totalWeakSpots);
            } else {
                System.out.println("No weak spots detected.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: The file was not found. Please check the file path.");
        } catch (Exception e) {
            System.err.println("An error occurred while processing the file: " + e.getMessage());
        }
    }

    /**
     * Fills the iceMatrix with data from the scanner.
     *
     * @param scanner   The Scanner used to read the data.
     * @param iceMatrix The matrix representing the ice sheet.
     */
    private static void fillIceData(Scanner scanner, int[][] iceMatrix) {
        for (int p = 0; p < iceMatrix.length; p++) {
            int q = 0;
            while (q < iceMatrix[p].length) {
                iceMatrix[p][q] = scanner.nextInt();
                q++;
            }
        }
    }

    /**
     * Determines if a given value represents a weak spot.
     *
     * @param value The value to check.
     * @return True if the value is less than or equal to 200 and divisible by 50, otherwise false.
     */
    public static boolean isWeakSpot(int value) {
        return value <= 200 && value % 50 == 0;
    }

    /**
     * Analyzes an ice sheet matrix for weak spots and cracks.
     *
     * @param iceMatrix The matrix representing the ice sheet.
     * @return An array where the first element is the number of weak spots and the second element is the number of cracks.
     */
    private static int[] analyzeIceSheet(int[][] iceMatrix) {
        int weakSpotCount = 0;
        int crackCount = 0;

        for (int p = 0; p < iceMatrix.length; p++) {
            int q = 0; // Initialize q for the while loop
            while (q < iceMatrix[p].length) {
                if (isWeakSpot(iceMatrix[p][q])) {
                    weakSpotCount++;
                    if (checkAdjacentForCrack(iceMatrix, p, q)) {
                        crackCount++;
                        System.out.printf("CRACK DETECTED @ [Sheet[%d](%d,%d)]\n",
                                0, p, q); // Change the first parameter to the current sheet index if needed
                    }
                }
                q++; // Increment q
            }
        }
        return new int[]{weakSpotCount, crackCount};
    }

    /**
     * Checks if there is a crack adjacent to a given weak spot.
     *
     * @param iceMatrix The matrix representing the ice sheet.
     * @param pCoord    The p-coordinate of the weak spot.
     * @param qCoord    The q-coordinate of the weak spot.
     * @return True if a crack is found adjacent to the weak spot, otherwise false.
     */
    public static boolean checkAdjacentForCrack(int[][] iceMatrix, int pCoord, int qCoord) {
        int totalRows = iceMatrix.length;
        int totalCols = iceMatrix[0].length;

        // Adjacent offsets for 8 directions (up, down, left, right, and diagonals)
        int[][] adjacentOffsets = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        for (int[] offset : adjacentOffsets) {
            int newPCoord = pCoord + offset[0];
            int newQCoord = qCoord + offset[1];

            if (newPCoord >= 0 && newPCoord < totalRows && newQCoord >= 0 && newQCoord < totalCols) {
                if (iceMatrix[newPCoord][newQCoord] % 10 == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
