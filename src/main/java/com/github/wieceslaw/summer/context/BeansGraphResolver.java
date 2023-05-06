package com.github.wieceslaw.summer.context;

import com.github.wieceslaw.summer.exceptions.CircularBeanDependencyException;
import com.github.wieceslaw.summer.exceptions.NotFoundBeanDependency;

import java.util.*;
import java.util.stream.Collectors;

public class BeansGraphResolver {
    private final Map<String, BeanDefinition> beanDefinitions;
    private final Set<String> beanDefinitionsIds;
    private final Map<String, Set<String>> graph;

    public BeansGraphResolver(Map<String, BeanDefinition> beanDefinitions) {
        this.beanDefinitions = beanDefinitions;
        beanDefinitionsIds = beanDefinitions.values().stream()
                .map(BeanDefinition::getId)
                .collect(Collectors.toSet());
        graph = new HashMap<>();
        createGraph();
    }

    private void createGraph() {
        for (BeanDefinition toBean : beanDefinitions.values()) {
            String toId = toBean.getId();
            if (!graph.containsKey(toId)) {
                graph.put(toId, new HashSet<>());
            }
            for (String fromId : toBean.getDependsOn()) {
                if (!graph.containsKey(fromId)) {
                    graph.put(fromId, new HashSet<>());
                }
                graph.get(fromId).add(toId);
            }
        }
    }

    public List<BeanDefinition> resolve() {
        GraphCycleChecker graphCycleChecker = new GraphCycleChecker(graph);
        for (String beanDependency : graph.keySet()) {
            if (!beanDefinitionsIds.contains(beanDependency)) {
                throw new NotFoundBeanDependency(beanDependency);
            }
        }
        boolean hasCycle = graphCycleChecker.hasCycle();
        if (hasCycle) {
            // TODO: add cycle root
            throw new CircularBeanDependencyException();
        }
        List<String> order = graphCycleChecker.getOrder();
        List<BeanDefinition> result = new ArrayList<>();
        for (int i = order.size(); i-- > 0; ) {
            result.add(beanDefinitions.get(order.get(i)));
        }
        return result;
    }
}
