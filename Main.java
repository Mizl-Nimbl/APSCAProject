import java.io.IOException;

public class Main
{
    public static boolean Running = true;
    public static void main(String arg[]) throws IOException 
    {
        Chat c = new Chat();
        
        System.out.println("start typing below to begin!\nType '!help' for a list of commands.");
        while (Running == true)
        {
            String input = c.getInput();
            if (input.equals("!help"))
            {
                Commands.help();
            }
            else if (input.equals("!exit"))
            {
                Commands.exit();
            }
            else if (input.equals("!quit"))
            {
                Commands.exit();
            }
            else if (input.equals("!wipe"))
            {
                Commands.wipesave();
            }
            else if (input.equals("!filter"))
            {
                Commands.filter();
            }
            else if (input.startsWith("!train"))
            {
                String url = input.substring(6);
                Commands.train(url);
            }
            else if(input.equals("!sample"))
            {
                Commands.sample();
            }
            //maybe make a save command to save data, so it can get smarter over time
            else
            {
                Commands.chat(input);
            }
        }
        Chat.s.close();
    }
}
