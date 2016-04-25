package tech.arauk.ark.arel.support;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ReflectionUtils {
    public static Set<Class<?>> getClassesByPackage(String packageName) {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        FilterBuilder filterBuilder = new FilterBuilder();
        filterBuilder.include(FilterBuilder.prefix(packageName));

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setScanners(new SubTypesScanner(false), new ResourcesScanner());
        configurationBuilder.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])));
        configurationBuilder.filterInputsBy(filterBuilder);

        Reflections reflections = new Reflections(configurationBuilder);

        return reflections.getSubTypesOf(Object.class);
    }
}
