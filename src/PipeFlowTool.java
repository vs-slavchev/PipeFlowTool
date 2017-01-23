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
import network.Simulation;
import utility.CanvasPanel;
import utility.CursorManager;
import utility.CursorManager.CursorType;
import utility.Values;

import java.io.*;

public class PipeFlowTool extends Application {

    private HBox buttonBar;
    private CanvasPanel canvasPanel;
    private VBox root;
    private String filePath = "";

    public static void main(String[] args) {
        launch(args);
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("Open file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("SIM", "*.sim")
        );
    }

    @Override
    public void start(Stage primaryStage) {
        ImageManager.initializeImages();

        root = new VBox();
        root.setMinWidth(640);
        root.setMinHeight(480);

        setUpButtonBar(primaryStage);
        root.getChildren().addAll(buttonBar, setUpCanvas(root, null));

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

        //addComponent items to file menu button
        MenuItem newItem = new MenuItem("New simulation");
        MenuItem openItem = new MenuItem("Open simulation");
        SeparatorMenuItem separator = new SeparatorMenuItem();
        MenuItem saveItem = new MenuItem("Save");
        MenuItem saveAsItem = new MenuItem("Save As...");
        fileMenuButton.getItems().addAll(newItem, openItem, separator, saveItem, saveAsItem);

        // assign action handlers to the items in the file menu
        newItem.setOnAction(e -> newFile());
        openItem.setOnAction(e -> openFile(primaryStage));
        saveItem.setOnAction(e -> saveFile());
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

        // set up the buttons that select objects
        Button pumpButton = createButton(CursorType.PUMP);
        Button splitterButton = createButton(CursorType.SPLITTER);
        Button mergerButton = createButton(CursorType.MERGER);
        Button sinkButton = createButton(CursorType.SINK);
        Button pipeButton = createButton(CursorType.PIPE);

        // addComponent the file menu, separators and the object buttons to the button bar
        buttonBar.getChildren().addAll(
                fileMenuButton, new Separator(Orientation.VERTICAL),
                pointerButton, deleteButton, new Separator(Orientation.VERTICAL),
                pumpButton, splitterButton, mergerButton, sinkButton, pipeButton);
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
        if (!canvasPanel.deleteSelectedComponents()) {
            CursorManager.setCursorType(CursorType.DELETE);
        }
    }

    private Canvas setUpCanvas(VBox root, Simulation sim) {
        Canvas canvas = new Canvas();

        if (sim != null) {
            canvasPanel = new CanvasPanel(canvas, sim);
        } else {
            canvasPanel = new CanvasPanel(canvas);
        }

        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());
        canvas.widthProperty().addListener(observable -> canvasPanel.redraw());
        canvas.heightProperty().addListener(observable -> canvasPanel.redraw());
        return canvas;
    }

    private void newFile() {
        this.canvasPanel.setSimulation(new Simulation());
        filePath = "";
    }

    private void openFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(primaryStage);
        //<-----------------------------Breakpoint here
        if (file != null) {
            try {
                FileInputStream inputStream = new FileInputStream(file.getPath());
                ObjectInputStream ois = new ObjectInputStream(inputStream);
                Simulation sim = (Simulation) ois.readObject();
                canvasPanel.setSimulation(sim);
                filePath = file.getPath();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void saveFile() {
        if (filePath != "") {
            try {
                FileOutputStream streamOut = new FileOutputStream(filePath);
                ObjectOutputStream oos = new ObjectOutputStream(streamOut);
                oos.writeObject(this.canvasPanel.getSimulation());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void saveFileAs(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            try {
                FileOutputStream streamOut = new FileOutputStream(file.getPath());
                ObjectOutputStream oos = new ObjectOutputStream(streamOut);
                oos.writeObject(this.canvasPanel.getSimulation());
                filePath = file.getPath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}