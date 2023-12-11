package com.example.a2048mult.Control;

/**
 * Interface to save MAC-Addresses of new Android-Devices
 */
public interface SaveDevices {
    /**
     * Saves the specified MAC-Addresses and decides player order
     * @param macAddress MAC-Address from new joined Android-Device
     */
    void saveDevice(String macAddress);
}
