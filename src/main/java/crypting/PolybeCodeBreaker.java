package crypting;

import utils.Analyzer;

import java.util.*;

/**
 * Created by Alex on 10/06/2014.
 */
public class PolybeCodeBreaker {

    List<DaPermutation> permutationFaites=new ArrayList<DaPermutation>();
    Analyzer.Lettre[] mesLettres = Analyzer.lesLettres;
    DaPermutation prochainePermutation = null;
    private String text;

    /**
     * Constructeur
     * @param text
     */
    public PolybeCodeBreaker(String text) {
        this.text = text;
    }

    public String decrypterAvecLettre(Analyzer.Lettre[] lesLettres)
    {
        String DecryptedText = new String();
        Map<String , Object> theMap = makeNewMapWithPossibleLetters(lesLettres);
        for (String Bigram : divisertexteCrypteEnNombre())
        {
            DecryptedText += theMap.get(Bigram);
        }
        return DecryptedText;
    }

    /**
     * Cptain Obvious
     */
    public String[] divisertexteCrypteEnNombre()
    {

        String[] dividedText = new String[text.length()/2];
        int j = 0;
        for(int i=0 ; i < text.length()/2 ; i++)
        {
            dividedText[i]=text.substring(j,j+2);
            j=j+2;
        }
        return dividedText;
    }

    /**
     * Fait la map reliant une lettre a un des nombre du carré de polybe : utilisé pour decrypté
     * @param lesLettres
     * @return
     */
    public Map<String, Object> makeNewMapWithPossibleLetters(Analyzer.Lettre[] lesLettres){
        Map<String,Integer> mapWithNumbers = sortMap(vectoriserTexteCrypte());
        Map<String,Object> mapWithLetters = new HashMap<String, Object>();

        int i =0;

        for (Map.Entry entry : mapWithNumbers.entrySet()) {
            mapWithLetters.put((String) entry.getKey(), lesLettres[i].getLettre());
            i++;
        }
        return mapWithLetters;
    }


    /**
     * Remplis une map avec la lettre Et le nombre d'occurence d'apparition (vectorisation)
     * @return
     */
    public Map vectoriserTexteCrypte()
    {
        Map<String,Integer> theMap = new HashMap<String, Integer>();


        for(String theCryptedLetter : divisertexteCrypteEnNombre()){

            if (theMap.containsKey(theCryptedLetter)){
                theMap.put(theCryptedLetter,theMap.get(theCryptedLetter)+1);
            }
            else{
                theMap.put(theCryptedLetter,1);
            }
        }
        return theMap;
    }


