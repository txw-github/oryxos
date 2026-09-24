<script setup>
import { computed } from 'vue'
import { useData } from 'vitepress'

const { lang } = useData()
const isZh = computed(() => lang.value === 'zh-CN')
const t = (zh, en) => isZh.value ? zh : en

const capabilities = computed(() => [
  {
    icon: '🤖',
    title: t('对接 LLM', 'LLM Providers'),
    subtitle: t('Provider 抽象层 · 显式模型映射 · 运行时切换', 'Provider abstraction · explicit model mapping · runtime switch'),
    code: `# 一个 Profile 对应一个 Agent（YAML）
# .oryxos/profiles/ops-assistant.yaml
name: ops-assistant
description: 企业运维助手
provider:
  name: deepseek      # 或 qwen / kimi / anthropic / openai ...
  model: deepseek-chat
tools:
  - http_get
  - shell
  - read_file
channels:
  - cli
settings:
  max_iterations: 10`,
  },
  {
    icon: '🔄',
    title: t('ReAct 循环', 'ReAct Loop'),
    subtitle: t('自实现核心循环 · Reason + Act · 多步工具调用', 'Self-implemented loop · Reason + Act · multi-step tool use'),
    code: `# Agent 核心循环（约数十行 Java）
# 组装 Prompt → 调 LLM → 有 Tool 调用？
#   否 → 返回最终响应
#   是 → 执行 Tool → 结果追加历史 → 继续循环
# MAX_ITERATIONS 默认 10 次，防死循环

用户 > 查一下北京天气并告诉我穿什么
Agent: 北京今天 24°C，多云，适合穿薄长袖或短袖加外套……`,
  },
  {
    icon: '🧠',
    title: t('Memory 三层记忆', 'Three-layer Memory'),
    subtitle: t('会话记忆 · 长期记忆 · 统一门面', 'Session · long-term · unified facade'),
    code: `# MemoryService 对 ReAct 只暴露一个接口
# 会话记忆  → SQLite（SessionManager）
# 长期记忆  → .oryxos/memory/MEMORY.md
#   内置 Tool: save_memory / recall_memory
#   接口预留向量检索升级空间

# 第一次对话：记住用户偏好
> 记住我喜欢用简洁风格回复
# 下次对话：自动 recall 并应用
Agent: 好的，后续回复保持简洁。`,
  },
  {
    icon: '🛠️',
    title: t('Tool 体系', 'Tool System'),
    subtitle: t('内置 Tool · MCP 零代码 · 三档接入', 'Built-in · MCP no-code · three tiers'),
    code: `# 零代码：SKILL.md + MCP
#   .oryxos/skills/daily-pr-digest.md
#   mcp_servers.yaml 配 github-mcp / slack-mcp
#   Profile 引用 Skill + MCP server → 完成

# 轻代码：自写 MCP server（任何语言）
# 重代码：@Tool 注解（Spring Bean）

# 沙箱白名单（SandboxChecker）：
#   文件路径 / Shell 命令 / HTTP 域名`,
  },
  {
    icon: '🌐',
    title: t('Web Service', 'Web Service'),
    subtitle: t('10 个 REST 端点 · 业务系统唯一通道', '10 REST endpoints · the only gateway for business systems'),
    code: `# 创建会话
curl -X POST http://localhost:8080/api/v1/sessions \\
  -H "Content-Type: application/json" \\
  -d '{"profile_name": "ops-assistant"}'

# 发消息
curl -X POST http://localhost:8080/api/v1/sessions/{id}/messages \\
  -H "Content-Type: application/json" \\
  -d '{"content": "查看生产环境 CPU 使用率"}'`,
  },
])

