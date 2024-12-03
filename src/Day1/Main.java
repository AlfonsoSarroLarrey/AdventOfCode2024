package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {
    private static final Integer LEFT = 0;
    private static final Integer RIGHT = 1;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> fullList;
        Main main = new Main();
        fullList = main.readList("./src/resources/InputListDay1.txt");
        main.sortList(fullList.get(LEFT));
        main.sortList(fullList.get(RIGHT));
        int totalDistance = main.calcDistance(fullList.get(LEFT), fullList.get(RIGHT));
        int similarityScore = main.calcSimScore(fullList.get(LEFT), fullList.get(RIGHT));
        System.out.println("Total distance is: " + totalDistance);
        System.out.println("Total similarity score is: " + similarityScore);
    }

    private ArrayList<ArrayList<Integer>> readList(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            ArrayList<Integer> singleListLeft = new ArrayList<>();
            ArrayList<Integer> singleListRight = new ArrayList<>();
            ArrayList<ArrayList<Integer>> fullList = new ArrayList<>();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] token = line.split("   ");
                singleListLeft.add(atoi(token[0]));
                singleListRight.add(atoi(token[1]));
            }

            fullList.add(singleListLeft);
            fullList.add(singleListRight);

            return fullList;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    private int calcDistance(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        int totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            totalDistance = totalDistance + abs(leftList.get(i) - rightList.get(i));
        }

        return totalDistance;
    }

    private int calcSimScore(ArrayList<Integer> leftList, ArrayList<Integer> rightList) {
        int totalScore = 0;
        for (int i = 0; i < leftList.size(); i++) {
            int mulFactor = 0;
            for (int j = 0; j < rightList.size(); j++) {
                if(leftList.get(i).compareTo(rightList.get(j)) == 0) {
                    mulFactor++;
                }
                if(leftList.get(i) < rightList.get(j)) {  break;  }
            }

            totalScore = totalScore + (leftList.get(i) * mulFactor);
        }

        return totalScore;
    }

    private void sortList(ArrayList<Integer> list) {
        quickSort(list, 0, list.size() - 1);
    }

    public void quickSort(ArrayList<Integer> list, int start, int end) {

        if (start < end) {
            int middle = (start + end) / 2;
            int pivot = list.get(middle);
            int index = partition(list, start, end, pivot);
            quickSort(list, start, index - 1);
            quickSort(list, index, end);
        }
    }

    public int partition(ArrayList<Integer> list, int left, int right, int pivotValue) {
        while(left <= right) {
            while(list.get(left) < pivotValue) {
                left++;
            }

            while(list.get(right) > pivotValue) {
                right--;
            }

            if(left <= right) {
                int aux = list.get(left);
                list.set(left, list.get(right));
                list.set(right, aux);
                left++;
                right--;
            }
        }

        return left;
    }

    public int atoi(String strNum) {
        int finalNum = 0;
        for (int i = 0; i < strNum.length(); i++) {
            finalNum = finalNum * 10 + (strNum.charAt(i) - '0');
        }
        return finalNum;
    }
}