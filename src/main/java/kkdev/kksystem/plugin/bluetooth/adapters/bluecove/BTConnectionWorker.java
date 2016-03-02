/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bluecove;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.StreamConnection;
import kkdev.kksystem.plugin.bluetooth.services.IServiceCallback;

/**
 *
 * @author sayma_000
 */
public class BTConnectionWorker {

    IServiceCallback Callback;
    StreamConnection Conn;
    DataInputStream DIS;
    DataOutputStream DOS;
    BufferedWriter BW;
    String SvcName;
    boolean Active;
    String svcTag;

    public BTConnectionWorker(IServiceCallback SvcCallback, String Name, String ServiceTag, StreamConnection Connection) {
        try {
            out.println("[BT] Received BTEXA connection for " + Name + " service");
            Conn = Connection;

            svcTag=ServiceTag;
            SvcName = Name;
            DIS = Conn.openDataInputStream();
            DOS = Conn.openDataOutputStream();
            BW = new BufferedWriter(new OutputStreamWriter(DOS));
            //
            Callback = SvcCallback;
            //
            Active = true;
            //
            Reader.start();

        } catch (IOException ex) {
            Logger.getLogger(BTConnectionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SendData(String ServiceTag,String Data) {
        if (!ServiceTag.equals(svcTag))
            return;
        
        try {
            //DOS.writeUTF(Data);
            BW.write(Data);
            BW.newLine();
            BW.flush();
        } catch (IOException ex) {
            Logger.getLogger(BTConnectionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Thread Reader = new Thread(new Runnable() {
        String RData;
        BufferedReader br;

        @Override
        public void run() {
            br = new BufferedReader(new InputStreamReader(DIS));
            while (Active) {
                try {
                    RData = br.readLine();
                    out.println("[BT][" + SvcName + "] Received " + RData);
                    if (RData!=null)
                    {
                        Callback.ReceiveServiceData(SvcName, SvcName, RData);
                    }
                    else
                    {
                        Active=false;
                    }
                } catch (IOException ex) {
                    out.println("[BT][" + SvcName + "] Error " + SvcName);
                    Logger.getLogger(BTConnectionWorker.class.getName()).log(Level.SEVERE, null, ex);
                    Active = false;
                }
            }

            out.println("[BT][" + SvcName + "] Close reader from " + SvcName);
        }

    });
}
