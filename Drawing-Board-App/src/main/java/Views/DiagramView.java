package Views;

import Controller.AppController;
import Interface.IModelListener;
import Interface.SMModelListener;
import Model.InteractionModel;
import Model.SMModel;
import Model.SMStateNode;
import Model.SMTransitionLink;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DiagramView extends StackPane implements SMModelListener, IModelListener {
    GraphicsContext gc;
    Canvas myCanvas;
    double width, height;
    double worldwidth, worldheight;
    SMModel model;
    private InteractionModel iModel;
    double nodeLeft, nodeTop, nodeWidth, nodeHeight;

    double opacity = 0.5;
    HBox Box = new HBox();
    VBox stateBox = new VBox();
    NodePropertiesView nodePropertiesView = new NodePropertiesView();
    LinkPropertiesView linkPropertiesView = new LinkPropertiesView();

    Button update = new Button("Update");

    public DiagramView(double newWorldwidth, double newWorldheight,double w, double h) {
        width = w;
        height = h;
        worldwidth = newWorldwidth;
        worldheight = newWorldheight;
        myCanvas = new Canvas(width, height);
        gc = myCanvas.getGraphicsContext2D();
        gc.setFill(Color.rgb(171,255,247));
        gc.fillRect(0,0,width,height);
        gc.setFont(new Font(0.02 *width));
        linkPropertiesView.stateSideEffects.setMinHeight(0.2*height);
        linkPropertiesView.stateContext.setMinHeight(0.2*height);
        nodePropertiesView.stateButton.setMinWidth(stateBox.getWidth());
        stateBox.getChildren().addAll(nodePropertiesView.stateButton, nodePropertiesView.stateLabel, nodePropertiesView.state);
        stateBox.setBackground(new Background(new BackgroundFill(Color.DARKGRAY,null,null)));
        stateBox.setSpacing(10);
        stateBox.setPadding(new Insets(20,0,0,0));
        Box.getChildren().addAll(myCanvas,stateBox);
        this.getChildren().addAll(Box);
    }

    public void setController(AppController controller) {
        // set up event handling - normalize coordinates
        myCanvas.setOnMousePressed(e -> {
            controller.handlePressed(e.getX()/width,e.getY()/height,e);}
        );
        myCanvas.setOnMouseDragged(e -> {
            controller.handleDragged(e.getX()/width,e.getY()/height);}
        );
        myCanvas.setOnMouseReleased(e -> {
            controller.handleReleased(e.getX()/width,e.getY()/height,e);
        });
        nodePropertiesView.state.setOnKeyPressed(e-> {
            if (e.getCode().equals(KeyCode.ENTER))controller.handleStateUpdate(nodePropertiesView.state.getText());});
        update.setOnMousePressed(e -> controller.handleUpdatePressed(linkPropertiesView.stateEvent.getText(), linkPropertiesView.stateContext.getText(), linkPropertiesView.stateSideEffects.getText()));
    }

    public void setInteractionModel(InteractionModel newModel) {
        iModel = newModel;
        iModel.setViewExtents(myCanvas.getWidth()/worldwidth, myCanvas.getHeight()/worldheight);
        iModel.setWorldExtents(worldwidth,worldheight);
    }

    public void draw() {

        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, width, height);
        gc.setFill(Color.rgb(171,255,247,opacity));
        gc.fillRect(0,0,width,height);
        gc.setLineWidth(3.0);
        gc.stroke();

        if (model.circularLink.size() != 0) {
            model.circularLink.forEach((node, circlelink) -> {
                if (circlelink == iModel.getSelectionLink()) {
                    gc.setFill(Color.CYAN);
                    gc.setStroke(Color.BLACK);
                }
                else {
                gc.setFill(Color.ALICEBLUE);
                gc.setStroke(Color.YELLOW);}
                gc.strokeOval((node.left + node.width)*width,node.top*height,(0.08*width),0.08*width);
                gc.strokeLine((node.left + node.width)*width,node.top*height + (0.08*width)/2,(node.left + node.width)*width + (0.08*width) ,node.top*height + (0.08*width)/2);
                gc.strokeRect(circlelink.startX*width, circlelink.startY*height, circlelink.endX*width, circlelink.endY*height);
                gc.fillRect(circlelink.startX*width, circlelink.startY*height, circlelink.endX*width, circlelink.endY*height);
                fillCircleEventDetails(circlelink);
            });
        }
        for (SMTransitionLink link: model.links) {

            gc.setStroke(Color.MAGENTA); //TO DENOTE LINK START || LINK FROM
            gc.strokeLine(link.startX * width,link.startY*height,link.left*width,link.top*height);


            gc.setStroke(Color.FIREBRICK); //TO DENOTE LINK END || LINK TO
            gc.strokeLine(link.left*width,link.top*height,link.endX*width,link.endY*height);

            if (link == iModel.getSelectionLink()) {
                gc.setFill(Color.CYAN);
                gc.setStroke(Color.BLACK);
            } else {
                gc.setFill(Color.ALICEBLUE);
                gc.setStroke(Color.YELLOW);
            }

                gc.strokeRect(link.left * width - (0.1 * width), link.top * height - (0.1 * height), link.width * width, link.height * height);
                gc.fillRect(link.left * width - (0.1 * width), link.top * height - (0.1 * height), link.width * width, link.height * height);
            fillEventDetails(link);

        }

        for (SMStateNode node : model.nodes) {
            if (node == iModel.getSelection()) {
                gc.setFill(Color.TEAL);
                gc.setStroke(Color.RED);
            } else {
                gc.setStroke(Color.BLACK);
                gc.setFill(Color.ORANGE);
            }
            nodeLeft = node.left * width;
            nodeTop = node.top * height;
            nodeWidth = node.width * width;
            nodeHeight = node.height * height;
            gc.fillRect(nodeLeft, nodeTop, nodeWidth, nodeHeight);
            gc.strokeRect(nodeLeft, nodeTop, nodeWidth, nodeHeight);
            gc.setFill(Color.BLACK);
            gc.setStroke(Color.BLACK);
            gc.fillText(node.state,(node.left+(node.width/2)) * width,(node.top+(node.height/2)) * height);
        }
    }

    private void fillCircleEventDetails(SMTransitionLink circlelink) {

        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(circlelink.eventLabel,circlelink.startX*width,circlelink.startY*height + (0.02*height));
        gc.fillText(circlelink.contextLabel,circlelink.startX*width,circlelink.startY*height + (0.09*height));
        gc.fillText(circlelink.effectsLabel,circlelink.startX*width,circlelink.startY*height + (0.18*height));

        gc.fillText(circlelink.event,circlelink.startX*width,circlelink.startY*height + (0.04*height));
        gc.fillText(circlelink.context,circlelink.startX*width,circlelink.startY*height + (0.2*height));
        gc.fillText(circlelink.sideEffects,circlelink.startX*width,circlelink.startY*height + (0.11*height));
    }

    private void updateLinkBounds(SMTransitionLink link) {
//        link.startX = (link.startX - iModel.viewLeft) * width;
//        link.startY = (link.startY - iModel.viewTop) * height;
//        link.endX = (link.endX - iModel.viewLeft) * width;
//        link.endY = (link.endY - iModel.viewTop) * height;
    }

    private void fillEventDetails(SMTransitionLink link) {
        gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.fillText(link.eventLabel,link.left*width-(0.08 * width),link.top*height-(0.08 * height));
        gc.fillText(link.contextLabel,link.left*width-(0.08 * width),link.top*height-(0.03 * height));
        gc.fillText(link.effectsLabel,link.left*width-(0.08 * width),link.top*height+(0.03 * height));

        gc.fillText(link.event,link.left*width-(0.08 * width),link.top*height-(0.06 * height));
        gc.fillText(link.context,link.left*width-(0.08 * width),link.top*height+(0.05 * height));
        gc.fillText(link.sideEffects,link.left*width-(0.08 * width),link.top*height-(0.01 * height));
    }



    @Override
    public void modelChanged() {
        draw();
        updateToolPalleteChange();
    }

    public void setSmModel(SMModel smModel) {model = smModel;}

    @Override
    public void iModelChanged() {
        draw();
        updateToolPalleteChange();
        if (iModel.getSelectedButtonIndex() == 1) this.setCursor(Cursor.MOVE);
        else if (iModel.getSelectedButtonIndex() == 2) this.setCursor(Cursor.CROSSHAIR);
        else this.setCursor(Cursor.DEFAULT);
    }

    private void updateToolPalleteChange() {
        stateBox.getChildren().clear();
        if (iModel.getSelectedButtonIndex() == 2 || iModel.getSelectionLink() != null){
            stateBox.getChildren().addAll(linkPropertiesView.transition, linkPropertiesView.eventLabel,linkPropertiesView.stateEvent,linkPropertiesView.contextLabel,linkPropertiesView.stateSideEffects,linkPropertiesView.effectsLabel,linkPropertiesView.stateContext,update);}
        else {
            nodePropertiesView.stateButton.setMinWidth(0.02*width);
            stateBox.getChildren().addAll(nodePropertiesView.stateButton, nodePropertiesView.stateLabel,nodePropertiesView.state);
        }
        stateBox.setPadding(new Insets(20,0,0,0));
        stateBox.setSpacing(10);
    }
}

