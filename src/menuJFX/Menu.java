/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menuJFX;

import com.games.flappy.Main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Ravo
 */

public class Menu extends Application {
	@Override
	public void start(Stage primaryStage) {

		BorderPane root = new BorderPane();

		Button btn = new Button();
		Button btn1 = new Button();

		btn.setText("Jouer");
		btn1.setText("Quitter");

		btn.setOnAction(event -> Main.jeu());
		btn1.setOnAction(event -> primaryStage.close());

		VBox root1 = new VBox();
		root1.setSpacing(150);
		root1.setAlignment(Pos.CENTER);

		root.setCenter(root1);

		root1.getChildren().addAll(takeTitle(), btn, btn1, takeAutor());

		Scene scene = new Scene(root, 1270, 720);

		scene.getStylesheets().add(Menu.class.getResource("backgr.css").toExternalForm());

		primaryStage.setTitle("Menu du jeu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	static Node takeTitle() {
		Text t = new Text();
		t.setFill(Color.WHITE);
		t.setText("My Flappy Bird");
		t.setFont(Font.font("null", FontWeight.BOLD, 100));
		DropShadow ds = new DropShadow();
		ds.setOffsetX(5.0);
		ds.setOffsetY(5.0);
		ds.setColor(Color.GRAY);
		t.setEffect(ds);
		return t;
	}

	static Node takeAutor() {
		Text t = new Text();
		t.setFill(Color.BLACK);
		t.setText("Realized by : 				RANDRIANTSEHENO Tambiniaina Ravo Odilon	&	KONG KONG Marc Christian");
		t.setFont(Font.font("null", FontWeight.SEMI_BOLD, 20));
		return t;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
