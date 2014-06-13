package utils;

public class Normalizer
{

    /**
     * Removes special characters and spaces
     * @param inputText Text to normalize
     * @return Normalized text
     */
    public static String normalize(String inputText)
    {
        // Convert to non accented text
        String normalizedText = java.text.Normalizer.normalize(inputText, java.text.Normalizer.Form.NFD);
        normalizedText = normalizedText.replaceAll("[^\\p{ASCII}]", "");

        // Remove special characters
        normalizedText = normalizedText.replaceAll("\\W|\\d", "");

        // Uppercase characters
        normalizedText = normalizedText.toUpperCase();

        return normalizedText;
    }

    /**
     * Replace all the letterToRemove char in inputText with letterToReplaceWith
     * @param inputText
     * @param letterToRemove
     * @param letterToReplaceWith
     * @return
     */
    public static String replaceLetter(String inputText, char letterToRemove,char letterToReplaceWith)
    {
        String finalText = inputText.replace(letterToRemove,letterToReplaceWith);
        return finalText;
    }
}
