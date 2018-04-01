/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server;

import hcmut.co3003.server.dal.FullInformationRecord;
import hcmut.co3003.server.util.Common;
import hcmut.co3003.server.util.Parser;
import hcmut.co3003.server.util.Token;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Scanner;

public class Proxy extends Service<Object> {
    private String thread;
    private byte ENDL = ((byte) '\n');

    public Proxy(Socket client) {
        super(client);
    }

    /**
     * Invoked when the Task is executed, the call method must be overridden and
     * implemented by subclasses. The call method actually performs the
     * background thread logic. Only the updateProgress, updateMessage, updateValue and
     * updateTitle methods of Task may be called from code within this method.
     * Any other interaction with the Task from the background thread will result
     * in runtime exceptions.
     *
     * @return The result of the background work, if any.
     * @throws Exception an unhandled exception which occurred during the
     *                   background operation
     */
    @Override
    public Object call() throws Exception {
        this.thread = Thread.currentThread().getName();
        logger.severe("[" + thread + "] run " + this.getClass().getName());
        client.setSoTimeout(5000);
        InputStream is = this.client.getInputStream();
        OutputStream os = this.client.getOutputStream();

        while (is.available() == 0
                && client.isConnected()
                && !client.isInputShutdown()) ;
        Scanner scanner = new Scanner(is);
        String request = scanner.nextLine();
        byte[] response = {};
        Object result = null;

        try {
            Enumeration<Token> tokens = Parser.parse(request);
            if (tokens.hasMoreElements()) {
                Token requestTag = tokens.nextElement();

                switch (requestTag.getType()) {
                    case GET:
                        Token t = tokens.nextElement();

                        if (t.getType() == Token.Type.Identifier) {// GET Identifier
                            logger.info("[" + this.thread + "]"
                                    + "Client request data latest record with id=" + t.toString());
                            FullInformationRecord record = new GetService(client, request).call();
                            response = record.toString().getBytes();
                            result = record;
                        } else if (t.getType() == Token.Type.FILE) {
                            throw new UnsupportedOperationException("Register gateway");
                        }
                        break;
                    case SET:
                        logger.info("[" + this.thread + "]" + "Not handled");
                        response = "SUCCESS".getBytes();
                        result = true;
                        break;
                    case DOWNLOAD:
                        logger.info("[" + this.thread + "]" + "Not handled");
                        response = "12345 &*%^*&df@#$%#".getBytes();
                        result = true;
                        break;
                    case REGISTER_GATEWAY:
                        throw new UnsupportedOperationException("Register gateway");
//                        break;
                    default:
                        logger.warning("[" + this.thread + "]" + "Bad Request: " + request);
                        result = new Common.Error.BadRequest(request);
                        response = result.toString().getBytes();
                }
            }
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

//        logger.info(String.format("[%s] Send: %s bytes", this.thread, response.length));
        os.write(response);
        os.write(ENDL);
        os.flush();
        logger.info(String.format("[%s] Send: %s bytes finish", this.thread, response.length));
        client.close();
        return result;
    }
}
