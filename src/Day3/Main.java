package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Main main = new Main();
        long total_mul_sum = main.readCourruptProgram("./src/resources/InputDay2.txt");
    }

    private long readCourruptProgram(String fileName) {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            //We define the regex to find all instances of "mul(<num_3dig>,<num_3dig>)"
            Pattern regex = Pattern.compile("(mul\\([0-9]{1,3},[0-9]{1,3}\\))");
            Stream<MatchResult> stream = scanner.findAll(regex);
            ArrayList<String> streamList = stream.toList();
            long mul_sum = 0;
            for (int i = 0; i < stream.; i++) {

            }stream.forEach(i ->
                    {
                        mul_sum = mul_sum + calcMultiplication(i.group());
                    }
            );

            return mul_sum;

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            throw new RuntimeException(e);
        }
    }

    private int calcMultiplication(String strNum) {
        int num1;
        int num2;
    }
}
