package scala.homework4.Rdd

import cats.Show
import org.apache.spark.sql._
import org.apache.spark.sql.functions.{avg, count, mean, round}

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import scala.homework4.Trips

object Main extends App {
  rdd();

  def rdd(): Unit = {
    var path_to_yellow_taxi = "src/resources/data/yellow_taxi_jan_25_2018/part-00004-5ca10efc-1651-4c8f-896a-3d7d3cc0e925-c000.snappy.parquet"

    val spark: SparkSession = SparkSession
      .builder()
      .appName("SparkRddAPI")
      .master("local[*]")
      .getOrCreate();

    import spark.implicits._

    val tripsRdd = spark
      .read.parquet(path_to_yellow_taxi)
      .as[Trips]
      .rdd

    val format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    val times = tripsRdd.map( x => format.parse(x.tpep_pickup_datetime).getHours)

    val countedAndOrdered = times.groupBy(identity)
                                  .map{ case (element, occurrences) => (element, occurrences.size) }
                                  .sortBy(_._2, false).collect


    countedAndOrdered.map { case (element, count) => println(s"Hour: $element, Count: $count" )}

    val asString = countedAndOrdered.map{ case (element, count) => (s"Hour: $element, Count: $count" )}
    asString.map(_=> println())

    val df = spark.createDataset(asString)
    df.write.mode(SaveMode.Overwrite).text("spark-warehouse/avgTrips")

    spark.stop()
  }
}

