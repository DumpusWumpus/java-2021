/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package Final;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.collections.FXCollections;
import javafx.geometry.Rectangle2D;
import java.util.*;
import java.io.File;

public class RPSGameController {
    @FXML
    private ImageView playerIcon;
    @FXML
    private ImageView computerIcon;
    @FXML
    private ImageView playerHandIcon;
    @FXML
    private ImageView cpuHandIcon;

    @FXML
    private Label playerTimesLabel;
    @FXML
    private Label cpuTimesLabel;

    @FXML
    private ComboBox<String> gamemodeComboBox;

    @FXML
    private Label timesLabel;
    @FXML
    private TextField timesTextField;

    // declare images
    private Image neutralFace;
    private Image happyFace;
    private Image sadFace;
    private Image rockHandImg;
    private Image paperHandImg;
    private Image scissorHandImg;

    private Media applaudSound;
    private Media lostSound;

    private MediaPlayer applaudPlayer;
    private MediaPlayer lostSoundPlayer;

    private String currentWinner;

    public void initialize()
    {
        // set the image variables to pictures
        neutralFace = new Image("normalFace.jpg");
        happyFace = new Image("happyFace.png");
        sadFace = new Image("sadFace.png");
        rockHandImg = new Image("rockHand.png");
        paperHandImg = new Image("paperHand.png");
        scissorHandImg = new Image("scissorsHand.png");

        // set both player icons to neutral face at start
        playerIcon.setImage(neutralFace);
        computerIcon.setImage(neutralFace);

        // flip the computer hand icon
        cpuHandIcon.setScaleX(-1);

        gamemodeComboBox.getItems().addAll(
            "One Game",
            "Five Games",
            "Infinite Games"
        );

        // hide the games played label until user chooses infinite games
        timesLabel.setVisible(false);
        timesTextField.setVisible(false);

        cpuTimesLabel.setVisible(false);
        playerTimesLabel.setVisible(false);

        // load the audio files
        lostSound = new Media(new File("sadTrombone.wav").toURI().toString());
        lostSoundPlayer = new MediaPlayer(lostSound);

        applaudSound = new Media(new File("applause.wav").toURI().toString());
        applaudPlayer = new MediaPlayer(applaudSound);
    }

    public void playButtonListener(){
        if(gamemodeComboBox.getValue() == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Select An Option");
            alert.setHeaderText(null);
            alert.setContentText("Please select an option from the games dropdown.");

            alert.showAndWait();
        }
        else if(gamemodeComboBox.getValue() == "One Game"){
            timesLabel.setVisible(false);
            timesTextField.setVisible(false);

            String playerHand = getHand();
            String cpuHand = RPSHandler.rollHand();

            setHandIcons(playerHand, cpuHand);

            String winner = RPSHandler.pickWinner(playerHand, cpuHand);

            if(winner == "PLAYER") playerWon();
            else if(winner == "CPU") computerWon();
            else tieGame();
        }
        else if(gamemodeComboBox.getValue() == "Five Games"){
            int playerWins = 0;
            int cpuWins = 0;

            for(int i = 0; i < 5; i++){
                String playerHand = getHand();
                String cpuHand = RPSHandler.rollHand();

                setHandIcons(playerHand, cpuHand);

                String winner = RPSHandler.pickWinner(playerHand, cpuHand);

                if(winner == "PLAYER") playerWins++;
                else if(winner == "CPU") cpuWins++;
            }

            playerTimesLabel.setText("You won " + playerWins + " times.");
            cpuTimesLabel.setText("Computer won " + cpuWins + " times.");
            cpuTimesLabel.setVisible(true);
            playerTimesLabel.setVisible(true);

            if(playerWins > cpuWins){
                playerWon();
            }
            else if(cpuWins > playerWins){
                computerWon();
            }
            else{
                tieGame();
            }
        }
        else if(gamemodeComboBox.getValue() == "Infinite Games"){
            int playerWins = 0;
            int cpuWins = 0;
            int gamesPlayed = 0;

            while(true){
                gamesPlayed++;
                String playerHand = getHand();
                String cpuHand = RPSHandler.rollHand();

                setHandIcons(playerHand, cpuHand);

                String winner = RPSHandler.pickWinner(playerHand, cpuHand);

                if(winner == "PLAYER") playerWins++;
                else if(winner == "CPU") cpuWins++;

                Alert playMoreDialog = new Alert(AlertType.CONFIRMATION);
                playMoreDialog.setTitle("The computer is asking you...");
                playMoreDialog.setHeaderText(null);
                playMoreDialog.setContentText("Keep playing?");

                ButtonType yesButton = new ButtonType("Yes");
                ButtonType noButton = new ButtonType("No");

                playMoreDialog.getButtonTypes().setAll(yesButton, noButton);

                Optional<ButtonType> result = playMoreDialog.showAndWait();

                if(result.get() == yesButton){
                    // keep looping...
                }
                else if(result.get() == noButton){
                    break;
                }
                else{
                    break;
                }
            }

            timesTextField.setText(gamesPlayed + "");

            playerTimesLabel.setText("You won " + playerWins + " times.");
            cpuTimesLabel.setText("Computer won " + cpuWins + " times.");
            cpuTimesLabel.setVisible(true);
            playerTimesLabel.setVisible(true);

            if(playerWins > cpuWins){
                playerWon();
            }
            else if(cpuWins > playerWins){
                computerWon();
            }
            else{
                tieGame();
            }
        }
    }

