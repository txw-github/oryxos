package com.oryxos.provider;

/**
 * OryxOS Provider 模块。
 *
 * <p>{@code ProviderService}：对接 DeepSeek、通义、Kimi、智谱、混元、豆包、Anthropic、OpenAI 等主流模型。
 * 维护 provider name → ChatModel 的显式映射（禁类型扫描，避免多 Provider 并存时 Bean 类型歧义）。
 * 包含 Function Calling 适配与 provider name 映射。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-provider";
    public static final String DESCRIPTION = "ProviderService、Function Calling 适配、provider name 映射";
}
