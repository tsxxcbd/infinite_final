import os
import json


def write_to_json_file(path, content, song_id):
    # 如果 content 是字符串，尝试将其解析为字典
    if isinstance(content, str):
        content = json.loads(content)

    content['song_id'] = song_id
    # 检查文件是否存在
    if os.path.exists(path):
        # 如果文件存在，先读取现有数据
        with open(path, 'r', encoding='utf-8') as file:
            try:
                # 尝试加载现有的 JSON 数据
                data = json.load(file)
            except json.JSONDecodeError:
                # 如果文件是空的或无法解码，初始化为一个空列表
                data = []
    else:
        # 如果文件不存在，初始化为一个空列表
        data = []

    # 将新内容添加到数据列表中
    data.append(content)

    # 将更新后的数据写回文件
    with open(path, 'w', encoding='utf-8') as file:
        json.dump(data, file, ensure_ascii=False, indent=4)


def load_songs_from_json(path):
    with open(path, 'r', encoding='utf-8') as file:
        return json.load(file)


