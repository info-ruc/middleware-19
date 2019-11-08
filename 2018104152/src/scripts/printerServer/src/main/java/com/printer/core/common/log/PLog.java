package com.printer.core.common.log;

import com.printer.core.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PLog {

    private static final Logger logger = LoggerFactory.getLogger(PLog.class);

    private static String createString(Object object){
        return StringUtil.paraseString(object);
    }

    public static void debug(Object obj) {
         logger.debug(createString(obj));
    }

    public static void info(Object obj) {
        logger.info(createString(obj));
    }

    public static void error(Object obj) {
        logger.error(createString(obj));
    }

}
