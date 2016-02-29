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
    public void StartAdapter(BTManager BTM);
    public void StopAdaper();
    public boolean State();
    public void RegisterService(ServicesConfig SC);
    public void SendJsonData(String Json);
   
}
