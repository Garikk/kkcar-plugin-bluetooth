/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration;

import kkdev.kksystem.base.classes.plugins.simple.SettingsManager;

/**
 *
 * @author blinov_is
 */
public abstract class PluginSettings {

   public static  String BT_CONF;
   private static SettingsManager Settings;

    public static BTConfig MainConfiguration;

    public static void InitConfig(String GlobalConfigUID, String MyUID) {
         BT_CONF=GlobalConfigUID+"_"+MyUID + ".json";
        
        Settings=new SettingsManager(BT_CONF,BTConfig.class);
        
        
        System.out.println("[BT][CONFIG] Load configuration");
        MainConfiguration=(BTConfig)Settings.LoadConfig();

        if (MainConfiguration == null) {
            System.out.println("[BT][CONFIG] Error Load configuration, try create default config");
            Settings.SaveConfig(kk_DefaultConfig.MakeDefaultConfig());
            MainConfiguration=(BTConfig)Settings.LoadConfig();
        }
        if (MainConfiguration == null) {
            System.out.println("[BT][CONFIG] Load configuration, fatal");
            return;
        }
        //
    }
}
