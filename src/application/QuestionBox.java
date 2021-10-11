package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** ConfirmBox class utilizes an internal display method to confirm a user action
 * @author Derek DiLeo*/
public class QuestionBox {

    // Define boolean variable to be returned to  display() caller
    static String answer;

    /** display() method displays the ConfirmBox to the user
     * @param title title of the pop-up window to be displayed
     * @param message message displayed in the pop-up window via Label class
     * @return boolean value (yes or no)
     * @author Derek DiLeo
     */
    public static String display(String title, String message) {
        Stage window = new Stage(); // window is easier to grasp than 'stage'

        // Create StackPanes (two needed for CSS to function correctly)
        StackPane stackPane1 = new StackPane();
        StackPane stackPane2 = new StackPane();

        // Create layout and add padding
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));

        // Block any input events until this window is closed
        window.initModality(Modality.APPLICATION_MODAL);

        // Set window title and dimensions
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);

        // Create a label to display the String message parameter which is passed from caller
        Label label = new Label();
        label.setText(message);
        
        // Create a text field for user to input a string
        TextField field = new TextField();

        // Create two buttons and define their behavior
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(e -> {
            answer = field.getText();
            window.close();
        });


        // Add all elements to VBox layout, center, add VBox layout and stackPane2 to stackPane1
        layout.getChildren().addAll(label, field, submitButton);
        layout.setAlignment(Pos.CENTER);
        stackPane1.getChildren().addAll(stackPane2, layout);

        // Create and set Scene from stackPane1
        Scene scene = new Scene(stackPane1);
        window.setScene(scene);

        // Show window and wait for user interaction
        window.showAndWait();
        return answer;
    }
}
