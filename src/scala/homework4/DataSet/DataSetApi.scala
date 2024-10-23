package scala.homework4.DataSet

import org.apache.spark.sql._
import org.apache.spark.sql.functions.{avg, count, mean, round}

import scala.homework4.Trips

object Main extends App {
dataFrame();

  def dataFrame(): Unit = {
    var path_to_yellow_taxi = "src/resources/data/yellow_taxi_jan_25_2018/part-00004-5ca10efc-1651-4c8f-896a-3d7d3cc0e925-c000.snappy.parquet"

    val spark: SparkSession = SparkSession
      .builder()
      .appName("SparkDataSetAPI")
      .master("local[*]")
      .getOrCreate();

    import spark.implicits._

    val trips = spark
      .read.parquet(path_to_yellow_taxi)
      .as[Trips]
      .map(Trips(_))

    trips.show(10)

    val tripsAvg = trips.groupBy("PULocationID")
      .agg(
        count("*").as("total_trips"),
        functions.min("trip_distance").as("min_distance"),
        functions.max("trip_distance").as("max_distance")
          )
      .as[AverageTrips];

    tripsAvg.show()

    tripsAvg.write.mode(SaveMode.Overwrite).format("parquet").saveAsTable("avgTrips")

    spark.stop()
  }
}
