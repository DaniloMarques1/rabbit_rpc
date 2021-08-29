package com.fitz.example.rpcrabbit;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MessageServerMain {

    public static void main(String[] args) throws RemoteException {
        MessageServer messageServer = new MessageServer();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("messageService", messageServer);
        System.out.println("Serviço de mensagem iniciado");
    }

}
