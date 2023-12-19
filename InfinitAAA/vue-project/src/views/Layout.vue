<script lang="ts">
import { getCurrentInstance, onMounted, reactive, ref, watch } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { Avatar, CloseBold } from '@element-plus/icons-vue'



import ChatWindow from '../components/ChatWindow.vue';


import { VideoPause, VideoPlay } from '@element-plus/icons-vue'
import lkSelect from '../views/lk-select.vue'
import { deleteSongFromList, getSongList } from '../api/playlist.js'
const songId = ref(1442451766); // 替换为你想获取 URL 的歌曲 ID

// 根据id获取音乐URL
const apiUrl = ref(`https://music.163.com/song/media/outer/url?id=${songId.value}.mp3`);

// watch(songId,(newSongId)=>{
  // console.log("gaibian"),
  // apiUrl.value = `https://music.163.com/song/media/outer/url?id=${newSongId}.mp3`
// })


// watch(songId, (newSongId) => {
  //songId 变化时更新 apiUrl
  // apiUrl.value = `https://music.163.com/song/media/outer/url?id=${newSongId}.mp3`;
// });
// 
// console.log("hahaha"+apiUrl.value);


// 转换函数：将普通文本格式的歌词转换为 LRC 格式
function convertToLRC(lyrics) {
  const lines = lyrics.split('\n');
  let lrcContent = '';

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i].trim();
    if (line !== '') {
      const timestamp = getTimestamp(line);
      const text = getText(line);

      if (timestamp !== null && text !== '') {
        lrcContent += `[${timestamp}]${text}\n`;
      }
    }
  }

  return lrcContent;
}
function getTimestamp(line) {
  const matches = line.match(/\[(\d{2}):(\d{2})\.(\d{3})]/);

  if (matches) {
    const minutes = parseInt(matches[1]);
    const seconds = parseInt(matches[2]);
    const milliseconds = parseInt(matches[3]);

    const totalMilliseconds = minutes * 60 * 1000 + seconds * 1000 + milliseconds;
    return formatTimestamp(totalMilliseconds);
  }

  return null;
}
function formatTimestamp(milliseconds) {
  const minutes = Math.floor(milliseconds / 60000);
  const seconds = Math.floor((milliseconds % 60000) / 1000);
  const ms = milliseconds % 1000;

  return (
    (minutes < 10 ? '0' : '') + minutes + ':' +
    (seconds < 10 ? '0' : '') + seconds + '.' +
    (ms < 10 ? '00' : ms < 100 ? '0' : '') + ms
  );
}
function getText(line) {
  return line.replace(/\[\d{2}:\d{2}\.\d{3}\]/, '').trim();
}


