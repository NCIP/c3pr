/**
 * RegistrationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.registrationservice.stubs.service;

public class RegistrationServiceLocator extends org.apache.axis.client.Service implements edu.duke.cabig.c3pr.grid.registrationservice.stubs.service.RegistrationService {

    public RegistrationServiceLocator() {
    }


    public RegistrationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RegistrationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RegistrationServicePortTypePort
    private java.lang.String RegistrationServicePortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getRegistrationServicePortTypePortAddress() {
        return RegistrationServicePortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RegistrationServicePortTypePortWSDDServiceName = "RegistrationServicePortTypePort";

    public java.lang.String getRegistrationServicePortTypePortWSDDServiceName() {
        return RegistrationServicePortTypePortWSDDServiceName;
    }

    public void setRegistrationServicePortTypePortWSDDServiceName(java.lang.String name) {
        RegistrationServicePortTypePortWSDDServiceName = name;
    }

    public edu.duke.cabig.c3pr.grid.registrationservice.stubs.RegistrationServicePortType getRegistrationServicePortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RegistrationServicePortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRegistrationServicePortTypePort(endpoint);
    }

    public edu.duke.cabig.c3pr.grid.registrationservice.stubs.RegistrationServicePortType getRegistrationServicePortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            edu.duke.cabig.c3pr.grid.registrationservice.stubs.bindings.RegistrationServicePortTypeSOAPBindingStub _stub = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.bindings.RegistrationServicePortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getRegistrationServicePortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRegistrationServicePortTypePortEndpointAddress(java.lang.String address) {
        RegistrationServicePortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (edu.duke.cabig.c3pr.grid.registrationservice.stubs.RegistrationServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                edu.duke.cabig.c3pr.grid.registrationservice.stubs.bindings.RegistrationServicePortTypeSOAPBindingStub _stub = new edu.duke.cabig.c3pr.grid.registrationservice.stubs.bindings.RegistrationServicePortTypeSOAPBindingStub(new java.net.URL(RegistrationServicePortTypePort_address), this);
                _stub.setPortName(getRegistrationServicePortTypePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RegistrationServicePortTypePort".equals(inputPortName)) {
            return getRegistrationServicePortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://registrationservice.grid.c3pr.cabig.duke.edu/RegistrationService/service", "RegistrationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://registrationservice.grid.c3pr.cabig.duke.edu/RegistrationService/service", "RegistrationServicePortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("RegistrationServicePortTypePort".equals(portName)) {
            setRegistrationServicePortTypePortEndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
