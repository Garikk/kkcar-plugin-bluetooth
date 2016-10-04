package kkdev.kksystem.plugin.bluetooth;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import kkdev.kksystem.base.classes.plugins.PluginConfiguration;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.KKPluginBase;
import kkdev.kksystem.plugin.bluetooth.configuration.PluginSettings;
import kkdev.kksystem.plugin.bluetooth.manager.BTManager;
import kkdev.kksystem.base.interfaces.IControllerUtils;
import kkdev.kksystem.base.interfaces.IBaseConnection;

/**
 *
 * @author blinov_is
 */
public final class KKPlugin extends KKPluginBase {
    IControllerUtils SysUtils;
    public KKPlugin() {
        super(new BTPluginInfo());
        Global.PM=new BTManager();
    }

    @Override
    public void pluginInit(IBaseConnection BaseConnector, String GlobalConfUID) {
        super.pluginInit(BaseConnector, GlobalConfUID);
        SysUtils=BaseConnector.systemUtilities();
        PluginSettings.InitConfig(this.globalConfID, this.pluginInfo.getPluginInfo().PluginUUID);
        Global.PM.Init(this);
    }

    
    @Override
    public void executePin(PluginMessage Pin) {
        super.executePin(Pin);
       // System.out.println("[BT][DEBUG] " +Pin.PinData );
        Global.PM.ReceivePIN(Pin);
        return;
    }
    
    
     @Override
    public void pluginStart() {
         super.pluginStart();
         Global.PM.Start();
    }
    
    public IControllerUtils GetUtils()
    {
        return SysUtils;
    }
     @Override
    public PluginConfiguration getPluginSettings() {
       return PluginSettings.MainConfiguration;
    }

}
