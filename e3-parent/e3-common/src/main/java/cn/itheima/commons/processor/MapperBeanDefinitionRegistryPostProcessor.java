package cn.itheima.commons.processor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.Ordered;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author Administrator
 */
public class MapperBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, Ordered {
    
//	private BeanDefinitionRegistry registry;

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        this.registry = registry;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanNamesForType(Mapper.class);
        for (String name : names) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
        }
    }

    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
