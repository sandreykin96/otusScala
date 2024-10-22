package scala.homework4

case class Cars(
                 Name: String,
                 Miles_per_Gallon: Double,
                 Cylinders: BigInt,
                 Displacement: Double,
                 Horsepower: BigInt,
                 Weight_in_lbs: BigInt,
                 Acceleration: Double,
                 Year: String,
                 Origin: String,
)


object Cars {
  def apply(c: Cars): Cars = {
    Cars(
      c.Name,
      c.Miles_per_Gallon,
      c.Cylinders,
      c.Displacement,
      c.Horsepower,
      c.Weight_in_lbs,
      c.Acceleration,
      c.Year,
      c.Origin
    )
  }
}
