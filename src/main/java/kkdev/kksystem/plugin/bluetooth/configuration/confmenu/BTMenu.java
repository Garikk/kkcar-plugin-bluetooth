/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.configuration.confmenu;

import static java.lang.System.out;
import kkdev.kksystem.base.classes.controls.PinControlData;
import static kkdev.kksystem.base.classes.controls.PinControlData.DEF_BTN_BACK;
import kkdev.kksystem.base.classes.display.tools.menumaker.MKMenuItem;
import kkdev.kksystem.base.classes.display.tools.menumaker.MenuMaker;
import kkdev.kksystem.base.constants.SystemConsts;
import kkdev.kksystem.plugin.bluetooth.Global;

/**
 *
 * @author blinov_is
 */
public class BTMenu {

    MenuMaker MMaker;
    boolean Discover = true;
    MKMenuItem[] MenuItems;

    public void InitBTMenu() {
        MMaker = new MenuMaker(Global.PM.CurrentFeature.get(SystemConsts.KK_BASE_UICONTEXT_DEFAULT), null, Global.PM.Connector, MenuItemExec);
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
        if (Discover) {
            MenuItems[0].DisplayName = "Discoverable On";
        } else {
            MenuItems[0].DisplayName = "Discoverable Off";
        }
        //
        MenuItems[0].ItemCommand = "DISC " + !Discover;
        //
        MenuItems[1] = new MKMenuItem();
        MenuItems[1].DisplayName = "=====";
        MenuItems[1].ItemCommand = "";
    }

    private MenuMaker.IMenuMakerItemSelected MenuItemExec = new MenuMaker.IMenuMakerItemSelected() {

        @Override
        public void SelectedItem(String ItemCMD) {
            if ("DISC true".equals(ItemCMD)) {
                Discover = true;
                Global.PM.MGMT_ChangeDiscoverState(Discover);
            } else if ("DISC false".equals(ItemCMD)) {
                Discover = false;
                Global.PM.MGMT_ChangeDiscoverState(Discover);
            }
            //
            UpdateMenuItems();
            //
            MMaker.UpdateMenuItems(MenuItems, false);
            // 
        }
    };

    public void ProcessControlPIN(PinControlData ControlData) {
        MMaker.ProcessControlCommand(ControlData.ControlID);

    }
    

}
