package com.alex.miaomiao.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsLog {

    private static final String ARROWHEAD = "\u27A3";
    private static final MsLog instance=new MsLog();

    private Logger logger;

    private MsLog() {}

    public static MsLog getLog(Class<?> clzz) {
       instance.setLogger(clzz);
       return instance;
    }

    public void setLogger(Class<?> clzz) {
        this.logger = LoggerFactory.getLogger(clzz);
    }

    public void debug(String msg) {
        this.logger.debug(msg);
    }
    public void debug(String format, Object... arguments) {
        this.logger.debug(format, arguments);
    }

    public void fmtDebug(String format, Object... arguments) {
        String fmt = String.format("%s %s", ARROWHEAD, format);
        this.logger.debug(fmt, arguments);
    }

    public void warn(String format, Object o) {
        this.logger.warn(format, o);
    }

    public void info(String format, Object... arguments){
        this.logger.info(format, arguments);
    }
}
