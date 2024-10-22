package scala.homework4.DataSet

case class TaxiZones(
                      LocationID: BigInt,
                      Borough: String,
                      Zone: String,
                      service_zone: String
)


object TaxiZones {
  def apply(c: TaxiZones): TaxiZones = {
    TaxiZones(
      c.LocationID,
      c.Borough,
      c.Zone,
      c.service_zone
    )
  }
}
