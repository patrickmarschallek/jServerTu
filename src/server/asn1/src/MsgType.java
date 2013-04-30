package asn1.src;

/*
 * Created by JAC (Java Asn1 Compiler)
 */

import com.turkcelltech.jac.Enumerated;
//import com.chaosinmotion.asn1.Tag;


public class MsgType extends Enumerated
{
	/**
	* To set your ENUMERATED type, just call one of the "setTo_elementName()" methods.
	* To encode/decode your object, just call encode(..) decode(..) methods
	* See 'TestProject.java' in the project to examine encoding/decoding examples
	*/
	
	public static final int  clientToServerInt_ = 0;

	public void setTo_clientToServerInt_0() {
		setValue(clientToServerInt_);
	}

	public static final int  serverToClientAnswer_ = 1;

	public void setTo_serverToClientAnswer_1() {
		setValue(serverToClientAnswer_);
	}

	public static final int  clientToServerString_ = 2;

	public void setTo_clientToServerString_2() {
		setValue(clientToServerString_);
	}

	public static final int  serverToClientResult_ = 3;

	public void setTo_serverToClientResult_3() {
		setValue(serverToClientResult_);
	}

	/* end of enumerated constants */

	/**
	* constructs empty ENUMERATED object
	*/
	public
	MsgType()
	{
		super();
	}
	
	/**
	* constructs empty ENUMERATED object with its name.
	*/
	public
	MsgType(String name)
	{
		super(name);
	}
	
	/**
	* if you want to set your ENUMERATED to a different undefined value, use this constructor.
	*/
	public
	MsgType(long value)
	{
		super(value);
	}
	
	/**
	* if you want to set your ENUMERATED to a different undefined value, use this constructor.
	*/
	public
	MsgType(String name, long value)
	{
		super(name, value);
	}
}
