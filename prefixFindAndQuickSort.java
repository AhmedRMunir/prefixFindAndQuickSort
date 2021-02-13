import java.io.*;
import java.util.*;

class prefixFindAndQuickSort {
    
  
  /**
    Runtime Complexity:
    
      O(N): traversing the name array
      O(1): comparing names and inserting matches into list
      O(N): converting list to array. Worst case if all names contain the prefix
      O(logN): sorting the array of names
      
      Total Runtime Complecity: O(2N + logN) = logN.
    
    Space Complexity:
    
      O(1): isSorted, prevName, prefixLength
      O(N): result. Worse case if all names contain prefix 
      O(N): converting list to array
      O(1): Strong some temp swapping variables in quickSort. 
      
      Total Space Complexity: O(N)
      
      
      Notes:
        1) A list was used to hold iniital results to avoid null values at the end of the array
        2) in-place quickSort was used to make sure the result was in sorted order.
        3) quickSort was only called if we detect that the array was not in sorted order
        4) Another way to solve was to sort as we insert. The only downside to this solution was the worst case of comparing the name to be inserted with every other pre-existing name which would cost us O(N) runtime, therefore, quicksort was a better option with O(logN) runtime for better scalability.
  */
  public static String[] findAndSort(String searchPrefix, String[] listOfNames) {
    
    // Handles empty list case
    if (searchPrefix.equals("") || listOfNames.length == 0) {
      return new String[]{"NO MATCHES"};
    }
       
    // result: container for the matches
    List<String> result = new ArrayList<>();
    
    // prefixLength: used to substring name
    int prefixLength = searchPrefix.length();
    
    // isSorted and prevName are used to check if listOfnames is sorted
    // We are assuming sorted order is in ascending order
    boolean isSorted = true;
    String prevName = listOfNames[0];
      
    for (int i = 0; i < listOfNames.length; i++) {
      
      String name = listOfNames[i];
      
      // Check if items are sorted
      if (prevName.compareTo(name) > 0) {
        isSorted = false;
      }
      prevName = name;
      
      // if substring fits in the name name and we find a match
      if (prefixLength <= name.length()) {
        String namePrefix = name.substring(0, prefixLength);
      
        if (namePrefix.equals(searchPrefix)) {
          result.add(name);
        }
      }
    }
    
    String[] newResult;
    
    // Case if no matches
    if (result.size() == 0) {
      newResult = new String[]{"NO MATCHES"};
    } else {
      // convert the list to an array and sort
      newResult = convertListToArray(result);
      if (!isSorted) {
        quickSort(newResult, 0, newResult.length - 1);
      }
    }

    return newResult;
  }

  
  // Sorts the given array with indices lo and hi using lo as a pivot
  // O(logn) runtime
  private static void quickSort(String[] arr, int lo, int hi) {
    if (lo < hi) {
      // partitoin the elements and return partitioning index
      int partition = partition(arr, lo, hi);
      
      // sort the rest of the array via partitioning and exclude the element at the partition index
      quickSort(arr, lo, partition - 1);
      quickSort(arr, partition + 1, hi);
    }
  }
    
  // Partition using lo as an index
  private static int partition(String[] arr, int lo, int hi) {
    int pivot = lo;
    int i = lo;
    int j = hi;

    // compare elements from both ends of the array, if we get stuck, swap and continue comparison
    while (i < j) {
      if (arr[j].compareTo(arr[pivot]) > 0) {
        j--;
      } else if (arr[i].compareTo(arr[pivot]) <= 0) {
        i++;
      } else {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
      }
    }

    // put the item used as a pivot in the partitioning index and return the partition index
    String temp = arr[i];
    arr[i] = arr[lo];
    arr[lo] = temp;

    return i;
  }
  
  // Converts list to array by O(n) array traversal
  private static String[] convertListToArray(List<String> list) {
    String[] result = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      result[i] = list.get(i);
    }
    return result;
  }

  public static int[] findAndSortInt(int prefix, int[] numbers) {
    String[] numToString = new String[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
      numToString[i] = String.valueOf(numbers[i]);
    }
    String[] stringResult = findAndSort(String.valueOf(prefix), numToString);

    int[] intResult = new int[stringResult.length];

    for (int j = 0; j < stringResult.length; j++) {
      intResult[j] = Integer.parseInt(stringResult[j]);
    }

    return intResult;
  }

  public static void main(String[] args) {
    String prefix = "Jhon";
    String[] names = new String[]{"Ahmed Munir", "Jhonnnnnnnnnnnnnnnnn", "JJJJohn"};
    String[] result = prefixFindAndQuickSort.findAndSort(prefix, names);
    System.out.println("Names found that match the prefix " + prefix + " are " + Arrays.toString(result));

    int numPrefix = 206;
    int[] numbers = new int[]{206123456, 12345678, 1312123345};
    int[] numResult = prefixFindAndQuickSort.findAndSortInt(numPrefix, numbers);
    System.out.println("Numbers found that match the prefix " + prefix + " are " + Arrays.toString(numResult));
  }
}
