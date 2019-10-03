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
public class btpyAdapter implements IBTAdapter {

    GatewayServer py4jGateway;
    private static btpyAdapter instance;
    private Adapter_Callback py_adapter_callback;
    private IBTAdapter_connector py_adapter_conector;

    class Adapter_Callback implements IBTAdapter_callback {

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

    GatewayServerListener gw_listener = new GatewayServerListener() {
        @Override
        public void connectionError(Exception excptn) {
            out.println("[BT][INF] conn ERR " + excptn);
        }

        @Override
        public void connectionStarted(Py4JServerConnection pjsc) {
            out.println("[BT][INF] conn started");
            py_adapter_conector = (IBTAdapter_connector) py4jGateway.getPythonServerEntryPoint(new Class[]{IBTAdapter_connector.class});
            py_adapter_conector.StopAdaper();
        }
        @Override
        public void connectionStopped(Py4JServerConnection pjsc) {
            out.println("[BT][INF] conn stopped");
        }

        @Override
        public void serverError(Exception excptn) {
            out.println("[BT][INF] server error " + excptn);
        }

        @Override
        public void serverPostShutdown() {
            out.println("[BT][INF] server psd ");
        }

        @Override
        public void serverPreShutdown() {
            out.println("[BT][INF] server pre psd ");
        }

        @Override
        public void serverStarted() {
            out.println("[BT][INF] server started ");
        }

        @Override
        public void serverStopped() {
            out.println("[BT][INF] server stopped ");
        }
    };

    @Override
    public void StartAdapter(BTManager BTM) {
        out.println("[BT][INF] Start btpyAdapter");
        if (this.py4jGateway == null) {
            this.py4jGateway = new GatewayServer(this.getInstance());
        }
        RootClassLoadingStrategy rmmClassLoader = new RootClassLoadingStrategy();
        ReflectionUtil.setClassLoadingStrategy(rmmClassLoader);
        this.py4jGateway.addListener(gw_listener);
        this.py4jGateway.start();
        out.println("[BT][INF] start adapter py4j");

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
        if (this.py_adapter_conector != null) {
            // this.py_adapter_conector.RegisterService(SC);
        }
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
