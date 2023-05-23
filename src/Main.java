import java.util.*; //imports all util packages
import java.io.*; //imports all io packages


public class Main { //main class
    static ArrayList<String> symbols = new ArrayList<>(); //list of symbols
    static ArrayList<Integer> nums = new ArrayList<>(); //list of numbers
    public static void main(String[] args) { //main method
        ArrayList<String> words = new ArrayList<>(); //big wordlist
        nums = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)); //adds numbers to the list
        symbols = new ArrayList<>(Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+", "[", "]", "{", "}", ";", ":", "'", "\"", ",", "<", ".", ">", "/", "?", "|", "`", "~")); //adds symbols to the list
        File file1 = new File("C:\\Users\\jtjak\\IdeaProjects\\autocorrect\\src\\words.txt"); //file obj to read the wordlist
        try { //try catch block for FileNotFoundException
            Scanner s = new Scanner(file1); //scanner declared with the file
            while (s.hasNextLine()) { //loops through every line in the file
                words.add(s.nextLine()); //adds each word to the wordlist
            }
        } catch(FileNotFoundException error) { //catches FileNotFoundException errors
            System.out.println("file not found"); // catches FileNotFoundException errors
        }

        String input = in(); //gets user input
        if (input.split(" ").length > 1) { //checks if the input are multiple words
            ArrayList<String> userin = parse(input); //parses the input
            for (String item : userin) { //loops through every word in the input
                correct(words, item); //checks if the word is correct
            }
        } else {
            correct(words, input); //checks if the word is correct
        }
    }

    public static void correct(ArrayList<String> words, String item) {
        if (!(check(words, item))) { //checks if the word is in the dictionary
            boolean start = true; //boolean for formatting
            boolean end = false; //boolean for formatting
            int i = 0; //index for formatting
            ArrayList<String> similar = getsimilar(item, words); //gets similar words
            System.out.print(item + ", is incorrect, did you mean: "); //prints similar words
            if (similar.size() == 1) { //checks if there is only one similar word
                System.out.println(similar.get(0) + "?"); //prints the similar word
            } else {
                for (String w : similar) { //loops through similar words
                    if (i == similar.size() - 1) {
                        end = true; //sets end to true if the index is the last
                    }
                    if (start) {
                        System.out.print(w); //prints the first word
                        start = false; //sets start to false
                    } else if (end) {
                        System.out.println(", " + w + "?"); //prints the last word
                    } else {
                        System.out.print(", " + w); //prints the middle words
                    }
                    i++; //increments index
                }
            }
            if (similar.size() == 0) {System.out.println();} //prints a new line if there are no similar words
        } else { //if the word is in the dictionary
            System.out.println(item + ", is correct"); //prints this if the word is in the dictionary <-(I thought I spelled this wrong but the program tells me otherwise)
        }
    }

    public static String in () { //method for getting user input
        System.out.println("input a string"); //prints instructions
        Scanner in = new Scanner(System.in); //scanner for user input
        System.out.print("> "); //literally does nothing
        String i = in.nextLine(); //get input from next line
        i = i.toLowerCase(); //converts input to lowercase
        return i; //returns input
    }

    public static boolean check (ArrayList<String> words, String input) { //method for checking if the word is in the dictionary
        return words.contains(input); //checks if input is in the wordlist
    }

    public static ArrayList<String> getsimilar (String word, ArrayList<String> arr) { //method for getting similar words
        ArrayList<String> similar = new ArrayList<>(); //arraylist for similar words
        for (String s : arr) { //loops through every word in the list
            if (StringSimilarity.similarity(s, word) >= 0.7) { //checks similarity value for word
                similar.add(s); //adds word to similar list
            }
        }
        return similar; //returns similar words
    }
    //used for parsing multi-word inputs
    public static ArrayList<String> parse (String input) { //method for parsing multi-word inputs
        ArrayList<String> parsed = new ArrayList<>(); //arraylist for parsed input
        String[] arr = input.split(" "); //splits input into array
        Collections.addAll(parsed, arr); //adds array to parsed list
        for (int i = 0; i < parsed.size(); i++) { //loops through every word in the parsed list
            for (Integer num : nums) { //loops through every number in the nums list
                if (parsed.get(i).contains(num.toString())) { //checks if the word contains a number
                    parsed.remove(i); //removes word from parsed list
                }
            }
            for (String symbol : symbols) { //loops through every symbol in the symbols list
                if (parsed.get(i).contains(symbol)) { //checks if the word contains a symbol
                    int index = parsed.get(i).indexOf(symbol); //gets index of symbol
                    String str = parsed.get(i).substring(0, index) + parsed.get(i).substring(index + 1); //removes symbol from word
                    parsed.set(i, str); //sets word to new word
                }
            }
        }
        return parsed; //returns parsed input
    }
}