/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bt_py;

import static java.lang.System.out;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import kkdev.kksystem.plugin.bluetooth.manager.BTManager;
import py4j.GatewayServer;

/**
 *
 * @author garikk
 */
public class btpyAdapter implements IBTAdapter  {

    GatewayServer py4jGateway;
    private static btpyAdapter instance;
    private Adapter_Callback py_adapter_callback;
    private IBTAdapter_connector py_adapter_conector;

    class Adapter_Callback implements IBTAdapter_callback
    {

        @Override
        public void ReceiveData(String ServiceTag, String DeviceAddr, String Json) {
            out.println(ServiceTag);
        }
    }
    
    public btpyAdapter getInstance() {
        if (instance == null) {
            instance = new btpyAdapter();
        }
        return instance;
    }
    
    @Override
    public void StartAdapter(BTManager BTM) {
        if (this.py4jGateway != null) {
            this.py4jGateway = new GatewayServer(this.getInstance());
        }
        this.py4jGateway.start();
        this.py_adapter_conector = (IBTAdapter_connector) this.py4jGateway.getPythonServerEntryPoint(new Class[] { IBTAdapter_connector.class });
        this.py_adapter_conector.StartAdapter();

    }

    @Override
    public void StopAdaper() {
        this.py_adapter_conector.StopAdaper();
        this.py4jGateway.shutdown();
    }

    @Override
    public void SetDiscoverableStatus(boolean Discover) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean State() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void RegisterService(ServicesConfig SC) {
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
