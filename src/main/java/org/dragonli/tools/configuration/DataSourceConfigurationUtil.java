package org.dragonli.tools.configuration;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceConfigurationUtil extends ConfigurationUtilBase {
    public DataSource getDataSource(String type,String key) throws Exception{
        if(type == null || "".equals(type=type.trim()))type = "com.zaxxer.hikari.HikariDataSource";
        Class<? extends DataSource> clazz = (Class<? extends DataSource>)Class.forName(type);
        DataSource dataSource = DataSourceBuilder.create().type(clazz).build();
        BindResult<DataSource> result = binder.bind("spring.gamecentral-datasource", Bindable.ofInstance(dataSource));
        return result.get();
    }
}
