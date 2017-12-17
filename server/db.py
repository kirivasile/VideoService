import sqlite3


class VideoDB:
    def __init__(self):
        self._db_name = 'videos.db'
        self._conn = sqlite3.connect(self._db_name)
        c = self._conn.cursor()
        c.execute('''CREATE TABLE IF NOT EXISTS videos
                        (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                         name TEXT,
                         videoUrl TEXT)''')
                         
    def get_videos(self):
        result = []
        try:
            c = self._conn.cursor()
            result = c.execute('''SELECT * FROM videos''').fetchall()
        except sqlite3.Error as e:
            print("Error on select: " + str(e))
        return result
        
    def insert_video(self, name, videoUrl):
        try:
            c = self._conn.cursor()
            c.execute('''INSERT INTO videos (name, videoUrl) VALUES (?, ?)''', (name, videoUrl))
            self._conn.commit()
        except sqlite3.Error as e:
            print("Error on insert: " + str(e))
            
    def close(self):
        self._conn.close()

if __name__ == "__main__":
    db = VideoDB()
    tempUrl = "http://jzvd.nathen.cn/c6e3dc12a1154626b3476d9bf3bd7266/6b56c5f0dc31428083757a45764763b0-5287d2089db37e62345123a1be272f8b.mp4"
    db.insert_video("Temporary video #1", tempUrl)
    db.insert_video("Temporary video #2", tempUrl)
    db.insert_video("Temporary video #3", tempUrl)
    db.insert_video("Temporary video #4", tempUrl)
    db.close()

                    
            
        