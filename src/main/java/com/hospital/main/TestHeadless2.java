package com.hospital.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import com.hospital.controllers.PatientController;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

public class TestHeadless2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        com.hospital.utils.Session.setLoggedInUser(new com.hospital.models.Patient(4, "John Doe", "johndoe", "pass", "O+", "1", 30));
        
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/views/PatientBooking.fxml"));
        javafx.scene.Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        
        // Force layout
        root.applyCss();
        root.layout();
        
        Platform.runLater(() -> {
            try {
                PatientController pc = loader.getController();
                // Find table 
                VBox contentArea = (VBox) scene.lookup("#contentArea");
                if (contentArea != null) {
                    System.out.println("TEST STARTING: Inspecting TableView data bindings...");
                    TableView<?> table = (TableView<?>) scene.lookup(".table-view");
                    if (table != null) {
                        System.out.println("Table Rows: " + table.getItems().size());
                        if(table.getItems().size() > 0) {
                            for(int i = 0; i < table.getColumns().size(); i++) {
                                TableColumn col = table.getColumns().get(i);
                                Object cellData = col.getCellData(0);
                                System.out.println("Column " + i + " (" + col.getText() + ") Value for Row 0: " + cellData);
                            }
                        }
                    } else {
                        System.out.println("TableView not found");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.exit(0);
        });
    }
}
