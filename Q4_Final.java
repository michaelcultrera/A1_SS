import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.event.EventHandler;


public class Q4_Final extends Application 
{
    //initialize variables
    int[] count;
    Rectangle[] graphBars;
    Label[] letters;
    BorderPane pane;

    public void start(Stage primaryStage)
    {
        count = new int[26]; 
        graphBars =  new Rectangle[26]; //1 bar for each letter of the alphabet
        letters = new Label[26];
        countLetters("Q4.java");

        pane = new BorderPane();
        pane.setTop(makeLayout());
        Scene scene = new Scene(pane,400,400);
        primaryStage.setTitle("Q4");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //count frequency of each letter in file
    public void countLetters(String filename)
    {
        //try catch used in case file is not found
        try  
        {
            Scanner input = new Scanner(new File(filename)); 
            String line;
            while(input.hasNextLine())  //execute loop if there is another line available
            {
                line = input.nextLine();  //store line in string
                char[] letter = line.toCharArray(); //character array used to avoid runtime errors
                
                //iterate through char array and count frequency of letters
                for(int i = 0; i < letter.length; i++) 
                {
                    if(letter[i] >= 'A' && letter[i] <= 'Z')
                    {
                        count[letter[i] - 'A']++;
                    }
                    if(letter[i] >= 'a' && letter[i] <= 'z')
                    {
                        count[letter[i] - 'a']++;
                    }
                }
                
            }
            input.close(); //close input file stream
        }
        catch (Exception e) 
        {
            System.out.println("File Not Found!");
        }
    }

    //set up bar graph using horizontal box layout
    public HBox makeGraph() 
    {
        HBox graph = new HBox();
        for(int i = 0; i < 26; i++)
        {
            graphBars[i] = new Rectangle();
            graphBars[i].setX(i*5 + 15);
            graphBars[i].setY(50);
            graphBars[i].setWidth(10);
            graphBars[i].setHeight(count[i]);
            graphBars[i].setStroke(Color.BLACK);
            graphBars[i].setFill(Color.WHITE);
            graph.setAlignment(Pos.BOTTOM_CENTER);
            graph.getChildren().add(graphBars[i]);
        }
        return graph;
    }

    public HBox makeLabel() 
    {
        //print out letters of alphabet on x-axis
        HBox label = new HBox(2);
        char letter = 'A';
        for(int i=0; i < 26; i++) {
            letters[i] = new Label(letter + "");
            label.getChildren().add(letters[i]);
            letter++;
        }
        label.setAlignment(Pos.BOTTOM_CENTER);
        return label;
    }

    //make field underneath graph consisting of filename label and txt field to input file name,using HBOX
    public HBox makeInput() {
        HBox input = new HBox();
        Label filename = new Label("Filename");
        TextField path = new TextField();
        path.setMinWidth(200);
        path.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event)  //handle method for when user hits enter key instead of clicking button
            {
                if(event.getCode() == KeyCode.ENTER)
                {
                    resetCount();
                    countLetters(path.getText());
                    pane.setTop(makeLayout());
                }
            }
        });
        Button btn = new Button("View");
        btn.setOnAction(e ->             //code to execute when button is pressed
        {
            resetCount();
            countLetters(path.getText());
            pane.setTop(makeLayout());
        });
        input.getChildren().addAll(filename,path,btn);
        input.setAlignment(Pos.BOTTOM_CENTER);  //HBOX formatting
        return input;
    }

    //VBOX used to stack graph, labels, and user input field
    public VBox makeLayout()
    {
        VBox layout = new VBox();
        layout.getChildren().add(makeGraph());
        layout.getChildren().add(makeLabel());
        layout.getChildren().add(makeInput());
        return layout;
    }

    public void resetCount() {
        for(int i = 0; i < 26; i++) 
        {
            count[i] = 0;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}