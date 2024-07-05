import View.Captchscii;

import java.util.Random;
import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Captchscii captchscii = new Captchscii(6);
        String captch = captchscii.getCaptcha();
        String trueAns = captchscii.getTrueStr();
        System.out.println(captch);
        System.out.println(trueAns);
    }


}
