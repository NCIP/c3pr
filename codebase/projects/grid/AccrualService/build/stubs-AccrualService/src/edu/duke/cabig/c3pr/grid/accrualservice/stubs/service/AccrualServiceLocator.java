/**
 * AccrualServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Apr 28, 2006 (12:42:00 EDT) WSDL2Java emitter.
 */

package edu.duke.cabig.c3pr.grid.accrualservice.stubs.service;

public class AccrualServiceLocator extends org.apache.axis.client.Service implements edu.duke.cabig.c3pr.grid.accrualservice.stubs.service.AccrualService {

    public AccrualServiceLocator() {
    }


    public AccrualServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AccrualServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AccrualServicePortTypePort
    private java.lang.String AccrualServicePortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getAccrualServicePortTypePortAddress() {
        return AccrualServicePortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AccrualServicePortTypePortWSDDServiceName = "AccrualServicePortTypePort";

    public java.lang.String getAccrualServicePortTypePortWSDDServiceName() {
        return AccrualServicePortTypePortWSDDServiceName;
    }

    public void setAccrualServicePortTypePortWSDDServiceName(java.lang.String name) {
        AccrualServicePortTypePortWSDDServiceName = name;
    }

    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.AccrualServicePortType getAccrualServicePortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(AccrualServicePortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAccrualServicePortTypePort(endpoint);
    }

    public edu.duke.cabig.c3pr.grid.accrualservice.stubs.AccrualServicePortType getAccrualServicePortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            edu.duke.cabig.c3pr.grid.accrualservice.stubs.bindings.AccrualServicePortTypeSOAPBindingStub _stub = new edu.duke.cabig.c3pr.grid.accrualservice.stubs.bindings.AccrualServicePortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getAccrualServicePortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAccrualServicePortTypePortEndpointAddress(java.lang.String address) {
        AccrualServicePortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (edu.duke.cabig.c3pr.grid.accrualservice.stubs.AccrualServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                edu.duke.cabig.c3pr.grid.accrualservice.stubs.bindings.AccrualServicePortTypeSOAPBindingStub _stub = new edu.duke.cabig.c3pr.grid.accrualservice.stubs.bindings.AccrualServicePortTypeSOAPBindingStub(new java.net.URL(AccrualServicePortTypePort_address), this);
                _stub.setPortName(getAccrualServicePortTypePortWSDDServiceName());
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
        if ("AccrualServicePortTypePort".equals(inputPortName)) {
            return getAccrualServicePortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService/service", "AccrualService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://accrualservice.grid.c3pr.cabig.duke.edu/AccrualService/service", "AccrualServicePortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("AccrualServicePortTypePort".equals(portName)) {
            setAccrualServicePortTypePortEndpointAddress(address);
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
