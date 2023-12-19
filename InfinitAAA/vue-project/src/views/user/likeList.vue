<script lang="ts" setup>
import { VueElement, proxyRefs, ref } from 'vue'
import type { TabsPaneContext } from 'element-plus'

/*import axios from 'axios'
import {getCurrentInstance} from 'vue';
import type { ComponentInternalInstance } from 'vue';
const { proxy } = getCurrentInstance() as ComponentInternalInstance;*/

const hhhName = ref('third')
const likeName = ref('third1')
import {
  Delete,RefreshRight,Plus
} from '@element-plus/icons-vue'

const handleClick = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}

const handleClick1 = (tab: TabsPaneContext, event: Event) => {
  console.log(tab, event)
}


const albumData = [
  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },
  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },  
  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },  
  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },
  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },  {
    cover: 'https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg',
    album: 'aaa',
    singer: 'Tom',
    number: '14首',
  },

  

]

const tableData = [
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },  
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },
  {
    id:'2102012051',
    song: 'lover',
    singer: 'Tom',
    album: 'lover',
    long: '5:20',
  },

]

let LikeSongData = [];
let LikeListsData = [];
let CreateListsData = [];
let User = {id:"10"};
let DeleteCreatedialogVisible = ref(false);
let DeletedialogVisible = ref(false);
let AdddialogVisible = ref(false)
let currentlistid = "";
let addcurrentsongid = "";
let addcurrentlistid = "";
let addSongTolistdialogVisible=ref(false);
import { reactive } from 'vue'
import type { FormProps } from 'element-plus'

const labelPosition = ref<FormProps['labelPosition']>('right')

const formLabelAlign = reactive({
  name: '',
  profile: '',
})

/*
const query=async()=>{
  try{
    const response1 = await proxy?.$axios.get("/api/PlayList/UserLikelists?userid="+User.id);
    const response2 = await proxy?.$axios.get("/api/PlayList/UserCreatelists?userid="+User.id);
    const response3 = await proxy?.$axios.get("/api/PlayList/UserlikedSong?userid="+User.id);

    LikeListsData = response1?.data;
    CreateListsData = response2?.data;
    LikeSongId = response3?.data;


  }catch(e){
    alert("获取数据失败"+e.message)
  }
}

const deletelikeButton=async()=>{

  try{

    console.log(currentlistid);

    await proxy?.$axios.delete("/api/PlayList/deletelikelist?id="+currentlistid);
    query();

    DeleteCreatedialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

const deletecreateButton=async()=>{
  try{
    console.log(currentlistid);
    await proxy?.$axios.delete("/api/PlayList/deletelist?id="+currentlistid);
    query();
    DeletedialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

const addcreateButton=async()=>{
  try{

    await proxy?.$axios.post("/api/PlayList/newlist?name="+formLabelAlign.name+"&profile="+formLabelAlign.profile+"&userid="+User.id);
    query();
    AdddialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

const addSongToList=async()=>{
  try{
    console.log(addcurrentsongid);
    console.log(addcurrentlistid);

    await proxy?.$axios.post("/api/PlayList/addonesong?PlayListId="+addcurrentlistid+"&songId="+addcurrentsongid);
    query();
    AdddialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

*/
import { queryUserLikeLists, queryUserCreateLists,
  queryUserLikeSong,deleteLikeList,
  deleteCreateList,addCreateList,
  addSongIntoList} from '../../api/playlist.js'

const query=async()=>{
  try{

    LikeListsData = await queryUserLikeLists(User.id)
    CreateListsData = await queryUserCreateLists(User.id);
    LikeSongData = await queryUserLikeSong(User.id);

  }catch(e){
    alert("获取数据失败"+e.message)
  }
}

