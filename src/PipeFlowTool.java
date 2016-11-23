import file.ImageManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.CanvasPanel;

import java.io.File;

public class PipeFlowTool extends Application {

    private VBox root;
    private HBox buttonBar;
    private CanvasPanel canvasPanel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ImageManager.initializeImages();

        root = new VBox();
        root.setMinWidth(640);
        root.setMinHeight(480);

        setUpButtonBar(primaryStage);
        Canvas canvas = new Canvas();
        setUpCanvas(canvas);
        root.getChildren().addAll(buttonBar, canvas);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Pipe Flow Tool");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setUpButtonBar(Stage primaryStage) {
        buttonBar = new HBox();

        MenuButton fileMenuButton = new MenuButton("File");
        fileMenuButton.setMinHeight(42.0); // image on buttons height + 10
        fileMenuButton.setPrefHeight(42.0);
        fileMenuButton.setMaxHeight(42.0);

        //add items to file menu button
        MenuItem newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As...");
        fileMenuButton.getItems().addAll(newItem, openItem, separator, saveItem, saveAsItem);

        newItem.setOnAction(e -> {/*TODO: clear everything and warn if not saved*/});
        openItem.setOnAction(e -> openFile(primaryStage));
        saveItem.setOnAction(e -> {/*TODO: overwrite with saveAs method by knowing the filename*/});
        saveAsItem.setOnAction(e -> saveFileAs(primaryStage));

        Button pumpButton = new Button();
        pumpButton.setGraphic(new ImageView(ImageManager.getImage("pump")));

        Button sinkButton = new Button("sink");
        Button splitterButton = new Button("splitter");
        Button adjustableSplitterButton = new Button("adjustable splitter");
        Button mergerButton = new Button("merger");

        buttonBar.getChildren().addAll(
                fileMenuButton, pumpButton, sinkButton,
                splitterButton, adjustableSplitterButton, mergerButton);
        buttonBar.setSpacing(10);
        buttonBar.setPadding(new Insets(10, 10, 10, 10));
    }

    private void setUpCanvas(Canvas canvas) {
        canvasPanel = new CanvasPanel(canvas);

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        canvas.widthProperty().addListener(observable -> canvasPanel.redraw());
        canvas.heightProperty().addListener(observable -> canvasPanel.redraw());
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
                System.out.println(ex.getMessage());
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