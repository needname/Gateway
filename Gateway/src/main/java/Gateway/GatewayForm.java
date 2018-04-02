package Gateway;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class GatewayForm {
    private JPanel panel1;
    private JButton connectButton;
    private JTextField ipHost;
    private JTextField time2send;
    private JLabel status;
    private JTextField port;
    private JLabel portLabel;

    public GatewayForm() {
        connectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //System.out.println("connected");
                if(e.getButton() != 0){
                    Gateway.time = Integer.parseInt(time2send.getText());
                    Gateway.port = Integer.parseInt(port.getText());
                    try{
                        Gateway.IP = InetAddress.getByName(ipHost.getText());
                        Thread tcp =  new Thread(new TCPGetID(Gateway.IP, Gateway.port));
                        tcp.start();
                        tcp.join();
                        System.out.println(Gateway.id);
                        if(!Gateway.id.equals("no_id")){

                            status.setText("Connected to " + Gateway.IP.toString() + " , id of gateway is: " + Gateway.id);
                            System.out.println("connected");
                            PrintWriter writer1 = new PrintWriter(Gateway.fileName1);
                            writer1.print("");
                            writer1.close();

                            PrintWriter writer2 = new PrintWriter(Gateway.fileName2);
                            writer2.print("");
                            writer2.close();

                            String fileName = Gateway.fileName1;

                            new Thread(new UDPListening(8080)).start();

                            while(true){
                                TimeUnit.SECONDS.sleep(Gateway.time);
                                //System.out.println(fileName);
                                new Thread(new UDPSendData(Gateway.IP,2000,fileName)).start();
                                if(fileName == Gateway.fileName1){
                                    fileName = Gateway.fileName2;

                                }
                                else{
                                    fileName = Gateway.fileName1;
                                }
                            }
                        }
                        else {
                            status.setText(TCPGetID.messRev);
                        }
                    }
                    catch(Exception e1){
                        status.setText(e1.toString());
                    }

                }
            }
        });
    }
    public static void main(String args[]) throws Exception {
        JFrame frame = new JFrame("GatewayForm");
        frame.setContentPane(new GatewayForm().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
