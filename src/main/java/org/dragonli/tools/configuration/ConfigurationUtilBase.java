package org.dragonli.tools.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConfigurationUtilBase {

    protected Binder binder;

    protected @Autowired
    ConfigurableEnvironment configurableEnvironment;
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
