public class Commands {
    public static void help()
    {
        System.out.println("Commands:");
        System.out.println("help - displays this message");
        System.out.println("exit - exits the program");
    }
    public static void exit()
    {
        Main.Running = false;
    }
    public static void train(String url)
    {
        //parse text data from URL
        //sort data into categories, maybe counting frequencies of words
        //write to a data file for the chatbot to use
    }
    public static void chat(String input)
    {
        //parse input
        //compare to data file
        //generate response
        //print response
    }
}
