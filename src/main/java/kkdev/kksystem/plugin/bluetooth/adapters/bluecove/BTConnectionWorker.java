/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.adapters.bluecove;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.StreamConnection;
import kkdev.kksystem.plugin.bluetooth.services.IServiceCallback;

/**
 *
 * @author sayma_000
 */
public class BTConnectionWorker  {
    IServiceCallback Callback;
    StreamConnection Conn;
    DataInputStream DIS;
    DataOutputStream DOS;
    String SvcName;
    boolean Active;
    
    public BTConnectionWorker(IServiceCallback SvcCallback, String Name,StreamConnection  Connection)
    {
        try {
            out.println("[BT] Received BTEXA connection for " + Name + " service");
            Conn  =Connection;
            
            SvcName=Name;
            DIS=Conn.openDataInputStream();
            DOS=Conn.openDataOutputStream();
            //
            Callback=SvcCallback;
            //
            Active=true;
            //
            Reader.start();
            
        } catch (IOException ex) {
            Logger.getLogger(BTConnectionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void SendData(String Data)
    {
        try {
            DOS.writeUTF(Data);
        } catch (IOException ex) {
            Logger.getLogger(BTConnectionWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    Thread Reader = new Thread(new Runnable() {
            String RData;
            @Override
            public void run() {
              while (Active)
              {
                  try {
                      if (DIS.available()!=0)
                      {
                           out.println("[BT][" + SvcName + "] NDDD " + DIS.available());
                         byte[] R=new byte[DIS.available()];
                         DIS.readFully(R);
                       
                         RData=new String(R);
                        Callback.ReceiveServiceData("BT", SvcName, RData);
                      }
                  } catch (IOException ex) {
                      out.println("[BT][" + SvcName + "] Error " + SvcName);
                      Logger.getLogger(BTConnectionWorker.class.getName()).log(Level.SEVERE, null, ex);
                      Active=false;
                  }
              }
              
                 out.println("[BT][" + SvcName + "] Close reader from " + SvcName);
            }
            
        });
}
