package com.oryxos.tool;

/**
 * OryxOS Tool 模块（三合一，不拆模块）。
 *
 * <p>内置 Tool：{@code read_file}、{@code write_file}、{@code list_dir}、{@code shell}、
 * {@code http_get}/{@code http_post}。MCP Client（SKILL.md + MCP 零代码接入）、
 * {@code ToolRegistry}、{@code SandboxChecker}（文件路径/Shell 命令/HTTP 域名三层白名单）。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-tool";
    public static final String DESCRIPTION = "内置 Tool（File/Shell/Http）、MCP Client、ToolRegistry、SandboxChecker（三合一）";
}
