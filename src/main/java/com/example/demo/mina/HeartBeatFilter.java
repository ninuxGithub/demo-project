package com.example.demo.mina;

import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.http.api.HttpRequest;
import org.apache.mina.http.api.HttpResponse;

@Slf4j
public class HeartBeatFilter implements KeepAliveMessageFactory {

    @Override
    public boolean isRequest(IoSession session, Object message) {
        try {
            if (message != null && message instanceof HttpRequest) {
                log.info("心跳消息===========>isRequest");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession session, Object message) {
        if (null != message && message instanceof HttpResponse) {
            return false;
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession session) {
        return session.getCurrentWriteMessage();
    }

    @Override
    public Object getResponse(IoSession session, Object request) {
        log.info("回应心跳==》");
        return request;
    }
}
