import { ref } from "vue";
import { defineStore } from "pinia";
import { getLoginUserUsingGet } from "@/api/userController";
import ACCESSENUM from "@/access/accessEnum";
import LoginUserVO = API.LoginUserVO;

/**
 * 用户登录信息
 */
export const loginUserStore = defineStore("loginUser", () => {
  const loginUser = ref<LoginUserVO>({
    userName: "未登录",
  });

  function setLoginUser(newLoginUser: LoginUserVO) {
    loginUser.value = newLoginUser;
  }

  async function fetchLoginUser() {
    // 调用接口获取用户信息
    const res = await getLoginUserUsingGet();
    if (res.data.code === 0 && res.data.data) {
      //登录成功
      console.log("userStore 获取登录用户 = ", res.data.data);
      setLoginUser(res.data.data);
    } else {
      //登录失败
      setLoginUser({
        userName: "未登录",
        userRole: ACCESSENUM.NOT_LOGIN,
      });
      /*      setTimeout(() => {
              setLoginUser({
                id: 1,
                userName: "登录",
                userRole: ACCESSENUM.ADMIN,
              });
            }, 3000);*/
    }
  }

  return { loginUser, setLoginUser, fetchLoginUser };
});
