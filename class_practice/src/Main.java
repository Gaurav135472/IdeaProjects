import java.util.Arrays;




public class Main {

    public static void main(String[] args) {
        int[] data0 = {5, 7, 1, 3, 8, 9, 3, 4, 5, 6};
        System.out.println("before bubbleSort data0[] = " + Arrays.toString(data0));
        bubbleSort(data0);
    }

    public static void swap(int[] array, int i1, int i2) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    public static void bubbleSort(int[] array) {
        int count = 0;
        int swapCount = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j] >= array[j + 1])
                    swap(array, j, j + 1);
                count ++;
            }

        }

        System.out.println(count);
        System.out.println(swapCount);
    }

    
}