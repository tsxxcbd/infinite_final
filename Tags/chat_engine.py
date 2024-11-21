import os
from openai import OpenAI

from file_handler import write_to_json_file, load_songs_from_json
from prompt import EMOTION_PROMPT, USR_PROMPT, CHAT_PROMPT
from similarity_analyzer import find_most_similar_song


def call_llm(sys_prompt, usr_prompt, model="qwen-plus"):
    """
    输入prompt，调用llm
    :param prompt:
    :param model:
    :return:
    """
    client = OpenAI(
        # 若没有配置环境变量，请用百炼API Key将下行替换为：api_key="sk-xxx",
        api_key=os.getenv("DASHSCOPE_API_KEY"),
        base_url="https://dashscope.aliyuncs.com/compatible-mode/v1"
    )
    completion = client.chat.completions.create(
        model=model,
        messages=[
            {'role': 'system', 'content': sys_prompt},
            {'role': 'user', 'content': usr_prompt}],
    )

    return completion.choices[0].message.content
    # api_key = os.environ.get("OPENAI_API_KEY")
    # base_url = os.environ.get("OPENAI_API_BASE")
    #
    # client = OpenAI(
    #     api_key=api_key,
    #     base_url=base_url
    # )
    #
    # messages = [
    #     {"role": "system", "content": prompt}
    # ]
    #
    # response = client.chat.completions.create(
    #     model=model,
    #     messages=messages,
    #     temperature=0,
    # )
    #
    # return response


def analyze_song_emotion(song):
    """
    根据歌曲信息，返回歌曲情感描述以及情感标签
    :param song:
    :return:
    """
    prompt_data = {
        "song_name": song.song_name,
        "lyrics": song.lyrics,
        "comments": song.comments
    }

    emotion_prompt = EMOTION_PROMPT.format(**prompt_data)
    usr_prompt = USR_PROMPT.format()

    response_message = call_llm(emotion_prompt, usr_prompt)
    # result = response_message.choices[0].message.content

    write_to_json_file("song_info/song_information.json", response_message, song.song_id)

    return response_message


def analyze_usr_mood(usr_input):
    """
    根据用户输入的要求，返回表示用户心情的二字词语
    :param usr_input:
    :return:
    """
    prompt_data = {
        "usr_input": usr_input
    }
    chat_prompt = CHAT_PROMPT.format(**prompt_data)
    usr_prompt = USR_PROMPT.format()

    response_message = call_llm(chat_prompt, usr_prompt)

    return response_message


def get_song_by_usr_mood(mood):
    """
    根据用户的心情推荐歌曲
    比较歌曲的tag和mood的相似度
    :param mood: 表示用户心情的二字词语
    :return:
    """
    songs = load_songs_from_json("song_info/song_information.json")
    song = find_most_similar_song(songs, mood)
    return song


def last_analysis(usr_input):
    mood = analyze_usr_mood(usr_input)
    result = get_song_by_usr_mood(mood)
    return result
