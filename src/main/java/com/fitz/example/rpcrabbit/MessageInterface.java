package com.fitz.example.rpcrabbit;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageInterface extends Remote {
    void sendName(String name) throws RemoteException;
}
