<script>
import { ref } from 'vue'
import { ElMessageBox, ElMessage,  } from 'element-plus'
import { useRouter } from 'vue-router'
import { useRoute } from 'vue-router'
import { searchService } from '../api/search'
import { queryUserCreateLists,addSongIntoList} from '../api/playlist.js'
import {
  Plus
} from '@element-plus/icons-vue'
export default {
  data() {
    return {
      searchResults: [],
      keyword: '',

    }
  }
  , setup() {
    const activeName = ref('first')

    const tableData = [
      {
        song: 'lover',
        singer: 'Tom',
        album: 'lover',
        long: '5:20',
      },
      // Rest of the tableData objects...
    ]

    const albumData = [
      {
        cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        album: 'aaa',
        singer: 'Tom',
        number: '14首',
      },
      // Rest of the albumData objects...
    ]

    const lyricData = [
      {
        lyrics: 'aaaa',
        singer: 'mmmm',
        album: 'zzzzz',
        long: '5:20',
      },
      // Rest of the lyricData objects...
    ]

    const singerData = [
      {
        cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
        name: 'nnnn',
        songNum: '单曲: 77',
        albumNum: '专辑: 15'
      },
      // Rest of the singerData objects...
    ]
    const route = useRoute()
    const router = useRouter()
    // const { query: { keyword } } = route

    //条目被点击后，调用的函数
    const handleCommand = (command) => {
      //判断指令
      if (command === 'logout') {
        //退出登录
        ElMessageBox.confirm(
          '您确认要退出吗?',
          'InfiniteMusic',
          {
            confirmButtonText: 'Ok',
            cancelButtonText: 'Cancel',
            type: 'warning',
          }
        )
          .then(async () => {
            //退出登录
            //1.清空pinia中存储的token以及个人信息
            // tokenStore.removeToken()
            // userInfoStore.removeInfo()

            //2.跳转到登录页面
            router.push('/login')
            ElMessage({
              type: 'success',
              message: '退出登录成功',
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '用户取消了退出登录',
            })
          })
      } else {
        //路由
        router.push('/user/' + command)
      }
    }

    const addSongTolistdialogVisible = ref(false);
    const addcurrentsongid=ref('');
    const addcurrentlistid=ref('');
    const CreateListsData=ref([]);
    // const CreateListsData=ref([
      // {
        // "id":6,
        // "name":'敲代码专用'
      // },{
        // "id":10,
        // "name":'快乐'
      // },{
        // "id":11,
        // "name":'学习专用'
      // }
    // ]);
    
    const User=ref({"id":10});
    const addSongToList=()=>{
        try{
          console.log(addcurrentsongid.value);
          console.log(addcurrentlistid.value);
          addSongIntoList(addcurrentsongid.value,addcurrentlistid.value);
          //query();
          addSongTolistdialogVisible=ref(false);
        }catch(e){
          //alert("获取数据失败"+e.message)
        }
    }
    const query=(id)=>{
      addcurrentsongid.value=id
      addSongTolistdialogVisible.value= true

      queryUserCreateLists(User.value.id)
          .then(results => {
        CreateListsData.value = results.map(item => ({
          id: item.id,
          name: item.name,
        }))
        console.log(CreateListsData+"qqq")
      })
      .catch(error => {
        console.error("搜索错误：", error);
      });
    }


    return {
      activeName,
      tableData,
      albumData,
      lyricData,
      singerData,
      handleCommand,
      Plus,
      addSongToList,
      query,
      addSongTolistdialogVisible,
      addcurrentsongid,
      addcurrentlistid,
      CreateListsData,
      User,
    }
  },
  created() {
    // 从路由参数中获取 keyword
    this.keyword = this.$route.query.keyword || '';
    // 调用搜索方法
    this.searchMusic();
    //this.query();
  },
  watch:{
    keyword(newkeyword){
      this.keyword=newkeyword;
      this.searchMusic();
    }
  },

  methods: {
    searchMusic() {
      searchService(this.keyword)
        .then(results => {

          // 假设 searchService 返回的是包含歌曲信息的数组
          this.searchResults = results.map(item => ({
            songName: item.songName,
            artist: item.artist,
            album: item.album,
            song_id: item.song_id,
          }))
          console.log("fff" + this.searchResults)

        })
        .catch(error => {
          console.error("搜索错误：", error);
        });
    },
    clearSearchResults(){
      this.searchResults=[];
    }
  },

  // addSongToList(){
    // try{

      // this.addSongTolistdialogVisible= false

      // console.log(this.addcurrentsongid)
      // console.log(this.addcurrentlistid)
      // addSongIntoList(addcurrentsongid,addcurrentlistid);
      // this.addSongTolistdialogVisible=ref(false)
  // 
    // }catch(e){
      // alert("获取数据失败"+e.message)
    // }

  // },

  // query(){

    // queryUserCreateLists(this.User.id)
        // .then(results => {
      // this.CreateListsData = results.map(item => ({
        // id: item.id,
        // name: item.name,
      // }))
      // console.log(this.CreateListsData)
    // })
    // .catch(error => {
      // console.error("搜索错误：", error);
    // });

  // },

}

