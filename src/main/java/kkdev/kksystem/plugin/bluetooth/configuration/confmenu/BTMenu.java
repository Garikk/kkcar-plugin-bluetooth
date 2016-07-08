/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration.confmenu;

import java.util.Random;
import kkdev.kksystem.base.classes.controls.PinDataControl;
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
    String DiscoverPin = "----";
    MKMenuItem[] MenuItems;

    public void initBTMenu(IKKControllerUtils Utils) {
        MMaker = new MenuMaker(Utils, Global.PM.currentFeature.get(SystemConsts.KK_BASE_UICONTEXT_DEFAULT), SystemConsts.KK_BASE_UICONTEXT_DEFAULT, null, Global.PM.getPluginConnector(), MenuItemExec, true);
//
        makeDiscoverPIN();
//
        updateMenuItems();
        //
        MMaker.addMenuItems(MenuItems);
        //
        MMaker.showMenu();
    }

    private void updateMenuItems() {
        MenuItems = new MKMenuItem[2];
        MenuItems[0] = new MKMenuItem();
        MenuItems[0].displayName = "Visibility";
        MenuItems[0].itemCommand = KK_MENUMAKER_SPECIALCMD_SUBMENU + " DISC on";//"DISC " + !Discover;
        MenuItems[0].itemBackFromSubItemCommand = "DISC off";
        MenuItems[0].subItems = new MKMenuItem[2];
        MenuItems[0].subItems[0] = new MKMenuItem();
        MenuItems[0].subItems[0].displayName = "BT Visible";
        //
        MenuItems[0].subItems[1] = new MKMenuItem();
        MenuItems[0].subItems[1].displayName = "PIN: " + DiscoverPin;
        //
        MenuItems[1] = new MKMenuItem();
        MenuItems[1].displayName = "Devices";
        MenuItems[1].itemCommand = "";
    }

    private MenuMaker.IMenuMakerItemSelected MenuItemExec = new MenuMaker.IMenuMakerItemSelected() {

        @Override
        public void selectedItem(String ItemCMD) {
            if ("DISC on".equals(ItemCMD)) {
                Discover = true;
                Global.PM.MGMT_ChangeDiscoverState(Discover, DiscoverPin);
            } else if ("DISC off".equals(ItemCMD)) {
                Discover = false;
                Global.PM.MGMT_ChangeDiscoverState(Discover, DiscoverPin);
            }
            //
            // 
        }

        @Override
        public void stepBack(String ItemCMD) {
            selectedItem(ItemCMD);
        }

        @Override
        public void activeMenuElement(String ItemText, String ItemCMD) {
            //not used         
        }

    };

    public void processControlPIN(PinDataControl ControlData) {
        MMaker.processControlCommand(ControlData.controlID);

    }

    private void makeDiscoverPIN() {
        DiscoverPin = String.valueOf((new Random()).nextInt(9999));
        while (DiscoverPin.length() < 4) {
            DiscoverPin = "0" + DiscoverPin;
        }
    }
}
////////// PAIRING!!
////////// bt-agent -c DisplayOnly -p /srv/etc/btpin > btlog.log &
