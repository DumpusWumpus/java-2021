//package Final;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class RPSGame extends Application {
    public void start(Stage stage) throws Exception {
        // Load the FXML file.
        Parent parent = FXMLLoader.load(getClass().getResource("RPSGame.fxml"));

        // Build the scene graph.
        Scene scene = new Scene(parent);

        // Display our window, using the scene graph.
        stage.setTitle("Rock, Paper, Scissors Game!");
        stage.setScene(scene);
        stage.show();
    }

   public static void main(String[] args){
      // Launch the application.
      launch(args);
   }
}
