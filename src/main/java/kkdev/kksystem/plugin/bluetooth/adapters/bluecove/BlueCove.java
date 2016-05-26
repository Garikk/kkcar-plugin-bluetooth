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
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnectionNotifier;
import javax.obex.Operation;
import javax.obex.ServerRequestHandler;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
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
    private List<Thread> BTServer;

    private HashMap<String, RemoteDevice> AvailableDevices;
    private List<ServicesConfig> ServicesMapping;
    private HashMap<String, IBTService> BTServices;
    private LocalDevice LD;
    private List<BTConnectionWorker> ConnectionWorker;
    BTManager BTM;

    @Override
    public void StartAdapter(BTManager MyBTM) {
        BTM = MyBTM;
        AvailableDevices = new HashMap<>();
        BTServices = new HashMap<>();
        ConnectionWorker = new ArrayList<>();
        //
        BTServer = new ArrayList<>();
        //
        try {
            //display local device address and name
            LD = LocalDevice.getLocalDevice();
            State = true;
            // Init Services
            InitServices();
            //
            //Init local devices
             InitLocalDevices();
             //
        } catch (BluetoothStateException ex) {
            State = false;
            out.println("[BT][ERR]" + ex.getMessage());
            out.println("[BT][ERR] Bluetooth adapter disabled");
        }
    }
    @Override
    public void RegisterService(ServicesConfig SC) {
        if (ServicesMapping==null)
            ServicesMapping=new ArrayList<>();
            
        ServicesMapping.add(SC);
    }

    private void InitServices() {
        IServiceCallback WorkerCallback = this;

        for (ServicesConfig SC : ServicesMapping) {
            System.out.println("[BT][INF] Check services " + SC.Name);
            if (SC.ServerMode) {
                Thread NS=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //UUID _uuid = new UUID("0000110100001000800000805F9B34FB", false);
                        UUID _uuid = new UUID(SC.ServicesUUID_String[0], false);

                        try {
                            System.out.println("[BT][INF] Init Service " + SC.Name);
                            System.out.println("[BT][INF] Wait connection on btspp://localhost:" + _uuid);
                            StreamConnectionNotifier serverConnection;
                            serverConnection = (StreamConnectionNotifier) Connector.open("btspp://localhost:" + _uuid + "");
                            while (State) {
                                ConnectionWorker.add(new BTConnectionWorker(WorkerCallback, SC.Name,SC.KK_TargetTag, serverConnection.acceptAndOpen()));
                                //
                                // Clean closed connections
                                List<BTConnectionWorker> CR = new ArrayList();
                                for (BTConnectionWorker CW : ConnectionWorker) {
                                    if (!CW.Active) {
                                        CR.add(CW);
                                    }
                                }
                                for (BTConnectionWorker CW : CR) {
                                    ConnectionWorker.remove(CW);
                                }
                                CR.clear();
                            }
                        } catch (IOException ex) {
                           System.out.println("[BT][ERR] " + SC.Name);
                        }
                        System.out.println("[BT][INF] STOP " + SC.Name);
                    }
                });
                BTServer.add(NS);
                NS.start();
            }
        }
    }

    private void InitLocalDevices() {
        for (ServicesConfig SC : ServicesMapping) {
            if (!SC.ServerMode) {
                if (SC.DevType == ServicesConfig.BT_ServiceType.RFCOMM) {
                    out.println("[BT][INF] SVC CONN " + SC.DevAddr);
                    IBTService Svc = null;
                    Svc = new BTServiceRFCOMM();
                    Svc.ConnectService(SC.DevAddr, "btspp://" + SC.DevAddr, this);
                } else {
                    out.println("[BT][INF] Not supported service type detected " + SC.Name);
                }
            }
        }
    }

    

    @Override
    public void StopAdaper() {
        State = false;
    }

    ServerRequestHandler ServerBTEXA = new ServerRequestHandler() {
        @Override
        public int onPut(Operation op) {
            return super.onPut(op); //To change body of generated methods, choose Tools | Templates.
        }

    };

    DiscoveryListener BTDiscovery = new DiscoveryListener() {

        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            System.out.println("[BT]  Discovered " + btDevice);
            //add the device to the vector
            if (!AvailableDevices.containsKey(btDevice.getBluetoothAddress())) {
                AvailableDevices.put(btDevice.getBluetoothAddress(), btDevice);
            }

        }

        @Override
        public void servicesDiscovered(int i, ServiceRecord[] srs) {
            System.out.println("[BT] Disc Comp " + i);
         
        }

        @Override
        public void serviceSearchCompleted(int i, int i1) {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void inquiryCompleted(int i) {
             System.out.println("[BT] Inc Comp " + i);
         
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

    @Override
    public void SendJsonData(String ServiceTag, String Json) {
       for (BTConnectionWorker CN:ConnectionWorker)
       {
           CN.SendData(ServiceTag,Json);
           
       }
    }

    @Override
    public void SetDiscoverableStatus(boolean Discover) {
        if (LD==null)
            return;
        
        try {
            if (Discover) {
                System.out.println("[BT] Discover ON");
                LD.setDiscoverable(DiscoveryAgent.GIAC);
            } else {
                System.out.println("[BT] Discover OFF");
                LD.setDiscoverable(DiscoveryAgent.NOT_DISCOVERABLE);
            }


        } catch (BluetoothStateException ex) {
            Logger.getLogger(BlueCove.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
