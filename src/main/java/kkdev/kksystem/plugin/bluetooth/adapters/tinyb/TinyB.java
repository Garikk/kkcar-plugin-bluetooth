/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.tinyb;

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
import java.util.*;
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;
import org.sputnikdev.bluetooth.manager.BluetoothManager;
import org.sputnikdev.bluetooth.manager.impl.BluetoothManagerBuilder;

/**
 *
 * @author sayma_000
 */
public class TinyB implements IBTAdapter, IServiceCallback {

    private List<Thread> BTServer;
    private BluetoothManager TinyBManager;
    private HashMap<String, Object> AvailableDevices;
    private List<ServicesConfig> ServicesMapping;
    private HashMap<String, IBTService> BTServices;
    //private BluetoothAdapter LD;
    private BluetoothManager TM;
    private List<BTConnectionWorker> ConnectionWorker;
    BTManager BTM;

    public TinyB()
    {
        /*
         BluetoothManager manager = new BluetoothManagerBuilder()
                .withTinyBTransport(true)
                .withBlueGigaTransport("^*.$")
                .build();
        
        
        
        BluetoothGattService gs;
        out.println("[BT][INF] Init");
        TM=BluetoothManager.getBluetoothManager();
        LD = TM.getAdapters().get(0);
        out.println("[BT][INF] Use: " + LD.getName() + " (" + LD.getAddress() + ")");
        out.println("[BT][INF] Init Ok");
        LD.startDiscovery();
        for (var i=1;i<3;i++)
        {
            if (LD.getDiscovering()){
                out.println("[BT][INF] "+ LD.getDevices());
                for (BluetoothDevice d: LD.getDevices())
                {
                    out.println(d.getAddress());
                    if (d.getAddress().equals("78:02:F8:12:E0:56"))
                    {
                        out.println("[BT][INF] CNN");
                       // if (!d.getConnected())
                       // {
                      //      d.connect();
                       // }

                        out.println("DEVNAME "+ d.getName() + " " + d.getAddress());
                        out.println("SVCS "+ d.getServices());
                        for (String uu: d.getUUIDs())
                        {
                            out.println("uid "+ uu);
                        }
                        
                        out.println("paired " +d.getPaired());
                        out.println("connectrd " +d.getConnected());
                       // d.disconnect();
                       
                    }
                    
                }
            }
            else
            {
                    out.println("[BT][INF] Not desc");
            }
            try {
            Thread.sleep(2000);
            }
            catch (Exception ex)
            {
                    out.println("[BT][INF] " + ex);
            }
        }
        LD.stopDiscovery();
        */
    }
    @Override
    public void StartAdapter(BTManager MyBTM) {
        
        
        BTM = MyBTM;
        AvailableDevices = new HashMap<>();
        BTServices = new HashMap<>();
        ConnectionWorker = new ArrayList<>();
        //
        BTServer = new ArrayList<>();
        //
       // try {
            //State = true;
            // Init Services
            InitServices();
            //
            //Init local devices
            InitLocalDevices();
            //
       // } catch (BluetoothStateException ex) {
         //   State = false;
          //  out.println("[BT][ERR]" + ex.getMessage());
          //  out.println("[BT][ERR] Bluetooth adapter disabled");
       // }

    }

    @Override
    public void RegisterService(ServicesConfig SC) {
        if (ServicesMapping == null) {
            ServicesMapping = new ArrayList<>();
        }

        ServicesMapping.add(SC);
    }

    private void InitServices() {

        IServiceCallback WorkerCallback = this;
        for (ServicesConfig SC : ServicesMapping) {
            System.out.println("[BT][INF] Check services " + SC.Name);
            if (SC.ServerMode) {
                /*
                Thread NS = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //UUID _uuid = new UUID("0000110100001000800000805F9B34FB", false);
                        String _uuid = SC.ServicesUUID_String[0];

                        try {
                            System.out.println("[BT][INF] Init Service " + SC.Name);
                            System.out.println("[BT][INF] Wait connection on btspp://localhost:" + _uuid);
                            StreamConnectionNotifier serverConnection;
                            serverConnection = (StreamConnectionNotifier) Connector.open("btspp://localhost:" + _uuid + "");
                            while (State) {
                                ConnectionWorker.add(new BTConnectionWorker(WorkerCallback, SC.Name, SC.KK_TargetTag, serverConnection.acceptAndOpen()));
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
            */
            }
        
        }

    }

    private void InitLocalDevices() {
        for (var SC : ServicesMapping) {
            if (!SC.ServerMode) {
                if (SC.DevType == RFCOMM) {
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
        var state = false;
    }

    @Override
    public void SetDiscoverableStatus(boolean Discover) {
       // if (LD == null) {
      //  }
      //  LD.setDiscoverable(Discover);
        if (Discover) {
            System.out.println("[BT] Discover ON");
        } else {
            System.out.println("[BT] Discover OFF");
        }
    }

    @Override
    public boolean State() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    //@Override
    //public void SendJsonData(String ServiceTag, String Json) {
//       for (BTConnectionWorker CN:ConnectionWorker)
//       {
//           CN.SendData(ServiceTag,Json);
           
//       }
  //  }

    @Override
    public void ReceiveServiceData(String Tag, String SrcAddr, Byte[] Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveServiceData(String Tag, String SrcAddr, String Data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendStringData(String ServiceTag, String Json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendBytesData(String ServiceTag, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
/*
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



    
}
*/
