/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package Final;

import java.util.*;

public class RPSHandler {
    private static Random rand = new Random();
    private static String[] choices = { "Rock", "Paper", "Scissors" };

    // this method rolls a single hand
    public static String rollHand(){
        int num = rand.nextInt(3);

        return choices[num];
    }

    // this method can roll multiple hands, each being random
    public static List<String> rollMutlipleHands(int times){
        List<String> hands = new ArrayList<>();

        for(int i = 0; i < times; i++){
            int num = rand.nextInt(3);

            hands.add(choices[num]);
        }

        return hands;
    }

    public static String pickWinner(String playerHand, String cpuHand){
        String winner = "TIE";

        switch(playerHand){
            case "Rock":
                if(cpuHand.equals("Paper")){
                    winner = "CPU";
                }
                else if(cpuHand.equals("Scissors")){
                    winner = "PLAYER";
                }
                break;
            case "Paper":
                if(cpuHand.equals("Scissors")){
                    winner = "CPU";
                }
                else if(cpuHand.equals("Rock")){
                    winner = "PLAYER";
                }
                break;
            case "Scissors":
                if(cpuHand.equals("Rock")){
                    winner = "CPU";
                }
                else if(cpuHand.equals("Paper")){
                    winner = "PLAYER";
                }
                break;
        }

        return winner;
    }
}
