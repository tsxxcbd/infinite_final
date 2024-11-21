import json

import pymongo

from chat_engine import analyze_song_emotion
from song import Song


def run(file_path):
    # my_client = pymongo.MongoClient('localhost', 27017)
    # db = my_client.SONGLIST
    # collection = db.songs
    # songs_data = collection.find()
    #
    # for song_data in songs_data:
    #     song = Song(
    #         song_id=song_data.get('song_id'),
    #         song_name=song_data.get('song_name'),
    #         lyrics=song_data.get('lyrics'),
    #         comments=song_data.get('comments')
    #     )
    #     result = analyze_song_emotion(song)
    #     print(result)
    with open(file_path, 'r', encoding='utf-8') as file:
        data = json.load(file)
        for item in data:
            song_id = item['song_id']
            song_name = item['song_name']
            lyrics = item['lyrics']
            comments = item['comments']
            song = Song(song_id, song_name, lyrics, comments)
            # 分析歌曲情感
            result = analyze_song_emotion(song)
            print(result)

