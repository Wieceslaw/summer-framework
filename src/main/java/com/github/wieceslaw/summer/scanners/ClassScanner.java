package com.github.wieceslaw.summer.scanners;

import com.github.wieceslaw.summer.exceptions.ScanningException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassScanner {
    public Set<Class<?>> findPackageClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) {
            throw new ScanningException("Unable to get system classloader input stream");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> lines = reader.lines().toList();
        Set<Class<?>> result = new HashSet<>();
        for (var line : lines) {
            if (line.endsWith(".class")) {
                result.add(getClass(line, packageName));
            } else {
                result.addAll(findPackageClasses(packageName + "." + line));
            }
        }
        return result;
    }
    
    private Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            throw new ScanningException(e);
        }
    }
}
