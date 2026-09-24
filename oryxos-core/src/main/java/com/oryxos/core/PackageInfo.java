package com.oryxos.core;

/**
 * OryxOS 核心引擎模块。
 *
 * <p>核心阶段交付 Agent OS 运行时内核：{@code ReActLoop}（Reason + Act 循环）、
 * {@code PromptBuilder}（组装 system prompt + Bootstrap + Skill + Memory + 对话历史 + Tool 列表）、
 * {@code ToolExecutor}（Tool 调度）、{@code ContextLoader}（Skill/Bootstrap 注入）、
 * Session、Profile、OryxTool 抽象。所有模块依赖本模块。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-core";
    public static final String DESCRIPTION = "核心引擎：ReActLoop、PromptBuilder、ToolExecutor、ContextLoader、Session、Profile、OryxTool 抽象";
}
