import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<String> x = new ArrayList<>();
        x.add("1");
        x.add("ehsan");
        x.add("helia");
        System.out.println(new Test().listToString(x));
    }

    private String listToString(ArrayList<String> list) {
        return String.join(",", list);
    }

}