</script>

<template>

      <el-table :data="searchResults" height="650"  class="songs" :header-cell-style="{ background:'transparent',
                         height:'45px',color:'#000000',border:'none'}">

                            <el-table-column prop="songName" label="歌曲" width="481px" />
                            <el-table-column prop="artist" label="歌手" width="400px" />
                            <el-table-column prop="album" label="专辑" width="300px" />
                            <el-table-column label="操作" width="300px">
                              <template v-slot="scope">
                              <el-button :icon="Plus" class="delete" background='transparent' circle @click="query(scope.row.song_id)"/>
                              </template>
                            </el-table-column>
                        </el-table>

  <el-dialog v-model="addSongTolistdialogVisible" title="添加该歌曲到歌单" width="30%">
    <span>  
      <el-form
      :label-position="labelPosition"
      label-width="100px"
      :model="CreateListsData"
      style="max-width: 460px"
      >
        <el-form-item label="要添加的歌单">
          <el-select
            v-model="addcurrentlistid"
            clearable
            placeholder="请选择"
          >
            <el-option 
              v-for= "item in CreateListsData"
              :key = "item.id"
              :label= "item.name"
              :value= "item.id"/>
          </el-select>
        </el-form-item>
      </el-form>
    </span>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addSongTolistdialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addSongToList">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>
  

<style>
.demo-tabs {
  color: #FFFFFF;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 70px;
  margin-right: 70px;
  font-size: 16px;
}

.demo-tabs>.el-tabs__item.is-active {
  color: #2E8CAC;

}

.el-tabs__item {
  color: #fff;
}

.el-tabs__item.is-active {
  color: #2E8CAC;
}

.el-tabs__item:hover {
  color: #2E8CAC;
}



.el-tabs__active-bar {
  background-color: #2E8CAC;
  text-align: center;
  width: 100px !important;
  /* 不加important  不会生效，important会覆盖行内样式权限级别*/
  left: 4.8%;
  /* 根据实际情设置，尽量居中就行*/
}


.el-table {
  background-color: transparent;
}


.el-table th>.cell {
  color: #7F7F7F;
  font-size: 16px;
}



.el-table tr {
  background-color: transparent;
  color: white;
  font-size: 16px;
  height: 60px;


}

.el-table td.el-table__cell {
  border: 0px;
}



.el-table--enable-row-hover .el-table__body tr:hover>td {
  background-color: rgba(38, 38, 38, 0.75) !important;
  color: white !important;
}


.image1 {
  width: 230px;
  height: 230px;
  display: block;
}

.albumInfo {
  background-color: transparent;
}

.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
}

.image {
  width: 120px;
  height: 120px;
}
</style>