package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.controller.LobbyController;
import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.awt.event.MouseEvent;
import java.util.Locale;

public class Settings extends MenuBar {

    private Translator translator;
    private Menu langMenu, tutorial;
    private MenuItem lang1, lang2;

    /**
     * @author Philipp
     */
    Settings() {

        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        this.langMenu = new Menu(translator.getString("settings.langMenu"));

        lang1 = new MenuItem(translator.getString("langMenu.german"));
        lang2 = new MenuItem(translator.getString("langMenu.english"));

        this.langMenu.getItems().addAll(lang1,lang2);

        tutorial = new Menu(translator.getString("tutorial"));


        this.getMenus().addAll(this.langMenu, this.tutorial);
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
    }

    //Getter
    public Menu getLangMenu() {
        return langMenu;
    }
}