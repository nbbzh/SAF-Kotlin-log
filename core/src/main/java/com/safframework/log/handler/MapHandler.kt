package com.safframework.log.handler

import com.safframework.log.L
import com.safframework.log.LogLevel
import com.safframework.log.LoggerPrinter
import com.safframework.log.extension.formatJSON
import com.safframework.log.extension.parseMap
import com.safframework.log.formatter.Formatter
import com.safframework.log.parser.Parser
import com.safframework.log.utils.toJavaClass
import org.json.JSONObject

/**
 * Created by tony on 2017/11/27.
 */
class MapHandler:BaseHandler(),Parser<Map<*,*>>{

    override fun handle(obj: Any, logLevel: LogLevel, tag: String): Boolean {

        if (obj is Map<*,*>) {

            L.printers().map {
                val s = L.getMethodNames(it.formatter)
                it.printLog(logLevel,tag ,String.format(s, parseString(obj,it.formatter)))
            }
            return true
        }

        return false
    }

    override fun parseString(map: Map<*, *>,formatter:Formatter): String {

        var msg = map.toJavaClass() + LoggerPrinter.BR + formatter.spliter()

        return msg + JSONObject().parseMap(map)
                .formatJSON()
                .let {
                    it.replace("\n", "\n${formatter.spliter()}")
                }
    }

}