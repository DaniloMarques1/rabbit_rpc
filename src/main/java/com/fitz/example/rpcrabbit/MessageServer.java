package com.fitz.example.rpcrabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageServer extends UnicastRemoteObject implements MessageInterface {

    public MessageServer() throws RemoteException {}

    @Override
    public void sendName(String name) {
        System.out.printf("Receveid new name %s\n", name);

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("0.0.0.0");
        connectionFactory.setPort(5672);

        String queueName = "message_name";

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            String msg = String.format("New name received %s", name);
            channel.basicPublish("", queueName, null, msg.getBytes());

            System.out.println("Nome enviado para a fila");

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
