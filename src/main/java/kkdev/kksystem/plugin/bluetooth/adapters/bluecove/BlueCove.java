/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bluecove;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.configuration.PluginSettings;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import kkdev.kksystem.plugin.bluetooth.services.IBTService;
import kkdev.kksystem.plugin.bluetooth.services.IServiceCallback;
import kkdev.kksystem.plugin.bluetooth.services.rfcomm.BTServiceRFCOMM;

/**
 *
 * @author sayma_000
 */
public class BlueCove implements DiscoveryListener, IBTAdapter,IServiceCallback {

    private boolean State=false;
    private static Object lock = new Object();
    private List<RemoteDevice> AvailableDevices;
    private HashMap<String, ServicesConfig> ServicesMapping;
    private String connectionURL;
    private HashMap<String,IBTService> BTServices;

    @Override
    public void StartAdapter() {
        AvailableDevices = new ArrayList();
        BlueCove client = new BlueCove();
        BTServices=new HashMap<>();
        //
        InitServicesMapping();
        //
        try {
            //display local device address and name
            LocalDevice localDevice;
            localDevice = LocalDevice.getLocalDevice();

            //find devices
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
            agent.startInquiry(DiscoveryAgent.GIAC, client);

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                State=false;
                out.println("[BT][ERR] Bluetooth adapter disables");
            }
            //
            State=true;
            
            
            UUID[] uuidSet;
            for (RemoteDevice Dev : AvailableDevices) {
                if (ServicesMapping.containsKey(Dev.getBluetoothAddress())) {
                    ServicesConfig Conf = ServicesMapping.get(Dev.getBluetoothAddress());
                    //
                    uuidSet = new UUID[Conf.ServicesUUID.length];
                    //
                    for (int i = 0; i < Conf.ServicesUUID.length; i++) {
                        uuidSet[i] = new UUID(Conf.ServicesUUID[i]);
                    }
                    //
                    connectionURL=null;
                    agent.searchServices(null, uuidSet, Dev, client);
                    //
                    try {
                        synchronized (lock) {
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //
                    if (connectionURL!=null)
                    {
                        IBTService Svc=null;
                        if (Conf.SourceType==ServicesConfig.BT_ServiceType.RFCOMM)
                        {
                            Svc=new BTServiceRFCOMM();
                            Svc.ConnectService(connectionURL, this);
                        }
                        
                        BTServices.put(Dev.getBluetoothAddress(), Svc);
                    }
                }
            }

        } catch (BluetoothStateException ex) {
            State=false;
            out.println("[BT][ERR] Bluetooth adapter disables");
        }
    }

     @Override
    public void ReceiveServiceData(String SrcAddr, Byte[] Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String SrcAddr, String Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    @Override
    public void StopAdaper() {
        State=false;
    }

    private void InitServicesMapping() {
        ServicesMapping = new HashMap<>();
        for (ServicesConfig SC : PluginSettings.MainConfiguration.BTServicesMapping) {
            ServicesMapping.put(SC.SourceAddr, SC);
        }

    }

//methods of DiscoveryListener
    public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
        //add the device to the vector
        if (!AvailableDevices.contains(btDevice)) {
            AvailableDevices.add(btDevice);
        }
    }

//implement this method since services are not being discovered
    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if (servRecord != null && servRecord.length > 0) {
            connectionURL = servRecord[0].getConnectionURL(0, false);
        }
        synchronized (lock) {
            lock.notify();
        }
    }

//implement this method since services are not being discovered
    public void serviceSearchCompleted(int transID, int respCode) {
        synchronized (lock) {
            lock.notify();
        }
    }

    public void inquiryCompleted(int discType) {
        synchronized (lock) {
            lock.notify();
        }

    }//end method

    @Override
    public boolean State() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
