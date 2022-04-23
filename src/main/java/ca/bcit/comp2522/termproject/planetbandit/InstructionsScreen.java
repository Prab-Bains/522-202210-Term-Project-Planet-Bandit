package ca.bcit.comp2522.termproject.planetbandit;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Represents the main menu of the game.
 *
 * @author Prab and Benny
 * @version 2022
 */
public class InstructionsScreen extends Application {
    private static final int APP_WIDTH = 1280;
    private static final int APP_HEIGHT = 720;

    private static final int OPTIONS_X_COORDINATE = 50;
    private static final int OPTIONS_Y_COORDINATE = 50;
    private static final int OPTIONS_SIZE = 1;
    private static final int SEPARATOR_LENGTH = 210;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 30;
    private static final double BUTTON_OPACITY = 0.4;
    private static final int BUTTON_FONT_SIZE = 20;
    private static Stage stage;



    private Parent createContent() {
        Pane root = new Pane();

        root.setPrefSize(APP_WIDTH, APP_HEIGHT);

        try (InputStream is = Files.newInputStream(Paths.get("resources/instructions.jpg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(APP_WIDTH);
            img.setFitHeight(APP_HEIGHT);
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        MenuBox vbox = new MenuBox(
                new MenuItem("BACK"));

        vbox.setTranslateX(OPTIONS_X_COORDINATE);
        vbox.setTranslateY(OPTIONS_Y_COORDINATE);
        vbox.setScaleY(OPTIONS_SIZE);

        root.getChildren().addAll(vbox);

        return root;

    }

    /**
     * Displays the main menu.
     * @param primaryStage a Stage object
     */
    public void start(final Stage primaryStage) {
        stage = primaryStage;
        Scene scene = new Scene(createContent());

        primaryStage.setTitle("Planet Bandit");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static final class MenuBox extends VBox {
        String id = "";
        int i = 1;

        private MenuBox(final MenuItem... items) {
            getChildren().add(createSeparator());
            for (MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
                item.setId(id + i);
                i++;
            }
        }

        private Line createSeparator() {
            Line sep = new Line();
            sep.setEndX(SEPARATOR_LENGTH);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

    }


    private static final class MenuItem extends StackPane {
        private MenuItem(final String name) {
            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
                    new Stop(0, Color.RED),
                    new Stop(1, Color.RED));

            Rectangle bg = new Rectangle(BUTTON_WIDTH, BUTTON_HEIGHT);
            bg.setOpacity(BUTTON_OPACITY);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, BUTTON_FONT_SIZE));
            bg.setFill(Color.RED);
            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> text.setFill(Color.WHITE));

            setOnMouseExited(event -> text.setFill(Color.DARKGREY));
            setOnMousePressed(event -> {
                text.setFill(Color.BLACK);
                bg.setFill(Color.YELLOW);

                String buttonID = this.getId();
                processButtonClick(buttonID);

            });

            setOnMouseReleased(event -> bg.setFill(gradient));

        }
    }

    /**
     * Process what should happen when each menu item is clicked.
     * @param id a string that represents the menu item id
     */
    public static void processButtonClick(final String id) {
        GamePlay gamePlay = new GamePlay();
        if (id.equals("1")) {
            MainMenu menu = new MainMenu();
            stage.close();
            menu.start(gamePlay.getStage());
        }
    }
}
