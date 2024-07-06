import View.Captchscii;

import java.util.Random;
import java.util.Scanner;
import java.util.SortedMap;

public class Test {

    public static void main(String[] args) {
        int a = 1;
        change(a);
        System.out.println(a);
    }

    private static void change(int a){
        a++;
        System.out.println("a inside function: "+a);
    }


}
