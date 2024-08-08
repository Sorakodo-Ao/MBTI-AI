import router from "@/router";
import { loginUserStore } from "@/store/userStore";
import ACCESSENUM from "@/access/accessEnum";
import checkAccess from "@/access/checkAccess";

router.beforeEach(async (to, from, next) => {
  //获取当前用户
  const userStore = loginUserStore();
  let user = userStore.loginUser;
  console.log("当前用户 = ", user);
  //如果之前没尝试过获取登录信息，则自动登录
  if (!user || !user.userRole) {
    //自动登录 await 是等待用户登陆成功在执行后续代码
    await userStore.fetchLoginUser();
    user = userStore.loginUser;
  }

  //获得当前页面需要的权限
  const needAccess = (to.meta?.access as string) ?? ACCESSENUM.NOT_LOGIN;
  //跳转的页面需要登录
  if (needAccess !== ACCESSENUM.NOT_LOGIN) {
    console.log("当前页面需要登录");
    //用户未登录或未获取到用户信息
    if (!user || !user.userRole || user.userRole === ACCESSENUM.NOT_LOGIN) {
      //转到登录页面
      next(`/user/login?redirect=${to.fullPath}`);
    }
    //用户已登录但权限不足
    //user.userRole !== ACCESSENUM.ADMIN && needAccess === ACCESSENUM.ADMIN
    if (!checkAccess(user, needAccess)) {
      //转到无权限页面
      next("/hide");
      return;
    }
  }
  next();
});
