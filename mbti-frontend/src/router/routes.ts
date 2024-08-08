import AboutView from "@/views/AboutView.vue";
import HomePage from "@/views/HomePage.vue";
import { RouteRecordRaw } from "vue-router";
import UserLayout from "@/layout/UserLayout.vue";
import LoginView from "@/views/user/LoginView.vue";
import RegisterView from "@/views/user/RegisterView.vue";
import NoAuthView from "@/views/noAuth/NoAuthView.vue";
import ACCESSENUM from "@/access/accessEnum";
import AdminUserPage from "@/views/admin/AdminUserPage.vue";
import AdminUserAnswerPage from "@/views/admin/AdminUserAnswerPage.vue";
import AdminScoringResultPage from "@/views/admin/AdminScoringResultPage.vue";
import AdminQuestionPage from "@/views/admin/AdminQuestionPage.vue";
import AdminAppPage from "@/views/admin/AdminAppPage.vue";
import AddAppPage from "@/views/add/AddAppPage.vue";
import AddQuestionPage from "@/views/add/AddQuestionPage.vue";
import AddScoringResultPage from "@/views/add/AddScoringResultPage.vue";
import DoAnswerPage from "@/views/answer/DoAnswerPage.vue";
import AppDetailPage from "@/views/app/AppDetailPage.vue";
import AnswerResultPage from "@/views/answer/AnswerResultPage.vue";
import MyAnswerPage from "@/views/answer/MyAnswerPage.vue";

export const routes: Array<RouteRecordRaw> = [
  {
    path: "/user",
    name: "user",
    component: UserLayout,
    meta: { hideMenu: true },
    children: [
      {
        path: "/user/login",
        name: "login",
        component: LoginView,
      },
      {
        path: "/user/register",
        name: "register",
        component: RegisterView,
      },
    ],
  },
  {
    path: "/",
    name: "主页",
    component: HomePage,
  },
  {
    path: "/about",
    name: "about",
    component: AboutView,
    meta: { hideMenu: true },
  },
  {
    path: "/add/app",
    name: "创建应用",
    component: AddAppPage,
  },
  {
    path: "/add/app/:id",
    name: "修改应用",
    props: true,
    component: AddAppPage,
    meta: {
      hideMenu: true,
    },
  },
  {
    path: "/add/question/:appId",
    name: "创建题目",
    component: AddQuestionPage,
    props: true,
    meta: {
      hideMenu: true,
    },
  },
  {
    path: "/add/scoring_result/:appId",
    name: "创建评分",
    component: AddScoringResultPage,
    props: true,
    meta: {
      hideMenu: true,
    },
  },
  {
    path: "/app/detail/:id",
    name: "应用详情页",
    props: true,
    component: AppDetailPage,
    meta: {
      hideMenu: true,
    },
  },
  {
    path: "/answer/do/:appId",
    name: "答题",
    component: DoAnswerPage,
    props: true,
    meta: {
      hideMenu: true,
      access: ACCESSENUM.USER,
    },
  },
  {
    path: "/answer/result/:id",
    name: "答题结果",
    component: AnswerResultPage,
    props: true,
    meta: {
      hideMenu: true,
      access: ACCESSENUM.USER,
    },
  },
  {
    path: "/answer/my",
    name: "我的答题",
    component: MyAnswerPage,
    meta: {
      access: ACCESSENUM.USER,
    },
  },
  {
    path: "/admin/user",
    name: "用户管理",
    component: AdminUserPage,
    meta: {
      access: ACCESSENUM.ADMIN,
    },
  },
  {
    path: "/admin/app",
    name: "应用管理",
    component: AdminAppPage,
    meta: {
      access: ACCESSENUM.ADMIN,
    },
  },
  {
    path: "/admin/question",
    name: "题目管理",
    component: AdminQuestionPage,
    meta: {
      access: ACCESSENUM.ADMIN,
    },
  },
  {
    path: "/admin/scoring_result",
    name: "评分管理",
    component: AdminScoringResultPage,
    meta: {
      access: ACCESSENUM.ADMIN,
    },
  },
  {
    path: "/admin/user_answer",
    name: "回答管理",
    component: AdminUserAnswerPage,
    meta: {
      access: ACCESSENUM.ADMIN,
    },
  },
  {
    path: "/hide",
    name: "hide",
    component: NoAuthView,
    meta: { hideMenu: true },
  },
];
