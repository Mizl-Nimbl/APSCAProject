import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.ArrayList;

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

        //remove everything before the body tag
        data = data.substring(data.indexOf("<body"));

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
        ArrayList<String> noun = new ArrayList<String>();
        ArrayList<String> verb = new ArrayList<String>();
        ArrayList<String> adverb = new ArrayList<String>();
        ArrayList<String> adjective = new ArrayList<String>();
        ArrayList<String> preposition = new ArrayList<String>();
        ArrayList<String> pronoun = new ArrayList<String>();
        ArrayList<String> interjection = new ArrayList<String>();
        ArrayList<String> conjunction = new ArrayList<String>();
        ArrayList<String> uncategorized = new ArrayList<String>();

        //BufferedReader reader = new BufferedReader(new FileReader("words.txt"));

        FileWriter nounWriter = new FileWriter("wordfiles/nouns.txt", true);
        FileWriter verbWriter = new FileWriter("wordfiles/verbs.txt", true);
        FileWriter adverbWriter = new FileWriter("wordfiles/adverbs.txt", true);
        FileWriter adjectiveWriter = new FileWriter("wordfiles/adjectives.txt", true);
        FileWriter prepositionWriter = new FileWriter("wordfiles/prepositions.txt", true);
        FileWriter pronounWriter = new FileWriter("wordfiles/pronouns.txt", true);
        FileWriter interjectionWriter = new FileWriter("wordfiles/interjections.txt", true);
        FileWriter conjunctionWriter = new FileWriter("wordfiles/conjunction.txt", true);
        FileWriter uncatWriter = new FileWriter("wordfiles/uncategorized.txt", true);

        System.out.println("CATEGORIZE THE WORDS");
        Chat c = new Chat();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            System.out.println("what type of word is: " + word);
            System.out.println("1. Noun");
            System.out.println("2. Verb");
            System.out.println("3. Adverb");
            System.out.println("4. Adjective");
            System.out.println("5. Preposition");
            System.out.println("6. Pronoun");
            System.out.println("7. Interjection");
            System.out.println("8. Conjunction");
            String input = c.getInput();

            if (input == "1") 
            {
                noun.add(word);
                nounWriter.write(word + "\n");
            }
            else if (input == "2") 
            {
                verb.add(word);
                verbWriter.write(word + "\n");
            }
            else if (input == "3") 
            {
                adverb.add(word);
                adverbWriter.write(word + "\n");
            }
            else if (input == "4") 
            {
                adjective.add(word);
                adjectiveWriter.write(word + "\n");
            }
            else if (input == "5") 
            {
                preposition.add(word);
                prepositionWriter.write(word + "\n");
            }
            else if (input == "6") 
            {
                pronoun.add(word);
                pronounWriter.write(word + "\n");
            }
            else if (input == "7") 
            {
                interjection.add(word);
                interjectionWriter.write(word + "\n");
            }
            else if (input == "8") 
            {
                conjunction.add(word);
                conjunctionWriter.write(word + "\n");
            }
            else
            {
                uncategorized.add(word);
                uncatWriter.write(word + "\n");
            }
        
            
        }

        nounWriter.close();
        verbWriter.close();
        adverbWriter.close();
        adjectiveWriter.close();
        prepositionWriter.close();
        pronounWriter.close();
        interjectionWriter.close();
        conjunctionWriter.close();
        uncatWriter.close();

        //reader.close();
    }
    public static void chat(String input)
    {
        //parse input
        //compare to data file
        //generate response
        //print response
    }
}
