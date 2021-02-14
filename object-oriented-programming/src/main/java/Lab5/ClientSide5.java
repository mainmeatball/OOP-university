package Lab5;

import javafx.application.Application;
import javafx.application.Platform;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSide5 extends Application {

    final String hostName = "127.0.0.1";
    final int portNumber = 4445;
    Socket kkSocket = new Socket(hostName, portNumber);
    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(
            new InputStreamReader(kkSocket.getInputStream()));

    public ClientSide5() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        grid.add(result, 0, 4, 6, 4);

        btn.setOnAction(e -> Platform.runLater( () -> {
            out.println(argA_re.getText().trim() + " " + argA_im.getText().trim());
            out.println(argB_re.getText().trim() + " " + argB_im.getText().trim());
            out.println(argC_re.getText().trim() + " " + argC_im.getText().trim());
            boolean connection = true;
            char[] a = new char[500];
            try {
                in.read(a);
            } catch (IOException ex) {
                connection = false;
                result.setText("Соединение с сервером пропало");
                ex.printStackTrace();
            }
            String res = new String(a);
            if (connection) result.setText(res);
        }));

        Scene scene = new Scene(grid, 600, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }
}