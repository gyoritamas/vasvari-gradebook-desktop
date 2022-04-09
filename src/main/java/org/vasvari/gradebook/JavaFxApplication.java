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

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext appContext;
    private static FxWeaver fxWeaver;
    private static Parent root;
    private static Scene scene;
    private static Stage theStage;

    @Override
    public void init() {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.appContext = new SpringApplicationBuilder()
                .sources(App.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        theStage = stage;
        fxWeaver = appContext.getBean(FxWeaver.class);
        root = fxWeaver.loadView(LoginController.class);
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // switching views
    public static <T> void setRoot(Class<T> clazz) {
        theStage.hide();
        root = fxWeaver.loadView(clazz);
        scene.setRoot(root);
        theStage.show();
    }

    @Override
    public void stop() {
        this.appContext.close();
        Platform.exit();
    }

    public static Scene getMainScene() {
        return scene;
    }

    public static Stage getTheStage(){
        return theStage;
    }
}
