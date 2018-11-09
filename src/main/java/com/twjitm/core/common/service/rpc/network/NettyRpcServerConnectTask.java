package com.twjitm.core.common.service.rpc.network;import com.twjitm.core.common.handler.rpc.NettyRpcClientServerHandler;import com.twjitm.core.common.service.rpc.server.NettyRpcNodeInfo;import com.twjitm.core.initalizer.NettyRpcClientMessageServerInitializer;import com.twjitm.core.utils.logs.LoggerUtils;import io.netty.bootstrap.Bootstrap;import io.netty.channel.ChannelFuture;import io.netty.channel.ChannelFutureListener;import io.netty.channel.ChannelOption;import io.netty.channel.EventLoopGroup;import io.netty.channel.socket.nio.NioSocketChannel;import io.netty.handler.logging.LogLevel;import io.netty.handler.logging.LoggingHandler;import org.apache.log4j.Logger;import java.net.InetSocketAddress;/** * @author twjitm - [Created on 2018-08-20 11:11] * @company https://github.com/twjitm/ * @jdk java version "1.8.0_77" * 连接到服务器 */public class NettyRpcServerConnectTask implements Runnable {private Logger logger=LoggerUtils.getLogger(NettyRpcServerConnectTask.class);    /**     * 连接地址     */    private InetSocketAddress remotePeer;    private EventLoopGroup eventLoopGroup;    private NettyRpcClient nettyRpcClient;    public NettyRpcServerConnectTask(            NettyRpcNodeInfo nettyRpcNodeInfo,            EventLoopGroup eventLoopGroup,            NettyRpcClient nettyRpcClient) {        this.eventLoopGroup = eventLoopGroup;        this.nettyRpcClient = nettyRpcClient;        this.remotePeer =  new InetSocketAddress(nettyRpcNodeInfo.getHost(), nettyRpcNodeInfo.getIntPort());    }    @Override    public void run() {        Bootstrap b = new Bootstrap();        b.group(eventLoopGroup)                .channel(NioSocketChannel.class)                .option(ChannelOption.TCP_NODELAY, true)                .handler(new LoggingHandler(LogLevel.DEBUG))                .handler(new NettyRpcClientMessageServerInitializer());        ChannelFuture channelFuture = b.connect(remotePeer);        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {            if (channelFuture1.isSuccess()) {                logger.info("CONNECT TO REMOTE SERVER. REMOTE PEER = " + remotePeer + " SUCCESS");                NettyRpcClientServerHandler handler = channelFuture1.channel().pipeline().get(NettyRpcClientServerHandler.class);                handler.setNettyRpcClient(nettyRpcClient);                nettyRpcClient.getRpcClientConnection().setChannel((NioSocketChannel) channelFuture1.channel());            }else{                logger.debug("CONNECT TO REMOTE SERVER. REMOTE PEER = " + remotePeer + "FAIL");            }        });        try {            channelFuture.await();        } catch (InterruptedException e) {            logger.error(e.toString(), e);        }        //连接结束        logger.debug("CONNECT TO REMOTE SERVER. REMOTE PEER = " + remotePeer);    }}