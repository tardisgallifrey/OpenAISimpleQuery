package com.tardisgallifrey.httpapi;

import com.google.gson.Gson;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner input = new Scanner(System.in);
        String myKey = System.getenv("OPENAI_API_KEY");

        System.out.println("Ask OpenAI a question:");
        String message = input.nextLine();
        Message[] messages = new Message[]{new Message("user", message)};
        input.close();

        BodybyJSON body = new BodybyJSON();

        body.setModel("gpt-3.5-turbo");
        body.setMessages(messages);
        body.setTemperature(0.7f);

        Gson response = new Gson();
        String jsonRequest = response.toJson(body);


        HttpRequest myRequest = HttpRequest
                .newBuilder()
                .uri(new URI("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+myKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> myResponse = httpClient.send(myRequest,
                HttpResponse.BodyHandlers
                        .ofString());

        List<String> return_message = getTokens(myResponse.body(), ",");

        for(String field : return_message){
            if( field.contains("content")){
                System.out.println("The response is:");
                int start = field.indexOf(": ");
                int end = field.indexOf("}");
                System.out.println(field.substring(start+3, end-8));
            }
        }

    }

    static List<String> getTokens(String str, String token) {

        List<String> tokens = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(str, token);
        while (tokenizer.hasMoreElements()) {
            tokens.add(tokenizer.nextToken());
        }
        return tokens;
    }
}
