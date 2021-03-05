package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Collection;
import java.util.Scanner;

public class Main extends Application {


    public  int current_player = 1;
    public int number_of_players = 2;
    Label cell_label;
    String temp;
    String[] parts = new String[2];
    int[] numbers = new int[2];
    int row_number, columnt_number;
    StackPane action_layout;
    Label action_number;
    Label dice_text;



    @Override
    public void start(Stage primaryStage) throws Exception{


        Stage window = primaryStage;
        window.setTitle("Snakes and Ladders Game");
        Cell[] cells = new Cell[101];

        BorderPane main_layout = new BorderPane();
        StackPane label_layout = new StackPane();
        GridPane cells_layout = new GridPane();
        GridPane dice_layout = new GridPane();


        main_layout.setCenter(cells_layout);
        main_layout.setBottom(dice_layout);
        main_layout.setTop(label_layout);

        cells_layout.setPadding(new Insets(40, 40, 40, 40));
        cells_layout.setAlignment(Pos.CENTER);

        label_layout.setPadding(new Insets(50, 0, 0 ,0));
        Label turn_label = new Label("Turn: Player 1");
        turn_label.setStyle("-fx-font-weight: bold");
        turn_label.setStyle("-fx-text-fill: purple");
        Font fnt = new Font("Courier New", 30);
        turn_label.setFont(new Font("Courier New", 30));
        label_layout.getChildren().addAll(turn_label);

        int cell_counter = 0;

        for (int i = 0; i<10; i++)
        {
            for (int j = 0; j<10; j++)
            {
                Cell cell = new Cell(0, 0, 65, 65);
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.PURPLE);
                cell.setOwned(false);

                if (i % 2 == 0) GridPane.setConstraints(cell, j, 9-i);
                if (i % 2 == 1) GridPane.setConstraints(cell, 9-j, 9-i);

                cell_label = new Label(Integer.toString(cell_counter+1));
                cell_label.setPadding(new Insets(45, 0, 0, 43));

                if (i % 2 == 0) {
                    GridPane.setConstraints(cell_label, j, 9-i);
                    cell.setRow(9-i);
                    cell.setColumn(j);
                }
                if (i % 2 == 1) {
                    GridPane.setConstraints(cell_label, 9-j, 9-i);
                    cell.setRow(9-i);
                    cell.setColumn(9-j);
                }
                cells[cell_counter] = cell;
                cell_counter += 1;

                cells_layout.getChildren().add(cell_label);

                cells_layout.getChildren().add(cell);
            }
        }

        Circle player_01 = new Circle(10);
        Circle player_02 = new Circle(10);
        Circle player_03 = new Circle(10);
        Circle player_04 = new Circle(10);

        player_01.setFill(Color.RED);
        player_02.setFill(Color.BLUE);
        player_03.setFill(Color.GREEN);
        player_04.setFill(Color.ORANGE);



        StackPane player_01_layout = new StackPane();
        StackPane player_02_layout = new StackPane();
        StackPane player_03_layout = new StackPane();
        StackPane player_04_layout = new StackPane();

        player_01_layout.getChildren().add(player_01);
        player_02_layout.getChildren().add(player_02);
        player_03_layout.getChildren().add(player_03);
        player_04_layout.getChildren().add(player_04);

        player_01_layout.setPadding(new Insets(0, 0, 25, 25));
        player_02_layout.setPadding(new Insets(25, 0, 0, 25));
        player_03_layout.setPadding(new Insets(0, 25, 25, 0));
        player_04_layout.setPadding(new Insets(25, 25, 0, 0));



        GridPane.setConstraints(player_01_layout, cells[0].getColumn(), cells[0].getRow());
        GridPane.setConstraints(player_02_layout, cells[0].getColumn(), cells[0].getRow());
        GridPane.setConstraints(player_03_layout, cells[0].getColumn(), cells[0].getRow());
        GridPane.setConstraints(player_04_layout, cells[0].getColumn(), cells[0].getRow());



        cells_layout.getChildren().addAll(player_01_layout, player_02_layout, player_03_layout, player_04_layout);




        Button dice_button = new Button("roll the dice");
        dice_button.setScaleX(1.5);
        dice_button.setScaleY(1.5);

