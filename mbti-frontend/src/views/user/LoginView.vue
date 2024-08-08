<template>
  <div id="loginView">
    <h2 style="margin-bottom: 16px">用户登录</h2>
    <a-form
      :model="form"
      :style="{ width: '500px', margin: '0 auto' }"
      label-align="left"
      auto-label-width
      @submit="handleSubmit"
    >
      <a-form-item field="userAccount" label="账号">
        <a-input
          v-model="form.userAccount"
          placeholder="please enter your account"
        />
      </a-form-item>
      <a-form-item field="userPassword" label="密码">
        <a-input-password
          v-model="form.userPassword"
          placeholder="please enter your password"
        />
      </a-form-item>
      <a-form-item>
        <a-space>
          <div
            style="
              display: flex;
              width: 100%;
              align-items: center;
              justify-content: space-between;
            "
          >
            <a-button type="primary" html-type="submit">登录</a-button>
            <a-link href="/user/register" style="margin-left: 150px">
              新用户注册
            </a-link>
          </div>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import API from "@/api";
import { reactive } from "vue";
import { userLoginUsingPost } from "@/api/userController";
import { Message } from "@arco-design/web-vue";
import { loginUserStore } from "@/store/userStore";
import { useRouter } from "vue-router";
import UserLoginRequest = API.UserLoginRequest;

const userStore = loginUserStore();
const router = useRouter();
const form = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginRequest);
const handleSubmit = async () => {
  console.log(form);
  const res = await userLoginUsingPost(form);
  if (res.data.code === 0) {
    await userStore.fetchLoginUser();
    Message.success("登录成功");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    Message.error("登录失败" + res.data.message);
  }
};
</script>

<style scoped>
#loginView {
}
</style>
