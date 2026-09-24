import { defineConfig } from 'vitepress'

export default defineConfig({
  title: 'OryxOS',
  titleTemplate: ':title — OryxOS',
  description: '企业级 Agent OS：五大核心能力（LLM、ReAct、Memory、Tool、Web Service）装进一个 Java 单二进制，私有部署、完全可审计、不锁云生态。',
  base: '/',
  cleanUrls: true,
  appearance: 'force-light',
  ignoreDeadLinks: true,

  head: [
    ['link', { rel: 'icon', type: 'image/svg+xml', href: '/oryxos-logo.svg' }],
    ['meta', { name: 'author', content: 'OryxOS' }],
    ['meta', { name: 'keywords', content: 'OryxOS, Agent OS, Java, Spring Boot, ReAct, MCP, enterprise agent, 企业级, Agent 操作系统, 私有部署' }],
    ['meta', { name: 'robots', content: 'index, follow' }],
    ['meta', { property: 'og:type', content: 'website' }],
    ['meta', { property: 'og:site_name', content: 'OryxOS' }],
    ['meta', { property: 'og:title', content: 'OryxOS — 企业级 Agent OS' }],
    ['meta', { property: 'og:description', content: 'Java 原生的企业级 Agent OS：数据留在企业自己的基础设施，不锁任何云生态。' }],
    ['meta', { property: 'og:url', content: 'https://oryxos.qqtang.cloud' }],
  ],

  locales: {
    root: {
      label: '中文',
      lang: 'zh-CN',
      themeConfig: {
        nav: [
          { text: '首页', link: '/' },
          { text: '文档', link: '/docs/IndustryResearch' },
          { text: 'GitHub', link: 'https://github.com/txw-github/oryxos' },
        ],
        sidebar: {
          '/docs/': [
            {
              text: '项目文档',
              items: [
                { text: '业界调研（Why）', link: '/docs/IndustryResearch' },
                { text: '需求文档（What）', link: '/docs/DeamandAnalysis' },
                { text: '技术方案（How）', link: '/docs/TechnicalSolution' },
                { text: 'AI 编程实施指引', link: '/docs/AiProgrammingGuide' },
              ],
            },
          ],
        },
      },
    },
    en: {
      label: 'English',
      lang: 'en-US',
      link: '/en/',
      themeConfig: {
        nav: [
          { text: 'Home', link: '/en/' },
          { text: 'Docs', link: '/en/docs/IndustryResearch' },
          { text: 'GitHub', link: 'https://github.com/txw-github/oryxos' },
        ],
        sidebar: {
          '/en/docs/': [
            {
              text: 'Project Docs',
              items: [
                { text: 'Industry Research (Why)', link: '/en/docs/IndustryResearch' },
                { text: 'Requirements (What)', link: '/en/docs/DeamandAnalysis' },
                { text: 'Technical Solution (How)', link: '/en/docs/TechnicalSolution' },
                { text: 'AI Programming Guide', link: '/en/docs/AiProgrammingGuide' },
              ],
            },
          ],
        },
      },
    },
  },

  themeConfig: {
    siteTitle: false,
    logo: '/oryxos-logo.svg',
    socialLinks: [
      { icon: 'github', link: 'https://github.com/txw-github/oryxos' },
    ],
  },

  sitemap: {
    hostname: 'https://oryxos.qqtang.cloud',
  },
})
