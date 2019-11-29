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
    private Menu langMenu;

    /**
     * @author Philipp
     */
    Settings() {

        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        this.langMenu = new Menu(translator.getString("settings.langMenu"));

        MenuItem m1 = new MenuItem(translator.getString("langMenu.german"));
        MenuItem m2 = new MenuItem(translator.getString("langMenu.english"));

        this.langMenu.getItems().addAll(m1,m2);

               // new MenuItem(translator.getString("langMenu.german")),
                 //                       new MenuItem(translator.getString("langMenu.english")));
        this.getMenus().add(this.langMenu);


        for(MenuItem m : this.langMenu.getItems()){
            m.setOnAction(event -> {
                LobbyController.changeTranslator(event);
            });
        }




    }

    //Getter
    public Menu getLangMenu() {
        return langMenu;
    }

}