package Gateway;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GatewayForm {
    private JPanel panel1;
    private JButton connectButton;
    private JTextField ipHost;
    private JTextField time2send;
    private JLabel status;
    private JTextField port;
    private JLabel portLabel;
    private JLabel IPHostLabel;
    private JLabel statusLabel;
    private JLabel timeLabel;
    private JButton disconnectButton;

    public GatewayForm() {
        connectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton() != 0){
                    status.setText("Waiting for response...");
                    SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                        @Override
                        protected Boolean doInBackground() throws Exception {
                            Gateway.time = Integer.parseInt(time2send.getText());

                            if (Gateway.time > 10 || Gateway.time < 1){
                                status.setText("Please enter time between 1s and 10s");
                            }
                            else{
                                Gateway.port = Integer.parseInt(port.getText());
                                try {
                                    Gateway.IP = InetAddress.getByName(ipHost.getText());
                                    Thread tcp = new Thread(new TCPGetID(Gateway.IP, Gateway.port));
                                    tcp.start();
                                    tcp.join();
                                    if (!Gateway.id.equals("no_id")) {
                                        Gateway.connect = true;
                                        publish("Connected to host " + ipHost.getText());

                                        String fileName = Gateway.fileName1;
                                        if (!Gateway.udpListening) {
                                            new Thread(new UDPListening(8080)).start();
                                            Gateway.udpListening = true;
                                        }

                                        while (Gateway.connect) {
                                            TimeUnit.SECONDS.sleep(Gateway.time);
                                            new Thread(new UDPSendData(Gateway.IP, Gateway.port, fileName)).start();
                                            if (fileName == Gateway.fileName1) {
                                                fileName = Gateway.fileName2;

                                            } else {
                                                fileName = Gateway.fileName1;
                                            }
                                        }
                                    } else {
                                        publish(TCPGetID.messRev);
                                    }
                                }
                                catch (Exception e){
                                    publish(e.toString());
                                }
                                return true;
                            }
                            return false;
                        }

                        @Override
                        protected void process(List<String> chunks) {
                            super.process(chunks);
                            String id = chunks.get(chunks.size()-1);
                            status.setText(id);
                        }
                    };
                    worker.execute();
                }
            }
        });
        disconnectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getButton() != 0){
                    Gateway.connect = false;
                    status.setText("Disconnect");
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