    /**
     * Tri de la map , celui qui a le plus d'apparition en premier
     * @param map
     * @return
     */
    public static Map<String, Integer> sortMap(final Map<String, Integer> map) {
        Map<String, Integer> sortedMap = new TreeMap<String, Integer>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return map.get(o2).compareTo(map.get(o1));
            }
        });
        sortedMap.putAll(map);
        return sortedMap;
    }

    /**
     * Retourne l'ensemble des permutation encore a faire (les possible - les deja faites)
     */

    private List<DaPermutation> calculerPermutationAFaire(){
        //Creer tableau de permutation + poids
        List<DaPermutation>permutationsPossible=new ArrayList<DaPermutation>();
        float lePoids ;
        for(int i=0; i<mesLettres.length; ++i){
            for(int j=0; j<mesLettres.length; ++j){
                if(i==j){
                    continue;
                }
                lePoids = Math.abs(i-j)+Math.abs(mesLettres[i].getFrequence() + mesLettres[j].getFrequence());
                DaPermutation nouvellePermutation = new DaPermutation(i,j,lePoids);
                if(!permutationsPossible.contains(nouvellePermutation)){
                    permutationsPossible.add(nouvellePermutation);
                }
            }
        }
        //ordoner le tableau
        Collections.sort(permutationsPossible,new Comparator<DaPermutation>() {
            @Override
            public int compare(DaPermutation o1, DaPermutation o2) {
                //enlever le - pour usage reel
                return -(int) (o1.getPoids()*100-o2.getPoids()*100);
            }
        });

        permutationsPossible=enleverPermutationFaite(permutationsPossible,permutationFaites);
        return permutationsPossible;
    }

    /**
     * Enlève des permutationAFaire les permutationFaites
     * @param PermutationAFaire
     * @param permutationFaites
     * @return
     */
    private static List<DaPermutation> enleverPermutationFaite(List<DaPermutation>PermutationAFaire,List<DaPermutation>permutationFaites){

        Iterator<DaPermutation> permuIterator = PermutationAFaire.iterator();
        while (permuIterator.hasNext()){
            DaPermutation laPermutation = permuIterator.next();
            if(permutationFaites.contains(laPermutation)){
                permuIterator.remove();
            }
        }
        return PermutationAFaire;
    }

    /**
     * renvoie soit le text modifié avec la prochaine permutation à faire soit si il n'y as pas de permutation a faire renvoie le text inchangé
     * @return
     */
    public String decripterAvecProchainePermutation(){

        if(prochainePermutation!=null){
            Analyzer.Lettre[] lesNouvellesLettres = permuter(prochainePermutation);
            return decrypterAvecLettre(lesNouvellesLettres);
        }
        else{
            return decrypterAvecLettre(mesLettres);
        }
    }

    /**
     *
     * @return
     */
    public boolean genererProchainePermutation() {
        List<DaPermutation> permutationAFaire = calculerPermutationAFaire();
        if(permutationAFaire.isEmpty()){
            return false;
        }
        prochainePermutation = permutationAFaire.get(0);
        return true;
    }

    /**
     * Permute avec une permutation donnée
     * @param laProchainePErmutation
     * @return
     */
    private Analyzer.Lettre[] permuter(DaPermutation laProchainePErmutation) {
        //clonage du tableau
        Analyzer.Lettre[] lesNouvellesLettres = clonerMesLettres();

        //permuter !
        Analyzer.Lettre lettreAPermuter = lesNouvellesLettres[laProchainePErmutation.getPosition1()];
        lesNouvellesLettres[laProchainePErmutation.getPosition1()]=lesNouvellesLettres[laProchainePErmutation.getPosition2()];
        lesNouvellesLettres[laProchainePErmutation.getPosition2()]=lettreAPermuter;


        return lesNouvellesLettres;
    }

    private Analyzer.Lettre[] clonerMesLettres() {
        Analyzer.Lettre[] lesNouvellesLettres = new Analyzer.Lettre[mesLettres.length];
        for(int i=0;i<lesNouvellesLettres.length;i++){
            lesNouvellesLettres[i] = mesLettres[i];
        }
        return lesNouvellesLettres;
    }

    /**
     * Ajouter une permutation faite au tableau permutationFaites (utilise peutEtre Jointe et joindre)
     * @param laNouvellePermutation
     */
    private void insererNouvellePermutation(DaPermutation laNouvellePermutation)
    {
        for(DaPermutation unePermutation : permutationFaites){
            if(laNouvellePermutation.peutEtreJointe(unePermutation)){
                unePermutation.joindre(laNouvellePermutation);
                return;
            }
        }
        permutationFaites.add(laNouvellePermutation);
    }

    /**
     * Parcequ'on devrait toujours avoir le choix, ici on decide d'accepter la permutation en changeant le tableau sur lequel tout se base !
     */
    public void accepterPermutation(){
        insererNouvellePermutation(prochainePermutation);
        mesLettres=permuter(prochainePermutation);
        prochainePermutation=null;
    }

    /**
     * On refuse la permutation , on ne modifie pas le tableau de lettre mais on ajoute quand même la permutation a celle faite pour ne pas la refaire a nouveau !
     */
    public void refuserPermutation(){
        insererNouvellePermutation(prochainePermutation);
        prochainePermutation=null;
    }

    /**
     * Mode manuel de permutation, on passe a la mano les lettre a permuter , on ecrit  donc une permutation personalisé qu'on verifie
     * @param lettre1
     * @param lettre2
     */
    public void permuter(char lettre1,char lettre2){
        int position1= -1;
        int position2= -1;
        for(int i=0; i<mesLettres.length;++i){
           if (mesLettres[i].getLettre()==lettre1){
               position1=i;
           }
           else if (mesLettres[i].getLettre()==lettre2){
               position2=i;
           }
        }
        if (position1==-1 || position2 == -1)
            throw new IllegalArgumentException("Parle moi mieux , j'piges pas les lettres que tu ecris (METS LE KIKOOCAPSLOCK EN MODE ON P-E)");
        prochainePermutation=new DaPermutation(position1,position2,76);

    }
}
