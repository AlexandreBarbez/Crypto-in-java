package crypting;

/**
 * Created by Alex on 10/06/2014.
 */
public class DaPermutation {

        private int position1;
        private int position2;
        private float poids;

        public DaPermutation(int position1, int position2, float poids) {
            this.position1 = Math.min(position1, position2);
            this.position2 = Math.max(position1, position2);
            this.poids = poids;
        }

        public int getPosition1() {
            return position1;
        }

        public int getPosition2() {
            return position2;
        }

        public float getPoids() {
            return poids;
        }

        public void setPoids(float poids) {
            this.poids = poids;
        }

        /**
         * test si egal a l'autre permutation linké
         * @param o
         * @return
         */
        @Override
        public boolean equals(Object o){
            if( o instanceof DaPermutation){
                return((DaPermutation) o).position1==position1 && ((DaPermutation) o).position2==position2;
            }return false;
        }

        /**
         * crée un identifiant "unique" pour la permutation : le hashcode
         * @return
         */
        @Override
        public int hashCode(){
            return position1*100+position2;
        }

        @Override
        public String toString() {
            return "Permutation{" +
                    "position1=" + position1 +
                    ", position2=" + position2 +
                    ", poids=" + poids +
                    '}';
        }

        /**
         * Test si une jointure est possible avec la permutation passé en parametre
         * @param uneAutrePermutation
         * @return
         */
        public boolean peutEtreJointe(DaPermutation uneAutrePermutation){
            return this.position1==uneAutrePermutation.position2 || this.position2==uneAutrePermutation.position1;
        }

        /**
         * Joint les permutation en changeant la valeur de position1 ou position2 de celle de l'objet courant (joindre 2,3 avec 3,7 revient a changer 2,3 en 2,7)
         * @param permuAJoindreAvec
         */
        public void joindre(DaPermutation permuAJoindreAvec){
            if(this.position1==permuAJoindreAvec.position2){
                this.position1=permuAJoindreAvec.position1;
            }else if(this.position2==permuAJoindreAvec.position1){
                this.position2=permuAJoindreAvec.position2;
            }
        }
}