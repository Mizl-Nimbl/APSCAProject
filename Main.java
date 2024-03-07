import java.io.IOException;

public class Main
{
    public static boolean Running = true;
    public static boolean Debug = false;
    public static void main(String arg[]) throws IOException 
    {
        Chat c = new Chat();
        Debug d = new Debug();
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
            if (Debug == false)
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
                else if(input.equals("!debug"))
                {
                    Debug = true;
                    System.out.println("Debug mode activated");
                }
                else
                {
                    Commands.chat(input);
                }
            }
            if (Debug == true)
            {
                String input = d.getInput();
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
                else if(input.equals("!debug"))
                {
                    Debug = false;
                    System.out.println("Debug mode deactivated");
                }
                else
                {
                    Commands.chat(input);
                }
            }
        }
        Chat.s.close();
    }
}
