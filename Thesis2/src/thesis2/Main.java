package thesis2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author erichoppe
 */
public class Main extends Application
{
    Pane root;
    HBox hbox, hbox2;
    Button trainButton, testButton, generateButton, resetButton;
    Label result;
    
    Evaluator eval;
    
    public Main()
    {
        eval = new Evaluator(this);
    }
    
    @Override
    public void start(Stage primaryStage)
    {
        root = new Pane();
        
        hbox = new HBox();    
        hbox.setLayoutX(20);
        hbox.setLayoutY(320);
        hbox.setMinWidth(280);
        hbox.setAlignment(Pos.CENTER);
        
        hbox2 = new HBox();
        hbox2.setLayoutY(420);
        hbox2.setMinWidth(600);
        hbox2.setSpacing(20);
        hbox2.setAlignment(Pos.CENTER);
        
        root.getChildren().addAll(hbox, hbox2);
   
        result = new Label();
        result.setFont(Font.font("Helvetica", 20));
        hbox.getChildren().add(result);
        
        trainButton = new Button("Train NN");
        trainButton.setFont(Font.font("Helvetica", 14));
        trainButton.setMinWidth(80);
        
        testButton = new Button("Test NN");
        testButton.setFont(Font.font("Helvetica", 14));
        testButton.setMinWidth(80);
        
        generateButton = new Button("Generate");
        generateButton.setFont(Font.font("Helvetica", 14));
        generateButton.setMinWidth(80);
        
        resetButton = new Button("Reset");
        resetButton.setFont(Font.font("Helvetica", 14));
        resetButton.setMinWidth(80);
        
        hbox2.getChildren().addAll(trainButton, testButton,
                generateButton, resetButton);
        
        trainButton.setOnAction(event -> 
        {
            trainButton.setEffect(new DropShadow());
            testButton.setEffect(null);
            generateButton.setEffect(null);
            resetButton.setEffect(null);
            
            eval.handleButtonClicked(trainButton);
        });
        
        testButton.setOnAction(event -> 
        {
            testButton.setEffect(new DropShadow());
            trainButton.setEffect(null);
            generateButton.setEffect(null);
            resetButton.setEffect(null);
            
            eval.handleButtonClicked(testButton);
        });
        
        generateButton.setOnAction(event -> 
        {
            generateButton.setEffect(new DropShadow());
            testButton.setEffect(null);
            trainButton.setEffect(null);
            resetButton.setEffect(null);
            
            eval.handleButtonClicked(generateButton);
        });
        
        resetButton.setOnAction(event ->
        {
            resetButton.setEffect(new DropShadow());
            testButton.setEffect(null);
            generateButton.setEffect(null);
            trainButton.setEffect(null);
            
            eval.handleButtonClicked(resetButton);
        });

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Generative Adversarial Network (Sample Size: "
                + "10,000; Training Size: 3,000)");
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
