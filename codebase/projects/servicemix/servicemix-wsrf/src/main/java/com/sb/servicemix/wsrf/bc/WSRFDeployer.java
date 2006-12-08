/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sb.servicemix.wsrf.bc;

import javax.jbi.management.DeploymentException;

import org.apache.servicemix.common.BaseComponent;
import org.apache.servicemix.common.Endpoint;
import org.apache.servicemix.common.xbean.AbstractXBeanDeployer;

public class WSRFDeployer extends AbstractXBeanDeployer {

    public WSRFDeployer(BaseComponent component) {
        super(component);
    }

    protected boolean validate(Endpoint endpoint) throws DeploymentException {
        if (endpoint instanceof WSRFEndpoint == false) {
            throw new DeploymentException("Endpoint should be a MyEndpoint");
        }
        ((WSRFEndpoint) endpoint).validate();
        return true;
    }
}
