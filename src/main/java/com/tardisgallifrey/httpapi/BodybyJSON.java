package com.tardisgallifrey.httpapi;

public class BodybyJSON {

    //model

    public void setModel(String model) {
        this.model = model;
    }

    private String model;

    //Temperature

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    private float temperature;

    public void setMessages(Message[] messages) {
        this.messages = messages;
    }

    private Message[] messages;



    public Message message;
}
