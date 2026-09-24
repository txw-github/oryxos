# OryxOS Docs

OryxOS is a Java-based enterprise Agent OS: it runs on your own K8s, servers, or bare metal as a unified base for all your business agents (ops assistant, support assistant, HR assistant, sales assistant, knowledge assistant, and more) — sharing one set of channels, model routing, tools, memory, and sandbox execution. **Data stays entirely on your own infrastructure. No cloud lock-in.**

## The Four Core Docs

- [Industry Research (Why)](./IndustryResearch.md) — Why OryxOS: the Agent OS landscape, the Java gap, OryxOS positioning
- [Requirements (What)](./DeamandAnalysis.md) — The five core capabilities, three-tier feature grading, acceptance criteria
- [Technical Solution (How)](./TechnicalSolution.md) — Key technical decisions, four-layer architecture, 9 modules, persistence
- [AI Programming Guide](./AiProgrammingGuide.md) — Spec-driven development workflow, 5 user stories

## Quick Start

```bash
# Requires JDK 21+ and Maven 3.9+
git clone https://github.com/txw-github/oryxos.git
cd oryxos
mvn clean package

# Initialize the workspace (creates .oryxos/)
java -jar oryxos-boot/target/oryxos.jar init

# Configure a Provider (API key via env var)
export DEEPSEEK_API_KEY=sk-xxxx

# Chat with an Agent
java -jar oryxos-boot/target/oryxos.jar chat

# Start the HTTP service
java -jar oryxos-boot/target/oryxos.jar serve   # default 8080
```
