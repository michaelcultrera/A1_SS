package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Part1 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set layout
        HBox layout = new HBox();

        // Variable declarations to store images
        String file;
        Image[] cards = new Image[54];
        ImageView[] images = new ImageView[54];

        // Load all card images
        for(int i=0; i < cards.length; i++) {
            file = "/Cards/"+(i+1)+".png";
            cards[i] = new Image(file);
            images[i] = new ImageView(cards[i]);
        }

        // To generate random cards
        Random rand = new Random();
        int randomValue;
        for(int i=0; i<3; i++) {
            randomValue = rand.nextInt(54);
            layout.getChildren().add(images[randomValue]);
        }

        // Stage for cards
        primaryStage.setScene(new Scene(layout));
        primaryStage.setTitle("Part_1");
        primaryStage.show();
    }

    public static void main(String[] args) { launch(args); }
}