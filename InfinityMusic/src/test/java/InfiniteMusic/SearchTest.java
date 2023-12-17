package InfiniteMusic;

import com.mongodb.client.*;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SearchTest {

    @Test
    public void testSearch() {
        // 替换为自己的MongoDB连接字符串，一般默认是这个
        String connectionString = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);

        // 替换为自己的数据库名称和集合名称，我把你的哪个json引入重新命名了Songs
        String databaseName = "Songs";
        String collectionName = "Songs";

        MongoDatabase database = mongoClient.getDatabase(databaseName);
        MongoCollection<Document> collection = database.getCollection(collectionName);

        // 替换为搜索关键词，可以是歌名，歌词，评论，专辑，作者
        String keyword = "OH LA LA LA";
        String escapedKeyword = Pattern.quote(keyword);
        //搜索方法
        int searchMethod =1;
        //综合搜索
        if(searchMethod==1)
        {        // MongoDB 聚合操作，每个元素都是 document
            AggregateIterable<Document> aggregation = collection.aggregate(Arrays.asList(
                    // 进行模糊匹配

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
                    // 开辟 "score" 计算 document 权值
                    new Document("$project",
                            new Document("song_name", 1)
                                    .append("song_id",1)
                                    .append("artist", 1)
                                    .append("album", 1)
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
                    // 根据权值给 document 排序
                    new Document("$sort",
                            new Document("score", -1)
                    )

            ));
            // 存储结果的列表
            List<Document> uniqueResults = new ArrayList<>();

            // 打印前20条搜索结果
            System.out.println("Search Results for '" + keyword + "':");
            int count = 0;

            for (Document result : aggregation) {
                // 检查文档是否已经打印过
                if (!uniqueResults.contains(result)) {
                    System.out.println(result.toJson());
                    uniqueResults.add(result);
                    count++;
                }

                // 如果已经打印了前20条，退出循环
                if (count >= 20) {
                    break;
                }
            }
        }
        //根据歌名搜索
        else if (searchMethod==2) {
            // MongoDB 聚合操作，每个元素都是 document
            AggregateIterable<Document> aggregation = collection.aggregate(Arrays.asList(
                    // 进行模糊匹配
                    new Document("$match",
                            new Document("$or",
                                    Arrays.asList(
                                            new Document("song_name", new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i"))

                                    )
                            )
                    ),
                    // 开辟 "score" 计算 document 权值
                    new Document("$project",
                            new Document("song_name", 1)
                                    .append("song_id",1)
                                    .append("artist", 1)
                                    .append("album", 1)
                                    .append("score",
                                            new Document("$sum",
                                                    Arrays.asList(
                                                            new Document("$cond", Arrays.asList(new Document("$regexMatch", new Document("input", "$song_name").append("regex", ".*" + escapedKeyword + ".*").append("options", "i")), 20, 0))
                                                    )
                                            )

                                    )
                    ),
                    // 根据权值给 document 排序
                    new Document("$sort",
                            new Document("score", -1)
                    )

            ));
            // 存储结果的列表
            List<Document> uniqueResults = new ArrayList<>();

            // 打印前20条搜索结果
            System.out.println("Search Results for '" + keyword + "':");
            int count = 0;

            for (Document result : aggregation) {
                // 检查文档是否已经打印过
                if (!uniqueResults.contains(result)) {
                    System.out.println(result.toJson());
                    uniqueResults.add(result);
                    count++;
                }

                // 如果已经打印了前20条，退出循环
                if (count >= 20) {
                    break;
                }
            }

        }
        //根据专辑搜索
        else if (searchMethod==3) {
            // MongoDB 聚合操作，每个元素都是 document
            AggregateIterable<Document> aggregation = collection.aggregate(Arrays.asList(
                    // 进行模糊匹配
                    new Document("$match",
                            new Document("$or",
                                    Arrays.asList(

                                            new Document("album", new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i"))

                                    )
                            )
                    ),
                    // 开辟 "score" 计算 document 权值
                    new Document("$project",
                            new Document("song_name", 1)
                                    .append("song_id",1)
                                    .append("artist", 1)
                                    .append("album", 1)
                                    .append("score",
                                            new Document("$sum",
                                                    Arrays.asList(
                                                            new Document("$cond", Arrays.asList(new Document("$regexMatch", new Document("input", "$album").append("regex", ".*" + escapedKeyword + ".*").append("options", "i")), 16, 0))

                                                    )
                                            )

                                    )
                    ),
                    // 根据权值给 document 排序
                    new Document("$sort",
                            new Document("score", -1)
                    )

            ));
            // 存储结果的列表
            List<Document> uniqueResults = new ArrayList<>();

            // 打印前20条搜索结果
            System.out.println("Search Results for '" + keyword + "':");
            int count = 0;

            for (Document result : aggregation) {
                // 检查文档是否已经打印过
                if (!uniqueResults.contains(result)) {
                    System.out.println(result.toJson());
                    uniqueResults.add(result);
                    count++;
                }

                // 如果已经打印了前20条，退出循环
                if (count >= 20) {
                    break;
                }
            }

        }
        //根据歌词搜索

        else if (searchMethod==4) {
            // MongoDB 聚合操作，每个元素都是 document
            AggregateIterable<Document> aggregation = collection.aggregate(Arrays.asList(
                    // 进行模糊匹配
                    new Document("$match",
                            new Document("$or",
                                    Arrays.asList(
                                            new Document("lyrics", new Document("$elemMatch",
                                                    new Document("$regex", ".*" + escapedKeyword + ".*").append("$options", "i")
                                            ))
                                    )
                            )
                    ),
                    // 开辟 "score" 计算 document 权值
                    new Document("$project",
                            new Document("song_name", 1)
                                    .append("song_id",1)
                                    .append("artist", 1)
                                    .append("album", 1)
                                  //  .append("lyrics",1)
                                    .append("score",
                                            new Document("$sum",
                                                    Arrays.asList(

                                                            new Document("$multiply", Arrays.asList(4, new Document("$size", "$lyrics")))
                                                    )
                                            )

                                    )
                    ),
                    // 根据权值给 document 排序
                    new Document("$sort",
                            new Document("score", -1)
                    )

            ));
            // 存储结果的列表
            List<Document> uniqueResults = new ArrayList<>();

            // 打印前20条搜索结果
            System.out.println("Search Results for '" + keyword + "':");
            int count = 0;

            for (Document result : aggregation) {
                // 检查文档是否已经打印过
                if (!uniqueResults.contains(result)) {
                    System.out.println(result.toJson());
                    uniqueResults.add(result);
                    count++;
                }

                // 如果已经打印了前20条，退出循环
                if (count >= 20) {
                    break;
                }
            }

        }




    }
}
