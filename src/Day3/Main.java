package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    static final boolean NUM1 = true;
    static final boolean NUM2 = false;


    public static void main(String[] args) {

        Main main = new Main();
        long total_mul_sum = main.readCorruptProgram("./src/resources/InputDay3.txt");
        System.out.println("The total sum of all multiplications is: " + total_mul_sum);
    }

    private long readCorruptProgram(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            //We define the regex to find all instances of "mul(<num_3dig>,<num_3dig>)"
            Pattern regex = Pattern.compile("mul\\([0-9]{1,3},[0-9]{1,3}\\)");
            Matcher matcher;

            long mul_sum = 0;

            while(scanner.hasNextLine()) {
                matcher = regex.matcher(scanner.nextLine());
                while(matcher.find()) {
                    mul_sum = mul_sum + calcMultiplication(matcher.group());
                }
            }

            return mul_sum;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    private int calcMultiplication(String strNum) {
        int num1 = 0;
        int num2 = 0;
        boolean currNum = NUM1;

        for (int i = 4; i < strNum.length() - 1; i++) {
            if(strNum.charAt(i) == ',') {
                currNum = NUM2;
                i++;
            }
            if(currNum == NUM1) {
                num1 = num1 * 10 + strNum.charAt(i) - '0';
            }
            else {
                num2 = num2 * 10 + strNum.charAt(i) - '0';
            }
        }

        return num1 * num2;
    }
}
