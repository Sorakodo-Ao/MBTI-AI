<template>
  <a-row class="globalHeader" align="center" :wrap="false">
    <!--导航栏-->
    <a-col flex="auto">
      <a-menu
        mode="horizontal"
        :selected-keys="selectKey"
        @menu-item-click="handleMenuClick"
      >
        <a-menu-item
          key="0"
          :style="{ padding: 0, marginRight: '38px' }"
          disabled
        >
          <div class="titleBar">
            <img src="../assets/logo.png" class="logoImg" />
            <div class="title">MBTI</div>
          </div>
        </a-menu-item>
        <a-menu-item v-for="item in visibleRoutes" :key="item.path">
          {{ item.name }}
        </a-menu-item>
      </a-menu>
    </a-col>
    <!--登录状态-->
    <a-col flex="100px">
      <div v-if="userStore.loginUser.id">
        {{ userStore.loginUser.userName ?? "匿名用户" }}
      </div>
      <div v-else>
        <a-button type="primary" href="/user/login">登录</a-button>
      </div>
    </a-col>
  </a-row>
</template>

<script setup lang="ts">
import { routes } from "@/router/routes";
import { useRouter } from "vue-router";
import { computed, ref } from "vue";
import { loginUserStore } from "@/store/userStore";
import checkAccess from "@/access/checkAccess";

const userStore = loginUserStore();
const router = useRouter();
//对应key的菜单高亮
const selectKey = ref(["/"]);
router.afterEach((to, from, failure) => {
  selectKey.value = [to.path];
});

//点击菜单跳转到对应key的页面
const handleMenuClick = (key: string) => {
  router.push({ path: key });
};

const visibleRoutes = computed(() => {
  return routes.filter((item) => {
    //是否是隐藏菜单
    if (item.meta?.hideMenu) {
      return false;
    }
    //权限检验
    if (!checkAccess(userStore.loginUser, item.meta?.access as string)) {
      return false;
    }
    return true;
  });
});
</script>

<style scoped>
.titleBar {
  display: flex;
  align-items: center;
}

.logoImg {
  width: 40px;
  height: 40px;
}

.title {
  margin-left: 5px;
  color: black;
}
</style>
