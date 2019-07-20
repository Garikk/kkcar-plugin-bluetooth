/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters;

import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import kkdev.kksystem.plugin.bluetooth.manager.BTManager;


/**
 *
 * @author blinov_is
 */
public interface IBTAdapter {
    void StartAdapter(BTManager BTM);
    void StopAdaper();
    void SetDiscoverableStatus(boolean Discover);
    boolean State();
    void RegisterService(ServicesConfig SC);
    void SendJsonData(String ServiceTag, String Json);
   
}
