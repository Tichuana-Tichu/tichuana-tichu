package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerSelector extends Stage {

    private TextField host, port;
    private CheckBox defaults;
    private Button confirm, save;
    private Label hostLbl, portLbl, defaultLbl;
    private static ServerSelector serverSelector;
    private static ServiceLocator serviceLocator;

    public ServerSelector(){

        serviceLocator = ServiceLocator.getServiceLocator();

        Configuration configuration = serviceLocator.getConfiguration();
        Translator translator = serviceLocator.getTranslator();

        hostLbl = new Label("Host");
        portLbl = new Label("Port");
        defaultLbl = new Label(translator.getString("serverselector.defaults"));

        host = new TextField();
        port = new TextField();
        host.setPromptText(configuration.getProperty("ipAddress"));
        port.setPromptText(configuration.getProperty("post"));

        defaults =new CheckBox();
        defaults.setOnAction(e -> changeDefaults());

        confirm = new Button(translator.getString("serverselector.confirm"));
        save = new Button(translator.getString("serverselector.save"));
        save.setDisable(true);

        HBox selector = new HBox();
        selector.getChildren().addAll(defaults,defaultLbl);


        VBox root = new VBox();
        root.getChildren().addAll(hostLbl,host,portLbl,port,selector,confirm,save);
        root.setSpacing(10);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                getClass().getResource(configuration.getProperty("tutorialStyle")).toExternalForm());
        root.getStyleClass().add("red");
        this.setScene(scene);
        this.setTitle(translator.getString("serverselector.title"));
        this.getIcons().add(new Image(configuration.getProperty("tichu-icon")));
    }

    /**
     * Factory Method to get Server Selector
     * @author Christian
     * @return serverSelector
     */
    public static ServerSelector getServerSelector(){
        if (serverSelector == null) {
            serverSelector = new ServerSelector();
        }
        return serverSelector;
    }

    public Button getSave() {
        return save;
    }
    public Button getConfirm() {
        return confirm;
    }
    public String getHost(){
        return host.getText();
    }
    public String getPort(){
        return port.getText();
    }

    /**
     * changes to localhost if defaults are selected
     * @author Christian
     */
    private void changeDefaults(){
        if (defaults.isSelected()) {
            host.setText("127.0.0.1");
            host.setDisable(true);
            port.setText("8080");
            port.setDisable(true);
        } else {
            host.setDisable(false);
            port.setDisable(false);
        }
    }

    /**
     * updates the strings when translator changes
     * @author Christian
     */
    public void update() {
        Configuration configuration = ServiceLocator.getServiceLocator().getConfiguration();
        Translator translator = ServiceLocator.getServiceLocator().getTranslator();
        defaultLbl.setText(translator.getString("serverselector.defaults"));
        confirm.setText(translator.getString("serverselector.confirm"));
        save.setText(translator.getString("serverselector.save"));
        host.setPromptText(configuration.getProperty("ipAddress"));
        port.setPromptText(configuration.getProperty("post"));
    }
}
