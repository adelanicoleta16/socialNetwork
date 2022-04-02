package com.example.admir;
import controller.FirstWindowController;
import domain.validators.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.database.EventsDBRepository;
import repository.database.FriendshipDBRepository;
import repository.database.MessageDBRepository;
import repository.database.UserDBRepository;
import service.*;
import java.io.IOException;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getResource("first-window-view.fxml"));
        Parent root = loader.load();
        FirstWindowController controller = loader.getController();

        String database = null, password = null;
        String user = "Miruna";
        switch (user) {
            case "Miruna":
                database = "jdbc:postgresql://localhost:5432/socialnetwork";
                password = "postgres";
                break;
            case "Ada":
                database = "jdbc:postgresql://localhost:1601/academic";
                password = "adelamiau";
                break;
            default:
                System.out.println("Nu stiu cine esti");
                return;
        }
        UserDBRepository userDBRepository = new UserDBRepository(database, "postgres",
                password, new UserValidator(), "users");
        FriendshipDBRepository friendshipDBRepository = new FriendshipDBRepository(database, "postgres",
                password, new FriendshipValidator(), "friendships");
        MessageDBRepository messageDBRepository = new MessageDBRepository(database, "postgres",
                password, new MessageValidator(), "messages");
        EventsDBRepository eventsDBRepository = new EventsDBRepository(database, "postgres",
                password, new EventValidator(), "events");

        SocialNetworkService service = new SocialNetworkService(new UserService(userDBRepository), new FriendshipService(friendshipDBRepository),
                new MessageService(messageDBRepository), new EventsService(eventsDBRepository));

        controller.setService(service);
        Scene scene = new Scene(root);
        stage.setTitle("ADMIR!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}

