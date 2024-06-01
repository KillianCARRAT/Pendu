import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData;

import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.util.ArrayList;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {
    /**
     * modèle du jeu
     **/
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier; // ????
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane panelCentral;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */
    private Button bJouer;

    private Button bInfo;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le chrono
     * ...);pklmM<>?
     */
    @Override
    public void init() {
        this.modelePendu = new MotMystere("./test/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        // A terminer d'implementer
        this.boutonMaison = new Button();
        ImageView imgHome = new ImageView(this.lesImages.get(11));
        imgHome.setFitHeight(20);
        imgHome.setPreserveRatio(true);
        boutonMaison.setOnAction(new RetourAccueil(this.modelePendu, this));
        boutonMaison.setGraphic(imgHome);

        this.boutonParametres = new Button();
        ImageView imgPara = new ImageView(this.lesImages.get(12));
        imgPara.setFitHeight(20);
        imgPara.setPreserveRatio(true);
        boutonParametres.setGraphic(imgPara);

        this.bInfo = new Button();
        ImageView imgInfo = new ImageView(this.lesImages.get(13));
        imgInfo.setFitHeight(20);
        imgInfo.setPreserveRatio(true);
        bInfo.setGraphic(imgInfo);

        this.bJouer = new Button("Jouer");
        this.bJouer.setOnAction(new ControleurLancerPartie(modelePendu, this));

        this.chrono = new Chronometre();

        this.panelCentral = new BorderPane();

        this.niveaux = new ArrayList<>();
        this.niveaux.add("Facile");
        this.niveaux.add("Médium");
        this.niveaux.add("Difficile");
        this.niveaux.add("Expert");

        this.leNiveau = new Text(this.niveaux.get(0));

        this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        this.motCrypte.setFont(Font.font(20));

    }

    public TilePane initialiseClavier() {
        String res = "ABCDEFGHIJKLMNOPQRSTUVWXYZ-";

        // Convertir la chaîne en un tableau de caractères
        char[] charArray = res.toCharArray();
        TilePane tilePane = new TilePane();

        // Convertir le tableau de caractères en ArrayList de String
        for (char c : charArray) {
            Button button = new Button(String.valueOf(c));
            button.setOnAction(new ControleurLettres(this.modelePendu, this));
            tilePane.getChildren().add(button);
        }

        return tilePane;

    }

    /**
     * @return le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene() {
        BorderPane fenetre = new BorderPane();
        fenetre.setTop(this.titre());
        fenetre.setCenter(this.panelCentral);
        return new Scene(fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private BorderPane titre() {
        BorderPane top = new BorderPane();

        Text label = new Text("Jeu du Pendu");
        label.setFont(new Font("Arial", 40));
        top.setLeft(label);

        HBox bout = new HBox();
        bout.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.bInfo);

        top.setRight(bout);
        top.setPadding(new Insets(10, 10, 10, 10));
        top.setStyle("-fx-background-color: #aed6f1;");
        return top;
    }

    public void setLeNiveau(String n) {
        this.leNiveau = new Text(n);
    }

    /**
     * charge les images à afficher en fonction des erreurs
     * 
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire) {
        for (int i = 0; i < this.modelePendu.getNbErreursMax() + 1; i++) {
            File file = new File(repertoire + "/pendu" + i + ".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }

        File f1 = new File(repertoire + "/home.png");
        System.out.println(f1.toURI().toString());
        this.lesImages.add(new Image(f1.toURI().toString()));

        File f2 = new File(repertoire + "/parametres.png");
        System.out.println(f2.toURI().toString());
        this.lesImages.add(new Image(f2.toURI().toString()));

        File f3 = new File(repertoire + "/info.png");
        System.out.println(f3.toURI().toString());
        this.lesImages.add(new Image(f3.toURI().toString()));
    }

    public TitledPane radioBouton() {
        VBox vbox = new VBox();
        ToggleGroup group = new ToggleGroup();

        for (String res : this.niveaux) {
            RadioButton t = new RadioButton(res);
            t.setToggleGroup(group);
            t.setOnAction(new ControleurNiveau(modelePendu));

            vbox.getChildren().add(t);
        }

        ((ToggleButton) vbox.getChildren().get(0)).setSelected(true);
        TitledPane rad = new TitledPane("Niveau de difficulté", vbox);
        rad.setCollapsible(false);

        return rad;
    }

    public void modeAccueil() {
        this.panelCentral.setCenter(bJouer);

        VBox test = new VBox(this.bJouer, radioBouton());
        test.setPadding(new Insets(10, 10, 10, 10));
        test.setSpacing(5);

        this.panelCentral.setCenter(test);

    }

    public void modeJeu() {
        HBox centre = new HBox();

        // Partie gauche
        VBox encore = new VBox();
        Text mC = this.motCrypte;
        this.dessin = new ImageView(
                this.lesImages.get(this.modelePendu.getNbErreursMax() - modelePendu.getNbErreursRestants()));
        this.dessin.setPreserveRatio(true);

        TilePane clavier = initialiseClavier();
        clavier.setPadding(new Insets(10));

        encore.setSpacing(20);
        encore.setPadding(new Insets(10, 20, 10, 20));
        encore.getChildren().addAll(mC, this.dessin, clavier);
        encore.setAlignment(Pos.TOP_CENTER);

        // Cote droit
        VBox map = new VBox();
        Button bou = new Button("Nouveau mot");
        bou.setOnAction(new ControleurLancerPartie(this.modelePendu, this));
        Text vc = new Text("Difficulté " + this.leNiveau.getText());
        vc.setFont(new Font("Arial", 20));

        TitledPane ch = new TitledPane("Chronomètre", this.chrono);
        ch.setCollapsible(false);
        map.getChildren().addAll(vc, ch, bou);
        map.setSpacing(10);
        map.setPadding(new Insets(10));

        centre.getChildren().addAll(encore, map);
        this.panelCentral.setCenter(centre);
    }

    public void modeParametres() {

    }

    public void nouveauMystere() {
        if (this.leNiveau.getText() == "Facile") {
            System.out.println("f");
            this.modelePendu = new MotMystere("./test/french", 3, 10, MotMystere.FACILE, 10);
            this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        } else if (this.leNiveau.getText() == "Médium") {
            System.out.println("m");
            this.modelePendu = new MotMystere("./test/french", 3, 10, MotMystere.MOYEN, 10);
            this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        } else if (this.leNiveau.getText() == "Difficile") {
            System.out.println("d");
            this.modelePendu = new MotMystere("./test/french", 3, 12, MotMystere.DIFFICILE, 10);
            this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        } else if (this.leNiveau.getText() == "Expert") {
            System.out.println("e");
            this.modelePendu = new MotMystere("./test/french", 10, 25, MotMystere.EXPERT, 10);
            this.motCrypte = new Text(this.modelePendu.getMotCrypte());
        }
        System.out.println(this.leNiveau.getText());
    }

    /** lance une partie */
    public void lancePartie() {
        this.setLeNiveau(niveaux.get(this.modelePendu.getNiveau()));
        nouveauMystere();
        this.modeJeu();
        this.chrono.start();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage() {
        this.dessin.setImage(
                this.lesImages.get(this.modelePendu.getNbErreursMax() - modelePendu.getNbErreursRestants()));
        this.motCrypte.setText(this.modelePendu.getMotCrypte());
    }

    

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     * 
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono() {
        return this.chrono;
    }

    public Alert popUpPartieEnCours() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "La partie est en cours! Etes-vous sûr de l'interrompre ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }

    public Alert popUpReglesDuJeu() {
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        return alert;
    }

    public Alert popUpMessageGagne() {
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Vous avez gagné! Félicitations!", ButtonType.OK);
        alert.setTitle("Attention");
        alert.showAndWait();
        return alert;
    }

    public Alert popUpMessagePerdu() {
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous avez perdu! Recommencez!", ButtonType.OK);
        alert.setTitle("Attention");
        alert.showAndWait();
        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     * 
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    /**
     * Programme principal
     * 
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }
}
