# OryxOS 文档

OryxOS 是一个基于 Java 的企业级 Agent OS：装在企业自己的 K8s/服务器上，作为统一底座跑各种业务 Agent（运维助手、客服助手、HR 助手、销售助手、知识管理助手等），共享一套渠道接入、模型路由、工具调用、记忆系统、沙箱执行能力。**数据完全留在企业自己的基础设施，不锁任何云生态。**

## 四份核心文档

- [业界调研（Why）](./IndustryResearch) — 为什么需要 OryxOS：Agent OS 格局、Java 生态缺位、OryxOS 定位
- [需求文档（What）](./DeamandAnalysis) — 五大核心能力、功能/扩展/社区共建三档分级、验收标准
- [技术方案（How）](./TechnicalSolution) — 关键技术决策、四层架构、9 模块、数据持久化
- [AI 编程实施指引](./AiProgrammingGuide) — AI 编程实施指引：Spec-Kit 流程、5 个 user story 拆解

## 快速开始

```bash
# 要求 JDK 21+ 与 Maven 3.9+
git clone https://github.com/txw-github/oryxos.git
cd oryxos
mvn clean package

# 初始化工作区（生成 .oryxos/）
java -jar oryxos-boot/target/oryxos.jar init

# 配置 Provider（环境变量注入 API key）
export DEEPSEEK_API_KEY=sk-xxxx

# 与 Agent 对话
java -jar oryxos-boot/target/oryxos.jar chat

# 启动 HTTP 服务
java -jar oryxos-boot/target/oryxos.jar serve   # 默认 8080
```
