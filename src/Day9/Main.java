package Day9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        ArrayList<ArrayList<Integer>> diskMap = main.readDiskMap("./src/resources/InputDay9.txt");

        System.out.println("Which part do you want to calculate the checksum for?");
        int part = System.console().readLine().charAt(0) - '0';

        if(part == 1) {
            main.compactDiskMap(diskMap);
        }
        else if(part == 2) {
            main.altCompactDiskMap(diskMap);
        }
        else {
            System.out.println("Please input a correct part, either part 1 or 2.");
            System.exit(0);
        }


        long checksum = main.calcChecksum(diskMap);
        System.out.println("The filesystem checksum is: " + checksum);

    }

    private ArrayList<ArrayList<Integer>> readDiskMap(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            String diskMapString = scanner.next();
            ArrayList<ArrayList<Integer>> diskMap = new ArrayList<>();
            ArrayList<Integer> diskSection;
            int numStored = 0;

            for (int i = 0; i < diskMapString.length(); i++) {
                if(i % 2 == 0) {
                    diskSection = new ArrayList<>(Collections.nCopies(diskMapString.charAt(i) - '0', numStored));
                    numStored++;
                }
                else {
                    diskSection = new ArrayList<>(Collections.nCopies(diskMapString.charAt(i) - '0', -1));
                }
                diskMap.add(diskSection);
            }

            return diskMap;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    private void compactDiskMap(ArrayList<ArrayList<Integer>> diskMap) {
        int left = 0;
        int right = diskMap.size() - 1;

        int lastUsedPos = 0;
        int i;

        while (left < right) {
            if(diskMap.get(right).isEmpty()) {
                diskMap.remove(diskMap.size() - 1);
                right--;
            } else if (diskMap.get(right).getFirst() == -1) {
                diskMap.remove(diskMap.size() - 1);
                right--;
            }

            int numsToTransfer = diskMap.get(right).size();


            while(numsToTransfer != 0) {
                if(diskMap.get(left).isEmpty()) {
                    left++;
                } else if (diskMap.get(left).getLast() != -1) {
                    left++;
                }
                for (i = lastUsedPos; i < diskMap.get(left).size() && numsToTransfer != 0; i++) {
                    diskMap.get(left).set(i, diskMap.get(right).remove(diskMap.get(right).size() - 1));
                    numsToTransfer--;
                }

                if(numsToTransfer == 0) {
                    lastUsedPos = i;
                    right--;
                }
                if(i == diskMap.get(left).size()){
                    lastUsedPos = 0;
                    left++;
                }
            }
        }
    }

    private void altCompactDiskMap(ArrayList<ArrayList<Integer>> diskMap) {

    }

    private long calcChecksum(ArrayList<ArrayList<Integer>> compactedDiskMap) {
        long pos = 0;
        long checkSum = 0;

        for (int i = 0; i < compactedDiskMap.size(); i++) {
            for (int j = 0; j < compactedDiskMap.get(i).size(); j++) {
                if(compactedDiskMap.get(i).get(j) != -1) {
                    checkSum = checkSum + (pos * compactedDiskMap.get(i).get(j));
                    pos++;
                }
            }
        }
        return checkSum;
    }
}
