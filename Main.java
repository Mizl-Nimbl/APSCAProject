import java.io.IOException;

public class Main
{
    public static boolean Running = true;
    public static void main(String arg[]) throws IOException 
    {
        Chat c = new Chat();
        System.out.println("Hello World!");
        System.out.println("start typing below to begin!");
        while (Running == true)
        {
            String input = c.getInput();
            if (input.equals("help"))
            {
                Commands.help();
            }
            else if (input.equals("exit"))
            {
                Commands.exit();
            }
            else if (input.equals("quit"))
            {
                Commands.exit();
            }
            else if (input.startsWith("train"))
            {
                String url = input.substring(6);
                Commands.train(url);
            }
            else
            {
                Commands.chat(input);
            }
        }
        Chat.s.close();
    }
}
