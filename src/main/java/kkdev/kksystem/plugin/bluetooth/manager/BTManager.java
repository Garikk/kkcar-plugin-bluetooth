package kkdev.kksystem.plugin.bluetooth.manager;

import java.io.FileWriter;
import java.io.IOException;
import kkdev.kksystem.base.classes.base.PinBaseData;
import kkdev.kksystem.base.classes.base.PinBaseDataTaggedObj;
import kkdev.kksystem.base.classes.controls.PinControlData;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerBase;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.plugin.bluetooth.KKPlugin;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.adapters.bluecove.BlueCove;
import kkdev.kksystem.plugin.bluetooth.configuration.BTConfig;
import kkdev.kksystem.plugin.bluetooth.configuration.PluginSettings;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import kkdev.kksystem.plugin.bluetooth.configuration.confmenu.BTMenu;

public class BTManager extends PluginManagerBase {

    String BT_PINFILE="btpin";
    
    private IBTAdapter Adapter;
    private BTMenu BTSettingsMenu;
    KKPlugin LocalConnector;

    public void Init(KKPlugin Conn) {
        this.Connector = Conn;
        LocalConnector=Conn;
        //Init Adapters and start scan and connect
        this.CurrentFeature.put(SystemConsts.KK_BASE_UICONTEXT_DEFAULT,PluginSettings.MainConfiguration.FeatureID);
        BTSettingsMenu=new BTMenu();
        //
        ConfigAndInitHW();
        //
    }
    public void Start() {
        BTSettingsMenu.InitBTMenu(LocalConnector.GetUtils());
    }
    private void ConfigAndInitHW() {
        //Init HW adapter
        if (PluginSettings.MainConfiguration.BTAdapter == BTConfig.AdapterTypes.BlueCove) {
            Adapter = new BlueCove();
            //Set up services
            for (ServicesConfig SVC : PluginSettings.MainConfiguration.BTServicesMapping) {
                Adapter.RegisterService(SVC);
            }
            Adapter.StartAdapter(this);
        }
    }
    
    public void MGMT_ChangeDiscoverState(boolean Discover,String PIN)
    {
        SetPairingPIN(PIN);
        Adapter.SetDiscoverableStatus(Discover);
    }
    
    
    public void BT_ReceiveData(String Tag, String Data)
    {
        PinBaseDataTaggedObj ObjDat;
        ObjDat=new PinBaseDataTaggedObj();
        ObjDat.DataType=PinBaseData.BASE_DATA_TYPE.TAGGED_OBJ;
        ObjDat.Tag=Tag;
        ObjDat.Value=Data;
        
        this.BASE_SendPluginMessage(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID,PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA,ObjDat);
    }
    
    public void ReceivePIN(PluginMessage Msg)
    {
        if (Msg.PinName.equals(KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA))
        {
            PinBaseDataTaggedObj PIN=(PinBaseDataTaggedObj)Msg.PinData;
            Adapter.SendJsonData(PIN.Tag,(String)PIN.Value);
        }
        else if (Msg.PinName.equals(KK_PLUGIN_BASE_CONTROL_DATA))
        {
             BTSettingsMenu.ProcessControlPIN((PinControlData)Msg.PinData);
        }
        
    }
    
    private void SetPairingPIN(String PIN) 
    {
        try
        {
            FileWriter fw;
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/" + BT_PINFILE);
            fw.write("* " +PIN);
            fw.flush();
            fw.close();
        }
        catch (IOException ex)
        {
            System.out.println("[BT][ERR] Error on create/update PINFILE");
        }

    
    }
    

}
