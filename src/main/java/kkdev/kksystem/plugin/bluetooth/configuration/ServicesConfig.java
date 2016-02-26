/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration;

/**
 *
 * @author sayma_000
 */
public class ServicesConfig {
    public enum BT_ServiceType
    {
        KK_EXA_CONNECTOR,
        RFCOMM,
        CUSTOM
    }
    
    public String Name;
    public String DevAddr;
    public BT_ServiceType DevType;
    public String KK_TargetTag;
    public long[] ServicesUUID_long=new long[0];
    public String[] ServicesUUID_String=new String[0];
    public boolean ServerMode;

}
