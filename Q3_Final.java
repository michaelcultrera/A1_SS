import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Random;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class Q3_Final extends Application
{
    Circle mainCircle, c1, c2, c3;
    Label lb1, lb2, lb3;
    Line l1, l2, l3;
    Random random;
    double [] initialValues;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Pane pane = new Pane();
        random = new Random();
        
        //create main circle
        mainCircle = new Circle(100);
		mainCircle.setCenterX(150);
		mainCircle.setCenterY(150);
		mainCircle.setFill(Color.WHITE);
        mainCircle.setStroke(Color.BLACK);
        
        pane.getChildren().add(mainCircle); //add main circle to pane
        
        initialValues = setInitValues(); //get x and y coordinates for 1st small circle
        c1 = new Circle(5);        
		c1.setCenterX(initialValues[0]); //use values retrieved from "setInitValues" to set x,y of circle
		c1.setCenterY(initialValues[1]);
		c1.setFill(Color.RED);
        c1.setStroke(Color.BLACK);

        initialValues = setInitValues(); //get x and y coordinates for 2nd small circle
        c2 = new Circle(5);
		c2.setCenterX(initialValues[0]);
		c2.setCenterY(initialValues[1]);
		c2.setFill(Color.RED);
        c2.setStroke(Color.BLACK);

        initialValues = setInitValues(); //get x and y coordinates for 3rd small circle
        c3 = new Circle(5);
		c3.setCenterX(initialValues[0]);
		c3.setCenterY(initialValues[1]);
		c3.setFill(Color.RED);
        c3.setStroke(Color.BLACK);

        //create a line connecting circle(c1) to circle(c2)
        l1 = new Line();
        l1.startXProperty().bind(c1.centerXProperty()); 
        l1.startYProperty().bind(c1.centerYProperty());
        l1.endXProperty().bind(c2.centerXProperty());
        l1.endYProperty().bind(c2.centerYProperty());

        //create a line connecting circle(c2) to circle(c3)
        l2 = new Line();
        l2.startXProperty().bind(c2.centerXProperty()); 
        l2.startYProperty().bind(c2.centerYProperty());
        l2.endXProperty().bind(c3.centerXProperty());
        l2.endYProperty().bind(c3.centerYProperty());

        //create a line connecting circle(c3) to circle(c1)
        l3 = new Line();
        l3.startXProperty().bind(c3.centerXProperty()); 
        l3.startYProperty().bind(c3.centerYProperty());
        l3.endXProperty().bind(c1.centerXProperty());
        l3.endYProperty().bind(c1.centerYProperty());

        lb1 = new Label(); //create labels for angles
        lb2 = new Label();
        lb3 = new Label();
        updateLabels();   //create initial angles

        //handle output when circle 1 is moved
        EventHandler<MouseEvent> e1 = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                mouseEvent(event,c1);
            }
        };

        //handle output when circle 2 is moved
        EventHandler<MouseEvent> e2 = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                mouseEvent(event,c2);
            }
        };
        
        //handle output when circle 3 is moved
        EventHandler<MouseEvent> e3 = new EventHandler<>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                mouseEvent(event,c3);
            }
        };
        
        //Register handler for each node
        c1.addEventHandler(MouseEvent.MOUSE_DRAGGED, e1);
        c2.addEventHandler(MouseEvent.MOUSE_DRAGGED, e2);
        c3.addEventHandler(MouseEvent.MOUSE_DRAGGED, e3);

        //add all necessary nodes to pane
        pane.getChildren().addAll(c1,c2,c3,l1,l2,l3,lb1,lb2,lb3);        
        
        //display stage to user
        Scene scene = new Scene(pane, 300, 300);
        primaryStage.setTitle("Q3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //use equation of circle to map point on top half of circle
    public double get_y_above(double x)
    {
        double y = get_y_below(x);
        y = 150 - (y - 150);
        return y;
    }

    //use equation of circle to map point on bottom half of circle
    public double get_y_below(double x)
    {
        double y = Math.sqrt(Math.pow(100,2) - Math.pow(x-150,2)) + 150;
        return y;
    }

    //find the x and y coordinates of the small red circles
    public double[] setInitValues()
    {
        double [] values = new double[2];
        values[0] = (double) random.nextInt(200) + 50.0; //generate x value between 50 pixels and 250
        double val_y = Math.sqrt(Math.pow(100,2) - Math.pow(values[0]-150,2)) + 150; //get y-value from equation of circle
        
        if(random.nextInt() % 2 == 0) //randomize if its below the center of the circle
        {
            val_y = 150 - (val_y - 150);
        }
        values[1] = val_y;

        return values;
    }

    //get the coordinates of cursor and use coordinates to update angles
    public void mouseEvent(MouseEvent event, Circle c) 
    {
        double mouse_x = event.getX();
        double mouse_y = event.getY();
        double y_val;

        if(mouse_x >= 50 && mouse_x <= 250)
        {
            if(mouse_y <= 150)
            {
                mouse_y = get_y_above(mouse_x);
            }
            else
            {
                mouse_y = get_y_below(mouse_x);
            }
            c.setCenterX(mouse_x);
            c.setCenterY(mouse_y);
        }
        updateLabels();
    }

    //find new angles
    public void updateLabels() 
    {
        //length of black lines used to compute angles
        double a = length(c1, c2);
        double b = length(c2, c3);
        double c = length(c3, c1);
        
        //compute angles(in degrees) 
        int degA = (int)Math.toDegrees(Math.acos((a * a - b * b - c * c) / (-2 * b * c)));
        int degB = (int)Math.toDegrees(Math.acos((b * b - a * a - c * c) / (-2 * a * c)));
        int degC = (int)Math.toDegrees(Math.acos((c * c - b * b - a * a) / (-2 * a * b)));
        
        //use binding property to update values
        lb3.setText("" + degA);
        lb1.layoutXProperty().bind(c1.centerXProperty().add(5));
        lb1.layoutYProperty().bind(c1.centerYProperty().add(5));
        lb1.setText("" + degB);
        lb2.layoutXProperty().bind(c2.centerXProperty().add(5));
        lb2.layoutYProperty().bind(c2.centerYProperty().add(5));
        lb2.setText("" + degC);
        lb3.layoutXProperty().bind(c3.centerXProperty().add(5));
        lb3.layoutYProperty().bind(c3.centerYProperty().add(5));
    }
    
    //compute length of line 
    private double length(Circle c1, Circle c2) 
    {
		return Math.sqrt(Math.pow(c1.getCenterX() - c2.getCenterX(), 2) + 
                Math.pow(c1.getCenterY() - c2.getCenterY(), 2));
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    
}