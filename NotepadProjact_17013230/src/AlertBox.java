package sample;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class AlertBox {

    public static void display(String name, String number, String notes){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Entry Found");
        window.setMinWidth(250);
        window.setMinHeight(150);

        System.out.println("Entry found");

        Label namelab = new Label();
        Label numlab = new Label();
        Label noteslab = new Label();
        namelab.setText("Name: " + name);
        numlab.setText("Number: "+ number);
        noteslab.setText("Notes: "+ notes);


        Button closebutton = new Button("Ok");
        closebutton.setOnAction(e -> window.close());


        VBox layout = new VBox(10);
        layout.getChildren().addAll(namelab, numlab, noteslab, closebutton);

        layout.setAlignment(Pos.CENTER);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

    }
}
