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
        KK_EXCONNECTOR,
        RFCOMM,
        CUSTOM
    }
    
    public String DevAddr;
    public BT_ServiceType DevType;
    public String TargetTag;
    public long[] ServicesUUID;
    public boolean ServerMode;
}
