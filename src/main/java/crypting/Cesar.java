package crypting;

import utils.Analyzer;
import utils.Normalizer;


public class Cesar
{
    private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static char[] letterTable = letters.toCharArray();
    private String text;
    private int key;

    public Cesar(String text)
    {
        this.text = text;
    }

    public Cesar(String text, int key)
    {
        this.text = text;
        this.key = key;
    }

    public String encrypt()
    {
        String normalizedString = Normalizer.normalize(text);

        String encryptedString = "";
        for (char clearLetter : normalizedString.toCharArray())
        {
            encryptedString += letterTable[(letters.indexOf(clearLetter) + key) % letterTable.length];
        }

        return encryptedString;
    }

    public String decrypt()
    {
        key = letterTable.length - key;
        return encrypt();
    }

    public String[] getAllCombinations()
    {
        String[] combinations = new String[letterTable.length];
        for (int i = 0; i < letterTable.length; i++)
        {
            key = i + 1;
            combinations[i] = decrypt();
        }

        return combinations;
    }

    /**
     * Checks the probability of every crypting possibility to be the right one
     * @return Object[][] with Object[0][n] = the crypted string and Object[1][n] = the indicator
     */
    public Object[][] analyze()
    {
        String[] combinations = getAllCombinations();
        Object[][] statistics = new Object[2][combinations.length];
        int i = 0;
        for (String combination : combinations)
        {
            statistics[0][i] = combination;
            statistics[1][i] = Analyzer.getFitnessIndicator(combination);

            i++;
        }

        return statistics;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setKey(int key)
    {
        this.key = key;
    }
}