const enterprise = computed(() => [
  {
    icon: '🔒',
    title: t('私有可审计', 'Private & Auditable'),
    desc: t('数据与部署完全在企业自己手里，每次 LLM 调用和 Tool 调用写入 SQLite 审计表，day one 立起可审计的数据地基。', 'Data and deployment stay entirely in your hands. Every LLM call and tool invocation is written to SQLite audit tables from day one.'),
  },
  {
    icon: '🛡️',
    title: t('沙箱白名单', 'Sandbox Whitelist'),
    desc: t('文件路径、Shell 命令、HTTP 域名三层白名单校验，应用层隔离工具执行边界，不用已废弃的 SecurityManager。', 'Three-tier whitelist for file paths, shell commands, and HTTP domains — app-layer isolation without the deprecated SecurityManager.'),
  },
  {
    icon: '🔑',
    title: t('凭证不落地', 'No Credentials at Rest'),
    desc: t('API key 通过环境变量或独立配置加载，不明文写死在 Profile 里。', 'API keys load via environment variables or separate config — never hardcoded in profiles.'),
  },
  {
    icon: '📦',
    title: t('单二进制部署', 'Single Binary'),
    desc: t('JDK 21 + Spring Boot 3.x 单体应用，mvn clean package 打出一个 fat JAR，像装一个 Spring Boot 应用一样自然。', 'JDK 21 + Spring Boot 3.x monolith — one fat JAR from mvn clean package. Deploys like any Spring Boot app.'),
  },
])

const scenarios = computed(() => [
  {
    num: '01',
    title: t('运维助手', 'Ops Assistant'),
    desc: t('告警分诊、日志查询、服务重启。CLI 或 REST API 接入，回复简洁专业。', 'Alert triage, log queries, service restarts. Via CLI or REST API — concise, professional replies.'),
  },
  {
    num: '02',
    title: t('客服助手', 'Customer Support'),
    desc: t('共享底座跑客服 Agent，多轮对话 + 工单工具 + 长期记忆记住客户偏好。', 'Customer-support Agent on the shared base: multi-turn chat, ticketing tools, long-term memory of customer preferences.'),
  },
  {
    num: '03',
    title: t('HR 助手', 'HR Assistant'),
    desc: t('制度问答、假勤查询。通过 HTTP 接入企业现有 OA 系统。', 'Policy Q&A, leave queries. Integrates into your existing OA systems over HTTP.'),
  },
  {
    num: '04',
    title: t('销售助手', 'Sales Assistant'),
    desc: t('客户资料整理、话术生成、跟进提醒。多个业务 Agent 共享同一套渠道与记忆底座。', 'Contact cleanup, pitch generation, follow-up reminders. Multiple agents share the same channel and memory base.'),
  },
  {
    num: '05',
    title: t('知识管理助手', 'Knowledge Assistant'),
    desc: t('文档检索、FAQ 维护。SKILL.md + MCP 零代码扩展新场景。', 'Document retrieval, FAQ maintenance. Extend with new SKILL.md + MCP scenarios with zero code.'),
  },
  {
    num: '06',
    title: t('统一底座', 'One Shared Base'),
    desc: t('多个 Agent 共享一套渠道接入、模型路由、工具调用、记忆系统、沙箱执行能力，上一个新 Agent 只需配一份 Profile。', 'All agents share one set of channels, model routing, tools, memory, and sandbox — adding an Agent is just one more Profile.'),
  },
])

const sdkCards = computed(() => [
  {
    icon: '🧩',
    title: t('MCP · 零代码', 'MCP · Zero Code'),
    desc: t('社区现成的 MCP server 直接接入。写一份 SKILL.md 描述任务，配置 mcp_servers.yaml，Profile 一引用就完成。', 'Plug in community MCP servers directly. Write a SKILL.md, configure mcp_servers.yaml, reference it in a Profile — done.'),
    installs: ['mcp_servers.yaml', 'skills/daily-pr-digest.md'],
    badges: ['SKILL.md', 'MCP', '零代码'],
  },
  {
    icon: '⚡',
    title: t('自写 MCP Server', 'Your Own MCP Server'),
    desc: t('任何语言都能写 MCP server，JSON-RPC over stdio 或 SSE，启动时自动 tools/list 注册到 ToolRegistry。', 'Write an MCP server in any language — JSON-RPC over stdio or SSE. Auto-registered via tools/list at startup.'),
    installs: ['MCP SDK (任何语言)', 'stdio / SSE'],
    badges: ['轻代码'],
  },
  {
    icon: '☕',
    title: t('@Tool 注解 · 重代码', '@Tool Annotation'),
    desc: t('Spring AI 负责扫描方法生成 JSON Schema，OryxOS 在其上加一层 OryxTool 抽象统一接口。', 'Spring AI generates JSON Schema from annotated methods; OryxOS wraps them in the OryxTool abstraction.'),
    installs: ['@Tool', 'OryxTool'],
    badges: ['重代码'],
  },
])
</script>

