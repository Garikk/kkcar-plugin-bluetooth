/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration;

import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_KKEXCONNECTION_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_BTSERVICE_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;


/**
 *
 * @author blinov_is
 *

 */
public abstract class kk_DefaultConfig {
    public static BTConfig MakeDefaultConfig() {
        
        BTConfig DefConf = new BTConfig();
        
        DefConf.ODBAdapter=BTConfig.AdapterTypes.BlueCove;
        // 
        DefConf.BTServicesMapping=new ServicesConfig[2];
        DefConf.BTServicesMapping[0]=new ServicesConfig();
        DefConf.BTServicesMapping[0].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[0].TargetTag="RAWGPS";
        DefConf.BTServicesMapping[0].DevAddr="00154B10060F:1"; //change this! (this is my test adapter
        DefConf.BTServicesMapping[0].lServicesUUID=new long[1];
        DefConf.BTServicesMapping[0].lServicesUUID[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[0].ServerMode=false;
        //
        DefConf.BTServicesMapping[1]=new ServicesConfig();
        DefConf.BTServicesMapping[1].DevType=ServicesConfig.BT_ServiceType.KK_EXCONNECTOR;
        DefConf.BTServicesMapping[1].TargetTag="EXCONNECTOR";
        DefConf.BTServicesMapping[1].DevAddr=""; //any
        DefConf.BTServicesMapping[1].ServerMode=true; //any
        DefConf.BTServicesMapping[1].sServicesUUID=new String[1];
        DefConf.BTServicesMapping[1].sServicesUUID[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_KKEXCONNECTION_UUID;
        return DefConf;
    }
}
    
