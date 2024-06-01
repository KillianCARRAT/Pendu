import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    private Text temps;
    private long duree;
    private long tempsCourant;

    public ControleurChronometre(Text temps) {
        this.duree = 0;
        this.tempsCourant = -1; // le Chrono n’est pas encore lancé
        this.temps = temps;
    }

    @Override
    public void handle(ActionEvent t) {
        long heureDuSysteme = System.currentTimeMillis();
        if (this.tempsCourant != -1) {
            // calcul du tps ´ecoul´e depuis la derni`ere frame
            long tempsEcoule = heureDuSysteme - this.tempsCourant;
            this.duree += tempsEcoule;
            this.temps.setText(duree / 1000 + "");
        }
        this.tempsCourant = heureDuSysteme;
    }
}
