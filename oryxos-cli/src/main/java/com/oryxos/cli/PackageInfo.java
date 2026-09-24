package com.oryxos.cli;

/**
 * OryxOS CLI 入口模块。
 *
 * <p>Picocli 命令行入口，12 个子命令：init、status、chat、serve、gateway、
 * profile list/create/show/delete、provider list、tool list、session list。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-cli";
    public static final String DESCRIPTION = "Picocli 命令行入口（12 个子命令）";
}
