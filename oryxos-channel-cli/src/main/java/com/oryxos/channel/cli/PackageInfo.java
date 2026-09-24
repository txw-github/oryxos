package com.oryxos.channel.cli;

/**
 * OryxOS CLI Channel 模块。
 *
 * <p>命令行渠道实现。CLI Channel 与 {@code oryxos-cli} 中的 Picocli 入口分离：
 * 渠道负责对话接入（交互式多轮对话），CLI 入口负责子命令分发。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-channel-cli";
    public static final String DESCRIPTION = "CLI Channel 实现";
}
