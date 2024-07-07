package View;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Captchscii {
//    private String filepath = Captchscii.class.get+"digits.txt";
    private int length;
    private String captcha;
    private String trueStr;

    public Captchscii(int length) {
        this.length = length;
        trueStr = "";

        try {
            String javaFilePath = Captchscii.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//            Path javaFileDirectory = Paths.get(javaFilePath).getParent();

//            "D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/src/View/digits.txt"
            // Create the absolute path to the digits.txt file
//            Path filePath = javaFileDirectory.resolve(javaFilePath+"/digits.txt");
//            String filepath = (javaFilePath+"digits.txt").substring(1).replaceAll("^\\$", "/");
//            System.out.println(filepath);
            List<String> lines = Files.readAllLines(Paths.get("D:/Ehsan/studies/uni/sem_6/OOP/Project/Phase_1/MyPart/src/View/digits.txt"));

            int[] randomLines = new int[length];
            Random rand = new Random();
            for (int i = 0; i < length; i++) {
                int randomNumber = rand.nextInt(36) + 1;
                randomLines[i] = randomNumber;

                if (randomNumber <= 26) {
                    trueStr += (char) ('A' + randomNumber - 1);
                }
                else {
                    trueStr += (char)('0' + randomNumber - 27);
                }
            }

            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < length; i++) {
                    builder.append(lines.get((randomLines[i]-1) * 10 + j));
                    builder.append("\t");
                }
                builder.append("\n");
            }

            this.captcha = builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCaptcha() {
        return captcha;
    }

    public String getTrueStr() {
        return trueStr;
    }
}
