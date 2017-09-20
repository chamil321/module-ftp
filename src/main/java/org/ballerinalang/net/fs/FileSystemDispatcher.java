/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.net.fs;

import org.ballerinalang.connector.api.BallerinaConnectorException;
import org.ballerinalang.connector.api.Resource;
import org.ballerinalang.connector.api.Service;
import org.ballerinalang.util.exceptions.BallerinaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.transport.filesystem.connector.server.contract.FileSystemMessage;

/**
 * Resource level dispatchers handler for file protocol.
 */
public class FileSystemDispatcher {

    private static final Logger log = LoggerFactory.getLogger(FileSystemDispatcher.class);

    public static Resource findResource(FileSystemMessage fileSystemMessage)
            throws BallerinaException {
        Service service = FileSystemServiceRegistry.getInstance().findService(fileSystemMessage);
        if (service == null) {
            throw new BallerinaConnectorException("No Service found to handle the service request.");
        }
        Resource[] resources = service.getResources();
        if (resources == null) {
            throw new BallerinaConnectorException("No resources define for given service: " + service.getName());
        } else if (resources.length >= 2) {
            throw new BallerinaConnectorException("More than one resource define for given service: "
                    + service.getName());
        }
        return resources[0];
    }
}
