/**
 * 
 */
package org.dragonli.tools.general;

import java.io.IOException;
import java.util.Properties;

/**
 * @author freeangel
 *
 */
public class PropertyPlaceholderConfigurer
		extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	public Properties toProperties() throws IOException
	{
            Properties props = this.mergeProperties();  
            this.convertProperties(props);
            Properties properties=new Properties();
            properties.putAll(props);  

		return properties;
	}
	
}
