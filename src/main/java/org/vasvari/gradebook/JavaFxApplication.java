package org.vasvari.gradebook;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.vasvari.gradebook.controllers.LoginController;
import org.vasvari.gradebook.controllers.MainController;
import org.vasvari.gradebook.service.gateway.LoginGateway;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext appContext;
    private static FxWeaver fxWeaver;
    private static Parent root;
    private static Scene scene;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.appContext = new SpringApplicationBuilder()
                .sources(App.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        //FxWeaver fxWeaver = appContext.getBean(FxWeaver.class);
        fxWeaver = appContext.getBean(FxWeaver.class);
        root = fxWeaver.loadView(LoginController.class);
//        root = fxWeaver.loadView(MainController.class);
        //Scene scene = new Scene(root);
        scene = new Scene(root);
        //stage.setMaximized(true);
        stage.setScene(scene);
        stage.setTitle("E-napló");
        stage.show();
    }

    // switching views
    public static <T> void setRoot(Class<T> clazz) {
        root = fxWeaver.loadView(clazz);
        scene.setRoot(root);
    }

    @Override
    public void stop() {
        this.appContext.close();
        Platform.exit();
    }

    public static Scene getMainScene() {
        return scene;
    }
}
