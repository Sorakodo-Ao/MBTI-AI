<template>
  <div id="userLayout">
    <a-layout style="height: 100vh">
      <a-layout-header class="header">
        <a-space>
          <img src="../assets/logo.png" class="logo" />
          <div>MBTI测试平台</div>
        </a-space>
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

<script setup lang="ts"></script>

<style scoped>
#userLayout {
  text-align: center;
  background: url("https://gw.alipayobjects.com/zos/rmsportal/FfdJeJRQWjEeGTpqgBKj.png")
    0% 0% / 100% 100% no-repeat;
}

.logo {
  width: 48px;
  height: 48px;
}

.header {
  margin: 16px 0;
  font-size: 16px;
}

.footer {
  padding: 16px;
  text-align: center;
  background: #efefef;
}

.content {
  margin-bottom: 26px;
  padding: 16px;
}
</style>
