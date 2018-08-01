package com.example.demo.mina;

import java.net.SocketAddress;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.http.api.DefaultHttpResponse;
import org.apache.mina.http.api.HttpRequest;
import org.apache.mina.http.api.HttpResponse;
import org.apache.mina.http.api.HttpStatus;
import org.apache.mina.http.api.HttpVersion;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends IoHandlerAdapter {

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

	/**
	 * http://localhost:3003/?name=minaHttp
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if (message instanceof HttpRequest) {
			// 请求，解码器将请求转换成HttpRequest对象
			HttpRequest request = (HttpRequest) message;
			Map<String, List<String>> parameters = request.getParameters();
			for (String key : parameters.keySet()) {
				log.info("key:{} value:{}", key, parameters.values().toString());
			}

			log.info("queryString :{}", request.getQueryString());

			// 获取请求参数
			String name = request.getParameter("name");
			if (name == null) {
				name = "World";
			}
			name = URLDecoder.decode(name, "UTF-8");

			// 响应HTML
			// String responseHtml = "<html><body>Hello, " + name + "</body></html>";
			// byte[] responseBytes = responseHtml.getBytes("UTF-8");
			// int contentLength = responseBytes.length;
			
			//响应json
			Map<String,Object> responseMap = new HashMap<>();
			responseMap.put("name", name);
			responseMap.put("status", 200);
			String jsonString = JSON.toJSONString(responseMap);
			byte[] responseBytes = jsonString.getBytes("UTF-8");
			int contentLength = responseBytes.length;
			

			// 构造HttpResponse对象，HttpResponse只包含响应的status line和header部分
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Content-Type", "application/json; charset=utf-8");
			headers.put("Content-Length", Integer.toString(contentLength));
			HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SUCCESS_OK, headers);

			// 响应BODY
			IoBuffer responseIoBuffer = IoBuffer.allocate(contentLength);
			responseIoBuffer.put(responseBytes);
			responseIoBuffer.flip();

			session.write(response); // 响应的status line和header部分
			// 发送数据到客户端
			final WriteFuture future = session.write(responseIoBuffer); // 响应body部分
			// 回调函数
			future.addListener(new IoFutureListener<WriteFuture>() {

				// write操作完成后调用的回调函数
				@Override
				public void operationComplete(WriteFuture future) {
					if (future.isWritten()) {
						log.info("write操作成功");
					} else {
						log.info("write操作失败");
					}
				}
			});
		} else {
			log.debug("message 不是httpRequest类型");
		}

	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.info("发送消息内容 : " + message.toString());
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		log.info(MessageFormat.format("连接Idle [{0}] from {1} ", status, session.getRemoteAddress()));
		try {
			if (status == IdleStatus.READER_IDLE) {
				log.info("进入读空闲状态");
				session.close(true);
			} else if (status == IdleStatus.BOTH_IDLE) {
				log.info("BOTH空闲");
				session.close(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		try {
			SocketAddress address = session.getRemoteAddress();
			if (null == address) {
				address = session.getLocalAddress();
			}
			log.info("连接关闭 : " + address.toString());
			int size = session.getService().getManagedSessions().values().size();
			log.info("连接关闭时session数量==》" + size);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
