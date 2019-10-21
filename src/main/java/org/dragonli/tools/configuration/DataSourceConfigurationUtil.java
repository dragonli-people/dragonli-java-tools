package org.dragonli.tools.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceConfigurationUtil extends ConfigurationUtilBase {

    @Value("${data-source-type-suffix:type}")
    protected String typeSuffix;

    @Value("${data-source-config-suffix:data-config}")
    protected String configSuffix;

    /**
     * create DataSource from a whole config node,incude type and data-config
     * @param prefix the path of whole config node.
     *               the path of type is ${prefix}.type,the path of dataSourceConfigPath is ${prefix}.data-config.
     *               "type" and "data-config" can be defined in service.general.db-resource-type-suffix and service.general.db-resource-config-suffix
     * @return a DataSource instance
     * @throws Exception general Exception
     */
    public DataSource getDataSource(String prefix) throws Exception{
        String type = configurableEnvironment.getProperty( prefix + "." + typeSuffix , "com.zaxxer.hikari.HikariDataSource" ) ;
        String key = prefix + "." + configSuffix;
        return getDataSource(type,key);
    }

    /**
     * create DataSource from type and the config path of datasource
     * @param type DataSource type : one of in org.springframework.boot.jdbc.DataSourceBuilder.DATA_SOURCE_TYPE_NAMES
     * @param dataSourceConfigPath the config path of datasource
     * @return a DataSource instance
     * @throws Exception general Exception
     */
    public DataSource getDataSource(String type,String dataSourceConfigPath) throws Exception{
        if(type == null || "".equals(type=type.trim()))type = "com.zaxxer.hikari.HikariDataSource";
        Class<? extends DataSource> clazz = (Class<? extends DataSource>)Class.forName(type);
        DataSource dataSource = DataSourceBuilder.create().type(clazz).build();
        BindResult<DataSource> result = binder.bind(dataSourceConfigPath, Bindable.ofInstance(dataSource));
        return result.get();
    }


}
