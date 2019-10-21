package org.dragonli.tools.configuration;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public class GeneralConfigurationUtil extends ConfigurationUtilBase {
    public String getProperty(String key,String defaultValue){
        return configurableEnvironment.getProperty(key,defaultValue);
    }

    public Map<String,Object> getMap(String key){
        return binder.bind("test1.map1", Bindable.<Map<String,Object>>ofInstance(new HashMap<>())).get();
    }

    public List<Object> getList(String key){
        return binder.bind("test1.map1", Bindable.<List<Object>>ofInstance(new LinkedList<>())).get();
    }
}
