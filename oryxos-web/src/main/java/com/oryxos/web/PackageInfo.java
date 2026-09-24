package com.oryxos.web;

/**
 * OryxOS Web Service 模块。
 *
 * <p>{@code WebServer} + 六个 ApiController（会话管理、Agent 调用、Profile/Memory/Tool 列表、
 * health/info）+ {@code GlobalExceptionHandler} + OpenAPI。核心 10 个 REST 端点，路径前缀
 * /api/v1/。核心阶段不做认证、SSE、WebSocket、RBAC。</p>
 */
public final class PackageInfo {

    private PackageInfo() {
    }

    public static final String NAME = "oryxos-web";
    public static final String DESCRIPTION = "WebServer、六个 ApiController、GlobalExceptionHandler、OpenAPI";
}
