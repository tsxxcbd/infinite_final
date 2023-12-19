
<script lang="ts">
import { useRoute } from 'vue-router'
import { onMounted, ref } from 'vue'

export default {
    setup() {
        const route = useRoute()
        const {query:{name}} = route
        const {query:{albumCover}}=route
        
        const url =albumCover;

        const {query:{songId}}=route


        const commentData = ref([]);

        async function fetchCommentInfo() {
            const commentUrl = `http://localhost:3000/comment/music?id=${songId}&limit=1`; // 替换为实际的评论API地址
            try {
            const response = await fetch(commentUrl, {
            headers: {
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
            },
            });

            if (!response.ok) {
            throw new Error('Network response was not ok');
            }

            const data = await response.json();

            console.log(data)

            const comments = data.hotComments || []; // 确保 comments 是一个有效的数组

            const newComments = comments.map(comment => ({
            userAvatar: comment.user.avatarUrl,
            userName: comment.user.nickname,
            date: comment.timeStr,
            userComment: comment.content,
            }));

            console.log(comments)

            commentData.value = newComments;
            } catch (error) {
                console.error('There was a problem with the fetch operation:', error);
            }
        };
        onMounted(async () => {
        await fetchCommentInfo();
        });


        return {
            commentData,url,name
        }
    }

}


</script>


<template>
    <div class="albumInfo">
        <el-image style="width: 120px; height: 120px" :src="url" fit="cover"/>
        <p>{{ name }}</p>
    </div>
    <el-table  height="500px" :data="commentData" style="width: 95%" :show-header="false" :row-style="{height: '125px'} ">
        <el-table-column label="commentDetails">
        <template #default="{ row }">
            <div class="comment-details" >
                <div style="display: flex">
                    <el-image style="width: 70px; height:70px" :src="row.userAvatar" class="userAvatar"/>
                </div>
            <div class="userName-date-userComment">
                <div class="userName">{{ row.userName }}</div>
                <div class="date">{{ row.date }}</div>
                <div class="userComment">{{ row.userComment }}</div>

            </div>

            </div>
        </template>
        </el-table-column>
    </el-table>


</template>

<style>
.albumInfo {
    display: flex;
    margin-bottom: 50px;
}

.albumInfo p {
    color: #F2F2F2;
    font-size: 28px;
    margin-left: 150px;
    margin-top: 60px;
}

.comment-details {
    display: flex;
}

.userAvatar {
    width: 70px;
    height: 70px;
    border-radius: 50%;
    margin-left: 30px;
}

.userName-date-userComment {
    margin-left: 40px;
}

.userName {
    font-size: 14px;
    color: #AAAAAA;
}

.date {
    font-size: 14px;
    color: #333333;
    
}

.userComment {
    font-size: 15px;
    color: #AAAAAA;
}
</style>