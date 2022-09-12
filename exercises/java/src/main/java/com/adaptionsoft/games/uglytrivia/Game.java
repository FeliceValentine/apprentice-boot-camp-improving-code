package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] cointCount = new int[6];
    boolean[] isInPenaltyBox = new boolean[6];
    
    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();


    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;
    
    public  Game(){
    	for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
    	}
    }

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}
	
	public boolean isPlayable() {
		return (numberOfPlayers() >= 2);
	}

	public boolean addNewPlayer(String playerName) {
		
		
	    players.add(playerName);
	    places[numberOfPlayers()] = 0;
	    cointCount[numberOfPlayers()] = 0;
	    isInPenaltyBox[numberOfPlayers()] = false;
	    
	    System.out.println(playerName + " was added");
	    System.out.println("They are player number " + players.size());
		return true;
	}
	
	public int numberOfPlayers() {
		return players.size();
	}

	public void turn(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);
		
		if (isInPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
			movePlayer(roll);
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
				}
			
		} else {
		
			movePlayer(roll);
			askQuestion();
		}
		
	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());		
	}
	
	private void movePlayer( int roll){
		places[currentPlayer] = places[currentPlayer] + roll;
		if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;
		System.out.println(players.get(currentPlayer)
				+ "'s new location is "
				+ places[currentPlayer]);
		System.out.println("The category is " + currentCategory());
	}
	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (isInPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				cointCount[currentPlayer]++;
				System.out.println(players.get(currentPlayer) 
						+ " now has "
						+ cointCount[currentPlayer]
						+ " Gold Coins.");
				
				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				
				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}
			
			
			
		} else {
		
			System.out.println("Answer was corrent!!!!");
			cointCount[currentPlayer]++;
			System.out.println(players.get(currentPlayer) 
					+ " now has "
					+ cointCount[currentPlayer]
					+ " Gold Coins.");
			
			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;
			
			return winner;
		}
	}
	
	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		isInPenaltyBox[currentPlayer] = true;
		
		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(cointCount[currentPlayer] == 6);
	}
}
