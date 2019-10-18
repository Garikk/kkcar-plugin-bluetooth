/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bt_py;

import java.util.ArrayList;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import kkdev.kksystem.plugin.bluetooth.manager.BTManager;

/**
 *
 * @author garikk
 */
public interface IBTAdapter_callback {
    void ReceiveDeviceList(Object DevList);
    void ReceiveData(String ServiceTag, String DeviceAddr, String Json);
}
