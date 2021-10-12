package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/** AlertBox class utilizes an internal display method to alert user of something
 * @author Derek DiLeo*/
public class AlertBox {

    /** display() method displays the ConfirmBox to the user
     * @param title title of the pop-up window to be displayed
     * @param message message displayed in the pop-up window via Label class
     * @author Derek DiLeo
     */
    public static void display(String title, String message) {
        Stage window = new Stage(); // window is easier to grasp than 'stage'

        // Block any input events until this window is closed
        window.initModality(Modality.APPLICATION_MODAL);

        // Set window title and dimensions
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(100);

        // Ask user for fibonacci position (positive integers only!)
        Label label = new Label();
        label.setText(message);

        // Create button and define behavior
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> window.close());
        
        // Allow Enter key to trigger "Submit" button
        okButton.setOnKeyPressed(e -> {
        	if (e.getCode().equals(KeyCode.ENTER)) {
        		okButton.fire();
            }
        });

        // Create layout, add padding + elements, set center alignment
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10,10,10,10));
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);

        // Create and set Scene from VBox layout
        Scene scene = new Scene(layout);
        window.setScene(scene);

        // Show window and wait for user interaction
        window.showAndWait();
    }
}
