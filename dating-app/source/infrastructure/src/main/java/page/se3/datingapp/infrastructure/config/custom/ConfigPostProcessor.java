package page.se3.datingapp.infrastructure.config.custom;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import page.se3.datingapp.domain.config.app.AppConfig;
import page.se3.datingapp.domain.config.helper.ConfigBean;
import page.se3.datingapp.domain.config.helper.ConfigRecursion;

import java.beans.Introspector;
import java.util.List;

@Component
public class ConfigPostProcessor implements BeanFactoryPostProcessor, EnvironmentAware {

    private volatile Environment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {
        AppConfig appConfig = Binder.get(environment)
                .bind("app", AppConfig.class)
                .get();

        if (!ConfigRecursion.isValid(appConfig)) {
            throw new IllegalStateException("AppConfig validation failed");
        }

        List<ConfigBean> configBeans = ConfigRecursion.findAllBeans(appConfig);

        for (ConfigBean config : configBeans) {
            String baseName = Introspector.decapitalize(config.getClass().getSimpleName());
            String uniqueBeanName = baseName + "_" + System.identityHashCode(config);

            beanFactory.registerSingleton(uniqueBeanName, config);
        }
    }
}
