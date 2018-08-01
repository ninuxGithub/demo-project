package com.example.demo.mina;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MonitorClientHandler extends IoHandlerAdapter {


	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		cause.printStackTrace();
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		/* Client端的逻辑会在这里 */
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		/* 或者会在这里，当然每个地方都会有一些东西需要处理，例如创建、关闭、空闲等等 */
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		/* 这里可以实现重连，但是这会涉及到你代码的一些资源分配或调度逻辑，跑一个线程进行重连 */
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("CLIENT=>sessionCreated: current sessionId:" + session.getId());
		super.sessionCreated(session);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

		System.out.println("CLIENT=>sessionIdle:" + session.getIdleCount(status));
		super.sessionIdle(session, status);
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("CLIENT=>sessionOpened: current sessionId:" + session.getId());
		super.sessionOpened(session);
	}

}
