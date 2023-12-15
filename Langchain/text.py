
#引入mongo数据库
import pymongo
myclient = pymongo.MongoClient('localhost', 27017)
db = myclient.SONGLIST
collection = db.songs

# 指定保存的文件名

"""
my_list = list(collection.find({}))
my_string = str(my_list)
# 打开文件并写入字符串
with open(file_name, "w",encoding="utf-8") as file:
    file.write(my_string)
"""
i=1
for coll in collection.find():
    if(i<=50):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs1.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>50 and i<=100):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs2.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>100 and i<=150):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs3.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>150 and i<=200):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs4.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>200 and i<=250):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs5.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>250 and i<=300):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs6.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>300 and i<=350):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs7.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>350 and i<=400):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs8.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>400 and i<=450):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs9.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>450 and i<=500):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs10.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>500 and i<=550):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs11.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>550 and i<=600):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs12.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>600 and i<=650):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs13.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>650 and i<=700):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs14.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>700 and i<=750):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs15.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>750 and i<=800):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs16.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>800 and i<=850):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs17.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>850 and i<=900):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs18.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue
    if(i>950 and i<=1000):
        id_int = coll.get("song_id")
        name=coll.get("song_name")
        singer=coll.get("artist")
        id=str(id_int)
        emotion=coll.get("emotion")
        with open("Songs19.txt", "a",encoding="utf-8") as file:
            file.write("{id="+id+",name="+name+",songer="+singer+",emotion="+emotion+"}\n")
        i+=1
        continue

print(f"文件已成功保存。")