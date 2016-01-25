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
public class BlueCove implements IBTAdapter, IServiceCallback {

    private boolean State = false;
    private static Object lock = new Object();
    private List<RemoteDevice> AvailableDevices;
    private HashMap<String, ServicesConfig> ServicesMapping;
    private String connectionURL;
    private HashMap<String, IBTService> BTServices;

    @Override
    public void StartAdapter() {
        AvailableDevices = new ArrayList();
        BTServices = new HashMap<>();
        LocalDevice LD;
        //
        InitServicesMapping();
        //
        try {
            //display local device address and name

            LD = LocalDevice.getLocalDevice();

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                State = false;
                return;
            }
            //
            State = true;
            //
            StartDevicesSearch();
            //
            StartBTEXAService();

        } catch (BluetoothStateException ex) {
            State = false;
            out.println("[BT][ERR] Bluetooth adapter disabled");
        }
    }

    private void StartDevicesSearch() throws BluetoothStateException {
       // DiscoveryAgent agent = LD.getDiscoveryAgent();
      //  agent.startInquiry(DiscoveryAgent.GIAC, BTDiscovery);
    }

    private void StartServicesSearch(UUID[] uuidSet, RemoteDevice Dev) throws BluetoothStateException {
     //   DiscoveryAgent agent = LD.getDiscoveryAgent();
     //   agent.searchServices(null, uuidSet, Dev, BTDiscovery);
    }

    private void ConnectLocalDevices() throws BluetoothStateException {

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
                StartServicesSearch(uuidSet, Dev);

                //
                if (connectionURL != null) {
                    IBTService Svc = null;
                    if (Conf.DevType == ServicesConfig.BT_ServiceType.RFCOMM) {
                        Svc = new BTServiceRFCOMM();
                        Svc.ConnectService(connectionURL, this);
                    }

                    BTServices.put(Dev.getBluetoothAddress(), Svc);
                }
            }
        }

    }

    private void StartBTEXAService() {

    }

    @Override
    public void StopAdaper() {
        State = false;
    }

    @Override
    public void ReceiveServiceData(String SrcAddr, Byte[] Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String SrcAddr, String Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void InitServicesMapping() {
        ServicesMapping = new HashMap<>();
        for (ServicesConfig SC : PluginSettings.MainConfiguration.BTServicesMapping) {
            ServicesMapping.put(SC.DevAddr, SC);
        }

    }
    DiscoveryListener BTDiscovery = new DiscoveryListener() {

        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            //add the device to the vector
            if (!AvailableDevices.contains(btDevice)) {
                AvailableDevices.add(btDevice);
            }
        }

        @Override
        public void servicesDiscovered(int i, ServiceRecord[] srs) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void serviceSearchCompleted(int i, int i1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void inquiryCompleted(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        if (servRecord != null && servRecord.length > 0) {
            connectionURL = servRecord[0].getConnectionURL(0, false);
        }

    }

//implement this method since services are not being discovered
    public void serviceSearchCompleted(int transID, int respCode) {

    }

    public void inquiryCompleted(int discType) {
     //   ConnectLocalDevices();
    }//end method

    @Override
    public boolean State() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
