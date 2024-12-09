package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class MainDay2 {
    private static final boolean ASCENDING = true;
    private static final boolean DESCENDING = false;
    private static final int NOT_SAFE = 0;
    private static final int SAFE = 1;

    private static final int PART = 2;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> fullReport;
        MainDay2 mainDay2 = new MainDay2();
        fullReport = mainDay2.readReports("./src/resources/InputDay2.txt");
        int safeReports = mainDay2.calcSafeReports(fullReport);
        System.out.println("To change from part one to part two of the solution, change the value of the constant accordingly.");
        System.out.println("Total safe reports (Part " + PART + ": " + safeReports);
    }

    private int calcSafeReports(ArrayList<ArrayList<Integer>> fullReport) {
        int totalSafeReports = 0;
        for (int i = 0; i < fullReport.size(); i++) {
            totalSafeReports = totalSafeReports + checkSafety(fullReport.get(i));
        }

        return totalSafeReports;
    }

    private int checkSafety(ArrayList<Integer> report) {
        int first_num = report.getFirst();
        int second_num = report.get(1);
        boolean level_direction;

        if(abs(first_num - second_num) > 3) { return NOT_SAFE; }

        if(first_num > second_num) {
            level_direction = DESCENDING;
        }
        else if(first_num < second_num) {
            level_direction = ASCENDING;
        }
        else {
            return NOT_SAFE;
        }


        for (int i = 1; i < report.size() - 1; i++) {
            if(abs(report.get(i) - report.get(i+1)) > 3) { return NOT_SAFE; }

            if(report.get(i) <= report.get(i+1) && level_direction == DESCENDING) { return NOT_SAFE; }

            if(report.get(i) >= report.get(i+1) && level_direction == ASCENDING) { return NOT_SAFE; }
        }

        return SAFE;
    }

    private ArrayList<ArrayList<Integer>> readReports(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            ArrayList<ArrayList<Integer>> fullReport = new ArrayList<>();

            while (scanner.hasNextLine()) {
                ArrayList<Integer> report = new ArrayList<>();
                String line = scanner.nextLine();
                String[] token = line.split(" ");
                for (int i = 0; i < token.length; i++) {
                    report.add(atoi(token[i]));
                }
                fullReport.add(report);
            }

            return fullReport;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    private int atoi(String strNum) {
        int finalNum = 0;
        for (int i = 0; i < strNum.length(); i++) {
            finalNum = finalNum * 10 + (strNum.charAt(i) - '0');
        }
        return finalNum;
    }
}
