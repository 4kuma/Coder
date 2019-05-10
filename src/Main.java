import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Coder coder = new Coder();
        String test = coder.getdata();

        System.out.println(test);
        coder.encode();

        coder.decode();
    }
}
