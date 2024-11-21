from nltk.corpus import wordnet as wn


def get_similarity(str1, str2):
    word1 = wn.synset(f"{str1}.n.01")
    word2 = wn.synset(f"{str2}.n.01")

    # 计算Wu-Palmer相似度
    wu_palmer_similariry = word1.wup_similarity(word2)
    return wu_palmer_similariry


def find_most_similar_song(songs, mood):
    best_match = None
    highest_similarity = 0

    for song in songs:
        tag = song.get('tag', '')
        similarity = get_similarity(tag, mood)
        if similarity > highest_similarity:
            highest_similarity = similarity
            best_match = song

    return best_match
