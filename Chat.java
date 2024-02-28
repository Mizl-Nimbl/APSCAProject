import java.util.Scanner;

public static Scanner s = new Scanner(System.in);

public class Chat 
{
    private String storedInput; 

    public Chat (String storedInput)
    {
        this.storedInput = storedInput;
    }

    public String getInput()
    {
        return storedInput; 
    }

    public void setInput()
    {
        System.out.print(":");
        String input = s.nextLine();
        storedInput = input;
    }
}
