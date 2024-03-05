import java.io.IOException;

public class Main
{
    public static boolean Running = true;
    public static void main(String arg[]) throws IOException 
    {
        Chat c = new Chat();
        //print GANG DOOHICKEY
        c.ascii();
        //go to sleep at 9 pm zzz
        try 
        {
            Thread.sleep(1000);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }

        System.out.println("Welcome to AJ and Noah's project!\nstart typing below to begin!\nType '!help' for a list of commands.\nType anything else to 'complete' text");
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
