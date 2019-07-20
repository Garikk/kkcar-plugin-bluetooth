/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration;

import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_KKEXCONNECTION_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;


/**
 *
 * @author blinov_is
 *
 * 
 */
public abstract class kk_DefaultConfig {
    public static BTConfig MakeDefaultConfig() {
        
        BTConfig DefConf = new BTConfig();
        
        DefConf.BTAdapter=BTConfig.AdapterTypes.PythonAdapter;
        // 
        DefConf.BTServicesMapping=new ServicesConfig[2];
        DefConf.BTServicesMapping[0]=new ServicesConfig();
        DefConf.BTServicesMapping[0].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[0].KK_TargetTag="RAWGPS";
        DefConf.BTServicesMapping[0].DevAddr="00154B10060F:1"; //change this! (this is my test adapter
        DefConf.BTServicesMapping[0].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[0].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[0].ServerMode=false;
        DefConf.BTServicesMapping[0].Name="BTGPSAdapter";
        
        
        //
        DefConf.BTServicesMapping[1]=new ServicesConfig();
        DefConf.BTServicesMapping[1].DevType=ServicesConfig.BT_ServiceType.KK_EXA_CONNECTOR;
        DefConf.BTServicesMapping[1].KK_TargetTag="BTEXACONNECTOR";
        DefConf.BTServicesMapping[1].Name="BTEXACONNECTOR";
        DefConf.BTServicesMapping[1].DevAddr=""; //any
        DefConf.BTServicesMapping[1].ServerMode=true; //any
        DefConf.BTServicesMapping[1].ServicesUUID_String=new String[1];
        DefConf.BTServicesMapping[1].ServicesUUID_String[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_KKEXCONNECTION_UUID;
        return DefConf;
    }
}
    