    public void comboBoxListener(){
        cpuTimesLabel.setVisible(false);
        playerTimesLabel.setVisible(false);
        tieGame();
        timesTextField.setText("");

        if(gamemodeComboBox.getValue() == "Infinite Games"){
            timesLabel.setVisible(true);
            timesTextField.setVisible(true);
        }
        else{
            timesLabel.setVisible(false);
            timesTextField.setVisible(false);
        }
    }

    // method for showing prompt to get the players hand
    public String getHand(){
        List<String> options = new ArrayList<>();
        options.add("Rock");
        options.add("Paper");
        options.add("Scissors");

        ChoiceDialog<String> handPrompt = new ChoiceDialog<>("Rock", options);
        handPrompt.setTitle("Choose Your Hand");
        handPrompt.setHeaderText(null);
        handPrompt.setContentText("Choose your Hand:");

        handPrompt.showAndWait();

        return handPrompt.getSelectedItem();
    }

    // method sets the hand images
    public void setHandIcons(String playerHand, String cpuHand){
        if(playerHand == "Rock") playerHandIcon.setImage(rockHandImg);
        else if(playerHand == "Paper") playerHandIcon.setImage(paperHandImg);
        else if(playerHand == "Scissors") playerHandIcon.setImage(scissorHandImg);

        if(cpuHand == "Rock") cpuHandIcon.setImage(rockHandImg);
        else if(cpuHand == "Paper") cpuHandIcon.setImage(paperHandImg);
        else if(cpuHand == "Scissors") cpuHandIcon.setImage(scissorHandImg);
    }

    public void playerIconMouseHovered(){
        if(currentWinner == "PLAYER") applaudPlayer.play();
        else if(currentWinner == "CPU") lostSoundPlayer.play();
    }
    public void playerIconMouseExited(){
        lostSoundPlayer.stop();
        applaudPlayer.stop();
    }

    public void cpuIconMouseHovered(){
        if(currentWinner == "CPU") applaudPlayer.play();
        else if(currentWinner == "PLAYER") lostSoundPlayer.play();
    }
    public void cpuIconMouseExited(){
        lostSoundPlayer.stop();
        applaudPlayer.stop();
    }

    public void playerWon(){
        currentWinner = "PLAYER";
        playerIcon.setImage(happyFace);
        computerIcon.setImage(sadFace);
    }

    public void computerWon(){
        currentWinner = "CPU";
        playerIcon.setImage(sadFace);
        computerIcon.setImage(happyFace);
    }

    public void tieGame(){
        currentWinner = "TIE";
        playerIcon.setImage(neutralFace);
        computerIcon.setImage(neutralFace);
    }
}
