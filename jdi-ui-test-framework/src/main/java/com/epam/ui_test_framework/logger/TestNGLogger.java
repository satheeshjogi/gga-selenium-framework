package com.epam.ui_test_framework.logger;

import com.epam.ui_test_framework.logger.base.AbstractLogger;
import com.epam.ui_test_framework.logger.enums.BusinessInfoTypes;
import com.epam.ui_test_framework.logger.enums.LogInfoTypes;
import com.epam.ui_test_framework.logger.enums.LogLevels;
import com.epam.ui_test_framework.utils.map.MapArray;

import static com.epam.ui_test_framework.logger.enums.LogInfoTypes.*;
import static com.epam.ui_test_framework.utils.common.Timer.nowTime;
import static java.lang.String.format;
import static org.testng.Reporter.log;

/**
 * Created by Roman_Iovlev on 6/9/2015.
 */
public class TestNGLogger extends AbstractLogger {
    private MapArray<LogInfoTypes, Integer> typesMap = new MapArray<>(new Object[][] {
            {BUSINESS, 2},
            {FRAMEWORK, 1},
            {TECHNICAL, 0}
    });

    @Override
    public void inLog(String message, LogLevels logLevel, LogInfoTypes logInfoType) {
        log(format("%s %s~ %s", typesMap.get(logInfoType), nowTime(), message));
    }
    @Override
    public void inLog(String message, BusinessInfoTypes infoType) {
        log(format("%s %s~ %s", typesMap.get(BUSINESS), nowTime(), format("[%s] %s", infoType, message)));
    }

    public TestNGLogger() { super(); }
    public TestNGLogger(LogLevels logLevel) { super(logLevel); }
}