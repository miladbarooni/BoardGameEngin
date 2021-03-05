package sample;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WinningBox {



    public static void display(String content) {

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(content);

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(30);
        layout.setPadding(new Insets(0, 0, 0, 0));
        Label winning_message = new Label(content);
        winning_message.setFont(new Font("Courier", 20));
        winning_message.setStyle("-fx-font-weight: bold");
        winning_message.setStyle("-fx-text-fill: green");
        Button exit = new Button("Exit");
        exit.setOnAction(e -> {
            Platform.exit();
            System.exit(0);

        });
        layout.getChildren().addAll(winning_message, exit);

        window.setScene(new Scene(layout, 300, 150));

        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        window.showAndWait();






    }
}



