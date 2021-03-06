package org.fortune.commons.datacache.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class AbstractCacheConfig {
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractCacheConfig.class);

    protected static final String SERVERS_ACCOUNTS_DELIMITER = "@";
    protected static final String SERVER_PORT_DELIMITER = ":";
    protected static final String SERVERS_DELIMITER = " ";
    protected static final String ACCOUNT_PASSWORD_DELIMITER = ":";

    @Value("${data.cache.server.host}")
    private String hubCacheServer;
    @Value("${data.cache.server.port}")
    private int hubCachePort;
    @Value("${data.cache.server.account}")
    private String hubCacheAccount;
    @Value("${data.cache.server.password}")
    private String hubCachePassword;

    //common settings
    @Value("${data.cache.common.cacheStrategy}")
    private String cacheStrategy;
    @Value("${data.cache.common.mappingLocalFlag}")
    private boolean mappingLocalFlag;
    @Value("${data.cache.common.expiredTime}")
    private int expiredTime;
    @Value("${data.cache.common.cacheKeyPrefix}")
    private String cacheKeyPrefix;
    @Value("${data.cache.common.loadFlag}")
    private boolean loadFlag;
    @Value("${data.cache.common.clusterFlag}")
    private boolean clusterFlag;
    @Value("${data.cache.common.authFlag}")
    private boolean authFlag;

    public String getHubCacheServer() {
        return hubCacheServer;
    }

    public void setHubCacheServer(String hubCacheServer) {
        this.hubCacheServer = hubCacheServer;
    }

    public int getHubCachePort() {
        return hubCachePort;
    }

    public void setHubCachePort(int hubCachePort) {
        this.hubCachePort = hubCachePort;
    }

    public String getHubCacheAccount() {
        return hubCacheAccount;
    }

    public void setHubCacheAccount(String hubCacheAccount) {
        this.hubCacheAccount = hubCacheAccount;
    }

    public String getHubCachePassword() {
        return hubCachePassword;
    }

    public void setHubCachePassword(String hubCachePassword) {
        this.hubCachePassword = hubCachePassword;
    }

    public String getCacheStrategy() {
        return cacheStrategy;
    }

    public void setCacheStrategy(String cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
    }

    public boolean isMappingLocalFlag() {
        return mappingLocalFlag;
    }

    public void setMappingLocalFlag(boolean mappingLocalFlag) {
        this.mappingLocalFlag = mappingLocalFlag;
    }

    public int getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(int expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getCacheKeyPrefix() {
        return cacheKeyPrefix;
    }

    public void setCacheKeyPrefix(String cacheKeyPrefix) {
        this.cacheKeyPrefix = cacheKeyPrefix;
    }

    public boolean isLoadFlag() {
        return loadFlag;
    }

    public void setLoadFlag(boolean loadFlag) {
        this.loadFlag = loadFlag;
    }

    public boolean isClusterFlag() {
        return clusterFlag;
    }

    public void setClusterFlag(boolean clusterFlag) {
        this.clusterFlag = clusterFlag;
    }

    public boolean isAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(boolean authFlag) {
        this.authFlag = authFlag;
    }
}
