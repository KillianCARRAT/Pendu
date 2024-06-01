import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Controleur du clavier
 */
public class ControleurLettres implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;
    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    ControleurLettres(MotMystere modelePendu, Pendu vuePendu){
        this.modelePendu = modelePendu;
        this.vuePendu = vuePendu;
    }

    /**
     * Actions à effectuer lors du clic sur une touche du clavier
     * Il faut donc: Essayer la lettre, mettre à jour l'affichage et vérifier si la partie est finie
     * @param actionEvent l'événement
     */
    @Override
    public void handle(ActionEvent event) {
        Button bouton = (Button) event.getSource();
        char lettre = bouton.getText().charAt(0);
        bouton.setDisable(true);

        modelePendu.essaiLettre(lettre);

        this.vuePendu.majAffichage();

        // Vérifie si le joueur a gagné
        if (this.modelePendu.gagne()) {
            this.vuePendu.popUpMessageGagne();
            this.vuePendu.getChrono().stop();
        }

        // Vérifie si le joueur a perdu
        if (this.modelePendu.perdu()) {
            this.vuePendu.popUpMessagePerdu();
            this.vuePendu.getChrono().stop();
        }
    }
}
