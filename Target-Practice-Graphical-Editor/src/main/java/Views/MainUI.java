package Views;

import Controller.AppController;
import Interface.SMModelListener;
import Model.InteractionModel;
import Model.SMModel;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class MainUI extends StackPane {

    public AppController appController;
    public int selectedButton;

    public MainUI() throws FileNotFoundException {

        HBox mainBox = new HBox();
        ToolPalette toolPalette = new ToolPalette();
        DiagramView diagramView = new DiagramView(1600,1600,800,800);
        DiagramView minidiagramView = new DiagramView(600,600,300,300);

        appController = new AppController();

//Interaction Model
        InteractionModel interactionModel = new InteractionModel();

        minidiagramView.setInteractionModel(interactionModel);
        toolPalette.setInteractionModel(interactionModel);
        diagramView.setInteractionModel(interactionModel);
        appController.setInteractionModel(interactionModel);

        interactionModel.addSubscriber(toolPalette);
        interactionModel.addSubscriber(diagramView);
        interactionModel.addSMSubscriber(diagramView);
        interactionModel.addSubscriber(minidiagramView);
        interactionModel.addSMSubscriber(minidiagramView);


//SmModel
        SMModel smModel = new SMModel();
        smModel.addSubscriber(diagramView);
        smModel.addSubscriber(minidiagramView);
        appController.setSmModel(smModel);
        diagramView.setSmModel(smModel);
        minidiagramView.setSmModel(smModel);

        toolPalette.setController(appController);
        diagramView.setController(appController);
        minidiagramView.setController(appController);

        StackPane stack = new StackPane();
        minidiagramView.Box.getChildren().remove(minidiagramView.stateBox);
        stack.getChildren().addAll(minidiagramView,diagramView);

        selectedButton = interactionModel.getSelectedButtonIndex();
        mainBox.getChildren().addAll(toolPalette, stack);
        this.getChildren().addAll(mainBox);

    }
}
