package sample;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

public class UserSettings {

    public UserSettings() {
    }

    private HashMap<String, Properties> hsm;
    // -- Properties

    // -- IP & Port
    private String ip;
    private int port;
    private float run_speed;

    public float getRun_speed() {
        return run_speed;
    }

    public void setRun_speed(float run_speed) {
        this.run_speed = run_speed;
    }

    public HashMap<String, Properties> getHsm() {
        return hsm;
    }

    public void setHsm(HashMap<String, Properties> hsm) {
        this.hsm = hsm;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}