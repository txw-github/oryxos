package com.oryxos.memory;

/**
 * OryxOS Memory 模块。
 *
 * <p>{@code MemoryService}：三层记忆统一门面（对 ReAct 只暴露一个接口）。会话记忆委托
 * {@code SessionManager}（SQLite 持久化），长期记忆委托 {@code LongTermMemory}
 * （.oryxos/memory/MEMORY.md，append/load/recallByKeyword/truncateIfNeeded）。
 * 内置 Tool：{@code save_memory}、{@code recall_memory}。接口预留向量检索升级空间。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-memory";
    public static final String DESCRIPTION = "MemoryService 三层统一门面、LongTermMemory、MemoryTools";
}
