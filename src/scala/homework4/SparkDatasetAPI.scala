package ru.otus.spark

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object SparkDatasetAPI {
  def main(args: Array[String]): Unit = {

    var path_to_customers = " "
    var path_to_retail = " ";
    var path_to_result = " ";

    val spark: SparkSession = SparkSession
      .builder()
      .appName("SparkDataFrameAPI")
      .master("local[*]")
      .getOrCreate

    import spark.implicits._

    val customers = spark.read
      .json(path_to_customers)
      .as[Customers]
      .filter(_.Country != "Unspecified")
      .map(Cust(_))

    val retail = spark.read
      .json(path_to_retail)
      .select(
        $"CustomerID".cast(LongType),
        $"Quantity".cast(IntegerType),
        $"UnitPrice".cast(DoubleType)
      )
      .as[Retail]

    val customerRetail = customers
      .join(retail, Seq("CustomerId"))
      .withColumn("value", $"Quantity" * $"UnitPrice")
      .groupBy("Country", "Birthdate")
      .agg(min("value").as("min"), mean("value").as("mean"), max("value").as("max"))
      .orderBy("Country", "Birthdate")
      .as[CustomerRetail]

    customerRetail.toJSON.write
      .mode(SaveMode.Overwrite)
      .text(path_to_result)

    /*
    customerRetail.write
      .mode(SaveMode.Overwrite)
      .json(args(2))
     */

    spark.stop()
  }
}
