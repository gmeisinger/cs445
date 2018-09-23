import java.util.Scanner;
import java.util.Random;
import java.io.IOException;
import java.nio.file.*;
/**
 * Program to test run times of several sorting methods
 * @author George Meisinger
 */
public class Assig4
{
    public static void main(String [] args) throws IOException
    {
        //get user input
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter array size: ");
        int arraySize = scan.nextInt();
        System.out.println("Enter number of trials: ");
        int numOfTrials = scan.nextInt();
        System.out.println("Enter file name: ");
        String outputFile = scan.next();

         //setup file
        Path path = Paths.get(outputFile);
        try
        {
            Files.createFile(path);
        }
        catch(IOException e)
        {
            System.out.println("File exists, overwrite? (Y/n): ");
            String ans = scan.next();
            if (ans.toLowerCase().equals("y"))
            {
                Files.delete(path);
                Files.createFile(path);
            }
            else System.exit(0);
        }
        scan.close();
        
        //set traceMode on/off
        boolean traceMode = false;
        if (arraySize <= 20) traceMode = true;

        //set up arrays
        //random
        Random rand = new Random();
        Integer[] randomArray = new Integer[arraySize];
        Integer[] incArray = new Integer[arraySize];
        Integer[] decArray = new Integer[arraySize];
        for (int i = 0; i<arraySize; i++)
        {
            randomArray[i] = new Integer(1 + rand.nextInt(arraySize));
            incArray[i] = new Integer(i + 1);
            decArray[i] = new Integer(arraySize - i);
        }
        
        //Random array first
        test(randomArray, arraySize, numOfTrials, path, "Random", traceMode);
        //sorted
        test(incArray, arraySize, numOfTrials, path, "Sorted", traceMode);
        //sorted reverse
        test(decArray, arraySize, numOfTrials, path, "Reverse", traceMode);


    }

