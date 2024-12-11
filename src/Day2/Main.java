package Day2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.abs;

public class Main {
    private static final boolean ASCENDING = true;
    private static final boolean DESCENDING = false;
    private static final int NOT_SAFE = 0;
    private static final int SAFE = 1;

    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> fullReport;
        Main main = new Main();
        fullReport = main.readReports("./src/resources/InputDay2.txt");

        System.out.println("Which part will be used to check the safety of the report?");
        Scanner in = new Scanner(System.in);

        String partStr = in.nextLine();
        int part = partStr.charAt(0) - '0';

        int safeReports = main.calcSafeReports(fullReport, part);
        System.out.println("Total safe reports: " + safeReports);
    }

    private int calcSafeReports(ArrayList<ArrayList<Integer>> fullReport, int part) {
        int totalSafeReports = 0;
        for (int i = 0; i < fullReport.size(); i++) {
//            if(checkSafety(fullReport.get(i), part, true) == NOT_SAFE) {
//                System.out.println(fullReport.get(i));
//                totalSafeReports++;
//            }
            totalSafeReports = totalSafeReports + checkSafety(fullReport.get(i), part, true);

        }

        return totalSafeReports;
    }

    private int checkSafety(ArrayList<Integer> report, int part, boolean canMakeMistake) {
        int first_num = report.getFirst();
        int second_num = report.get(1);
        boolean level_direction;

        if(abs(first_num - second_num) > 3) {
            if(part == 1 || !canMakeMistake) {
                return NOT_SAFE;
            }

            ArrayList<Integer> auxReport1 =  new ArrayList<>(report);
            auxReport1.remove(0);
            if(checkSafety(auxReport1, part, false) == SAFE) {
                return SAFE;
            }

            ArrayList<Integer> auxReport2 =  new ArrayList<>(report);
            auxReport2.remove(1);

            return checkSafety(auxReport2, part, false);
        }

        if(first_num > second_num) {
            level_direction = DESCENDING;
            //Checking edge cases where first descends and rest is ascend correctly. EX: 2, 1, 2, 3, 4
            if(report.get(1) <= report.get(2)) {
                if(!canMakeMistake) {
                    return NOT_SAFE;
                }
                ArrayList<Integer> auxReport1 =  new ArrayList<>(report);
                auxReport1.remove(0);
                if(checkSafety(auxReport1, part, false) == SAFE) {
                    return SAFE;
                }

                ArrayList<Integer> auxReport2 =  new ArrayList<>(report);
                auxReport2.remove(1);

                return checkSafety(auxReport2, part, false);
            }
        }
        else if(first_num < second_num) {
            level_direction = ASCENDING;
            //Checking edge cases where first ascends and rest is descend correctly. EX: 9, 10, 9, 8, 7
            if(report.get(1) >= report.get(2)) {
                if(!canMakeMistake) {
                    return NOT_SAFE;
                }
                ArrayList<Integer> auxReport1 =  new ArrayList<>(report);
                auxReport1.remove(0);
                if(checkSafety(auxReport1, part, false) == SAFE) {
                    return SAFE;
                }

                ArrayList<Integer> auxReport2 =  new ArrayList<>(report);
                auxReport2.remove(1);

                return checkSafety(auxReport2, part, false);
            }
        }
        else {
            if(part == 1 || !canMakeMistake) {
                return NOT_SAFE;
            }

            ArrayList<Integer> auxReport1 =  new ArrayList<>(report);
            auxReport1.remove(0);
            if(checkSafety(auxReport1, part, false) == SAFE) {
                return SAFE;
            }

            ArrayList<Integer> auxReport2 =  new ArrayList<>(report);
            auxReport2.remove(1);

            return checkSafety(auxReport2, part, false);
        }


        for (int i = 1; i < report.size(); i++) {
            if(abs(report.get(i-1) - report.get(i)) > 3) {
                if(part == 1 || !canMakeMistake) {
                    return NOT_SAFE;
                }

                ArrayList<Integer> auxReport1 = new ArrayList<>(report);
                auxReport1.remove(i-1);
                if(checkSafety(auxReport1, part, false) == SAFE) {
                    return SAFE;
                }

                ArrayList<Integer> auxReport2 =  new ArrayList<>(report);
                auxReport2.remove(i);

                return checkSafety(auxReport2, part, false);
            }

            if(report.get(i-1) <= report.get(i) && level_direction == DESCENDING) {
                if(part == 1 || !canMakeMistake) {
                    return NOT_SAFE;
                }

                ArrayList<Integer> auxReport1 = new ArrayList<>(report);
                auxReport1.remove(i-1);
                if(checkSafety(auxReport1, part, false) == SAFE) {
                    return SAFE;
                }

                ArrayList<Integer> auxReport2 =  new ArrayList<>(report);
                auxReport2.remove(i);

                return checkSafety(auxReport2, part, false);
            }

            if(report.get(i-1) >= report.get(i) && level_direction == ASCENDING) {
                if(part == 1 || !canMakeMistake) {
                    return NOT_SAFE;
                }

                ArrayList<Integer> auxReport1 = new ArrayList<>(report);
                auxReport1.remove(i-1);
                if(checkSafety(auxReport1, part, false) == SAFE) {
                    return SAFE;
                }

                ArrayList<Integer> auxReport2 = new ArrayList<>(report);
                auxReport2.remove(i);

                return checkSafety(auxReport2, part, false);
            }
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
