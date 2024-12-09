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
        System.out.println();
        ArrayList<ArrayList<Integer>> compactedDiskMap = main.compactDiskMap(diskMap);
        System.out.println();
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

    private ArrayList<ArrayList<Integer>> compactDiskMap(ArrayList<ArrayList<Integer>> diskMap) {
        int left = 0;
        int right = diskMap.size() - 1;

        while (left < right) {
            if(diskMap.get(right).getFirst() == -1 || diskMap.get(right).isEmpty()) {
                diskMap.remove(diskMap.size() - 1);
                right--;
            }

            int numsToTransfer = diskMap.get(right).size();
            int lastUsedPos = 0;
            int i;

            while(numsToTransfer != 0) {
                if(diskMap.get(left).getFirst() != -1 || diskMap.get(left).isEmpty()) {
                    left++;
                }
                for (i = lastUsedPos; i < diskMap.get(left).size() && numsToTransfer != 0; i++) {
                    diskMap.get(left).set(i, diskMap.get(right).getLast());
                    diskMap.get(right).remove(diskMap.get(right).size() - 1);
                    numsToTransfer--;
                }

                if(numsToTransfer == 0) {
                    lastUsedPos = i;
                    right--;
                }
                else {
                    lastUsedPos = 0;
                    left++;
                }
            }

            diskMap.remove(diskMap.size() - 1);

        }
        return diskMap;
    }
}