export default {
  name: "index",
  components: { ChatWindow, VideoPause, VideoPlay, lkSelect },
  props: {
    audioSrc: {
      type: String,
      default: apiUrl.value
    },
    backSecond: {
      type: Number,
      default: 3
    },
    forwardSecond: {
      type: Number,
      default: 10
    },
  },
  data() {
    return {
      
      duration: '00:00',
      currentDuration: '00:00',
      audio: '',
      volume: 1,
      paused: true,
      isMoveIn: false, //是否在移动中
      showVolumePanel: false,
      volumeVisible: false,
      name: 'lixi',
      keyword: '',

      searched: false,
      showWelcome: true,

      nowPlayList : { "id": 6 },
      nowPlaySonglist : [],
      nowPlaySong : {},
      addSongTolistdialogVisible : ref(false)
    }
  },
  setup() {
    const url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F3d7413ad-9cea-48be-ae51-5ef3921edc01%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704703597&t=141658c39e3ff55c042a6e45a23b6dd7";
    const dialogVisible = ref(false);
    const number = ref(5);
    const handleClose = (done: () => void) => {
      ElMessageBox.confirm('Are you sure you want to close this?', 'Confirmation', {
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel',
    type: 'warning',
    customClass: 'custom-message-box', // 添加 customClass 属性
  })
        .then(() => {
          done()
        })
        .catch(() => {
          // catch error
        })
    };
    const drawer = ref(false);
    const direction = ref('rtl');
    // 路由跳转
    const router = useRouter();
    const currentSong = ref({
      name: '',
      album: '',
      singer: '',
      albumCover: '',
      LRC: ''
    });
    let nowPlayList = { id: '7'};
    let nowPlaySonglist = [];
    let nowPlaySong = {};
    let addSongTolistdialogVisible = ref(false);


    async function fetchLyrics() {
      const lyricsUrl = `http://localhost:3000/lyric?id=${songId.value}`;
      let savedLRC = '';
      try {
        const response = await fetch(lyricsUrl, {
          headers: {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36' // 设置请求的源
          }
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();
        const lyrics = data.lrc ? data.lrc.lyric : '暂无歌词';
        const LRC = convertToLRC(lyrics);
        savedLRC = LRC;
        currentSong.value.LRC = LRC;
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);
      }

      console.log(savedLRC + "666");
    };
    async function fetchSongDetails() {
      const songDetailsUrl = `http://localhost:3000/song/detail?ids=${songId.value}`;

      try {
        const response = await fetch(songDetailsUrl, {
          headers: {
            'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
          },
        });

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        const data = await response.json();

        console.log(data)

        const song = data.songs[0]; // 假设API返回的数据结构中包含了歌曲详情信息

        currentSong.value.name = song.name;
        currentSong.value.singer = song.ar[0].name;
        currentSong.value.album = song.al.name;
        currentSong.value.albumCover = song.al.picUrl;
      } catch (error) {
        console.error('There was a problem with the fetch operation:', error);

      }
    }

    //下面是路由函数
    const goToPlayerPage = () => {
      router.push({
        path: '/player',
        query: {
          name: currentSong.value.name,
          singer: currentSong.value.singer,
          album: currentSong.value.album,
          albumCover: currentSong.value.albumCover,
          LRC: currentSong.value.LRC
        }
      })

    };
    const handleCommand = (command: string) => {
      if (command === 'logout') {
        // Handle logout confirmation
        ElMessageBox.confirm(
          'Are you sure you want to logout?',
          'InfiniteMusic',
          {
            confirmButtonText: 'Ok',
            cancelButtonText: 'Cancel',
            type: 'warning',
            customClass: 'custom-message-box'
          }
        ).then(async () => {
          // Perform logout actions
          router.push('/login')
          ElMessage({
            type: 'success',
            message: 'Logged out successfully',
          })
        }).catch(() => {
          ElMessage({
            type: 'info',
            message: 'User canceled logout',
          })
        })
      } else {
        // Handle other commands (routing)
        router.push('/user/' + command)
      }
    };
    //Function to navigate to search

    const searchResults= ref([]) 
    const keyword = ref()


    const navigateToSearch = () => {
      router.push({
        path:'/search',
        query: {
          keyword: keyword.value
        }
    })


    };

    const goToCommentPage = () => {
      router.push({
        path: '/comment',
        query: {
          name: currentSong.value.name,
          albumCover: currentSong.value.albumCover,
          songId: songId.value
        }
      })
    };
    onMounted(async () => {
      await fetchLyrics();
      await fetchSongDetails();
      //await navigateToSearch();
    });

    return {
      currentSong, url, Avatar,CloseBold,
      goToPlayerPage, goToCommentPage, handleCommand,
      drawer, direction, dialogVisible, number, handleClose
    };
  },
  methods: {
    handleRowDoubleClick(row) {
      //console.log(`歌曲ID为 ${row.songId} 的行`);
      const songId = row.songid;
      console.log(`双击了歌曲ID为 ${songId} 的行`);
    
    // 在这里执行你的逻辑，row 包含了所在行的数据
   // this.songId.value = row.songid;
    //console.log(`双击了歌曲ID为 ${songId} 的行`);
    // 进行其他操作...
    }
    ,updateShowWelcome() {
      this.showWelcome = this.$route.path === '/';
    },
    clearSearchResults() {
      this.$emit('clearSearchResults');
    }
    ,
    navigateToSearch() {
      // 使用 $router.push 将 keyword 作为参数传递给 search.vue
      this.$router.push({
        path: '/search',
        query: { keyword: this.keyword },
      });
    }
    ,
    handleButtonClick() {
      //this.searchMusic();
      this.navigateToSearch();
    },

    //后退
    handleBack() {
      if (this.audio.currentTime > this.backSecond) {
        this.audio.currentTime = this.audio.currentTime - this.backSecond
      }
    },
    //前进
    handleForward() {
      if (this.audio.duration - this.audio.currentTime > this.forwardSecond) {
        this.audio.currentTime = this.audio.currentTime + 10
      }
    },
    //暂停或播放
    handlePauseOrPlay() {
      this.audio.paused ? this.audio.play() : this.audio.pause()
      this.paused = !this.paused
    },
    //视频在可以播放时触发
    getDuration() {
      this.duration = this.timeFormat(this.$refs.audio.duration)
      this.audio = this.$refs.audio
      this.audio.volume = this.volume //默认百分百音量
      //监听音量的变化
      // this.audio.addEventListener('volumechange',(value)=>{
      //   console.log(this.audio.volume)
      // })
    },
    //将单位为秒的的时间转换成mm:ss的形式
    timeFormat(number: string | number): string {
      const parsedNumber = typeof number === 'string' ? parseInt(number, 10) : number;
      if (isNaN(parsedNumber)) {
        return '00:00'; // Handle invalid input
      }

      const minute = parseInt(String(parsedNumber / 60));
      const second = parseInt(String(parsedNumber % 60));
      const formattedMinute = minute >= 10 ? String(minute) : "0" + minute;
      const formattedSecond = second >= 10 ? String(second) : "0" + second;

      return formattedMinute + ":" + formattedSecond;
    },
    //进度条发生变化时触发
    updateTime() {
      if (!this.$refs.progress) return
      this.currentDuration = this.timeFormat(this.audio.currentTime)
      //如果不是正在移动 和 没有暂停播放就执行
      if (!this.isMoveIn || !this.audio.paused) {
        // 设置当前时间
        let MoveX = this.$refs.progress.clientWidth * (this.audio.currentTime / this.audio.duration)
        //播放时更新距离
        this.$refs.currentProgress.style.width = MoveX + 'px'
        this.$refs.circle.style.left = MoveX - (this.$refs.circle.clientWidth / 2) + 'px'
      }
    },
    //点击进度条更新进度
    clickProgress(e) {
      //如果不是正在移动 和 没有暂停播放就执行
      if (!this.isMoveIn || !this.audio.paused) {
        this.updateProgress(e.offsetX)
      }
    },
    //更新进度
    updateProgress(MoveX) {
      //当前移动的位置 = 当前移动的位置 / 当前进度条的可视长度    //this.$refs.progress.clientWidth 注意一定要拿总长度 否则会拿进度条已经走过的长度
      let clickProgress = (MoveX / this.$refs.progress.clientWidth)
      //设置播放的时间 = 总时长 * 当前点击的长度
      this.audio.currentTime = this.audio.duration * clickProgress
      //设置移动的位置
      this.$refs.currentProgress.style.width = MoveX + 'px'
      this.$refs.circle.style.left = MoveX - (this.$refs.circle.clientWidth / 2) + 'px'
    },
    //更新音量进度
    updateVolumeProgress(MoveY) {
      const totalHeight = this.$refs.progressY.clientHeight; // 音量条总高度
      const circleHeight = this.$refs.circleY.clientHeight; // 圆圈高度
      const maxVolume = 1; // 最大音量
      const minVolume = 0; // 最小音量

      // 计算音量的百分比
      let volumePercentage = (totalHeight - MoveY) / totalHeight;

      // 将音量百分比转换为在指定范围内的音量值
      let volume = minVolume + volumePercentage * (maxVolume - minVolume);

      // 确保音量值在有效范围内
      volume = Math.max(minVolume, Math.min(maxVolume, volume));

      // 更新音量值
      this.audio.volume = volume;
      this.volume = volume;

      // 更新音量条高度
      this.$refs.currentProgressY.style.height = `${MoveY}px`;

      // 更新小圆圈位置
      this.$refs.circleY.style.top = `${MoveY - circleHeight / 2}px`;
    },

    //鼠标弹起
    handleMouseup() {
      setTimeout(() => {
        this.audio.play()
        this.paused = false
        this.isMoveIn = false
      }, 200)
    },
    //调整进度
    handleMousedown() {
      this.audio.pause()
      this.paused = true
      this.isMoveIn = true
      let progress = this.$refs.progress
      //进度条 左 边距离页面左边的距离 移动最小值
      let moveMin = progress.offsetParent.offsetLeft + progress.offsetLeft
      //进度条 右 边距离页面左边的距离 移动最大值
      let moveMax = progress.offsetParent.offsetLeft + progress.offsetLeft + progress.clientWidth
      //小圆圈的宽度
      let circleWidth = (this.$refs.circle.clientWidth / 2)
      let moveX = (e) => {
        if (e.pageX >= moveMax) {
          return
        } else if (e.pageX <= moveMin) {
          return
        }
        this.$refs.circle.style.left = e.pageX - moveMin - circleWidth + 'px'
        this.updateProgress(e.pageX - moveMin)
      }
      //获取当前鼠标的位置 X
      document.addEventListener('mousemove', moveX)
      //鼠标弹起来
      document.addEventListener('mouseup', () => {
        document.removeEventListener('mousemove', moveX)
      })
    },
    //调整音量
    handleVolumeMousedown(e) {
      const progressY = this.$refs.progressY.getBoundingClientRect();
      const moveMin = progressY.top;
      const moveMax = progressY.bottom;

      const circleHeight = this.$refs.circleY.clientHeight / 2;

      const change = e.movementY > 0 ? -1 : 1; // Change direction based on mouse movement

      const moveY = (e) => {
        e.preventDefault();
        if (e.pageY >= moveMax) {
          return;
        } else if (e.pageY <= moveMin) {
          return;
        }
        this.$refs.circleY.style.top = e.pageY - moveMin - circleHeight + 'px';
        this.updateVolumeProgress(e.pageY - moveMin, change);
      };

      document.addEventListener('mousemove', moveY);
      document.addEventListener('mouseup', () => {
        document.removeEventListener('mousemove', moveY);
      });
    },


    //删去播放列表中的歌曲
    deletePlaySongButton(id) {
      try {

        deleteSongFromList(id,this.nowPlayList.id);
        this.getnowSonglist();
        
      } catch (e) {
        alert("获取数据失败" + e.message)
      }
    },
    //获取播放列表中的歌曲
    getnowSonglist() {
      try {
        this.drawer = true;

       getSongList(this.nowPlayList.id) 
       .then(results => {
  // 假设 searchService 返回的是包含歌曲信息的数组
          this.nowPlaySonglist = results.map(item => ({
            songName: item.songName,
            artist: item.artist,
            album: item.album,
            songid:item.songid,
          }) )
        })
         .catch(error => {
          console.error("搜索错误：", error);
      });
        //console.log(this.nowPlaySonglist)

      } catch (e) {
        alert("获取数据失败" + e.message)
      }
    }


  },
  watch: {
    savedLRC(newLRC) {
      this.$set(this.currentSong.value, 'LRC', newLRC);
    },
    
    $route() {
      this.updateShowWelcome();
    }
   
    

  },
  created() {
    this.updateShowWelcome();
  },


}



</script>



<template>
  <div class="common-layout">
    <el-container class="layout-container">
      <el-header class="top">
        <div class="title" width="290px">
          <img src="../assets/logo.png" class="logo">
          <h3 class="logoname">InfiniteMusic</h3>
        </div>
        <div class="search-box" @command="handleCommand">
          <input class="search-txt" type='text' id='inp' v-model="keyword" @input="clearSearchResults"
            placeholder="搜索音乐" />
          <el-button type="primary" round class="search iconfont icon-sousuo" command="searchVue"
            @click="handleButtonClick">
          </el-button>
        </div>
        <div class="chatSearch" width="150px">
          <el-button type="primary" round :icon="Avatar" class="assistant" @click="dialogVisible = true">
            Assistant
          </el-button>
        </div>
        <!-- 音乐对话功能 -->
        <el-dialog v-model="dialogVisible" title="音乐对话" width="40%" :before-close="handleClose" >
          <span>
            <ChatWindow />
          </span>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">Cancel</el-button>
              <el-button type="primary" @click="dialogVisible = false">
                Confirm
              </el-button>
            </span>
          </template>
        </el-dialog>

        <el-dropdown placement="bottom-end" @command="handleCommand">
          <div class="self el-dropdown-link" style="outline: none">
            <el-image :src="url" alt="photo" class="avatar " />
            <p class="username">orange</p>
          </div>
          <!-- 下拉菜单 -->
          <!-- command：条目被点击触发后，在事件函数上可以声明一个参数，接收条目对应的指令 -->
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="likeList">个人主页</el-dropdown-item>
              <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>

      <el-main>
        <div class="welcome-box" v-if="showWelcome">
          <p class="welcome">Welcome to infinite music</p>
        </div>

        <router-view></router-view>
        <el-drawer v-model="drawer" title="播放列表" :direction="direction" :before-close="handleClose" size="400">
          <span>
            <p class="number">{{ number }}首歌曲</p>
            <el-table height="600px" :data="this.nowPlaySonglist" style="width: 100%" :show-header="false"
              :row-style="{ height: '100px' }" @row-dblclick="handleRowDoubleClick">
              <el-table-column label="Details" width="360">
                <template #default="{ row }">
                  <div class="song-details">
                    <div class="name-singer" width="220px">
                      <div class="name">{{ row.songName }}</div>
                      <div class="singer">{{ row.artist }}</div>
                    </div>
                    <div>
                      <el-button type="primary" :icon="CloseBold" class="delete" background="transparent" circle
                        @click="deletePlaySongButton(row.songid)" ></el-button> 
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </span>
        </el-drawer>
      </el-main>
      <audio @canplay="getDuration" controls @timeupdate="updateTime" v-show="false" ref="audio" :src="audioSrc" />
      <player :currentTime="this.audio.currentTime" />


      <el-footer class="bottom">
        <div class="music-player">
          <div class="song-info">
            <p @click="goToPlayerPage">{{ currentSong.name + " - " + currentSong.singer }}</p>
            <p class="Time">{{ currentDuration + "/" + duration }}</p>


          </div>
          <div class="progress" width="1373px" ref="progress" @click="clickProgress" @mouseup="handleMouseup">
            <div class="currentProgress" ref="currentProgress">
              <span class="circle" ref="circle" @mousedown="handleMousedown"></span>
            </div>
          </div>

          <!-- Other music player controls -->
          <div class="icons">
            <div class="left">
              <!-- <el-button type="primary" circle class="iconfont icon-follow"></el-button> -->
              <el-button type="primary" circle class="iconfont icon-comment" @click="goToCommentPage" />
            </div>


            <div class="middle">
              <el-button type="primary" circle class="iconfont icon-shangyishou"></el-button>
              <span class="play" @click="handlePauseOrPlay">
                <el-icon size="35px" color="#F2F2F2">
                  <video-pause v-show="!paused" />
                  <video-play v-show="paused" />
                </el-icon>
              </span>

              <el-button type="primary" circle class="iconfont icon-xiayishou"></el-button>
            </div>

            <div class="right">

              <div class="volumePanel">
                <lk-select v-model:visible="volumeVisible">
                  <div class="progressY" ref="progressY">
                    <div class="currentProgressY" ref="currentProgressY">
                      <span class="circleY" ref="circleY" @mousedown="handleVolumeMousedown"></span>
                    </div>
                  </div>
                  <template v-slot:reference>
                    <el-button type="primary" circle class="iconfont icon-shengyin volume" v-if="volume"></el-button>
                    <el-button type="primary" circle class="iconfont icon-shengyin volume" v-else></el-button>
                  </template>

                </lk-select>
              </div>

              <el-button type="primary" circle class="iconfont icon-sort" @click="getnowSonglist" /> 
            </div>
          </div>

          <!-- <el-button :icon="playstatus ? 'el-icon-pause' : 'el-icon-play'" @click="togglePlay"></el-button> -->
        </div>

      </el-footer>
    </el-container>
  </div>
</template>
  
<style scoped lang="less">



</style>

<style lang="less">
/* basic里面的东西，每个页面都要用到 */
body {
  background-color: #0F181F;
  width: 1410px;
  margin: 0 auto;
}

.custom-message-box {
  p {
    color: black; /* 修改为你想要的字体颜色 */

  }
}

el-dialog {
  p {
    color: black;
  }
}


.welcome-box {
  background-image: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F3d7413ad-9cea-48be-ae51-5ef3921edc01%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704703597&t=141658c39e3ff55c042a6e45a23b6dd7");
  width: 100%;
  height: 600px;
  text-align: center;

}

.welcome {
  font-size: 58px;
  font-style: italic;
  backdrop-filter: blur(10px);
  height: 600px;
  line-height: 600px;
}


div {
  padding: 0%;
}

p {
  color: #FFFFFF;
}

.top {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  height: 150px;
}

.logo {
  width: 60px;
  height: 60px;
  margin: auto;
  margin-left: 30px;
  border-radius: 50%;
}

.title {
  display: flex;
  align-items: center;
}

.logoname {
  margin: auto;
  margin-left: 20px;
  font-size: 28px;
  font-weight: bold;
  font-style: italic;
  color: #FFFFFF;
}

.search-box {
  width: 350px;
  padding: 0;
  display: flex;
  margin-left: 90px;
  background: #ffffff;
  height: 40px;
  border: none;
  border-radius: 40px;
  align-items: center;
}

.search {
  background-color: transparent;
  margin-left: 5px;
  font-size: 26px;
  color: #797979;
  border: none;
}

.search-txt {
  border: none;
  background: none;
  outline: none;
  float: left;
  color: #000;
  font-size: 16px;
  transition: 0.6s;
  line-height: 32px;
  width: 252px;
  margin-left: 20px;
}

.blank {
  position: relative;
  width: 20%;
}

.chatSearch {
  display: flex;
  align-items: center;
}

.assistant {
  margin-left: 40px;
  width: 160px;
  height: 40px;
  outline: #797979 1px;
  border-radius: 40px;
  font-size: 16px;
  line-height: 40px;
  font-weight: bold;
  background-color: transparent;
}

.assistant i {
  color: dodgerblue;
  font-size: 22px;
  margin-right: 6px
}


.self {
  display: flex;
  align-items: center;
  margin-left: 250px;
}



.avatar {
  width: 60px;
  height: 60px;
  border-radius: 50%;
}

.username {
  width: 30px;
  margin-left: 30px;
  font-size: 20px;
  color: #FFFFFF;

}




.example-showcase .el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
}

