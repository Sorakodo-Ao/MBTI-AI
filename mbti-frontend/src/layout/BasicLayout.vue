<template>
  <div id="basicLayout">
    <a-layout style="height: 100vh">
      <a-layout-header class="header">
        <GlobalHeader />
      </a-layout-header>
      <a-layout-content class="content">
        <router-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        站长：<a
          href="https://www.baidu.com"
          target="_blank"
          style="text-decoration: none"
        >
          Sorako
        </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import GlobalHeader from "@/components/GlobalHeader.vue";
</script>

<style scoped>
#basicLayout {
}

.footer {
  padding: 16px;
  text-align: center;
  background: #efefef;
}

.content {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto 26px;
  padding: 16px;
}

.header {
  margin-bottom: 16px;
  box-shadow: #eee 1px 1px 5px;
}
</style>
