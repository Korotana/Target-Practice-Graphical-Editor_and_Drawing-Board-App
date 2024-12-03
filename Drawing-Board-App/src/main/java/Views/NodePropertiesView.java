package Views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class NodePropertiesView {
    Button stateButton = new Button("STATE");
    Label stateLabel = new Label("State Name::");
    TextField state = new TextField();

    public NodePropertiesView() {
        stateButton.setFont(new Font(20));
        stateButton.setDisable(false);

    }
}
