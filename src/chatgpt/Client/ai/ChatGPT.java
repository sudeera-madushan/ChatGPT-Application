package chatgpt.Client.ai;

import chatgpt.Client.controller.ChatFormController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatGPT {

    private final String url;
    private final String apiKey;
    private final String model;
    private OutputStreamWriter writer;
    private StringBuffer response;
    private BufferedReader in;
    private URL obj;
    private HttpURLConnection con;

    public ChatGPT() {
         url = "https://api.openai.com/v1/chat/completions";
         apiKey = "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";
         model = "gpt-3.5-turbo";
        try {
            obj=new URL(url);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String send(String message){

        try {
            con= (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type","application/json");
            con.setDoOutput(true);
            String body="{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            return (response.toString().split("\"content\": \"")[1]).split("\"      },      \"finish_reason\": \"")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public void receive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                }
            }
        }).start();
    }
}