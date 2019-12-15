package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Settings extends MenuBar {

    private Translator translator;
    private Menu langMenu, tutorial, config;
    private MenuItem lang1, lang2, lang3, showTutorial, serverConfig;
    private static Settings settings = null;

    /**
     * @author Philipp
     */
    Settings() {

        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        this.langMenu = new Menu(translator.getString("settings.langMenu"));

        lang1 = new MenuItem(translator.getString("langMenu.german"));
        lang2 = new MenuItem(translator.getString("langMenu.english"));
        lang3 = new MenuItem(translator.getString("langMenu.chinese"));

        this.langMenu.getItems().addAll(lang1,lang2,lang3);

        tutorial = new Menu(translator.getString("tutorial"));
        showTutorial = new MenuItem(translator.getString("tutorial.show"));
        showTutorial.setOnAction(e -> Tutorial.getTutorial().show());
        tutorial.getItems().add(showTutorial);

        config = new Menu(translator.getString("settings.settings"));
        serverConfig = new MenuItem(translator.getString("settings.server"));
        serverConfig.setOnAction(e -> ServerSelector.getServerSelector().show());
        config.getItems().add(serverConfig);

        this.getMenus().addAll(this.langMenu, this.tutorial, this.config);
    }

    /**
     * Updates the menu with terms from the new translator
     * @author Christian
     */
    public void update(){
        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        langMenu.setText(translator.getString("settings.langMenu"));
        lang1.setText(translator.getString("langMenu.german"));
        lang2.setText(translator.getString("langMenu.english"));
        lang3.setText(translator.getString("langMenu.chinese"));
        tutorial.setText(translator.getString("tutorial"));
        showTutorial.setText(translator.getString("tutorial.show"));

    }

    public static Settings getTopSettings(){
        if (settings == null){
            settings = new Settings();
        }
        return settings;
    }

    //Getter
    public Menu getLangMenu() {
        return langMenu;
    }
}