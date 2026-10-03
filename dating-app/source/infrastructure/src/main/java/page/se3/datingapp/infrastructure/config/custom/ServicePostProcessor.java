package page.se3.datingapp.infrastructure.config.custom;

import lombok.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.service.SubService;

@Component
public class ServicePostProcessor implements BeanDefinitionRegistryPostProcessor {

    private static final String BASE_PACKAGE = "page.se3.datingapp";

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        var scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RootService.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(SubService.class));

        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(BASE_PACKAGE)) {
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                registry.registerBeanDefinition(beanClassName, beanDefinition);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(@NonNull ConfigurableListableBeanFactory beanFactory) throws BeansException {}
}
