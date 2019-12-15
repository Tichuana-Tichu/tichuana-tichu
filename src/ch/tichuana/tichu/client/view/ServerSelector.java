package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.Configuration;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerSelector extends Stage {

    private TextField host, port;
    private CheckBox defaults;
    private Button confirm;
    private static ServerSelector serverSelector;
    private static ServiceLocator serviceLocator;

    public ServerSelector(){

        serviceLocator = ServiceLocator.getServiceLocator();

        //temporary
        serviceLocator.setConfiguration(new Configuration("src/ch/tichuana/tichu/client/resources/config.properties"));
        serviceLocator.setTranslator(new Translator("de"));

        Configuration configuration = serviceLocator.getConfiguration();
        Translator translator = serviceLocator.getTranslator();

        Label hostLbl = new Label("Host");
        Label portLbl = new Label("Port");
        Label defaultLbl = new Label(translator.getString("serverselector.defaults"));

        host = new TextField();
        port = new TextField();
        host.setPromptText("127.0.0.1");
        port.setPromptText("8080");

        defaults =new CheckBox();
        defaults.setOnAction(e -> changeDefaults());

        confirm = new Button(translator.getString("serverselector.confirm"));

        HBox selector = new HBox();
        selector.getChildren().addAll(defaults,defaultLbl);


        VBox root = new VBox();
        root.getChildren().addAll(hostLbl,host,portLbl,port,selector,confirm);
        root.setSpacing(10);

        Scene scene = new Scene(root);
        this.setScene(scene);
        this.setTitle(translator.getString("serverselector.title"));
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
    public void changeDefaults(){
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
}
