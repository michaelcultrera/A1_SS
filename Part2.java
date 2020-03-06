package code;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.text.DecimalFormat;

public class Part2 extends Application {

    Label amountLabel, yearsLabel, interestLabel, futureLabel;
    TextField amountText, yearsText, interestText, futureAmount;
    Button calculate;

    // Used to put future value to 2 decimals
    DecimalFormat format = new DecimalFormat("0.00");

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Set border layout
        BorderPane pane = new BorderPane();
        pane.setTop(getGridPane());
        pane.setBottom(getHBox());

        // Set the scene and title
        primaryStage.setScene(new Scene(pane));
        primaryStage.setTitle("Question_2");
        primaryStage.show();
    }

    public GridPane getGridPane() {

        // Set grid layout
        GridPane layout = new GridPane();
        layout.setVgap(0);
        layout.setHgap(5);
        layout.setPadding(new Insets(10, 10, 0, 10));

        // Label definitions
        amountLabel = new Label("Investment Amount");
        yearsLabel = new Label("Years");
        interestLabel = new Label("Annual Interest Rate");
        futureLabel =  new Label("Future Value");

        // Adding labels to layout
        layout.add(amountLabel, 0, 0);
        layout.add(yearsLabel, 0, 1);
        layout.add(interestLabel, 0, 2);
        layout.add(futureLabel, 0, 3);

        // Text field declarations and positions
        amountText = new TextField();
        amountText.setAlignment(Pos.BASELINE_RIGHT);
        yearsText = new TextField();
        yearsText.setAlignment(Pos.BASELINE_RIGHT);
        interestText = new TextField();
        interestText.setAlignment(Pos.BASELINE_RIGHT);
        futureAmount = new TextField();
        futureAmount.setAlignment(Pos.BASELINE_RIGHT);

        // Add text fields to layout
        layout.add(amountText, 1, 0);
        layout.add(yearsText, 1, 1);
        layout.add(interestText, 1, 2);
        layout.add(futureAmount, 1, 3);

        return layout;
    }

    public HBox getHBox() {

        // Set layout insets
        HBox layout = new HBox();
        layout.setPadding(new Insets(5, 10, 10, 0));

        // Calculation button declaration
        calculate = new Button("Calculate");

        // Set button action
        calculate.setOnAction(e -> {
            futureAmount.setText("");
            double future = 0;
            double amount = isDouble(amountText);
            double years = isDouble(yearsText);
            double interest = isDouble(interestText);
            future = futureAmount(amount, years, interest);
            if(amount >= 0 && years >= 0 && interest >= 0)
                futureAmount.setText(format.format(future));
        });

        // Add button to layout
        layout.getChildren().add(calculate);
        layout.setAlignment(Pos.BOTTOM_RIGHT);

        return layout;
    }

    // Formula needed to calculate
    public double futureAmount(double amount, double years, double interest) {
        return amount * (Math.pow((1 + (interest/100)/12),(years*12)));
    }

    // Make sure text field is of type double
    public double isDouble(TextField field) {
        try {
            double value = Double.parseDouble(field.getText());
            return value;
        } catch(NumberFormatException e) {
            field.setText("Invalid Input");
            return -1;
        }
    }

    public static void main(String[] args) { launch(args); }
}