    /**
     * runs an array through each sorting algorithm and sends results to a file
     * @param arr the array to be tested
     * @param arraySize size of array
     * @param numOfTrials number of trials per test
     * @param path path to output file
     * @param dataConfig string representing the data configuration
     * @param traceMode boolean to set trace mode on/off
     */
    public static void test(Integer[] arr, int arraySize, int numOfTrials, Path path, String dataConfig, boolean traceMode) throws IOException
    {
        //Simple quickSort
        long totalTime = 0;
        double avgTime;
        String outString;
        //for each trial
        //only if array size <= 100k
        if (arraySize<=100000)
        {
            for (int i =0; i<numOfTrials;i++)
            {
                //make copy
                Integer[] arrCopy = copyArray(arr, arraySize);
                //get time
                long start = System.nanoTime();
                //sort
                Quick.quickSort(arrCopy, arraySize);
                //get time
                long finish = System.nanoTime();
                long delta = finish - start;
                totalTime = totalTime + delta;
                if (traceMode)
                {
                    System.out.println("Algorithm: Simple QuickSort");
                    System.out.println("Array size: "+arraySize);
                    System.out.println("Data configuration: "+dataConfig);
                    System.out.println("Initial data: ");
                    for (int j=0;j<arraySize;j++)
                    {
                        System.out.print(arr[j]+" ");
                    }
                    System.out.println("\nData after sort: ");
                    for (int j=0;j<arraySize;j++)
                    {
                        System.out.print(arrCopy[j]+" ");
                    }
                    System.out.println("\n");
                }
            }
        
            avgTime = totalTime / numOfTrials / 1000000000.0;
            outString = "Algorithm: Simple QuickSort\nArray Size: " + arraySize + "\nOrder: "+dataConfig+"\nNumber of trials: " + numOfTrials + "\nAverage Time per trial: " + avgTime + " sec.\n\n";
            //output results to file
            writeToFile(outString, path);
        }

        //median of 3 (5)
        totalTime = 0;
        //for each trial
        for (int i =0; i<numOfTrials;i++)
        {
            //make copy
            Integer[] arrCopy = copyArray(arr, arraySize);
            //get time
            long start = System.nanoTime();
            //sort
            TextMergeQuick.quickSort(arrCopy, arraySize, 5, false);
            //get time
            long finish = System.nanoTime();
            long delta = finish - start;
            totalTime = totalTime + delta;
            if (traceMode)
            {
                System.out.println("Algorithm: Median of 3 (5)");
                System.out.println("Array size: "+arraySize);
                System.out.println("Data configuration: "+dataConfig);
                System.out.println("Initial data: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arr[j]+" ");
                }
                System.out.println("\nData after sort: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arrCopy[j]+" ");
                }
                System.out.println("\n");
            }
        }
        avgTime = totalTime / numOfTrials / 1000000000.0;
        outString = "Algorithm: Median of 3 (5)\nArray Size: " + arraySize + "\nOrder: "+dataConfig+"\nNumber of trials: " + numOfTrials + "\nAverage Time per trial: " + avgTime + " sec.\n\n";
        //output results to file
        writeToFile(outString, path);

        //med of 3 < 20
        totalTime = 0;
        //for each trial
        for (int i =0; i<numOfTrials;i++)
        {
            //make copy
            Integer[] arrCopy = copyArray(arr, arraySize);
            //get time
            long start = System.nanoTime();
            //sort
            TextMergeQuick.quickSort(arrCopy, arraySize, 20, false);
            //get time
            long finish = System.nanoTime();
            long delta = finish - start;
            totalTime = totalTime + delta;
            if (traceMode)
            {
                System.out.println("Algorithm: Median of 3 (20)");
                System.out.println("Array size: "+arraySize);
                System.out.println("Data configuration: "+dataConfig);
                System.out.println("Initial data: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arr[j]+" ");
                }
                System.out.println("\nData after sort: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arrCopy[j]+" ");
                }
                System.out.println("\n");
            }
        }
        avgTime = totalTime / numOfTrials / 1000000000.0;
        outString = "Algorithm: Median of 3 (20)\nArray Size: " + arraySize + "\nOrder: "+dataConfig+"\nNumber of trials: " + numOfTrials + "\nAverage Time per trial: " + avgTime + " sec.\n\n";
        //output results to file
        writeToFile(outString, path);

        //med of 3 < 100
        totalTime = 0;
        //for each trial
        for (int i =0; i<numOfTrials;i++)
        {
            //make copy
            Integer[] arrCopy = copyArray(arr, arraySize);
            //get time
            long start = System.nanoTime();
            //sort
            TextMergeQuick.quickSort(arrCopy, arraySize, 100, false);
            //get time
            long finish = System.nanoTime();
            long delta = finish - start;
            totalTime = totalTime + delta;
            if (traceMode)
            {
                System.out.println("Algorithm: Median of 3 (100)");
                System.out.println("Array size: "+arraySize);
                System.out.println("Data configuration: "+dataConfig);
                System.out.println("Initial data: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arr[j]+" ");
                }
                System.out.println("\nData after sort: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arrCopy[j]+" ");
                }
                System.out.println("\n");
            }
        }
        avgTime = totalTime / numOfTrials / 1000000000.0;
        outString = "Algorithm: Median of 3 (100)\nArray Size: " + arraySize + "\nOrder: "+dataConfig+"\nNumber of trials: " + numOfTrials + "\nAverage Time per trial: " + avgTime + " sec.\n\n";
        //output results to file
        writeToFile(outString, path);

        //random pivot (5)
        totalTime = 0;
        //for each trial
        for (int i =0; i<numOfTrials;i++)
        {
            //make copy
            Integer[] arrCopy = copyArray(arr, arraySize);
            //get time
            long start = System.nanoTime();
            //sort
            TextMergeQuick.quickSort(arrCopy, arraySize, 5, true);
            //get time
            long finish = System.nanoTime();
            long delta = finish - start;
            totalTime = totalTime + delta;
            if (traceMode)
            {
                System.out.println("Algorithm: Random Pivot (5)");
                System.out.println("Array size: "+arraySize);
                System.out.println("Data configuration: "+dataConfig);
                System.out.println("Initial data: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arr[j]+" ");
                }
                System.out.println("\nData after sort: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arrCopy[j]+" ");
                }
                System.out.println("\n");
            }
        }
        avgTime = totalTime / numOfTrials / 1000000000.0;
        outString = "Algorithm: Random Pivot (5)\nArray Size: " + arraySize + "\nOrder: "+dataConfig+"\nNumber of trials: " + numOfTrials + "\nAverage Time per trial: " + avgTime + " sec.\n\n";
        //output results to file
        writeToFile(outString, path);

        //merge sort
        totalTime = 0;
        //for each trial
        for (int i =0; i<numOfTrials;i++)
        {
            //make copy
            Integer[] arrCopy = copyArray(arr, arraySize);
            //get time
            long start = System.nanoTime();
            //sort
            TextMergeQuick.mergeSort(arrCopy, arraySize);
            //get time
            long finish = System.nanoTime();
            long delta = finish - start;
            totalTime = totalTime + delta;
            if (traceMode)
            {
                System.out.println("Algorithm: Merge Sort");
                System.out.println("Array size: "+arraySize);
                System.out.println("Data configuration: "+dataConfig);
                System.out.println("Initial data: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arr[j]+" ");
                }
                System.out.println("\nData after sort: ");
                for (int j=0;j<arraySize;j++)
                {
                    System.out.print(arrCopy[j]+" ");
                }
                System.out.println("\n");
            }
        }
        avgTime = totalTime / numOfTrials / 1000000000.0;
        outString = "Algorithm: Merge Sort\nArray Size: " + arraySize + "\nOrder: "+dataConfig+"\nNumber of trials: " + numOfTrials + "\nAverage Time per trial: " + avgTime + " sec.\n\n";
        //output results to file
        writeToFile(outString, path);
    }

    /**copies the given array
     * @param arr array to be copied
     * @param arrSize size of array
     */
    public static Integer[] copyArray(Integer[] arr, int arrSize)
    {
        Integer[] newArr = new Integer[arrSize];
        for (int i=0;i<arrSize;i++)
        {
            newArr[i] = arr[i];
        }
        return newArr;
    }

    /**method to write to file
     * @param str string to write
     * @param path path to file
     */
    private static void writeToFile(String str, Path path) throws IOException{
        try
        {    
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        }
        catch(NoSuchFileException e)
        {
            Files.createFile(path);
            Files.write(path, str.getBytes(), StandardOpenOption.APPEND);
        }
    }
}