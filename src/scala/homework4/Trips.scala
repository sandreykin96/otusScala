package scala.homework4

case class Trips(
                 VendorID: BigInt,
                 tpep_pickup_datetime: String,
                 tpep_dropoff_datetime: String,
                 passenger_count: Option[BigInt],
                 trip_distance: Option[Double],
                 RatecodeID: Option[BigInt],
                 store_and_fwd_flag: String,
                 PULocationID: Option[BigInt],
                 DOLocationID: Option[BigInt],
                 payment_type: Option[BigInt],
                 fare_amount: Option[Double],
                 extra: Option[Double],
                 mta_tax: Option[Double],
                 tip_amount: Option[Double],
                 tolls_amount: Option[Double],
                 improvement_surcharge: Option[Double],
                 total_amount: Option[Double]
)

object Trips {
  def apply(c: Trips): Trips = {
    Trips(
      c.VendorID,
      c.tpep_pickup_datetime,
      c.tpep_dropoff_datetime,
      c.passenger_count,
      c.trip_distance,
      c.RatecodeID,
      c.store_and_fwd_flag,
      c.PULocationID,
      c.DOLocationID,
      c.payment_type,
      c.fare_amount,
      c.extra,
      c.mta_tax,
      c.tip_amount,
      c.tolls_amount,
      c.improvement_surcharge,
      c.total_amount
    )
  }
}
