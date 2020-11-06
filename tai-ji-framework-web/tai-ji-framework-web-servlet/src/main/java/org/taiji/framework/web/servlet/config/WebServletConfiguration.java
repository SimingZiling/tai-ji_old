package org.taiji.framework.web.servlet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.taiji.framework.core.confog.ApplicationConfiguration;
import org.taiji.framework.core.confog.Configuration;

public class WebServletConfiguration implements Configuration {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static String characterEncoding;

    public static String getCharacterEncoding() {
        return characterEncoding;
    }

    @Override
    public void distribution() {

        logger.info("配置信息分发至WebServlet配置");
        String characterEncodingName = "characterEncoding";
        if(!ApplicationConfiguration.containsConfig(characterEncodingName)){
            characterEncoding = "UTF-8";
        }else {
            characterEncoding = String.valueOf(ApplicationConfiguration.getApplicationConfig(characterEncodingName));
        }
    }
}
