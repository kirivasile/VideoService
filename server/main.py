from flask import Flask, jsonify
from db import VideoDB

app = Flask(__name__)
video_db = VideoDB()

@app.route("/")
def hello():
    res = video_db.get_videos()
    return jsonify([{"id": x[0], "name": x[1], "videoUrl": x[2]} for x in res])
    

if __name__ == "__main__":
    app.run(host="0.0.0.0")