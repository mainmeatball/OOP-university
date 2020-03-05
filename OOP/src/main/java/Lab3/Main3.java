package Lab3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main3 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        TApplication app = new TApplication();

        primaryStage.setTitle("Complex polynom");

        final int argsWidth = 50;

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(50, 25, 25, 25));

        Text sceneTitle = new Text("Введите многочлен, чтобы посчитать его корни:");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        grid.add(sceneTitle, 0, 0, 6, 1);

        TextField argA_re = new TextField();
        argA_re.setPrefWidth(argsWidth);
        argA_re.setPromptText("Re:");
        grid.add(argA_re, 0, 1);

        TextField argA_im = new TextField();
        argA_im.setPrefWidth(argsWidth);
        argA_im.setPromptText("Im:");
        grid.add(argA_im, 0, 2);

        Label xx = new Label("x^2 + ");
        grid.add(xx, 1, 1, 1, 2);

        TextField argB_re = new TextField();
        argB_re.setPrefWidth(argsWidth);
        argB_re.setPromptText("Re:");
        grid.add(argB_re, 2, 1);

        TextField argB_im = new TextField();
        argB_im.setPrefWidth(argsWidth);
        argB_im.setPromptText("Im:");
        grid.add(argB_im, 2, 2);

        Label x = new Label("x + ");
        grid.add(x, 3, 1, 1, 2);

        TextField argC_re = new TextField();
        argC_re.setPrefWidth(argsWidth);
        argC_re.setPromptText("Re:");
        grid.add(argC_re, 4, 1);

        TextField argC_im = new TextField();
        argC_im.setPrefWidth(argsWidth);
        argC_im.setPromptText("Im:");
        grid.add(argC_im, 4, 2);

        Label userName = new Label(" = 0");
        grid.add(userName, 5, 1, 1, 2);

        Button btn = new Button("Посчитать");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 0, 3, 6, 1);

        final Text result = new Text();
        result.setTextAlignment(TextAlignment.CENTER);
        grid.add(result, 0, 4, 6, 1);

        btn.setOnAction(e -> result.setText(app.exec(
                argA_re.getText() + " " + argA_re.getText(),
                argB_re.getText() + " " + argB_re.getText(),
                argC_re.getText() + " " + argC_re.getText())));

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