const deletelikeButton=async()=>{

  try{
  
    console.log(currentlistid);
  
    deleteLikeList(currentlistid);
    query();
    DeletedialogVisible=ref(false)
  
  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

const deletecreateButton=async()=>{
  try{
    console.log(currentlistid);

    deleteCreateList(currentlistid);
    query();
    DeleteCreatedialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

const addcreateButton=async()=>{
  try{

    addCreateList(formLabelAlign.name,formLabelAlign.profile,User.id)
    query();
    AdddialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}

const addSongToList=async()=>{
  try{
    console.log(addcurrentsongid);
    console.log(addcurrentlistid);

    addSongIntoList(addcurrentsongid,addcurrentlistid);
    query();
    addSongTolistdialogVisible=ref(false)

  }catch(e){
    alert("获取数据失败"+e.message)
  }

}




const url = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F3d7413ad-9cea-48be-ae51-5ef3921edc01%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704703597&t=141658c39e3ff55c042a6e45a23b6dd7"


const user = ref({
  name: 'orange',
  sex: '男',
  age: 20,
  nickname: 'orange',
});




</script>
<template>

<div class="user2" style="display: grid; grid-template-columns: auto auto auto; align-items: center;">
    <el-image style="width: 120px; height: 120px" :src="url" class="avatar2" />
    <h4 class="username2" style="grid-column: 2 / 8;">{{ user.nickname }}</h4>
    <el-button class="refresh" :icon="RefreshRight" circle @click="query()" style="grid-column: 9 / 10;"></el-button>
    <el-button class="refresh" :icon="Plus" circle @click="AdddialogVisible=true" style="grid-column: 10 / 10;"></el-button>
</div>

    <el-tabs v-model="hhhName" class="my-tabs" @tab-click="handleClick">
    <el-tab-pane label="我喜欢" name="first">
        <el-tabs v-model="likeName" class="like-tabs" @tab-click="handleClick1">
        <el-tab-pane label="歌曲" name="first1">
            <el-table :data="LikeSongData" height="380"  class="songs" :header-cell-style="{ background:'transparent',
                         height:'45px',color:'#000000',border:'none'}">
                            <el-table-column prop="songName" label="歌曲" width="350px" />
                            <el-table-column prop="artist" label="歌手" width="320px" />
                            <el-table-column prop="album" label="专辑" width="300px" />
                            <el-table-column label="操作" width="300px">
                              <template v-slot="scope">
                              <el-button :icon="Plus" class="delete" background='transparent' circle @click="addSongTolistdialogVisible = true,addcurrentsongid=scope.row.songid"/>
                              </template>
                            </el-table-column>
                            </el-table>
        </el-tab-pane>
        <el-tab-pane label="歌单" name="third1">
            <el-table :data=LikeListsData height="380"  class="songs" :header-cell-style="{ background:'transparent',
                         height:'45px',color:'#000000',border:'none'}">
                            <el-table-column prop="name" label="歌单名" width="400px" />
                            <el-table-column prop="creatorname" label="创作者" width="250px" />
                            <el-table-column prop="number" label="歌曲数目" width="250px" />
                            <el-table-column label="操作" width="250px">
                              <template v-slot="scope">
                              <el-button :icon="Delete" class="delete" background='transparent' circle @click="DeletedialogVisible = true,currentlistid=scope.row.id"/>
                            </template>
                            </el-table-column>
                        </el-table>
        </el-tab-pane>
        </el-tabs>
    </el-tab-pane>
    <el-tab-pane label="我的歌单" name="second">
        <el-table :data="CreateListsData" height="450"  class="songs" :header-cell-style="{ background:'transparent',
                        height:'45px',color:'#000000',border:'none'}">
                            <!-- <el-table-column label="封面" align="center" prop="workPhoto" width="400"> -->
                                <!-- <template #default="scope"> -->
                                    <!-- <div style="display: flex; align-items: center"> -->
                                        <!-- <el-image style="width: 100px; height: 100px" :src="scope.row.cover"/> -->
                                    <!-- </div> -->
                                <!-- </template> -->
                            <!-- </el-table-column> -->
                            <el-table-column prop="name" label="歌单名" width="300px" />
                            <el-table-column prop="number" label="歌曲数目" width="250px" />
                            <el-table-column prop="profile" label="歌曲简介" width="600px" />
                            <el-table-column label="删除" width="250px">
                              <template v-slot="scope">
                              <el-button :icon="Delete" class="delete" background="transparent" circle @click="DeleteCreatedialogVisible = true,currentlistid=scope.row.id,query()"/>
                            </template>
                            </el-table-column>
                            
        </el-table>
    </el-tab-pane>
    <el-tab-pane label="音乐画像" name="third">
      <el-carousel :interval="4000" type="card" height="450px">
    <el-carousel-item>
      <div class="user-info">
        <h1>基本资料</h1>
        <h2>{{ user.name }}</h2>
        <p>性别: {{ user.sex }}</p>
        <p>年龄: {{ user.age }}</p>
        <p>爱听音乐的男孩</p>
      </div>
    </el-carousel-item>
  </el-carousel>
    </el-tab-pane>
    </el-tabs>
    
    <el-dialog v-model="DeletedialogVisible" title="是否要取消收藏该歌单" width="40%">
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="DeletedialogVisible = false">取消</el-button>
          <el-button type="primary" @click="DeletedialogVisible= false,deletelikeButton(),query()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="DeleteCreatedialogVisible" title="是否要删除该歌单" width="40%">
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="DeleteCreatedialogVisible = false">取消</el-button>
          <el-button type="primary" @click="DeleteCreatedialogVisible= false,deletecreateButton(),query()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
    <el-dialog v-model="AdddialogVisible" title="创建歌单" width="30%">
    <span>  
      <el-form
      :label-position="labelPosition"
      label-width="100px"
      :model="formLabelAlign"
      style="max-width: 460px"
    >
    <el-form-item label="歌单名">
      <el-input v-model="formLabelAlign.name" />
    </el-form-item>
    <el-form-item label="简介">
      <el-input v-model="formLabelAlign.profile" />
    </el-form-item>
      </el-form>
    </span>

    <template #footer>
      <span class="dialog-footer">
        <el-button @click="AdddialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addcreateButton(),AdddialogVisible= false,query()">
          确定
        </el-button>
      </span>
    </template>
    </el-dialog>

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
          <el-button type="primary" @click="addSongToList(),addSongTolistdialogVisible= false,query()">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>

</template>

<style>
.refresh {
  background-color: transparent;
  border: none;
}
/*  */
.refresh i{
  color: #f2f2f2;
  font-size: 30px;
}

.delete {
  background-color: transparent;
  border: none;
}

.delete i {
  color: #f2f2f2;
  font-size: 22px;
}

.avatar2 {
    margin-left: 60px;
    border-radius: 50%;
}

.username2 {
    
    font-size: 28px;
    color: #FFFFFF;
}

.my-tabs {
    color: #FFFFFF;
    margin-top: 50px;

}


.my-tabs > .el-tabs__item.is-active {
    color: #2E8CAC;
    
}

.my-tabs 
{
    .el-tabs__item{    
    color:#fff ;
    font-size: 18px;
    vertical-align: middle; 
    margin-left: 70px;
    margin-right: 70px;
    }

    .el-tabs__item.is-active{    
        color:#2E8CAC ;
    }
    
    .el-tabs__item:hover{    
        color:#2E8CAC ;
    }

    .el-tabs__active-bar{
    background-color: #2E8CAC ;  
    text-align: center;
    width: 90px !important;      /* 不加important  不会生效，important会覆盖行内样式权限级别*/
    left: -1%;                  /* 根据实际情设置，尽量居中就行*/
}
}







.el-carousel__item {
  background-image: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F3d7413ad-9cea-48be-ae51-5ef3921edc01%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704703597&t=141658c39e3ff55c042a6e45a23b6dd7");
  text-align: center;
 vertical-align: middle; 
 

}

.like-tabs {
    color: #FFFFFF;
    margin-top: 10px;

    .like-tabs > .el-tabs__item.is-active {
    color: #2E8CAC;
    
}

.el-tabs__item{    
    color:#fff ;
    font-size: 14px;
    vertical-align: middle; 
    margin-left: 60px;
    margin-right: 60px;
    
}

.el-tabs__item.is-active{    
       color:#2E8CAC ;
}
   
.el-tabs__item:hover{    
       color:#2E8CAC ;
}



.el-tabs__nav-wrap::after{
   
    display: none;
}

.el-tabs__active-bar{
    display: none;
}
}

/* .el-carousel__item { */
  /* background-image: url("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fsafe-img.xhscdn.com%2Fbw1%2F3d7413ad-9cea-48be-ae51-5ef3921edc01%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fsafe-img.xhscdn.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1704703597&t=141658c39e3ff55c042a6e45a23b6dd7"); */
/*   */
/*   */
/* } */

.el-carousel {
  text-align: center;
}



h1 {
  font-size: 30px;
  color: #f2f2f2;
}

h2 {
  margin-top: 50px;
}

.user-info {
  height: 100%;
  text-align: center;
  padding: 20px;
  backdrop-filter: blur(10px); 
  margin: auto;
}







</style>
