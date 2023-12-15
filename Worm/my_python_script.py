import requests
import pymongo

# MongoDB连接
client = pymongo.MongoClient("mongodb://localhost:27017/")  # 替换成你的MongoDB连接信息
db = client["music_db"]  # 数据库名，如果不存在会被创建
collection = db["songs"]  # 集合名，如果不存在会被创建


# 榜单的歌单 ID（示例：飙升榜）
playlist_ids = {
    # "黑胶vip爱搜榜": "7785091694",
    #  "云音乐民谣榜": "5059661515",
    # "黑胶VIP爱听榜": "5453912201",
    # "国语珍藏": "6845897480",
    "云贝推歌精选": "5186456364"

    #以下是需要更新爬取的榜单
    # "飙升榜": "19723756",  # 你需要替换为实际的榜单 ID
    # "新歌榜": "3779629",  # 同样需要替换为实际的榜单 ID
    # "热歌榜": "3778678",   # 替换为实际的榜单 ID
    # "billboard": "60198",
    # "听歌识曲榜": "6688069460"

}


# 获取热门歌曲列表
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
    'Referer': 'https://music.163.com/',
}

# 循环获取每个榜单的歌曲信息
for playlist_name,playlist_id in playlist_ids.items():
    playlist_url = 'http://music.163.com/api/playlist/detail?id=' + playlist_id

    response = requests.get(playlist_url, headers=headers)
    data = response.json()

    for song in data['result']['tracks']:
        song_id = song['id']

         # 检查数据库中是否已存在该歌曲的ID
        if collection.find_one({'song_id': song_id}):
            print(f"Song with ID {song_id} already exists in the database.")
            continue

        song_name = song['name']
        artist = song['artists'][0]['name']
        album = song['album']['name']

        # 获取评论
        comments_url = f'http://music.163.com/api/v1/resource/comments/R_SO_4_{song_id}?limit=20'
        comments_response = requests.get(comments_url, headers=headers)
        comments_data = comments_response.json()
        comments = [comment['content'] for comment in comments_data['comments']]

        # 获取歌词
        lyrics_url = f'http://music.163.com/api/song/lyric?id={song_id}&lv=-1&kv=-1&tv=-1'
        lyrics_response = requests.get(lyrics_url, headers=headers)
        lyrics_data = lyrics_response.json()
        lyrics = lyrics_data['lrc']['lyric'] if 'lrc' in lyrics_data else None

        import re
    
        # 去掉所有[]及其中的内容
        str_without_brackets = re.sub(r'\[[^\]]*\]', '', lyrics)

        # 去掉每首歌的开头部分，eg：歌词 : 周杰伦
        str_without_header = re.sub(r' [^ ]* : [^\n]*\n', '', str_without_brackets)

        # 根据换行符进行分割
        lyrics = str_without_header.split('\n')

        # 存入MongoDB
        song_info = {
            'song_id': song_id,
            'song_name': song_name,
            'artist': artist,
            'album': album,
            'comments': comments,
            'lyrics': lyrics
        }
        collection.insert_one(song_info)
