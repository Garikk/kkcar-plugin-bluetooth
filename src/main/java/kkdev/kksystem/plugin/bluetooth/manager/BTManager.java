package kkdev.kksystem.plugin.bluetooth.manager;

import static java.lang.System.out;
import java.util.ArrayList;
import kkdev.kksystem.base.classes.base.PinBaseDataTaggedObj;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerBase;
import kkdev.kksystem.base.constants.PluginConsts;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.plugin.bluetooth.KKPlugin;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.adapters.bluecove.BlueCove;
import kkdev.kksystem.plugin.bluetooth.configuration.BTConfig;
import kkdev.kksystem.plugin.bluetooth.configuration.PluginSettings;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;

public class BTManager extends PluginManagerBase {

    private IBTAdapter Adapter;

    public void Start(KKPlugin Conn) {
        this.Connector = Conn;
        //Init Adapters and start scan and connect
        PluginSettings.InitConfig(Conn.GlobalConfID, Conn.PluginInfo.GetPluginInfo().PluginUUID);
        //
        ConfigAndInitHW();
        //
    }

    private void ConfigAndInitHW() {
        //Init HW adapter
        if (PluginSettings.MainConfiguration.ODBAdapter == BTConfig.AdapterTypes.BlueCove) {
            Adapter = new BlueCove();
            //Set up services
            for (ServicesConfig SVC : PluginSettings.MainConfiguration.BTServicesMapping) {
                Adapter.RegisterService(SVC);
            }
            Adapter.StartAdapter(this);
        }
       
    }
    
    public void BT_ReceiveData(String Tag, String Data)
    {
        out.println("[BT][RCV] " +Data);
          
        PinBaseDataTaggedObj RecDat;
        
        RecDat=new PinBaseDataTaggedObj();
        RecDat.FeatureUID=SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID;
        RecDat.Tag=Tag;
        RecDat.Value=Data;
        
        this.BASE_SendPluginMessage(this.CurrentFeature,PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA,Data);
    }
    
    
    

}
