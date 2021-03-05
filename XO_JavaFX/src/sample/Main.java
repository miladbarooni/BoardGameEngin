package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.application.Platform;

import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {




    public int current_player;
    public String temp;
    public Label time_01;
    public Label time_02;

    public int time_01_number = 15;
    public int time_02_number = 15;

    public boolean has_started = false;

    public Main() throws IOException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {

                        BufferedReader br = null;
                        try {
                            br = new BufferedReader(new FileReader("..\\BoardGameEngine\\XO_communication_cpp.txt"));
                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            if (br.readLine() == null) return;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File file = new File("..\\BoardGameEngine\\XO_communication_cpp.txt");
                        try {
                            Scanner sc = new Scanner(file);
                            temp = sc.nextLine();

                            if (temp.equals("0")) timer.cancel();
                            if (temp.equals("1")) timer.cancel();

                            if (temp.equals("0")) WinningBox.display("Player 1 has won");
                            if (temp.equals("1")) WinningBox.display("Player 2 has won");


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }




                    }



                });

            }
            }, 0, 100);


        Timer timer2 = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {

                        if (!has_started) return;

                        if (current_player == 1) {
                            if (time_01_number > 0) time_01_number -= 1;
                            time_01.setText("P1: " + Integer.toString(time_01_number));

                        }

                        if (current_player == 2) {
                            if (time_02_number > 0) time_02_number -= 1;
                            time_02.setText("P2: " + Integer.toString(time_02_number));


                        }




                    }



                });

            }
        }, 0, 1000);



        Stage window = primaryStage;
        window.setTitle("XO Game");

        current_player = 1;



        GridPane grid = new GridPane();
        grid.setPadding(new Insets(60, 60, 60, 60));


        GridPane label_layout = new GridPane();
        label_layout.setPadding(new Insets(40, 0, 0, 0));
        label_layout.setAlignment(Pos.CENTER);
        label_layout.setHgap(70);
        //label_layout.setPadding(new Insets(50, 0, 0 ,0));


        Label turn_label = new Label("Turn: Player 1");
        turn_label.setStyle("-fx-font-weight: bold");
        turn_label.setStyle("-fx-text-fill: purple");


        Font fnt = new Font("Courier New", 30);
        turn_label.setFont(new Font("Courier New", 30));
        GridPane.setConstraints(turn_label, 1, 0);
        label_layout.getChildren().addAll(turn_label);







        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                Cell cell = new Cell(0, 0, 160, 160);
                cell.setFill(Color.TRANSPARENT);
                cell.setStroke(Color.PURPLE);
                cell.setOwned(false);
                cell.setRow(i);
                cell.setColumn(j);
                GridPane.setConstraints(cell, i, j);
                grid.getChildren().add(cell);
                cell.setOnMouseClicked(e -> {
                    if (!cell.isOwned()) {
//                        cell.setOwned(true);
                        System.gc();
                        if (!has_started) has_started = true;


                        if (current_player == 1) {
                            Label mark = new Label("X");
                            mark.setStyle("-fx-text-fill: blue");
                            mark.setFont(new Font("Courier", 80));
                            mark.setPadding(new Insets(0, 0, 0, 55));
                            GridPane.setConstraints(mark, cell.getRow(), cell.getColumn());
                            grid.getChildren().add(mark);
                            temp = Integer.toString(cell.getRow()) + " " + Integer.toString((cell.getColumn()));


                            // write to file
                            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream("..\\BoardGameEngine\\XO_communication_java.txt"), "utf-8"))) {
                                writer.write(temp);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            // write to file

                            // read from while
                            BufferedReader br = null;
                            try {
                                br = new BufferedReader(new FileReader("..\\BoardGameEngine\\XO_communication_cpp.txt"));
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
                                    br = new BufferedReader(new FileReader("..\\BoardGameEngine\\XO_communication_cpp.txt"));
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            try {
                                br.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            File file = new File("..\\BoardGameEngine\\XO_communication_cpp.txt");
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
                            // read from while

                            if (temp.equals("0")) WinningBox.display("Player 1 has won");
                            if (temp.equals("draw")) WinningBox.display("Draw!");


                            current_player = 2;
                            update_turn_label(turn_label);
                        }
                        else if (current_player == 2) {
                            Label mark = new Label("O");
                            mark.setStyle("-fx-text-fill: red");
                            mark.setFont(new Font("Courier", 80));
                            mark.setPadding(new Insets(0, 0, 0, 55));
                            GridPane.setConstraints(mark, cell.getRow(), cell.getColumn());
                            grid.getChildren().add(mark);
                            temp = Integer.toString(cell.getRow()) + " " + Integer.toString((cell.getColumn()));

                            // write to file
                            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                    new FileOutputStream("..\\BoardGameEngine\\XO_communication_java.txt"), "utf-8"))) {
                                writer.write(temp);
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
                                br = new BufferedReader(new FileReader("..\\BoardGameEngine\\XO_communication_cpp.txt"));
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
                                    br = new BufferedReader(new FileReader("..\\BoardGameEngine\\XO_communication_cpp.txt"));
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                }

                            }
                            try {
                                br.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            File file = new File("..\\BoardGameEngine\\XO_communication_cpp.txt");
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

                            if (temp.equals("1")) WinningBox.display("Player 2 has won");
                            if (temp.equals("draw")) WinningBox.display("Draw!");

                            current_player = 1;
                            update_turn_label(turn_label);

                        }




                    }
                });


            }
        }








        BorderPane layout = new BorderPane();
        layout.setCenter(grid);
        layout.setTop(label_layout);


        time_01 = new Label("P1: 15");
        time_02 = new Label("P2: 15");

        time_01.setStyle("-fx-font-weight: bold");
        time_01.setStyle("-fx-text-fill: blue");

        time_02.setStyle("-fx-font-weight: bold");
        time_02.setStyle("-fx-text-fill: red");

        time_01.setScaleX(2);
        time_01.setScaleY(2);

        time_02.setScaleX(2);
        time_02.setScaleY(2);


        GridPane.setConstraints(time_01, 0, 0);
        GridPane.setConstraints(time_02, 2, 0);
        label_layout.getChildren().addAll(time_01, time_02);



        window.setScene(new Scene(layout, 600, 675));
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



    Cell (int x, int y, int width, int height) {
        super(x, y, width, height);
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

