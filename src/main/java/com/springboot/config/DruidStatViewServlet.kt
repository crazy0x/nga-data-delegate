package com.springboot.config

import javax.servlet.annotation.WebInitParam
import javax.servlet.annotation.WebServlet

import com.alibaba.druid.support.http.StatViewServlet

/**
 * Druid的Servlet
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = arrayOf("/druid/*"),
initParams= arrayOf(
        WebInitParam(name="allow",value="127.0.0.1"),
        WebInitParam(name="loginUsername",value="admin"),
        WebInitParam(name="loginPassword",value="123456"),
        WebInitParam(name="resetEnable",value="false")))
class DruidStatViewServlet : StatViewServlet()
