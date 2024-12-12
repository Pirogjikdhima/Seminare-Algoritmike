package Laboratore.Lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class lab1 {
    private static int partition(int[] a, int low, int high)
    {
        int pivot = a[high];
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
            if (a[j] <= pivot)
            {
                i++;

                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        int temp = a[i+1];
        a[i+1] = a[high];
        a[high] = temp;

        return i+1;
    }
    private static void quicksort(int[] a, int l, int h)
    {
        if (l < h)
        {
            int pivot = partition(a, l, h);
            quicksort(a, l, pivot-1);
            quicksort(a, pivot+1, h);
        }
    }
    public static void QuickSort(int[] a){
        quicksort(a,0,a.length-1);
    }

    private static void merge(int[] array, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        System.arraycopy(array, left, leftArray, 0, n1);
        System.arraycopy(array, middle + 1, rightArray, 0, n2);

        int i = 0, j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k++] = leftArray[i++];
        }
        while (j < n2) {
            array[k++] = rightArray[j++];
        }
    }

    private static void mergesort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;

            mergesort(array, left, middle);
            mergesort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }
    public static void MergeSort(int[] a){
        mergesort(a,0,a.length-1);
    }

    public static void create(int n,String path) throws FileNotFoundException {
        Random random  = new Random();
        int[] a = new int[n];
        for (int i = 0;i<a.length;i++){
            a[i] = random.nextInt(200);
        }
        try (PrintWriter w = new PrintWriter(path)) {
            for (int i : a) {
                w.write(i + " ");
            }
        }
    }

    public static int[] read(String path){
        List<Integer> numbers = new ArrayList<>();

        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    numbers.add(scanner.nextInt());
                } else {
                    scanner.next();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        int[] a = new int[numbers.size()];
        for(int i = 0;i<a.length;i++) {
            a[i] = numbers.get(i);

        }
        return a;
    }

    public static void main(String[] args) throws FileNotFoundException {

        int j = 1;
        for (int i = 10 ; i<1000001;i*=10){
            create(i,"src/file"+ j + ".txt");
            j++;
        }
        for(int i = 1;i<7;i++) {

            int[] a = read("src/file" + i + ".txt");
            long start = System.nanoTime();
            MergeSort(a);
            long end = System.nanoTime();
            System.out.println("Iteration: " + i + ", MergeSort time " + (end - start) + " ns");
            int[] b = read("src/file" + i + ".txt");
            start = System.nanoTime();
            QuickSort(b);
            end = System.nanoTime();
            System.out.println("Iteration: " + i + ", QuickSort time " + (end - start) + " ns");
        }
    }
}
