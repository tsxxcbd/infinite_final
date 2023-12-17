package InfiniteMusic.service.impl;

import InfiniteMusic.service.SearchService;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.bson.Document;
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private MongoClient mongoClient;

    @Override
    public List<SearchResult> search(String keyword) {
        String escapedKeyword = Pattern.quote(keyword);

        MongoDatabase mDatabase = mongoClient.getDatabase("Songs");
        MongoCollection<Document> collection = mDatabase.getCollection("Songs");

        AggregateIterable<Document> aggregation = collection.aggregate(Arrays.asList(
                new Document("$match",
                        new Document("$or",
                                Arrays.asList(
                                        new Document("song_name", new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i")),
                                        new Document("artist", new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i")),
                                        new Document("album", new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i")),
                                        new Document("comments", new Document("$elemMatch",
                                                new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i")
                                        )),
                                        new Document("lyrics", new Document("$elemMatch",
                                                new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i")
                                        ))
                                )
                        )
                ),
                new Document("$project",
                        new Document("song_name", 1)
                                .append("artist", 1)
                                .append("album", 1)
                                .append("song_id",1)
                                .append("score",
                                        new Document("$sum",
                                                Arrays.asList(
                                                        new Document("$cond", Arrays.asList(new Document("$regexMatch", new Document("input", "$song_name").append("regex", ".*" + escapedKeyword + ".*").append("options", "i")), 20, 0)),
                                                        new Document("$cond", Arrays.asList(new Document("$regexMatch", new Document("input", "$artist").append("regex", ".*" + escapedKeyword + ".*").append("options", "i")), 20, 0)),
                                                        new Document("$cond", Arrays.asList(new Document("$regexMatch", new Document("input", "$album").append("regex", ".*" + escapedKeyword + ".*").append("options", "i")), 16, 0)),
                                                        new Document("$multiply", Arrays.asList(4, new Document("$size", "$comments"))),
                                                        new Document("$multiply", Arrays.asList(4, new Document("$size", "$lyrics")))
                                                )
                                        )
                                )
                ),
                new Document("$sort",
                        new Document("score", -1)
                )
        ));

        List<SearchResult> results = new ArrayList<>();
        for (Document doc : aggregation) {
            Integer song_id=doc.getInteger("song_id");
            String songName = doc.getString("song_name");
            String artist = doc.getString("artist");
            String album = doc.getString("album");
            results.add(new SearchResult(song_id,songName, artist, album));
        }

        return results;
    }
}
