package kkdev.kksystem.plugin.bluetooth.manager;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import kkdev.kksystem.base.classes.base.PinDataTaggedObj;
import kkdev.kksystem.base.classes.base.PinDataTaggedString;
import kkdev.kksystem.base.classes.controls.PinDataControl;
import kkdev.kksystem.base.classes.notify.NotifyConsts;
import kkdev.kksystem.base.classes.notify.NotifyConsts.NOTIFY_METHOD;
import kkdev.kksystem.base.classes.plugins.PluginMessage;
import kkdev.kksystem.base.classes.plugins.simple.managers.PluginManagerBase;
import kkdev.kksystem.base.constants.PluginConsts;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA;
import static kkdev.kksystem.base.constants.PluginConsts.KK_PLUGIN_BASE_CONTROL_DATA;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.plugin.bluetooth.KKPlugin;
import kkdev.kksystem.plugin.bluetooth.adapters.IBTAdapter;
import kkdev.kksystem.plugin.bluetooth.adapters.bt_py.btpyAdapter;
import kkdev.kksystem.plugin.bluetooth.adapters.tinyb.TinyB;
import kkdev.kksystem.plugin.bluetooth.configuration.BTConfig;
import kkdev.kksystem.plugin.bluetooth.configuration.PluginSettings;
import kkdev.kksystem.plugin.bluetooth.configuration.ServicesConfig;
import kkdev.kksystem.plugin.bluetooth.configuration.confmenu.BTMenu;

public class BTManager extends PluginManagerBase {

    String BT_PINFILE = "btpin";

    private IBTAdapter Adapter;
    private BTMenu BTSettingsMenu;
    KKPlugin LocalConnector;

    public void Init(KKPlugin Conn) {
        setPluginConnector(Conn);
        LocalConnector = Conn;
        //Init Adapters and start scan and connect
        this.currentFeature.put(SystemConsts.KK_BASE_UICONTEXT_DEFAULT, PluginSettings.MainConfiguration.FeatureID);
        BTSettingsMenu = new BTMenu();
        //
        ConfigAndInitHW();
        //
    }

    public void Start() {
        BTSettingsMenu.initBTMenu(LocalConnector.GetUtils());
    }

    private void ConfigAndInitHW() {
        //Init HW adapter
        if (PluginSettings.MainConfiguration.BTAdapter == BTConfig.AdapterTypes.TinyB) {
            Adapter = new TinyB();
        } else if (PluginSettings.MainConfiguration.BTAdapter == BTConfig.AdapterTypes.Python_Bluetooh) {
            Adapter = new btpyAdapter();
        }
        //Set up services
        for (ServicesConfig SVC : PluginSettings.MainConfiguration.BTServicesMapping) {
            Adapter.RegisterService(SVC);
        }
        Adapter.StartAdapter(this);

    }

    public void MGMT_ChangeDiscoverState(boolean Discover, String PIN) {
        SetPairingPIN(PIN);
        Adapter.SetDiscoverableStatus(Discover);
        NOTIFY_METHOD[] NM = new NOTIFY_METHOD[1];
        NM[0] = NOTIFY_METHOD.VOICE;
        String[] Tag = new String[2];
        Tag[0] = "BT_VISIBLE_STATE_CHANGE";
        Tag[1] = "BLUETOOTH";
        if (Discover) {
            this.NOTIFY_SendNotifyMessage(PluginSettings.MainConfiguration.FeatureID, NotifyConsts.NOTIFY_TYPE.SYSTEM_INFO, NM, Tag, "Bluetooth is visible", null);
        } else {
            this.NOTIFY_SendNotifyMessage(PluginSettings.MainConfiguration.FeatureID, NotifyConsts.NOTIFY_TYPE.SYSTEM_INFO, NM, Tag, "Bluetooth is invisible", null);
        }
    }

    public void BT_DiscoveredDevices(String[] devices) {
        NOTIFY_METHOD[] NM = new NOTIFY_METHOD[1];
        NM[0] = NOTIFY_METHOD.INFO;
        String[] Tag = new String[2];
        Tag[0] = "BT_VISIBLE_STATE_CHANGE";
        Tag[1] = "BLUETOOTH";
        this.NOTIFY_SendNotifyMessage(PluginSettings.MainConfiguration.FeatureID, NotifyConsts.NOTIFY_TYPE.SYSTEM_INFO, NM, Tag, "BT Devices", devices);
    }

    public void BT_ReceiveData(String Tag, String Data) {
        PinDataTaggedString ObjDat;
        ObjDat = new PinDataTaggedString();
        ObjDat.tag = Tag;
        ObjDat.value = Data;

        this.BASE_SendPluginMessage(SystemConsts.KK_BASE_FEATURES_SYSTEM_MULTIFEATURE_UID, SystemConsts.KK_BASE_UICONTEXT_DEFAULT, PluginConsts.KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA, ObjDat);
    }

    public void ReceivePIN(PluginMessage Msg) {
        if (Msg.pinName.equals(KK_PLUGIN_BASE_BASIC_TAGGEDOBJ_DATA)) {
            PinDataTaggedObj PIN = (PinDataTaggedObj) Msg.getPinData();
            //        Adapter.SendJsonData(PIN.tag, (String) PIN.value);
        } else if (Msg.pinName.equals(KK_PLUGIN_BASE_CONTROL_DATA)) {
            if (Msg.FeatureID.contains(PluginSettings.MainConfiguration.FeatureID)) {
                BTSettingsMenu.processControlPIN((PinDataControl) Msg.getPinData());
            }
        }
    }

    private void SetPairingPIN(String PIN) {
        try {
            FileWriter fw;
            fw = new FileWriter(SystemConsts.KK_BASE_CONFPATH + "/" + BT_PINFILE);
            fw.write("* " + PIN);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            System.out.println("[BT][ERR] Error on create/update PINFILE");
        }
    }
}
