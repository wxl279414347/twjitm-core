package com.twjitm.core.common.config.global;import com.twjitm.core.utils.xml.JdomUtils;import org.jdom2.Element;import org.springframework.stereotype.Service;/** * @author twjitm - [Created on 2018-08-22 19:14] * @company https://github.com/twjitm/ * @jdk java version "1.8.0_77" * rpc 网络配置 */@Servicepublic class NettyGameRpcConfig extends NettyGameConfig {    public void init() {        String file = GlobalConstants.ConfigFile.RPC_SERVER_CONFIG_FILE_PATH;        Element rootElement = JdomUtils.getRootElementByStream(file);        super.init(rootElement);    }}