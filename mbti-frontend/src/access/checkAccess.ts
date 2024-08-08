import LoginUserVO = API.LoginUserVO;
import ACCESSENUM from "@/access/accessEnum";

/**
 * 检查用户权限
 * @param loginUser
 * @param needAccess
 */
const checkAccess = (
  loginUser: LoginUserVO,
  needAccess = ACCESSENUM.NOT_LOGIN
) => {
  //当前用户权限
  const userAccess = loginUser?.userRole ?? ACCESSENUM.NOT_LOGIN;
  //当前页面需要的权限可以为未登录
  if (needAccess === ACCESSENUM.NOT_LOGIN) {
    return true;
  }
  //权限为用户
  if (needAccess === ACCESSENUM.USER) {
    if (userAccess === ACCESSENUM.USER) {
      return true;
    }
  }
  //权限为管理员
  if (needAccess === ACCESSENUM.ADMIN) {
    if (userAccess !== ACCESSENUM.ADMIN) {
      return false;
    }
  }
  return true;
};
export default checkAccess;
