import json

from flask import Flask, request
from chat_engine import last_analysis

app = Flask(__name__)

data1 = ""


@app.route('/chat', methods=['GET'])
def api_endpoint():
    # 调用你的函数
    get_data = request.args.to_dict()
    text = get_data.get('input')
    result = last_analysis(text)

    # 返回结果
    print(result)
    message = {
        "ans": result['summary'],
        "id": result['song_id'],
    }

    return json.dumps(message)


if __name__ == '__main__':
    app.run(debug=True)