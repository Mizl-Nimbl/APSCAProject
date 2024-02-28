import java.io.IOException;

public class Main
{
    public static boolean Running = true;
    public static void main(String arg[]) throws IOException 
    {
        Chat c = new Chat();

        System.out.println("wassup gang"); 
        System.out.println("start typing below to begin!");

        while (Running == true)
        {
            String input = c.newInput();
    
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
            else if (input.equals("wipe"))
            {
                Commands.wipesave();
            }
            else if (input.equals("filter"))
            {
                Commands.filter();
            }
            else if (input.startsWith("train"))
            {
                String url = input.substring(6);
                Commands.train(url);
            }
            else if (c.chat(input))
            {
                Commands.chat(input);
            }
            else
            {
                System.out.println("e: Command not found. Type 'help' for a list of commands.");
            }
        }
        c.s.close();
    }
}
