<template>
  <div class="top-nav">
    <b-navbar toggleable="lg" type="dark" variant="dark">
      <b-button pill variant="success" v-b-toggle.sidebar>실시간 채팅</b-button>
      <b-sidebar id="sidebar" title="chat" backdrop shadow>
        <div id="app">
          <div v-for="(item, idx) in recvList" :key="idx">
            <div
              v-if="item.userName == userInfo.userName"
              style="
                background-color: green;
                border-radius: 10px;
                margin: 5px 10px;
                padding: 5px 10px;
                font-size: 18px;
                color: white;
              "
            >
              <span>{{ item.userName }}: {{ item.content }}</span>
            </div>
            <div
              v-else
              style="
                background-color: gray;
                border-radius: 10px;
                margin: 5px 10px;
                padding: 5px 10px;
                font-size: 18px;
                color: white;
              "
            >
              <span>{{ item.userName }}: {{ item.content }}</span>
            </div>
          </div>
          <div class="text-input">
            +
            <input
              v-model="message"
              type="text"
              placeholder="message"
              @keyup="sendMessage"
            />
          </div>
        </div>
      </b-sidebar>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav id="nav-menu">
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Home' }" class="link"
              ><b-icon icon="house" font-scale="1"></b-icon> HOME</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'BoardList' }" class="link"
              ><b-icon icon="journal" font-scale="1"></b-icon>
              게시판</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Search' }" class="link"
              ><b-icon icon="search" font-scale="1"></b-icon> 검색</router-link
            ></b-nav-item
          >
          <b-nav-item href="#"
            ><router-link :to="{ name: 'Site' }" class="link"
              ><b-icon icon="link45deg"></b-icon> 관련 사이트</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="userInfo ml-auto" v-if="userInfo">
          <b-nav-item class="align-self-center"
            ><b-avatar
              variant="primary"
              v-text="userInfo.userId.charAt(0).toUpperCase()"
              class="mr-1"
            ></b-avatar
            >{{ userInfo.userName }}({{ userInfo.userId }}) 님 환영합니다.
          </b-nav-item>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item class="align-self-center"
              ><router-link
                :to="{ name: 'MyPage' }"
                class="link align-self-center"
                ><b-icon icon="exclamation-circle" class="mr-1"></b-icon
                >마이페이지</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item
              class="link align-self-center"
              @click.prevent="onClickLogout"
            >
              <b-icon icon="power" class="mr-1"></b-icon
              >로그아웃</b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>

        <b-navbar-nav class="userInfo ml-auto" v-else>
          <b-nav-item-dropdown right>
            <template #button-content>
              <b-icon icon="people" font-scale="2"></b-icon>
            </template>
            <b-dropdown-item
              ><router-link :to="{ name: 'SignUp' }" class="link"
                ><b-icon icon="person-circle"></b-icon> 회원가입</router-link
              ></b-dropdown-item
            >
            <b-dropdown-item
              ><router-link :to="{ name: 'SignIn' }" class="link"
                ><b-icon icon="key"></b-icon> 로그인</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { app } from "@/main";

const memberStore = "memberStore";

export default {
  name: "NaviBar",
  data() {
    return {
      isSticky: true,
      userName: "",
      message: "",
      recvList: [],
    };
  },
  created() {
    this.connect();
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  watch: {
    recvList: function () {
      if (this.recvList.length >= 20) {
        // this.recvList = this.recvList.shift();
        this.recvList.shift();
      }
    },
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
    sendMessage(e) {
      if (e.keyCode === 13 && this.message !== "") {
        this.send();
        this.message = "";
      }
    },
    send() {
      if (this.userInfo == null) {
        app.$bvToast.toast("채팅을 위해서는 로그인이 필요합니다.", {
          title: `앗!`,
          variant: "danger",
          solid: true,
        });
        return;
      }
      //console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userInfo.userName,
          content: this.message,
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = "http://localhost:8080";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      // console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`);
      this.stompClient.connect(
        {},
        (frame) => {
          // 소켓 연결 성공
          this.connected = true;
          console.log("소켓 연결 성공", frame);
          // 서버의 메시지 전송 endpoint를 구독
          this.stompClient.subscribe("/send", (res) => {
            // 받은 데이터를 json으로 파싱하고 리스트에 삽입.
            this.recvList.push(JSON.parse(res.body));
          });
        },
        (error) => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
};
</script>

<style scoped>
#nav-menu a.link,
#nav-menu a.link:hover {
  color: white;
}

a:hover {
  text-decoration: none;
  font-weight: bold;
}

a.dropdown-item,
a.link {
  color: #212529;
}

#nav-menu a.link.router-link-exact-active,
a.link.align-self-center.router-link-exact-active.router-link-active,
a.router-link-exact-active {
  color: #42b983;
  font-weight: bold;
}

@media (max-width: 992px) {
  .top-nav {
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    height: 67px;
    z-index: 1;
  }
}

@media (min-width: 992px) {
  .top-nav {
    position: fixed;
    left: 0;
    top: 0;
    right: 0;
    height: 67px;
    z-index: 1;
  }
}

.text-input {
  position: fixed;
  left: 10%;
  bottom: 3%;
}
</style>
