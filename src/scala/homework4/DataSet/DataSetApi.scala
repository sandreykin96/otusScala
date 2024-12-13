package scala.homework4.DataSet

import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.homework4.Trips

object Main extends App {
dataFrame();

  def dataFrame(): Unit = {
    var path_to_yellow_taxi = "src/resources/data/yellow_taxi_jan_25_2018"

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

    val tripsAvg = trips.groupBy(date_format('tpep_pickup_datetime, "dd-MM-yyyy").as("date"))
      .agg(
        round(sum('trip_distance), 2).as("total_day_distance"),
        round(mean('trip_distance), 2).as("mean_day_distance"),
        round(max('trip_distance), 2).as("max_day_distance"),
        round(min('trip_distance), 2).as("min_day_distance"),
        round(stddev('trip_distance), 2).as("stddev_day_distance")
      )

    tripsAvg.show()

    tripsAvg.write.mode(SaveMode.Overwrite).format("parquet").save("src/resources/result/avgTrips")

    spark.stop()
  }
}
