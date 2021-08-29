package com.fitz.example.rpcrabbit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessageClient {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Iniciando cliente");
        Registry registry = LocateRegistry.getRegistry();
        MessageInterface message = (MessageInterface) registry.lookup("messageService");


        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("0.0.0.0");
        try {
            message.sendName("Danilo Marques de Oliveira");
            String queueName = "message_name";

            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);

            DeliverCallback callback = (consumerTag, delivery) -> {
                String msg = new String(delivery.getBody());
                System.out.printf("Mensagem consumida da fila %s\n", msg);
            };

            channel.basicConsume(queueName, true, callback, consumerTah -> {});
            System.out.println("Esperando a leitura da mensagem");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
