import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Commands 
{
    //globals for operations, MAKE GETTERS AND SETTERS LATER
    
    /*
    LENGTHFACTOR should be above 0. higher values will increase the length of the response. 
    */

    public static final double LENGTHFACTOR = 3; 

    /*
    HEATFACTOR should be above 0. higher values will decrease the randomness of the response
    extremely high values will shorten the sentence significantly at the cost of being lectured to about steel.
    */

    public static final double HEATFACTOR = 2;

    /* DO NOT TWEAK ANYTHING BELOW THIS POINT. */

    public static void sample()
    {
        System.out.println("https://www.mizl.dev/blog");
    }
    public static void help()
    {
        System.out.println("Commands:");
        System.out.println("!help - displays this message");
        System.out.println("!exit/quit - exits the program");
        System.out.println("!wipe - wipes the data file and hashed file clean");
        System.out.println("!filter - filters the data file to remove duplicates and sort by frequency");
        System.out.println("!train <url> - trains the bot with the data from the url");
        System.out.println("!sample - gives a sample website to train the bot with");
        System.out.println("anything else - chat with the bot");
    }
    public static void exit()
    {
        Main.Running = false;
    }
    public static void train(String urlinput) throws IOException 
    {
        //download data from the web
        StringBuilder content = new StringBuilder();

        try 
        {
            URL url = new URL(urlinput);
            URLConnection urlConnection = null;
            try 
            {
                urlConnection = url.openConnection();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) 
                {
                    String line;
                    while ((line = reader.readLine()) != null) 
                    {
                        content.append(line).append("\n");
                    }
                }
            } 
            catch (IOException e) 
            {
                System.out.println("E: error occurred while connecting to the URL: " + e.getMessage() + " ...maybe the URL is invalid?");
                return;
            }
        } 
        catch (Exception e) 
        {
            System.out.println("E: error occurred while connecting to the URL: " + e.getMessage() + " ...maybe the URL is malformed?");
            return;
        }

        String data = content.toString();

        StringBuilder htmlfiltered = new StringBuilder();
        boolean inside = false;

        System.out.println("FINDING BODY TEXT");

        int bodyIndex = data.indexOf("<body");
        if (bodyIndex == -1) {
            System.out.println("E: No <body> tag found in the data. please review words.txt before filtering...");
            bodyIndex = 0;
        }

        // Remove everything before the body tag
        data = data.substring(bodyIndex);

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

        FileWriter writer = new FileWriter("wordfiles/words.txt", true);

        System.out.println("CATEGORIZING");
        for (int i = 0; i < words.length - 1; i++) 
        {
            String currentWord = words[i] + " " + words[i + 1];
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
        //parse current data
        BufferedReader reader = new BufferedReader(new FileReader("wordfiles/words.txt"));
        Map<String, Integer> wordCount = new HashMap<>();
        String line = reader.readLine();
        while (line != null) 
        {
            line = line.replaceAll("[^a-zA-Z ]", "");
            line = line.replaceAll("[^a-zA-Z\\s]", "");
            line = line.toLowerCase();
            wordCount.put(line, wordCount.getOrDefault(line, 0) + 1);
            line = reader.readLine();
        }
        reader.close();

        BufferedReader hashedReader = new BufferedReader(new FileReader("wordfiles/hashed.txt"));
        List<String> lines = new ArrayList<>();
        String hashedLine= hashedReader.readLine();
        while (hashedLine != null) 
        {
            lines.add(hashedLine);
            hashedLine = hashedReader.readLine();
        }
        hashedReader.close();
    
        FileWriter writer = new FileWriter("wordfiles/hashed.txt");
        for (String currentLine : lines) 
        {
            String word = currentLine.split(", ")[0];
            int count = Integer.parseInt(currentLine.split(", ")[1]);
            if (wordCount.containsKey(word))
            {
                int oldCount = wordCount.get(word);
                count += oldCount;
            }
            writer.write(word + ", " + count + "\n");
        }
        for (Entry<String, Integer> entry : wordCount.entrySet()) 
        {
            if (!lines.contains(entry.getKey())) 
            {
                writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
            }
        }
        writer.close();

        //combine word counts for duplicate words
        //sort by frequency
        //write back to file
        BufferedReader sortreader = new BufferedReader(new FileReader("wordfiles/hashed.txt"));
        Map<String, Integer> sorted = new HashMap<>();
        Map<String, Integer> unsorted = new HashMap<>();

        String sortline = sortreader.readLine();
        while (sortline != null) 
        {
            String word = sortline.split(", ")[0];
            int count = Integer.parseInt(sortline.split(", ")[1]);
            unsorted.put(word, count);
            sortline = sortreader.readLine();
        }

        for (Entry<String, Integer> entry : unsorted.entrySet()) 
        {
            if (sorted.containsKey(entry.getKey()))
            {
                int oldCount = sorted.get(entry.getKey());
                int newCount = entry.getValue();
                int totalCount = oldCount + newCount;
                sorted.put(entry.getKey(), totalCount);
            }
            else
            {
                sorted.put(entry.getKey(), entry.getValue());
            }
        }

        List<Entry<String, Integer>> sortedList = new ArrayList<>(sorted.entrySet());
        for (int i = 1; i < sortedList.size(); i++) 
        {
            Entry<String, Integer> key = sortedList.get(i);
            int j = i - 1;
            while (j > -1 && sortedList.get(j).getValue() > key.getValue()) 
            {
                sortedList.set(j + 1, sortedList.get(j));
                j--;
            }
            sortedList.set(j + 1, key);
        }

        FileWriter sorter = new FileWriter("wordfiles/hashed.txt");

        for (Entry<String, Integer> entry : sortedList) 
        {
            sorter.write(entry.getKey() + ", " + entry.getValue() + "\n");
        }

        sorter.close();
        sortreader.close();

        try 
        {
            FileWriter writer2 = new FileWriter("wordfiles/words.txt");
            writer2.write("");
            writer2.flush();
            writer2.close();
            System.out.println("WIPED WORDS");
        } 
        catch (IOException e) 
        {
            System.out.println("FAILURE TO WIPE WORDS");
            e.printStackTrace();
        }

        System.out.println("DONE");
    }
    public static void chat(String input) throws IOException 
    {
        //parse input
        String[] words = input.split(" ");
        //read data from file
        BufferedReader reader = new BufferedReader(new FileReader("wordfiles/hashed.txt"));
        Map<String, Integer> wordCount = new HashMap<>();
        String line = reader.readLine();

        while (line != null) 
        {
            String word = line.split(", ")[0];
            int count = Integer.parseInt(line.split(", ")[1]);
            wordCount.put(word, count);
            line = reader.readLine();
        }

        reader.close();

        //normalize word counts
        int counter = 0;
        int totalCount = 0;
        Map<String, Integer> normalized = new HashMap<>();

        for (int value : wordCount.values()) {
            totalCount += value;
        }

        for (Entry<String, Integer> entry : wordCount.entrySet()) 
        {
            System.out.print("Generating probability tables: " + counter + "/" + wordCount.size() + "   " + "\r");
            double normalizedProbability = ((double) entry.getValue() / (double) totalCount) * (Math.random() * HEATFACTOR + 0.80);
            entry.setValue((int) ((normalizedProbability * totalCount) * 10));
            normalized.put(entry.getKey(), entry.getValue());
            totalCount -= entry.getValue();
            counter++;
        }
        System.out.println("Generating probability tables: done!                      ");

        String response = "";
        response += String.join(" ", words) + " ";

        double current = 0;

        for (Entry<String, Integer> entry : normalized.entrySet())
        {
            //System.out.println("Generating response: " + entry.getKey() + "   " + "\r");
            double random = Math.random() * 1.5 + 1.7;
            current = entry.getValue() * 0.1;

            if (random * HEATFACTOR < current)
            {
                response += entry.getKey() + " ";
            }
            else 
            {
                response += "";
            }

            if(response.length() > (Math.random() * 2000) * LENGTHFACTOR)
            {
                break;
            }
        }

        System.out.println("Generating response: done!");
        System.out.println(response.toString());
    }
}

