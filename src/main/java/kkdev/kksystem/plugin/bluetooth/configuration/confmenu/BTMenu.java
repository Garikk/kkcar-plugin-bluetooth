/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration.confmenu;

import static java.lang.System.out;
import java.util.Random;
import kkdev.kksystem.base.classes.controls.PinControlData;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_BACK;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import static kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker.KK_MENUMAKER_SPECIALCMD_SUBMENU;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.base.interfaces.IKKControllerUtils;
import kkdev.kksystem.plugin.bluetooth.Global;

/**
 *
 * @author blinov_is
 */
public class BTMenu {

    MenuMaker MMaker;
    boolean Discover = false;
    String DiscoverPin="----";
    MKMenuItem[] MenuItems;

    public void InitBTMenu(IKKControllerUtils Utils) {
        MMaker = new MenuMaker(Utils, Global.PM.CurrentFeature.get(SystemConsts.KK_BASE_UICONTEXT_DEFAULT), SystemConsts.KK_BASE_UICONTEXT_DEFAULT, null, Global.PM.Connector, MenuItemExec);
//
     MakeDiscoverPIN();
//
        UpdateMenuItems();
        //
        MMaker.AddMenuItems(MenuItems);
        //
        MMaker.ShowMenu();
    }

    private void UpdateMenuItems() {
        MenuItems = new MKMenuItem[2];
        MenuItems[0] = new MKMenuItem();
        MenuItems[0].DisplayName = "Visibility";
        MenuItems[0].ItemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU + " DISC on";//"DISC " + !Discover;
        MenuItems[0].ItemBackFromSubItemCommand="DISC off";
        MenuItems[0].SubItems=new MKMenuItem[2];
        MenuItems[0].SubItems[0]=new MKMenuItem();
        MenuItems[0].SubItems[0].DisplayName="BT Visible";
        //
        MenuItems[0].SubItems[1]=new MKMenuItem();
        MenuItems[0].SubItems[1].DisplayName="PIN: " + DiscoverPin;
        //
        MenuItems[1] = new MKMenuItem();
        MenuItems[1].DisplayName = "Devices";
        MenuItems[1].ItemCommand = "";
    }

    private MenuMaker.IMenuMakerItemSelected MenuItemExec = new MenuMaker.IMenuMakerItemSelected() {

        @Override
        public void SelectedItem(String ItemCMD) {
            if ("DISC on".equals(ItemCMD)) {
                Discover = true;
                Global.PM.MGMT_ChangeDiscoverState(Discover,DiscoverPin);
            } else if ("DISC off".equals(ItemCMD)) {
                Discover = false;
                Global.PM.MGMT_ChangeDiscoverState(Discover,DiscoverPin);
            }
            //
            // 
        }

        @Override
        public void StepBack(String ItemCMD) {
            SelectedItem(ItemCMD);
        }


    };

    public void ProcessControlPIN(PinControlData ControlData) {
        MMaker.ProcessControlCommand(ControlData.ControlID);

    }
    

    private void MakeDiscoverPIN()
    {
       DiscoverPin = String.valueOf((new Random()).nextInt(9999));
        while (DiscoverPin.length() < 4) {
            DiscoverPin = "0" + DiscoverPin;
        }
    }
}
////////// PAIRING!!
////////// bt-agent -c DisplayOnly -p /srv/etc/btpin > btlog.log &