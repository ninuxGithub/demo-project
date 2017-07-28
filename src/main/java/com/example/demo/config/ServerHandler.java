package com.example.demo.config;

import java.text.MessageFormat;
import java.util.List;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ServerHandler extends IoHandlerAdapter {

    private Gson gson;
    private static final String REGISTER = "register";
    private static final String CONTROL = "control";
    private static final String AQI = "aqi";

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        log.error(session.getRemoteAddress().toString() + " : " + cause.toString());
        session.close(true);
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        log.info("连接创建 : " + session.getRemoteAddress().toString());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.info("连接打开：" + session.getRemoteAddress().toString());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        gson = new Gson();
        try {
            String text = message.toString();
            log.info("接收消息内容 : " + text);
            String result = "defalut-Value";//analyzeData(text, session);
            session.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* private String analyzeData(String text, IoSession session) {
        String controlResult = "";
        gson = new Gson();
        try {
            RegisterDto registerInfo = gson.fromJson(text, RegisterDto.class);
            String msgType = registerInfo.getHead().getMsgType();
            if (REGISTER.equals(msgType)) {
                controlResult = saveSessionKey(registerInfo, session);
            } else if (CONTROL.equals(msgType)) {
                controlResult = control(registerInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controlResult;
    }*/


   /* private String saveSessionKey(RegisterDto registerInfo, IoSession session) {
        gson = new Gson();
        Result result = new Result();
        OutputBody body = new OutputBody();
        OutputHead head = new OutputHead();
        head.setMsgType(registerInfo.getHead().getMsgType());
        head.setMsgId(registerInfo.getHead().getMsgId());
        result.setHead(head);
        try {
            List<String> sessionKeys = registerInfo.getBody().getSessionKey();

            for (String key : sessionKeys) {
                SessionCache.getInstance().save(key, session);
            }
            session.setAttribute(sessionKeys);
            body.setCode("1");
            body.setInfo("注册执行成功");
            result.setBody(body);
        } catch (Exception e) {
            e.printStackTrace();
            body.setCode("0");
            body.setInfo("注册执行失败");
        }
        return gson.toJson(result);
    }*/

   /* private String control(RegisterDto registerInfo) {
        ControlDto controlDto = new ControlDto();
        Gson gson = new Gson();
        String msgId = registerInfo.getHead().getMsgId();
        String sessionId = registerInfo.getHead().getSessionId();
        String deviceId = registerInfo.getBody().getDeviceId();
        String userOpenId = registerInfo.getBody().getUserOpenId();
        String deviceKind = registerInfo.getBody().getDeviceKind();
        String controlKey = registerInfo.getBody().getControlKey();
        controlDto.setControlKey(controlKey);
        controlDto.setDeviceId(deviceId);
        controlDto.setDeviceKind(deviceKind);
        controlDto.setMsgId(msgId);
        controlDto.setSessionId(sessionId);
        controlDto.setUserOpenId(userOpenId);
        String control = gson.toJson(controlDto);
        return WechatUtils.controlDevice(control);
    }*/

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        log.info("发送消息内容 : " + message.toString());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        log.info(MessageFormat.format("连接Idle [{0}] from {1} ", status, session.getRemoteAddress()));
        if (status == IdleStatus.READER_IDLE) {
            log.info("进入读空闲状态");
            session.close(true);
        } else if (status == IdleStatus.BOTH_IDLE) {
            log.info("BOTH空闲");
            session.close(true);
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        log.info("连接关闭 : " + session.getRemoteAddress().toString());
        int size = session.getService().getManagedSessions().values().size();
        log.info("连接关闭时session数量==》" + size);
//        List<String> sessions = (List<String>) session.getAttribute("mac");
//        if (sessions != null) {
//            for (String key : sessions) {
//                SessionCache.getInstance().remove(key, session);
//            }
//        }
    }

}
