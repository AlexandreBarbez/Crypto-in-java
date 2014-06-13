package utils;

import java.util.Arrays;
import java.util.Comparator;

public class Analyzer
{
    /**
     * Class utilisée pour decryptage du carré de Polybe , est utilisé pour créer un tableau de Lettre représentant l'alphabet et les % d'apparition de chacune des lettres
     */
    public static class Lettre {
        private char laLettre;
        private float laFrequence;

        public Lettre(char p_lettre, float p_frequence){
            this.laLettre = p_lettre;
            this.laFrequence = p_frequence;
        }

        public char getLettre(){
            return laLettre;
        }

        public float getFrequence(){
            return laFrequence;
        }
    }

    public static Lettre[] lesLettres = new Lettre[26];

    /**
    * utilisé pour remplir le tableau de lettre lesLettres par le bloc static de Analyser
    * */
    public static Object[][] commonFrenchLetters =
    {
        {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",},
        {9.42f,1.02f,2.64f,3.39f,15.87f,0.95f,1.04f,0.77f,8.41f,0.89f,0.00f,5.34f,3.24f,7.15f,5.14f,2.86f,1.06f,6.46f,7.90f,7.26f,6.24f,2.15f,0.00f,0.30f,0.24f,0.32f}
    };


    public static int countSubstringOccurences(String subStr, String str)
    {
        return (str.length() - str.replace(subStr, "").length()) / subStr.length();
    }

    /**
     * Computes the divergence of the percentage of occurrences of the letters in the string with the statistics of the language
     * (The smaller the better)
     * @return
     */
    public static float getFitnessIndicator(String decryptedString)
    {
        Object[][] commonFrenchLetters = Analyzer.commonFrenchLetters;
        Object[] letters = commonFrenchLetters[0];
        Object[] percentage = commonFrenchLetters[1];

        float indicator = 0f;
        for (int i = 0; i < letters.length; i++)
        {
            int count = Analyzer.countSubstringOccurences((String) letters[i], decryptedString);
            indicator += Math.abs((Float)percentage[i] - ((float)count / (float)decryptedString.length()) * 100f);
        }
        return indicator;
    }

    /**
     * Bloc statique remplissant le tableau de Lettre lesLettres en utilisant le tableau commonFrenchLetters
     */
    static {
        for(int i=0 ; i<lesLettres.length; ++i){
            lesLettres[i]=new Lettre(commonFrenchLetters[0][i].toString().charAt(0),(Float)commonFrenchLetters[1][i]);
        }
        Arrays.sort(lesLettres, new Comparator<Lettre>() {
            @Override
            public int compare(Lettre o1, Lettre o2) {
                return (int) (o2.getFrequence()*100-o1.getFrequence()*100);
            }
        });
    }
}
