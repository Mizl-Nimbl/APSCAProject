import java.util.Scanner;
public class Chat {
    public static Scanner s = new Scanner(System.in);

    public String getInput()
    {
        System.out.print(":");
        String input = s.nextLine();
        return input;
    }

    public void ascii()
    {
        System.out.println("        _____                     _____              _     _      _              ");
        System.out.println("       / ____|                   |  __ \\            | |   (_)    | |             ");
        System.out.println("      | |  __  __ _ _ __   __ _  | |  | | ___   ___ | |__  _  ___| | _____ _   _ ");
        System.out.println("      | | |_ |/ _` | '_ \\ / _` | | |  | |/ _ \\ / _ \\| '_ \\| |/ __| |/ / _ \\ | | |");
        System.out.println("      | |__| | (_| | | | | (_| | | |__| | (_) | (_) | | | | | (__|   <  __/ |_| |");
        System.out.println("       \\_____|\\__,_|_| |_|\\__, | |_____/ \\___/ \\___/|_| |_|_|\\___|_|\\_\\___|\\__, |");
        System.out.println("                           __/ |                                            __/ |");
        System.out.println("                          |___/                                            |___/ ");
    }
    //need setter
}
