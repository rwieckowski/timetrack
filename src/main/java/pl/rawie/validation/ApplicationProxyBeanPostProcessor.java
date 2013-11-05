package pl.rawie.validation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import pl.rawie.validation.annotation.ApplicationService;

import java.lang.reflect.Proxy;

@Component
public class ApplicationProxyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return isApplicationService(bean) ? wrap(bean) : bean;
    }

    private boolean isApplicationService(Object bean) {
        return bean.getClass().isAnnotationPresent(ApplicationService.class);
    }

    private Object wrap(Object bean) {
        System.out.println("Initializing application service proxy for " + bean.getClass());
        return Proxy.newProxyInstance(
                bean.getClass().getClassLoader(),
                bean.getClass().getInterfaces(),
                new ApplicationServiceProxy(bean));
    }
}
