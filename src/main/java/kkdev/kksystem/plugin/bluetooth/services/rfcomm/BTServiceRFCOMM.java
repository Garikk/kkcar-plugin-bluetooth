/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.plugin.bluetooth.services.rfcomm;

import kkdev.kksystem.plugin.bluetooth.services.IServiceCallback;
import kkdev.kksystem.plugin.bluetooth.services.IBTService;

/**
 *
 * @author sayma_000
 */
public class BTServiceRFCOMM implements IBTService {

    private String ServiceURL;
    private String MyTag;

    @Override
    public void ConnectService(String Tag,String ConnectionURL, IServiceCallback Callback) {

        Thread ServiceReader = new Thread(() -> {
            //while (!Stop) {
               // try {
                    //out.println("[BT][INF] SVC DEV " +Tag + " " +ConnectionURL);
                    ServiceURL = ConnectionURL;
                    //StreamConnection streamConnection = null;
                    //streamConnection = (StreamConnection) Connector.open(ServiceURL);
                    //InputStream inStream = streamConnection.openInputStream();
                    //BufferedReader bReader2 = new BufferedReader(new InputStreamReader(inStream));

                    //while (!Stop) {
                    //    String lineRead = bReader2.readLine();
                    //    Callback.ReceiveServiceData(MyTag,lineRead, lineRead);
                    //}
                    //
                   // if (Stop) {
                    //    streamConnection.close();
                    //}
               // } catch (IOException ex) {
               //    out.println("[BT][INF] Service " +Tag + " " +ConnectionURL + " DISABLED (Device not found or error)");
                  //  Logger.getLogger(BTServiceRFCOMM.class.getName()).log(Level.SEVERE, null, ex);
               //     Stop=true;
               // }
           // }

        });

        ServiceReader.start();

    }

    @Override
    public void StopService() {
        boolean stop = true;
    }

}
