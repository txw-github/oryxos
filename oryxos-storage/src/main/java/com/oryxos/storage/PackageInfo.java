package com.oryxos.storage;

/**
 * OryxOS Storage 模块。
 *
 * <p>SQLite 存储层：{@code sessions}、{@code tool_invocations}、{@code llm_calls} 三张表。
 * 审计相关的 tool_invocations 和 llm_calls 核心阶段就写入落库（day one 立起可审计的数据地基）。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-storage";
    public static final String DESCRIPTION = "SQLite 存储层：sessions、tool_invocations、llm_calls 三张表";
}
