public class StringSimilarity {

    // this code is part of the org.apache.commons.lang3.StringUtils library
    // more info here: https://commons.apache.org/proper/commons-lang/apidocs/org/apache/commons/lang3/StringUtils.html
    // documentation auto-generated by GitHub copilot.

    public static int getLevenshteinDistance(String X, String Y) { //this is the method that calculates the similarity value

        int m = X.length(); //gets length of first word
        int n = Y.length(); //gets length of second word

        int[][] T = new int[m + 1][n + 1]; //creates 2d array for storing values
        for (int i = 1; i <= m; i++) { //loops through every letter in the first word
            T[i][0] = i; //sets the first row of the array to the index of the letter
        }
        for (int j = 1; j <= n; j++) { //loops through every letter in the second word
            T[0][j] = j; //sets the first column of the array to the index of the letter
        }

        int cost; //declares cost variable
        for (int i = 1; i <= m; i++) { //loops through every letter in the first word
            for (int j = 1; j <= n; j++) { //loops through every letter in the second word
                cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0: 1; //checks if the letters are the same
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1), //sets the value of the current index to the minimum of the three adjacent values
                        T[i - 1][j - 1] + cost); //adds the cost to the minimum of the three adjacent values
            }
        }

        return T[m][n]; //returns the value of the last index
    }

    public static double similarity(String x, String y) { //method for getting the similarity value
        if (x == null || y == null) { //checks if the strings are null
            throw new IllegalArgumentException("Strings must not be null"); //throws an error if the strings are null
        }

        double maxLength = Double.max(x.length(), y.length()); //gets the length of the longest word
        if (maxLength > 0) { //checks if the length is greater than 0
            return (maxLength - getLevenshteinDistance(x, y)) / maxLength; //returns the similarity value
        }
        return 1.0; //returns 1 if the length is 0
    }
}