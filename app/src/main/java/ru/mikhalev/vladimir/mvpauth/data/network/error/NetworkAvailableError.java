package ru.mikhalev.vladimir.mvpauth.data.network.error;

/**
 * Developer Vladimir Mikhalev 15.12.2016
 */
public class NetworkAvailableError extends Throwable {
    public NetworkAvailableError() {
        super("Интернет не доступен");
    }
}
