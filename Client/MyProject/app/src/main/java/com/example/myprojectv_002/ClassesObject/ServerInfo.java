package com.example.myprojectv_002.ClassesObject;

public class ServerInfo {
    private static String IP = "192.168.1.223";
    //private static String IP = "172.20.165.3";
    private static int port = 3000;

    public static String getIP() {
        return IP;
    }
    public static int getPort() {
        return port;
    }
}
