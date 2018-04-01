/*
 * Copyright (c) 2018 by Duong Thai Minh.
 * All rights reserved.
 */

package hcmut.co3003.server;

import hcmut.co3003.server.dal.FullInformationRecord;

import java.net.Socket;

public class GetService extends Service<FullInformationRecord> {
    String request;

    public GetService(Socket client, String request) {
        super(client);
        this.request = request;
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
    public FullInformationRecord call() throws Exception {
        return null;
    }
}
