package org.landy.common.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.landy.commons.datacache.DataCacheFacade;
import org.landy.commons.datacache.adapter.CacheDataLoadAdapter;
import org.landy.commons.web.conf.ApplicationContextConfiguration;
import org.landy.commons.web.conf.ExportAttachmentHandlerConfiguration;
import org.landy.commons.web.conf.FreemarkerConfiguration;
import org.landy.commons.web.conf.WebContextLoaderConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class) //调用Spring单元测试类
@WebAppConfiguration  //调用Java Web组件，如自动注入ServletContext Bean等
@ContextConfiguration(classes = {
        ApplicationContextConfiguration.class,
        ExportAttachmentHandlerConfiguration.class,
        FreemarkerConfiguration.class,
        WebContextLoaderConfiguration.class,
        DataCachedConfiguration.class
}) //加载Spring配置文件
public class WebApplicationContextTest {

    @Test
    public void test() {
        System.out.println("hello,world!");
    }

}

@Configuration
class DataCachedConfiguration {
    // 配置DataCacheFacade
    @Bean(name = DataCacheFacade.BEAN_NAME_DATA_CACHE_FACADE)
    public DataCacheFacade dataCacheFacade() {
        DataCacheFacade dataCacheFacade = new DataCacheFacade();
        List<CacheDataLoadAdapter> cacheDataAdapterList = new ArrayList<>();
        cacheDataAdapterList.add(new TestCodeCacheDataLoadAdapter());
        dataCacheFacade.setCacheDataAdapterList(cacheDataAdapterList);
        return dataCacheFacade;
    }

    public class TestCodeCacheDataLoadAdapter extends CacheDataLoadAdapter {
        private List<String> keys=new ArrayList<String>();
        @Override
        public boolean loadData() {
            Map<String,Object> dataMap= new HashMap<>();
            Map<String,Object> data = new HashMap<>();
            data.put("chris2","chris1");
            data.put("chris3","chris2");
            data.put("chris4","chris3");
            dataMap.put("landy12",data);
            dataMap.put("landy22","test2");
            dataMap.put("landy32","test3");
            dataMap.put("landy42","test4");
            dataMap.put("landy52","test5");
            for(Map.Entry<String,Object> item1:dataMap.entrySet()){
                keys.add(item1.getKey());
                super.getStoreCacheDataService().store(item1.getKey(),item1.getValue());
            }
            return true;
        }

        @Override
        public List<String> getStoreKeys() {
            return keys;
        }
    }
}
