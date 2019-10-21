package org.dragonli.tools.configuration;

import org.apache.log4j.Logger;
import org.dragonli.tools.general.DataCachePool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;

public class ConfigurationUtilBase {

    protected final Logger logger = Logger.getLogger(getClass());

    protected Binder binder;

    public Binder getBinder() {
        return binder;
    }

    public ConfigurableEnvironment getConfigurableEnvironment() {
        return configurableEnvironment;
    }

    @Autowired
    protected ConfigurableEnvironment configurableEnvironment;
    public void init() throws Exception{
        binder = Binder.get(configurableEnvironment);

//
//        System.out.println(configurableEnvironment==null);
//        String v = configurableEnvironment.getProperty("spring.netty-service.privatekey","abc123");
//        System.out.println(v);
//        DataSource dataSource = DataSourceBuilder.create().build();
//        BindResult<DataSource> result = binder.bind("spring.gamecentral-datasource", Bindable.<DataSource>ofInstance(dataSource));
//        System.out.println(dataSource.getConnection().getMetaData().getURL());
//        Map<String,Object> map1 = new HashMap<>();
//        BindResult<Map<String,Object>> result1 = binder.bind("test1.map1", Bindable.<Map<String,Object>>ofInstance(map1));
//        System.out.println(map1.size());

//        configurableEnvironment = null;
    }



}
