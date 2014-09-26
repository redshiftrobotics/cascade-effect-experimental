#include <SD.h>

#include <LiquidCrystal.h>

#define HW__VERSION_CODE 10724 // SparkFun "9DOF Sensor Stick" version "SEN-10724" (HMC5883L magnetometer)
#define OUTPUT__BAUD_RATE 38400
#define OUTPUT__DATA_INTERVAL 20  // in milliseconds

// Output mode definitions (do not change)
#define OUTPUT__MODE_CALIBRATE_SENSORS 0 // Outputs sensor min/max values as text for manual calibration
#define OUTPUT__MODE_ANGLES 1 // Outputs yaw/pitch/roll in degrees
#define OUTPUT__MODE_SENSORS_CALIB 2 // Outputs calibrated sensor values for all 9 axes
#define OUTPUT__MODE_SENSORS_RAW 3 // Outputs raw (uncalibrated) sensor values for all 9 axes
#define OUTPUT__MODE_SENSORS_BOTH 4 // Outputs calibrated AND raw sensor values for all 9 axes
#define OUTPUT__FORMAT_TEXT 0 // Outputs data as text
#define OUTPUT__FORMAT_BINARY 1 // Outputs data as binary float
int output_mode = OUTPUT__MODE_ANGLES;
int output_format = OUTPUT__FORMAT_TEXT;
#define OUTPUT__STARTUP_STREAM_ON false  // true or false
boolean output_errors = false;  // true or false
#define OUTPUT__HAS_RN_BLUETOOTH false  // true or false

// Gyroscope
// "gyro x,y,z (current/average) = .../OFFSET_X  .../OFFSET_Y  .../OFFSET_Z
#define GYRO_AVERAGE_OFFSET_X ((float) 16.40)
#define GYRO_AVERAGE_OFFSET_Y ((float) 52.39)
#define GYRO_AVERAGE_OFFSET_Z ((float) -18.91)

// Check if hardware version code is defined
#ifndef HW__VERSION_CODE
  // Generate compile error
  #error YOU HAVE TO SELECT THE HARDWARE YOU ARE USING! See "HARDWARE OPTIONS" in "USER SETUP AREA" at top of Razor_AHRS.ino!
#endif

#include <Wire.h>


// Gain for gyroscope (ITG-3200)
#define GYRO_GAIN 0.06957 // Same gain on all axes
#define GYRO_SCALED_RAD(x) (x * TO_RAD(GYRO_GAIN)) // Calculate the scaled gyro readings in radians per second

// Stuff
#define GRAVITY 256.0f // "1G reference" used for DCM filter and accelerometer calibration
#define TO_RAD(x) (x * 0.01745329252)  // *pi/180
#define TO_DEG(x) (x * 57.2957795131)  // *180/pi

float degreedriftpersecond = 1.39149;

float gyro[3];
float yaw = 180;
float calibrationyaw = 180;
float Diff;
float StartingTime;

// DCM timing in the main loop
unsigned long timestamp;
unsigned long timestamp_old;
float G_Dt; // Integration time for DCM algorithm


void read_sensors() {
  Read_Gyro(); // Read gyroscope
}

// Apply calibration to raw sensor readings
void compensate_sensor_errors() 
{
    gyro[2] -= GYRO_AVERAGE_OFFSET_Z;
}

void setup()
{
  StartingTime = millis();
  // Init serial output
  //Serial.begin(OUTPUT__BAUD_RATE);
  

  setupNXTpin();

  
  // Init sensors
  delay(50);  // Give sensors enough time to start
  I2C_Init();
  Gyro_Init();
  
  // Read sensors, init DCM algorithm
  delay(20);  // Give sensors enough time to collect data
}

void PrintFloat(float ToPrint)
{
  char charBuf[10];
  dtostrf(ToPrint, 10, 5, charBuf);
  Serial.println(charBuf);
}

// Main loop
void loop()
{
  // Time to read the sensors again?
  if((millis() - timestamp) >= OUTPUT__DATA_INTERVAL)
  {
    timestamp_old = timestamp;
    timestamp = millis();
    if (timestamp > timestamp_old)
      G_Dt = (float) (timestamp - timestamp_old) / 1000.0f; // Real time of loop run. We use this on the DCM algorithm (gyro integration time)
    else G_Dt = 0;

    // Update sensor readings
    read_sensors();
    
    yaw += TO_DEG(gyro[2]) * G_Dt + (degreedriftpersecond / 1000) * (G_Dt * 1000);
    
    //sends the data to gyro
    //PrintFloat(yaw);
    
    sendNXTdata(map(yaw, -180, 180, 0, 512));
    
    //sendNXTdata();
 }
}
