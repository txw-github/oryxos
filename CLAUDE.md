# CLAUDE.md

本文件是 OryxOS 项目的 AI 编程代理工作约定。**在 OryxOS 里写任何代码前先读本文件**，并遵守本文档中的非协商原则。详细背景见 `docs/` 下的四份文档：

- `docs/IndustryResearch.md` — 业界调研（Why）
- `docs/DeamandAnalysis.md` — 需求文档（What）
- `docs/TechnicalSolution.md` — 技术方案（How）
- `docs/AiProgrammingGuide.md` — AI 编程实施指引（怎么用 AI 做）

## 项目是什么

OryxOS 是一个**基于 Java 的企业级 Agent OS**：装在企业自己的 K8s/服务器上，作为统一底座跑各种业务 Agent（运维助手、客服助手、HR 助手等），共享渠道接入、模型路由、工具调用、记忆、沙箱能力。数据完全留在企业自己的基础设施，不锁云生态。

核心阶段交付 **Agent OS 的运行时内核**（地基）；企业级治理层（多租户、SSO、完整审计、Tool Policy）在扩展阶段和社区共建补齐（终局）。

## 技术栈（非协商）

- `JDK 21` + `Spring Boot 3.x` **单体应用**，Maven 多模块（9 个），单二进制部署（fat JAR）
- Spring AI + Spring AI Alibaba（LLM Provider 抽象，复用主流 LLM connector）
- **自实现 ReAct loop**（Agent 核心循环，约数十行 Java）
- Spring MVC（Web Service，Java 21 virtual thread）
- `Picocli` 命令行、SnakeYAML（Profile 解析）
- `SQLite` + Spring Data JPA（Session、审计持久化）
- MCP Java SDK（MCP Client）、Logback + SLF4J（结构化日志）
- Micrometer + Prometheus（扩展阶段）

## 7 条 constitution 原则（不能违反，AI agent 不许自己改）

1. **JDK 21 + Spring Boot 3.x 单体应用**，Maven 多模块（9 个），单二进制部署。
2. **五大核心能力优先**（LLM、ReAct、Memory、Tool、Web Service），支撑模块次之；核心阶段只交付运行时内核。
3. **自实现 ReAct loop**，不直接用 Spring AI 的 Agent 抽象。
4. **Spring AI 只用一半**：只用它的 Provider 抽象、协议转换和 `@Tool` 的 schema 生成，**禁用它的自动 tool 执行**，tool 调度完全由 `ReActLoop` + `ToolExecutor` 控制。这是最容易被写错的一条。
5. **Plugin Tool 三档接入**，主推 SKILL.md + MCP 零代码方式（轻代码自写 MCP server、重代码 `@Tool` 注解）。
6. **核心阶段 SQLite + MEMORY.md 文件存储**，向量检索放扩展阶段；审计相关的 `tool_invocations` 和 `llm_calls` 核心阶段就写入落库（不是只放日志）。
7. **每个 user story 完成后有可演示 demo**，优先级是跑通而非完美。

## 9 个 Maven 模块

| 模块 | 职责 |
|---|---|
| `oryxos-core` | 核心引擎：`ReActLoop`、`PromptBuilder`、`ToolExecutor`、`ContextLoader`、Session、Profile、OryxTool 抽象。所有模块依赖它 |
| `oryxos-provider` | `ProviderService`、Function Calling 适配、provider name 映射 |
| `oryxos-memory` | `MemoryService`（三层统一门面）、`LongTermMemory`、`MemoryTools` |
| `oryxos-tool` | 内置 Tool（File/Shell/Http）、MCP Client、`ToolRegistry`、`SandboxChecker`（**三合一，不拆模块**） |
| `oryxos-web` | `WebServer`、六个 ApiController、`GlobalExceptionHandler`、OpenAPI |
| `oryxos-channel-cli` | CLI Channel 实现 |
| `oryxos-storage` | SQLite 存储层：`sessions`、`tool_invocations`、`llm_calls` 三张表 |
| `oryxos-cli` | Picocli 命令行入口（12 个子命令） |
| `oryxos-boot` | Spring Boot 启动模块，打成 fat JAR |

模块间通过接口解耦；扩展阶段加新 Channel/新 Tool 只加新模块，不改 core。

## 最容易被写错的 5 个点（写代码时重点自查）

1. **启用 Spring AI 的自动 tool 执行** → tool 被调两次。必须禁用，tool 调度只走 `ReActLoop` + `ToolExecutor`。
2. **Provider 用类型扫描**（"扫描容器里所有 ChatModel"）→ 多 Provider 并存时 Bean 类型相同有歧义。必须维护 **provider name → ChatModel 的显式映射**。
3. **把 Tool 拆成多模块**（builtin/skill/mcp）→ 应合并为一个 `oryxos-tool` 模块，共享 OryxTool 抽象和 `ToolRegistry`。
4. **把 SkillLoader 当成 Tool** → SKILL.md 不是可执行 Tool，是注入 system prompt 的指令模板，归 `oryxos-core` 的 `ContextLoader`（跟 Bootstrap 文件一类）。
5. **审计表没落库**（觉得日志够了）→ `tool_invocations`、`llm_calls` 核心阶段就要写入 SQLite，day one 立起可审计的数据地基。

