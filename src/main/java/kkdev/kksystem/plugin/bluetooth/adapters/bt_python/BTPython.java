/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bt_python;

import grpc.BtService.DiscoveredDevices.BTDevice;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import static kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig.BT_ServiceType.RFCOMM;
import kkdev.kksystem.plugin.bluetooth.manager.BTManager;
import kkdev.kksystem.plugin.bluetooth.services.IBTService;
import kkdev.kksystem.plugin.bluetooth.services.IServiceCallback;
import kkdev.kksystem.plugin.bluetooth.services.rfcomm.BTServiceRFCOMM;

/**
 *
 * @author sayma_000
 */
public class BTPython implements IBTAdapter, IServiceCallback {

    private boolean State = false;

    private BTGRPCConnector BTConnector;
    private List<ServicesConfig> ServicesMapping;
    private BTServiceApi BTApi;
    BTManager BTM;

    @Override
    public void StartAdapter(BTManager MyBTM) {
      
        BTConnector = new BTGRPCConnector();
        BTM = MyBTM;
        var availableDevices = new HashMap<String, String>();
        var BTServices = new HashMap<String, IBTService>();
        List<BTConnectionWorker> connectionWorker = new ArrayList<>();
        //
        List<Thread> BTServer = new ArrayList<>();
        //
        //LD = BTConnector.getLocalDeviceInfo();
        State = true;
        // Init Services
        //InitServices();
        //
        //Init local devices
        //InitLocalDevices();
        //
    }
    @Override
    public void RegisterService(ServicesConfig SC) {
        if (ServicesMapping==null)
            ServicesMapping=new ArrayList<>();
            
        ServicesMapping.add(SC);
    }

    private void InitServices() {


    }

    @Override
    public void StopAdaper() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SetDiscoverableStatus(boolean Discover) {
        
    }

    @Override
    public boolean State() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendJsonData(String ServiceTag, String Json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String Tag, String SrcAddr, Byte[] Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String Tag, String SrcAddr, String Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
