package Views;

import Controller.AppController;
import Interface.IModelListener;
import Model.InteractionModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ToolPalette extends StackPane implements IModelListener {

    InteractionModel iModel;
    private ArrayList<Button> buttonList = new ArrayList<>();
    private VBox buttonToolbar = new VBox();


    public ToolPalette() throws FileNotFoundException {

        CreatePointerButton();
        CreateMoveButton();
        CreateLinkButton();
        buttonToolbar.setBackground(new Background(new BackgroundFill(Color.HOTPINK, CornerRadii.EMPTY, Insets.EMPTY)));
        buttonToolbar.setMaxWidth(100);
        buttonToolbar.setSpacing(5);
        buttonToolbar.setAlignment(Pos.BASELINE_LEFT);
        this.getChildren().addAll(buttonToolbar);

    }

    public ArrayList<Button> getButtonList() {
        return buttonList;
    }

    public void setButtonList(ArrayList<Button> buttonList) {
        this.buttonList = buttonList;
    }

    public void CreatePointerButton() throws FileNotFoundException {
        Button arrow = new Button();
        arrow.setMaxWidth(90);
        arrow.setMaxHeight(90);
        arrow.setBackground(new Background(new BackgroundFill(Color.CYAN,new CornerRadii(10),null)));
        FileInputStream input = new FileInputStream("src/main/java/Views/cursor.png");
        ImageView imageView = new ImageView(new Image(input));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        arrow.setGraphic(imageView);
        this.buttonList.add(arrow);
        this.buttonToolbar.getChildren().addAll(arrow);
    }

    public void CreateMoveButton() throws FileNotFoundException {
        Button move = new Button();
        move.setMaxWidth(90);
        move.setMaxHeight(90);
        move.setBackground(new Background(new BackgroundFill(Color.CYAN,new CornerRadii(10),new Insets(5,5,5,5))));
        FileInputStream input = new FileInputStream("src/main/java/Views/move.png");
        ImageView imageView = new ImageView(new Image(input));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        move.setGraphic(imageView);
        this.buttonList.add(move);
        this.buttonToolbar.getChildren().addAll(move);
    }

    public void CreateLinkButton() throws FileNotFoundException {
        Button Link = new Button();
        Link.setMaxWidth(90);
        Link.setMaxHeight(90);
        Link.setBackground(new Background(new BackgroundFill(Color.CYAN,new CornerRadii(10),new Insets(5,5,5,5))));
        FileInputStream input = new FileInputStream("src/main/java/Views/crosshair.png");
        ImageView imageView = new ImageView(new Image(input));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        Link.setGraphic(imageView);
        this.buttonList.add(Link);
        this.buttonToolbar.getChildren().addAll(Link);
    }

    public void setInteractionModel(InteractionModel im) {
        iModel = im;
    }

    public void setController(AppController controller) {
        buttonList.forEach(b -> {
            b.setOnAction(e -> {
                controller.handleButtonClick(buttonList.indexOf(b));
            });
        });
    }


    @Override
    public void iModelChanged() {
        buttonList.forEach(b -> {
            b.setBackground(new Background(new BackgroundFill(Color.CYAN,new CornerRadii(10),new Insets(5,5,5,5))));
            if (buttonList.get(iModel.getSelectedButtonIndex()).equals(b)){
                b.setBackground(new Background(new BackgroundFill(Color.CYAN,new CornerRadii(10),null)));
            }
        });
        if (iModel.getSelectedButtonIndex() == 1) this.setCursor(Cursor.MOVE);
        else if (iModel.getSelectedButtonIndex() == 2) this.setCursor(Cursor.CROSSHAIR);
        else this.setCursor(Cursor.DEFAULT);

    }
}
