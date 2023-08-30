package chatgpt.Client.controller;

import chatgpt.Client.ai.ChatGPT;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static chatgpt.Client.controller.LoginFormController.userName;

public class ChatFormController {
    public TextField txtClientMessage;
    public Label lblUserName;
    public VBox chatListContext;
    public ScrollPane scrollPane;
    private ChatGPT chatGPT;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    public void initialize() throws IOException {
        lblUserName.setText(userName);
        txtClientMessage.requestFocus();
        chatGPT = new ChatGPT();
    }

    public void sendMassage(){
        String messageToSend = txtClientMessage.getText();
        if (!messageToSend.isEmpty()){

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    HBox hBox =  new HBox();

                    VBox vBox1=new VBox();
                    Text user = new Text("you ");
                    TextFlow userFlow = new TextFlow(user);
                    vBox1.getChildren().add(userFlow);
                    user.setFill(Color.WHITE);
                    user.setStyle("-fx-font-size: 10");
                    userFlow.setTextAlignment(TextAlignment.RIGHT);

                    hBox.setAlignment(Pos.CENTER_RIGHT );
                    hBox.setPadding(new Insets(5,10,5,10));
                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
//            textFlow.setStyle("-fx-background-color: #3a86ff;"+"-fx-background-radius: 10px");
                    vBox1.setStyle("-fx-background-color: #3a86ff;"+"-fx-background-radius: 10px 0px 10px 10px");
                    textFlow.setPadding(new Insets(5,10,5,10));
                    text.setFill(Color.color(1,1,1));
                    vBox1.getChildren().add(textFlow);
                    hBox.getChildren().add(vBox1);
                    chatListContext.getChildren().add(hBox);
                    txtClientMessage.clear();
                }
            });
            new Thread(()->{
                String send = chatGPT.send(messageToSend);
                addLabel(send,chatListContext);
            }).start();

        }
        scrollPane.setVvalue(1.0);
    }

    public void sendOnAction(MouseEvent actionEvent) {
        sendMassage();
    }

    public void closeOnAction(MouseEvent mouseEvent) {
        System.exit(0);
    }


    public static void  addLabel(String messageFromServer,VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        VBox vBox1=new VBox();
        hBox.setPadding(new Insets(5,10,5,10));

        Text text;
        Text user;
        text = new Text(messageFromServer);
        user=new Text("Open AI");
        TextFlow userFlow = new TextFlow(user);
        vBox1.getChildren().add(userFlow);
        user.setFill(Color.BLUE);
        user.setStyle("-fx-font-size: 10");

        TextFlow textFlow = new TextFlow(text);
        vBox1.setStyle("-fx-background-color: #edf6f9;"+"-fx-background-radius: 0 10px 10px 10px");
        textFlow.setPadding(new Insets(5,10,5,10));
//        hBox.getChildren().add(textFlow);
        vBox1.getChildren().add(textFlow);
        hBox.getChildren().add(vBox1);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }
    public void textFieldEnterOnAction(ActionEvent actionEvent) {
        sendMassage();
    }
}

