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
import py4j.GatewayServerListener;
import py4j.Py4JServerConnection;
import py4j.reflection.ReflectionUtil;
import py4j.reflection.RootClassLoadingStrategy;

/**
 *
 * @author garikk
 */
public class btpyAdapter implements IBTAdapter, IBTAdapter_callback {

    GatewayServer py4jGateway;
    private static btpyAdapter instance;
    private IBTAdapter_connector py_adapter_conector;

    public btpyAdapter getInstance() {
        if (instance == null) {
            instance = new btpyAdapter();
        }
        return instance;
    }

    GatewayServerListener gw_listener = new GatewayServerListener() {
        @Override
        public void connectionError(Exception excptn) {
            out.println("[BT][INF] py4j conn ERR " + excptn);
        }

        @Override
        public void connectionStarted(Py4JServerConnection pjsc) {
            out.println("[BT][INF] py4j conn started");
        }

        @Override
        public void connectionStopped(Py4JServerConnection pjsc) {
            out.println("[BT][INF] py4j conn stopped");
        }

        @Override
        public void serverError(Exception excptn) {
            out.println("[BT][INF] py4j server error " + excptn);
        }

        @Override
        public void serverPostShutdown() {
            out.println("[BT][INF] py4j server psd ");
        }

        @Override
        public void serverPreShutdown() {
            out.println("[BT][INF] py4j server pre psd ");
        }

        @Override
        public void serverStarted() {
            out.println("[BT][INF] py4j server started ");
        }

        @Override
        public void serverStopped() {
            out.println("[BT][INF] py4j server stopped ");
        }
    };

    @Override
    public void StartAdapter(BTManager BTM) {
        out.println("[BT][INF] Start btpyAdapter");
        if (this.py4jGateway == null) {
            GatewayServer.turnLoggingOff();
            this.py4jGateway = new GatewayServer(this.getInstance());
        }
        RootClassLoadingStrategy rmmClassLoader = new RootClassLoadingStrategy();
        ReflectionUtil.setClassLoadingStrategy(rmmClassLoader);
        this.py4jGateway.addListener(gw_listener);
        this.py4jGateway.start();
        py_adapter_conector = (IBTAdapter_connector) py4jGateway.getPythonServerEntryPoint(new Class[]{IBTAdapter_connector.class});
        py_adapter_conector.RequestNearbyDevices();

        out.println("[BT][INF] Adapter started");

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
       //
    }

    @Override
    public void SendStringData(String ServiceTag, String Json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void SendBytesData(String ServiceTag, byte[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ReceiveDeviceList(Object DevList) {
       out.println(DevList);
    }

    @Override
    public void ReceiveData(String ServiceTag, String DeviceAddr, String Json) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
