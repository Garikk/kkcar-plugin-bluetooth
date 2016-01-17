package kkdev.kksystem.plugin.bluetooth;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import kkdev.kksystem.base.classes.plugins.PluginInfo;
import kkdev.kksystem.base.classes.plugins.simple.IPluginInfoRequest;
import kkdev.kksystem.base.constants.PluginConsts;

/**
 *
 * @author blinov_is e
 */
public class BTPluginInfo  implements IPluginInfoRequest {
    @Override
    public PluginInfo GetPluginInfo()
    {
        PluginInfo Ret=new PluginInfo();
        
        Ret.PluginName="KKBTConnection";
        Ret.PluginDescription="Basic Bluetooth plugin";
        Ret.PluginVersion=1;
        Ret.Enabled=true;
        Ret.ReceivePins = GetMyReceivePinInfo();
        Ret.TransmitPins = GetMyTransmitPinInfo();
        Ret.PluginUUID="f0ab876b-5403-40d8-86a2-309f337757a9";
        return Ret;
    }
    
    
    private String[] GetMyReceivePinInfo(){
    
        String[] Ret=new String[1];
    

        Ret[0]=PluginConsts.KK_PLUGIN_BASE_PIN_COMMAND;

        
        return Ret;
    }
    private String[] GetMyTransmitPinInfo(){
    
        String[] Ret=new String[1];
        Ret[0] = PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;



        
        return Ret;
    }
    
}
