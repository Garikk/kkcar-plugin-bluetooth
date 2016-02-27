/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.services.rfcomm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import kkdev.kksystem.plugin.bluetooth.services.IServiceCallback;
import kkdev.kksystem.plugin.bluetooth.services.IBTService;

/**
 *
 * @author sayma_000
 */
public class BTServiceRFCOMM implements IBTService {

    private String ServiceURL;
    private boolean Stop = false;
    private String MyTag;

    @Override
    public void ConnectService(String Tag,String ConnectionURL, IServiceCallback Callback) {

        Thread ServiceReader = new Thread(new Runnable() {
            public void run() {
                while (!Stop) {
                    try {
                        out.println("[BT][INF] SVC DEV " +Tag + " " +ConnectionURL);
                        ServiceURL = ConnectionURL;
                        StreamConnection streamConnection = null;
                        streamConnection = (StreamConnection) Connector.open(ServiceURL);
                        InputStream inStream = streamConnection.openInputStream();
                        BufferedReader bReader2 = new BufferedReader(new InputStreamReader(inStream));

                        while (!Stop) {
                            String lineRead = bReader2.readLine();
                            Callback.ReceiveServiceData(MyTag,lineRead, lineRead);
                        }
                        //
                        if (Stop) {
                            streamConnection.close();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(BTServiceRFCOMM.class.getName()).log(Level.SEVERE, null, ex);
                        Stop=true;
                    }
                }

            }
        });

        ServiceReader.start();

    }

    @Override
    public void StopService() {
        Stop = true;
    }

}
