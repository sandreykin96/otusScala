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
    var path_to_yellow_taxi = "src/resources/data/yellow_taxi_jan_25_2018"

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

    val result = times
      .groupBy(x => x)
      .map(s => Tuple2(s._1, s._2.size))
      .sortBy(_._2, ascending = false)
      .map(x => s"${x._1} ${x._2.toString}")
      .cache()

    result.saveAsTextFile("src/resources/result/times")
    result.foreach(println)

    spark.stop()
  }
}

