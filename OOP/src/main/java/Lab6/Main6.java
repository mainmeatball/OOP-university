package Lab6;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main6 extends Application {

    private final int argsWidth = 50;
    private final ObservableList<String> triangles =
            FXCollections.observableArrayList(
                    "Разносторонний треугольник",
                    "Равнобедренный треугольник",
                    "Равносторонний треугольник"
            );

    public Main6() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drawing Operations Test");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        Canvas canvas = new Canvas(1000, 800);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        ChoiceBox<String> cb = new ChoiceBox<>(triangles);
        cb.setValue(triangles.get(0));

        Label aLabel = new Label("a = ");
        TextField a = new TextField();
        a.setPrefWidth(argsWidth);

        Label bLabel = new Label("b = ");
        TextField b = new TextField();
        b.setPrefWidth(argsWidth);

        Label cLabel = new Label("c = ");
        TextField c = new TextField();
        c.setPrefWidth(argsWidth);

        Label angleLable = new Label("angle = ");
        TextField angle = new TextField();
        angle.setPrefWidth(argsWidth);
        angle.setDisable(true);

        HBox hBox = new HBox();
        hBox.setSpacing(30);
        hBox.getChildren().addAll(cb, aLabel, a, bLabel, b, cLabel, c, angleLable, angle);
        hBox.setAlignment(Pos.CENTER);

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                m -> drawTriangle(
                        gc,
                        m,
                        cb.getSelectionModel().getSelectedItem(),
                        Double.parseDouble(a.getText()),
                        Double.parseDouble(b.getText().isEmpty() ? a.getText() : b.getText()),
                        Double.parseDouble(c.getText().isEmpty() ? a.getText() : c.getText()),
                        Integer.parseInt(angle.getText().isEmpty() ? "0" : angle.getText())
                ));

        cb.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    switch (newValue) {
                        case "Разносторонний треугольник": {
                            a.setDisable(false);
                            b.setDisable(false);
                            c.setDisable(false);
                            angle.setDisable(true);
                            break;
                        }
                        case "Равнобедренный треугольник": {
                            a.setDisable(false);
                            b.setDisable(true);
                            c.setDisable(true);
                            angle.setDisable(false);
                            break;
                        }
                        case "Равносторонний треугольник": {
                            a.setDisable(false);
                            b.setDisable(true);
                            c.setDisable(true);
                            angle.setDisable(true);
                            break;
                        }
                    }
                }
        );

        grid.add(hBox, 0, 0);
        grid.add(canvas, 0, 1);
        primaryStage.setScene(new Scene(grid));
        primaryStage.show();
    }

    private void drawTriangle(GraphicsContext gc, MouseEvent m, String choice, double a, double b, double c, int angle) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

        Triangle triangle;

        if (choice.equals(triangles.get(0))) {
            if (Triangle.isCorrect(a,b,c)) {
                triangle = new Triangle(a, b, c);
                triangle.setPoint(m.getX(), m.getY());
                gc.strokePolygon(triangle.getXPoints(), triangle.getYPoints(), 3);
            } else {
                System.out.println("треугольник некорректно задан");
            }
        } else if (choice.equals(triangles.get(1))) {
            triangle = new IsoscalesTriangle(a, angle);
            triangle.setPoint(m.getX(), m.getY());
            gc.strokePolygon(triangle.getXPoints(), triangle.getYPoints(), 3);
        } else {
            System.out.println("I'm here");
            triangle = new EquilateralTriangle(a);
            triangle.setPoint(m.getX(), m.getY());
            gc.strokePolygon(triangle.getXPoints(), triangle.getYPoints(), 3);
        }


    }
}