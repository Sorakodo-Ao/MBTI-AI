<template>
  <div id="registerView">
    <h2 style="margin-bottom: 16px">用户注册</h2>
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
      <a-form-item field="userPassword" label="确认密码">
        <a-input-password
          v-model="form.checkPassword"
          placeholder="please confirm your password"
        />
      </a-form-item>
      <a-form-item>
        <a-button type="primary" html-type="submit">注册</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script setup lang="ts">
import API from "@/api";
import { reactive } from "vue";
import { userRegisterUsingPost } from "@/api/userController";
import { Message } from "@arco-design/web-vue";
import { loginUserStore } from "@/store/userStore";
import { useRouter } from "vue-router";
import UserRegisterRequest = API.UserRegisterRequest;

const userStore = loginUserStore();
const router = useRouter();
const form = reactive({
  userAccount: "",
  userPassword: "",
  checkPassword: "",
} as UserRegisterRequest);
const handleSubmit = async () => {
  console.log(form);
  const res = await userRegisterUsingPost(form);
  if (res.data.code === 0) {
    await userStore.fetchLoginUser();
    Message.success("注册成功");
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    Message.error("注册失败" + res.data.message);
  }
};
</script>

<style scoped>
#registerView {
}
</style>
