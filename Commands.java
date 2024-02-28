import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

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
    public static void train(String urlinput) throws IOException 
    {
        //download data from the web
        StringBuilder content = new StringBuilder();
        URL url = new URL(urlinput);
        URLConnection urlConnection = url.openConnection();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                content.append(line).append("\n");
            }
        }

        String data = content.toString();

        StringBuilder htmlfiltered = new StringBuilder();
        boolean inside = false;

        System.out.println("FINDING BODY TEXT");
        //remove everything before the body tag
        data = data.substring(data.indexOf("<body"));

        System.out.println("FILTERING");
        //filter out html tags
        for (int i = 0; i < data.length(); i++) 
        {
            if (data.charAt(i) == '<') 
            {
                inside = true;
            }
            else if (data.charAt(i) == '>') 
            {
                inside = false;
            }
            else if (!inside && data.charAt(i) == '(' + ')' + ';')
            {
                htmlfiltered.setLength(0);
            }
            else if (!inside)
            {
                htmlfiltered.append(data.charAt(i));
            }
        }

        String[] words = htmlfiltered.toString().split("\\s+");

        //BufferedReader reader = new BufferedReader(new FileReader("words.txt"));

        FileWriter writer = new FileWriter("wordfiles/words.txt", true);

        System.out.println("CATEGORIZING");
        for (int i = 0; i < words.length; i++) 
        {
            String currentWord = words[i];
            int sortedIndex = i - 1;
	        while(sortedIndex > -1 && words[sortedIndex].length() > currentWord.length()) {
                words[sortedIndex + 1] = words[sortedIndex];
                sortedIndex--;
            }
	        words[sortedIndex + 1] = currentWord;
            writer.write(currentWord + "\n");
        }
        writer.close();
        System.out.println("DONE");

        
    }
    public static void wipesave()
    {
        try 
        {
            FileWriter writer = new FileWriter("wordfiles/words.txt");
            writer.write("");
            writer.close();
            System.out.println("WIPED WORDS");
        } 
        catch (IOException e) 
        {
            System.out.println("FAILURE TO WIPE WORDS");
            e.printStackTrace();
        }
        try 
        {
            FileWriter writer = new FileWriter("wordfiles/hashed.txt");
            writer.write("");
            writer.close();
            System.out.println("WIPED HASHES");
        } 
        catch (IOException e) 
        {
            System.out.println("FAILURE TO WIPE HASHES");
            e.printStackTrace();
        }
    }
    public static void filter() throws IOException 
    {
        Map<String, Integer> wordCount = new HashMap<>();
        BufferedReader hashedReader = new BufferedReader(new FileReader("wordfiles/hashed.txt"));
        String hashedLine;
        while ((hashedLine = hashedReader.readLine()) != null) {
            String[] parts = hashedLine.split(" ");
            try {
                wordCount.put(parts[0], Integer.parseInt(parts[1]));
            } catch (NumberFormatException e) {
                // Skip invalid lines
                continue;
            }
        }
        hashedReader.close();

        BufferedReader reader = new BufferedReader(new FileReader("wordfiles/words.txt"));
        String line = reader.readLine();
        StringBuilder filteredWords = new StringBuilder();
        System.out.println("FILTERING");
        while (line != null) 
        {
            line = line.replaceAll("[^a-zA-Z]", "");
            filteredWords.append(line).append("\n");
            wordCount.put(line, wordCount.getOrDefault(line, 0) + 1);
            line = reader.readLine();
        }
        reader.close();

        System.out.println("REPLACING");
        String[] words = filteredWords.toString().split("\\s+");
        for (int i = 0; i < words.length; i++) 
        {
            String word = words[i];
            if (word.matches("[a-z]+[A-Z][a-zA-Z]*")) 
            {
                String[] parts = word.split("(?=[A-Z])");
                if (parts.length > 1) 
                {
                    words[i] = parts[0] + "\n" + parts[1].toLowerCase();
                }
            }
        }

        System.out.println("WRITING");
        FileWriter writer = new FileWriter("wordfiles/hashed.txt");
        writer.write(String.join("\n", words + " " + wordCount));
        writer.close();
        System.out.println("DONE");
    }
    public static void chat(String input)
    {
        //parse input
        //compare to data file
        //generate response
        //print response
    }
}
