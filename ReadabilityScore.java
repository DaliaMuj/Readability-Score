package readability;

import java.nio.file.Paths;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
class Estimation {
    public static int characters;
    public static int words;
    public static int sentences;
    public static int[] syllables;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String text = Files.readString(Paths.get(args[0]));
            words = text.split("\\s+").length;
            sentences = text.split("[!.?]+").length;
            characters = text.replaceAll("[ \n\t]","").split("").length;
            syllables = countSyllables(text);
            System.out.println("The text is:");
            System.out.println(text);
            System.out.println("Words:" + words);
            System.out.println("Sentences:" + sentences);
            System.out.println("Characters:" + characters);
            System.out.println("Syllables:" + syllables[0]);
            System.out.println("Polysyllables:" + syllables[1]);
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): " );
            String scoreType = scanner.next();
            scoreTypes(scoreType);
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        scanner.close();
    }
    public static String age(double score) {
        if (score >= 1 && score < 2) {
            return "6-year-olds";
        } else if (score >= 2 && score < 3) {
            return "7-year-olds";
        } else if (score >= 3 && score < 4) {
            return "9-year-olds";
        } else if (score >= 4 && score < 5) {
            return "10-year-olds";
        } else if (score >= 5 && score < 6) {
            return "11-year-olds";
        } else if (score >= 6 && score < 7) {
            return "12-year-olds";
        } else if (score >= 7 && score < 8) {
            return "13-year-olds";
        } else if (score >= 8 && score < 9) {
            return "14-year-olds";
        } else if (score >= 9 && score < 10) {
            return "15-year-olds";
        } else if (score >= 10 && score < 11) {
            return "16-year-olds";
        } else if (score >= 11 && score < 12) {
            return "17-year-olds";
        } else if (score >= 12 && score < 13) {
            return "18-year-olds";
        } else {
            return "24-year-olds";
        }
    }
    public static int[] countSyllables(String text) {
        int numSyllables = 0;
        int numPolysyllables = 0;
        String[] words = text.split("[ \n]");

        for (String x : words) {

            int inThisWord = 0;

            boolean lastWasVowel = false;
            for (int i = 0; i < x.length(); i++) {
                String now = "" + x.charAt(i);
                if (now.matches("[aeiouy]")) {
                    if (!lastWasVowel) {
                        inThisWord++;
                        lastWasVowel = true;
                    }
                } else {
                    lastWasVowel = false;
                }
            }

            char finalLetter = 0; 
            for (int j = x.length()-1; j >= 0; j--) {
                char nowLetter = x.charAt(j);
                if (Character.isLetter(nowLetter)) {
                    finalLetter = nowLetter;
                    break;
                }
            }

            if (finalLetter == 'e' || finalLetter == 'E') {
                inThisWord--;
            }

            if (inThisWord == 0) {
                inThisWord = 1;
            }
            if (inThisWord > 2) {
                numPolysyllables++;
            }
            numSyllables += inThisWord;

        }

        return new int[]{numSyllables, numPolysyllables};
    }
    public static void scoreTypes(String input) {
        double ari = 4.71 * ((double)characters / (double)words) + 0.5 * ((double)words / (double)sentences) - 21.43;
        double fk = 0.39 * (double)words/(double)sentences + 11.8 * (double)syllables[0] / (double)words- 15.59;
        double smog = 1.043 * Math.sqrt((double)syllables[1] * 30 / (double)sentences) + 3.1291;
        double cl =  0.0588 * ((double)characters / words * 100) - 0.296 * ((double) sentences / words * 100) - 15.8;
        double totalScore = ari + fk + smog + cl;
        switch(input) {
            case "ARI":
            System.out.println("Automated Readability Index:" + ari + "(about " + age(ari) + ")");
            break;
            case "FK":
            System.out.println("Flesch–Kincaid readability tests: " + fk + "(about" + age(fk) + ")");
            break;
            case "SMOG":
            System.out.println("Simple Measure of Gobbledygook: " + smog + "(about" + age(smog) + ")");
            break;
            case "CL":
            System.out.println("Coleman–Liau index: " + cl + "(about " + age(cl) + ")");
            case "all":
            System.out.println("Automated Readability Index:" + ari + "(about " + age(ari) + ")");
            System.out.println("Flesch–Kincaid readability tests: " + fk + "(about" + age(fk) + ")");
            System.out.println("Simple Measure of Gobbledygook: " + smog + "(about" + age(smog) + ")");
            System.out.println("Coleman–Liau index: " + cl + "(about " + age(cl) + ")");
            System.out.print("\nThis text should be understood by " + age(totalScore/4) + " year olds.");
        }
    }
}



