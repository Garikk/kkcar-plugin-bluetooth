/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration;

import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_SERVICE_RFCOMM_UUID;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_PLUGIN_EXTCONNECTOR_BTSERVICE_UUID;


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
        DefConf.BTServicesMapping=new ServicesConfig[1];
        DefConf.BTServicesMapping[0]=new ServicesConfig();
        DefConf.BTServicesMapping[0].SourceType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[0].TargetTag="RAWGPS";
        DefConf.BTServicesMapping[0].SourceAddr=""; //any
        DefConf.BTServicesMapping[0].ServicesUUID=new long[1];
        DefConf.BTServicesMapping[0].ServicesUUID[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_SERVICE_RFCOMM_UUID;
        return DefConf;
    }
}
    
