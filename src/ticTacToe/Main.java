/*
 * Simple tic tac toe game using JavaFX
 * Created by: Max Hao
 * Created on: September 21, 2017
 */

package ticTacToe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	static boolean turn = true; // determines who gets to place a tile
	static Button[][] tiles = new Button[3][3]; 
	static Text message = new Text("Welcome to Tic Tac Toe!");
	static Button reset = new Button("reset");

	public static void main(String[] args) {
		launch(args);
	}

	// Create board
	@Override
	public void start(Stage primaryStage) {
		System.out.println("Creating board");
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		
		message.setText("Player X's Turn");
		
		reset.setText("Reset");
		reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("Reset button clicked");
				start(primaryStage);
			}
			
		});

		primaryStage.setTitle("Tic Tac Toe");
		grid.add(message, 0, 3, 3, 1);
		grid.add(reset, 0, 4, 3, 1);

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tiles[i][j] = new Button("Button");
				tiles[i][j].setText("-");
				tiles[i][j].setOnAction(Clicked);
				grid.add(tiles[i][j], i, j);
			}
		}
		
		turn = true;

		primaryStage.show();
	}

	// Places tiles when clicked
	final EventHandler<ActionEvent> Clicked = new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			Button selected = (Button) event.getSource();
			if (selected.getText() != "-") {	// Return if tile is already occupied
				return;
			}

			if (turn) {
				selected.setText("X");
				System.out.println("X tile placed");
				message.setText("Player O's Turn");
			} else {
				selected.setText("O");
				System.out.println("O tile placed");
				message.setText("Player X's Turn");
			}

			turn = !turn;

			if (check()) {
				win();
			} else if (cats()) {
				draw();
			}
		}
	};

	// Check for any wins
	public boolean check() {
		String[][] log = new String[3][3];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				log[i][j] = tiles[i][j].getText();
			}
		}

		// Check for column win
		for (int i = 0; i < 3; i++) {
			if (log[i][0] == log[i][1] && log[i][1] == log[i][2] && log[i][0] != "-") {
				System.out.print("Column win by ");
				return true;
			}
		}

		// Check for row win
		for (int i = 0; i < 3; i++) {
			if (log[0][i] == log[1][i] && log[1][i] == log[2][i] && log[0][i] != "-") {
				System.out.print("Row win by ");
				return true;
			}
		}
		
		// Check for diagonal win
		if ((log[0][0] == log[1][1] && log[1][1] == log[2][2] && log[0][0] != "-") ||
				(log[0][2] == log[1][1] && log[1][1] == log[2][0] && log[2][0] != "-")) {
			System.out.print("Diagonal win by ");
			return true;
		}

		return false;
	}
	
	// Check for any draws
	public boolean cats() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tiles[i][j].getText() == "-") {
					return false;
				}
			}
		}
		
		return true;
	}

	// Win prompt
	public void win() {
		
		if (turn) {
			message.setText("Player O Wins!");
			System.out.println("Player O");
		} else {
			message.setText("Player X Wins!");
			System.out.println("Player X");
		}
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tiles[i][j].setOnAction(null);
			}
		}
	}
	
	// Draw prompt
	public void draw() {
		System.out.println("Draw");
		message.setText("Draw!");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				tiles[i][j].setOnAction(null);
			}
		}
	}
}