## 五大核心能力 & 5 个 user story

按依赖顺序推进（不按时间、不按优先级标记）：

| User Story | 核心能力 | 依赖 | 验收 demo |
|---|---|---|---|
| US-1 | 对接 LLM | 无（基础） | （与 US-2 合并）Demo 一 |
| US-2 | ReAct 循环 | US-1 | Demo 一（查天气穿衣） |
| US-3 | Memory 三层记忆 | US-2 | Demo 二（跨对话记偏好） |
| US-4 | Plugin Tool 体系 | US-2（与 US-3 并行） | Demo 三（零代码 PR digest） |
| US-5 | Web Service | 前 4 个 | Demo 四 + 五（同步调用、多端点联动） |

推进顺序：`US-1 → US-2 → (US-3 ∥ US-4) → US-5`。每个 user story 结束后跑一次跨 artifact 一致性检查（spec ↔ 代码），发现漂移立刻修正。

## 关键机制速查

- **ReAct 循环**：Reason + Act。组装 Prompt（system prompt + Bootstrap + Skill + Memory + 对话历史 + Tool 列表）→ 调 LLM → 无 Tool 调用则返回；有则执行 Tool、结果追加历史、继续循环；MAX_ITERATIONS 默认 10 次。
- **Memory**：`MemoryService` 三层统一门面（对 ReAct 只暴露一个接口），会话记忆委托 `SessionManager`（SQLite），长期记忆委托 `LongTermMemory`（`.oryxos/memory/MEMORY.md`，append/load/recallByKeyword/truncateIfNeeded，接口预留向量检索升级）。内置 Tool：`save_memory`、`recall_memory`。
- **Tool**：`OryxTool` 抽象（getName/getDescription/getInputSchema/execute）。内置 5 个：`read_file`、`write_file`、`list_dir`、`shell`、`http_get`/`http_post`。Plugin 三档：SKILL.md+MCP（零代码）、自写 MCP server（轻代码）、`@Tool` Spring Bean（重代码）。
- **Sandbox**：`SandboxChecker` 应用层白名单（文件路径白名单、Shell 命令白名单、HTTP 域名白名单）。**不用 SecurityManager**（JDK 17 废弃、JDK 21 不可用）。
- **Web Service**：核心 10 个 REST 端点（会话管理 4、Agent 调用 1、Profile/Memory/Tool 列表 3、health/info 2），路径前缀 `/api/v1/`。核心阶段不做认证、SSE、WebSocket、RBAC。
- **CLI**：12 个子命令：`init`、`status`、`chat`、`serve`、`gateway`、`profile list/create/show/delete`、`provider list`、`tool list`、`session list`。
- **工作区** `.oryxos/`：`profiles/`、`sessions/`、`skills/`、`logs/`、`tools/`、`memory/MEMORY.md`、`AGENTS.md`/`SOUL.md`/`USER.md`（Bootstrap）、`mcp_servers.yaml`、`oryxos.db`。
- **Profile**：YAML，字段 name、description、identity、provider、tools、skills、mcp_servers、channels、bootstrap、settings（max_iterations、max_history_turns）。
- **持久化选型**：SQLite（sessions/tool_invocations/llm_calls 三张表）+ MEMORY.md 关键词检索。核心阶段**不引入向量库**（LanceDB Java 本地版未 GA、其余需外部进程）；接口已预留升级空间。SQLite `ddl-auto=update` 对表结构演进支持弱，改表结构要手动维护脚本或引入 Flyway/Liquibase。

## 开发方式

- **主体开发阶段（从零到 1.0）**：用 Spec-Kit 跑 spec-driven 流程（constitution → specify → plan → tasks → implement），需求文档和技术方案喂给 Spec-Kit，按 5 个 user story 组织任务。
- **增量阶段（小改动）**：手动提示词 + Claude Code，1~3 个文件的小改动不走 Spec-Kit 完整流程。
- 构建命令：`mvn clean package` 生成 fat JAR，`java -jar` 启动。
- 核心阶段不做：Tool 调用并行、上下文动态压缩、Agent 间任务委托、流式响应、fallback/hedge racing、语义检索。

## 需要向项目方确认、不要自作主张的事

- Spec-Kit 命令名/artifacts 格式随版本变化，以实施时官方文档为准（本文档不锁版本细节）。
- 具体实现取舍（如 provider name 映射用 Qualifier 还是配置表）在研发阶段定，但"显式映射不靠类型扫描"这个原则不可破。
- 向量库升级方案（LanceDB Java GA / pgvector / JVector）在扩展阶段决议。
- GraalVM Native Image 引入时机在核心阶段结束后决议（核心阶段用普通 Spring Boot）。
