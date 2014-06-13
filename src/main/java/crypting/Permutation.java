package crypting;

import utils.Normalizer;

public class Permutation
{
    private static String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static char[] letterTable = letters.toCharArray();
    private String cryptedString = new String();
    private char[] cryptedTable = new char[letters.length()];
    private String text;
    private String key;
    private char[] keyTable;

    public Permutation(String text)
    {
        this.text = text;
    }

    public Permutation(String text, String key)
    {
        this.text = text;
        this.key = key;
        this.keyTable = key.toCharArray();
    }

    public String encrypt()
    {
        String normalizedString = Normalizer.normalize(text);

        String encryptedString = "";
        for (char clearLetter : normalizedString.toCharArray())
        {
            encryptedString += keyTable[letters.indexOf(clearLetter)];
        }

        return encryptedString;
    }

    public String decrypt()
    {
        String normalizedString = Normalizer.normalize(text);

        String decryptedString = "";
        for (char clearLetter : normalizedString.toCharArray())
        {
            decryptedString += letterTable[key.indexOf(clearLetter)];
        }

        return decryptedString;
    }

    /**
     * Generates a crypted letter table
     */
    private void generateCryptedString()
    {
        char chosenLetter;
        for (int i = 0; i < letterTable.length; i++)
        {
            do
            {
                chosenLetter = letterTable[(int)(Math.random() * letterTable.length)];
            } while(cryptedString.indexOf(chosenLetter) > -1);

            cryptedString += chosenLetter;
        }

        cryptedTable = cryptedString.toCharArray();
    }

    /**
     * Generates the decrypting permutation table from the occurrences
     * @param occurrences
     * @return
     */
    /*private Map<String, String> generatePermutationTable(Map<String, Integer> occurrences)
    {
        Map<String, String> permutationTable = new HashMap<String, String>();
        Object[][] commonFrenchLetters = Analyzer.getSortedLetters();
        int i = 0;
        for (Map.Entry<String, Integer> occurrence : occurrences.entrySet())
        {
            System.out.println(occurrence.getKey() + " => " + ((float)occurrence.getValue() / (float)text.length() * 100));
            permutationTable.put(occurrence.getKey(), (String)commonFrenchLetters[0][i]);

            i++;
        }

        return permutationTable;
    }*/

    /**
     * Checks the probability of every crypting possibility to be the right one
     * @return Object[][] with Object[0][n] = the crypted string and Object[1][n] = the indicator
     */
    public void analyze()
    {

    }

    public float checkFitness(String str)
    {
        return 0f;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
