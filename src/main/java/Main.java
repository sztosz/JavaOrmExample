import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Status;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        launch(args);

        /// TESTING ORM
        try {
            Status status = new Status();
            List<Status> statuses = status.all();
            for (Status st : statuses) {
                System.out.print(st.id + " " + st.name + "\n");
            }
            Status find_result = status.find(4);
            System.out.print(find_result.id + " " + find_result.name + "\n");

        } catch (NoSuchFieldException | IllegalAccessException | URISyntaxException | SQLException e) {
            e.printStackTrace();
        }

        /// TESTING ORM

    }

}
