package com.springboot.controller

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiImplicitParams
import io.swagger.annotations.ApiOperation
import okhttp3.*
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.Charset
import java.net.MalformedURLException
import java.net.URL
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletResponse
import java.io.*


@Api(value="NGA数据接口")
@RestController
@RequestMapping("/nga")
class NgaDataController {

    @RequestMapping(value="/thread")
    @ApiOperation(notes="帖子列表",value="帖子",httpMethod="GET")
    @ApiImplicitParams(ApiImplicitParam(name = "fid", paramType = "query", dataType = "int"),
            ApiImplicitParam(name = "page", paramType = "query", dataType = "int"))
    fun list (fid : Integer, page : Integer) : JSONObject {
        val a = System.currentTimeMillis() / 1000 - 100
        val requestBuilder = Request.Builder().url("http://bbs.ngacn.cc/thread.php?fid=$fid&page=$page&lite=js").addHeader("cookie", "guestJs=$a;")
        var bytes = OkHttpClient().newCall(requestBuilder.build()).execute().body().bytes()
        val content = String(bytes, Charset.forName("GBK"))
        return JSON.parse(content.replace("window.script_muti_get_var_store=", "")) as JSONObject
    }

    @RequestMapping(value="/read")
    @ApiOperation(notes="帖子详情",value="帖子",httpMethod="GET")
    @ApiImplicitParams(ApiImplicitParam(name = "tid", paramType = "query", dataType = "int"),
            ApiImplicitParam(name = "page", paramType = "query", dataType = "int"))
    fun detail (tid : Integer, page : Integer) : JSONObject {
        val a = System.currentTimeMillis() / 1000 - 100
        val requestBuilder = Request.Builder().url("http://bbs.ngacn.cc/read.php?tid=$tid&page=$page&lite=js").addHeader("cookie", "guestJs=$a;")
        var bytes = OkHttpClient().newCall(requestBuilder.build()).execute().body().bytes()
        val content = String(bytes, Charset.forName("GBK"))
        return JSON.parse(content.replace("window.script_muti_get_var_store=", "")) as JSONObject
    }

    @RequestMapping(value="/pic")
    @ApiOperation(notes="读取图片",value="图片",httpMethod="GET")
    @ApiImplicitParams(ApiImplicitParam(name = "url", paramType = "query", dataType = "string"))
    fun pic (url : String) {
        try {
            var u = URL(url)
            val dataInputStream = DataInputStream(u.openStream())
            getResponse().contentType = "image/png"
            val stream = getResponse().outputStream
            stream.write(File2byte(dataInputStream))
            stream.flush()
            stream.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun File2byte(fis: DataInputStream): ByteArray? {
        var buffer: ByteArray? = null
        try {
            val bos = ByteArrayOutputStream()
            val b = ByteArray(1024)
            var n: Int
            n = fis.read(b)
            while ((n) != -1) {
                bos.write(b, 0, n)
                n = fis.read(b)
            }
            fis.close()
            bos.close()
            buffer = bos.toByteArray()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return buffer
    }

    fun getResponse(): HttpServletResponse {
        return (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).response
    }
}
