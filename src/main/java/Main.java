import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Status;

import java.util.HashMap;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
//        launch(args);

        /// TESTING ORM

        List<Status> statuses = (List<Status>) (Object) new Status().all();
        for (Status st : statuses) {
            System.out.print(st.id + " " + st.name + "\n");
        }
        Status find_result = (Status) new Status().find(4);
        System.out.print(find_result.id + " " + find_result.name + "\n");

        List<Status> where_result = (List<Status>) (Object) new Status().where(new HashMap<String, String>() {{
            put("name", "service");
        }});
        for (Status st : where_result) {
            System.out.print(st.id + " " + st.name + "\n");
        }

        List<Status> where_result_1 = (List<Status>) (Object) new Status().where(new HashMap<String, String>() {{
            put("id", "4");
        }});
        for (Status st : where_result_1) {
            System.out.print(st.id + " " + st.name + "\n");
        }

        /// TESTING ORM

    }

}
