package com.example.demo.config;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineDecoder;
import org.apache.mina.filter.codec.textline.TextLineEncoder;


public  class MyCodeFactory implements ProtocolCodecFactory {
	
	private LineDelimiter enLineDelimiter = new LineDelimiter(",");

    private final TextLineEncoder encoder;
    private final TextLineDecoder decoder;
    /*final static char endchar = 0x1a;*/
    //final static String endchar = Constant.CHAR2;
    
    public MyCodeFactory() {
        this(Charset.forName("gb2312"));
    }
    
    public MyCodeFactory(Charset charset) {
    	 encoder = new TextLineEncoder(charset, enLineDelimiter);
         decoder = new TextLineDecoder(charset, enLineDelimiter);
         
         }

	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return decoder;
	}
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		return encoder;
	}
	public int getEncoderMaxLineLength() {
        return encoder.getMaxLineLength();
    }
    public void setEncoderMaxLineLength(int maxLineLength) {
        encoder.setMaxLineLength(maxLineLength);
    }
    public int getDecoderMaxLineLength() {
        return decoder.getMaxLineLength();
    }
    public void setDecoderMaxLineLength(int maxLineLength) {
        decoder.setMaxLineLength(maxLineLength);
    }

}