.el-dropdown-item {
  background: #091014;
  text-align: center;
  font-size: 13px;
  color: #F2F2F2;
  width: 114px;
  height: 40px;
  line-height: 40px;
  overflow: hidden;
  border: none;
}

.el-dropdown-item:hover {
  cursor: pointer;
  border: #F2F2F2 1px;
}

/* 底部播放 */

.bottom {
  display: flex;
  height: 160px;
  position: fixed;
  bottom: 0;
}

.song-info {
  color: #B6B6B6;
  font-size: 16px;
  display: flex;
}

.Time {
  margin-left: 1100px;
}

.music-player {
  display: flex;
  flex-direction: column;
  margin-left: 5px;
}

.custom-slider .el-slider__runway {
  height: 2px;
  /* Adjust the height of the track */
  width: 1380px;
  background-color: #B6B6B6;
}

.custom-slider .el-slider__bar {
  height: 2px;
  background-color: #2E8CAC;
}

.custom-slider .el-slider__button {
  width: 10px;
  /* Adjust the width of the thumb */
  height: 10px;
  /* Adjust the height of the thumb */
  margin-top: -4px;
  /* Center the thumb vertically within the track */
  margin-left: -5px;
  /* Center the thumb horizontally within the track */
}

.icons {
  display: flex;
  margin-top: 35px;
}

