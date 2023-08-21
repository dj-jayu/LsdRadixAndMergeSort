import java.security.InvalidParameterException;
import java.util.*;

/**
 * Your implementation of various divide & conquer sorting algorithms.
 */

public class Sorting {

    private static <T> void mergeTwoArrays(T[] arr1, T[] arr2, T[] destiny, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        int t = 0;
        while (i < arr1.length && j < arr2.length) {
            if (comparator.compare(arr1[i], arr2[j]) <= 0) {
                destiny[t] = arr1[i];
                i++;
            } else {
                destiny[t] = arr2[j];
                j++;
            }
            t++;
        }
        while (i < arr1.length) {
            destiny[t] = arr1[i];
            i++;
            t++;
        }
        while (j < arr2.length) {
            destiny[t] = arr2[j];
            j++;
            t++;
        }
    }
    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(n log n)
     * And a best case running time of: O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: You may need to create a helper method that merges two arrays
     * back into the original T[] array. If two data are equal when merging,
     * think about which subarray you should pull from first.
     *
     * You may assume that the passed in array and comparator are both valid
     * and will not be null.
     *
     * @param <T>        Data type to sort.
     * @param arr        The array to be sorted.
     * @param comparator The Comparator used to compare the data in arr.
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {return;}
        T[] newArr = mergeRec(arr, 0, arr.length - 1, comparator);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = newArr[i];
        }
    }
    private static <T> T[] mergeRec(T[] arr, int left, int right, Comparator<T> comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        if (left == right) {
            return (T[]) new Object[]{arr[left]};
        }

        int mid = (right - left) / 2 + left;
        if ((right - left) % 2 == 0) {
            mid -= 1;
        }
        T[] leftArr = mergeRec(arr, left, mid, comparator);
        T[] rightArr = mergeRec(arr, mid + 1, right, comparator);


        T[] newArr = (T[]) new Object[leftArr.length + rightArr.length];
        mergeTwoArrays(leftArr, rightArr, newArr, comparator);

        return newArr;
    }

    private static int findNumberOfDigits(int n) {
        if (n == 0) {
            return 1;
        }
        int correction = 0;
        if (n == Integer.MIN_VALUE) {
            throw new InvalidParameterException("Can't process Integer.MIN_VALUE");
        }
        int numDigits = 0;
        while (n != 0) {
            n /= 10;
            numDigits += 1;
        }
        return numDigits;
    }
    private static int findLengthOfArray(int[] arr) {
        int length = 0;
        for (int n : arr) {
            if (n == Integer.MIN_VALUE) {
                length = findNumberOfDigits(Integer.MAX_VALUE) + 1;
                return length;
            }
            if (n == Integer.MAX_VALUE) {
                length = findNumberOfDigits(Integer.MAX_VALUE);
                return length;
            }
            length = Math.max(length, findNumberOfDigits(n));
        }
        return length;
    }

    private static ArrayList<LinkedList<Integer>> createArrayOfLinkedLists() {
        ArrayList<LinkedList<Integer>> arr = new ArrayList<>();
        for (int i = 0; i < 19; i++) {
            arr.add(i, new LinkedList<Integer>());
        }
        return arr;
    }

    private static int getIDigit(int number, int digitIndex) {
        if (digitIndex <= 0) {
            throw new InvalidParameterException("digitIndex must be greater than zero");
        }
        for (int i = 0; i < digitIndex - 1; i++) {
            number = number / 10;
        }
        return number % 10;
    }
    private static void pullElements(int[] source, ArrayList<LinkedList<Integer>> to, int digitIndex) {
        for (int number : source) {
            if (number == Integer.MIN_VALUE) {
                to.get(0).addFirst(number);
                continue;
            }
            int iDigit = getIDigit(number, digitIndex);
            int correctedIndex = iDigit + 9;
            to.get(correctedIndex).add(number);
        }
    }

    private static void pushElements(ArrayList<LinkedList<Integer>> source, int[] to) {
        int arrayIndex = 0;
        for (LinkedList<Integer> linkedList : source) {
            for (Integer number : linkedList) {
                to[arrayIndex] = number;
                arrayIndex++;
            }
            linkedList.clear();
        }
    }
    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of: O(kn)
     * And a best case running time of: O(kn)
     *
     * Feel free to make an initial O(n) passthrough of the array to
     * determine k, the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * You may use an ArrayList or LinkedList if you wish, but it should only
     * be used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with merge sort. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * You may assume that the passed in array is valid and will not be null.
     *
     * @param arr The array to be sorted.
     */
    public static void lsdRadixSort(int[] arr) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int max_length = Sorting.findLengthOfArray(arr);
        ArrayList<LinkedList<Integer>> auxArray = createArrayOfLinkedLists();
        for (int i = 1; i <= max_length; i++) {
            pullElements(arr, auxArray, i);
            pushElements(auxArray, arr);
        }
    }
}