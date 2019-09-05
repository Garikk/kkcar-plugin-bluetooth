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
        DefConf.BTServicesMapping=new ServicesConfig[10];
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
        
        //
        DefConf.BTServicesMapping[2]=new ServicesConfig();
        DefConf.BTServicesMapping[2].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[2].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[2].Name="RADAR_SENSOR_1";
        DefConf.BTServicesMapping[2].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[2].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[2].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[2].ServerMode=false;
        DefConf.BTServicesMapping[2].Name="RADAR_SENSOR_1";

        DefConf.BTServicesMapping[3]=new ServicesConfig();
        DefConf.BTServicesMapping[3].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[3].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[3].Name="RADAR_SENSOR_2";
        DefConf.BTServicesMapping[3].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[3].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[3].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[3].ServerMode=false;
        DefConf.BTServicesMapping[3].Name="RADAR_SENSOR_2";

        DefConf.BTServicesMapping[4]=new ServicesConfig();
        DefConf.BTServicesMapping[4].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[4].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[4].Name="RADAR_SENSOR_3";
        DefConf.BTServicesMapping[4].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[4].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[4].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[4].ServerMode=false;
        DefConf.BTServicesMapping[4].Name="RADAR_SENSOR_3";

        DefConf.BTServicesMapping[5]=new ServicesConfig();
        DefConf.BTServicesMapping[5].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[5].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[5].Name="RADAR_SENSOR_4";
        DefConf.BTServicesMapping[5].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[5].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[5].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[5].ServerMode=false;
        DefConf.BTServicesMapping[5].Name="RADAR_SENSOR_4";

        DefConf.BTServicesMapping[6]=new ServicesConfig();
        DefConf.BTServicesMapping[6].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[6].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[6].Name="RADAR_SENSOR_5";
        DefConf.BTServicesMapping[6].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[6].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[6].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[6].ServerMode=false;
        DefConf.BTServicesMapping[6].Name="RADAR_SENSOR_5";

        DefConf.BTServicesMapping[7]=new ServicesConfig();
        DefConf.BTServicesMapping[7].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[7].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[7].Name="RADAR_SENSOR_6";
        DefConf.BTServicesMapping[7].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[7].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[7].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[7].ServerMode=false;
        DefConf.BTServicesMapping[7].Name="RADAR_SENSOR_6";
        
        DefConf.BTServicesMapping[8]=new ServicesConfig();
        DefConf.BTServicesMapping[8].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[8].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[8].Name="RADAR_SENSOR_7";
        DefConf.BTServicesMapping[8].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[8].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[8].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[8].ServerMode=false;
        DefConf.BTServicesMapping[8].Name="RADAR_SENSOR_7";

        DefConf.BTServicesMapping[9]=new ServicesConfig();
        DefConf.BTServicesMapping[9].DevType=ServicesConfig.BT_ServiceType.RFCOMM;
        DefConf.BTServicesMapping[9].KK_TargetTag="RADAR";
        DefConf.BTServicesMapping[9].Name="RADAR_SENSOR_9";
        DefConf.BTServicesMapping[9].DevAddr=""; //RADAR
        DefConf.BTServicesMapping[9].ServicesUUID_long=new long[1];
        DefConf.BTServicesMapping[9].ServicesUUID_long[0]=KK_PLUGIN_BASE_PLUGIN_BLUETOOTH_BTSERVICE_RFCOMM_UUID;
        DefConf.BTServicesMapping[9].ServerMode=false;
        DefConf.BTServicesMapping[9].Name="RADAR_SENSOR_9";

        return DefConf;
    }
}
    