.bottom-icon {
  font-size: 35px;
  background-color: transparent;
  border: none;
  color: #F2F2F2;

}

.icon-follow {
  background-color: transparent;
}

.iconfont {
  background-color: transparent;
  border: none;
  font-size: 35px;
}

.left {
  display: flex;
  justify-content: space-around;
  width: 150px;
}


.middle {
  position: relative;
  left: 27%;
  display: flex;
  justify-content: space-around;
  width: 300px;
}



.right {
  position: relative;
  left: 56%;
  justify-content: space-between;
  display: flex;
  justify-content: space-around;
  width: 150px;
}


.el-drawer {
  background: #000;


}

.el-drawer p {
  margin-left: 10px;
}

.el-drawer__title {
  margin-top: 50px;
  margin-left: 5px;
  font-size: 24px;
  color: #F2F2F2;
}

.song-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
}

/* Styles for name and singer column */
.name-singer {
  display: flex;
  flex-direction: column;
}

/* Apply specific styles to each part of the song details */
.name {
  color: #F2F2F2;
  font-size: 18px;
}

.singer {
  margin-top: 20px;
  font-size: 16px;
  color: #AAAAAA;
}

.long {
  font-size: 18px;
  color: #AAAAAA;
  /* Change color of long to differentiate */
}

