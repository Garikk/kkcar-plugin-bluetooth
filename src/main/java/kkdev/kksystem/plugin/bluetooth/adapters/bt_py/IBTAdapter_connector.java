/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bt_py;

import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;

/**
 *
 * @author garikk
 */
public interface IBTAdapter_connector {
    void StartAdapter();
    void StopAdaper();
    void SetDiscoverableStatus(boolean Discover);
    boolean State();
    //void RegisterService(ServicesConfig SC);
    void SendStringData(String ServiceTag, String Json);
    void SendBytesData(String ServiceTag, byte[] data);
}
