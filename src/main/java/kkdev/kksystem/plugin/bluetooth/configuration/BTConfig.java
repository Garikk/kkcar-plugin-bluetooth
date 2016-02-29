/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration;

import kkdev.kksystem.base.classes.plugins.ExternalConfiguration;

/**
 *
 * @author blinov_is
 */
public class BTConfig  extends ExternalConfiguration {
    public enum AdapterTypes {
        BlueCove
    }
    
    public AdapterTypes BTAdapter;
    public ServicesConfig[] BTServicesMapping;

}
