/**
 * Copyright 2018 Netifi Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.netifi.proteus.spring.core;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.ReflectionUtils;

/**
 * Handles post-processing of {@link io.rsocket.rpc.annotations.Client} annotated fields.
 */
public class ProteusClientAnnotationProcessor implements BeanPostProcessor {
    private ConfigurableListableBeanFactory beanFactory;

    public ProteusClientAnnotationProcessor(ConfigurableListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        scanProteusClientAnnotation(bean, beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected void scanProteusClientAnnotation(Object bean, String beanName) {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fcb = new ProteusClientFieldCallback(beanFactory, bean);
        ReflectionUtils.doWithFields(managedBeanClass, fcb);
    }
}
