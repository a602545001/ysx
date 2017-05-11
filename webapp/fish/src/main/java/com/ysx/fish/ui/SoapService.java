package com.ysx.fish.ui;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import org.apache.cxf.annotations.WSDLDocumentation;

@WebService(serviceName = "collectService", targetNamespace = "http://ccc.emis.dcs.api", portName = "collectServicePort", name = "collectPortType")
@SOAPBinding(style = Style.RPC, use = Use.ENCODED)
@WSDLDocumentation(value = "collect class")
public class SoapService {

	@WebMethod(operationName = "collect")
	@WebResult(name = "result")
	@WSDLDocumentation(value = "collect method")
	public int collect(@WebParam(name = "data") String data) {
		System.out.println("1");
		return 1;
	}
}
