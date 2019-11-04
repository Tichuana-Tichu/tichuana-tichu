package ch.tichuana.tichu.client.view;

import ch.tichuana.tichu.client.services.ServiceLocator;
import ch.tichuana.tichu.client.services.Translator;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class Settings extends MenuBar {

    private Translator translator;
    private Menu langMenu;

    /**
     * @author Philipp
     */
    Settings() {

        this.translator = ServiceLocator.getServiceLocator().getTranslator();
        this.langMenu = new Menu(translator.getString("settings.langMenu"));

        this.langMenu.getItems().addAll(new MenuItem(translator.getString("langMenu.german")),
                                        new MenuItem(translator.getString("langMenu.english")));
        this.getMenus().add(this.langMenu);
    }

    //Getter
    public Menu getLangMenu() {
        return langMenu;
    }
}