<template>
  <div class="oryxos-page">

    <!-- ── HERO ── -->
    <section class="oryxos-hero">
      <div class="oryxos-hero-inner">
        <div class="oryxos-badge">
          <span class="oryxos-badge-dot"></span>
          {{ t('Java 原生 · 私有可审计 · 不锁云生态', 'Java-native · Private & auditable · No cloud lock-in') }}
        </div>

        <img src="/oryxos-logo.svg" alt="OryxOS logo" class="oryxos-hero-logo" />

        <p class="oryxos-title-sub">{{ t('企业级 Agent OS', 'Enterprise Agent OS') }}</p>

        <p class="oryxos-hero-desc">
          {{ t('OryxOS 装在企业自己的 K8s、服务器或物理机上，作为统一底座跑各种业务 Agent。共享一套渠道接入、模型路由、工具调用、记忆系统、沙箱执行能力——数据完全留在企业自己的基础设施。', 'OryxOS runs on your own K8s, servers, or bare metal — a unified base for all your business agents. One shared set of channels, model routing, tools, memory, and sandbox. Data never leaves your infrastructure.') }}
        </p>

        <div class="oryxos-hero-actions">
          <a class="oryxos-btn-primary" :href="t('/docs/', '/en/docs/')">
            {{ t('开始使用', 'Get Started') }} →
          </a>
          <a class="oryxos-btn-ghost" :href="t('/docs/TechnicalSolution', '/en/docs/TechnicalSolution')">
            {{ t('技术方案', 'Technical Solution') }}
          </a>
          <a class="oryxos-btn-ghost" href="https://github.com/oryx-labs/oryxos" target="_blank" rel="noopener">
            GitHub
          </a>
        </div>

        <div class="oryxos-hero-note">
          {{ t('JDK 21 · Spring Boot 3.x · 自实现 ReAct 循环 · SQLite 审计 · MCP · Picocli · 单二进制 fat JAR', 'JDK 21 · Spring Boot 3.x · Self-implemented ReAct loop · SQLite audit · MCP · Picocli · Single fat JAR') }}
        </div>
      </div>
    </section>

    <!-- ── PROBLEM ── -->
    <section class="oryxos-section">
      <div class="oryxos-section-inner">
        <div class="oryxos-problem">
          <div class="oryxos-problem-text">
            <h2 class="oryxos-section-title">{{ t('为什么需要 OryxOS', 'Why OryxOS') }}</h2>
            <p>{{ t('开源 Agent 生态已有 OpenClaw（Node.js）和 Hermes Agent（Python），但 Java 生态里没有任何一个项目把 Agent OS 作为定位——而 Java 恰恰是大量企业现有后端的事实标准。', 'The open-source agent ecosystem has OpenClaw (Node.js) and Hermes Agent (Python), but no Java project positions itself as an Agent OS — yet Java is the de-facto standard in enterprise backends.') }}</p>
            <p class="oryxos-problem-item">
              <strong>{{ t('① 数据必须留在企业自己手里？', '① Data must stay inside the enterprise?') }}</strong>
              {{ t('私有部署、完全可审计、能纳入现有 IT 治理。', 'Private deployment, fully auditable, fits existing IT governance.') }}
            </p>
            <p class="oryxos-problem-item">
              <strong>{{ t('② 企业 Java 体系如何对接？', '② How do you integrate with existing Java systems?') }}</strong>
              {{ t('标准 Spring Boot 工程，跟现有 Java 体系直接对接，Tool 用 MCP 任何语言都能写。', 'A standard Spring Boot project that plugs straight into your Java stack; tools are MCP — writable in any language.') }}
            </p>
            <p class="oryxos-solution-line">{{ t('OryxOS 填的就是这个位置：Java 原生、装好就跑、可审计、能进企业现有安全审查流程的 Agent 运行时。', 'OryxOS fills exactly that gap: a Java-native, run-and-done, auditable Agent runtime that passes enterprise security review.') }}</p>
          </div>
          <div class="oryxos-problem-compare">
            <div class="oryxos-compare-item oryxos-compare-bad">
              <div class="oryxos-compare-label">{{ t('今天的做法', 'Today') }}</div>
              <div class="oryxos-compare-rows">
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon">✗</span>
                  <span>{{ t('Node.js / Python Agent 需要胶水代码接入 Java 后端', 'Node.js / Python agents need glue code into the Java backend') }}</span>
                </div>
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon">✗</span>
                  <span>{{ t('私有可审计需要二次加固', 'Private auditability requires re-hardening') }}</span>
                </div>
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon">✗</span>
                  <span>{{ t('多租户 / SSO / 完整审计空白', 'No multi-tenancy / SSO / full audit') }}</span>
                </div>
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon">✗</span>
                  <span>{{ t('数据与云生态绑定', 'Data tied to cloud ecosystems') }}</span>
                </div>
              </div>
            </div>
            <div class="oryxos-compare-item oryxos-compare-good">
              <div class="oryxos-compare-label">OryxOS</div>
              <div class="oryxos-compare-rows">
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon oryxos-icon-ok">✓</span>
                  <span>{{ t('Java / Spring Boot 原生对齐', 'Native Java / Spring Boot alignment') }}</span>
                </div>
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon oryxos-icon-ok">✓</span>
                  <span>{{ t('Day one 私有可审计设计', 'Day-one private & auditable design') }}</span>
                </div>
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon oryxos-icon-ok">✓</span>
                  <span>{{ t('扩展阶段补齐多租户 / SSO / 完整审计', 'Multi-tenancy / SSO / full audit in extension phase') }}</span>
                </div>
                <div class="oryxos-compare-row">
                  <span class="oryxos-compare-icon oryxos-icon-ok">✓</span>
                  <span>{{ t('数据完全留在企业自己的基础设施', 'Data stays entirely on your infrastructure') }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ── FLOW DIAGRAM ── -->
    <section class="oryxos-section oryxos-flow-section">
      <div class="oryxos-section-inner">
        <div class="oryxos-section-header">
          <div class="oryxos-section-tag">{{ t('系统架构', 'Architecture') }}</div>
          <h2 class="oryxos-section-title">{{ t('模块化单体，单二进制交付', 'Modular monolith, single-binary delivery') }}</h2>
        </div>
        <img src="/oryxos-architecture.svg" alt="OryxOS architecture" class="oryxos-flow-img" />
      </div>
    </section>

    <!-- ── CAPABILITIES ── -->
    <section class="oryxos-section oryxos-primitives-section">
      <div class="oryxos-section-inner oryxos-primitives-inner">
        <div class="oryxos-section-header">
          <div class="oryxos-section-tag">{{ t('五大核心能力', 'Five Core Capabilities') }}</div>
          <h2 class="oryxos-section-title">{{ t('运行时内核的五个支柱', 'The five pillars of the runtime kernel') }}</h2>
        </div>
        <div class="oryxos-primitives">
          <div v-for="p in capabilities" :key="p.title" class="oryxos-primitive">
            <div class="oryxos-primitive-header">
              <span class="oryxos-primitive-icon">{{ p.icon }}</span>
              <div>
                <h3 class="oryxos-primitive-title">{{ p.title }}</h3>
                <p class="oryxos-primitive-subtitle">{{ p.subtitle }}</p>
              </div>
            </div>
            <pre class="oryxos-code"><code>{{ p.code }}</code></pre>
          </div>
        </div>
      </div>
    </section>

    <!-- ── ENTERPRISE ── -->
    <section class="oryxos-section">
      <div class="oryxos-section-inner">
        <div class="oryxos-section-header">
          <div class="oryxos-section-tag">{{ t('企业级基因', 'Enterprise DNA') }}</div>
          <h2 class="oryxos-section-title">{{ t('Day one 设计', 'Day-one design') }}</h2>
        </div>
        <div class="oryxos-enterprise-grid">
          <div v-for="e in enterprise" :key="e.title" class="oryxos-enterprise-card">
            <div class="oryxos-enterprise-icon">{{ e.icon }}</div>
            <h3 class="oryxos-enterprise-title">{{ e.title }}</h3>
            <p class="oryxos-enterprise-desc">{{ e.desc }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- ── SCENARIOS ── -->
    <section class="oryxos-section">
      <div class="oryxos-section-inner">
        <div class="oryxos-section-header">
          <div class="oryxos-section-tag">{{ t('真实场景', 'Real Scenarios') }}</div>
          <h2 class="oryxos-section-title">{{ t('多个业务 Agent，一个共享底座', 'Many business agents, one shared base') }}</h2>
        </div>
        <div class="oryxos-scenarios">
          <div v-for="s in scenarios" :key="s.num" class="oryxos-scenario">
            <div class="oryxos-scenario-num">{{ s.num }}</div>
            <div>
              <h3 class="oryxos-scenario-title">{{ s.title }}</h3>
              <p class="oryxos-scenario-desc">{{ s.desc }}</p>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ── SDK / TOOLS ── -->
    <section class="oryxos-section oryxos-sdk-section">
      <div class="oryxos-section-inner">
        <div class="oryxos-section-header">
          <div class="oryxos-section-tag">{{ t('Tool 接入', 'Tool Integration') }}</div>
          <h2 class="oryxos-section-title">{{ t('三档接入，按需选择', 'Three tiers — pick what fits') }}</h2>
        </div>
        <div class="oryxos-sdk-cards">
          <div v-for="(card, i) in sdkCards" :key="card.title" class="oryxos-sdk-card" :class="{ 'oryxos-sdk-card-featured': i === 0 }">
            <div class="oryxos-sdk-card-icon">{{ card.icon }}</div>
            <h3 class="oryxos-sdk-card-title">{{ card.title }}</h3>
            <p class="oryxos-sdk-card-desc">{{ card.desc }}</p>
            <div class="oryxos-sdk-installs">
              <code v-for="inst in card.installs" :key="inst">{{ inst }}</code>
            </div>
            <div class="oryxos-sdk-badges">
              <span v-for="b in card.badges" :key="b" class="oryxos-sdk-badge">{{ b }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ── CTA ── -->
    <section class="oryxos-section oryxos-cta-section">
      <div class="oryxos-section-inner">
        <div class="oryxos-cta">
          <h2 class="oryxos-cta-title">{{ t('开始构建', 'Start Building') }}</h2>
          <p class="oryxos-cta-desc">{{ t('要求 JDK 21+ 与 Maven 3.9+，一分钟起一个可跑的 Agent 运行时。', 'Requires JDK 21+ and Maven 3.9+. Get a runnable Agent runtime in a minute.') }}</p>
          <pre class="oryxos-code oryxos-cta-code"><code>git clone https://github.com/txw-github/oryxos.git
cd oryxos
mvn clean package

# 初始化工作区
java -jar oryxos-boot/target/oryxos.jar init

# 配置 Provider（环境变量注入 API key）
export DEEPSEEK_API_KEY=sk-xxxx

# 与 Agent 对话
java -jar oryxos-boot/target/oryxos.jar chat

# 启动 HTTP 服务
java -jar oryxos-boot/target/oryxos.jar serve   # 默认 8080</code></pre>
          <div class="oryxos-cta-links">
            <a class="oryxos-btn-primary" :href="t('/docs/', '/en/docs/')">{{ t('查看文档', 'Read the Docs') }}</a>
            <a class="oryxos-btn-ghost" href="https://github.com/txw-github/oryxos" target="_blank" rel="noopener">GitHub</a>
          </div>
        </div>
      </div>
    </section>

  </div>
</template>

<style scoped>
.oryxos-page {
  min-height: 100vh;
  background: #ffffff;
  color: #000000;
  font-family: inherit;
}

/* ── Hero ── */
.oryxos-hero {
  position: relative;
  padding: 84px 24px 72px;
  text-align: center;
  overflow: hidden;
}
.oryxos-hero-inner {
  position: relative;
  max-width: 780px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.oryxos-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 6px 16px;
  border-radius: 20px;
  border: 1px solid #d4d4d4;
  background: #f5f5f5;
  color: #555555;
  font-size: 12px;
  margin-bottom: 28px;
}
.oryxos-badge-dot {
  width: 6px; height: 6px;
  border-radius: 50%;
  background: #0f766e;
  animation: pulse 2s infinite;
}
@keyframes pulse {
  0%,100% { opacity:1; transform:scale(1); }
  50% { opacity:0.4; transform:scale(1.4); }
}
.oryxos-hero-logo {
  width: 280px;
  max-width: 70vw;
  height: auto;
  margin-bottom: 18px;
}
.oryxos-title-sub {
  font-size: 22px;
  font-weight: 700;
  color: #000000;
  margin: 0 0 18px;
}
.oryxos-hero-desc {
  font-size: 16px;
  line-height: 1.7;
  color: #444444;
  max-width: 640px;
  margin: 0 0 32px;
}
.oryxos-hero-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  justify-content: center;
  margin-bottom: 20px;
}
.oryxos-btn-primary {
  padding: 11px 28px;
  border-radius: 8px;
  background: #0f766e;
  color: #ffffff;
  font-weight: 600;
  font-size: 14px;
  text-decoration: none;
  transition: opacity 0.2s, transform 0.15s;
}
.oryxos-btn-primary:hover { opacity: 0.85; transform: translateY(-1px); }
.oryxos-btn-ghost {
  padding: 11px 28px;
  border-radius: 8px;
  border: 1px solid #d4d4d4;
  color: #333333;
  font-weight: 600;
  font-size: 14px;
  text-decoration: none;
  transition: border-color 0.2s, background 0.2s;
}
.oryxos-btn-ghost:hover { border-color: #0f766e; background: #f0fdfa; }
.oryxos-hero-note {
  font-size: 12px;
  color: #999999;
}

/* ── Section ── */
.oryxos-section { padding: 72px 24px; }
.oryxos-section-inner { max-width: 1000px; margin: 0 auto; }
.oryxos-primitives-inner { max-width: 1400px; }
.oryxos-section-header { text-align: center; margin-bottom: 48px; }
.oryxos-section-tag {
  display: inline-block;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  color: #0f766e;
  padding: 4px 12px;
  border-radius: 20px;
  border: 1px solid #99f6e4;
  background: #f0fdfa;
  margin-bottom: 14px;
}
.oryxos-section-title {
  font-size: clamp(22px, 4vw, 32px);
  font-weight: 700;
  color: #000000;
  margin: 0 0 12px;
}
.oryxos-section-desc {
  font-size: 15px;
  color: #666666;
  max-width: 600px;
  margin: 0 auto;
  line-height: 1.6;
}

/* ── Problem ── */
.oryxos-problem {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 48px;
  align-items: start;
}
.oryxos-problem-text p { color: #666666; line-height: 1.7; margin: 0 0 14px; font-size: 15px; }
.oryxos-problem-item strong { color: #000000; display: block; margin-bottom: 4px; }
.oryxos-solution-line { color: #000000 !important; font-weight: 600; }
.oryxos-problem-compare { display: flex; flex-direction: column; gap: 16px; }
.oryxos-compare-item {
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #e5e5e5;
}
.oryxos-compare-bad { background: #fafafa; }
.oryxos-compare-good { background: #f0fdfa; border-color: #99f6e4; }
.oryxos-compare-label { font-size: 11px; font-weight: 700; color: #999999; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 0.08em; }
.oryxos-compare-rows { display: flex; flex-direction: column; gap: 8px; }
.oryxos-compare-row { display: flex; align-items: flex-start; gap: 10px; font-size: 13px; color: #555555; line-height: 1.5; }
.oryxos-compare-icon { flex-shrink: 0; font-style: normal; color: #bbbbbb; font-weight: 700; width: 14px; }
.oryxos-icon-ok { color: #0f766e; }

/* ── Flow diagram ── */
.oryxos-flow-section { padding: 72px 24px 96px; }
.oryxos-flow-img {
  width: 100%;
  display: block;
  border: 1px solid #e5e5e5;
  border-radius: 12px;
}

/* ── Primitives ── */
.oryxos-primitives-section { background: #f8fafc; }
.oryxos-primitives { display: grid; grid-template-columns: repeat(3, minmax(0, 1fr)); grid-auto-rows: 1fr; gap: 16px; }
.oryxos-primitive {
  padding: 20px;
  border-radius: 14px;
  border: 1px solid #e5e5e5;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  gap: 12px;
  transition: border-color 0.2s, box-shadow 0.2s;
  min-width: 0;
  overflow: hidden;
}
.oryxos-primitive .oryxos-code { flex: 1; }
.oryxos-primitive:hover { border-color: #0f766e; box-shadow: 0 4px 16px rgba(15,118,110,0.10); }
.oryxos-primitive-header { display: flex; align-items: flex-start; gap: 12px; }
.oryxos-primitive-icon { font-size: 28px; flex-shrink: 0; }
.oryxos-primitive-title { font-size: 17px; font-weight: 700; color: #000000; margin: 0 0 2px; }
.oryxos-primitive-subtitle { font-size: 12px; color: #999999; margin: 0; }
.oryxos-code {
  background: #f8fafc;
  border: 1px solid #e5e5e5;
  border-radius: 8px;
  padding: 14px 16px;
  font-size: 12px;
  line-height: 1.6;
  color: #334155;
  overflow-x: auto;
  margin: 0;
  white-space: pre;
}
.oryxos-code code { font-family: 'JetBrains Mono', 'Fira Code', monospace; background: none; color: inherit; }

/* ── Enterprise ── */
.oryxos-enterprise-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 20px;
}
.oryxos-enterprise-card {
  padding: 24px 20px;
  border-radius: 14px;
  border: 1px solid #e5e5e5;
  background: #ffffff;
  display: flex;
  flex-direction: column;
  gap: 10px;
  transition: border-color 0.2s, box-shadow 0.2s;
}
.oryxos-enterprise-card:hover { border-color: #0f766e; box-shadow: 0 4px 16px rgba(15,118,110,0.10); }
.oryxos-enterprise-icon { font-size: 28px; }
.oryxos-enterprise-title { font-size: 16px; font-weight: 700; color: #000000; margin: 0; }
.oryxos-enterprise-desc { font-size: 13px; color: #666666; line-height: 1.6; margin: 0; flex: 1; }

/* ── Scenarios ── */
.oryxos-scenarios { display: grid; grid-template-columns: repeat(2, 1fr); gap: 20px; }
.oryxos-scenario {
  display: flex;
  gap: 16px;
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #e5e5e5;
  background: #fafafa;
}
.oryxos-scenario-num {
  font-size: 28px;
  font-weight: 900;
  color: #e5e5e5;
  line-height: 1;
  flex-shrink: 0;
  font-variant-numeric: tabular-nums;
}
.oryxos-scenario-title { font-size: 15px; font-weight: 600; color: #000000; margin: 0 0 6px; }
.oryxos-scenario-desc { font-size: 13px; color: #666666; line-height: 1.6; margin: 0; }

/* ── SDK ── */
.oryxos-sdk-section { background: #f8fafc; }
.oryxos-sdk-cards {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}
.oryxos-sdk-card {
  background: #ffffff;
  border: 1px solid #e5e5e5;
  border-radius: 16px;
  padding: 28px 24px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}
.oryxos-sdk-card-featured { border-color: #0f766e; box-shadow: 0 4px 16px rgba(15,118,110,0.10); }
.oryxos-sdk-card-icon { font-size: 28px; }
.oryxos-sdk-card-title { font-size: 17px; font-weight: 700; color: #000000; margin: 0; }
.oryxos-sdk-card-desc { font-size: 14px; color: #666666; line-height: 1.6; margin: 0; flex: 1; }
.oryxos-sdk-installs { display: flex; flex-direction: column; gap: 6px; }
.oryxos-sdk-installs code {
  font-family: 'JetBrains Mono', 'Fira Code', monospace;
  font-size: 12px;
  background: #f8fafc;
  border: 1px solid #e5e5e5;
  border-radius: 6px;
  padding: 5px 10px;
  color: #0f766e;
  display: block;
}
.oryxos-sdk-badges { display: flex; flex-wrap: wrap; gap: 8px; }
.oryxos-sdk-badge {
  padding: 3px 10px;
  border-radius: 12px;
  font-size: 11px;
  font-weight: 700;
  background: #f0fdfa;
  border: 1px solid #99f6e4;
  color: #0f766e;
}

/* ── CTA ── */
.oryxos-cta-section { background: #f8fafc; }
.oryxos-cta { text-align: center; max-width: 680px; margin: 0 auto; }
.oryxos-cta-title { font-size: 28px; font-weight: 700; color: #000000; margin: 0 0 12px; }
.oryxos-cta-desc { font-size: 15px; color: #666666; margin: 0 0 24px; }
.oryxos-cta-code { text-align: left; margin-bottom: 28px; }
.oryxos-cta-links { display: flex; gap: 12px; justify-content: center; flex-wrap: wrap; }

/* ── Responsive ── */
@media (max-width: 1100px) {
  .oryxos-enterprise-grid { grid-template-columns: repeat(2, 1fr); }
}
@media (max-width: 900px) {
  .oryxos-sdk-cards { grid-template-columns: 1fr; }
}
@media (max-width: 768px) {
  .oryxos-hero { padding: 56px 20px 48px; }
  .oryxos-problem { grid-template-columns: 1fr; }
  .oryxos-primitives { grid-template-columns: 1fr; }
  .oryxos-scenarios { grid-template-columns: 1fr; }
  .oryxos-enterprise-grid { grid-template-columns: 1fr; }
  .oryxos-section { padding: 48px 20px; }
}
</style>
