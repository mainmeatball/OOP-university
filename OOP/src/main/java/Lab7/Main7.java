package Lab7;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Main7 extends Application {

    static class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    private final ObservableList<String> shapesNames =
            FXCollections.observableArrayList(
                    "Прямоугольник",
                    "Квадрат",
                    "Эллипс",
                    "Круг"
            );

    Set<Point> points = new HashSet<>();

    Set<Point> interactionPoints = new HashSet<>();

    List<SymmetricShape> shapes = new ArrayList<>();

    Label infoLabel = new Label("");
    TextField columns = new TextField("10");
    TextField rows = new TextField("10");

    public Main7() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(8);
        grid.setVgap(8);
        grid.setPadding(new Insets(8));

        Canvas canvas = new Canvas(1000, 700);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        ChoiceBox<String> cb = new ChoiceBox<>(shapesNames);
        cb.setValue(shapesNames.get(0));

        Label width = new Label("Ширина: ");

        Slider sliderWidth = new Slider(0, 800, 200);
        sliderWidth.setShowTickLabels(true);
        sliderWidth.setShowTickMarks(true);
        sliderWidth.setSnapToTicks(true);
        sliderWidth.setMajorTickUnit(100);
        sliderWidth.setMinorTickCount(50);
        sliderWidth.setBlockIncrement(50);

        Label height = new Label("Высота: ");

        Slider sliderHeight = new Slider(0, 800, 100);
        sliderHeight.setShowTickLabels(true);
        sliderHeight.setShowTickMarks(true);
        sliderHeight.setSnapToTicks(true);
        sliderHeight.setMajorTickUnit(100);
        sliderHeight.setMinorTickCount(50);
        sliderHeight.setBlockIncrement(50);

        Button regular = new Button("Регулярный");
        Button random = new Button("Случайный");
        Button clear = new Button("Очистить");

        int argsWidth = 50;
        columns.setPrefWidth(argsWidth);
        rows.setPrefWidth(argsWidth);

        cb.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    switch (newValue) {
                        case "Прямоугольник":
                        case "Эллипс": {
                            width.setVisible(true);
                            sliderWidth.setVisible(true);
                            height.setVisible(true);
                            sliderHeight.setVisible(true);
                            break;
                        }
                        case "Квадрат":
                        case "Круг": {
                            width.setVisible(false);
                            sliderWidth.setVisible(true);
                            height.setVisible(false);
                            sliderHeight.setVisible(false);
                            break;
                        }
                    }
                }
        );

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(cb, width, sliderWidth, height, sliderHeight, regular, random, columns, rows, clear, infoLabel);
        hBox.setAlignment(Pos.CENTER);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                m -> drawShape(
                        gc,
                        m,
                        cb.getSelectionModel().getSelectedItem(),
                        sliderWidth.valueProperty().intValue(),
                        sliderHeight.valueProperty().intValue()
                )
        );

        regular.setOnAction(e -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            points.clear();
            interactionPoints.clear();
            drawLines(canvas, Integer.parseInt(columns.getText()), Integer.parseInt(rows.getText()));
            drawShapes(gc);
            highlightIntersectionPoints(gc, shapes);
        });

        random.setOnAction(e -> {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            points.clear();
            interactionPoints.clear();
            drawDots(canvas, Integer.parseInt(columns.getText()), Integer.parseInt(rows.getText()));
            drawShapes(gc);
            highlightIntersectionPoints(gc, shapes);
        });

        clear.setOnAction(e -> {
            points.clear();
            shapes.clear();
            interactionPoints.clear();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            updateInfoLabel();
        });

        grid.add(hBox, 0, 0);
        grid.add(canvas, 0, 1);
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }

    private void drawShape(GraphicsContext gc, MouseEvent m, String choice, int width, int height) {
        SymmetricShape shape;

        switch (choice) {
            case "Прямоугольник": {
                shape = new Rectangle(width, height);
                break;
            }
            case "Квадрат": {
                shape = new Square(width);
                break;
            }
            case "Эллипс": {
                shape = new Ellipsis(width, height);
                break;
            }
            case "Круг": {
                shape = new Circle(width);
                break;
            }
            default:
                shape = new Rectangle(width, height);
                break;
        }

        shape.setCenter(m.getX(), m.getY());

        shapes.add(shape);

        if (!points.isEmpty()) {
            highlightIntersectionPoints(gc, shape);
        }

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        shape.draw(gc);
    }

    void highlightIntersectionPoints(GraphicsContext gc, List<SymmetricShape> shapes) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);

        for (SymmetricShape shape : shapes) {
            for (Point p : points) {
                if (shape.contains(p.x, p.y)) {
                    gc.strokeOval(p.x, p.y, 3, 3);
                    interactionPoints.add(p);
                }
            }
        }

        updateInfoLabel();
    }

    void highlightIntersectionPoints(GraphicsContext gc, SymmetricShape shape) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(5);

        for (Point p : points) {
            if (shape.contains(p.x, p.y)) {
                gc.strokeOval(p.x, p.y, 3, 3);
                interactionPoints.add(p);
            }
        }

        updateInfoLabel();
    }

    void drawShapes(GraphicsContext gc) {
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        shapes.forEach(shape -> shape.draw(gc));
    }

    void drawLines(Canvas canvas, double ii, double jj) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        double iStep = (canvas.getWidth() / (ii + 1));
        double jStep = (canvas.getHeight() / (jj + 1));

        int countI = 0;
        for (double i = iStep; countI < ii; i += iStep, countI++) {
            int countJ = 0;
            gc.strokeLine(i, 0, i, canvas.getHeight());
            for (double j = jStep; countJ < jj; j += jStep, countJ++) {
                if (countI == 0) {
                    gc.strokeLine(0, j, canvas.getWidth(), j);
                }
                points.add(new Point(i, j));
            }
        }
    }

    void drawDots(Canvas canvas, double ii, double jj) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);

        double iStep = (canvas.getWidth() / ii);
        double jStep = (canvas.getHeight() / jj);

        int countI = 0;
        for (double i = iStep; countI < ii; i += iStep, countI++) {
            int countJ = 0;
            for (double j = jStep; countJ < jj; j += jStep, countJ++) {
                double x = ThreadLocalRandom.current().nextDouble(i - iStep, i + 1);
                double y = ThreadLocalRandom.current().nextDouble(j - jStep, j + 1);
                points.add(new Point(x, y));
                gc.strokeOval(x, y, 1, 1);
            }

        }
    }

    void updateInfoLabel() {
        infoLabel.setText("" + interactionPoints.size() * 100 / points.size() + "%");
    }

}