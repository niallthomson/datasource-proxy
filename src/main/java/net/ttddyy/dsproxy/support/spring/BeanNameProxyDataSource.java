package net.ttddyy.dsproxy.support.spring;

import net.ttddyy.dsproxy.proxy.ProxyDataSource;

import org.springframework.beans.factory.BeanNameAware;

/**
 * Extending {@link ProxyDataSource} to use
 * spring bean name(id) as dataSourceName when it is not set.
 *
 * @author Tadaya Tsuyukubo
 */
public class BeanNameProxyDataSource extends ProxyDataSource implements BeanNameAware {

    public void setBeanName(String name) {
        final String dataSourceName = this.getDataSourceName();
        if (dataSourceName == null || "".equals(dataSourceName)) {
            this.setDataSourceName(name);
        }
    }
}
