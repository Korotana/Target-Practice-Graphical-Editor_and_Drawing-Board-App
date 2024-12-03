package Views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LinkPropertiesView {
    Button transition = new Button("Transition");
    Label eventLabel = new Label("Event:");
    Label contextLabel = new Label("Context:");
    Label effectsLabel = new Label("SideEffects:");
    TextField stateEvent = new TextField();
    TextField stateSideEffects = new TextField();
    TextField stateContext = new TextField();
    public LinkPropertiesView() {
        transition.setDisable(false);
    }

}
