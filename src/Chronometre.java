import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text {
    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le temps écoulé en millisecondes
     */
    private long elapsedTime = 0;
    /**
     * le temps de départ en millisecondes
     */
    private long startTime = 0;

    /**
     * Constructeur permettant de créer le chronomètre
     * avec un label initialisé à "0:0:0"
     * Ce constructeur crée la Timeline, la KeyFrame et initialise le Text
     */
    public Chronometre() {
        this.setText("0 s");
        this.setFont(new Font(20));
        this.setTextAlignment(TextAlignment.CENTER);

        // Create KeyFrame and Timeline
        keyFrame = new KeyFrame(Duration.millis(1000), e -> updateElapsedTime());
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Permet au contrôleur de mettre à jour le texte
     * la durée est affichée sous la forme h:m:s
     * @param tempsMillisec la durée à afficher
     */
    public void setTime(long tempsMillisec) {
        long totalSeconds = tempsMillisec / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;

        if (minutes > 0) {
            this.setText(minutes + " min " + seconds + " s");
        } else {
            this.setText(seconds + " s");
        }
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start() {
        startTime = System.currentTimeMillis() - elapsedTime;
        timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop() {
        elapsedTime = System.currentTimeMillis() - startTime;
        timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime() {
        timeline.stop();
        elapsedTime = 0;
        startTime = 0;
        setTime(0);
    }

    /**
     * Met à jour le temps écoulé et le texte affiché
     */
    private void updateElapsedTime() {
        long currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - startTime;
        setTime(elapsedTime);
    }
}
