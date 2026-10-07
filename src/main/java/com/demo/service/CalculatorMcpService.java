package com.demo.service;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class CalculatorMcpService {

    private final ToolCallbackProvider toolCallbackProvider;

    public CalculatorMcpService(ToolCallbackProvider toolCallbackProvider) {
        this.toolCallbackProvider = toolCallbackProvider;
    }

    public String multiply(double a, double b) {

        ToolCallback[] tools = toolCallbackProvider.getToolCallbacks();

        for (ToolCallback tool : tools) {

            if ("multiply".equals(tool.getToolDefinition().name())) {

                String arguments = String.format(
                        "{\"a\":%s,\"b\":%s}",
                        a,
                        b
                );

                return tool.call(arguments);
            }
        }

        throw new IllegalStateException(
                "MCP Tool 'multiply'를 찾을 수 없습니다."
        );
    }
}