package com.twjitm.core.common.service.rpc.client.proxy;import com.twjitm.core.common.service.rpc.client.NettyRpcFuture;/** * @author twjitm - [Created on 2018-08-20 14:45] * @company https://github.com/twjitm/ * @jdk java version "1.8.0_77" */public interface INettyAsyncRpcProxy {    public NettyRpcFuture call(String funcName, Object... args);}