package com.wsl.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author tan
 * @version 1.0
 * @date 2020/9/25 9:45
 */
@Data
@ConfigurationProperties(prefix = "wsl.druid")
public class DruidMonitorProperties {

    private String username;
    private String password;
    private String servletPath = "/druid/*";
    private Boolean resetEnable = false;
    private List<String> allowIps;
    private List<String> denyIps;
}
