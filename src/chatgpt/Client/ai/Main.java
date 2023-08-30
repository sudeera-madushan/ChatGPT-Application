package chatgpt.Client.ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.print("\n\tYou : ");
        String nextLine = scanner.nextLine();
        while (true) {
            System.out.print("\n\t"+chatGPT(nextLine));
            System.out.print("\n\n\tYou : ");
            nextLine = scanner.nextLine();
        }
    }
    public static String chatGPT(String message){
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";
        try {
            URL obj=new URL(url);
            HttpURLConnection con= (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type","application/json");

            String body="{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();
            return (response.toString().split("\"content\": \"")[1]).split("\"      },      \"finish_reason\": \"")[0];
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}