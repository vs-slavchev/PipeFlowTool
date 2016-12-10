import file.ImageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.NetworkFactory;
import utility.CanvasPanel;
import utility.CursorManager;
import utility.CursorManager.CursorType;
import utility.Values;

import java.io.File;

public class PipeFlowTool extends Application {

    private HBox buttonBar;
    private CanvasPanel canvasPanel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ImageManager.initializeImages();

        VBox root = new VBox();
        root.setMinWidth(640);
        root.setMinHeight(480);

        setUpButtonBar(primaryStage);
        root.getChildren().addAll(buttonBar, setUpCanvas(root));

        Scene scene = new Scene(root);
        primaryStage.setTitle("Pipe Flow Tool");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();

        CursorManager.setScene(scene);
    }

    private void setUpButtonBar(Stage primaryStage) {
        buttonBar = new HBox();

        MenuButton fileMenuButton = new MenuButton("File");
        fileMenuButton.setMinHeight(Values.FILE_BUTTON_HEIGHT);
        fileMenuButton.setPrefHeight(Values.FILE_BUTTON_HEIGHT);
        fileMenuButton.setMaxHeight(Values.FILE_BUTTON_HEIGHT);

        //add items to file menu button
        MenuItem newItem = new MenuItem("New simulation");
        MenuItem openItem = new MenuItem("Open simulation");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As...");
        fileMenuButton.getItems().addAll(newItem, openItem, separator, saveItem, saveAsItem);

        // assign action handlers to the items in the file menu
        newItem.setOnAction(e -> {/*TODO: clear everything and warn if not saved*/});
        openItem.setOnAction(e -> openFile(primaryStage));
        saveItem.setOnAction(e -> {/*TODO: overwrite with saveAs method by knowing the filename*/});
        saveAsItem.setOnAction(e -> saveFileAs(primaryStage));

        setUpButtons(fileMenuButton);
    }

    private void setUpButtons(MenuButton fileMenuButton) {
        Button pointerButton = new Button();
        pointerButton.setGraphic(new ImageView(ImageManager.getImage("pointer32")));
        pointerButton.setOnAction(event -> CursorManager.setCursorType(CursorType.POINTER));

        Button deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(ImageManager.getImage("delete32")));
        deleteButton.setOnAction(event -> deleteButtonClicked());

        // set up the 4 buttons that select objects
        Button pumpButton = createButton(CursorType.PUMP);
        Button splitterButton = createButton(CursorType.SPLITTER);
        Button mergerButton = createButton(CursorType.MERGER);
        Button sinkButton = createButton(CursorType.SINK);

        // add the file menu, separators and the object buttons to the button bar
        buttonBar.getChildren().addAll(
                fileMenuButton, new Separator(Orientation.VERTICAL),
                pointerButton, deleteButton, new Separator(Orientation.VERTICAL),
                pumpButton, splitterButton, mergerButton, sinkButton);
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
    }

    private Button createButton(CursorType cursor) {
        Button button = new Button();
        button.setGraphic(new ImageView(
                ImageManager.getImage(cursor.toString().toLowerCase() + "32")));
        button.setOnAction(event -> CursorManager.setCursorType(cursor));
        return button;
    }

    private void deleteButtonClicked() {
        if (!canvasPanel.deleteSelectedObjects()) {
            CursorManager.setCursorType(CursorType.DELETE);
        }
    }

    private Canvas setUpCanvas(VBox root) {
        Canvas canvas = new Canvas();
        canvasPanel = new CanvasPanel(canvas);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        canvas.widthProperty().addListener(observable -> canvasPanel.redraw());
        canvas.heightProperty().addListener(observable -> canvasPanel.redraw());
        return canvas;
    }

    private void openFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            //openFile(file); // TODO: implement method
        }
    }

    private void saveFileAs(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            /*try {
                // TODO: write and save to file
            } catch (IOException ex) {

            }*/
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JPG", "*.jpg") // TODO: fix extension
        );
    }
}