.progress,
.progressY {
  height: 2px;
  border-radius: 2px;
  background-color: #DADFEA;
  cursor: pointer;

  .currentProgress {
    position: relative;
    height: 100%;
    width: 0;
    background-color: #2E8CAC;
    border-radius: 2px;


    .circle {
      position: absolute;
      right: -6px;
      top: -2px;
      display: inline-block;
      width: 7px;
      height: 7px;
      border-radius: 50%;
      border: 1px solid #2E8CAC;
      background-color: #fff;

      &:hover {
        width: 7px;
        height: 7px;
        top: -3px;
        border-radius: 50%;
      }
    }
  }
}

.circleY {
  position: absolute;
  right: -6px;
  top: -2px;
  display: inline-block;
  width: 7px;
  height: 7px;
  border-radius: 50%;
  border: 1px solid #2E8CAC;
  background-color: #fff;

  &:hover {
    width: 7px;
    height: 7px;
    top: -3px;
    border-radius: 50%;
  }
}

.progressY {
  width: 5px;
  height: 60px;
  margin-left: 12px;
  margin-bottom: 20px;
  bottom: calc(100% + 15px);
  position: absolute;
  background-color: #2E8CAC;


  .currentProgressY {
    height: 100%;
    width: 100%;
    position: relative;
    background-color: #fff;

    .circleY {
      position: absolute;
      right: -3px;
      width: 8px;
      height: 8px;
      border-radius: 50%;
      transition: top 0.2s ease-in-out;
      /* 添加过渡效果 */

      &:hover {
        width: 12px;
        height: 12px;
        border-radius: 50%;
      }
    }
  }
}
</style>
