package crypting;

import utils.Normalizer;

/**
 * Classe gérant l'encodage et décodage avec le code
 */
public class Polybe
{



    private String text;
    private String[] polybeArray ={"00","01","02","03","04","10","11","12","13","14","20","21","22","23","24","30","31","32","33","34","40","41","42","43","44"};
    private static String letters ="ABCDEFGHIJKLMNOPQRSTUVXYZ";

    public Polybe(String text)
    {
        this.text = text;
    }

    public String encrypt() {

        String normalizedString = Normalizer.normalize(Normalizer.replaceLetter(text,'w','v'));
        String encryptedString = "";
        for (char clearLetter : normalizedString.toCharArray())
        {
            encryptedString += polybeArray[(letters.indexOf(clearLetter))];
        }
        return encryptedString;
    }

    /**
     * decrypt an encoded text with the Polybe method by cutting them into couple of values and search the letter that correspond
     * @return
     */
    public String decrypt() {
        int i = 0;

        String decryptedtext ="";

        do{
            String theEncodeLetter = text.substring(i,i+2);
            decryptedtext += letters.charAt(java.util.Arrays.asList(polybeArray).indexOf(theEncodeLetter));
            i=i+2;
        }while (i+2<=text.length());
        return decryptedtext;
    }
}
