import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int j = 4000;

        Integer[] arr = new Integer[j];
        for (int i =0; i < j; i++) {
            arr[i] = (int) (10000 * Math.random());
        }

        Sorting.mergeSort(arr, Integer::compareTo);
        System.out.println(Arrays.toString(arr));
    }
}