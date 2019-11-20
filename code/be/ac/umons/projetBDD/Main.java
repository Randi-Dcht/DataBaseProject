package be.ac.umons.projetBDD;

public class Main {

    public static void main(String[] args)
    {
        Saving.REOPEN("basedonnee");
        BaseDonne bd = new BaseDonne("jdbc:sqlite:");
        bd.connection("./misc/TestGui.db");
        bd.creerBaseDonnee();
//        bd.creerTable("CREATE TABLE etudiant(Matricule integer PRIMARY KEY, Nom text, Faculte text)");
        bd.creerTable("etudiant", "Matricule integer PRIMARY KEY, Nom text, Faculte text");
//        bd.insererTable("INSERT INTO etudiant(Matricule,Nom,Faculte) VALUES(1234,'rnd','sciences')");
        bd.insererTable("etudiant", "1234,'rnd','sciences'");
        bd.terminer();
        Saving.CLOSE();
    }

}