        dice_button.setOnAction(e -> {

            // write to file
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("..\\BoardGameEngine\\SaL_communication_java.txt"), "utf-8"))) {
                writer.write("1");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // write to file

            // read from file
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader("..\\BoardGameEngine\\SaL_communication_cpp.txt"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            while (true) {
                try {
                    if (!(br.readLine() == null)) break;
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    br = new BufferedReader(new FileReader("..\\BoardGameEngine\\SaL_communication_cpp.txt"));
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
            try {
                br.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            File file = new File("..\\BoardGameEngine\\SaL_communication_cpp.txt");
            try {
                Scanner sc = new Scanner(file);
                temp = sc.nextLine();

            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            writer.print("");
            writer.close();
            // read from file

            parts = temp.split(" ");
            dice_text.setText(parts[0]);
            columnt_number = Integer.parseInt(parts[1]);
            if (current_player == 1) {
                GridPane.setConstraints(player_01_layout, cells[columnt_number].getColumn(), cells[columnt_number].getRow());
                if (parts[2].equals("1")) WinningBox.display("player 1 has won!");
            }
            if (current_player == 2) {
                GridPane.setConstraints(player_02_layout, cells[columnt_number].getColumn(), cells[columnt_number].getRow());
                if (parts[2].equals("1")) WinningBox.display("player 2 has won!");
            }

            if (current_player == 3) {
                GridPane.setConstraints(player_03_layout, cells[columnt_number].getColumn(), cells[columnt_number].getRow());
                if (parts[2].equals("1")) WinningBox.display("player 3 has won!");
            }

            if (current_player == 4) {
                GridPane.setConstraints(player_04_layout, cells[columnt_number].getColumn(), cells[columnt_number].getRow());
                if (parts[2].equals("1")) WinningBox.display("player 4 has won!");
            }

            if (current_player == 4) current_player = 1;
            else current_player += 1;
            update_turn_label(turn_label);

        });

        StackPane dice = new StackPane();
        Rectangle dice_square = new Rectangle(0, 0, 35 ,35);
        dice_text = new Label("0");
        dice_text.setStyle("-fx-font-weight: bold");
        dice_text.setStyle("-fx-text-fill: purple");
        dice_text.setFont(new Font("Courier New", 30));

        dice_square.setFill(Color.TRANSPARENT);
        dice_square.setStroke(Color.PURPLE);
        dice.getChildren().addAll(dice_square, dice_text);

        GridPane.setConstraints(dice, 1, 0);
        GridPane.setConstraints(dice_button, 0, 0);

        dice_layout.getChildren().addAll(dice_button, dice);

        dice_layout.setHgap(40);

        dice_layout.setPadding(new Insets(0, 40, 40, 40));

        dice_layout.setAlignment(Pos.CENTER);


        // read from file
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("..\\BoardGameEngine\\SaL_communication_cpp.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        while (true) {
            try {
                if (!(br.readLine() == null)) break;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                br = new BufferedReader(new FileReader("..\\BoardGameEngine\\SaL_communication_cpp.txt"));
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

        }
        try {
            br.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        File file = new File("..\\BoardGameEngine\\SaL_communication_cpp.txt");
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                temp = sc.nextLine();
                parts = temp.split(" ");

                numbers[0] = Integer.parseInt(parts[0]);
                numbers[1] = Integer.parseInt(parts[1]);

                if (numbers[0] < 0) cells[numbers[1]].setFill(Color.RED);
                if (numbers[0] > 0) cells[numbers[1]].setFill(Color.GREEN);

                row_number = cells[numbers[1]].getRow();
                columnt_number = cells[numbers[1]].getColumn();

                action_layout = new StackPane();
                action_number = new Label(parts[0]);
                action_number.setScaleX(1.7);
                action_number.setScaleY(1.7);
                action_number.setStyle("-fx-font-weight: bold");
                action_number.setStyle("-fx-text-fill: white");

                action_layout.getChildren().add(action_number);
                GridPane.setConstraints(action_layout, columnt_number, row_number);
                cells_layout.getChildren().add(action_layout);


            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        writer.print("");
        writer.close();
        // read from file









        window.setScene(new Scene(main_layout, 800, 900));
        window.show();



    }

    public void update_turn_label(Label turn_label) {
        turn_label.setText("Turn: Player " + Integer.toString(current_player));
    }


    public static void main(String[] args) {
        launch(args);
    }
}




class Cell extends Rectangle {



    private boolean owned;
    private int row;
    private int column;
    private int value;
    private boolean action;




    Cell (int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public boolean isAction() {
        return action;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isOwned() {
        return owned;
    }

    public void setOwned(boolean owned) {
        this.owned = owned;
    }
}
