import java.util.Scanner;
public class Chat {
    public static Scanner s = new Scanner(System.in);
    public String getInput()
    {
        System.out.print(":");
        String input = s.nextLine();
        return input;
    }
}
