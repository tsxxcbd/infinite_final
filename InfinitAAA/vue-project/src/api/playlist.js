import request from '@/utils/request.js'

//查询页面内的所有数据
//修改头像
export const queryUserLikeLists = (userid) => {
    return request.get("/api/PlayList/UserLikelists?userid=" + userid)
}

export const queryUserCreateLists = (userid) => {
    console.log(userid)
    return request.get("/api/PlayList/UserCreatelists?userid=" + userid)
}

export const queryUserLikeSong = (userid) => {
    console.log(userid)
    return request.get("/api/PlayList/UserlikedSong?userid=" + userid)
}

export const deleteLikeList = (currentlistid) => {
    return request.delete("/api/PlayList/deletelikelist?id=" + currentlistid)
}

export const deleteCreateList = (currentlistid) => {
    return request.delete("/api/PlayList/deletelist?id=" + currentlistid)
}

export const addCreateList = (name, profile, userid) => {
    return request.post("/api/PlayList/newlist?name=" + name + "&profile=" + profile + "&userid=" + userid)
}

export const addSongIntoList = (addcurrentsongid, addcurrentlistid) => {
    return request.post("/api/PlayList/addonesong?PlayListId=" + addcurrentlistid + "&songId=" + addcurrentsongid)
}

export const deleteSongFromList = (deletecurrentsongid, deletecurrentlistid) => {
    return request.delete("/api/PlayList/deleteonesong?PlayListId=" + deletecurrentlistid + "&songId=" + deletecurrentsongid)
}

export const getSongList = (playlistid) => {
    console.log(playlistid)
    return request.get("/api/PlayList/List/" + playlistid)
}

