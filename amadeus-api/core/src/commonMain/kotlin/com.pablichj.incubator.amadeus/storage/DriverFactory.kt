package com.pablichj.incubator.amadeus.storage

import app.cash.sqldelight.db.SqlDriver
import com.pablichj.amadeus.Database

expect class DriverFactory {
  fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
  val driver = driverFactory.createDriver()
  return Database(driver)
}
