package scala.homework4.DataSet

import org.apache.spark.sql._

object Main extends App {
dataFrame();

  def dataFrame(): Unit = {

    var path_to_cars = "src/resources/data/cars.json"
    var path_to_cars_dates = "src/resources/data/cars_dates.json"
    var path_to_taxi_zones = "src/resources/data/taxi_zones.json"
    var path_to_yellow_taxi = "src/resources/data/yellow_taxi_jan_25_2018/part-00004-5ca10efc-1651-4c8f-896a-3d7d3cc0e925-c000.snappy.parquet"

    val spark: SparkSession = SparkSession
      .builder()
      .appName("SparkDataSetAPI")
      .master("local[*]")
      .getOrCreate();

    import spark.implicits._

    val cars = spark.read
      .json(path_to_cars)
      .as[Cars]
      //.filter(_.Country != "Unspecified")
      .map(Cars(_))

    /*val taxiZones = spark.read
      .json(path_to_cars)
      .as[Cars]
      //.filter(_.Country != "Unspecified")
      .map(Cars(_))*/

    val taxiZones = spark.read
      .json(path_to_taxi_zones)
      .as[TaxiZones]
      //.filter(_.Country != "Unspecified")
      .map(TaxiZones(_))

/*
    val retail = spark.read
      .json(path_to_retail)
      .select(
        $"CustomerID".cast(LongType),
        $"Quantity".cast(IntegerType),
        $"UnitPrice".cast(DoubleType)
      )
      .as[Retail]

    val customerRetail = cars
      .join(retail, Seq("CustomerId"))
      .withColumn("value", $"Quantity" * $"UnitPrice")
      .groupBy("Country", "Birthdate")
      .agg(min("value").as("min"), mean("value").as("mean"), max("value").as("max"))
      .orderBy("Country", "Birthdate")
      .as[CustomerRetail]

    customerRetail.toJSON.write
      .mode(SaveMode.Overwrite)
      .text(path_to_result)
*/
    /*
    customerRetail.write
      .mode(SaveMode.Overwrite)
      .json(args(2))
     */

    spark.stop()
  }
}
