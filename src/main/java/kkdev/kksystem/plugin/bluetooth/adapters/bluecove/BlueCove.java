/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bluecove;

import java.io.IOException;
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
import kkdev.kksystem.plugin.bluetooth.manager.BTManager;
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
    private HashMap<String, RemoteDevice> AvailableDevices;
    private HashMap<String, ServicesConfig> ServicesMapping;
    private HashMap<String, IBTService> BTServices;
    private LocalDevice LD;
    BTManager BTM;
    
    @Override
    public void StartAdapter(BTManager MyBTM) {
        BTM=MyBTM;
        AvailableDevices = new HashMap<>();
        BTServices = new HashMap<>();

        //
        InitServicesMapping();
        //
        try {
            //display local device address and name

            LD = LocalDevice.getLocalDevice();

            //
            State = true;
            // 
            // Use this for connect new external devices
            StartDevicesSearch();
           // ConnectLocalDevices();
            //
            StartBTEXAService();

        } catch (BluetoothStateException ex) {
            State = false;
            out.println("[BT][ERR] Bluetooth adapter disabled");
        }
    }

    private void StartDevicesSearch() throws BluetoothStateException {
        DiscoveryAgent agent = LD.getDiscoveryAgent();
        agent.startInquiry(DiscoveryAgent.GIAC, BTDiscovery);
    }

    private void StartServicesSearch(UUID[] uuidSet, RemoteDevice Dev) throws BluetoothStateException {
        DiscoveryAgent agent = LD.getDiscoveryAgent();
        agent.searchServices(null, uuidSet, Dev, BTDiscovery);

    }

    private void ConnectLocalDevices() throws BluetoothStateException {

        for (ServicesConfig Conf : ServicesMapping.values()) {
            //
            if (Conf.DevType == ServicesConfig.BT_ServiceType.RFCOMM & Conf.ServerMode==false) {
                out.println("[BT][INF] SVC CONN " + Conf.DevAddr);
                IBTService Svc = null;
                Svc = new BTServiceRFCOMM();
                Svc.ConnectService(Conf.DevAddr, "btspp://" + Conf.DevAddr, this);
            } else {

            }
        }

    }

    private void ConnectLocalDevicesAfterDiscovery() throws BluetoothStateException {

        UUID[] uuidSet;
        for (RemoteDevice Dev : AvailableDevices.values()) {
            if (ServicesMapping.containsKey(Dev.getBluetoothAddress())) {
                ServicesConfig Conf = ServicesMapping.get(Dev.getBluetoothAddress());
                //
                uuidSet = new UUID[Conf.lServicesUUID.length + Conf.sServicesUUID.length];
                //
                for (int i = 0; i < Conf.sServicesUUID.length; i++) {
                    uuidSet[i] = new UUID(Conf.sServicesUUID[i], false);
                }
                for (int i = Conf.sServicesUUID.length; i < Conf.lServicesUUID.length + Conf.sServicesUUID.length; i++) {
                    uuidSet[i] = new UUID(Conf.lServicesUUID[i]);
                }
                //
                StartServicesSearch(uuidSet, Dev);

            }
        }

    }

    private void StartBTEXAService() {

    }

    @Override
    public void StopAdaper() {
        State = false;
    }



    private void InitServicesMapping() {
        ServicesMapping = new HashMap<>();
        for (ServicesConfig SC : PluginSettings.MainConfiguration.BTServicesMapping) {
            ServicesMapping.put(SC.DevAddr, SC);
            //    if (!AvailableDevices.containsKey(SC.DevAddr))
//            {
//                AvailableDevices.put(SC.DevAddr,new RemoteDevice(SC.DevAddr));
//                
//            }
        }

    }
    DiscoveryListener BTDiscovery = new DiscoveryListener() {

        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            //add the device to the vector
            if (!AvailableDevices.containsKey(btDevice.getBluetoothAddress())) {
                AvailableDevices.put(btDevice.getBluetoothAddress(), btDevice);
            }

        }

        @Override
        public void servicesDiscovered(int i, ServiceRecord[] srs) {
            //   if (servRecord != null && servRecord.length > 0) {
            //  connectionURL = servRecord[0].getConnectionURL(0, false);
            //
            ServicesConfig Conf = ServicesMapping.get(srs[0].getHostDevice().getBluetoothAddress());
            for (ServiceRecord SR : srs) {
                    out.println("[BT] Found SVC: " + SR.getAttributeValue(0x0001) + " " + SR.getConnectionURL(0, false));
                   
                }

            /*
                if (connectionURL != null) {
                    IBTService Svc = null;
                    if (Conf.DevType == ServicesConfig.BT_ServiceType.RFCOMM) {
                        Svc = new BTServiceRFCOMM();
                        Svc.ConnectService(connectionURL, this);
                    }

                    BTServices.put(Dev.getBluetoothAddress(), Svc);
                }
             */
        }

        @Override
        public void serviceSearchCompleted(int i, int i1) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void inquiryCompleted(int i) {
            for (RemoteDevice RD : AvailableDevices.values()) {
                out.println("[BT] Found devices: " + RD.getBluetoothAddress());
            }

            try {
                ConnectLocalDevicesAfterDiscovery();
            } catch (BluetoothStateException ex) {
                Logger.getLogger(BlueCove.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    @Override
    public boolean State() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String Tag, String SrcAddr, Byte[] Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String Tag, String SrcAddr, String Data) {
        BTM.BT_ReceiveData(Tag, Data);
    }


}
