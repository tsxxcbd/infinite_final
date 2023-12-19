<script lang="ts" setup>
import { ref, onMounted, watch, defineProps, reactive, provide, inject } from 'vue'



const route = useRoute()
const {query:{name}} = route
const {query:{singer}} = route
const {query:{album}} = route
const {query:{LRC}} = route
const {query:{albumCover}}=route

const url=albumCover



</script>

<script lang="ts">
import { useRoute } from 'vue-router'


interface LyricsLine {
  time: number;
  words: string;
}
export default {

  data() {
    return {
      className: "text load",
      lrcTime: "",
      currenttime: "",
      LRC:  this.$route.query.LRC, // 歌词内容
      lrcData: [] as { time: number; words: string }[], // 歌词数据数组
      dataWords: "", // 当前歌词内容
      // 当前歌词及其周围几句歌词的显示数量
      contextLines: 11,
      // 当前播放歌曲的歌词及其周围几句歌词数据
      displayedLyrics: [] as LyricsLine[],
      initialLyricIndex: 0, // 设置初始歌词的起始位置
      visibleLyricCount: 11, // 显示的歌词行数
    };
  },
  methods: {
    formatLrc() {
      var strLrc = this.LRC.split("\n");
      let arr: { time: number; words: string }[] = [];
      for (var i = 0; i < strLrc.length; i++) {
        var str = strLrc[i];
        var parts = str.split("]");
        if (parts.length > 1) {
          var timeStr = parts[0].substring(1);
          var obj = {
            time: this.formatTime(timeStr),
            words: parts[1],
          };
          arr.push(obj);
        }
      }
      this.lrcData = arr;
      //this.dataWords = this.lrcData.slice(0, this.visibleLyricCount);
    },
    formatTime(time: string) {
      var parts = time.split(":");
      var minutes = parseInt(parts[0]);
      var seconds = parseFloat(parts[1]);
      return minutes * 60 + seconds;
    },
    audioTime(e) {
      const time = e.target.currentTime;
      const currentLineIndex = this.findCurrentLineIndex(time);

      if (currentLineIndex !== -1) {
        const startIndex = Math.max(currentLineIndex - 5, 0);
        const endIndex = Math.min(currentLineIndex + 5, this.lrcData.length - 1);
        this.displayedLyrics = this.lrcData.slice(startIndex, endIndex + 1);
      }
    },
    findCurrentLineIndex(time: number): number {
      for (let i = 0; i < this.lrcData.length - 1; i++) {
        if (time >= this.lrcData[i].time && time < this.lrcData[i + 1].time) {
          return i;
        }
      }
      return this.lrcData.length - 1;
    },
    updateDisplayedLyrics(currentTime) {
      const currentIndex = this.lrcData.findIndex(
        (line) => line.time >= currentTime
      );

      if (currentIndex !== -1) {
        const startIndex = Math.max(0, currentIndex - this.contextLines);
        const endIndex = Math.min(
          this.lrcData.length - 1,
          currentIndex + this.contextLines
        );
        this.displayedLyrics = this.lrcData.slice(startIndex, endIndex + 1);
      }
    },
  },
  watch: {
    dataWords() {
      this.className = "text";
      setTimeout(() => {
        this.className = "text load";
      }, 50);
    },
  },
  mounted() {
    this.formatLrc();
    console.log(this.currentSong);
    const audio = document.querySelector('audio'); // 获取音频元素，这里可能需要根据你的具体情况修改选择器

    if (audio) {
      audio.addEventListener('timeupdate', (e) => {
        this.audioTime(e);
      });
    }
  },


};
</script>
<template>
  <div class="player">
    <el-image class="albumCover" style="width: 420px; height: 420px" :src=url fit="cover" />
    <div class="songInfo">
      <p class="songName">{{ name }}</p>
      <span class="otherInfo">
        <p class="singer">歌手：{{ singer }} </p>
        <p class="album">专辑：{{ album }}</p>
      </span>




      <div class="lyrics">
        <p v-for="(line, index) in displayedLyrics" :key="index">{{ line.words }}</p>
      </div>
    </div>

  </div>
</template>

<style>
.player {
  display: flex;

}

.songInfo {
  margin-left: 250px;
  text-align: center;
}



.albumCover {
  margin-top: 60px;
  margin-left: 120px;
}

.songName {
  font-size: 28px;
  color: #F2F2F2;
}

.singer,
.album {
  color: #797979;
  font-size: 14px;
}




.lyrics {
  text-align: center;
  margin-top: 30px;
  /* 添加其他样式 */
}
</style>